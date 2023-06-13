package vapourdrive.primitive_quarry.content.quarry;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import vapourdrive.primitive_quarry.PrimitiveQuarry;
import vapourdrive.vapourware.shared.base.AbstractBaseMachineScreen;
import vapourdrive.vapourware.shared.utils.CompUtils;
import vapourdrive.vapourware.shared.utils.DeferredComponent;

import java.util.List;

public class QuarryScreen extends AbstractBaseMachineScreen<QuarryContainer> {
    protected final QuarryContainer machineContainer;
    protected int imgHeight = 209;

    public QuarryScreen(QuarryContainer container, Inventory inv, Component name) {
        super(container, inv, name, new DeferredComponent(PrimitiveQuarry.MODID, "primitive_quarry"), 12, 13, 58, 158, 6, 1, true);
        PrimitiveQuarry.debugLog(comp.getMod());
        this.machineContainer = container;
    }

    @Override
    public int getYSize() {
        return imgHeight;
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);
        graphics.drawString(this.font, machineContainer.getDiameterComponent(), this.titleLabelX + 33,
                this.titleLabelY + 20, 16777215);
    }

    @Override
    protected void getAdditionalInfoHover(List<Component> hoveringText) {
        super.getAdditionalInfoHover(hoveringText);
        hoveringText.add(CompUtils.getComp(comp.getMod(), comp.getTail() + ".wrench").withStyle(ChatFormatting.GOLD));
    }

}
