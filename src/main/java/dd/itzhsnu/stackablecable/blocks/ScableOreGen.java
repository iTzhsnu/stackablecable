package dd.itzhsnu.stackablecable.blocks;

import dd.itzhsnu.stackablecable.registrys.BlocksInit;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class ScableOreGen {

    public static void oreGen(final BiomeLoadingEvent event) {
        if (event.getCategory().equals(Biome.Category.THEEND)) {
        } else {
            if (event.getCategory().equals(Biome.Category.NETHER)) {
            } else {
                generateOre(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlocksInit.NIOBIUM_ORE.get().defaultBlockState(), 8, 30, 5);
                generateOre(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlocksInit.TITANIUM_ORE.get().defaultBlockState(), 8, 30, 5);
            }

        }
    }

    public static void generateOre(BiomeGenerationSettingsBuilder builder, RuleTest test, BlockState state, int size, int range, int amount) {
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                Feature.ORE.configured(new OreFeatureConfig(test,state,size)).range(range).squared().count(amount));
    }
}
