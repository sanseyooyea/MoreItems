package work.microhand.sanseyooyea.moreitems.common.item.multifuncswordtool;

import work.microhand.sanseyooyea.moreitems.MoreItems;

/**
 * @author SanseYooyea
 */
public class IronSwordTool extends AbstractMultiFuncSwordTool {
    private static final ToolMaterial MATERIAL;

    static {
        MATERIAL = ToolMaterial.IRON;
    }

    public IronSwordTool() {
        super(MATERIAL);
        this.setRegistryName(MoreItems.MOD_ID, "iron_sword_tool");
        this.setTranslationKey(MoreItems.MOD_ID + ".iron_sword_tool");
    }
}
