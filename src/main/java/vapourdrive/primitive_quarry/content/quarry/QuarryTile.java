package vapourdrive.primitive_quarry.content.quarry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import vapourdrive.primitive_quarry.PrimitiveQuarry;
import vapourdrive.primitive_quarry.config.ConfigSettings;
import vapourdrive.vapourware.shared.utils.MachineUtils;
import vapourdrive.vapourware.shared.base.AbstractBaseFuelUserTile;
import vapourdrive.vapourware.shared.base.itemhandlers.FuelHandler;
import vapourdrive.vapourware.shared.base.itemhandlers.IngredientHandler;
import vapourdrive.vapourware.shared.base.itemhandlers.OutputHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static vapourdrive.primitive_quarry.setup.Registration.PRIMITIVE_QUARRY_TILE;

public class QuarryTile extends AbstractBaseFuelUserTile {

    public final int[] INGREDIENT_SLOT = {0};
    public final int[] FILTER_SLOT = {0,1,2,3,4,5,6};
    private final FuelHandler fuelHandler = new FuelHandler(this, FUEL_SLOT.length);
    private final IngredientHandler ingredientHandler = new QuarryIngredientHandler(this, INGREDIENT_SLOT.length);
    private final OutputHandler outputHandler = new OutputHandler(this, OUTPUT_SLOTS.length);
    private final IngredientHandler filterHandler = new QuarryFilterHandler(this, FILTER_SLOT.length);
    private final LazyOptional<OutputHandler> lazyOutputHandler = LazyOptional.of(() -> outputHandler);
    private final CombinedInvWrapper combined = new CombinedInvWrapper(fuelHandler, ingredientHandler,outputHandler, filterHandler);
    private final LazyOptional<CombinedInvWrapper> combinedHandler = LazyOptional.of(() -> combined);
    public final QuarryData quarryData = new QuarryData();
    private int mineTimer = 0;

