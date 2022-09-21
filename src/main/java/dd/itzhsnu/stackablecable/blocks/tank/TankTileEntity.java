package dd.itzhsnu.stackablecable.blocks.tank;

import dd.itzhsnu.stackablecable.registrys.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TankTileEntity extends TileEntity {
    public TankTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public TankTileEntity() {
        this(TileEntityInit.TANK_TILE_ENTITY.get());
    }

    protected FluidTank tank = createFHandler();
    private final LazyOptional<IFluidHandler> handler = LazyOptional.of(() -> tank);

    private FluidTank createFHandler() {
        return new FluidTank(64000) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                super.onContentsChanged();
            }
        };
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        this.tank.writeToNBT(compound);

        return compound;
    }

    @Override
    public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        this.tank.readFromNBT(compound);

    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    public FluidTank getTank() {
        return this.tank;
    }

}
