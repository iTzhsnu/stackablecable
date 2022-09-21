package dd.itzhsnu.stackablecable.blocks.generator;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import dd.itzhsnu.stackablecable.scable;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GeneratorScreen extends ContainerScreen<GeneratorContainer> {
    private final ResourceLocation GENERATOR_GUI = new ResourceLocation(scable.MOD_ID, "textures/gui/generator.png");

    public GeneratorScreen(GeneratorContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);

        this.leftPos = 0;
        this.topPos = 0;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.textureManager.bind(GENERATOR_GUI);
        int x = this.getGuiLeft();
        int y = this.getGuiTop();
        this.blit(matrixStack, x, y, 0, 0, 175, 201);
    }
}
