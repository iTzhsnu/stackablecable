package dd.itzhsnu.stackablecable.items.all_purpose_tool;

import com.google.common.collect.Lists;
import dd.itzhsnu.stackablecable.registrys.ItemsInit;
import dd.itzhsnu.stackablecable.registrys.RecipesInit;
import dd.itzhsnu.stackablecable.registrys.TagsInit;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.List;

public class AP_Tool_Convert_Recipe extends SpecialRecipe {
    public AP_Tool_Convert_Recipe(ResourceLocation resourceLocation) {
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

        if (list.size() == 1) {
            return TagsInit.CAN_CONVERT_TO_AP_TOOL.contains(list.get(0).getItem());
        } else {
            return false;
        }
    }

    @Override
    public ItemStack assemble(CraftingInventory inv) {
        List<ItemStack> list = Lists.newArrayList();
        for (int i = 0; inv.getContainerSize() > i; ++i) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                list.add(stack);
            }
        }

        if (list.size() == 1 && TagsInit.CAN_CONVERT_TO_AP_TOOL.contains(list.get(0).getItem())) {
            ItemStack stack = list.get(0);
            ItemStack output = ItemStack.EMPTY;
            CompoundNBT nbt = new CompoundNBT();
            float speed = 0.0F;
            float attack_speed = 0.0F;

            if (TagsInit.AP_TOOL_TYPE_SWORD.contains(stack.getItem())) {
                speed = stack.getDestroySpeed(Blocks.COBWEB.defaultBlockState()) - 9.0F;
                output = ItemsInit.AP_TOOL_SWORD.get().getDefaultInstance();
                attack_speed = -2.4F;
            } else if (TagsInit.AP_TOOL_TYPE_PICKAXE.contains(stack.getItem())) {
                speed = stack.getDestroySpeed(Blocks.COBBLESTONE.defaultBlockState());
                switch (stack.getHarvestLevel(ToolType.PICKAXE, null, null)) {
                    case 0: output = ItemsInit.AP_TOOL_PICKAXE_0.get().getDefaultInstance();
                        break;
                    case 1: output = ItemsInit.AP_TOOL_PICKAXE_1.get().getDefaultInstance();
                        break;
                    case 2: output = ItemsInit.AP_TOOL_PICKAXE_2.get().getDefaultInstance();
                        break;
                    case 3: output = ItemsInit.AP_TOOL_PICKAXE_3.get().getDefaultInstance();
                        break;
                    case 4: output = ItemsInit.AP_TOOL_PICKAXE_4.get().getDefaultInstance();
                        break;
                    case 5: output = ItemsInit.AP_TOOL_PICKAXE_5.get().getDefaultInstance();
                        break;
                }
                attack_speed = -2.8F;
            } else if (TagsInit.AP_TOOL_TYPE_AXE.contains(stack.getItem())) {
                speed = stack.getDestroySpeed(Blocks.OAK_BUTTON.defaultBlockState());
                switch (stack.getHarvestLevel(ToolType.AXE, null, null)) {
                    case 0: output = ItemsInit.AP_TOOL_AXE_0.get().getDefaultInstance();
                       break;
                    case 1: output = ItemsInit.AP_TOOL_AXE_1.get().getDefaultInstance();
                        break;
                    case 2: output = ItemsInit.AP_TOOL_AXE_2.get().getDefaultInstance();
                        break;
                    case 3: output = ItemsInit.AP_TOOL_AXE_3.get().getDefaultInstance();
                        break;
                    case 4: output = ItemsInit.AP_TOOL_AXE_4.get().getDefaultInstance();
                        break;
                    case 5: output = ItemsInit.AP_TOOL_AXE_5.get().getDefaultInstance();
                        break;
                }
                attack_speed = -3.0F;
            } else if (TagsInit.AP_TOOL_TYPE_SHOVEL.contains(stack.getItem())) {
                speed = stack.getDestroySpeed(Blocks.DIRT.defaultBlockState());
                switch (stack.getHarvestLevel(ToolType.SHOVEL, null, null)) {
                    case 0: output = ItemsInit.AP_TOOL_SHOVEL_0.get().getDefaultInstance();
                        break;
                    case 1: output = ItemsInit.AP_TOOL_SHOVEL_1.get().getDefaultInstance();
                        break;
                    case 2: output = ItemsInit.AP_TOOL_SHOVEL_2.get().getDefaultInstance();
                        break;
                    case 3: output = ItemsInit.AP_TOOL_SHOVEL_3.get().getDefaultInstance();
                        break;
                    case 4: output = ItemsInit.AP_TOOL_SHOVEL_4.get().getDefaultInstance();
                        break;
                    case 5: output = ItemsInit.AP_TOOL_SHOVEL_5.get().getDefaultInstance();
                        break;
                }
                attack_speed = -3.0F;
            } else if (TagsInit.AP_TOOL_TYPE_HOE.contains(stack.getItem())) {
                speed = stack.getDestroySpeed(Blocks.OAK_LEAVES.defaultBlockState());
                switch (stack.getHarvestLevel(ToolType.HOE, null, null)) {
                    case 0: output = ItemsInit.AP_TOOL_HOE_0.get().getDefaultInstance();
                        break;
                    case 1: output = ItemsInit.AP_TOOL_HOE_1.get().getDefaultInstance();
                        break;
                    case 2: output = ItemsInit.AP_TOOL_HOE_2.get().getDefaultInstance();
                        break;
                    case 3: output = ItemsInit.AP_TOOL_HOE_3.get().getDefaultInstance();
                        break;
                    case 4: output = ItemsInit.AP_TOOL_HOE_4.get().getDefaultInstance();
                        break;
                    case 5: output = ItemsInit.AP_TOOL_HOE_5.get().getDefaultInstance();
                        break;
                }
            } else if (TagsInit.AP_TOOL_TYPE_SHEARS.contains(stack.getItem())) {
                speed = stack.getDestroySpeed(Blocks.COBWEB.defaultBlockState()) - 9.0F;
                output = ItemsInit.AP_TOOL_SHEARS.get().getDefaultInstance();
            }

            speed = speed * 100.0F;

            long[] speeds = new long[] {(long) speed};
            int[] mode_list = new int[] {Item.getId(output.getItem())};
            int[] durability_list = new int[] {stack.getMaxDamage()};
            long[] atk_damage_list;
            long[] atk_speed_list;
            int[] old_list = new int[] {Item.getId(stack.getItem())};

            if (stack.getItem() instanceof ToolItem) {
                float attack_damage = ((ToolItem) stack.getItem()).getAttackDamage() * 100F;
                atk_damage_list = new long[] {(long) attack_damage};
            } else if (stack.getItem() instanceof SwordItem) {
                float attack_damage = ((SwordItem) stack.getItem()).getTier().getAttackDamageBonus() * 100F + 300F;
                atk_damage_list = new long[] {(long) attack_damage};
            } else {
                atk_damage_list = new long[] {0};
            }
            attack_speed = attack_speed * 100F;
            atk_speed_list = new long[] {(long) attack_speed};

            nbt.putFloat("Mining_Speed", speed / 100F);
            nbt.putLongArray("Mining_Speed_List", speeds);
            nbt.putInt("Durability", stack.getMaxDamage());
            nbt.putIntArray("Durability_List", durability_list);
            nbt.putIntArray("Mode_List", mode_list);
            nbt.putInt("Mode_ID", 0);
            nbt.putLongArray("Attack_Damage_List", atk_damage_list);
            nbt.putLongArray("Attack_Speed_List", atk_speed_list);
            nbt.putIntArray("Old_List", old_list);
            nbt.putIntArray("Custom_Tool_ID", new int[] {0});
            //nbt.putInt("Repair_Material", Item.getId());

            output.setTag(nbt);

            output.addAttributeModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier("Mode Attack Damage", (float) atk_damage_list[0] / 100, AttributeModifier.Operation.ADDITION), EquipmentSlotType.MAINHAND);
            output.addAttributeModifier(Attributes.ATTACK_SPEED, new AttributeModifier("Mode Attack Speed", (float) atk_speed_list[0] / 100, AttributeModifier.Operation.ADDITION), EquipmentSlotType.MAINHAND);

            return output;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipesInit.AP_TOOL_CONVERT_RECIPE;
    }
}
