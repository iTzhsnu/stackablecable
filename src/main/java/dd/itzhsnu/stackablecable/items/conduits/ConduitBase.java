package dd.itzhsnu.stackablecable.items.conduits;

import dd.itzhsnu.stackablecable.registrys.BlocksInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ConduitBase extends Item {
    public ConduitBase(Properties properties) {
        super(properties);
    }

    public static ActionResultType conduitSet(ItemUseContext context, BlockState conduit) {
            World world = context.getLevel();
            BlockPos clickPos = context.getClickedPos();
            BlockState clickPosState = context.getLevel().getBlockState(clickPos);
            BlockPos pos = clickPos.relative(context.getClickedFace());
            BlockState state = context.getLevel().getBlockState(pos);

            if (state.is(Blocks.AIR) || state.is(Blocks.CAVE_AIR) && !clickPosState.is(BlocksInit.CONDUIT_BLOCK.get())) {
                if (!world.isClientSide()) {
                    world.setBlock(pos, conduit, 11);
                    if (!context.getPlayer().isCreative()) {
                        int count = context.getItemInHand().getCount() - 1;
                        ItemStack stack = context.getItemInHand().copy();
                        stack.setCount(count);
                        context.getPlayer().setItemInHand(context.getHand(), stack);
                    }
                } else {
                    context.getPlayer().playSound(SoundEvents.WOOL_PLACE, 1, 1);
                }
                return ActionResultType.SUCCESS;
            }

        return ActionResultType.FAIL;
    }
}
