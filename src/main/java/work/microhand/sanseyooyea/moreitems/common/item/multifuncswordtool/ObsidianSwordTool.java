package work.microhand.sanseyooyea.moreitems.common.item.multifuncswordtool;

import work.microhand.sanseyooyea.moreitems.MoreItems;
import work.microhand.sanseyooyea.moreitems.common.init.Material;

/**
 * @author SanseYooyea
 */
public class ObsidianSwordTool extends AbstractMultiFuncSwordTool {
    private static final ToolMaterial MATERIAL;

    static {
        MATERIAL = Material.OBSIDIAN;
    }

    public ObsidianSwordTool() {
        super(MATERIAL);
        this.setRegistryName(MoreItems.MOD_ID, "obsidian_sword_tool");
        this.setTranslationKey(MoreItems.MOD_ID + ".obsidian_sword_tool");
    }
}
