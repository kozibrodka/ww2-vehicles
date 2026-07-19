package net.kozibrodka.ww2.compat.ami;

import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import net.kozibrodka.ww2.recipe.VehicleShapedRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VehicleShapedRecipeWrapper implements RecipeWrapper {
    public final VehicleShapedRecipe recipe;

    public VehicleShapedRecipeWrapper(VehicleShapedRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public List<?> getInputs() {
        /// SUPER WYNALAZEK - EZZZ
        return new ArrayList<>(Arrays.asList(recipe.getIngredients()));
    }

    @Override
    public List<?> getOutputs() {
        return List.of(recipe.getOutput());
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

    }

    @Override
    public void drawAnimations(@NotNull Minecraft minecraft, int recipeWidth, int recipeHeight) {

    }

    @Nullable
    @Override
    public ArrayList<Object> getTooltip(int mouseX, int mouseY) {
        return null;
    }

    @Override
    public boolean handleClick(@NotNull Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}
