package vapourdrive.primitive_quarry.content.quarry;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import vapourdrive.vapourware.shared.base.slots.BaseSlotIngredient;

public class QuarrySlotFilter extends BaseSlotIngredient {
    public QuarrySlotFilter(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition, "vapourware.voidfilterslot");
    }

    @Override
    protected boolean isValidIngredient(ItemStack stack) {
        return true;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
