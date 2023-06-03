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
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;
import vapourdrive.primitive_quarry.PrimitiveQuarry;
import vapourdrive.primitive_quarry.setup.Registration;
import vapourdrive.vapourware.shared.base.AbstractBaseMachineContainer;
import vapourdrive.vapourware.shared.base.slots.SlotFuel;
import vapourdrive.vapourware.shared.base.slots.SlotOutput;
import vapourdrive.vapourware.shared.base.slots.SlotTool;
import vapourdrive.vapourware.shared.utils.CompUtils;

import java.util.Objects;

public class QuarryContainer extends AbstractBaseMachineContainer {
    // gui position of the player inventory grid
    public static final int PLAYER_INVENTORY_XPOS = 8;
    public static final int PLAYER_INVENTORY_YPOS = 127;

    public static final int OUTPUT_INVENTORY_XPOS = 35;
    public static final int OUTPUT_INVENTORY_YPOS = 23;


    public QuarryContainer(int windowId, Level world, BlockPos pos, Inventory inv, Player player, QuarryData machineData) {
        super(windowId, world, pos, inv, player, Registration.PRIMITIVE_QUARRY_CONTAINER.get(), machineData);

        //We use this vs the builtin method because we split all the shorts
        addSplitDataSlots(machineData);

        layoutPlayerInventorySlots(PLAYER_INVENTORY_XPOS, PLAYER_INVENTORY_YPOS);

        if (tileEntity != null) {
            tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(h -> {
                addSlot(new SlotFuel(h, 0, 8, 77));
                addSlot(new SlotTool(h, 1, 8, 102));
                addSlot(new SlotOutput(h, 2, OUTPUT_INVENTORY_XPOS, OUTPUT_INVENTORY_YPOS));
                addSlot(new SlotOutput(h, 3, OUTPUT_INVENTORY_XPOS + 18, OUTPUT_INVENTORY_YPOS));
                addSlot(new SlotOutput(h, 4, OUTPUT_INVENTORY_XPOS + 18 * 2, OUTPUT_INVENTORY_YPOS));
                addSlot(new SlotOutput(h, 5, OUTPUT_INVENTORY_XPOS + 18 * 3, OUTPUT_INVENTORY_YPOS));
                addSlot(new SlotOutput(h, 6, OUTPUT_INVENTORY_XPOS + 18 * 4, OUTPUT_INVENTORY_YPOS));
                addSlot(new SlotOutput(h, 7, OUTPUT_INVENTORY_XPOS + 18 * 5, OUTPUT_INVENTORY_YPOS));
                addSlot(new SlotOutput(h, 8, OUTPUT_INVENTORY_XPOS + 18 * 6, OUTPUT_INVENTORY_YPOS));
                addSlot(new SlotOutput(h, 9, OUTPUT_INVENTORY_XPOS, OUTPUT_INVENTORY_YPOS + 18));
                addSlot(new SlotOutput(h, 10, OUTPUT_INVENTORY_XPOS + 18, OUTPUT_INVENTORY_YPOS + 18));
                addSlot(new SlotOutput(h, 11, OUTPUT_INVENTORY_XPOS + 18 * 2, OUTPUT_INVENTORY_YPOS + 18));
                addSlot(new SlotOutput(h, 12, OUTPUT_INVENTORY_XPOS + 18 * 3, OUTPUT_INVENTORY_YPOS + 18));
                addSlot(new SlotOutput(h, 13, OUTPUT_INVENTORY_XPOS + 18 * 4, OUTPUT_INVENTORY_YPOS + 18));
                addSlot(new SlotOutput(h, 14, OUTPUT_INVENTORY_XPOS + 18 * 5, OUTPUT_INVENTORY_YPOS + 18));
                addSlot(new SlotOutput(h, 15, OUTPUT_INVENTORY_XPOS + 18 * 6, OUTPUT_INVENTORY_YPOS + 18));
                addSlot(new SlotOutput(h, 16, OUTPUT_INVENTORY_XPOS, OUTPUT_INVENTORY_YPOS + 18*2));
                addSlot(new SlotOutput(h, 17, OUTPUT_INVENTORY_XPOS + 18, OUTPUT_INVENTORY_YPOS + 18*2));
                addSlot(new SlotOutput(h, 18, OUTPUT_INVENTORY_XPOS + 18 * 2, OUTPUT_INVENTORY_YPOS + 18*2));
                addSlot(new SlotOutput(h, 19, OUTPUT_INVENTORY_XPOS + 18 * 3, OUTPUT_INVENTORY_YPOS + 18*2));
                addSlot(new SlotOutput(h, 20, OUTPUT_INVENTORY_XPOS + 18 * 4, OUTPUT_INVENTORY_YPOS + 18*2));
                addSlot(new SlotOutput(h, 21, OUTPUT_INVENTORY_XPOS + 18 * 5, OUTPUT_INVENTORY_YPOS + 18*2));
                addSlot(new SlotOutput(h, 22, OUTPUT_INVENTORY_XPOS + 18 * 6, OUTPUT_INVENTORY_YPOS + 18*2));
                addSlot(new SlotOutput(h, 23, OUTPUT_INVENTORY_XPOS, OUTPUT_INVENTORY_YPOS + 18*3));
                addSlot(new SlotOutput(h, 24, OUTPUT_INVENTORY_XPOS + 18, OUTPUT_INVENTORY_YPOS + 18*3));
                addSlot(new SlotOutput(h, 25, OUTPUT_INVENTORY_XPOS + 18 * 2, OUTPUT_INVENTORY_YPOS + 18*3));
                addSlot(new SlotOutput(h, 26, OUTPUT_INVENTORY_XPOS + 18 * 3, OUTPUT_INVENTORY_YPOS + 18*3));
                addSlot(new SlotOutput(h, 27, OUTPUT_INVENTORY_XPOS + 18 * 4, OUTPUT_INVENTORY_YPOS + 18*3));
                addSlot(new SlotOutput(h, 28, OUTPUT_INVENTORY_XPOS + 18 * 5, OUTPUT_INVENTORY_YPOS + 18*3));
                addSlot(new SlotOutput(h, 29, OUTPUT_INVENTORY_XPOS + 18 * 6, OUTPUT_INVENTORY_YPOS + 18*3));
                addSlot(new QuarrySlotFilter(h, 30, OUTPUT_INVENTORY_XPOS, OUTPUT_INVENTORY_YPOS + 79));
                addSlot(new QuarrySlotFilter(h, 31, OUTPUT_INVENTORY_XPOS + 18, OUTPUT_INVENTORY_YPOS + 79));
                addSlot(new QuarrySlotFilter(h, 32, OUTPUT_INVENTORY_XPOS + 18 * 2, OUTPUT_INVENTORY_YPOS + 79));
                addSlot(new QuarrySlotFilter(h, 33, OUTPUT_INVENTORY_XPOS + 18 * 3, OUTPUT_INVENTORY_YPOS + 79));
                addSlot(new QuarrySlotFilter(h, 34, OUTPUT_INVENTORY_XPOS + 18 * 4, OUTPUT_INVENTORY_YPOS + 79));
                addSlot(new QuarrySlotFilter(h, 35, OUTPUT_INVENTORY_XPOS + 18 * 5, OUTPUT_INVENTORY_YPOS + 79));
                addSlot(new QuarrySlotFilter(h, 36, OUTPUT_INVENTORY_XPOS + 18 * 6, OUTPUT_INVENTORY_YPOS + 79));
            });
        }
    }

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
            if (index >= 36 && index <= 72) {
                PrimitiveQuarry.debugLog("From output");
                if (!this.moveItemStackTo(stack, 0, 36, false)) {
                    return ItemStack.EMPTY;
                }
            }

            //Player Inventory
            else if (index <= 35) {
                //Inventory to fuel
                if (ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0.0) {
                    if (!this.moveItemStackTo(stack, 36, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.getMaxStackSize() == 1) {
                    if (!this.moveItemStackTo(stack, 37, 38, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(stack, 66, 73, false)) {
                    return ItemStack.EMPTY;
                }

                //Inventory to hotbar
                if (index <= 26) {
                    PrimitiveQuarry.debugLog("From Player inventory to hotbar");
                    if (!this.moveItemStackTo(stack, 27, 36, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                //Hotbar to inventory
                else {
                    PrimitiveQuarry.debugLog("From Hotbar to inventory");
                    if (!this.moveItemStackTo(stack, 0, 27, false)) {
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
