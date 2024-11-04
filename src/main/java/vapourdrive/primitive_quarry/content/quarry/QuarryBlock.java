package vapourdrive.primitive_quarry.content.quarry;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;
import vapourdrive.vapourware.shared.base.AbstractBaseMachineBlock;

import javax.annotation.Nullable;


public class QuarryBlock extends AbstractBaseMachineBlock {

    public static final MapCodec<QuarryBlock> CODEC = simpleCodec(QuarryBlock::new);

    public QuarryBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASEDRUM), 0.2f);
    }

    public QuarryBlock(Properties properties) {
        super(properties, 0.2f);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new QuarryTile(pos, state);
    }

    @Override
    public @NotNull BlockState rotate(@NotNull BlockState state, LevelAccessor world, @NotNull BlockPos pos, @NotNull Rotation direction) {
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof QuarryTile machine) {
            machine.setWorkingDiameter(0);
            machine.resetCount();
        }
        return rotate(state, direction);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        } else {
            return (level1, pos, state1, tile) -> {
                if (tile instanceof QuarryTile machine) {
                    machine.tickServer(state1);
                }
            };
        }
    }

//    @Override
//    protected void openContainer(Level level, @NotNull BlockPos pos, @NotNull Player player) {
//        BlockEntity blockEntity = level.getBlockEntity(pos);
//        if (blockEntity instanceof QuarryTile machine) {
//            MenuProvider containerProvider = new MenuProvider() {
//                @Override
//                public @NotNull Component getDisplayName() {
//                    return Component.translatable(PrimitiveQuarry.MODID + ".primitive_quarry");
//                }
//
//                @Override
//                public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player playerEntity) {
//                    return new QuarryContainer(windowId, level, pos, playerInventory, playerEntity, machine.getQuarryData());
//                }
//            };
////            NetworkHooks.openScreen((ServerPlayer) player, containerProvider, blockEntity.getBlockPos());
//            player.openMenu(containerProvider);
//        } else {
//            throw new IllegalStateException("Our named container provider is missing!");
//        }
//    }

    @Override
    protected void openContainer(Level level, @NotNull BlockPos pos, @NotNull Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof QuarryTile quarry) {
            player.openMenu((MenuProvider) blockentity, pos);
        }
    }


    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, @NotNull Level world, @NotNull BlockPos blockPos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = world.getBlockEntity(blockPos);
            if (tileEntity instanceof QuarryTile machine) {
                AbstractBaseMachineBlock.dropContents(world, blockPos, machine.getItemHandler(null));
            }
            super.onRemove(state, world, blockPos, newState, isMoving);
        }
    }

    @Override
    public boolean sneakWrenchMachine(Player player, Level level, BlockPos pos) {
        BlockEntity tileEntity = level.getBlockEntity(pos);
        if (tileEntity instanceof QuarryTile machine) {
            machine.setWorkingDiameter(0);
            machine.resetCount();
        }
        return true;
    }

    @Override
    protected @NotNull MapCodec<? extends QuarryBlock> codec() {
        return CODEC;
    }
}
