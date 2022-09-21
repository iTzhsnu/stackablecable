package dd.itzhsnu.stackablecable.blocks.generator;

import dd.itzhsnu.stackablecable.blocks.capacitor.CapacitorEnergyStorage;
import dd.itzhsnu.stackablecable.blocks.conduits.ConduitTileEntity;
import dd.itzhsnu.stackablecable.registrys.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GeneratorTileEntity extends TileEntity implements ITickableTileEntity {
    public GeneratorTileEntity(TileEntityType<?> tileEntityIn) {
        super(tileEntityIn);
    }

    public GeneratorTileEntity() {
        this(TileEntityInit.GENERATOR_TILE_ENTITY.get());
    }

    protected CapacitorEnergyStorage eStorage = new CapacitorEnergyStorage(100000, 0, 100000);
    private final LazyOptional<IEnergyStorage> eHandler = LazyOptional.of(() -> eStorage);
    protected ItemStackHandler iStorage = createIHandler();
    private final LazyOptional<IItemHandler> iHandler = LazyOptional.of(() -> iStorage);
    private int fuelTime;

    private ItemStackHandler createIHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                super.onContentsChanged(slot);
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return slot == 0 && AbstractFurnaceTileEntity.isFuel(stack);
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!isItemValid(slot, stack)) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }

            @Nonnull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                return super.extractItem(slot, amount, simulate);
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return eHandler.cast();
        } else if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return iHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        compound.put("inv", iStorage.serializeNBT());
        this.eStorage.writeToNBT(compound);
        compound.putInt("FuelTime", this.fuelTime);

        return compound;
    }

    @Override
    public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        iStorage.deserializeNBT(compound.getCompound("inv"));
        this.eStorage.readFromNBT(compound);
        this.fuelTime = compound.getInt("FuelTime");
    }

    private boolean canOutput(Direction direction) {
            BlockPos pos = getBlockPos().relative(direction);
         return ConduitTileEntity.getEnergyHandler(level, pos, direction.getOpposite()).map(h -> {
             IEnergyStorage storage = h.getKey();
             return eStorage.getEnergyStored() > 0 && storage.getMaxEnergyStored() > 0 && storage.getEnergyStored() != storage.getMaxEnergyStored() && storage.canReceive();
         }).orElse(false);
    }

    /**
     * Energy Output (Eject)
     * @param direction Energy Output(Eject) Direction
     * @return Eject the energy you have in the set direction.
     */
    private void outputEnergy(Direction direction) {
        BlockPos pos = getBlockPos().relative(direction);
        ConduitTileEntity.getEnergyHandler(level, pos, direction.getOpposite()).map(h -> {
            IEnergyStorage handler = h.getKey();
            if (eStorage.getEnergyStored() >= 1 && eStorage.canExtract() && handler.canReceive()) {
                int energy = eStorage.getEnergyStored() + handler.getEnergyStored(); //Total Energy
                int energy1 = eStorage.getEnergyStored(); //Storage Energy
                if (energy > handler.getMaxEnergyStored()) energy1 = handler.getMaxEnergyStored() - handler.getEnergyStored(); //Modify Energy
                int count = eStorage.extractEnergy(energy1, true); //Simulate Energy Extract
                if (count > 0) {
                    //Set Energy
                    int extract = handler.receiveEnergy(count, false); //Receive and Extract Amount
                    eStorage.extractEnergy(extract, false); //Extract
                }
            }

            return null;
        });
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            if (this.fuelTime >= 1) {
                int energy = this.eStorage.getEnergyStored() + 120;
                if (energy <= this.eStorage.getMaxEnergyStored()) {
                    eStorage.setEnergy(energy);
                }
                --this.fuelTime;
            } else {
                ItemStack itemStack = this.iStorage.getStackInSlot(0);
                if (AbstractFurnaceTileEntity.isFuel(itemStack)) {
                    this.fuelTime = ForgeHooks.getBurnTime(itemStack, null);
                    int count = this.iStorage.getStackInSlot(0).getCount() - 1;
                    this.iStorage.getStackInSlot(0).setCount(count);
                }
            }
            for (int dir = 1; dir <= 6; dir++) {
                if (dir == 1 && canOutput(Direction.NORTH)) {
                    outputEnergy(Direction.NORTH);
                    break;
                }
                if (dir == 2 && canOutput(Direction.SOUTH)) {
                    outputEnergy(Direction.SOUTH);
                    break;
                }
                if (dir == 3 && canOutput(Direction.EAST)) {
                    outputEnergy(Direction.EAST);
                    break;
                }
                if (dir == 4 && canOutput(Direction.WEST)) {
                    outputEnergy(Direction.WEST);
                    break;
                }
                if (dir == 5 && canOutput(Direction.UP)) {
                    outputEnergy(Direction.UP);
                    break;
                }
                if (dir == 6 && canOutput(Direction.DOWN)) {
                    outputEnergy(Direction.DOWN);
                    break;
                }
            }
        }
    }
}
