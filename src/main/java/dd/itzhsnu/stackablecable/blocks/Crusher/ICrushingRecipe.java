package dd.itzhsnu.stackablecable.blocks.Crusher;

import dd.itzhsnu.stackablecable.scable;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public interface ICrushingRecipe extends IRecipe<IInventory> {

    ResourceLocation TYPE_ID = new ResourceLocation(scable.MOD_ID, "crushing");

    @Override
    default IRecipeType<?> getType() {
        return Registry.RECIPE_TYPE.getOptional(TYPE_ID).get();
    }

    @Override
    default boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    default boolean isSpecial() {
        return true;
    }
}
