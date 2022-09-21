package dd.itzhsnu.stackablecable.registrys;

import dd.itzhsnu.stackablecable.fluids.nitrogen_fluid;
import dd.itzhsnu.stackablecable.scable;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidsInit {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, scable.MOD_ID);

    public static final RegistryObject<Fluid> STILL_NITROGEN = FLUIDS.register("nitrogen_still", nitrogen_fluid.Source::new);
    public static final RegistryObject<Fluid> FLOWING_NITROGEN = FLUIDS.register("nitrogen_flowing", nitrogen_fluid.Flowing::new);
}
