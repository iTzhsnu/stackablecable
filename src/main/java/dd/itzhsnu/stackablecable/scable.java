package dd.itzhsnu.stackablecable;

import dd.itzhsnu.stackablecable.blocks.ScableOreGen;
import dd.itzhsnu.stackablecable.client.scabletab;
import dd.itzhsnu.stackablecable.items.all_purpose_tool.AP_Tool_Recipe;
import dd.itzhsnu.stackablecable.network.ScableNetwork;
import dd.itzhsnu.stackablecable.registrys.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(scable.MOD_ID)
@Mod.EventBusSubscriber(modid = scable.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class scable {
    public static final String MOD_ID = "scable";
    public static final ItemGroup SCABLE_TAB = new scabletab();
    public scable (){
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ScableOreGen::oreGen);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ItemsInit.ITEMS.register(modEventBus);
        BlocksInit.BLOCKS.register(modEventBus);
        FluidsInit.FLUIDS.register(modEventBus);
        TileEntityInit.TILE_ENTITIES.register(modEventBus);
        ContainersInit.CONTAINERS.register(modEventBus);
        RecipesInit.register(modEventBus);
    }

    @SubscribeEvent
    public static void registerRecipes(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
        event.getRegistry().register(RecipesInit.AP_TOOL_CONVERT_RECIPE.setRegistryName(scable.MOD_ID, "ap_tool_convert"));
        event.getRegistry().register(RecipesInit.AP_TOOL_COMBINE_RECIPE.setRegistryName(scable.MOD_ID, "ap_tool_combine"));
        event.getRegistry().register(RecipesInit.AP_TOOL_RECIPE.setRegistryName(AP_Tool_Recipe.TYPE_ID));
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        ScableNetwork.init();
    }

}