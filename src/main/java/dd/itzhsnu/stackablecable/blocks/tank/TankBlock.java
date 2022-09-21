package dd.itzhsnu.stackablecable.blocks.tank;

import dd.itzhsnu.stackablecable.registrys.TileEntityInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;
import java.util.UUID;

public class TankBlock extends Block {
    public TankBlock() {
        super(Properties.of(Material.GLASS).strength(2F, 10F).sound(SoundType.GLASS)
                .requiresCorrectToolForDrops().harvestLevel(1).harvestTool(ToolType.PICKAXE));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityInit.TANK_TILE_ENTITY.get().create();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult brtResult) {
            if (FluidUtil.interactWithFluidHandler(player, hand, world, pos, brtResult.getDirection())) {
                return ActionResultType.SUCCESS;
            } else if (!world.isClientSide()) {
                LazyOptional<IFluidHandler> handler = world.getBlockEntity(pos).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
                player.sendMessage(new TranslationTextComponent("text.scable.stored_fluid", handler.map(h -> h.getFluidInTank(0).getFluid()).get().getRegistryName(), handler.map(h -> h.getFluidInTank(0).getAmount()).get()), UUID.randomUUID());
                return ActionResultType.CONSUME;
            }
            return ActionResultType.CONSUME;
    }
}
