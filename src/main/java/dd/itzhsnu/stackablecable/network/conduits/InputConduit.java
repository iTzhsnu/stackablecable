package dd.itzhsnu.stackablecable.network.conduits;

import dd.itzhsnu.stackablecable.blocks.conduits.ConduitBlock;
import dd.itzhsnu.stackablecable.blocks.conduits.ConduitTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class InputConduit {

    public BlockPos pos;
    public int type;
    public byte directionByte;

    public InputConduit() {

    }

    public InputConduit(BlockPos pos, int type, byte directionByte) {
        this.type = type;
        this.pos = pos;
        this.directionByte = directionByte;
    }

    public static void encode(InputConduit input, PacketBuffer buffer) {
        buffer.writeBlockPos(input.pos);
        buffer.writeInt(input.type);
        buffer.writeByte(input.directionByte);
    }

    public static InputConduit decode(PacketBuffer buffer) {
        return new InputConduit(buffer.readBlockPos(), buffer.readInt(), buffer.readByte());
    }

    public static void handle(InputConduit input, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayerEntity player = context.getSender();
            TileEntity te = player.getCommandSenderWorld().getBlockEntity(input.pos);
            if (te instanceof ConduitTileEntity) {
                int io;
                int type = 1;
                if (input.type >= 1 && input.type <= 3) {
                    type = input.type;
                } else if (input.type >= 4 && input.type <= 6) {
                    modifyIO(te, input.directionByte);
                    type = input.type - 3;
                }
                io = ((ConduitTileEntity) te).getIO(input.directionByte);
                ConduitBlock.modeMessage(player, io, type, intFromDirection(input.directionByte));
            }
        });
        context.setPacketHandled(true);
    }

    private static void modifyIO(TileEntity te, int ioPos) {
        if (te instanceof ConduitTileEntity) {
            int ioType = ((ConduitTileEntity) te).getIO(ioPos) + 1;
            if (ioType == 4 || ioType == 8) ioType = ioType + 1;
            if (ioType == 12) ioType = 0;
            ((ConduitTileEntity) te).modifyIO(ioPos, ioType);
        }
    }

    private static Direction intFromDirection(int direction) {
        if (direction == 1 || direction == 7 || direction == 13) {
            //SOUTH (1, 7, 13)
            return Direction.SOUTH;
        } else if (direction == 2 || direction == 8 || direction == 14) {
            //EAST (2, 8, 14)
            return Direction.EAST;
        } else if (direction == 3 || direction == 9 || direction == 15) {
            //WEST (3, 9, 15)
            return Direction.WEST;
        } else if (direction == 4 || direction == 10 || direction == 16) {
            //UP (4, 10, 16)
            return Direction.UP;
        } else if (direction == 5 || direction == 11 || direction == 17) {
            //DOWN (5, 11, 17)
            return Direction.SOUTH;
        } else {
            //NORTH (0, 6, 12)
            return Direction.NORTH;
        }
    }
}
