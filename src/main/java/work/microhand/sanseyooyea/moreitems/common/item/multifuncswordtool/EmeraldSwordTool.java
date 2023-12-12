package work.microhand.sanseyooyea.moreitems.common.item.multifuncswordtool;

import net.minecraft.item.Item;
import work.microhand.sanseyooyea.moreitems.MoreItems;
import work.microhand.sanseyooyea.moreitems.common.init.Material;

/**
 * @author SanseYooyea
 */
public class EmeraldSwordTool extends AbstractMultiFuncSwordTool {
    private static final Item.ToolMaterial MATERIAL;

    static {
        MATERIAL = Material.EMERALD;
    }

    public EmeraldSwordTool() {
        super(MATERIAL);
        this.setRegistryName(MoreItems.MOD_ID, "emerald_sword_tool");
        this.setTranslationKey(MoreItems.MOD_ID + ".emerald_sword_tool");
    }
}
