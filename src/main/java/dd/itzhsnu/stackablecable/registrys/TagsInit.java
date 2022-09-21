package dd.itzhsnu.stackablecable.registrys;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

import static net.minecraft.tags.FluidTags.bind;

public class TagsInit {

    public static final ITag.INamedTag<Item> AP_TOOLS = ItemTags.bind("scable:ap_tools");
    public static final ITag.INamedTag<Item> CAN_CONVERT_TO_AP_TOOL = ItemTags.bind("scable:can_convert_ap_tool");
    public static final ITag.INamedTag<Item> AP_TOOL_TYPE_SWORD = ItemTags.bind("scable:ap_tool_type_sword");
    public static final ITag.INamedTag<Item> AP_TOOL_TYPE_PICKAXE = ItemTags.bind("scable:ap_tool_type_pickaxe");
    public static final ITag.INamedTag<Item> AP_TOOL_TYPE_AXE = ItemTags.bind("scable:ap_tool_type_axe");
    public static final ITag.INamedTag<Item> AP_TOOL_TYPE_SHOVEL = ItemTags.bind("scable:ap_tool_type_shovel");
    public static final ITag.INamedTag<Item> AP_TOOL_TYPE_HOE = ItemTags.bind("scable:ap_tool_type_hoe");
    public static final ITag.INamedTag<Item> AP_TOOL_TYPE_SHEARS = ItemTags.bind("scable:ap_tool_type_shears");

    public static final ITag.INamedTag<Fluid> NITROGEN = bind("scable:nitrogen");
}
