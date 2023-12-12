package work.microhand.sanseyooyea.moreitems.common.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import work.microhand.sanseyooyea.moreitems.common.item.bow.AbstractBow;
import work.microhand.sanseyooyea.moreitems.common.network.utils.PacketBase;

/**
 * @author SanseYooyea
 */
public class PacketAddPotion extends PacketBase {

    @Override
    public void encodeInto(ChannelHandlerContext var1, ByteBuf buf) {

    }

    @Override
    public void decodeInto(ChannelHandlerContext var1, ByteBuf buf) {

    }

    @Override
    public void handleServerSide(EntityPlayerMP entityPlayerMP) {
        if (entityPlayerMP.getHeldItemMainhand().getItem() instanceof AbstractBow) {
            AbstractBow bow = (AbstractBow) entityPlayerMP.getHeldItemMainhand().getItem();
            PotionEffect effect = bow.getPotionEffect();
            if (effect == null) {
                return;
            }

            entityPlayerMP.addPotionEffect(effect);
            return;
        }

        if (entityPlayerMP.getHeldItemOffhand().getItem() instanceof AbstractBow) {
            AbstractBow bow = (AbstractBow) entityPlayerMP.getHeldItemOffhand().getItem();
            PotionEffect effect = bow.getPotionEffect();
            if (effect == null) {
                return;
            }

            entityPlayerMP.addPotionEffect(effect);
        }
    }

    @Override
    public void handleClientSide(EntityPlayer entityPlayer) {

    }
}
