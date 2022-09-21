package dd.itzhsnu.stackablecable.blocks.conduits;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.EnergyStorage;

public class ConduitEnergyStorage extends EnergyStorage {
    public ConduitEnergyStorage(int capacity) {
        super(capacity, capacity, capacity, 0);
    }

    public void setEnergy(int energyIn) {
        if (energyIn < 0)
            energyIn = 0;
        if (energyIn > getMaxEnergyStored())
            energyIn = getEnergyStored();
        this.energy = energyIn;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        this.maxExtract = capacity;
        this.maxReceive = capacity;
    }

    public void readFromNBT(CompoundNBT compound) {
        setEnergy(compound.getInt("Energy"));
        this.capacity = compound.getInt("MaxEnergy");
        this.maxExtract = compound.getInt("MaxExtract");
        this.maxReceive = compound.getInt("MaxReceive");
    }

    public void writeToNBT(CompoundNBT compound) {
        compound.putInt("Energy", this.energy);
        compound.putInt("MaxEnergy", this.capacity);
        compound.putInt("MaxExtract", this.maxExtract);
        compound.putInt("MaxReceive", this.maxReceive);
    }
}
