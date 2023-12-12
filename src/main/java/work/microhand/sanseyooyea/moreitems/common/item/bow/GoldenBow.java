package work.microhand.sanseyooyea.moreitems.common.item.bow;

import work.microhand.sanseyooyea.moreitems.MoreItems;

/**
 * @author SanseYooyea
 */
public class GoldenBow extends AbstractBow {
    public GoldenBow() {
        super(1, 1D, 256, null);
        this.setRegistryName(MoreItems.MOD_ID, "golden_bow");
        this.setTranslationKey(MoreItems.MOD_ID + ".golden_bow");
    }
}
