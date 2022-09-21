package dd.itzhsnu.stackablecable.items.all_purpose_tool;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import dd.itzhsnu.stackablecable.registrys.ItemsInit;
import dd.itzhsnu.stackablecable.registrys.RecipesInit;
import dd.itzhsnu.stackablecable.scable;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class AP_Tool_Recipe implements ICraftingRecipe, IShapedRecipe<CraftingInventory> {

    public static final ResourceLocation TYPE_ID = new ResourceLocation(scable.MOD_ID, "ap_tool_recipe");
    private final ResourceLocation id;
    private final int width;
    private final int height;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final float mining_Speed;
    private final int durability;
    private final float attack_Damage;
    private final float attack_Speed;
    private final int old_Id = Item.getId(ItemsInit.AP_TOOL_NAME.get());
    private final int item_Id;

    public AP_Tool_Recipe(ResourceLocation id, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result, float mining_Speed, int durability, float attack_Damage, float attack_Speed, int item_Id) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.ingredients = ingredients;
        this.result = result;
        this.mining_Speed = mining_Speed;
        this.durability = durability;
        this.attack_Damage = attack_Damage;
        this.attack_Speed = attack_Speed;
        this.item_Id = item_Id;
    }

    @Override
    public int getRecipeWidth() {
        return this.width;
    }

    @Override
    public int getRecipeHeight() {
        return this.height;
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        for(int i = 0; i <= inv.getWidth() - this.width; ++i) {
            for(int j = 0; j <= inv.getHeight() - this.height; ++j) {
                if (this.matches(inv, i, j, true)) {
                    return true;
                }

                if (this.matches(inv, i, j, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean matches(CraftingInventory inv, int width, int height, boolean p_77573_4_) {
        for(int i = 0; i < inv.getWidth(); ++i) {
            for(int j = 0; j < inv.getHeight(); ++j) {
                int k = i - width;
                int l = j - height;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.width && l < this.height) {
                    if (p_77573_4_) {
                        ingredient = this.ingredients.get(this.width - k - 1 + l * this.width);
                    } else {
                        ingredient = this.ingredients.get(k + l * this.width);
                    }
                }

                if (!ingredient.test(inv.getItem(i + j * inv.getWidth()))) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(CraftingInventory inv) {
        return this.getResultItem().copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= this.width && height >= this.height;
    }

    @Override
    public ItemStack getResultItem() {
        ItemStack output = this.result.copy();
        CompoundNBT nbt = new CompoundNBT();
        float mining_Speed = this.mining_Speed * 100F;
        float attack_Damage = this.attack_Damage * 100F;
        float attack_Speed = this.attack_Speed * 100F;
        long[] mining_Speeds = new long[] {(long) mining_Speed};
        int[] durabilitys = new int[] {this.durability};
        int[] modes = new int[] {Item.getId(output.getItem())};
        long[] attack_Damages = new long[] {(long) attack_Damage};
        long[] attack_Speeds = new long[] {(long) attack_Speed};
        int[] olds = new int[] {this.old_Id};
        int[] old_item_Id = new int[] {this.item_Id};

        nbt.putFloat("Mining_Speed", this.mining_Speed);
        nbt.putLongArray("Mining_Speed_List", mining_Speeds);
        nbt.putInt("Durability", this.durability);
        nbt.putIntArray("Durability_List", durabilitys);
        nbt.putIntArray("Mode_List", modes);
        nbt.putInt("Mode_ID", 0);
        nbt.putLongArray("Attack_Damage_List", attack_Damages);
        nbt.putLongArray("Attack_Speed_List", attack_Speeds);
        nbt.putIntArray("Old_List", olds);
        nbt.putIntArray("Custom_Tool_ID", old_item_Id);

        output.setTag(nbt);

        output.addAttributeModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier("Mode Attack Damage", this.attack_Damage, AttributeModifier.Operation.ADDITION), EquipmentSlotType.MAINHAND);
        output.addAttributeModifier(Attributes.ATTACK_SPEED, new AttributeModifier("Mode Attack Speed", this.attack_Speed, AttributeModifier.Operation.ADDITION), EquipmentSlotType.MAINHAND);

        return output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipesInit.AP_TOOL_RECIPE;
    }

    private static Map<String, Ingredient> keyFromJson(JsonObject json) {
        Map<String, Ingredient> map = Maps.newHashMap();

        for(Map.Entry<String, JsonElement> entry : json.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }

            map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    @VisibleForTesting
    static String[] shrink(String... strings) {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;

        for(int i1 = 0; i1 < strings.length; ++i1) {
            String s = strings[i1];
            i = Math.min(i, firstNonSpace(s));
            int j1 = lastNonSpace(s);
            j = Math.max(j, j1);
            if (j1 < 0) {
                if (k == i1) {
                    ++k;
                }

                ++l;
            } else {
                l = 0;
            }
        }

        if (strings.length == l) {
            return new String[0];
        } else {
            String[] astring = new String[strings.length - l - k];

            for(int k1 = 0; k1 < astring.length; ++k1) {
                astring[k1] = strings[k1 + k].substring(i, j + 1);
            }

            return astring;
        }
    }

    private static int firstNonSpace(String string) {
        int i;
        for(i = 0; i < string.length() && string.charAt(i) == ' '; ++i) {
        }

        return i;
    }

    private static int lastNonSpace(String string) {
        int i;
        for(i = string.length() - 1; i >= 0 && string.charAt(i) == ' '; --i) {
        }

        return i;
    }

    private static String[] patternFromJson(JsonArray p_192407_0_) {
        String[] astring = new String[p_192407_0_.size()];
        int max_Width = 3;
        int max_Height = 3;

        if (astring.length > max_Height) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, " + max_Height + " is maximum");
        } else if (astring.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        } else {
            for(int i = 0; i < astring.length; ++i) {
                String s = JSONUtils.convertToString(p_192407_0_.get(i), "pattern[" + i + "]");
                if (s.length() > max_Width) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, " + max_Width + " is maximum");
                }

                if (i > 0 && astring[0].length() != s.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }

                astring[i] = s;
            }

            return astring;
        }
    }

    private static NonNullList<Ingredient> dissolvePattern(String[] p_192402_0_, Map<String, Ingredient> p_192402_1_, int p_192402_2_, int p_192402_3_) {
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(p_192402_2_ * p_192402_3_, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(p_192402_1_.keySet());
        set.remove(" ");

        for(int i = 0; i < p_192402_0_.length; ++i) {
            for(int j = 0; j < p_192402_0_[i].length(); ++j) {
                String s = p_192402_0_[i].substring(j, j + 1);
                Ingredient ingredient = p_192402_1_.get(s);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
                }

                set.remove(s);
                nonnulllist.set(j + p_192402_2_ * i, ingredient);
            }
        }

        if (!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return nonnulllist;
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AP_Tool_Recipe> {

        @Override
        public AP_Tool_Recipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Map<String, Ingredient> map = keyFromJson(JSONUtils.getAsJsonObject(json, "key"));
            String[] astring = shrink(patternFromJson(JSONUtils.getAsJsonArray(json, "pattern")));
            int i = astring[0].length();
            int j = astring.length;
            NonNullList<Ingredient> nonNullList = dissolvePattern(astring, map, i, j);
            ItemStack result = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getAsString(json, "result"))), JSONUtils.getAsInt(json, "count"));
            float mining_Speed = JSONUtils.getAsFloat(json, "Mining_Speed");
            int durability = JSONUtils.getAsInt(json, "Durability");
            float attack_Damage = JSONUtils.getAsFloat(json, "Attack_Damage");
            float attack_Speed = JSONUtils.getAsFloat(json, "Attack_Speed");
            int item_Id = JSONUtils.getAsInt(json, "Item_ID");

            return new AP_Tool_Recipe(recipeId, i, j, nonNullList, result, mining_Speed, durability, attack_Damage, attack_Speed, item_Id);
        }

        @Nullable
        @Override
        public AP_Tool_Recipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            int i = buffer.readVarInt();
            int j = buffer.readVarInt();
            NonNullList<Ingredient> nonNullList = NonNullList.withSize(i * j, Ingredient.EMPTY);

            for(int k = 0; k < nonNullList.size(); ++k) {
                nonNullList.set(k, Ingredient.fromNetwork(buffer));
            }

            ItemStack result = buffer.readItem();
            float mining_Speed = buffer.readFloat();
            int durability = buffer.readInt();
            float attack_Damage = buffer.readFloat();
            float attack_Speed = buffer.readFloat();
            int item_Id = buffer.readInt();

            return new AP_Tool_Recipe(recipeId, i, j, nonNullList, result, mining_Speed, durability, attack_Damage, attack_Speed, item_Id);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, AP_Tool_Recipe recipe) {
            buffer.writeVarInt(recipe.width);
            buffer.writeVarInt(recipe.height);
            for(Ingredient ingredient : recipe.ingredients) {
                ingredient.toNetwork(buffer);
            }
            buffer.writeItem(recipe.result);
            buffer.writeFloat(recipe.mining_Speed);
            buffer.writeInt(recipe.durability);
            buffer.writeFloat(recipe.attack_Damage);
            buffer.writeFloat(recipe.mining_Speed);
            buffer.writeInt(recipe.item_Id);
        }
    }
}
