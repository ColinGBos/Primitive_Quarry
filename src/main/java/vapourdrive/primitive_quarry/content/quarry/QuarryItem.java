package vapourdrive.primitive_quarry.content.quarry;

import net.minecraft.world.level.block.Block;
import vapourdrive.primitive_quarry.PrimitiveQuarry;
import vapourdrive.primitive_quarry.config.ConfigSettings;
import vapourdrive.vapourware.shared.base.BaseMachineItem;
import vapourdrive.vapourware.shared.utils.DeferredComponent;

public class QuarryItem extends BaseMachineItem {
    public QuarryItem(Block block, Properties properties) {
        super(block, properties, new DeferredComponent(PrimitiveQuarry.MODID, "primitive_quarry.info", ConfigSettings.PRIMITIVE_QUARRY_MAX_RADIUS));
    }

}
