package vapourdrive.primitive_quarry.content.quarry;

import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import vapourdrive.vapourware.shared.base.AbstractBaseFuelUserTile;
import vapourdrive.vapourware.shared.base.itemhandlers.IngredientHandler;

public class QuarryIngredientHandler extends IngredientHandler {
    public QuarryIngredientHandler(AbstractBaseFuelUserTile tile, int size) {
        super(tile, size);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return stack.getMaxStackSize() == 1;
    }
}
