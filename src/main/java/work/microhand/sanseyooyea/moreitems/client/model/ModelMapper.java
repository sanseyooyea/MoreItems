package work.microhand.sanseyooyea.moreitems.client.model;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import work.microhand.sanseyooyea.moreitems.MoreItems;
import work.microhand.sanseyooyea.moreitems.common.item.ItemInitializer;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author SanseYooyea
 */
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MoreItems.MOD_ID)
public class ModelMapper {
    @SubscribeEvent
    public static void onModelReg(ModelRegistryEvent event) {
        Arrays.stream(ItemInitializer.items)
                .forEach(
                        item -> ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"))
                );
    }
}
