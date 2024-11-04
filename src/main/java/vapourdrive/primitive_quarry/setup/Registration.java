package vapourdrive.primitive_quarry.setup;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import vapourdrive.primitive_quarry.content.quarry.*;

import java.util.function.Supplier;

import static vapourdrive.primitive_quarry.PrimitiveQuarry.MODID;

public class Registration {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, MODID);
    private static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID);
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, MODID);

    public static final Supplier<Block> PRIMITIVE_QUARRY_BLOCK = BLOCKS.register("primitive_quarry", () -> new QuarryBlock());
    public static final Supplier<Item> PRIMITIVE_QUARRY_ITEM = ITEMS.register("primitive_quarry", () -> new QuarryItem(PRIMITIVE_QUARRY_BLOCK.get(), new Item.Properties()));
    @SuppressWarnings("all")
    public static final Supplier<BlockEntityType<QuarryTile>> PRIMITIVE_QUARRY_TILE = TILES.register("primitive_quarry", () -> BlockEntityType.Builder.of(QuarryTile::new, PRIMITIVE_QUARRY_BLOCK.get()).build(null));

    public static final Supplier<MenuType<QuarryMenu>> PRIMITIVE_QUARRY_CONTAINER = MENUS.register("primitive_quarry",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new QuarryMenu(windowId, world, pos, inv, inv.player, new QuarryData());
            }));

    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        TILES.register(eventBus);
        MENUS.register(eventBus);
    }

    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        // Add to ingredients tab
        if (event.getTab() == vapourdrive.vapourware.setup.Registration.VAPOUR_GROUP.get() || event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(PRIMITIVE_QUARRY_ITEM.get().getDefaultInstance());
        }
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, PRIMITIVE_QUARRY_TILE.get(), QuarryTile::getItemHandler);
    }
}
