package net.kozibrodka.vehicles.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;

public interface VehicleRecipeTemplate {
    boolean canCraft(CraftingInventory arg);

    ItemStack craft(CraftingInventory arg);

    int getIngredientCount();

    ItemStack getOutput();
}
