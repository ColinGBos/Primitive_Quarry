package vapourdrive.primitive_quarry.integrations.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import vapourdrive.primitive_quarry.PrimitiveQuarry;
import vapourdrive.primitive_quarry.content.quarry.QuarryScreen;
import vapourdrive.primitive_quarry.setup.Registration;

@JeiPlugin
public class JEI_Plugin implements IModPlugin {


    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(PrimitiveQuarry.MODID, "jei_plugin");
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(QuarryScreen.class, 142, 5, 15, 15, RecipeTypes.FUELING);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
//        AgriculturalEnhancements.debugLog("Register recipe catalyst");
        registration.addRecipeCatalyst(new ItemStack(Registration.PRIMITIVE_QUARRY_BLOCK.get()), RecipeTypes.FUELING);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(Registration.PRIMITIVE_QUARRY_ITEM.get()), VanillaTypes.ITEM_STACK, Component.translatable("primitivequarry.primitive_quarry.info"));
    }


}
