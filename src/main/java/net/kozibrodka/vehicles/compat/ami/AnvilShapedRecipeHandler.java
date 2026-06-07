package net.kozibrodka.vehicles.compat.ami;

import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeHandler;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import net.kozibrodka.vehicles.recipe.VehicleShapedRecipe;
import org.jetbrains.annotations.NotNull;

public class AnvilShapedRecipeHandler implements RecipeHandler<VehicleShapedRecipe> {
    @NotNull
    @Override
    public Class<VehicleShapedRecipe> getRecipeClass() {
        return VehicleShapedRecipe.class;
    }

    @NotNull
    @Override
    public String getRecipeCategoryUid() {
        return "anvil_shaped";
    }

    @NotNull
    @Override
    public RecipeWrapper getRecipeWrapper(@NotNull VehicleShapedRecipe recipe) {
        return new AnvilShapedRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(@NotNull VehicleShapedRecipe recipe) {
        return true;
    }
}
