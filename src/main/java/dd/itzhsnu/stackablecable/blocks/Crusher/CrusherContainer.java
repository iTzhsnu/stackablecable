package dd.itzhsnu.stackablecable.blocks.Crusher;

import dd.itzhsnu.stackablecable.registrys.BlocksInit;
import dd.itzhsnu.stackablecable.registrys.ContainersInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CrusherContainer extends Container {

    public final TileEntity te;
    public final IWorldPosCallable stillValidCallable;

    public CrusherContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInv, PlayerEntity player) {
        super(ContainersInit.CRUSHER_CONTAINER.get(), windowId);
        this.te = world.getBlockEntity(pos);
        this.stillValidCallable = IWorldPosCallable.create(te.getLevel(), te.getBlockPos());


        // Tile Entity
        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            addSlot(new SlotItemHandler(h, 0, 80, 17));
            addSlot(new SlotItemHandler(h, 1, 80, 53));
        });

        // Main Player Inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Player Hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));
        }
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(stillValidCallable, playerIn, BlocksInit.CRUSHER_BLOCK.get());
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            stack = stack1.copy();
            if (index < 2 && !this.moveItemStackTo(stack1, 2, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
            if (!this.moveItemStackTo(stack1, 0, 2, false)) {
                return ItemStack.EMPTY;
            }

            if (stack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            }
            else {
                slot.setChanged();
            }
        }
        return stack;
    }

}
