package work.microhand.sanseyooyea.moreitems.common.item.multifuncswordtool;

import work.microhand.sanseyooyea.moreitems.MoreItems;

/**
 * @author SanseYooyea
 */
public class DiamondSwordTool extends AbstractMultiFuncSwordTool {
    private static final ToolMaterial MATERIAL;

    static {
        MATERIAL = ToolMaterial.DIAMOND;
    }

    public DiamondSwordTool() {
        super(MATERIAL);
        this.setRegistryName(MoreItems.MOD_ID, "diamond_sword_tool");
        this.setTranslationKey(MoreItems.MOD_ID + ".diamond_sword_tool");
    }
}
