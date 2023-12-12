package work.microhand.sanseyooyea.moreitems.common.item.bow;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import work.microhand.sanseyooyea.moreitems.MoreItems;

/**
 * @author SanseYooyea
 */
public class ObsidianBow extends AbstractBow {
    public ObsidianBow() {
        super(1, 4D, 1024, new PotionEffect(MobEffects.RESISTANCE, 20, 0));
        this.setRegistryName(MoreItems.MOD_ID, "obsidian_bow");
        this.setTranslationKey(MoreItems.MOD_ID + ".obsidian_bow");
    }
}
