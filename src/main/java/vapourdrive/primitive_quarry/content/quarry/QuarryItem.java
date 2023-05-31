package vapourdrive.primitive_quarry.content.quarry;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import vapourdrive.primitive_quarry.config.ConfigSettings;
import vapourdrive.vapourware.shared.base.BaseMachineItem;

import javax.annotation.Nullable;
import java.util.List;

public class QuarryItem extends BaseMachineItem {
    public QuarryItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.translatable("primitivequarry.primitive_quarry.info", ConfigSettings.PRIMITIVE_QUARRY_MAX_RADIUS.get()).withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, level, list, flag);
    }

}
