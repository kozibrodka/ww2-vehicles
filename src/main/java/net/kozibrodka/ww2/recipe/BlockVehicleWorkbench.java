package net.kozibrodka.ww2.recipe;

import net.fabricmc.loader.api.FabricLoader;
import net.kozibrodka.ww2.events.GuiListener;
import net.kozibrodka.ww2.events.TextureListener;
import net.kozibrodka.ww2.gui.CraftingInventoryVehicle;
import net.kozibrodka.ww2.gui.GuiVehicleCrafting;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;

public class BlockVehicleWorkbench extends TemplateBlock
{

    public BlockVehicleWorkbench(Identifier i)
    {
        super(i, Material.METAL);
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity entityplayer)
    {
        GuiListener.craftingX = x;
        GuiListener.craftingY = y;
        GuiListener.craftingZ = z;
        GuiHelper.openGUI(entityplayer, Identifier.of(GuiListener.MOD_ID, "openCraftingVehicle"), entityplayer.inventory, new CraftingInventoryVehicle(entityplayer.inventory, world, x, y, z));
        return true;
    }

    @Override
    public int getTexture(int i)
    {
        if(i == 1)
        {
            return TextureListener.veh_work_top;
        }else
        if(i == 0)
        {
            return TextureListener.veh_work_side;
        }else
        {
            return TextureListener.veh_work_bottom;
        }
    }

}
