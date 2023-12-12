package work.microhand.sanseyooyea.moreitems.common.item.bow;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

/**
 * @author SanseYooyea
 */
public abstract class AbstractBow extends ItemBow {
    protected int consumeAmount;

    protected double extraDamage;

    protected PotionEffect potionEffect;

    public AbstractBow(int consumeAmount, double extraDamage, int maxDamage, PotionEffect potionEffect) {
        this.consumeAmount = consumeAmount;
        this.extraDamage = extraDamage;
        this.potionEffect = potionEffect;

        this.setMaxDamage(maxDamage);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    public PotionEffect getPotionEffect() {
        return potionEffect;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack bow, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (!(entityLiving instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer entityplayer = (EntityPlayer) entityLiving;
        boolean infinity = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow) > 0;
        ItemStack ammo = this.findAmmo(entityplayer);

        int charge = this.getMaxItemUseDuration(bow) - timeLeft;
        charge = ForgeEventFactory.onArrowLoose(bow, worldIn, entityplayer, charge, !ammo.isEmpty() || infinity);
        if (charge < 0) {
            return;
        }

        if (ammo.isEmpty() && !infinity) {
            return;
        }

        if (ammo.isEmpty()) {
            ammo = new ItemStack(Items.ARROW);
        }

        float arrowVelocity = getArrowVelocity(charge);

        if ((double) arrowVelocity < 0.1D) {
            return;
        }

        boolean infinity1 = entityplayer.capabilities.isCreativeMode || (ammo.getItem() instanceof ItemArrow && ((ItemArrow) ammo.getItem()).isInfinite(ammo, bow, entityplayer));

        if (!worldIn.isRemote) {
            ItemArrow itemarrow = (ItemArrow) (ammo.getItem() instanceof ItemArrow ? ammo.getItem() : Items.ARROW);
            EntityArrow entityarrow = itemarrow.createArrow(worldIn, ammo, entityplayer);
            entityarrow = this.customizeArrow(entityarrow);
            entityarrow.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, arrowVelocity * 3.0F, 1.0F);

            if (Float.compare(arrowVelocity, 1.0F) == 0) {
                entityarrow.setIsCritical(true);
            }

            // 力量
            int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, bow);
            if (powerLevel > 0) {
                entityarrow.setDamage(entityarrow.getDamage() + (double) powerLevel * 0.5D + 0.5D + extraDamage);
            }

            // 冲击
            int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, bow);
            if (punchLevel > 0) {
                entityarrow.setKnockbackStrength(punchLevel);
            }

            // 点燃
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, bow) > 0) {
                entityarrow.setFire(100);
            }

            bow.damageItem(1, entityplayer);

            if (infinity1 || entityplayer.capabilities.isCreativeMode && (ammo.getItem() == Items.SPECTRAL_ARROW || ammo.getItem() == Items.TIPPED_ARROW)) {
                entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
            }

            worldIn.spawnEntity(entityarrow);
        }

        worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + arrowVelocity * 0.5F);

        if (!infinity1 && !entityplayer.capabilities.isCreativeMode) {
            ammo.shrink(consumeAmount);

            if (ammo.isEmpty()) {
                entityplayer.inventory.deleteStack(ammo);
            }
        }

        entityplayer.addStat(StatList.getObjectUseStats(Items.BOW));
    }


    @Override
    protected boolean isArrow(ItemStack stack) {
        return stack.getItem() instanceof ItemArrow && stack.getCount() >= consumeAmount;
    }
}