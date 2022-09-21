package dd.itzhsnu.stackablecable.blocks.capacitor;

import dd.itzhsnu.stackablecable.registrys.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapacitorTileEntity extends TileEntity {

    public CapacitorTileEntity(TileEntityType<?> tileEntityIn) {
        super(tileEntityIn);
    }

    public CapacitorTileEntity() {
        this(TileEntityInit.CAPACITOR_TILE_ENTITY.get());
    }

    protected CapacitorEnergyStorage storage = new CapacitorEnergyStorage(10000000);
    private final LazyOptional<IEnergyStorage> handler = LazyOptional.of(() -> storage);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        this.storage.writeToNBT(compound);

        return compound;
    }

    @Override
    public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        this.storage.readFromNBT(compound);
    }
}
