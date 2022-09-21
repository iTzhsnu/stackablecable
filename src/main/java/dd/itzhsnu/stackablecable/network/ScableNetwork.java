package dd.itzhsnu.stackablecable.network;

import dd.itzhsnu.stackablecable.network.conduits.InputConduit;
import dd.itzhsnu.stackablecable.scable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ScableNetwork {

    public static final String NETWORK_VERSION = "0.1.0";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(scable.MOD_ID, "network"), () -> NETWORK_VERSION
            , version -> version.equals(NETWORK_VERSION), version -> version.equals(NETWORK_VERSION));

    public static void init() {
        CHANNEL.registerMessage(0, InputConduit.class, InputConduit::encode, InputConduit::decode, InputConduit::handle);
    }

}
