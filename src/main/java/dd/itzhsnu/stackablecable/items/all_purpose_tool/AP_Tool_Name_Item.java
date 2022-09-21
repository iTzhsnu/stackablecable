package dd.itzhsnu.stackablecable.items.all_purpose_tool;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AP_Tool_Name_Item extends Item {
    public AP_Tool_Name_Item(Properties properties) {
        super(properties);
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        if (stack.getTag() != null && stack.getTag().contains("Custom_Tool_ID")) {
            return new TranslationTextComponent("item.scable.custom_ap_tool_" + stack.getTag().getIntArray("Custom_Tool_ID")[stack.getTag().getInt("Mode_ID")]);
        }
        return new TranslationTextComponent("item.scable.ap_tool_bugged");
    }
}
