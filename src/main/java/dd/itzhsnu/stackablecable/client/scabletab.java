package dd.itzhsnu.stackablecable.client;

import dd.itzhsnu.stackablecable.registrys.ItemsInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

public class scabletab extends ItemGroup {

    public scabletab() {
        super("scable");
    }

    @Override
    public ItemStack makeIcon() {

        ItemStack itemStack = new ItemStack(ItemsInit.ITEM_CONDUIT_ITEM.get());
        return itemStack;
    }

    @Override
    public void fillItemList(NonNullList<ItemStack> stacks) {
        super.fillItemList(stacks);
        fluidConduits(stacks, 1);
        fluidConduits(stacks, 2);
        fluidConduits(stacks, 3);
        energyConduits(stacks, 1);
        energyConduits(stacks, 2);
        energyConduits(stacks, 3);
        energyConduits(stacks, 4);
        energyConduits(stacks, 5);
        wrench(stacks);
    }

    public void fluidConduits(NonNullList<ItemStack> stacks, int typeId) {
        ItemStack item = new ItemStack(ItemsInit.FLUID_CONDUIT_ITEM.get());
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("Type", typeId);
        nbt.putInt("CustomModelData", typeId);
        item.setTag(nbt);
        stacks.add(item);
    }

    public void energyConduits(NonNullList<ItemStack> stacks, int typeId) {
        ItemStack item = new ItemStack(ItemsInit.ENERGY_CONDUIT_ITEM.get());
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("Type", typeId);
        nbt.putInt("CustomModelData", typeId);
        item.setTag(nbt);
        stacks.add(item);
    }

    public void wrench(NonNullList<ItemStack> stacks) {
        ItemStack stack = new ItemStack(ItemsInit.WRENCH_ITEM.get());
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("Mode", 1);
        stack.setTag(nbt);
        stacks.add(stack);
    }
}