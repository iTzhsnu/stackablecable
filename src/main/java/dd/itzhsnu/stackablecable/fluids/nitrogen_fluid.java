package dd.itzhsnu.stackablecable.fluids;

import dd.itzhsnu.stackablecable.registrys.BlocksInit;
import dd.itzhsnu.stackablecable.registrys.FluidsInit;
import dd.itzhsnu.stackablecable.registrys.ItemsInit;
import dd.itzhsnu.stackablecable.registrys.TagsInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fluids.FluidAttributes;

public abstract class nitrogen_fluid extends FlowingFluid {
    @Override
    public Fluid getFlowing() {
        return FluidsInit.FLOWING_NITROGEN.get();
    }

    @Override
    public Fluid getSource() {
        return FluidsInit.STILL_NITROGEN.get();
    }

    @Override
    public Item getBucket() {
        return ItemsInit.NITROGEN_BUCKET.get();
    }

    @Override
    protected boolean canConvertToSource() {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(IWorld worldIn, BlockPos pos, BlockState state) {
    }

    @Override
    protected int getSlopeFindDistance(IWorldReader worldIn) {
        return 4;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, IBlockReader world, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.is(TagsInit.NITROGEN);
    }

    @Override
    public int getTickDelay(IWorldReader p_205569_1_) {
        return 5;
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    public boolean isSame(Fluid fluidIn) {
        return fluidIn == FluidsInit.STILL_NITROGEN.get() || fluidIn == FluidsInit.FLOWING_NITROGEN.get();
    }

    @Override
    protected int getDropOff(IWorldReader p_204528_1_) {
        return 1;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return BlocksInit.NITROGEN_BLOCK.get().defaultBlockState().setValue(FlowingFluidBlock.LEVEL, getLegacyLevel(state));
    }

    @Override
    protected FluidAttributes createAttributes() {
        return FluidAttributes
                .builder(new ResourceLocation("scable", "block/nitrogen_still"), new ResourceLocation("scable", "block/nitrogen_flow"))
                .translationKey("block.scable.nitrogen").build(this);
    }

    public static class Flowing extends nitrogen_fluid {

        @Override
        protected void createFluidStateDefinition(StateContainer.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }

        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

    }

    public static class Source extends nitrogen_fluid {

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }

        @Override
        public int getAmount(FluidState state) {
            return 8;
        }
    }
}
