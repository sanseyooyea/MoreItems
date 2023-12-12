package work.microhand.sanseyooyea.moreitems.common.item.bow;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.microhand.sanseyooyea.moreitems.MoreItems;

import javax.annotation.Nullable;

/**
 * @author SanseYooyea
 */
public class FlamingBow extends AbstractBow {
    public FlamingBow() {
        super(3, 0D, 384, null);
        this.setTranslationKey(MoreItems.MOD_ID + ".flaming_bow");
        this.setRegistryName(MoreItems.MOD_ID, "flaming_bow");
        this.addPropertyOverride(new ResourceLocation(MoreItems.MOD_ID + ":textures/items/烈焰长弓/bow_0"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (entityIn == null) {
                    return 0.0F;
                } else {
                    return !(entityIn.getActiveItemStack().getItem() instanceof ItemBow) ? 0.0F : (float) (stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation(MoreItems.MOD_ID + ":textures/items/烈焰长弓/bow_2"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
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
            Vec3d vec3d = entityplayer.getLook(1.0F);
            double d2 = entityplayer.posX - (entityplayer.posX + vec3d.x * 4.0D);
            double d3 = entityplayer.getEntityBoundingBox().minY + (double) (entityplayer.height / 2.0F) - (0.5D + entityplayer.posY + (entityplayer.height / 2.0F));
            double d4 = entityplayer.posZ - (entityplayer.posZ + vec3d.z * 4.0D);
            worldIn.playEvent(null, 1016, new BlockPos(entityplayer), 0);
            EntityLargeFireball entityfireball = new EntityLargeFireball(worldIn, entityplayer, -d2, d3, -d4);
            entityfireball.explosionPower = 0;
            entityfireball.posX = entityplayer.posX + vec3d.x * 4.0D;
            entityfireball.posY = entityplayer.posY + (double) (entityplayer.height / 2.0F) + 0.5D;
            entityfireball.posZ = entityplayer.posZ + vec3d.z * 4.0D;
            worldIn.spawnEntity(entityfireball);
            bow.damageItem(1, entityplayer);
        }

        worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + arrowVelocity * 0.5F);

        if (!infinity1 && !entityplayer.capabilities.isCreativeMode) {
            ammo.shrink(consumeAmount);

            if (ammo.isEmpty()) {
                entityplayer.inventory.deleteStack(ammo);
            }
        }

        entityplayer.addStat(StatList.getObjectUseStats(Items.BOW));
    }
}
