package net.kozibrodka.vehicles.recipe;

import net.fabricmc.loader.api.FabricLoader;
import net.kozibrodka.vehicles.events.TextureListener;
import net.kozibrodka.vehicles.gui.GuiVehicleCrafting;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public class BlockVehicleWorkbench extends TemplateBlockBase
{

    public BlockVehicleWorkbench(Identifier i)
    {
        super(i, Material.METAL);
    }

    public boolean canUse(Level world, int i, int j, int k, PlayerBase entityplayer)
    {
        if(world.isServerSide)
        {
            return true;
        } else
        {
            ((Minecraft) FabricLoader.getInstance().getGameInstance()).openScreen(new GuiVehicleCrafting(entityplayer.inventory, world, i, j, k));
            return true;
        }
    }

    public int getTextureForSide(int i)
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
