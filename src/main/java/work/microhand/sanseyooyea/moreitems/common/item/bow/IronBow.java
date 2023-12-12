package work.microhand.sanseyooyea.moreitems.common.item.bow;

import work.microhand.sanseyooyea.moreitems.MoreItems;

/**
 * @author SanseYooyea
 */
public class IronBow extends AbstractBow {
    public IronBow() {
        super(1, 2D, 512, null);
        this.setRegistryName(MoreItems.MOD_ID, "iron_bow");
        this.setTranslationKey(MoreItems.MOD_ID + ".iron_bow");
    }
}
