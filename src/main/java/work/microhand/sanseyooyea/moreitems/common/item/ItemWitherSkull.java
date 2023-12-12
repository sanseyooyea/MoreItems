package work.microhand.sanseyooyea.moreitems.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import work.microhand.sanseyooyea.moreitems.MoreItems;

/**
 * @author SanseYooyea
 */
public class ItemWitherSkull extends Item {
    public ItemWitherSkull() {
        super();
        this.setRegistryName(MoreItems.MOD_ID, "wither_skull");
        this.setTranslationKey(MoreItems.MOD_ID + ".wither_skull");
        this.setCreativeTab(CreativeTabs.MISC);
        this.setMaxStackSize(1);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!worldIn.isRemote) {
            Vec3d vec3d = playerIn.getLook(1.0F);
            double d2 = playerIn.posX - (playerIn.posX + vec3d.x * 4.0D);
            double d3 = playerIn.getEntityBoundingBox().minY + (double) (playerIn.height / 2.0F) - (0.5D + playerIn.posY + (playerIn.height / 2.0F));
            double d4 = playerIn.posZ - (playerIn.posZ + vec3d.z * 4.0D);
            worldIn.playEvent(null, 1016, new BlockPos(playerIn), 0);
            EntityWitherSkull entitywitherskull = new EntityWitherSkull(worldIn, playerIn, -d2, d3, -d4);
            entitywitherskull.posX = playerIn.posX + vec3d.x * 4.0D;
            entitywitherskull.posY = playerIn.posY + (double) (playerIn.height / 2.0F) + 0.5D;
            entitywitherskull.posZ = playerIn.posZ + vec3d.z * 4.0D;
            worldIn.spawnEntity(entitywitherskull);
            playerIn.getHeldItem(handIn).damageItem(1, playerIn);
        }
        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }


}
