package dd.itzhsnu.stackablecable.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class Wrench extends Item {
    public Wrench(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (player.isShiftKeyDown()) {
            if (!world.isClientSide()) {
                ItemStack stack = player.getItemInHand(hand);
                if (stack.getTag() != null) modeChange(stack, player);
                    return ActionResult.consume(stack);
            } else {
                player.playSound(SoundEvents.UI_BUTTON_CLICK, 1, 1);
            }
        }
        return super.use(world, player, hand);
    }

    /**
     * Mode Change <p>
     *     Mode List: 1. Item I/O, 2. Fluid I/O, 3. Energy I/O, 4. Copy NBT, 5. Paste NBT
     * </p>
     * @param stack ItemStack
     * @param player Player
     * @return Changed Mode
     */
    private void modeChange(ItemStack stack, PlayerEntity player) {
        if (stack.getTag() != null) {
            int mode = 0;
            int opposite = 0;
            if (stack.getTag().contains("Opposite")) opposite = stack.getTag().getInt("Opposite");
            if (stack.getTag().contains("Mode")) mode = stack.getTag().getInt("Mode");
            mode = mode + 1;
            if (mode > 3 && opposite == 1) {
                mode = 1;
                opposite = 0;
            }
            if (mode > 5 && opposite == 0) {
                mode = 1;
                opposite = 1;
            }
            stack.getTag().putInt("Mode", mode);
            stack.getTag().putInt("Opposite", opposite);
            switch (mode) {
                case 1:
                    player.sendMessage(new TranslationTextComponent("tooltip.scable.wrench_mode_1"), UUID.randomUUID());
                    break;
                case 2:
                    player.sendMessage(new TranslationTextComponent("tooltip.scable.wrench_mode_2"), UUID.randomUUID());
                    break;
                case 3:
                    player.sendMessage(new TranslationTextComponent("tooltip.scable.wrench_mode_3"), UUID.randomUUID());
                    break;
                case 4:
                    player.sendMessage(new TranslationTextComponent("tooltip.scable.wrench_mode_4"), UUID.randomUUID());
                    break;
                case 5:
                    player.sendMessage(new TranslationTextComponent("tooltip.scable.wrench_mode_5"), UUID.randomUUID());
                    break;
                default:
                    player.sendMessage(new TranslationTextComponent("tooltip.scable.wrench_mode_unknown"), UUID.randomUUID());
                    break;
            }
            if (opposite > 0)
                player.sendMessage(new TranslationTextComponent("tooltip.scable.opposite"), UUID.randomUUID());
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> text, ITooltipFlag flag) {
        if (stack.getTag() != null) {
            switch (stack.getTag().getInt("Mode")) {
                case 1: text.add(new TranslationTextComponent("tooltip.scable.wrench_mode_1"));
                break;
                case 2: text.add(new TranslationTextComponent("tooltip.scable.wrench_mode_2"));
                break;
                case 3: text.add(new TranslationTextComponent("tooltip.scable.wrench_mode_3"));
                break;
                case 4: text.add(new TranslationTextComponent("tooltip.scable.wrench_mode_4"));
                break;
                case 5: text.add(new TranslationTextComponent("tooltip.scable.wrench_mode_5"));
                break;
                default: text.add(new TranslationTextComponent("tooltip.scable.wrench_mode_unknown"));
                break;
            }
            if (stack.getTag().getInt("Opposite") > 0) text.add(new TranslationTextComponent("tooltip.scable.opposite"));
        } else {
            text.add(new TranslationTextComponent("tooltip.scable.wrench_mode_unknown"));
        }
        text.add(new TranslationTextComponent("tooltip.scable.wrench"));
        text.add(new TranslationTextComponent("tooltip.scable.wrench_1"));
    }
}
