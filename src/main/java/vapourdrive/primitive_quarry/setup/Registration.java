package vapourdrive.primitive_quarry.setup;

import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vapourdrive.primitive_quarry.content.quarry.*;

import static vapourdrive.primitive_quarry.PrimitiveQuarry.MODID;

public class Registration {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
    public static final RegistryObject<QuarryBlock> PRIMITIVE_QUARRY_BLOCK = BLOCKS.register("primitive_quarry", QuarryBlock::new);
    public static final RegistryObject<Item> PRIMITIVE_QUARRY_ITEM = ITEMS.register("primitive_quarry", () -> new QuarryItem(PRIMITIVE_QUARRY_BLOCK.get(), new Item.Properties()));
    @SuppressWarnings("all")
    public static final RegistryObject<BlockEntityType<QuarryTile>> PRIMITIVE_QUARRY_TILE = TILES.register("primitive_quarry", () -> BlockEntityType.Builder.of(QuarryTile::new, PRIMITIVE_QUARRY_BLOCK.get()).build(null));

    public static final RegistryObject<MenuType<QuarryContainer>> PRIMITIVE_QUARRY_CONTAINER = CONTAINERS.register("primitive_quarry", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new QuarryContainer(windowId, world, pos, inv, inv.player, new QuarryData());
    }));


    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        TILES.register(eventBus);
        CONTAINERS.register(eventBus);
    }

    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        // Add to ingredients tab
        if (event.getTabKey() == vapourdrive.vapourware.setup.Registration.VAPOUR_GROUP.getKey()) {
            event.accept(PRIMITIVE_QUARRY_ITEM);
        }
    }
}
