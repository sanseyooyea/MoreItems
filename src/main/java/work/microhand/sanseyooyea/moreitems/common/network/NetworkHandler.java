package work.microhand.sanseyooyea.moreitems.common.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.FMLOutboundHandler.OutboundTarget;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.microhand.sanseyooyea.moreitems.MoreItems;
import work.microhand.sanseyooyea.moreitems.common.network.utils.PacketBase;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author SanseYooyea
 */
@Sharable
public class NetworkHandler extends MessageToMessageCodec<FMLProxyPacket, PacketBase> {
    private final LinkedList<Class<? extends PacketBase>> packets = new LinkedList<>();
    private final ConcurrentLinkedQueue<PacketBase> receivedPacketsClient = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<PacketBase>> receivedPacketsServer = new ConcurrentHashMap<>();
    private EnumMap<Side, FMLEmbeddedChannel> channels;
    private boolean modInitialised = false;

    public NetworkHandler() {
    }

    public boolean registerPacket(Class<? extends PacketBase> cl) {
        if (packets.size() > 256) {
            MoreItems.log.warn("Packet limit exceeded in More Items packet handler by packet " + cl.getCanonicalName() + ".");
            return false;
        } else if (packets.contains(cl)) {
            MoreItems.log.warn("Tried to register " + cl.getCanonicalName() + " packet class twice.");
            return false;
        } else if (modInitialised) {
            MoreItems.log.warn("Tried to register packet " + cl.getCanonicalName() + " after mod initialisation.");
            return false;
        } else {
            packets.add(cl);
            return true;
        }
    }

    protected void encode(ChannelHandlerContext ctx, PacketBase msg, List<Object> out) throws Exception {
        try {
            ByteBuf encodedData = Unpooled.buffer();
            Class<? extends PacketBase> cl = msg.getClass();
            if (!packets.contains(cl)) {
                throw new NullPointerException("Packet not registered : " + cl.getCanonicalName());
            }

            byte discriminator = (byte) packets.indexOf(cl);
            encodedData.writeByte(discriminator);
            msg.encodeInto(ctx, encodedData);
            FMLProxyPacket proxyPacket = new FMLProxyPacket(new PacketBuffer(encodedData.copy()), ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
            out.add(proxyPacket);
        } catch (Exception var8) {
            MoreItems.log.error("ERROR encoding packet");
            MoreItems.log.throwing(var8);
        }

    }

    protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception {
        try {
            ByteBuf encodedData = msg.payload();
            byte discriminator = encodedData.readByte();
            Class<? extends PacketBase> cl = packets.get(discriminator);
            if (cl == null) {
                throw new NullPointerException("Packet not registered for discriminator : " + discriminator);
            }

            PacketBase packet = cl.newInstance();
            packet.decodeInto(ctx, encodedData.slice());
            switch (FMLCommonHandler.instance().getEffectiveSide()) {
                case CLIENT:
                    receivedPacketsClient.offer(packet);
                    break;
                case SERVER:
                    INetHandler netHandler = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
                    EntityPlayer player = ((NetHandlerPlayServer) netHandler).player;
                    if (!receivedPacketsServer.containsKey(player.getName())) {
                        receivedPacketsServer.put(player.getName(), new ConcurrentLinkedQueue<>());
                    }

                    receivedPacketsServer.get(player.getName()).offer(packet);
                    break;
                default:
                    // do nothing
            }
        } catch (Exception var10) {
            MoreItems.log.error("ERROR decoding packet");
            MoreItems.log.throwing(var10);
        }

    }

    public void handleClientPackets() {
        for (PacketBase packet = receivedPacketsClient.poll(); packet != null; packet = receivedPacketsClient.poll()) {
            packet.handleClientSide(getClientPlayer());
        }

    }

    public void handleServerPackets() {
        Iterator<String> var1 = receivedPacketsServer.keySet().iterator();

        while (true) {
            while (var1.hasNext()) {
                String playerName = var1.next();
                ConcurrentLinkedQueue<PacketBase> receivedPacketsFromPlayer = receivedPacketsServer.get(playerName);
                EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(playerName);
                if (player == null) {
                    receivedPacketsFromPlayer.clear();
                } else {
                    for (PacketBase packet = receivedPacketsFromPlayer.poll(); packet != null; packet = receivedPacketsFromPlayer.poll()) {
                        packet.handleServerSide(player);
                    }
                }
            }

            return;
        }
    }

    public void initialise() {
        channels = NetworkRegistry.INSTANCE.newChannel("MoreItems", this);
        registerPacket(PacketAddPotion.class);
    }

    public void postInitialise() {
        if (!modInitialised) {
            modInitialised = true;
            packets.sort(new Comparator<Class<? extends PacketBase>>() {
                public int compare(Class<? extends PacketBase> c1, Class<? extends PacketBase> c2) {
                    int com = String.CASE_INSENSITIVE_ORDER.compare(c1.getCanonicalName(), c2.getCanonicalName());
                    if (com == 0) {
                        com = c1.getCanonicalName().compareTo(c2.getCanonicalName());
                    }

                    return com;
                }
            });
        }
    }

    @SideOnly(Side.CLIENT)
    private EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }

    public void sendToAll(PacketBase packet) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(OutboundTarget.ALL);
        channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public void sendTo(PacketBase packet, EntityPlayerMP player) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(OutboundTarget.PLAYER);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public void sendToAllAround(PacketBase packet, NetworkRegistry.TargetPoint point) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(OutboundTarget.ALLAROUNDPOINT);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
        channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public void sendToDimension(PacketBase packet, int dimensionID) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(OutboundTarget.DIMENSION);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionID);
        channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public void sendToServer(PacketBase packet) {
        channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(OutboundTarget.TOSERVER);
        channels.get(Side.CLIENT).writeAndFlush(packet);
    }

    public void sendToAllTracking(PacketBase packet, Entity entity) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(OutboundTarget.TRACKING_ENTITY);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(entity);
        channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public void sendToAll(Packet packet) {
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendPacketToAllPlayers(packet);
    }

    public void sendTo(Packet packet, EntityPlayerMP player) {
        player.connection.sendPacket(packet);
    }

    public void sendToAllAround(Packet packet, NetworkRegistry.TargetPoint point) {
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendToAllNearExcept(null, point.x, point.y, point.z, point.range, point.dimension, packet);
    }

    public void sendToDimension(Packet packet, int dimensionID) {
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendPacketToAllPlayersInDimension(packet, dimensionID);
    }

    public void sendToServer(Packet packet) {
        Minecraft.getMinecraft().player.connection.sendPacket(packet);
    }

    public void sendToAllAround(PacketBase packet, double x, double y, double z, float range, int dimension) {
        sendToAllAround(packet, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
    }
}