package net.kozibrodka.ww2.compat.ami;

import net.glasslauncher.mods.alwaysmoreitems.api.*;
import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.recipe.VehicleRecipeRegistry;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.util.Identifier;

public class AMICompat implements ModPluginProvider {

    @Override
    public String getName() {
        return "WW2 Mod";
    }

    @Override
    public Identifier getId() {
        return mod_Vehicles.MOD_ID.id("ww2");
    }

    @Override
    public void onAMIHelpersAvailable(AMIHelpers amiHelpers) {

    }

    @Override
    public void onItemRegistryAvailable(ItemRegistry itemRegistry) {

    }

    @Override
    public void register(ModRegistry registry) {
//        registry.addDescription(new ItemStack(ItemListener.hemp), "description.wolves.hemp");

        registry.addRecipeCategories(new AnvilShapedRecipeCategory());
        registry.addRecipeHandlers(new AnvilShapedRecipeHandler());
        registry.addRecipes(VehicleRecipeRegistry.getInstance().getShapedRecipes());


    }

    @Override
    public void onRecipeRegistryAvailable(RecipeRegistry recipeRegistry) {

    }

    @Override
    public SyncableRecipe deserializeRecipe(NbtCompound recipe) {
        return null;
    }

    @Override
    public void updateBlacklist(AMIHelpers amiHelpers) {

    }
}
