package dd.itzhsnu.stackablecable.blocks.conduits;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import dd.itzhsnu.stackablecable.network.ScableNetwork;
import dd.itzhsnu.stackablecable.network.conduits.InputConduit;
import dd.itzhsnu.stackablecable.scable;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ConduitScreen extends ContainerScreen<ConduitContainer> {
    private static final ResourceLocation GUI = new ResourceLocation(scable.MOD_ID, "textures/gui/conduit.png");
    private final ConduitContainer container;
    private Direction buttonDirection = Direction.NORTH;

    public ConduitScreen(ConduitContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
        this.leftPos = 0;
        this.topPos = 0;
    }

    @Override
    protected void init() {
        super.init();
        //NORTH DATA BUTTON
        this.addButton(new ImageButton(this.leftPos + 25, this.height / 2 - 79, 20, 18, 0, 0, 19, new ResourceLocation("scable:textures/gui/conduit_button_north.png"), 256, 256
                , onPress -> buttonDirection = Direction.NORTH, (button, stack, x, y) -> this.renderTooltip(stack, new TranslationTextComponent("tooltip.scable.button_north"), x, y), new TranslationTextComponent("tooltip.scable.button_north")));
        //SOUTH DATA BUTTON
        this.addButton(new ImageButton(this.leftPos + 45, this.height / 2 - 79, 20, 18, 0, 0, 19, new ResourceLocation("scable:textures/gui/conduit_button_south.png"), 256, 256
                , onPress -> buttonDirection = Direction.SOUTH, (button, stack, x, y) -> this.renderTooltip(stack, new TranslationTextComponent("tooltip.scable.button_south"), x, y), new TranslationTextComponent("tooltip.scable.button_south")));
        //EAST DATA BUTTON
        this.addButton(new ImageButton(this.leftPos + 65, this.height / 2 - 79, 20, 18, 0, 0, 19, new ResourceLocation("scable:textures/gui/conduit_button_east.png"), 256, 256
                , onPress -> buttonDirection = Direction.EAST, (button, stack, x, y) -> this.renderTooltip(stack, new TranslationTextComponent("tooltip.scable.button_east"), x, y), new TranslationTextComponent("tooltip.scable.button_east")));
        //WEST DATA BUTTON
        this.addButton(new ImageButton(this.leftPos + 85, this.height / 2 - 79, 20, 18, 0, 0, 19, new ResourceLocation("scable:textures/gui/conduit_button_west.png"), 256, 256
                , onPress -> buttonDirection = Direction.WEST, (button, stack, x, y) -> this.renderTooltip(stack, new TranslationTextComponent("tooltip.scable.button_west"), x, y), new TranslationTextComponent("tooltip.scable.button_west")));
        //UP DATA BUTTON
        this.addButton(new ImageButton(this.leftPos + 105, this.height / 2 - 79, 20, 18, 0, 0, 19, new ResourceLocation("scable:textures/gui/conduit_button_up.png"), 256, 256
                , onPress -> buttonDirection = Direction.UP, (button, stack, x, y) -> this.renderTooltip(stack, new TranslationTextComponent("tooltip.scable.button_up"), x, y), new TranslationTextComponent("tooltip.scable.button_up")));
        //DOWN DATA BUTTON
        this.addButton(new ImageButton(this.leftPos + 125, this.height / 2 - 79, 20, 18, 0, 0, 19, new ResourceLocation("scable:textures/gui/conduit_button_down.png"), 256, 256
                , onPress -> buttonDirection = Direction.DOWN, (button, stack, x, y) -> this.renderTooltip(stack, new TranslationTextComponent("tooltip.scable.button_down"), x, y), new TranslationTextComponent("tooltip.scable.button_down")));
        //MODIFY I/O BUTTON
        this.addButton(new ImageButton(this.leftPos + 85, this.height / 2 - 19, 20, 18, 0, 0, 19, new ResourceLocation("scable:textures/gui/conduit_button_item.png"), 256, 256
                , onPress -> ScableNetwork.CHANNEL.sendToServer(new InputConduit(container.te.getBlockPos(), 4, (byte) ConduitTileEntity.getDirectionFromIO(buttonDirection, 1))), (button, stack, x, y) -> this.renderTooltip(stack, new TranslationTextComponent("tooltip.scable.button_item_change"), x, y), new TranslationTextComponent("tooltip.scable.button_item_change")));
        this.addButton(new ImageButton(this.leftPos + 105, this.height / 2 - 19, 20, 18, 0, 0, 19, new ResourceLocation("scable:textures/gui/conduit_button_fluid.png"), 256, 256
                , onPress -> ScableNetwork.CHANNEL.sendToServer(new InputConduit(container.te.getBlockPos(), 5, (byte) ConduitTileEntity.getDirectionFromIO(buttonDirection, 2))), (button, stack, x, y) -> this.renderTooltip(stack, new TranslationTextComponent("tooltip.scable.button_fluid_change"), x, y), new TranslationTextComponent("tooltip.scable.button_fluid_change")));
        this.addButton(new ImageButton(this.leftPos + 125, this.height / 2 - 19, 20, 18, 0, 0, 19, new ResourceLocation("scable:textures/gui/conduit_button_energy.png"), 256, 256
                , onPress -> ScableNetwork.CHANNEL.sendToServer(new InputConduit(container.te.getBlockPos(), 6, (byte) ConduitTileEntity.getDirectionFromIO(buttonDirection, 3))), (button, stack, x, y) -> this.renderTooltip(stack, new TranslationTextComponent("tooltip.scable.button_energy_change"), x, y), new TranslationTextComponent("tooltip.scable.button_energy_change")));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int x, int y) {
        this.font.draw(matrixStack, this.inventory.getDisplayName(), this.inventoryLabelX, this.inventoryLabelY, 4210752);
        //this.font.draw(matrixStack, new TranslationTextComponent("screen.scable.item"), this.titleLabelX, this.titleLabelY + 27, 4210752);
        //this.font.draw(matrixStack, new TranslationTextComponent("screen.scable.fluid"), this.titleLabelX, this.titleLabelY + 37, 4210752);
        //this.font.draw(matrixStack, new TranslationTextComponent("screen.scable.energy"), this.titleLabelX, this.titleLabelY + 47, 4210752);
        switch (buttonDirection) {
            case NORTH: this.font.draw(matrixStack, new TranslationTextComponent("screen.scable.north"), this.titleLabelX, this.titleLabelY + 17, 4210752);
            break;
            case SOUTH: this.font.draw(matrixStack, new TranslationTextComponent("screen.scable.south"), this.titleLabelX, this.titleLabelY + 17, 4210752);
            break;
            case EAST: this.font.draw(matrixStack, new TranslationTextComponent("screen.scable.east"), this.titleLabelX, this.titleLabelY + 17, 4210752);
            break;
            case WEST: this.font.draw(matrixStack, new TranslationTextComponent("screen.scable.west"), this.titleLabelX, this.titleLabelY + 17, 4210752);
            break;
            case UP: this.font.draw(matrixStack, new TranslationTextComponent("screen.scable.up"), this.titleLabelX, this.titleLabelY + 17, 4210752);
            break;
            case DOWN: this.font.draw(matrixStack, new TranslationTextComponent("screen.scable.down"), this.titleLabelX, this.titleLabelY + 17, 4210752);
            break;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.textureManager.bind(GUI);
        int x = this.getGuiLeft();
        int y = this.getGuiTop();
        this.blit(matrixStack, x, y, 0, 0, 175, 201);
    }
}
