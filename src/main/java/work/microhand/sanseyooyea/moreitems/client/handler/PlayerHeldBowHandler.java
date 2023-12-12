package work.microhand.sanseyooyea.moreitems.client.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import work.microhand.sanseyooyea.moreitems.MoreItems;
import work.microhand.sanseyooyea.moreitems.common.item.bow.AbstractBow;
import work.microhand.sanseyooyea.moreitems.common.network.PacketAddPotion;

/**
 * @author SanseYooyea
 */
@Mod.EventBusSubscriber(modid = MoreItems.MOD_ID)
public class PlayerHeldBowHandler {
    @SubscribeEvent
    public static void onPlayerHeldBow(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }

        EntityPlayer player = event.player;

        if (!(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof AbstractBow)
                && !(player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof AbstractBow)) {
            return;
        }

        PacketAddPotion packet = new PacketAddPotion();
        MoreItems.NETWORK.sendToServer(packet);
    }
}
