package dd.itzhsnu.stackablecable.blocks.Crusher;

import com.google.gson.JsonObject;
import dd.itzhsnu.stackablecable.registrys.BlocksInit;
import dd.itzhsnu.stackablecable.registrys.RecipesInit;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class CrushingRecipe implements ICrushingRecipe {
    private final ResourceLocation id;
    private final Ingredient input;
    private final ItemStack output;
    private final int time;

    public CrushingRecipe(ResourceLocation id, Ingredient input, ItemStack output, int time) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.time = time;
    }

    @Override
    public boolean matches(IInventory inv, World world) {
        return this.input.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(IInventory inv) {
        return output;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(BlocksInit.CRUSHER_BLOCK.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipesInit.CRUSHING_SERIALIZER.get();
    }

    public static class CrushingRecipeType implements IRecipeType<CrushingRecipe> {
        @Override
        public String toString() {
            return CrushingRecipe.TYPE_ID.toString();
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CrushingRecipe> {

        @Override
        public CrushingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient input = Ingredient.fromJson(json.get("ingredient"));
            ResourceLocation itemId = new ResourceLocation(JSONUtils.getAsString(json, "result"));
            int count = JSONUtils.getAsInt(json, "count");
            ItemStack output = new ItemStack(ForgeRegistries.ITEMS.getValue(itemId), count);
            int time = JSONUtils.getAsInt(json, "time");

            return new CrushingRecipe(recipeId, input, output, time);
        }

        @Nullable
        @Override
        public CrushingRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            int time = buffer.readInt();

            return new CrushingRecipe(recipeId, input, output, time);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, CrushingRecipe recipe) {
            recipe.input.toNetwork(buffer);
            buffer.writeItem(recipe.getResultItem());
            buffer.writeInt(recipe.getTime());
        }
    }
}
