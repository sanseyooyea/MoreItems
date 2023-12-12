package work.microhand.sanseyooyea.moreitems.common.item.bow;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import work.microhand.sanseyooyea.moreitems.MoreItems;

/**
 * @author SanseYooyea
 */
public class EmeraldBow extends AbstractBow {
    public EmeraldBow() {
        super(1, 4D, 1024, new PotionEffect(MobEffects.SPEED, 20, 1));
        this.setRegistryName(MoreItems.MOD_ID, "emerald_bow");
        this.setTranslationKey(MoreItems.MOD_ID + ".emerald_bow");
    }
}
