package work.microhand.sanseyooyea.moreitems.common.item.multifuncswordtool;

import work.microhand.sanseyooyea.moreitems.MoreItems;

/**
 * @author SanseYooyea
 */
public class StoneSwordTool extends AbstractMultiFuncSwordTool {
    private static final ToolMaterial MATERIAL;

    static {
        MATERIAL = ToolMaterial.STONE;
    }

    public StoneSwordTool() {
        super(MATERIAL);
        this.setRegistryName(MoreItems.MOD_ID, "stone_sword_tool");
        this.setTranslationKey(MoreItems.MOD_ID + ".stone_sword_tool");
    }
}
