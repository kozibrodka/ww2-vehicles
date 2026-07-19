package net.kozibrodka.ww2.compat.ami;

import net.glasslauncher.mods.alwaysmoreitems.api.gui.AMIDrawable;
import net.glasslauncher.mods.alwaysmoreitems.api.gui.GuiItemStackGroup;
import net.glasslauncher.mods.alwaysmoreitems.api.gui.RecipeLayout;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeCategory;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import net.glasslauncher.mods.alwaysmoreitems.gui.DrawableHelper;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

public class VehiclelShapedRecipeCategory implements RecipeCategory {

    @NotNull
    private final AMIDrawable background = DrawableHelper.createDrawable("/assets/ww2/stationapi/textures/gui/vehicleCrafting_AMI.png", 8, 16, 160, 85);

    @NotNull
    @Override
    public String getUid() {
        return "vehicle_shaped";
    }

    @NotNull
    @Override
    public String getTitle() {
        return "Vehicle Crafting";
    }

    @NotNull
    @Override
    public AMIDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
    }

    @Override
    public void drawAnimations(Minecraft minecraft) {

    }

    @Override
    public void setRecipe(@NotNull RecipeLayout recipeLayout, @NotNull RecipeWrapper recipeWrapper) {
//        GuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
//        int xOffset = 3;
//        int yOffset = 8;
//        for (int i = 0; i < recipeWrapper.getInputs().size(); i++) {
//            guiItemStacks.init(i, true, xOffset + (i % 5) * 18, yOffset + (i / 5) * 18);
//        }
//        for (int i = 0; i < recipeWrapper.getInputs().size(); i++) {
//            guiItemStacks.setFromRecipe(i, recipeWrapper.getInputs().get(i));
//        }
//        guiItemStacks.init(25, false, 130 + xOffset, yOffset + 36);
//        guiItemStacks.setFromRecipe(25, recipeWrapper.getOutputs().get(0));

        ///

        VehicleShapedRecipeWrapper wrapper = (VehicleShapedRecipeWrapper) recipeWrapper;
        GuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(craftOutputSlot, false, 118, 37);
        guiItemStacks.setFromRecipe(craftOutputSlot, recipeWrapper.getOutputs().get(0));

        int xOffset = 23;
        int yOffset = 1;

        for (int y = 0; y < wrapper.recipe.getHeight(); ++y) {
            for (int x = 0; x < wrapper.recipe.getWidth(); ++x) {
                int index = x + (y * wrapper.recipe.getWidth());
                guiItemStacks.init(index + 1, true, x * 18 + xOffset, y * 18 + yOffset);
                guiItemStacks.setFromRecipe(index + 1, recipeWrapper.getInputs().get(index));
            }
        }
    }
    private static final int craftOutputSlot = 0;
}
