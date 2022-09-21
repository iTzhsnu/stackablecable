package dd.itzhsnu.stackablecable.blocks.conduits;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ConduitFluidTank extends FluidTank {
    public ConduitFluidTank(int capacity) {
        super(capacity, e -> true);
    }

    @Override
    public FluidTank readFromNBT(CompoundNBT nbt) {
        FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt);
        setFluid(fluid);
        this.capacity = nbt.getInt("Capacity");

        return this;
    }

    @Override
    public CompoundNBT writeToNBT(CompoundNBT nbt) {
        fluid.writeToNBT(nbt);
        nbt.putInt("Capacity", this.capacity);

        return nbt;
    }
}
