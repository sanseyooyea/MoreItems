package work.microhand.sanseyooyea.moreitems.common.item;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import work.microhand.sanseyooyea.moreitems.MoreItems;
import work.microhand.sanseyooyea.moreitems.common.item.bow.*;
import work.microhand.sanseyooyea.moreitems.common.item.multifuncswordtool.*;
import work.microhand.sanseyooyea.moreitems.common.item.tool.EmeraldTool;
import work.microhand.sanseyooyea.moreitems.common.item.tool.ObsidianTool;

/**
 * @author SanseYooyea
 */
@Mod.EventBusSubscriber(modid = MoreItems.MOD_ID)
public class ItemInitializer {
    public static Item[] items = new Item[23];
    public static AbstractBow[] bows = new AbstractBow[6];

    public static Item[] emeraldTools = new EmeraldTool().tools;
    public static Item[] obsidianTools = new ObsidianTool().tools;

    public static AbstractMultiFuncSwordTool[] multiFuncSwordTools = new AbstractMultiFuncSwordTool[6];

    static {
        // 弓
        bows[0] = new FlamingBow();
        bows[1] = new IronBow();
        bows[2] = new GoldenBow();
        bows[3] = new DiamondBow();
        bows[4] = new EmeraldBow();
        bows[5] = new ObsidianBow();

        // 多功能工具
        multiFuncSwordTools[0] = new StoneSwordTool();
        multiFuncSwordTools[1] = new IronSwordTool();
        multiFuncSwordTools[2] = new GoldenSwordTool();
        multiFuncSwordTools[3] = new DiamondSwordTool();
        multiFuncSwordTools[4] = new EmeraldSwordTool();
        multiFuncSwordTools[5] = new ObsidianSwordTool();

        // 将 bows 和 tools 中的元素赋值给 items
        int index = 0;
        for (Item item : bows) {
            items[index++] = item;
        }

        // 绿宝石工具
        for (Item item : emeraldTools) {
            items[index++] = item;
        }
        // 黑曜石工具
        for (Item item : obsidianTools) {
            items[index++] = item;
        }
        for (Item item : multiFuncSwordTools) {
            items[index++] = item;
        }

        // 凋零之首
        items[index++] = new ItemWitherSkull();
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                items
        );
    }
}
