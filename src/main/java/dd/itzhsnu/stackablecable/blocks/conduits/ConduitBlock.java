package dd.itzhsnu.stackablecable.blocks.conduits;

import dd.itzhsnu.stackablecable.registrys.ItemsInit;
import dd.itzhsnu.stackablecable.registrys.TileEntityInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SixWayBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.UUID;

public class ConduitBlock extends Block {
    //Property
    public static final BooleanProperty ITEM = BooleanProperty.create("item");
    public static final IntegerProperty FLUID = IntegerProperty.create("fluid", 0, 3);
    public static final IntegerProperty ENERGY = IntegerProperty.create("energy", 0, 5);
    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty WEST = SixWayBlock.WEST;
    public static final BooleanProperty UP = SixWayBlock.UP;
    public static final BooleanProperty DOWN = SixWayBlock.DOWN;


    public ConduitBlock() {
        super(Properties.of(Material.WOOL).strength(0.2F, 50F).sound(SoundType.WOOL).noCollission());
        this.registerDefaultState(this.defaultBlockState().setValue(ITEM, false).setValue(FLUID, 0).setValue(ENERGY, 0)
                .setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false)
                .setValue(UP, false).setValue(DOWN, false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        VoxelShape voxelShape = Block.box(6, 6, 6, 10, 10, 10);
        if (state.getValue(DOWN)) voxelShape = VoxelShapes.or(voxelShape, Block.box(6, 1, 6, 10, 6, 10), Block.box(4, 0, 4, 12, 1, 12));
        if (state.getValue(UP)) voxelShape = VoxelShapes.or(voxelShape, Block.box(6, 9, 6, 10, 15, 10), Block.box(4, 15, 4, 12, 16, 12));
        if (state.getValue(NORTH)) voxelShape = VoxelShapes.or(voxelShape, Block.box(6, 6, 1, 10, 10, 6), Block.box(4, 4, 0, 12, 12, 1));
        if (state.getValue(SOUTH)) voxelShape = VoxelShapes.or(voxelShape, Block.box(6, 6, 9, 10, 10, 15), Block.box(4, 4, 15, 12, 12, 16));
        if (state.getValue(WEST)) voxelShape = VoxelShapes.or(voxelShape, Block.box(1, 6, 6, 6, 10, 10), Block.box(0, 4, 4, 1, 12, 12));
        if (state.getValue(EAST)) voxelShape = VoxelShapes.or(voxelShape, Block.box(9, 6, 6, 15, 10, 10), Block.box(15, 4, 4, 16, 12, 12));
        return voxelShape;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ITEM, FLUID, ENERGY, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityInit.CONDUIT_TILE_ENTITY.get().create();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isClientSide()) {
            Item heldItem = player.getItemInHand(hand).getItem();
            ItemStack heldItemStack = player.getItemInHand(hand);
            TileEntity te = world.getBlockEntity(pos);
            boolean item = state.getValue(ITEM);
            int fluid = state.getValue(FLUID);
            int energy = state.getValue(ENERGY);
            if (heldItem == ItemsInit.ITEM_CONDUIT_ITEM.get() && !item) {
                world.setBlock(pos, state.setValue(ITEM, true), 3);
                if (!player.isCreative()) {
                    int count = heldItemStack.getCount() - 1;
                    ItemStack stack = heldItemStack.copy();
                    stack.setCount(count);
                    player.setItemInHand(hand, stack);
                }
            } else if (heldItem == ItemsInit.FLUID_CONDUIT_ITEM.get() && fluid == 0 && heldItemStack.getTag() != null) {
                world.setBlock(pos, state.setValue(FLUID, heldItemStack.getTag().getInt("Type")), 3);
                if (!player.isCreative()) {
                    int count = heldItemStack.getCount() - 1;
                    ItemStack stack = heldItemStack.copy();
                    stack.setCount(count);
                    player.setItemInHand(hand, stack);
                }
            } else if (heldItem == ItemsInit.ENERGY_CONDUIT_ITEM.get() && energy == 0 && heldItemStack.getTag() != null) {
                world.setBlock(pos, state.setValue(ENERGY, heldItemStack.getTag().getInt("Type")), 3);
                if (!player.isCreative()) {
                    int count = heldItemStack.getCount() - 1;
                    ItemStack stack = heldItemStack.copy();
                    stack.setCount(count);
                    player.setItemInHand(hand, stack);
                }
            } else if (heldItem == ItemsInit.WRENCH_ITEM.get() && heldItemStack.getTag() != null && te instanceof ConduitTileEntity) {
                Direction direction = hit.getDirection();
                if (heldItemStack.getTag().get("Opposite") != null && heldItemStack.getTag().getInt("Opposite") > 0) direction = hit.getDirection().getOpposite();
                int ioInt = 0;
                int ioType = 0;
                switch (heldItemStack.getTag().getInt("Mode")) {
                    case 1: ioType = 1; //ITEM
                        switch (((ConduitTileEntity) te).getIO(ConduitTileEntity.getDirectionFromIO(direction, 1))) {
                            case 0: ioInt = 1;
                            break;
                            case 1: ioInt = 2;
                            break;
                            case 2: ioInt = 3;
                            break;
                            case 3: ioInt = 5;
                            break;
                            case 5: ioInt = 6;
                            break;
                            case 6: ioInt = 7;
                            break;
                            case 7: ioInt = 9;
                            break;
                            case 9: ioInt = 10;
                            break;
                            case 10: ioInt = 11;
                            break;
                    }
                    break;
                    case 2: ioType = 2; //FLUID
                        switch (((ConduitTileEntity) te).getIO(ConduitTileEntity.getDirectionFromIO(direction, 2))) {
                            case 0: ioInt = 1;
                                break;
                            case 1: ioInt = 2;
                                break;
                            case 2: ioInt = 3;
                                break;
                            case 3: ioInt = 5;
                                break;
                            case 5: ioInt = 6;
                                break;
                            case 6: ioInt = 7;
                                break;
                            case 7: ioInt = 9;
                                break;
                            case 9: ioInt = 10;
                                break;
                            case 10: ioInt = 11;
                                break;
                        }
                    break;
                    case 3: ioType = 3; //ENERGY
                        switch (((ConduitTileEntity) te).getIO(ConduitTileEntity.getDirectionFromIO(direction, 3))) {
                            case 0: ioInt = 1;
                                break;
                            case 1: ioInt = 2;
                                break;
                            case 2: ioInt = 3;
                                break;
                            case 3: ioInt = 5;
                                break;
                            case 5: ioInt = 6;
                                break;
                            case 6: ioInt = 7;
                                break;
                            case 7: ioInt = 9;
                                break;
                            case 9: ioInt = 10;
                                break;
                            case 10: ioInt = 11;
                                break;
                        }
                    break;
                    case 4: //COPY
                        heldItemStack.getTag().putIntArray("IOList", ((ConduitTileEntity) te).getIoList());
                        player.sendMessage(new TranslationTextComponent("text.scable.wrench_mode_4"), UUID.randomUUID());
                    break;
                    case 5: //PASTE
                        ((ConduitTileEntity) te).setIoList(heldItemStack.getTag().getIntArray("IOList"));
                        player.sendMessage(new TranslationTextComponent("text.scable.wrench_mode_5"), UUID.randomUUID());
                    break;
                    default: player.sendMessage(new TranslationTextComponent("text.scable.wrench_mode_unknown"), UUID.randomUUID());
                    break;
                }
                if (ioType > 0) {
                    ((ConduitTileEntity) te).modifyIO(ConduitTileEntity.getDirectionFromIO(direction, ioType), ioInt);
                    //Message
                    modeMessage(player, ioInt, ioType, direction);
                }
            } else {
                if (te instanceof ConduitTileEntity) {
                    INamedContainerProvider containerProvider = createContainerProvider(world, pos);
                    NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, pos);
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    private INamedContainerProvider createContainerProvider(World world, BlockPos pos) {
        return new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.scable.conduit");
            }

            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory playerInv, PlayerEntity player) {
                return new ConduitContainer(i, world, pos, playerInv, player);
            }
        };
    }

