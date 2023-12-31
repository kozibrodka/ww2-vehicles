package net.kozibrodka.vehicles.gui;

import net.minecraft.client.gui.screen.container.ContainerBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.level.Level;
import org.lwjgl.opengl.GL11;

public class GuiVehicleCrafting extends ContainerBase {
    private static int blueStart = 0;

    public GuiVehicleCrafting(PlayerInventory inventoryplayer, Level world, int i, int j, int k) {
        super(new CraftingInventoryVehicle(inventoryplayer, world, i, j, k));
        this.containerHeight = 184;
    }

    public void onClose() {
        super.onClose();
        this.container.onClosed(minecraft.player);
    }

    protected void renderForeground() {
        this.textManager.drawText("Vehicle Crafting", 6, 6, 4210752);
        this.textManager.drawText("Inventory", 8, this.containerHeight - 96 + 2, 4210752);
    }

    protected void renderContainerBackground(float f) {
        int i = minecraft.textureManager.getTextureId("/assets/vehicles/stationapi/textures/gui/vehicleCrafting.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(i);
        int j = (this.width - this.containerWidth) / 2;
        int k = (this.height - this.containerHeight) / 2;
        this.blit(j, k, 0, 0, this.containerWidth, this.containerHeight);
    }

}
