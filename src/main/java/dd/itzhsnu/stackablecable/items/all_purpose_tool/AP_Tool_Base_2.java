package dd.itzhsnu.stackablecable.items.all_purpose_tool;

import dd.itzhsnu.stackablecable.registrys.ItemsInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class AP_Tool_Base_2 extends Item {
    public AP_Tool_Base_2(Properties properties) {
        super(properties.defaultDurability(1000000));
    }

    public String getAP_ToolType() {
        return "none";
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public boolean isValidRepairItem(ItemStack repairItem, ItemStack repairMaterial) {
        if (repairItem.getTag() != null && repairItem.getTag().contains("Repair_Material")) {
            Ingredient ingredient = Ingredient.of(Item.byId(repairItem.getTag().getInt("Repair_Material")));
            return ingredient.test(repairMaterial) || super.isValidRepairItem(repairItem, repairMaterial);
        }
        return false;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        if (stack.getTag() != null && stack.getTag().contains("Durability")) {
            return stack.getTag().getInt("Durability");
        }
        return 1;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (player.isShiftKeyDown()) {
            if (!world.isClientSide()) {
                if (player.getItemInHand(hand).getTag().getIntArray("Mode_List").length > 1) {
                    int[] id = player.getItemInHand(hand).getTag().getIntArray("Mode_List");
                    int modeID = player.getItemInHand(hand).getTag().getInt("Mode_ID");
                    CompoundNBT nbt = player.getItemInHand(hand).getTag().copy();
                    ++modeID;
                    if (modeID >= id.length) modeID = 0;
                    ItemStack stack = new ItemStack(Item.byId(id[modeID]));

                    nbt.putFloat("Mining_Speed", (float) nbt.getLongArray("Mining_Speed_List")[modeID] / 100);
                    nbt.putInt("Mode_ID", modeID);
                    nbt.remove("AttributeModifiers");

                    stack.setTag(nbt);

                    stack.addAttributeModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier("Mode Attack Damage", (float) nbt.getLongArray("Attack_Damage_List")[modeID] / 100 , AttributeModifier.Operation.ADDITION), EquipmentSlotType.MAINHAND);
                    stack.addAttributeModifier(Attributes.ATTACK_SPEED, new AttributeModifier("Mode Attack Speed", (float) nbt.getLongArray("Attack_Speed_List")[modeID] / 100, AttributeModifier.Operation.ADDITION), EquipmentSlotType.MAINHAND);

                    player.setItemInHand(hand, stack);
                }
            } else {
                player.playSound(SoundEvents.UI_BUTTON_CLICK, 1, 1);
            }
        }
        return super.use(world, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> text, ITooltipFlag flag) {
        if (stack.getTag() != null) {
            int[] old_list = stack.getTag().getIntArray("Old_List");
            CompoundNBT nbt = stack.getTag();
            for (int i = 0; old_list.length > i; ++i) {
                if (Item.byId(old_list[i]) == ItemsInit.AP_TOOL_NAME.get()) {
                    text.add(new TranslationTextComponent("item.scable.custom_ap_tool_" + nbt.getIntArray("Custom_Tool_ID")[i]));
                } else {
                    text.add(new TranslationTextComponent(Item.byId(old_list[i]).getName(stack).getString()));
                }
            }
            text.add(StringTextComponent.EMPTY);
            text.add(new TranslationTextComponent("text.scable.tool_type").append(new TranslationTextComponent("text.scable." + getAP_ToolType())));
            if (nbt.contains("Mining_Speed"))
                text.add(new TranslationTextComponent("text.scable.mining_speed").append(String.valueOf(nbt.getFloat("Mining_Speed"))));
            if (nbt.contains("Durability")) {
                int damage = Math.max(stack.getDamageValue(), 0);
                text.add(new TranslationTextComponent("text.scable.durability").append(String.valueOf(nbt.getInt("Durability") - damage)));
            }
        }
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        if (stack.getTag() != null && stack.getTag().contains("Custom_Tool_ID")) {
            int tool_Id = stack.getTag().getIntArray("Custom_Tool_ID")[stack.getTag().getInt("Mode_ID")];
            if (tool_Id >= 1) {
                return new TranslationTextComponent("item.scable.ap_tool").append(" (").append(new TranslationTextComponent("item.scable.custom_ap_tool_" + tool_Id)).append(")");
            } else if (tool_Id == 0) {
                return new TranslationTextComponent("item.scable.ap_tool").append(" (").append(new TranslationTextComponent(Item.byId(stack.getTag().getIntArray("Old_List")[stack.getTag().getInt("Mode_ID")]).getDescriptionId())).append(")");
               }
        }
        return new TranslationTextComponent("item.scable.ap_tool_bugged");
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.category.canEnchant(Items.DIAMOND_SWORD) || enchantment.category.canEnchant(Items.DIAMOND_PICKAXE);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }
}