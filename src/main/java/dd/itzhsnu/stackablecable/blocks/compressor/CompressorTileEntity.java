package dd.itzhsnu.stackablecable.blocks.compressor;

import dd.itzhsnu.stackablecable.blocks.capacitor.CapacitorEnergyStorage;
import dd.itzhsnu.stackablecable.registrys.FluidsInit;
import dd.itzhsnu.stackablecable.registrys.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CompressorTileEntity extends TileEntity implements ITickableTileEntity {
    public CompressorTileEntity(TileEntityType<?> tileEntityIn) {
        super(tileEntityIn);
    }

    public CompressorTileEntity() {
        this(TileEntityInit.COMPRESSOR_TILE_ENTITY.get());
    }

    protected FluidTank tank = createTank();
    private final LazyOptional<IFluidHandler> fHandler = LazyOptional.of(() -> tank);
    protected CapacitorEnergyStorage storage = new CapacitorEnergyStorage(100000, 100000, 0);
    private final LazyOptional<IEnergyStorage> eHandler = LazyOptional.of(() -> storage);
    private int pressTime;

    private FluidTank createTank() {
        return new FluidTank(8000) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                super.onContentsChanged();
            }

            @Override
            public int fill(FluidStack resource, FluidAction action) {
                return 0;
            }
        };
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        this.tank.writeToNBT(nbt);
        this.storage.writeToNBT(nbt);
        nbt.putInt("PressTime", this.pressTime);

        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.tank.readFromNBT(nbt);
        this.storage.readFromNBT(nbt);
        this.pressTime = nbt.getInt("PressTime");
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fHandler.cast();
        } else if (cap == CapabilityEnergy.ENERGY) {
            return eHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public FluidTank getTank() {
        return this.tank;
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            if (storage.getEnergyStored() >= 160 && tank.getFluidAmount() <= 7000) {
                this.pressTime = this.pressTime + 1;
                int energy = storage.getEnergyStored() - 160;
                storage.setEnergy(energy);
                if (this.pressTime == 200) {
                    int mBucket = tank.getFluidAmount() + 1000;
                    FluidStack fluid = new FluidStack(FluidsInit.STILL_NITROGEN.get(), mBucket);
                    tank.setFluid(fluid);
                    this.pressTime = 0;
                    setChanged();
                }
            }
        }
    }
}
