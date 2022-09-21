package dd.itzhsnu.stackablecable.registrys;

import dd.itzhsnu.stackablecable.items.Other_Shears;
import dd.itzhsnu.stackablecable.items.all_purpose_tool.*;
import dd.itzhsnu.stackablecable.items.hammer.Hammer;
import dd.itzhsnu.stackablecable.items.TextBlockItem;
import dd.itzhsnu.stackablecable.items.Wrench;
import dd.itzhsnu.stackablecable.items.conduits.EnergyConduit;
import dd.itzhsnu.stackablecable.items.conduits.FluidConduit;
import dd.itzhsnu.stackablecable.items.conduits.ItemConduit;
import dd.itzhsnu.stackablecable.scable;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemsInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, scable.MOD_ID);

    public static final RegistryObject<Item> TITANIUM_ORE_BLOCK = ITEMS.register("titanium_ore", () -> new BlockItem(BlocksInit.TITANIUM_ORE.get(), (new Item.Properties().tab(scable.SCABLE_TAB))));
    public static final RegistryObject<Item> TITANIUM_DUST = ITEMS.register("titanium_dust", () -> new Item(new Item.Properties().tab(scable.SCABLE_TAB)));
    public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot", () -> new Item(new Item.Properties().tab(scable.SCABLE_TAB)));
    public static final RegistryObject<Item> TITANIUM_BLOCK_ITEM = ITEMS.register("titanium_block", () -> new BlockItem(BlocksInit.TITANIUM_BLOCK.get(), (new Item.Properties().tab(scable.SCABLE_TAB))));
    public static final RegistryObject<Item> NIOBIUM_ORE_BLOCK = ITEMS.register("niobium_ore", () -> new BlockItem((BlocksInit.NIOBIUM_ORE.get()), (new Item.Properties().tab(scable.SCABLE_TAB))));
    public static final RegistryObject<Item> NIOBIUM_DUST = ITEMS.register("niobium_dust", () -> new Item(new Item.Properties().tab(scable.SCABLE_TAB)));
    public static final RegistryObject<Item> NIOBIUM_INGOT = ITEMS.register("niobium_ingot", () -> new Item(new Item.Properties().tab(scable.SCABLE_TAB)));
    public static final RegistryObject<Item> NIOBIUM_BLOCK_ITEM = ITEMS.register("niobium_block", () -> new BlockItem(BlocksInit.NIOBIUM_BLOCK.get(), (new Item.Properties().tab(scable.SCABLE_TAB))));
    public static final RegistryObject<Item> NIOBIUM_TITANIUM_DUST = ITEMS.register("niobium_titanium_dust", () -> new Item(new Item.Properties().tab(scable.SCABLE_TAB)));
    public static final RegistryObject<Item> NIOBIUM_TITANIUM_INGOT = ITEMS.register("niobium_titanium_ingot", () -> new Item(new Item.Properties().tab(scable.SCABLE_TAB)));
    public static final RegistryObject<Item> NIOBIUM_TITANIUM_BLOCK_ITEM = ITEMS.register("niobium_titanium_block", () -> new BlockItem(BlocksInit.NIOBIUM_TITANIUM_BLOCK.get(), (new Item.Properties().tab(scable.SCABLE_TAB))));
    public static final RegistryObject<Item> NITROGEN_BUCKET = ITEMS.register("nitrogen_bucket", () -> new BucketItem(FluidsInit.STILL_NITROGEN, new Item.Properties().tab(scable.SCABLE_TAB).stacksTo(1)));
    public static final RegistryObject<Item> CRUSHER_ITEM = ITEMS.register("crusher", () -> new TextBlockItem(BlocksInit.CRUSHER_BLOCK.get(), (new Item.Properties().tab(scable.SCABLE_TAB).stacksTo(1))));
    public static final RegistryObject<Item> TANK_ITEM = ITEMS.register("tank", () -> new BlockItem(BlocksInit.TANK_BLOCK.get(), (new Item.Properties().tab(scable.SCABLE_TAB).fireResistant().stacksTo(1))));
    public static final RegistryObject<Item> IRON_DUST = ITEMS.register("iron_dust", () -> new Item(new Item.Properties().tab(scable.SCABLE_TAB)));
    public static final RegistryObject<Item> GOLD_DUST = ITEMS.register("gold_dust", () -> new Item(new Item.Properties().tab(scable.SCABLE_TAB)));
    public static final RegistryObject<Item> NETHERITE_SCRAP_DUST = ITEMS.register("netherite_scrap_dust", () -> new Item(new Item.Properties().tab(scable.SCABLE_TAB)));
    public static final RegistryObject<Item> NETHERITE_DUST = ITEMS.register("netherite_dust", () -> new Item(new Item.Properties().tab(scable.SCABLE_TAB)));
    public static final RegistryObject<Item> HAMMER_ITEM = ITEMS.register("hammer", () -> new Hammer(new Item.Properties().tab(scable.SCABLE_TAB).durability(768)));
    public static final RegistryObject<Item> CAPACITOR_ITEM = ITEMS.register("capacitor", () -> new BlockItem(BlocksInit.CAPACITOR_BLOCK.get(), (new Item.Properties().tab(scable.SCABLE_TAB).stacksTo(1))));
    public static final RegistryObject<Item> GENERATOR_ITEM = ITEMS.register("generator", () -> new TextBlockItem(BlocksInit.GENERATOR_BLOCK.get(), (new Item.Properties().tab(scable.SCABLE_TAB).stacksTo(1))));
    public static final RegistryObject<Item> COMPRESSOR_ITEM = ITEMS.register("compressor", () -> new TextBlockItem(BlocksInit.COMPRESSOR_BLOCK.get(), (new Item.Properties().tab(scable.SCABLE_TAB).stacksTo(1))));
    public static final RegistryObject<Item> IRON_GEAR_ITEM = ITEMS.register("iron_gear", () -> new Item(new Item.Properties().tab(scable.SCABLE_TAB)));
    public static final RegistryObject<Item> WRENCH_ITEM = ITEMS.register("wrench", () -> new Wrench(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ITEM_CONDUIT_ITEM = ITEMS.register("item_conduit", () -> new ItemConduit(new Item.Properties().tab(scable.SCABLE_TAB)));
    public static final RegistryObject<Item> FLUID_CONDUIT_ITEM = ITEMS.register("fluid_conduit", () -> new FluidConduit(new Item.Properties()));
    public static final RegistryObject<Item> ENERGY_CONDUIT_ITEM = ITEMS.register("energy_conduit", () -> new EnergyConduit(new Item.Properties()));

    public static final RegistryObject<Item> EMERALD_SWORD = ITEMS.register("emerald_sword", () -> new SwordItem(ItemTier.DIAMOND, 3, -2.4F, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> EMERALD_PICKAXE = ITEMS.register("emerald_pickaxe", () -> new PickaxeItem(ItemTier.DIAMOND, 1, -2.8F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> EMERALD_AXE = ITEMS.register("emerald_axe", () -> new AxeItem(ItemTier.DIAMOND, 5, -3.0F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> EMERALD_SHOVEL = ITEMS.register("emerald_shovel", () -> new ShovelItem(ItemTier.DIAMOND, 1.5F, -3.0F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> EMERALD_HOE = ITEMS.register("emerald_hoe", () -> new HoeItem(ItemTier.DIAMOND, -3, 0, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> EMERALD_SHEARS = ITEMS.register("emerald_shears", () -> new Other_Shears(ItemTier.DIAMOND, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));

    public static final RegistryObject<Item> WOODEN_SHEARS = ITEMS.register("wooden_shears", () -> new Other_Shears(ItemTier.WOOD, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> STONE_SHEARS = ITEMS.register("stone_shears", () -> new Other_Shears(ItemTier.STONE, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> GOLDEN_SHEARS = ITEMS.register("golden_shears", () -> new Other_Shears(ItemTier.GOLD, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> DIAMOND_SHEARS = ITEMS.register("diamond_shears", () -> new Other_Shears(ItemTier.DIAMOND, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> NETHERITE_SHEARS = ITEMS.register("netherite_shears", () -> new Other_Shears(ItemTier.NETHERITE, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));

    public static final RegistryObject<Item> AP_TOOL_NAME = ITEMS.register("ap_tool_name", () -> new AP_Tool_Name_Item(new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_SWORD = ITEMS.register("ap_tool_sword", () -> new AP_Tool_Sword(new Item.Properties()));

    public static final RegistryObject<Item> AP_TOOL_PICKAXE_0 = ITEMS.register("ap_tool_pickaxe_0", () -> new AP_Tool_Pickaxe(0, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_PICKAXE_1 = ITEMS.register("ap_tool_pickaxe_1", () -> new AP_Tool_Pickaxe(1, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_PICKAXE_2 = ITEMS.register("ap_tool_pickaxe_2", () -> new AP_Tool_Pickaxe(2, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_PICKAXE_3 = ITEMS.register("ap_tool_pickaxe_3", () -> new AP_Tool_Pickaxe(3, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_PICKAXE_4 = ITEMS.register("ap_tool_pickaxe_4", () -> new AP_Tool_Pickaxe(4, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_PICKAXE_5 = ITEMS.register("ap_tool_pickaxe_5", () -> new AP_Tool_Pickaxe(5, new Item.Properties()));

    public static final RegistryObject<Item> AP_TOOL_AXE_0 = ITEMS.register("ap_tool_axe_0", () -> new AP_Tool_Axe(0, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_AXE_1 = ITEMS.register("ap_tool_axe_1", () -> new AP_Tool_Axe(1, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_AXE_2 = ITEMS.register("ap_tool_axe_2", () -> new AP_Tool_Axe(2, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_AXE_3 = ITEMS.register("ap_tool_axe_3", () -> new AP_Tool_Axe(3, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_AXE_4 = ITEMS.register("ap_tool_axe_4", () -> new AP_Tool_Axe(4, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_AXE_5 = ITEMS.register("ap_tool_axe_5", () -> new AP_Tool_Axe(5, new Item.Properties()));

    public static final RegistryObject<Item> AP_TOOL_SHOVEL_0 = ITEMS.register("ap_tool_shovel_0", () -> new AP_Tool_Shovel(0, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_SHOVEL_1 = ITEMS.register("ap_tool_shovel_1", () -> new AP_Tool_Shovel(1, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_SHOVEL_2 = ITEMS.register("ap_tool_shovel_2", () -> new AP_Tool_Shovel(2, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_SHOVEL_3 = ITEMS.register("ap_tool_shovel_3", () -> new AP_Tool_Shovel(3, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_SHOVEL_4 = ITEMS.register("ap_tool_shovel_4", () -> new AP_Tool_Shovel(4, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_SHOVEL_5 = ITEMS.register("ap_tool_shovel_5", () -> new AP_Tool_Shovel(5, new Item.Properties()));

    public static final RegistryObject<Item> AP_TOOL_HOE_0 = ITEMS.register("ap_tool_hoe_0", () -> new AP_Tool_Hoe(0, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_HOE_1 = ITEMS.register("ap_tool_hoe_1", () -> new AP_Tool_Hoe(1, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_HOE_2 = ITEMS.register("ap_tool_hoe_2", () -> new AP_Tool_Hoe(2, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_HOE_3 = ITEMS.register("ap_tool_hoe_3", () -> new AP_Tool_Hoe(3, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_HOE_4 = ITEMS.register("ap_tool_hoe_4", () -> new AP_Tool_Hoe(4, new Item.Properties()));
    public static final RegistryObject<Item> AP_TOOL_HOE_5 = ITEMS.register("ap_tool_hoe_5", () -> new AP_Tool_Hoe(5, new Item.Properties()));

    public static final RegistryObject<Item> AP_TOOL_SHEARS = ITEMS.register("ap_tool_shears", () -> new AP_Tool_Shears(new Item.Properties()));
}
