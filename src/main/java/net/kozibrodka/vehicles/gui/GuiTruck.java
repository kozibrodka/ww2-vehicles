package net.kozibrodka.vehicles.gui;

import net.kozibrodka.vehicles.entity.EntityTruck;
import net.kozibrodka.vehicles.entity.EntityVehicle;
import net.minecraft.client.gui.screen.container.ContainerBase;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;

public class GuiTruck extends ContainerBase
{

    public GuiTruck(PlayerInventory inventoryplayer, EntityTruck entityvehicle)
    {
        super(new InventoryTruck(inventoryplayer, entityvehicle));
        vehicle = entityvehicle;
    }

    protected void renderForeground()
    {
        textManager.drawText(vehicle.automobile.longName, 60, 6, 0x404040);
        textManager.drawText("Inventory", 8, (containerHeight - 96) + 2, 0x404040);
        if(vehicle.automobile.numCargoSlots > 0)
        {
            textManager.drawText("Cargo:", 36, 22, 0x404040);
        }
        if(vehicle.automobile.numBulletSlots > 0)
        {
            textManager.drawText("Bullets:", 36, 40, 0x404040);
        }
        if(vehicle.automobile.numShellSlots > 0)
        {
            textManager.drawText("Shells:", 36, 58, 0x404040);
        }
    }

    protected void renderContainerBackground(float f)
    {
        int i = minecraft.textureManager.getTextureId("/assets/vehicles/stationapi/textures/gui/vehicle.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(i);
        int j = (width - containerWidth) / 2;
        int k = (height - containerHeight) / 2;
        blit(j, k, 0, 0, containerWidth, containerHeight);
        if(vehicle.isFuelled())
        {
            int l = vehicle.getBurnTimeRemainingScaled(12);
            blit(j + 8, (k + 36 + 12) - l, 176, 12 - l, 14, l + 2);
        }
        for(int i1 = vehicle.automobile.numCargoSlots; i1 < 5; i1++)
        {
            blit(j + 79 + 18 * i1, k + 17, 190, 0, 18, 18);
        }

        for(int j1 = vehicle.automobile.numBulletSlots; j1 < 5; j1++)
        {
            blit(j + 79 + 18 * j1, k + 35, 190, 0, 18, 18);
        }

        for(int k1 = vehicle.automobile.numShellSlots; k1 < 5; k1++)
        {
            blit(j + 79 + 18 * k1, k + 53, 190, 0, 18, 18);
        }

    }

    private EntityTruck vehicle;
}
