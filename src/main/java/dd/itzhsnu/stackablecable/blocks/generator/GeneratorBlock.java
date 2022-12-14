package dd.itzhsnu.stackablecable.blocks.generator;

import dd.itzhsnu.stackablecable.registrys.TileEntityInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class GeneratorBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public GeneratorBlock() {
        super(Properties.of(Material.METAL).strength(3F, 50F).sound(SoundType.METAL)
                .requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(2));
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityInit.GENERATOR_TILE_ENTITY.get().create();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isClientSide()) {
            TileEntity te = worldIn.getBlockEntity(pos);

            if (te instanceof GeneratorTileEntity) {
                INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);

                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, pos);
            }
        }
        return ActionResultType.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!world.isClientSide()) {
            TileEntity te = world.getBlockEntity(pos);
            if (te instanceof GeneratorTileEntity) {
                te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                    for (int i = 0; i < h.getSlots(); i++) {
                        world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), h.getStackInSlot(i)));
                    }
                });
            }
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }

    private INamedContainerProvider createContainerProvider(World world, BlockPos pos) {
        return new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.scable.generator");
            }

            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory playerInv, PlayerEntity player) {
                return new GeneratorContainer(i, world, pos, playerInv, player);
            }
        };
    }
}
