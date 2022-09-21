package dd.itzhsnu.stackablecable.items.conduits;

import dd.itzhsnu.stackablecable.blocks.conduits.ConduitBlock;
import dd.itzhsnu.stackablecable.registrys.BlocksInit;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class ItemConduit extends ConduitBase {
    public ItemConduit(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        BlockState state = BlocksInit.CONDUIT_BLOCK.get().defaultBlockState().setValue(ConduitBlock.ITEM, true);
        return conduitSet(context, state);
    }
}
