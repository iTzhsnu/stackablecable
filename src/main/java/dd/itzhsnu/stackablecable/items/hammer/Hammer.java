package dd.itzhsnu.stackablecable.items.hammer;

import com.google.common.collect.ImmutableMap;
import dd.itzhsnu.stackablecable.registrys.BlocksInit;
import dd.itzhsnu.stackablecable.registrys.ItemsInit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class Hammer extends Item {
    public Hammer(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack container = itemStack.copy();

        if (container.hurt(3, random, null)) {
            return ItemStack.EMPTY;
        } else {
            return container;
        }
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    private static final Map<Block, Item> HAMMER_CRUSHING_CRAFT =
            new ImmutableMap.Builder<Block, Item>()
                    .put(BlocksInit.NIOBIUM_ORE.get(), ItemsInit.NIOBIUM_DUST.get())
                    .put(BlocksInit.TITANIUM_ORE.get(), ItemsInit.TITANIUM_DUST.get())
                    .put(BlocksInit.TITANIUM_BLOCK.get(), ItemsInit.TITANIUM_DUST.get())
                    .put(BlocksInit.NIOBIUM_BLOCK.get(), ItemsInit.NIOBIUM_DUST.get())
                    .put(BlocksInit.NIOBIUM_TITANIUM_BLOCK.get(), ItemsInit.NIOBIUM_TITANIUM_DUST.get())
                    .put(Blocks.IRON_ORE, ItemsInit.IRON_DUST.get()).put(Blocks.GOLD_ORE, ItemsInit.GOLD_DUST.get())
                    .put(Blocks.IRON_BLOCK, ItemsInit.IRON_DUST.get()).put(Blocks.GOLD_BLOCK, ItemsInit.GOLD_DUST.get())
                    .put(Blocks.ANCIENT_DEBRIS, ItemsInit.NETHERITE_SCRAP_DUST.get()).put(Blocks.NETHERITE_BLOCK, ItemsInit.NETHERITE_DUST.get())
                    .build();

    private static int crushingDropCount(ItemUseContext context) {
        World world = context.getLevel();
        BlockPos blockClickPos = context.getClickedPos();
        Block blockId = world.getBlockState(blockClickPos).getBlock();

        if (blockId == BlocksInit.NIOBIUM_ORE.get() || blockId == BlocksInit.TITANIUM_ORE.get() || blockId == Blocks.IRON_ORE || blockId == Blocks.GOLD_ORE) {
            return 2;
        } else if (blockId == BlocksInit.NIOBIUM_BLOCK.get() || blockId == BlocksInit.TITANIUM_BLOCK.get() || blockId == BlocksInit.NIOBIUM_TITANIUM_BLOCK.get()
                || blockId == Blocks.IRON_BLOCK || blockId == Blocks.GOLD_BLOCK || blockId == Blocks.NETHERITE_BLOCK) {
            return 9;
        } else if (blockId == Blocks.ANCIENT_DEBRIS) {
            return 3;
        } else {
            return 1;
        }
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        if (!context.getLevel().isClientSide()) {
            World world = context.getLevel();
            BlockPos blockClickPos = context.getClickedPos();
            Block blockClicked = world.getBlockState(blockClickPos).getBlock();

            context.getItemInHand().hurtAndBreak(1, context.getPlayer(), p -> p.broadcastBreakEvent(context.getHand()));

            if (canCrushing(blockClicked) && random.nextFloat() > 0.3f) {
                ItemEntity itemEntity = new ItemEntity(world, blockClickPos.getX(), blockClickPos.getY(), blockClickPos.getZ(),
                        new ItemStack(HAMMER_CRUSHING_CRAFT.get(blockClicked), crushingDropCount(context)));

                world.destroyBlock(blockClickPos, false);
                world.addFreshEntity(itemEntity);
            }
            return ActionResultType.SUCCESS;
        }

        return super.useOn(context);
    }

    public boolean canCrushing(Block block) {
        return HAMMER_CRUSHING_CRAFT.containsKey(block);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("tooltip.scable.hammer"));
    }
}
