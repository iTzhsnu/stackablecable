package dd.itzhsnu.stackablecable.blocks.capacitor;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.EnergyStorage;

public class CapacitorEnergyStorage extends EnergyStorage {
    public CapacitorEnergyStorage(int capacity) {
        super(capacity, capacity, capacity, 0);
    }

    public CapacitorEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer, maxTransfer, 0);
    }

    public CapacitorEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract, 0);
    }

    public CapacitorEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return super.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return super.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return super.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return super.getMaxEnergyStored();
    }

    @Override
    public boolean canExtract() {
        return super.canExtract();
    }

    @Override
    public boolean canReceive() {
        return super.canReceive();
    }

    public void setEnergy(int energyIn) {
        if (energyIn < 0)
            energyIn = 0;
        if (energyIn > getMaxEnergyStored())
            energyIn = getEnergyStored();
        this.energy = energyIn;
    }

    public void readFromNBT(CompoundNBT compound) {
        setEnergy(compound.getInt("Energy"));
    }

    public void writeToNBT(CompoundNBT compound) {
        compound.putInt("Energy", this.energy);
    }
}
