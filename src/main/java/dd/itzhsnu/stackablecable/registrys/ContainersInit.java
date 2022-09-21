package dd.itzhsnu.stackablecable.registrys;

import dd.itzhsnu.stackablecable.blocks.Crusher.CrusherContainer;
import dd.itzhsnu.stackablecable.blocks.conduits.ConduitContainer;
import dd.itzhsnu.stackablecable.blocks.generator.GeneratorContainer;
import dd.itzhsnu.stackablecable.scable;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainersInit {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, scable.MOD_ID);

    public static final RegistryObject<ContainerType<CrusherContainer>> CRUSHER_CONTAINER = CONTAINERS
            .register("crusher", () -> IForgeContainerType.create(((windowId, inv, data) -> new CrusherContainer(windowId, inv.player.getCommandSenderWorld(), data.readBlockPos(), inv, inv.player))));
    public static final RegistryObject<ContainerType<GeneratorContainer>> GENERATOR_CONTAINER = CONTAINERS
            .register("generator", () -> IForgeContainerType.create(((windowId, inv, data) -> new GeneratorContainer(windowId, inv.player.getCommandSenderWorld(), data.readBlockPos(), inv, inv.player))));
    public static final RegistryObject<ContainerType<ConduitContainer>> CONDUIT_CONTAINER = CONTAINERS
            .register("conduit", () -> IForgeContainerType.create(((windowId, inv, data) -> new ConduitContainer(windowId, inv.player.getCommandSenderWorld(), data.readBlockPos(), inv, inv.player))));
}
