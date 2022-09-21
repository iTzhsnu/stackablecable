package dd.itzhsnu.stackablecable.items.all_purpose_tool;

import com.google.common.collect.Lists;
import dd.itzhsnu.stackablecable.registrys.RecipesInit;
import dd.itzhsnu.stackablecable.registrys.TagsInit;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;

public class AP_Tool_Combine_Recipe extends SpecialRecipe {
    public AP_Tool_Combine_Recipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        List<ItemStack> list = Lists.newArrayList();
        for (int i = 0; inv.getContainerSize() > i; ++i) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                list.add(stack);
            }
        }

        if (list.size() == 2) {
            return TagsInit.AP_TOOLS.contains(list.get(0).getItem()) && TagsInit.AP_TOOLS.contains(list.get(1).getItem());
        } else {
            return false;
        }
    }

    @Override
    public ItemStack assemble(CraftingInventory inv) {
        List<ItemStack> list = Lists.newArrayList();
        for (int i = 0; inv.getContainerSize() > i; ++i) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) list.add(stack);
        }
        if (list.size() == 2 && TagsInit.AP_TOOLS.contains(list.get(0).getItem()) && TagsInit.AP_TOOLS.contains(list.get(1).getItem())) {
            ItemStack output = list.get(0).copy();
            CompoundNBT nbt = list.get(0).getTag();
            CompoundNBT inputNBT = list.get(1).getTag();
            CompoundNBT outputNBT = list.get(0).getTag().copy();
            List<Long> mining_Speeds = Lists.newArrayList();
            List<Integer> durability_List = Lists.newArrayList();
            List<Long> attack_Damages = Lists.newArrayList();
            List<Long> attack_Speeds = Lists.newArrayList();
            List<Integer> old_Lists = Lists.newArrayList();
            List<Integer> mode_Lists = Lists.newArrayList();
            List<Integer> old_item_Id = Lists.newArrayList();
            int durability = 0;
            //Mining Speed List Update
            for (int i = 0; nbt.getLongArray("Mining_Speed_List").length > i; ++i) mining_Speeds.add(nbt.getLongArray("Mining_Speed_List")[i]);
            for (int i = 0; inputNBT.getLongArray("Mining_Speed_List").length > i; ++i) mining_Speeds.add(inputNBT.getLongArray("Mining_Speed_List")[i]);
            outputNBT.putLongArray("Mining_Speed_List", mining_Speeds);
            //Durability List Update and Durability
            for (int i = 0; nbt.getIntArray("Durability_List").length > i; ++i) durability_List.add(nbt.getIntArray("Durability_List")[i]);
            for (int i = 0; inputNBT.getIntArray("Durability_List").length > i; ++i) durability_List.add(inputNBT.getIntArray("Durability_List")[i]);
            outputNBT.putIntArray("Durability_List", durability_List);
            for (int i = 0; outputNBT.getIntArray("Durability_List").length > i; ++i) durability = durability + outputNBT.getIntArray("Durability_List")[i];
            outputNBT.putInt("Durability", durability / outputNBT.getIntArray("Durability_List").length);
            //Attack Damage List Update
            for (int i = 0; nbt.getLongArray("Attack_Damage_List").length > i; ++i) attack_Damages.add(nbt.getLongArray("Attack_Damage_List")[i]);
            for (int i = 0; inputNBT.getLongArray("Attack_Damage_List").length > i; ++i) attack_Damages.add(inputNBT.getLongArray("Attack_Damage_List")[i]);
            outputNBT.putLongArray("Attack_Damage_List", attack_Damages);
            //Attack Speed List Update
            for (int i = 0; nbt.getLongArray("Attack_Speed_List").length > i; ++i) attack_Speeds.add(nbt.getLongArray("Attack_Speed_List")[i]);
            for (int i = 0; inputNBT.getLongArray("Attack_Speed_List").length > i; ++i) attack_Speeds.add(inputNBT.getLongArray("Attack_Speed_List")[i]);
            outputNBT.putLongArray("Attack_Speed_List", attack_Speeds);
            //Old Item Names Update
            for (int i = 0; nbt.getIntArray("Old_List").length > i; ++i) old_Lists.add(nbt.getIntArray("Old_List")[i]);
            for (int i = 0; inputNBT.getIntArray("Old_List").length > i; ++i) old_Lists.add(inputNBT.getIntArray("Old_List")[i]);
            outputNBT.putIntArray("Old_List", old_Lists);
            //Mode List Update
            for (int i = 0; nbt.getIntArray("Mode_List").length > i; ++i) mode_Lists.add(nbt.getIntArray("Mode_List")[i]);
            for (int i = 0; inputNBT.getIntArray("Mode_List").length > i; ++i) mode_Lists.add(inputNBT.getIntArray("Mode_List")[i]);
            outputNBT.putIntArray("Mode_List", mode_Lists);
            //Old Item (Custom) ID
            for (int i = 0; nbt.getIntArray("Custom_Tool_ID").length > i; ++i) old_item_Id.add(nbt.getIntArray("Custom_Tool_ID")[i]);
            for (int i = 0; inputNBT.getIntArray("Custom_Tool_ID").length > i; ++i) old_item_Id.add(inputNBT.getIntArray("Custom_Tool_ID")[i]);
            outputNBT.putIntArray("Custom_Tool_ID", old_item_Id);

            output.setTag(outputNBT);
            return output;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipesInit.AP_TOOL_COMBINE_RECIPE;
    }
}