    public static void modeMessage(PlayerEntity player, int io, int type, Direction direction) {
        switch (io) {
            case 0: player.sendMessage(new TranslationTextComponent("text.scable.conduit_0"), UUID.randomUUID());
                break;
            case 1: player.sendMessage(new TranslationTextComponent("text.scable.conduit_1"), UUID.randomUUID());
                break;
            case 2: player.sendMessage(new TranslationTextComponent("text.scable.conduit_2"), UUID.randomUUID());
                break;
            case 3: player.sendMessage(new TranslationTextComponent("text.scable.conduit_3"), UUID.randomUUID());
                break;
            case 5: player.sendMessage(new TranslationTextComponent("text.scable.conduit_4"), UUID.randomUUID());
                break;
            case 6: player.sendMessage(new TranslationTextComponent("text.scable.conduit_5"), UUID.randomUUID());
                break;
            case 7: player.sendMessage(new TranslationTextComponent("text.scable.conduit_6"), UUID.randomUUID());
                break;
            case 9: player.sendMessage(new TranslationTextComponent("text.scable.conduit_7"), UUID.randomUUID());
                break;
            case 10: player.sendMessage(new TranslationTextComponent("text.scable.conduit_8"), UUID.randomUUID());
                break;
            case 11: player.sendMessage(new TranslationTextComponent("text.scable.conduit_9"), UUID.randomUUID());
                break;
        }
        switch (type) {
            case 1: switch (direction) {
                case NORTH: player.sendMessage(new TranslationTextComponent("text.scable.conduit_1_1"), UUID.randomUUID());
                    break;
                case SOUTH: player.sendMessage(new TranslationTextComponent("text.scable.conduit_1_2"), UUID.randomUUID());
                    break;
                case EAST: player.sendMessage(new TranslationTextComponent("text.scable.conduit_1_3"), UUID.randomUUID());
                    break;
                case WEST: player.sendMessage(new TranslationTextComponent("text.scable.conduit_1_4"), UUID.randomUUID());
                    break;
                case UP: player.sendMessage(new TranslationTextComponent("text.scable.conduit_1_5"), UUID.randomUUID());
                    break;
                case DOWN: player.sendMessage(new TranslationTextComponent("text.scable.conduit_1_6"), UUID.randomUUID());
                    break;
            }
                break;
            case 2: switch (direction) {
                case NORTH: player.sendMessage(new TranslationTextComponent("text.scable.conduit_2_1"), UUID.randomUUID());
                    break;
                case SOUTH: player.sendMessage(new TranslationTextComponent("text.scable.conduit_2_2"), UUID.randomUUID());
                    break;
                case EAST: player.sendMessage(new TranslationTextComponent("text.scable.conduit_2_3"), UUID.randomUUID());
                    break;
                case WEST: player.sendMessage(new TranslationTextComponent("text.scable.conduit_2_4"), UUID.randomUUID());
                    break;
                case UP: player.sendMessage(new TranslationTextComponent("text.scable.conduit_2_5"), UUID.randomUUID());
                    break;
                case DOWN: player.sendMessage(new TranslationTextComponent("text.scable.conduit_2_6"), UUID.randomUUID());
                    break;
            }
                break;
            case 3: switch (direction) {
                case NORTH: player.sendMessage(new TranslationTextComponent("text.scable.conduit_3_1"), UUID.randomUUID());
                    break;
                case SOUTH: player.sendMessage(new TranslationTextComponent("text.scable.conduit_3_2"), UUID.randomUUID());
                    break;
                case EAST: player.sendMessage(new TranslationTextComponent("text.scable.conduit_3_3"), UUID.randomUUID());
                    break;
                case WEST: player.sendMessage(new TranslationTextComponent("text.scable.conduit_3_4"), UUID.randomUUID());
                    break;
                case UP: player.sendMessage(new TranslationTextComponent("text.scable.conduit_3_5"), UUID.randomUUID());
                    break;
                case DOWN: player.sendMessage(new TranslationTextComponent("text.scable.conduit_3_6"), UUID.randomUUID());
                    break;
            }
                break;
        }
    }
}
