package dd.itzhsnu.stackablecable.blocks.compressor;

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
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;
import java.util.UUID;

public class CompressorBlock extends Block {
    public CompressorBlock() {
        super(Properties.of(Material.METAL).strength(4F, 50F).sound(SoundType.METAL)
                .requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(2));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityInit.COMPRESSOR_TILE_ENTITY.get().create();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isClientSide()) {
            if (FluidUtil.interactWithFluidHandler(player, hand, world, pos, hit.getDirection())) {
                return ActionResultType.SUCCESS;
            } else {
                TileEntity te = world.getBlockEntity(pos);
                player.sendMessage(new TranslationTextComponent("text.scable.nitrogen_amount", te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).map(h -> h.getFluidInTank(0).getAmount()).get(), te.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).get()), UUID.randomUUID());
                return ActionResultType.CONSUME;
            }
        }
        return ActionResultType.CONSUME;
    }
}
