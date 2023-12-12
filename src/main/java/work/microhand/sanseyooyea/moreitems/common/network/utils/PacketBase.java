package work.microhand.sanseyooyea.moreitems.common.network.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author SanseYooyea
 */
public abstract class PacketBase {
    public PacketBase() {
    }

    public static void writeUTF(ByteBuf data, String s) {
        ByteBufUtils.writeUTF8String(data, s);
    }

    public static String readUTF(ByteBuf data) {
        return ByteBufUtils.readUTF8String(data);
    }

    public abstract void encodeInto(ChannelHandlerContext var1, ByteBuf buf);

    public abstract void decodeInto(ChannelHandlerContext var1, ByteBuf buf);

    public abstract void handleServerSide(EntityPlayerMP entityPlayerMP);

    @SideOnly(Side.CLIENT)
    public abstract void handleClientSide(EntityPlayer entityPlayer);
}