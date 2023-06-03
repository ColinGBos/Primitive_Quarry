package vapourdrive.primitive_quarry.content.quarry;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import vapourdrive.vapourware.shared.base.slots.BaseSlotIngredient;
import vapourdrive.vapourware.shared.utils.DeferredComponent;

public class QuarrySlotFilter extends BaseSlotIngredient {
    public QuarrySlotFilter(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition, new DeferredComponent("voidfilterslot"));
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
