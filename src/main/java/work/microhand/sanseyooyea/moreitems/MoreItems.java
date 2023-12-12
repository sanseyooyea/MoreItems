package work.microhand.sanseyooyea.moreitems;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import work.microhand.sanseyooyea.moreitems.common.CommonProxy;
import work.microhand.sanseyooyea.moreitems.common.network.NetworkHandler;

/**
 * @author SanseYooyea
 */
@Mod(
        modid = MoreItems.MOD_ID,
        name = MoreItems.MOD_NAME,
        version = MoreItems.VERSION
)
public class MoreItems {

    public static final String MOD_ID = "moreitems";
    public static final String MOD_NAME = "MoreItems";
    public static final String VERSION = "1.0-SNAPSHOT";
    public static Logger log;

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static MoreItems INSTANCE;

    @SidedProxy(clientSide = "work.microhand.sanseyooyea.moreitems.client.ClientProxy",
            serverSide = "work.microhand.sanseyooyea.moreitems.common.CommonProxy")
    public static CommonProxy proxy;

    public static NetworkHandler NETWORK;

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        log = event.getModLog();
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        NETWORK = new NetworkHandler();
        NETWORK.initialise();
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        NETWORK.postInitialise();
    }
}
