package dd.itzhsnu.stackablecable.registrys;

import dd.itzhsnu.stackablecable.blocks.Crusher.CrusherTileEntity;
import dd.itzhsnu.stackablecable.blocks.capacitor.CapacitorTileEntity;
import dd.itzhsnu.stackablecable.blocks.compressor.CompressorTileEntity;
import dd.itzhsnu.stackablecable.blocks.conduits.ConduitTileEntity;
import dd.itzhsnu.stackablecable.blocks.generator.GeneratorTileEntity;
import dd.itzhsnu.stackablecable.blocks.tank.TankTileEntity;
import dd.itzhsnu.stackablecable.scable;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, scable.MOD_ID);

    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_TILE_ENTITY = TILE_ENTITIES
            .register("crusher", () -> TileEntityType.Builder.of(CrusherTileEntity::new, BlocksInit.CRUSHER_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<TankTileEntity>> TANK_TILE_ENTITY = TILE_ENTITIES
            .register("tank", () -> TileEntityType.Builder.of(TankTileEntity::new, BlocksInit.TANK_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<CapacitorTileEntity>> CAPACITOR_TILE_ENTITY = TILE_ENTITIES
            .register("capacitor", () -> TileEntityType.Builder.of(CapacitorTileEntity::new, BlocksInit.CAPACITOR_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<GeneratorTileEntity>> GENERATOR_TILE_ENTITY = TILE_ENTITIES
            .register("generator", () -> TileEntityType.Builder.of(GeneratorTileEntity::new, BlocksInit.GENERATOR_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<CompressorTileEntity>> COMPRESSOR_TILE_ENTITY = TILE_ENTITIES
            .register("compressor", () -> TileEntityType.Builder.of(CompressorTileEntity::new, BlocksInit.COMPRESSOR_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<ConduitTileEntity>> CONDUIT_TILE_ENTITY = TILE_ENTITIES
            .register("conduit", () -> TileEntityType.Builder.of(ConduitTileEntity::new, BlocksInit.CONDUIT_BLOCK.get()).build(null));

}
