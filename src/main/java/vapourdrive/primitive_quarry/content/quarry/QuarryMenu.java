package vapourdrive.primitive_quarry.content.quarry;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import vapourdrive.primitive_quarry.PrimitiveQuarry;
import vapourdrive.primitive_quarry.setup.Registration;
import vapourdrive.vapourware.shared.base.AbstractBaseMachineMenu;
import vapourdrive.vapourware.shared.base.slots.SlotFuel;
import vapourdrive.vapourware.shared.base.slots.SlotOutput;
import vapourdrive.vapourware.shared.base.slots.SlotTool;
import vapourdrive.vapourware.shared.utils.CompUtils;

import java.util.Objects;

public class QuarryMenu extends AbstractBaseMachineMenu {
    // gui position of the player inventory grid
    public static final int PLAYER_INVENTORY_XPOS = 8;
    public static final int PLAYER_INVENTORY_YPOS = 127;

    public static final int OUTPUT_INVENTORY_XPOS = 35;
    public static final int OUTPUT_INVENTORY_YPOS = 23;


    public QuarryMenu(int windowId, Level world, BlockPos pos, Inventory inv, Player player, QuarryData machineData) {
        super(windowId, world, pos, inv, player, Registration.PRIMITIVE_QUARRY_CONTAINER.get(), machineData);

//        layoutPlayerInventorySlots(PLAYER_INVENTORY_XPOS, PLAYER_INVENTORY_YPOS);

        if (tileEntity != null && tileEntity instanceof QuarryTile quarryTile) {
            IItemHandler handler = quarryTile.getItemHandler(null);
            addSlot(new SlotFuel(handler, 0, 8, 77));
            addSlot(new SlotTool(handler, 1, 8, 102));
            addSlot(new SlotOutput(handler, 2, OUTPUT_INVENTORY_XPOS, OUTPUT_INVENTORY_YPOS));
            addSlot(new SlotOutput(handler, 3, OUTPUT_INVENTORY_XPOS + 18, OUTPUT_INVENTORY_YPOS));
            addSlot(new SlotOutput(handler, 4, OUTPUT_INVENTORY_XPOS + 18 * 2, OUTPUT_INVENTORY_YPOS));
            addSlot(new SlotOutput(handler, 5, OUTPUT_INVENTORY_XPOS + 18 * 3, OUTPUT_INVENTORY_YPOS));
            addSlot(new SlotOutput(handler, 6, OUTPUT_INVENTORY_XPOS + 18 * 4, OUTPUT_INVENTORY_YPOS));
            addSlot(new SlotOutput(handler, 7, OUTPUT_INVENTORY_XPOS + 18 * 5, OUTPUT_INVENTORY_YPOS));
            addSlot(new SlotOutput(handler, 8, OUTPUT_INVENTORY_XPOS + 18 * 6, OUTPUT_INVENTORY_YPOS));
            addSlot(new SlotOutput(handler, 9, OUTPUT_INVENTORY_XPOS, OUTPUT_INVENTORY_YPOS + 18));
            addSlot(new SlotOutput(handler, 10, OUTPUT_INVENTORY_XPOS + 18, OUTPUT_INVENTORY_YPOS + 18));
            addSlot(new SlotOutput(handler, 11, OUTPUT_INVENTORY_XPOS + 18 * 2, OUTPUT_INVENTORY_YPOS + 18));
            addSlot(new SlotOutput(handler, 12, OUTPUT_INVENTORY_XPOS + 18 * 3, OUTPUT_INVENTORY_YPOS + 18));
            addSlot(new SlotOutput(handler, 13, OUTPUT_INVENTORY_XPOS + 18 * 4, OUTPUT_INVENTORY_YPOS + 18));
            addSlot(new SlotOutput(handler, 14, OUTPUT_INVENTORY_XPOS + 18 * 5, OUTPUT_INVENTORY_YPOS + 18));
            addSlot(new SlotOutput(handler, 15, OUTPUT_INVENTORY_XPOS + 18 * 6, OUTPUT_INVENTORY_YPOS + 18));
            addSlot(new SlotOutput(handler, 16, OUTPUT_INVENTORY_XPOS, OUTPUT_INVENTORY_YPOS + 18 * 2));
            addSlot(new SlotOutput(handler, 17, OUTPUT_INVENTORY_XPOS + 18, OUTPUT_INVENTORY_YPOS + 18 * 2));
            addSlot(new SlotOutput(handler, 18, OUTPUT_INVENTORY_XPOS + 18 * 2, OUTPUT_INVENTORY_YPOS + 18 * 2));
            addSlot(new SlotOutput(handler, 19, OUTPUT_INVENTORY_XPOS + 18 * 3, OUTPUT_INVENTORY_YPOS + 18 * 2));
            addSlot(new SlotOutput(handler, 20, OUTPUT_INVENTORY_XPOS + 18 * 4, OUTPUT_INVENTORY_YPOS + 18 * 2));
            addSlot(new SlotOutput(handler, 21, OUTPUT_INVENTORY_XPOS + 18 * 5, OUTPUT_INVENTORY_YPOS + 18 * 2));
            addSlot(new SlotOutput(handler, 22, OUTPUT_INVENTORY_XPOS + 18 * 6, OUTPUT_INVENTORY_YPOS + 18 * 2));
            addSlot(new SlotOutput(handler, 23, OUTPUT_INVENTORY_XPOS, OUTPUT_INVENTORY_YPOS + 18 * 3));
            addSlot(new SlotOutput(handler, 24, OUTPUT_INVENTORY_XPOS + 18, OUTPUT_INVENTORY_YPOS + 18 * 3));
            addSlot(new SlotOutput(handler, 25, OUTPUT_INVENTORY_XPOS + 18 * 2, OUTPUT_INVENTORY_YPOS + 18 * 3));
            addSlot(new SlotOutput(handler, 26, OUTPUT_INVENTORY_XPOS + 18 * 3, OUTPUT_INVENTORY_YPOS + 18 * 3));
            addSlot(new SlotOutput(handler, 27, OUTPUT_INVENTORY_XPOS + 18 * 4, OUTPUT_INVENTORY_YPOS + 18 * 3));
            addSlot(new SlotOutput(handler, 28, OUTPUT_INVENTORY_XPOS + 18 * 5, OUTPUT_INVENTORY_YPOS + 18 * 3));
            addSlot(new SlotOutput(handler, 29, OUTPUT_INVENTORY_XPOS + 18 * 6, OUTPUT_INVENTORY_YPOS + 18 * 3));
            addSlot(new QuarrySlotFilter(handler, 30, OUTPUT_INVENTORY_XPOS, OUTPUT_INVENTORY_YPOS + 79));
            addSlot(new QuarrySlotFilter(handler, 31, OUTPUT_INVENTORY_XPOS + 18, OUTPUT_INVENTORY_YPOS + 79));
            addSlot(new QuarrySlotFilter(handler, 32, OUTPUT_INVENTORY_XPOS + 18 * 2, OUTPUT_INVENTORY_YPOS + 79));
            addSlot(new QuarrySlotFilter(handler, 33, OUTPUT_INVENTORY_XPOS + 18 * 3, OUTPUT_INVENTORY_YPOS + 79));
            addSlot(new QuarrySlotFilter(handler, 34, OUTPUT_INVENTORY_XPOS + 18 * 4, OUTPUT_INVENTORY_YPOS + 79));
            addSlot(new QuarrySlotFilter(handler, 35, OUTPUT_INVENTORY_XPOS + 18 * 5, OUTPUT_INVENTORY_YPOS + 79));
            addSlot(new QuarrySlotFilter(handler, 36, OUTPUT_INVENTORY_XPOS + 18 * 6, OUTPUT_INVENTORY_YPOS + 79));
        }
        layoutPlayerInventorySlots(PLAYER_INVENTORY_XPOS, PLAYER_INVENTORY_YPOS);
        //We use this vs the builtin method because we split all the shorts
        addSplitDataSlots(machineData);
    }

//    @Override
//    protected void layoutPlayerInventorySlots(int leftCol, int topRow) {
//        int k;
//        for(k = 0; k < 3; ++k) {
//            for(int j = 0; j < 9; ++j) {
//                this.addSlot(new Slot(this.playerInv, j + k * 9 + 9, leftCol + j * 18, topRow + k * 18));
//            }
//        }
//
//        for(k = 0; k < 9; ++k) {
//            this.addSlot(new Slot(this.playerInv, k, leftCol + k * 18, topRow + 58));
//        }
//    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return stillValid(ContainerLevelAccess.create(Objects.requireNonNull(tileEntity.getLevel()), tileEntity.getBlockPos()), playerEntity, Registration.PRIMITIVE_QUARRY_BLOCK.get());
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        PrimitiveQuarry.debugLog("index: " + index);

        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();

            //Furnace outputs to Inventory
            if (index >= 0 && index <= 36) {
                PrimitiveQuarry.debugLog("From output");
                if (!this.moveItemStackTo(stack, 37, 73, false)) {
                    return ItemStack.EMPTY;
                }
            }

            //Player Inventory
            else if (index >= 37) {
                //Inventory to fuel
                if (stack.getBurnTime(RecipeType.SMELTING) > 0.0) {
                    if (!this.moveItemStackTo(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.getMaxStackSize() == 1) {
                    if (!this.moveItemStackTo(stack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                }

                //Inventory to hotbar
                if (index < 64) {
                    PrimitiveQuarry.debugLog("From Player inventory to hotbar");
                    if (!this.moveItemStackTo(stack, 64, 72, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                //Hotbar to inventory
                else {
                    PrimitiveQuarry.debugLog("From Hotbar to inventory");
                    if (!this.moveItemStackTo(stack, 37, 63, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }

    public Component getDiameterComponent() {
        return CompUtils.getArgComp(PrimitiveQuarry.MODID, "primitive_quarry.diameter", machineData.get(2));
    }
}
