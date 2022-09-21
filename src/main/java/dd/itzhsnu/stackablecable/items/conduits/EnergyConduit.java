package dd.itzhsnu.stackablecable.items.conduits;

import dd.itzhsnu.stackablecable.blocks.conduits.ConduitBlock;
import dd.itzhsnu.stackablecable.registrys.BlocksInit;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class EnergyConduit extends ConduitBase {
    public EnergyConduit(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        ItemStack heldItemStack = context.getItemInHand();
        if (heldItemStack.getTag() != null) {
            BlockState state = BlocksInit.CONDUIT_BLOCK.get().defaultBlockState().setValue(ConduitBlock.ENERGY, heldItemStack.getTag().getInt("Type"));
            return conduitSet(context, state);
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if (stack.getTag() != null) {
            switch (stack.getTag().getInt("Type")) {
                case 1: tooltip.add(new TranslationTextComponent("tooltip.scable.energy_1k"));
                    break;
                case 2: tooltip.add(new TranslationTextComponent("tooltip.scable.energy_10k"));
                    break;
                case 3: tooltip.add(new TranslationTextComponent("tooltip.scable.energy_50k"));
                    break;
                case 4: tooltip.add(new TranslationTextComponent("tooltip.scable.energy_100k"));
                    break;
                case 5: tooltip.add(new TranslationTextComponent("tooltip.scable.energy_int"));
                    break;
                default: tooltip.add(new TranslationTextComponent("tooltip.scable.energy_unknown"));
                    break;
            }
        } else {
            tooltip.add(new TranslationTextComponent("tooltip.scable.energy_unknown"));
        }
    }
}
