package dd.itzhsnu.stackablecable.registrys;

import dd.itzhsnu.stackablecable.blocks.Crusher.CrushingRecipe;
import dd.itzhsnu.stackablecable.items.all_purpose_tool.AP_Tool_Combine_Recipe;
import dd.itzhsnu.stackablecable.items.all_purpose_tool.AP_Tool_Convert_Recipe;
import dd.itzhsnu.stackablecable.items.all_purpose_tool.AP_Tool_Recipe;
import dd.itzhsnu.stackablecable.scable;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipesInit {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, scable.MOD_ID);

    public static final RegistryObject<CrushingRecipe.Serializer> CRUSHING_SERIALIZER = RECIPE_SERIALIZERS
            .register("crushing", CrushingRecipe.Serializer::new);
    public static final SpecialRecipeSerializer<AP_Tool_Convert_Recipe> AP_TOOL_CONVERT_RECIPE = new SpecialRecipeSerializer<>(AP_Tool_Convert_Recipe::new);
    public static final SpecialRecipeSerializer<AP_Tool_Combine_Recipe> AP_TOOL_COMBINE_RECIPE = new SpecialRecipeSerializer<>(AP_Tool_Combine_Recipe::new);
    public static final IRecipeSerializer<AP_Tool_Recipe> AP_TOOL_RECIPE = new AP_Tool_Recipe.Serializer();

    public static IRecipeType<CrushingRecipe> CRUSHING_RECIPE = new CrushingRecipe.CrushingRecipeType();

    public static void register(IEventBus bus) {
        RECIPE_SERIALIZERS.register(bus);

        Registry.register(Registry.RECIPE_TYPE, CrushingRecipe.TYPE_ID, CRUSHING_RECIPE);
    }
}
