package work.microhand.sanseyooyea.moreitems.common.item.multifuncswordtool;

import work.microhand.sanseyooyea.moreitems.MoreItems;

/**
 * @author SanseYooyea
 */
public class GoldenSwordTool extends AbstractMultiFuncSwordTool {
    private static final ToolMaterial MATERIAL;

    static {
        MATERIAL = ToolMaterial.GOLD;
    }

    public GoldenSwordTool() {
        super(MATERIAL);
        this.setRegistryName(MoreItems.MOD_ID, "golden_sword_tool");
        this.setTranslationKey(MoreItems.MOD_ID + ".golden_sword_tool");
    }
}