    public QuarryTile(BlockPos pos, BlockState state) {
        super(PRIMITIVE_QUARRY_TILE.get(), pos, state, ConfigSettings.PRIMITIVE_QUARRY_FUEL_STORAGE.get() * 100, ConfigSettings.PRIMITIVE_QUARRY_FUEL_TO_WORK.get(), new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,15,16,17,18,19,20,21,22,23,24,25,26,27});
    }

    public void tickServer(BlockState state) {
        super.tickServer(state);
        mineTimer++;
        if (mineTimer >= ConfigSettings.PRIMITIVE_QUARRY_PROCESS_TIME.get()) {
            doWorkProcesses(state);
            mineTimer = 0;
        }
    }

    private void doWorkProcesses(BlockState state) {
//            AgriculturalEnhancements.debugLog(""+direction);
        assert this.level != null;
        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        if(getDiameter() == 0){
            doDiameterProcess(direction, this.worldPosition, this.level);
            if(getDiameter() == 0){
                return;
            }
        }

        if(!canWork(state)){
            return;
        }

        BlockPos pos = getPosFromCount(direction, this.worldPosition, getCount());

        if(pos.getY()<=-64){
            setWorkingDiameter(-1);
            return;
        }
        BlockState targetState = this.level.getBlockState(pos);
        if(targetState.isAir() || targetState.getDestroySpeed(this.level, pos)<0){
            bumpCount();
            return;
        }
        mineAndProgress(this.level, targetState, pos, true, true);
    }

    private void mineAndProgress(Level level, BlockState targetState, BlockPos pos, Boolean bump, Boolean filter){
        ItemStack tool = getStackInSlot(MachineUtils.Area.INGREDIENT, 0);

        LootContext.Builder builder = (new LootContext.Builder((ServerLevel) level)).withRandom(level.random).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos)).withParameter(LootContextParams.TOOL, tool);

        List<ItemStack> drops = MachineUtils.cleanItemStacks(targetState.getDrops(builder));
        if(filter){
            drops = removeFilteredDrops(drops);
        }

        if (MachineUtils.canPushAllOutputs(drops, this)) {
            for (ItemStack stack : drops) {
                if(!stack.isEmpty()) {
                    MachineUtils.pushOutput(stack, false, this);
                }
            }
            MachineUtils.playSound(level, pos, level.getRandom(), targetState.getSoundType().getBreakSound(), 0f, 0.7f);
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            if(bump) {
                bumpCount();
                consumeFuel(getMinFuelToWork(), false);
                if(level.getRandom().nextFloat()>0.95){
                    PrimitiveQuarry.debugLog("Mining: "+pos+", "+targetState);
                }
            }
        }
    }

    private void doDiameterProcess(Direction direction, BlockPos worldPosition, Level level) {
        int maxRadius = ConfigSettings.PRIMITIVE_QUARRY_MAX_RADIUS.get();
        for(int i = 1; i<=maxRadius; i++){
            BlockPos pos = worldPosition.relative(direction, i);
            BlockState state = level.getBlockState(pos);
            if(state.is(Blocks.REDSTONE_TORCH) || state.is(Blocks.REDSTONE_WALL_TORCH)){
                mineAndProgress(level, state, pos, false, false);
                setWorkingDiameter(Math.max(1, i*2-1));
                return;
            }
        }
    }

    public void setWorkingDiameter(int i) {
        quarryData.set(QuarryData.Data.DIAMETER, i);
    }

    public int getDiameter(){
        return quarryData.get(QuarryData.Data.DIAMETER);
    }

    private List<ItemStack> removeFilteredDrops(List<ItemStack> drops) {
        List<ItemStack> ret = new ArrayList<>();
        for(ItemStack stack:drops) {
            boolean add = true;
            for (int i = 0; i < filterHandler.getSlots(); i++) {
                if (stack.is(filterHandler.getStackInSlot(i).getItem())){
                    add = false;
                }
            }
            if(add){
                ret.add(stack);
            }
        }
        return ret;
    }

    private int getCount(){
        return quarryData.get(QuarryData.Data.COUNT);
    }

    private void bumpCount(){
        quarryData.set(QuarryData.Data.COUNT, getCount()+1);
    }

    public void resetCount() {
        quarryData.set(QuarryData.Data.COUNT, 0);
    }

    private BlockPos getPosFromCount(Direction direction, BlockPos pos, int count){
        int diameter = getDiameter();
        int blocksPerLevel = diameter*diameter;
        int y = count/blocksPerLevel;
        int x = (count%blocksPerLevel)/diameter-((diameter-1)/2);
        int z = (count%diameter)+1;

        return pos.relative(Direction.DOWN, y).relative(direction, z).relative(direction.getClockWise(), x);
    }

    @Override
    public boolean canWork(BlockState state) {
        boolean canWork;
        if(getDiameter()<0){
            canWork = false;
        } else if(Objects.requireNonNull(this.getLevel()).hasNeighborSignal(this.worldPosition)){
            canWork = false;
        } else if (getCurrentFuel() < getMinFuelToWork()) {
            canWork = false;
        } else canWork = !outputHandler.isFull();
        changeStateIfNecessary(state, canWork);
        return canWork;
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        outputHandler.deserializeNBT(tag.getCompound("invOut"));
        fuelHandler.deserializeNBT(tag.getCompound("invFuel"));
        ingredientHandler.deserializeNBT(tag.getCompound("invIngredient"));
        filterHandler.deserializeNBT(tag.getCompound("invFilter"));
        quarryData.set(QuarryData.Data.FUEL, tag.getInt("fuel"));
        quarryData.set(QuarryData.Data.COUNT, tag.getInt("count"));
        quarryData.set(QuarryData.Data.DIAMETER, tag.getInt("diameter"));
        mineTimer = tag.getInt("harvestTimer");
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("invOut", outputHandler.serializeNBT());
        tag.put("invFuel", fuelHandler.serializeNBT());
        tag.put("invIngredient", ingredientHandler.serializeNBT());
        tag.put("invFilter", filterHandler.serializeNBT());
        tag.putInt("fuel", getCurrentFuel());
        tag.putInt("count", getCount());
        tag.putInt("diameter", getDiameter());
        tag.putInt("harvestTimer", mineTimer);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (capability == ForgeCapabilities.ITEM_HANDLER) {
            if (side == Direction.DOWN) {
                return lazyOutputHandler.cast();
            }
            return combinedHandler.cast();
        }
        return super.getCapability(capability, side);
    }

    public IItemHandler getItemHandler() {
        return combined;
    }


    @Override
    public int getCurrentFuel() {
        return quarryData.get(QuarryData.Data.FUEL);
    }

    @Override
    public boolean addFuel(int toAdd, boolean simulate) {
        if (toAdd + getCurrentFuel() > getMaxFuel()) {
            return false;
        }
        if (!simulate) {
            quarryData.set(QuarryData.Data.FUEL, getCurrentFuel() + toAdd);
        }

        return true;
    }

    @Override
    public boolean consumeFuel(int toConsume, boolean simulate) {
        if (getCurrentFuel() < toConsume) {
            return false;
        }
        if (!simulate) {
            quarryData.set(QuarryData.Data.FUEL, getCurrentFuel() - toConsume);
        }
        return true;
    }

    public QuarryData getQuarryData() {
        return quarryData;
    }

    @Override
    public ItemStack getStackInSlot(MachineUtils.Area area, int index) {
        return switch (area) {
            case FUEL -> fuelHandler.getStackInSlot(FUEL_SLOT[index]);
            case OUTPUT -> outputHandler.getStackInSlot(OUTPUT_SLOTS[index]);
            case INGREDIENT -> ingredientHandler.getStackInSlot(INGREDIENT_SLOT[index]);
            case INGREDIENT_2 -> filterHandler.getStackInSlot(FILTER_SLOT[index]);
        };
    }

    @Override
    public void removeFromSlot(MachineUtils.Area area, int index, int amount, boolean simulate) {
        switch (area) {
            case FUEL -> fuelHandler.extractItem(FUEL_SLOT[index], amount, simulate);
            case OUTPUT -> outputHandler.extractItem(OUTPUT_SLOTS[index], amount, simulate);
            case INGREDIENT -> ingredientHandler.extractItem(INGREDIENT_SLOT[index], amount, simulate);
            case INGREDIENT_2 -> filterHandler.extractItem(FILTER_SLOT[index], amount, simulate);
        }
    }

    @Override
    public ItemStack insertToSlot(MachineUtils.Area area, int index, ItemStack stack, boolean simulate) {
        return switch (area) {
            case FUEL -> fuelHandler.insertItem(FUEL_SLOT[index], stack, simulate);
            case OUTPUT -> outputHandler.insertItem(OUTPUT_SLOTS[index], stack, simulate, true);
            case INGREDIENT -> ingredientHandler.insertItem(INGREDIENT_SLOT[index], stack, simulate);
            case INGREDIENT_2 -> filterHandler.insertItem(FILTER_SLOT[index], stack, simulate);
        };
    }
}
