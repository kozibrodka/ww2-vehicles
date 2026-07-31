package net.kozibrodka.ww2.events;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.recipe.CraftingRegistry;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class RecipeListener {

    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();

    @EventListener
    public void registerRecipes(RecipeRegisterEvent event) {
        addPartsRecipes(event);
    }

    public void addPartsRecipes(RecipeRegisterEvent event)
    {
        // TODO, rózne Ifsy ze wglęzdu na opcje + bullet enabled, vehilles itd...
        CraftingRegistry.addShapedRecipe(new ItemStack(ww2Parts.largeWheel), " X ", "X#X", " X ", '#', Item.IRON_INGOT, 'X', Item.LEATHER);
        CraftingRegistry.addShapedRecipe(new ItemStack(ww2Parts.machinegun), "###", "X  ", '#', Item.IRON_INGOT, 'X', Block.PLANKS);
        CraftingRegistry.addShapedRecipe(new ItemStack(mod_Vehicles.tankBullet, 16), "G", "I", 'G', Item.GUNPOWDER, 'I', Item.IRON_INGOT);
        CraftingRegistry.addShapedRecipe(new ItemStack(ww2Parts.piston), "IFI", "I I", 'I', Item.IRON_INGOT, 'F', Item.FLINT_AND_STEEL);
        CraftingRegistry.addShapedRecipe(new ItemStack(ww2Parts.smallEngine), "P P", "PIP", 'P', ww2Parts.piston, 'I', Item.IRON_INGOT);
        CraftingRegistry.addShapedRecipe(new ItemStack(ww2Parts.mediumEngine), "P P", "PIP", "P P", 'P', ww2Parts.piston, 'I', Item.IRON_INGOT);
        CraftingRegistry.addShapedRecipe(new ItemStack(ww2Parts.mediumEngine), "P P", " S ", 'P', ww2Parts.piston, 'S', ww2Parts.smallEngine);
        CraftingRegistry.addShapelessRecipe(new ItemStack(ww2Parts.largeEngine), ww2Parts.smallEngine, ww2Parts.smallEngine);
        CraftingRegistry.addShapedRecipe(new ItemStack(ww2Parts.largeEngine), "P P", " M ", 'P', ww2Parts.piston, 'M', ww2Parts.mediumEngine);
        CraftingRegistry.addShapedRecipe(new ItemStack(ww2Parts.smallCarChassis), "G  ", "III", "III", 'G', Block.GLASS, 'I', Item.IRON_INGOT);
        CraftingRegistry.addShapedRecipe(new ItemStack(ww2Parts.tankBarrel), "  I", "III", "  I", 'I', Item.IRON_INGOT);
        CraftingRegistry.addShapedRecipe(new ItemStack(ww2Parts.mediumTankTurret), "IIB", "II ", 'B', ww2Parts.tankBarrel, 'I', Item.IRON_INGOT);
        CraftingRegistry.addShapedRecipe(new ItemStack(ww2Parts.mediumTankBody), "III", "IRG", "III", 'G', Block.GLASS, 'I', Item.IRON_INGOT, 'R', Item.REDSTONE);
        CraftingRegistry.addShapedRecipe(new ItemStack(ww2Parts.trackPiece, 4), " I ", "III", "I I", 'I', Item.IRON_INGOT);
        CraftingRegistry.addShapedRecipe(new ItemStack(ww2Parts.caterpillarTrack), "III", "IWI", "III", 'W', ww2Parts.largeWheel, 'I', ww2Parts.trackPiece);
        CraftingRegistry.addShapedRecipe(new ItemStack(mod_Vehicles.tankShell), "GGG", "III", 'G', Item.GUNPOWDER, 'I', Item.IRON_INGOT);
    }
}
