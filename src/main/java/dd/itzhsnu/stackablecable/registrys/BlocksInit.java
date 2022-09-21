package dd.itzhsnu.stackablecable.registrys;

import dd.itzhsnu.stackablecable.blocks.Crusher.CrusherBlock;
import dd.itzhsnu.stackablecable.blocks.capacitor.CapacitorBlock;
import dd.itzhsnu.stackablecable.blocks.compressor.CompressorBlock;
import dd.itzhsnu.stackablecable.blocks.conduits.ConduitBlock;
import dd.itzhsnu.stackablecable.blocks.generator.GeneratorBlock;
import dd.itzhsnu.stackablecable.blocks.tank.TankBlock;
import dd.itzhsnu.stackablecable.scable;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlocksInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, scable.MOD_ID);

    public static final RegistryObject<Block> TITANIUM_BLOCK = BLOCKS.register("titanium_block", () -> new Block(AbstractBlock.Properties.of(Material.METAL).strength(4F,60F).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final RegistryObject<Block> NIOBIUM_BLOCK = BLOCKS.register("niobium_block", () -> new Block(AbstractBlock.Properties.of(Material.METAL).strength(4F,60F).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final RegistryObject<Block> NIOBIUM_TITANIUM_BLOCK = BLOCKS.register("niobium_titanium_block", () -> new Block(AbstractBlock.Properties.of(Material.METAL).strength(4.5F,70F).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final RegistryObject<Block> TITANIUM_ORE = BLOCKS.register("titanium_ore", () -> new OreBlock(AbstractBlock.Properties.of(Material.STONE).strength(3F,30F).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> NIOBIUM_ORE = BLOCKS.register("niobium_ore", () -> new OreBlock(AbstractBlock.Properties.of(Material.STONE).strength(3F,30F).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> NITROGEN_BLOCK = BLOCKS.register("nitrogen", () -> new FlowingFluidBlock(() -> (FlowingFluid) FluidsInit.STILL_NITROGEN.get(), AbstractBlock.Properties.of(Material.WATER).strength(100F).noDrops()));
    public static final RegistryObject<Block> CRUSHER_BLOCK = BLOCKS.register("crusher", CrusherBlock::new);
    public static final RegistryObject<Block> TANK_BLOCK = BLOCKS.register("tank", TankBlock::new);
    public static final RegistryObject<Block> CAPACITOR_BLOCK = BLOCKS.register("capacitor", CapacitorBlock::new);
    public static final RegistryObject<Block> GENERATOR_BLOCK = BLOCKS.register("generator", GeneratorBlock::new);
    public static final RegistryObject<Block> COMPRESSOR_BLOCK = BLOCKS.register("compressor", CompressorBlock::new);
    public static final RegistryObject<Block> CONDUIT_BLOCK = BLOCKS.register("conduit", ConduitBlock::new);
}
