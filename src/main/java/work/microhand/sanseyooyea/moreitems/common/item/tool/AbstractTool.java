package work.microhand.sanseyooyea.moreitems.common.item.tool;

import net.minecraft.item.*;
import work.microhand.sanseyooyea.moreitems.MoreItems;

/**
 * @author SanseYooyea
 */
public abstract class AbstractTool {
    public AbstractSword sword;
    public AbstractSpade spade;
    public AbstractPickaxe pickaxe;
    public AbstractAxe axe;
    public AbstractHoe hoe;
    public Item.ToolMaterial material;
    public Item[] tools;

    public AbstractTool(Item.ToolMaterial material) {
        this.material = material;
        tools = new Item[5];

        sword = new AbstractSword();
        spade = new AbstractSpade();
        pickaxe = new AbstractPickaxe();
        axe = new AbstractAxe();
        hoe = new AbstractHoe();

        tools[0] = sword;
        tools[1] = spade;
        tools[2] = pickaxe;
        tools[3] = axe;
        tools[4] = hoe;
    }

    class AbstractSword extends ItemSword {
        public AbstractSword() {
            super(material);
            this.setRegistryName(MoreItems.MOD_ID, material.name().toLowerCase() + "_sword");
            this.setTranslationKey(MoreItems.MOD_ID + "." + material.name().toLowerCase() + "_sword");
        }
    }

    class AbstractSpade extends ItemSpade {
        public AbstractSpade() {
            super(material);
            this.setRegistryName(MoreItems.MOD_ID, material.name().toLowerCase() + "_shovel");
            this.setTranslationKey(MoreItems.MOD_ID + "." + material.name().toLowerCase() + "_shovel");
        }
    }

    class AbstractPickaxe extends ItemPickaxe {
        public AbstractPickaxe() {
            super(material);
            this.setRegistryName(MoreItems.MOD_ID, material.name().toLowerCase() + "_pickaxe");
            this.setTranslationKey(MoreItems.MOD_ID + "." + material.name().toLowerCase() + "_pickaxe");
        }
    }

    class AbstractAxe extends ItemAxe {
        public AbstractAxe() {
            super(material, material.getAttackDamage() + 5F, -3.0F);
            this.setRegistryName(MoreItems.MOD_ID, material.name().toLowerCase() + "_axe");
            this.setTranslationKey(MoreItems.MOD_ID + "." + material.name().toLowerCase() + "_axe");
        }
    }

    class AbstractHoe extends ItemHoe {
        public AbstractHoe() {
            super(material);
            this.setRegistryName(MoreItems.MOD_ID, material.name().toLowerCase() + "_hoe");
            this.setTranslationKey(MoreItems.MOD_ID + "." + material.name().toLowerCase() + "_hoe");
        }
    }

}