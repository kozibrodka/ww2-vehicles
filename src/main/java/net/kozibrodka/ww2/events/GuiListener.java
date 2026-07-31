package net.kozibrodka.ww2.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.ww2.entity.EntityTank;
import net.kozibrodka.ww2.entity.EntityTruck;
import net.kozibrodka.ww2.gui.GuiTruck;
import net.kozibrodka.ww2.gui.GuiTank;
import net.kozibrodka.ww2.gui.GuiVehicleCrafting;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.modificationstation.stationapi.api.client.gui.screen.GuiHandler;
import net.modificationstation.stationapi.api.client.registry.GuiHandlerRegistry;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class GuiListener {

    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerGuiHandlers(GuiHandlerRegistryEvent event) {
        GuiHandlerRegistry registry = event.registry;

        event.register(MOD_ID.id("openTank"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openTank, () -> null));
        event.register(MOD_ID.id("openTruck"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openTruck, () -> null));
        event.register(MOD_ID.id("openCraftingVehicle"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openCraftingVehicle, () -> null));
    }

    @Environment(EnvType.CLIENT)
    public Screen openTank(PlayerEntity player, Inventory inventoryBase) {
        return new GuiTank(player.inventory, (EntityTank) player.vehicle);
    }

    @Environment(EnvType.CLIENT)
    public Screen openTruck(PlayerEntity player, Inventory inventoryBase) {
        return new GuiTruck(player.inventory, (EntityTruck) player.vehicle);
    }

    @Environment(EnvType.CLIENT)
    public Screen openCraftingVehicle(PlayerEntity player, Inventory inventoryBase) {
        return new GuiVehicleCrafting(player.inventory, player.world, craftingX, craftingY, craftingZ);
    }

    public static int craftingX;
    public static int craftingY;
    public static int craftingZ;
}
