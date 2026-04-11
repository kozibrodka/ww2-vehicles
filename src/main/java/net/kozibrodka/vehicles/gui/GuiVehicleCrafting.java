package net.kozibrodka.vehicles.gui;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiVehicleCrafting extends HandledScreen {
    private static int blueStart = 0;

    public GuiVehicleCrafting(PlayerInventory inventoryplayer, World world, int i, int j, int k) {
        super(new CraftingInventoryVehicle(inventoryplayer, world, i, j, k));
        this.backgroundHeight = 184;
    }

    public void removed() {
        super.removed();
        this.handler.onClosed(minecraft.player);
    }

    protected void drawForeground() {
        this.textRenderer.draw("Vehicle Crafting", 6, 6, 4210752);
        this.textRenderer.draw("Inventory", 8, this.backgroundHeight - 96 + 2, 4210752);
    }

    protected void drawBackground(float f) {
        int i = minecraft.textureManager.getTextureId("/assets/vehicles/stationapi/textures/gui/vehicleCrafting.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(i);
        int j = (this.width - this.backgroundWidth) / 2;
        int k = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(j, k, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

}
