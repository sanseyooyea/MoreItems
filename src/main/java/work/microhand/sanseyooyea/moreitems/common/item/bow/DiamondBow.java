package work.microhand.sanseyooyea.moreitems.common.item.bow;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import work.microhand.sanseyooyea.moreitems.MoreItems;

/**
 * @author SanseYooyea
 */
public class DiamondBow extends AbstractBow {
    public DiamondBow() {
        super(1, 3D, 768, new PotionEffect(MobEffects.STRENGTH, 20, 1));
        this.setRegistryName(MoreItems.MOD_ID, "diamond_bow");
        this.setTranslationKey(MoreItems.MOD_ID + ".diamond_bow");
    }
}
