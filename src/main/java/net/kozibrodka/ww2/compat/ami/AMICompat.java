package net.kozibrodka.ww2.compat.ami;

import net.glasslauncher.mods.alwaysmoreitems.api.*;
import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.events.ww2Parts;
import net.kozibrodka.ww2.recipe.VehicleRecipeRegistry;
import net.minecraft.item.ItemStack;
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

        registry.addRecipeCategories(new VehiclelShapedRecipeCategory());
        registry.addRecipeHandlers(new VehicleShapedRecipeHandler());
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
//        amiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(ww2Parts.largeEngine));
        if(!mod_Vehicles.ww2Glass.register_TANK){
            amiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(mod_Vehicles.vehicleClassic_Sherman));
            amiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(mod_Vehicles.vehicleClassic_Panzer));
            /// + części i recepisy
        }
        if(!mod_Vehicles.ww2Glass.register_TRUCK){
            amiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(mod_Vehicles.vehicleClassic_WillysJeep));
            amiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(mod_Vehicles.vehicleClassic_Kubelwagen));
        }
    }
    // TODO poustawiać zaleznosci... + recipe zwykłe też brak.
}
