package dd.itzhsnu.stackablecable.blocks.Crusher;

import dd.itzhsnu.stackablecable.blocks.capacitor.CapacitorEnergyStorage;
import dd.itzhsnu.stackablecable.registrys.RecipesInit;
import dd.itzhsnu.stackablecable.registrys.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.*;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class CrusherTileEntity extends TileEntity implements ITickableTileEntity {
    public CrusherTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public CrusherTileEntity() {
        this(TileEntityInit.CRUSHER_TILE_ENTITY.get());
    }

    protected CapacitorEnergyStorage eStorage = new CapacitorEnergyStorage(100000);
    private final LazyOptional<IEnergyStorage> eHandler = LazyOptional.of(() -> eStorage);
    protected ItemStackHandler iStorage = createHandler();
    private final LazyOptional<IItemHandler> iHandler = LazyOptional.of(() -> iStorage);
    private int crushingTime;

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(2) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                super.onContentsChanged(slot);
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return slot == 0;
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
                if (slot == 0) {
                    return ItemStack.EMPTY;
                } else {
                    return super.extractItem(slot, amount, simulate);
                }
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
        compound.putInt("CrushingTime", this.crushingTime);

        return compound;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        iStorage.deserializeNBT(nbt.getCompound("inv"));
        this.eStorage.readFromNBT(nbt);
        this.crushingTime = nbt.getInt("CrushingTime");

    }

    public void craft() {
        Inventory inv = new Inventory(iStorage.getSlots());
        for (int i = 0; i < iStorage.getSlots(); i++) {
            inv.setItem(i, iStorage.getStackInSlot(i));
        }

        Optional<CrushingRecipe> recipe = level.getRecipeManager().getRecipeFor(RecipesInit.CRUSHING_RECIPE, inv, level);

        recipe.ifPresent(iRecipe -> {
            if (this.crushingTime >= iRecipe.getTime()) {
                ItemStack output = iRecipe.getResultItem();
                //Get Item or Nothing
                if (output.getItem() == this.iStorage.getStackInSlot(1).getItem() || this.iStorage.getStackInSlot(1).isEmpty()) {
                    int count0 = this.iStorage.getStackInSlot(0).getCount() - 1;
                    int count1 = iRecipe.getResultItem().getCount() + this.iStorage.getStackInSlot(1).getCount();
                    //Get Count or Nothing
                    if (this.iStorage.getStackInSlot(1).isEmpty()) {
                        //Set Items (Nothing Ver)
                        this.iStorage.setStackInSlot(1, output);
                        this.iStorage.getStackInSlot(0).setCount(count0);
                    } else if (count1 <= this.iStorage.getStackInSlot(1).getMaxStackSize()) {
                        //Set Items
                        this.iStorage.getStackInSlot(0).setCount(count0);
                        this.iStorage.getStackInSlot(1).setCount(count1);
                    }
                    this.crushingTime = 0;
                }

            } else if (this.crushingTime <= iRecipe.getTime() && this.eStorage.getEnergyStored() >= 160) {
                this.crushingTime = this.crushingTime + 1;
                int energy = this.eStorage.getEnergyStored() - 160;
                this.eStorage.setEnergy(energy);
            }

            setChanged();
        });
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            craft();
        }
    }
}
