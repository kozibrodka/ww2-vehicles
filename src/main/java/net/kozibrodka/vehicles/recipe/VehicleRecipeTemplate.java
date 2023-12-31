package net.kozibrodka.vehicles.recipe;

import net.minecraft.inventory.Crafting;
import net.minecraft.item.ItemInstance;

public interface VehicleRecipeTemplate {
    boolean canCraft(Crafting arg);

    ItemInstance craft(Crafting arg);

    int getIngredientCount();

    ItemInstance getOutput();
}
