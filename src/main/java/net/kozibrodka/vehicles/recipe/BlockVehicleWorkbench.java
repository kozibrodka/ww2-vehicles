package net.kozibrodka.vehicles.recipe;

import net.fabricmc.loader.api.FabricLoader;
import net.kozibrodka.vehicles.events.TextureListener;
import net.kozibrodka.vehicles.gui.GuiVehicleCrafting;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;

public class BlockVehicleWorkbench extends TemplateBlock
{

    public BlockVehicleWorkbench(Identifier i)
    {
        super(i, Material.METAL);
    }

    public boolean onUse(World world, int i, int j, int k, PlayerEntity entityplayer)
    {
        if(world.isRemote)
        {
            return true;
        } else
        {
            ((Minecraft) FabricLoader.getInstance().getGameInstance()).setScreen(new GuiVehicleCrafting(entityplayer.inventory, world, i, j, k));
            return true;
        }
    }

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
