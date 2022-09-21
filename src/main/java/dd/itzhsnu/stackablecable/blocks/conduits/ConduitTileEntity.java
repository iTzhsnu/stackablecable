package dd.itzhsnu.stackablecable.blocks.conduits;

import dd.itzhsnu.stackablecable.registrys.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class ConduitTileEntity extends TileEntity implements ITickableTileEntity {
    public ConduitTileEntity(TileEntityType<?> tileEntityIn) {
        super(tileEntityIn);
    }

    public ConduitTileEntity() {
        this(TileEntityInit.CONDUIT_TILE_ENTITY.get());
    }

    protected ConduitEnergyStorage eStorage = new ConduitEnergyStorage(0);
    private final LazyOptional<IEnergyStorage> eHandler = LazyOptional.of(() -> eStorage);
    protected ItemStackHandler iStorage = createIHandler();
    private final LazyOptional<IItemHandler> iHandler = LazyOptional.of(() -> iStorage);
    protected ConduitFluidTank tank = createFHandler();
    private final LazyOptional<IFluidHandler> fHandler = LazyOptional.of(() -> tank);
    private int[] ioList = new int[] {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3}; // North, South, East, West, UP, Down => 0 to 5 is Item, 6 to 11 is Fluid, 12 to 17 is Energy
    private Direction iNotOutput = null;
    private Direction fNotOutput = null;
    private Direction eNotOutput = null;
    private int getFType() {
        BlockState state = level.getBlockState(getBlockPos());
        switch (state.getValue(ConduitBlock.FLUID)) {
            case 1: return 1000;
            case 2: return 10000;
            case 3: return 100000;
            default: return 0;
        }
    }
    private int getEType() {
        BlockState state = level.getBlockState(getBlockPos());
        switch (state.getValue(ConduitBlock.ENERGY)) {
            case 1: return 1000;
            case 2: return 10000;
            case 3: return 50000;
            case 4: return 100000;
            case 5: return 2147483647;
            default: return 0;
        }
    }

    private ItemStackHandler createIHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                super.onContentsChanged(slot);
            }
        };
    }

    private ConduitFluidTank createFHandler() {
        return new ConduitFluidTank(0) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                super.onContentsChanged();
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.NORTH && this.ioList[0] >= 1 && ioList[0] != 4 && ioList[0] != 8 && ioList[0] < 12) return iHandler.cast();
            if (side == Direction.SOUTH && this.ioList[1] >= 1 && ioList[1] != 4 && ioList[1] != 8 && ioList[1] < 12) return iHandler.cast();
            if (side == Direction.EAST && this.ioList[2] >= 1 && ioList[2] != 4 && ioList[2] != 8 && ioList[2] < 12) return iHandler.cast();
            if (side == Direction.WEST && this.ioList[3] >= 1 && ioList[3] != 4 && ioList[3] != 8 && ioList[3] < 12) return iHandler.cast();
            if (side == Direction.UP && this.ioList[4] >= 1 && ioList[4] != 4 && ioList[4] != 8 && ioList[4] < 12) return iHandler.cast();
            if (side == Direction.DOWN && this.ioList[5] >= 1 && ioList[5] != 4 && ioList[5] != 8 && ioList[5] < 12) return iHandler.cast();
            if (side == null && getBlockState().getValue(ConduitBlock.ITEM)) return iHandler.cast();
        } else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (side == Direction.NORTH && this.ioList[6] >= 1 && ioList[6] != 4 && ioList[6] != 8 && ioList[6] < 12) return fHandler.cast();
            if (side == Direction.SOUTH && this.ioList[7] >= 1 && ioList[7] != 4 && ioList[7] != 8 && ioList[7] < 12) return fHandler.cast();
            if (side == Direction.EAST && this.ioList[8] >= 1 && ioList[8] != 4 && ioList[8] != 8 && ioList[8] < 12) return fHandler.cast();
            if (side == Direction.WEST && this.ioList[9] >= 1 && ioList[9] != 4 && ioList[9] != 8 && ioList[9] < 12) return fHandler.cast();
            if (side == Direction.UP && this.ioList[10] >= 1 && ioList[10] != 4 && ioList[10] != 8 && ioList[10] < 12) return fHandler.cast();
            if (side == Direction.DOWN && this.ioList[11] >= 1 && ioList[11] != 4 && ioList[11] != 8 && ioList[1] < 12) return fHandler.cast();
        } else if (cap == CapabilityEnergy.ENERGY) {
            if (side == Direction.NORTH && this.ioList[12] >= 1 && ioList[12] != 4 && ioList[12] != 8 && ioList[12] < 12) return eHandler.cast();
            if (side == Direction.SOUTH && this.ioList[13] >= 1 && ioList[13] != 4 && ioList[13] != 8 && ioList[13] < 12) return eHandler.cast();
            if (side == Direction.EAST && this.ioList[14] >= 1 && ioList[14] != 4 && ioList[14] != 8 && ioList[14] < 12) return eHandler.cast();
            if (side == Direction.WEST && this.ioList[15] >= 1 && ioList[15] != 4 && ioList[15] != 8 && ioList[15] < 12) return eHandler.cast();
            if (side == Direction.UP && this.ioList[16] >= 1 && ioList[16] != 4 && ioList[16] != 8 && ioList[16] < 12) return eHandler.cast();
            if (side == Direction.DOWN && this.ioList[17] >= 1 && ioList[17] != 4 && ioList[17] != 8 && ioList[17] < 12) return eHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        nbt.put("inv", iStorage.serializeNBT());
        this.tank.writeToNBT(nbt);
        this.eStorage.writeToNBT(nbt);
        nbt.putIntArray("IOSettings", this.ioList);

        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        iStorage.deserializeNBT(nbt.getCompound("inv"));
        this.tank.readFromNBT(nbt);
        this.eStorage.readFromNBT(nbt);
        this.ioList = nbt.getIntArray("IOSettings");

    }

    /**
     * Item, Fluid, Energy Input / Output System <p>
     * type id list </p><p>
     * 0. not connected, 1. input, 2. output, 3. i/o </p><p>
     * 12. not work, 0. always work, 4. need rs, 8. reverse rs </p><p>
     * 1. input (a), 2. output (a), 3. i/o (a), 4. not work, 5. input (rs), 6. output (rs), 7. i/o (rs), 8. not work, 9. input (rrs), 10. output (rrs), 11. i/o (rrs) </p>
     */
    private void io() {
        if (getBlockState().getValue(ConduitBlock.ITEM)) {
            //Item I/O
            Direction direction = null;
            int intDir = 0;
            FOR: for (int dir = 1; dir <= 6; dir++) {
                if (dir == 1 && ioList[0] > 0 && haveItemHandler(Direction.NORTH)) {
                    //NORTH
                    direction = Direction.NORTH;
                } else if (dir == 2 && ioList[1] > 0 && haveItemHandler(Direction.SOUTH)) {
                    //SOUTH
                    direction = Direction.SOUTH;
                    intDir = 1;
                } else if (dir == 3 && ioList[2] > 0 && haveItemHandler(Direction.EAST)) {
                    //EAST
                    direction = Direction.EAST;
                    intDir = 2;
                } else if (dir == 4 && ioList[3] > 0 && haveItemHandler(Direction.WEST)) {
                    //WEST
                    direction = Direction.WEST;
                    intDir = 3;
                } else if (dir == 5 && ioList[4] > 0 && haveItemHandler(Direction.UP)) {
                    //UP
                    direction = Direction.UP;
                    intDir = 4;
                } else if (dir == 6 && ioList[5] > 0 && haveItemHandler(Direction.DOWN)) {
                    //DOWN
                    direction = Direction.DOWN;
                    intDir = 5;
                }
                if (direction != null) {
                    switch (ioList[intDir]) {
                        case 1: if (canInputItem(direction)) {
                            inputItem(direction);
                            break FOR;
                        }
                            break;
                        case 2: if (canOutputItem(direction)) {
                            outputItem(direction);
                            break FOR;
                        }
                            break;
                        case 3: if (canInputItem(direction)) {
                            inputItem(direction);
                            break FOR;
                        }
                            if (canOutputItem(direction)) {
                                outputItem(direction);
                                break FOR;
                            }
                            break;
                        case 5: if (canInputItem(direction) && level.hasNeighborSignal(getBlockPos())) {
                            inputItem(direction);
                            break FOR;
                        }
                            break;
                        case 6: if (canOutputItem(direction) && level.hasNeighborSignal(getBlockPos())) {
                            outputItem(direction);
                            break FOR;
                        }
                            break;
                        case 7: if (canInputItem(direction) && level.hasNeighborSignal(getBlockPos())) {
                            inputItem(direction);
                            break FOR;
                        }
                            if (canOutputItem(direction) && level.hasNeighborSignal(getBlockPos())) {
                                outputItem(direction);
                                break FOR;
                            }
                            break;
                        case 9: if (canInputItem(direction) && !level.hasNeighborSignal(getBlockPos())) {
                            inputItem(direction);
                            break FOR;
                        }
                            break;
                        case 10: if (canOutputItem(direction) && !level.hasNeighborSignal(getBlockPos())) {
                            outputItem(direction);
                            break FOR;
                        }
                            break;
                        case 11: if (canInputItem(direction) && !level.hasNeighborSignal(getBlockPos())) {
                            inputItem(direction);
                            break FOR;
                        }
                            if (canOutputItem(direction) && !level.hasNeighborSignal(getBlockPos())) {
                                outputItem(direction);
                                break FOR;
                            }
                            break;
                    }
                }
            }
        }
        if (getBlockState().getValue(ConduitBlock.FLUID) >= 1) {
            //Fluid I/O
            Direction direction = null;
            int intDir = 0;
            FOR: for (int dir = 1; dir <= 6; dir++) {
                if (dir == 1 && ioList[6] > 0 && haveFluidHandler(Direction.NORTH)) {
                    //NORTH
                    direction = Direction.NORTH;
                    intDir = 6;
                } else if (dir == 2 && ioList[7] > 0 && haveFluidHandler(Direction.SOUTH)) {
                    //SOUTH
                    direction = Direction.SOUTH;
                    intDir = 7;
                } else if (dir == 3 && ioList[8] > 0 && haveFluidHandler(Direction.EAST)) {
                    //EAST
                    direction = Direction.EAST;
                    intDir = 8;
                } else if (dir == 4 && ioList[9] > 0 && haveFluidHandler(Direction.WEST)) {
                    //WEST
                    direction = Direction.WEST;
                    intDir = 9;
                } else if (dir == 5 && ioList[10] > 0 && haveFluidHandler(Direction.UP)) {
                    //UP
                    direction = Direction.UP;
                    intDir = 10;
                } else if (dir == 6 && ioList[11] > 0 && haveFluidHandler(Direction.DOWN)) {
                    //DOWN
                    direction = Direction.DOWN;
                    intDir = 11;
                }
                if (direction != null) {
                    switch (ioList[intDir]) {
                        case 1: if (canInputFluid(direction)) {
                            inputFluid(direction);
                            break FOR;
                        }
                            break;
                        case 2: if (canOutputFluid(direction)) {
                            outputFluid(direction);
                            break FOR;
                        }
                            break;
                        case 3: if (canInputFluid(direction)) {
                            inputFluid(direction);
                            break FOR;
                        }
                            if (canOutputFluid(direction)) {
                                outputFluid(direction);
                                break FOR;
                            }
                            break;
                        case 5: if (canInputFluid(direction) && level.hasNeighborSignal(getBlockPos())) {
                            inputFluid(direction);
                            break FOR;
                        }
                            break;
                        case 6: if (canOutputFluid(direction) && level.hasNeighborSignal(getBlockPos())) {
                            outputFluid(direction);
                            break FOR;
                        }
                            break;
                        case 7: if (canInputFluid(direction) && level.hasNeighborSignal(getBlockPos())) {
                            inputFluid(direction);
                            break FOR;
                        }
                            if (canOutputFluid(direction) && level.hasNeighborSignal(getBlockPos())) {
                                outputFluid(direction);
                                break FOR;
                            }
                            break;
                        case 9: if (canInputFluid(direction) && !level.hasNeighborSignal(getBlockPos())) {
                            inputFluid(direction);
                            break FOR;
                        }
                            break;
                        case 10: if (canOutputFluid(direction) && !level.hasNeighborSignal(getBlockPos())) {
                            outputFluid(direction);
                            break FOR;
                        }
                            break;
                        case 11: if (canInputFluid(direction) && !level.hasNeighborSignal(getBlockPos())) {
                            inputFluid(direction);
                            break FOR;
                        }
                            if (canOutputFluid(direction) && !level.hasNeighborSignal(getBlockPos())) {
                                outputFluid(direction);
                                break FOR;
                            }
                            break;
                    }
                }
            }
        }
        if (getBlockState().getValue(ConduitBlock.ENERGY) >= 1) {
            //Energy I/O
            for (int dir = 1; dir <= 6; dir++) {
                Direction direction = null;
                int intDir = 0;
                if (dir == 1 && ioList[12] > 0 && haveEnergyHandler(Direction.NORTH)) {
                    //NORTH
                    direction = Direction.NORTH;
                    intDir = 12;
                }
                if (dir == 2 && ioList[13] > 0 && haveEnergyHandler(Direction.SOUTH)) {
                    //SOUTH
                    direction = Direction.SOUTH;
                    intDir = 13;
                }
                if (dir == 3 && ioList[14] > 0 && haveEnergyHandler(Direction.EAST)) {
                    //EAST
                    direction = Direction.EAST;
                    intDir = 14;
                }
                if (dir == 4 && ioList[15] > 0 && haveEnergyHandler(Direction.WEST)) {
                    //WEST
                    direction = Direction.WEST;
                    intDir = 15;
                }
                if (dir == 5 && ioList[16] > 0 && haveEnergyHandler(Direction.UP)) {
                    //UP
                    direction = Direction.UP;
                    intDir = 16;
                }
                if (dir == 6 && ioList[17] > 0 && haveEnergyHandler(Direction.DOWN)) {
                    //DOWN
                    direction = Direction.DOWN;
                    intDir = 17;
                }
                if (direction != null) {
                    switch (ioList[intDir]) {
                        case 1: if (canInputEnergy(direction)) {
                            inputEnergy(direction);
                        }
                        break;
                        case 2: if (canOutputEnergy(direction)) {
                            outputEnergy(direction);
                        }
                        break;
                        case 3: if (canInputEnergy(direction)) {
                            inputEnergy(direction);
                        } else if (canOutputEnergy(direction)) {
                            outputEnergy(direction);
                        }
                        break;
                        case 5: if (canInputEnergy(direction) && level.hasNeighborSignal(getBlockPos())) {
                            inputEnergy(direction);
                        }
                        break;
                        case 6: if (canOutputEnergy(direction) && level.hasNeighborSignal(getBlockPos())) {
                            outputEnergy(direction);
                        }
                        break;
                        case 7: if (canInputEnergy(direction) && level.hasNeighborSignal(getBlockPos())) {
                            inputEnergy(direction);
                        } else if (canOutputEnergy(direction) && level.hasNeighborSignal(getBlockPos())) {
                            outputEnergy(direction);
                        }
                        break;
                        case 9: if (canInputEnergy(direction) && !level.hasNeighborSignal(getBlockPos())) {
                            inputEnergy(direction);
                        }
                        break;
                        case 10: if (canOutputEnergy(direction) && !level.hasNeighborSignal(getBlockPos())) {
                            outputEnergy(direction);
                        }
                        break;
                        case 11: if (canInputEnergy(direction) && !level.hasNeighborSignal(getBlockPos())) {
                            inputEnergy(direction);
                        } else if (canOutputEnergy(direction) && !level.hasNeighborSignal(getBlockPos())) {
                            outputEnergy(direction);
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * get Can Input Item
     * @param direction Input Direction
     * @return can Input = true
     */
    private boolean canInputItem(Direction direction) {
            BlockPos pos = getBlockPos().relative(direction);
            return VanillaInventoryCodeHooks.getItemHandler(level, pos.getX(), pos.getY(), pos.getZ(), direction.getOpposite()).map(h -> {
                IItemHandler handler = h.getKey();
                for (int slot = 0; slot < handler.getSlots(); slot++) {
                    ItemStack stack1 = handler.getStackInSlot(slot);
                    ItemStack checkStack = stack1.getStack().copy();
                    checkStack.setCount(1);
                    if (!stack1.isEmpty() && iStorage.insertItem(0, checkStack, true).isEmpty() && !handler.extractItem(slot, 1, true).isEmpty()) {
                        return true;
                    }
                }
                return false;
            }).orElse(false);
    }

    /**
     * get Can Input Fluid
     * @param direction Input Direction
     * @return can Input = true
     */
    private boolean canInputFluid(Direction direction) {
            BlockPos pos = getBlockPos().relative(direction);
            return FluidUtil.getFluidHandler(level, pos, direction.getOpposite()).map(handler -> {
                for (int slot = 0; slot < handler.getTanks(); slot++) {
                    if (!handler.getFluidInTank(slot).isEmpty()) {
                        FluidStack stack = handler.getFluidInTank(slot);
                        FluidStack checkStack = stack.copy();
                        checkStack.setAmount(1);
                        if (tank.fill(checkStack, IFluidHandler.FluidAction.SIMULATE) == 1 && !handler.drain(checkStack, IFluidHandler.FluidAction.SIMULATE).isEmpty()) {
                            return true;
                        }
                    }
                }
                return false;
            }).orElse(false);

    }

    /**
     * get Can Input Energy
     * @param direction Input Direction
     * @return can Input = true
     */
    private boolean canInputEnergy(Direction direction) {
            BlockPos pos = getBlockPos().relative(direction);
            return ConduitTileEntity.getEnergyHandler(level, pos, direction.getOpposite()).map(h -> {
                IEnergyStorage storage = h.getKey();
                return eStorage.getEnergyStored() != eStorage.getMaxEnergyStored() && storage.getEnergyStored() > 0 && storage.canExtract();
            }).orElse(false);

    }

    /**
     * get Can Output Item
     * @param direction Output Direction
     * @return can Output = true
     */
    private boolean canOutputItem(Direction direction) {
        if (direction != iNotOutput || iNotOutput == null) {
            BlockPos pos = getBlockPos().relative(direction);
            return VanillaInventoryCodeHooks.getItemHandler(level, pos.getX(), pos.getY(), pos.getZ(), direction.getOpposite()).map(h -> {
                IItemHandler handler = h.getKey();
                for (int slot = 0; slot < handler.getSlots(); slot++) {
                    ItemStack stack = iStorage.getStackInSlot(0);
                    ItemStack checkStack = stack.copy();
                    checkStack.setCount(1);
                    if (!stack.isEmpty() && handler.insertItem(slot, checkStack, true).isEmpty() && !iStorage.extractItem(0, 1, true).isEmpty()) {
                        return true;
                    }
                }
                return false;
            }).orElse(false);
        } else {
            return false;
        }
    }

    /**
     * get Can Output Fluid
     * @param direction Output Direction
     * @return can Output = true
     */
    private boolean canOutputFluid(Direction direction) {
        if (direction != fNotOutput || fNotOutput == null) {
            BlockPos pos = getBlockPos().relative(direction);
            return FluidUtil.getFluidHandler(level, pos, direction.getOpposite()).map(handler -> {
                for (int slot = 0; slot < handler.getTanks(); slot++) {
                    if (!tank.getFluid().isEmpty()) {
                        FluidStack stack = tank.getFluid();
                        FluidStack checkStack = stack.copy();
                        checkStack.setAmount(1);
                        if (!stack.isEmpty() && handler.fill(checkStack, IFluidHandler.FluidAction.SIMULATE) == 1 && !tank.drain(checkStack, IFluidHandler.FluidAction.SIMULATE).isEmpty()) {
                            return true;
                        }
                    }
                }
                return false;
            }).orElse(false);
        } else {
            return false;
        }
    }

    /**
     * get Can Output Energy
     * @param direction Output Direction
     * @return can Output = true
     */
    private boolean canOutputEnergy(Direction direction) {
        if (direction != eNotOutput || eNotOutput == null) {
            BlockPos pos = getBlockPos().relative(direction);
            return ConduitTileEntity.getEnergyHandler(level, pos, direction.getOpposite()).map(h -> {
                IEnergyStorage storage = h.getKey();
                return eStorage.getEnergyStored() > 0 && storage.getMaxEnergyStored() > 0 && storage.getEnergyStored() != storage.getMaxEnergyStored() && storage.canReceive();
            }).orElse(false);
        } else {
            return false;
        }
    }

    /**
     * get have ItemHandler
     * @param direction Handler Detection Direction
     * @return have Handler = true
     */
    private boolean haveItemHandler(Direction direction) {
        BlockPos pos = getBlockPos().relative(direction);
        return VanillaInventoryCodeHooks.getItemHandler(level, pos.getX(), pos.getY(), pos.getZ(), direction.getOpposite()).isPresent();
    }

    /**
     * get have Fluid Handler
     * @param direction Handler Detection Direction
     * @return have Handler = true
     */
    private boolean haveFluidHandler(Direction direction) {
        BlockPos pos = getBlockPos().relative(direction);
        return FluidUtil.getFluidHandler(level, pos, direction.getOpposite()).map(h -> true).orElse(false);
    }

    /**
     * get have Energy Handler
     * @param direction Handler Detection Direction
     * @return have Handler = true
     */
    private boolean haveEnergyHandler(Direction direction) {
        BlockPos pos = getBlockPos().relative(direction);
        return getEnergyHandler(level, pos, direction.getOpposite()).isPresent();
    }

    /**
     * Item Input (Collect)
     * @param direction Item Input(Collect) Direction
     * @return If the item in the set direction can be collected, it will be collected.
     */
    private void inputItem(Direction direction) {
        BlockPos pos = getBlockPos().relative(direction);
        ItemStack stack = iStorage.getStackInSlot(0);
        VanillaInventoryCodeHooks.getItemHandler(level, pos.getX(), pos.getY(), pos.getZ(), direction.getOpposite()).map(h -> {
            IItemHandler handler = h.getKey();
            for (int slot = 0; slot < handler.getSlots(); slot++) {
                ItemStack stack1 = handler.getStackInSlot(slot);
                ItemStack checkStack = stack1.getStack().copy();
                checkStack.setCount(1);
                if (iStorage.insertItem(0, checkStack, true).isEmpty() && !handler.extractItem(slot, 1, true).isEmpty()) {
                    if (!stack1.isEmpty()) {
                        int totalCount = stack.getCount() + stack1.getCount();
                        if (totalCount > iStorage.getSlotLimit(0)) {
                            //slot limit over
                            ItemStack stack2 = new ItemStack(handler.getStackInSlot(slot).getItem(), iStorage.getSlotLimit(0));
                            stack2.setTag(stack1.getTag());
                            int count1 = totalCount - iStorage.getSlotLimit(0);
                            if (stack.isEmpty()) {
                                iStorage.insertItem(0, stack2, false);
                                stack1.setCount(count1);
                                this.iNotOutput = direction;
                                break;
                            } else if (!stack.isEmpty() && ItemHandlerHelper.canItemStacksStack(stack, stack1)) {
                                iStorage.insertItem(0, stack2, false);
                                stack1.setCount(count1);
                                this.iNotOutput = direction;
                            }
                        } else {
                            //not slot limit over
                            ItemStack stack2 = new ItemStack(stack1.getItem(), stack1.getCount());
                            stack2.setTag(stack1.getTag());
                            if (stack.isEmpty()) {
                                iStorage.insertItem(0, stack2, false);
                                stack1.setCount(0);
                                this.iNotOutput = direction;
                                break;
                            } else if (!stack.isEmpty() && ItemHandlerHelper.canItemStacksStack(stack, stack1)) {
                                iStorage.insertItem(0, stack2, false);
                                stack1.setCount(0);
                                this.iNotOutput = direction;
                            }
                        }
                    }
                }
            }
            return null;
        });
    }

    /**
     * Item Output (Eject)
     * @param direction Item Output(Eject) Direction
     * @return Eject the items you have in the set direction.
     */
    private void outputItem(Direction direction) {
        BlockPos pos = getBlockPos().relative(direction);
        ItemStack stack = iStorage.getStackInSlot(0);
        VanillaInventoryCodeHooks.getItemHandler(level, pos.getX(), pos.getY(), pos.getZ(), direction.getOpposite()).map(h -> {
            IItemHandler handler = h.getKey();
            for (int slot = 0; slot < handler.getSlots(); slot++) {
                ItemStack stackInSlot = handler.getStackInSlot(slot);
                ItemStack checkStack = stack.getStack().copy();
                checkStack.setCount(1);
                if (handler.insertItem(slot, checkStack, true).isEmpty() && !iStorage.extractItem(0, 1, true).isEmpty()) {
                    if (!stack.isEmpty()) {
                        int totalCount = stack.getCount() + stackInSlot.getCount();
                        if (totalCount > handler.getSlotLimit(slot)) {
                            //slot limit over
                            ItemStack stack1 = new ItemStack(stack.getItem(), handler.getSlotLimit(slot));
                            stack1.setTag(stack.getTag());
                            int count1 = totalCount - handler.getSlotLimit(slot);
                            if (stackInSlot.isEmpty()) {
                                handler.insertItem(slot, stack1, false);
                                stack.setCount(count1);
                                break;
                            } else if (!stackInSlot.isEmpty() && ItemHandlerHelper.canItemStacksStack(stack, stackInSlot)) {
                                handler.insertItem(slot, stack1, false);
                                stack.setCount(count1);
                            }
                        } else {
                            //not slot limit over
                            ItemStack stack1 = new ItemStack(stack.getItem(), stack.getCount());
                            stack1.setTag(stack.getTag());
                            if (stackInSlot.isEmpty()) {
                                handler.insertItem(slot, stack1, false);
                                stack.setCount(0);
                                break;
                            } else if (!stackInSlot.isEmpty() && ItemHandlerHelper.canItemStacksStack(stack, stackInSlot)) {
                                handler.insertItem(slot, stack1, false);
                                stack.setCount(0);
                            }
                        }
                    }
                }
            }
            return null;
        });
    }

    /**
     * Fluid Input (Collect)
     * @param direction Fluid Input(Collect) Direction
     * @return If the fluid in the set direction can be collected, it will be collected.
     */
    private void inputFluid(Direction direction) {
        BlockPos pos = getBlockPos().relative(direction);
        FluidUtil.getFluidHandler(level, pos, direction.getOpposite()).map(handler -> {
            for (int slot = 0; slot < handler.getTanks(); slot++) {
                FluidStack stack = tank.getFluid();
                FluidStack slotInStack = handler.getFluidInTank(slot);
                FluidStack checkStack1 = slotInStack.copy();
                if (!checkStack1.isEmpty()) checkStack1.setAmount(1);
                if (!slotInStack.isEmpty() && tank.fill(checkStack1, IFluidHandler.FluidAction.SIMULATE) == 1 && !handler.drain(checkStack1, IFluidHandler.FluidAction.SIMULATE).isEmpty()) {
                    int count = stack.getAmount() + slotInStack.getAmount();
                    if (count > tank.getCapacity()) {
                        //tank capacity limit over
                        FluidStack stack1 = new FluidStack(slotInStack.getFluid(), tank.getCapacity());
                        int count1 = tank.getCapacity() - tank.getFluidAmount();
                        if (stack.isEmpty()) {
                            tank.fill(stack1, IFluidHandler.FluidAction.EXECUTE);
                            handler.drain(count1, IFluidHandler.FluidAction.EXECUTE);
                            this.fNotOutput = direction;
                            break;
                        } else if (!stack.isEmpty() && stack.getFluid() == slotInStack.getFluid()) {
                            tank.fill(stack1, IFluidHandler.FluidAction.EXECUTE);
                            handler.drain(count1, IFluidHandler.FluidAction.EXECUTE);
                            this.fNotOutput = direction;
                        }
                    } else {
                        //tank capacity limit not over
                        FluidStack stack1 = new FluidStack(slotInStack.getFluid(), slotInStack.getAmount());
                        if (stack.isEmpty()) {
                            tank.fill(stack1, IFluidHandler.FluidAction.EXECUTE);
                            handler.drain(handler.getFluidInTank(slot), IFluidHandler.FluidAction.EXECUTE);
                            this.fNotOutput = direction;
                            break;
                        } else if (!stack.isEmpty() && stack.getFluid() == slotInStack.getFluid()) {
                            tank.fill(stack1, IFluidHandler.FluidAction.EXECUTE);
                            handler.drain(handler.getFluidInTank(slot), IFluidHandler.FluidAction.EXECUTE);
                            slotInStack.setAmount(0);
                            this.fNotOutput = direction;
                        }
                    }
                }
            }
            return true;
        });
    }

    /**
     * Fluid Output (Eject)
     * @param direction Fluid Output(Eject) Direction
     * @return Eject the fluid you have in the set direction.
     */
    private void outputFluid(Direction direction) {
        BlockPos pos = getBlockPos().relative(direction);
        FluidUtil.getFluidHandler(level, pos, direction.getOpposite()).map(handler -> {
            for (int slot = 0; slot < handler.getTanks(); slot++) {
                FluidStack stack = tank.getFluid();
                FluidStack slotInStack = handler.getFluidInTank(slot);
                FluidStack checkStack = stack.copy();
                if (!checkStack.isEmpty()) checkStack.setAmount(1);
                if (!stack.isEmpty() && handler.fill(checkStack, IFluidHandler.FluidAction.SIMULATE) == 1 && !tank.drain(checkStack, IFluidHandler.FluidAction.SIMULATE).isEmpty()) {
                    int count = stack.getAmount() + slotInStack.getAmount();
                    if (count > handler.getTankCapacity(slot)) {
                        //tank capacity limit over
                        FluidStack stack1 = new FluidStack(stack.getFluid(), slotInStack.getAmount());
                        int count1 = handler.getTankCapacity(slot) - slotInStack.getAmount();
                        if (slotInStack.isEmpty()) {
                            handler.fill(stack1, IFluidHandler.FluidAction.EXECUTE);
                            tank.drain(count1, IFluidHandler.FluidAction.EXECUTE);
                            break;
                        } else if (!slotInStack.isEmpty() && stack.getFluid() == slotInStack.getFluid()) {
                            handler.fill(stack1, IFluidHandler.FluidAction.EXECUTE);
                            tank.drain(count1, IFluidHandler.FluidAction.EXECUTE);
                        }
                    } else {
                        //tank capacity limit not over
                        FluidStack stack1 = new FluidStack(stack.getFluid(), stack.getAmount());
                        if (slotInStack.isEmpty()) {
                            handler.fill(stack1, IFluidHandler.FluidAction.EXECUTE);
                            tank.drain(tank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE);
                            break;
                        } else if (!slotInStack.isEmpty() && stack.getFluid() == slotInStack.getFluid()) {
                            handler.fill(stack1, IFluidHandler.FluidAction.EXECUTE);
                            tank.drain(tank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE);
                        }
                    }
                }
            }
            return true;
        });
    }

    /**
     * Energy Input (Collect)
     * @param direction Energy Input(Collect) Direction
     * @return If the energy in the set direction can be collected, it will be collected.
     */
    private void inputEnergy(Direction direction) {
        BlockPos pos = getBlockPos().relative(direction);
        getEnergyHandler(level, pos, direction.getOpposite()).map(h -> {
            IEnergyStorage handler = h.getKey();
            if (handler.getEnergyStored() >= 1 && handler.canExtract() && eStorage.canReceive()) {
                int energy = eStorage.getEnergyStored() + handler.getEnergyStored(); //Total Energy
                int energy1 = handler.getEnergyStored(); //Handler Energy
                if (energy > eStorage.getMaxEnergyStored()) energy1 = eStorage.getMaxEnergyStored() - eStorage.getEnergyStored(); //Modify Energy
                int count = handler.extractEnergy(energy1, true); //Simulate Energy Extract
                if (count > 0) {
                    //Set Energy
                    int extract = eStorage.receiveEnergy(count, false); //Receive and Extract Amount
                    handler.extractEnergy(extract, false); //Extract
                    this.eNotOutput = direction;
                }
            }

            return null;
        });
    }

    /**
     * Energy Output (Eject)
     * @param direction Energy Output(Eject) Direction
     * @return Eject the energy you have in the set direction.
     */
    private void outputEnergy(Direction direction) {
        BlockPos pos = getBlockPos().relative(direction);
        getEnergyHandler(level, pos, direction.getOpposite()).map(h -> {
            IEnergyStorage handler = h.getKey();
            if (eStorage.getEnergyStored() >= 1 && eStorage.canExtract() && handler.canReceive()) {
                int energy = eStorage.getEnergyStored() + handler.getEnergyStored(); //Total Energy
                int energy1 = eStorage.getEnergyStored(); //Storage Energy
                if (energy > handler.getMaxEnergyStored()) energy1 = handler.getMaxEnergyStored() - handler.getEnergyStored(); //Not Over Energy Count
                int count = eStorage.extractEnergy(energy1, true); //Extract Energy Count
                if (count > 0) {
                    //Set Energy
                    int extract = handler.receiveEnergy(count, false); //Receive
                    eStorage.extractEnergy(extract, false); //Extract
                }
            }
            return null;
        });
    }

    public static Optional<Pair<IEnergyStorage, Object>> getEnergyHandler(World world, BlockPos pos, @Nullable Direction side) {
        BlockState state = world.getBlockState(pos);

        if (state.hasTileEntity()) {
            TileEntity te = world.getBlockEntity(pos);
            if (te != null) {
                return te.getCapability(CapabilityEnergy.ENERGY, side).map(cap -> ImmutablePair.of(cap, te));
            }
        }

        return Optional.empty();
    }

    private boolean canConnect(Direction direction) {
        int type1 = getDirectionFromIO(direction, 1);
        int type2 = getDirectionFromIO(direction, 2);
        int type3 = getDirectionFromIO(direction, 3);
        if (ioList[type1] >= 1 && ioList[type1] != 4 && ioList[type1] != 8 && !(ioList[type1] >= 12) && haveItemHandler(direction) && getBlockState().getValue(ConduitBlock.ITEM)) {
            return true;
        } else if (ioList[type2] >= 1 && ioList[type2] != 4 && ioList[type2] != 8 && !(ioList[type2] >= 12) && haveFluidHandler(direction) && getBlockState().getValue(ConduitBlock.FLUID) > 0) {
            return true;
        } else return ioList[type3] >= 1 && ioList[type3] != 4 && ioList[type3] != 8 && !(ioList[type3] >= 12) && haveEnergyHandler(direction) && getBlockState().getValue(ConduitBlock.ENERGY) > 0;
    }

    private void autoSetConnect() {
        BlockState state = getBlockState();
        //TRUE
        if (!state.getValue(ConduitBlock.NORTH) && canConnect(Direction.NORTH)) level.setBlock(getBlockPos(), state.setValue(ConduitBlock.NORTH, true), 3); //NORTH
        if (!state.getValue(ConduitBlock.SOUTH) && canConnect(Direction.SOUTH)) level.setBlock(getBlockPos(), state.setValue(ConduitBlock.SOUTH, true), 3); //SOUTH
        if (!state.getValue(ConduitBlock.EAST) && canConnect(Direction.EAST)) level.setBlock(getBlockPos(), state.setValue(ConduitBlock.EAST, true), 3); //EAST
        if (!state.getValue(ConduitBlock.WEST) && canConnect(Direction.WEST)) level.setBlock(getBlockPos(), state.setValue(ConduitBlock.WEST, true), 3); //WEST
        if (!state.getValue(ConduitBlock.UP) && canConnect(Direction.UP)) level.setBlock(getBlockPos(), state.setValue(ConduitBlock.UP, true), 3); //UP
        if (!state.getValue(ConduitBlock.DOWN) && canConnect(Direction.DOWN)) level.setBlock(getBlockPos(), state.setValue(ConduitBlock.DOWN, true), 3); //DOWN
        //FALSE
        if (state.getValue(ConduitBlock.NORTH) && !canConnect(Direction.NORTH)) level.setBlock(getBlockPos(), state.setValue(ConduitBlock.NORTH, false), 3); //NORTH
        if (state.getValue(ConduitBlock.SOUTH) && !canConnect(Direction.SOUTH)) level.setBlock(getBlockPos(), state.setValue(ConduitBlock.SOUTH, false), 3); //SOUTH
        if (state.getValue(ConduitBlock.EAST) && !canConnect(Direction.EAST)) level.setBlock(getBlockPos(), state.setValue(ConduitBlock.EAST, false), 3); //EAST
        if (state.getValue(ConduitBlock.WEST) && !canConnect(Direction.WEST)) level.setBlock(getBlockPos(), state.setValue(ConduitBlock.WEST, false), 3); //WEST
        if (state.getValue(ConduitBlock.UP) && !canConnect(Direction.UP)) level.setBlock(getBlockPos(), state.setValue(ConduitBlock.UP, false), 3); //UP
        if (state.getValue(ConduitBlock.DOWN) && !canConnect(Direction.DOWN)) level.setBlock(getBlockPos(), state.setValue(ConduitBlock.DOWN, false), 3); //DOWN
    }
    //TODO: create GUI
    public void modifyIO(int pos, int io) {
        this.ioList[pos] = io;
    }

    public int getIO(int directionInt) {
        return this.ioList[directionInt];
    }

    public int[] getIoList() {
        return this.ioList;
    }

    public void setIoList(int[] ints) {
        this.ioList = ints;
    }

    public static int getDirectionFromIO(Direction direction, int type) {
        //ITEM IO
        if (direction == Direction.NORTH && type == 1) return 0;
        if (direction == Direction.SOUTH && type == 1) return 1;
        if (direction == Direction.EAST && type == 1) return 2;
        if (direction == Direction.WEST && type == 1) return 3;
        if (direction == Direction.UP && type == 1) return 4;
        if (direction == Direction.DOWN && type == 1) return 5;
        //FLUID IO
        if (direction == Direction.NORTH && type == 2) return 6;
        if (direction == Direction.SOUTH && type == 2) return 7;
        if (direction == Direction.EAST && type == 2) return 8;
        if (direction == Direction.WEST && type == 2) return 9;
        if (direction == Direction.UP && type == 2) return 10;
        if (direction == Direction.DOWN && type == 2) return 11;
        //ENERGY IO
        if (direction == Direction.NORTH && type == 3) return 12;
        if (direction == Direction.SOUTH && type == 3) return 13;
        if (direction == Direction.EAST && type == 3) return 14;
        if (direction == Direction.WEST && type == 3) return 15;
        if (direction == Direction.UP && type == 3) return 16;
        if (direction == Direction.DOWN && type == 3) return 17;
        return 0;
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            this.tank.setCapacity(getFType());
            this.eStorage.setCapacity(getEType());
            this.autoSetConnect();
            io();
        }
    }
}
