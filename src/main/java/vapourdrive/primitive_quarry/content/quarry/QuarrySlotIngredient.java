package vapourdrive.primitive_quarry.content.quarry;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import vapourdrive.vapourware.shared.base.slots.BaseSlotIngredient;

public class QuarrySlotIngredient extends BaseSlotIngredient {
    public QuarrySlotIngredient(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition, "vapourware.toolslot");
    }

    @Override
    protected boolean isValidIngredient(ItemStack stack) {
        return stack.getMaxStackSize() == 1;
    }
}
