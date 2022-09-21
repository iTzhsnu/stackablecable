package dd.itzhsnu.stackablecable.items;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.tags.BlockTags;

public class Other_Shears extends ShearsItem {
    private final IItemTier tier;

    public Other_Shears(IItemTier tier, Properties properties) {
        super(properties.durability(tier.getUses()));
        this.tier = tier;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (!state.is(Blocks.COBWEB) && !state.is(BlockTags.LEAVES)) {
            float count = tier.getSpeed() - 1.0F;
            return state.is(BlockTags.WOOL) ? count : super.getDestroySpeed(stack, state);
        } else {
            return tier.getSpeed() + 9.0F;
        }
    }
}
