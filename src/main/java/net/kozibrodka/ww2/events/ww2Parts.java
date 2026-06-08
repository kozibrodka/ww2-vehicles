package net.kozibrodka.ww2.events;

import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class ww2Parts {
    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();

//    @EventListener(priority = ListenerPriority.HIGHEST)
    @EventListener
    public void registerItems(ItemRegistryEvent event) {
//    public static void registerWW2Parts() {
        if(FabricLoader.getInstance().isModLoaded("planes")) {
            biplaneWing = new TemplateItem(Identifier.of(MOD_ID, "biplaneWing")).setTranslationKey(MOD_ID, "biplaneWing").setMaxCount(8);
            woodenTail =  new TemplateItem(Identifier.of(MOD_ID, "woodenTail")).setTranslationKey(MOD_ID, "woodenTail").setMaxCount(4);
            woodenPropeller =  new TemplateItem(Identifier.of(MOD_ID, "woodenPropeller")).setTranslationKey(MOD_ID, "woodenPropeller").setMaxCount(4);
            triplaneWing =  new TemplateItem(Identifier.of(MOD_ID, "triplaneWing")).setTranslationKey(MOD_ID, "triplaneWing").setMaxCount(8);
            woodenCockpit =  new TemplateItem(Identifier.of(MOD_ID, "woodenCockpit")).setTranslationKey(MOD_ID, "woodenCockpit").setMaxCount(4);
            wheel =  new TemplateItem(Identifier.of(MOD_ID, "wheel")).setTranslationKey(MOD_ID, "wheel").setMaxCount(12);
            metalWingMG =  new TemplateItem(Identifier.of(MOD_ID, "metalWingMG")).setTranslationKey(MOD_ID, "metalWingMG").setMaxCount(8);
            metalTail =  new TemplateItem(Identifier.of(MOD_ID, "metalTail")).setTranslationKey(MOD_ID, "metalTail").setMaxCount(4);
            bombBay =  new TemplateItem(Identifier.of(MOD_ID, "bombBay")).setTranslationKey(MOD_ID, "bombBay").setMaxCount(4);
            metalNose =  new TemplateItem(Identifier.of(MOD_ID, "metalNose")).setTranslationKey(MOD_ID, "metalNose").setMaxCount(4);
            metalWing =  new TemplateItem(Identifier.of(MOD_ID, "metalWing")).setTranslationKey(MOD_ID, "metalWing").setMaxCount(8);
            metalWingSection =  new TemplateItem(Identifier.of(MOD_ID, "metalWingSection")).setTranslationKey(MOD_ID, "metalWingSection").setMaxCount(8);
            metalCockpit =  new TemplateItem(Identifier.of(MOD_ID, "metalCockpit")).setTranslationKey(MOD_ID, "metalCockpit").setMaxCount(4);
            advancedMetalCockpit =  new TemplateItem(Identifier.of(MOD_ID, "advancedMetalCockpit")).setTranslationKey(MOD_ID, "advancedMetalCockpit").setMaxCount(2);
            passengerBay =  new TemplateItem(Identifier.of(MOD_ID, "passengerBay")).setTranslationKey(MOD_ID, "passengerBay").setMaxCount(4);
            metalPropeller =  new TemplateItem(Identifier.of(MOD_ID, "metalPropeller")).setTranslationKey(MOD_ID, "metalPropeller").setMaxCount(4);
            metalWingpPropv4 =  new TemplateItem(Identifier.of(MOD_ID, "metalWingpPropv4")).setTranslationKey(MOD_ID, "metalWingpPropv4").setMaxCount(8);
            metalWingpPropv6 =  new TemplateItem(Identifier.of(MOD_ID, "metalWingpPropv6")).setTranslationKey(MOD_ID, "metalWingpPropv6").setMaxCount(8);
            metalWingpPropv8 =  new TemplateItem(Identifier.of(MOD_ID, "metalWingpPropv8")).setTranslationKey(MOD_ID, "metalWingpPropv8").setMaxCount(8);
            metalWingpPropRot =  new TemplateItem(Identifier.of(MOD_ID, "metalWingpPropRot")).setTranslationKey(MOD_ID, "metalWingpPropRot").setMaxCount(8);
            vehicleSeat =  new TemplateItem(Identifier.of(MOD_ID, "vehicleSeat")).setTranslationKey(MOD_ID, "vehicleSeat").setMaxCount(8);
        }
        if(true || FabricLoader.getInstance().isModLoaded("planes")) {
            smallEngine =  new TemplateItem(Identifier.of(MOD_ID, "smallEngine")).setTranslationKey(MOD_ID, "smallEngine").setMaxCount(4);
            mediumEngine =  new TemplateItem(Identifier.of(MOD_ID, "mediumEngine")).setTranslationKey(MOD_ID, "mediumEngine").setMaxCount(4);
            largeEngine =  new TemplateItem(Identifier.of(MOD_ID, "largeEngine")).setTranslationKey(MOD_ID, "largeEngine").setMaxCount(4);
            rotaryEngine =  new TemplateItem(Identifier.of(MOD_ID, "rotaryEngine")).setTranslationKey(MOD_ID, "rotaryEngine").setMaxCount(4);
            piston =  new TemplateItem(Identifier.of(MOD_ID, "piston")).setTranslationKey(MOD_ID, "piston").setMaxCount(16);
            machinegun =  new TemplateItem(Identifier.of(MOD_ID, "machinegun")).setTranslationKey(MOD_ID, "machinegun").setMaxCount(8);
            symbolGerman =  new TemplateItem(Identifier.of(MOD_ID, "symbolGerman")).setTranslationKey(MOD_ID, "symbolGerman");
            symbolAmerican =  new TemplateItem(Identifier.of(MOD_ID, "symbolAmerican")).setTranslationKey(MOD_ID, "symbolAmerican");
            symbolBritish =  new TemplateItem(Identifier.of(MOD_ID, "symbolBritish")).setTranslationKey(MOD_ID, "symbolBritish");
            symbolRussian =  new TemplateItem(Identifier.of(MOD_ID, "symbolRussian")).setTranslationKey(MOD_ID, "symbolRussian");
        }
        if(true) {
            redstoneCoil =  new TemplateItem(Identifier.of(MOD_ID, "redstoneCoil")).setTranslationKey(MOD_ID, "redstoneCoil").setMaxCount(16);
            denseredRedstoneCoil =  new TemplateItem(Identifier.of(MOD_ID, "denseredRedstoneCoil")).setTranslationKey(MOD_ID, "denseredRedstoneCoil").setMaxCount(16);
            largeWheel =  new TemplateItem(Identifier.of(MOD_ID, "largeWheel")).setTranslationKey(MOD_ID, "largeWheel").setMaxCount(4);
            smallCarChassis =  new TemplateItem(Identifier.of(MOD_ID, "smallCarChassis")).setTranslationKey(MOD_ID, "smallCarChassis").setMaxCount(4);
            lightTankBody =  new TemplateItem(Identifier.of(MOD_ID, "lightTankBody")).setTranslationKey(MOD_ID, "lightTankBody").setMaxCount(4);
            mediumTankBody =  new TemplateItem(Identifier.of(MOD_ID, "mediumTankBody")).setTranslationKey(MOD_ID, "mediumTankBody").setMaxCount(4);
            heavyTankBody =  new TemplateItem(Identifier.of(MOD_ID, "heavyTankBody")).setTranslationKey(MOD_ID, "heavyTankBody").setMaxCount(4);
            advancedTankBody =  new TemplateItem(Identifier.of(MOD_ID, "advancedTankBody")).setTranslationKey(MOD_ID, "advancedTankBody").setMaxCount(4);
            tankBarrel =  new TemplateItem(Identifier.of(MOD_ID, "tankBarrel")).setTranslationKey(MOD_ID, "tankBarrel").setMaxCount(4);
            gunShield =  new TemplateItem(Identifier.of(MOD_ID, "gunShield")).setTranslationKey(MOD_ID, "gunShield").setMaxCount(4);
            lightTankTurret =  new TemplateItem(Identifier.of(MOD_ID, "lightTankTurret")).setTranslationKey(MOD_ID, "lightTankTurret").setMaxCount(4);
            mediumTankTurret =  new TemplateItem(Identifier.of(MOD_ID, "mediumTankTurret")).setTranslationKey(MOD_ID, "mediumTankTurret").setMaxCount(4);
            heavyTankTurret =  new TemplateItem(Identifier.of(MOD_ID, "heavyTankTurret")).setTranslationKey(MOD_ID, "heavyTankTurret").setMaxCount(4);
            trackPiece =  new TemplateItem(Identifier.of(MOD_ID, "trackPiece")).setTranslationKey(MOD_ID, "trackPiece").setMaxCount(32);
            towBar =  new TemplateItem(Identifier.of(MOD_ID, "towBar")).setTranslationKey(MOD_ID, "towBar").setMaxCount(4);
            caterpillarTrack =  new TemplateItem(Identifier.of(MOD_ID, "caterpillarTrack")).setTranslationKey(MOD_ID, "caterpillarTrack").setMaxCount(8);
        }
    }

    public static Item biplaneWing;
    public static Item woodenTail;
    public static Item woodenPropeller;
    public static Item triplaneWing;
    public static Item woodenCockpit;
    public static Item wheel;
    public static Item metalWingMG;
    public static Item metalTail;
    public static Item bombBay;
    public static Item machinegun;
    public static Item metalWing;
    public static Item metalWingSection;
    public static Item metalNose;
    public static Item metalCockpit;
    public static Item advancedMetalCockpit;
    public static Item smallEngine;
    public static Item mediumEngine;
    public static Item largeEngine;
    public static Item rotaryEngine;
    public static Item piston;
    public static Item passengerBay;
    public static Item metalPropeller;
    public static Item metalWingpPropv4;
    public static Item metalWingpPropv6;
    public static Item metalWingpPropv8;
    public static Item metalWingpPropRot;
    public static Item vehicleSeat;
    public static Item redstoneCoil;
    public static Item denseredRedstoneCoil;
    public static Item largeWheel;
    public static Item smallCarChassis;
    public static Item lightTankBody;
    public static Item mediumTankBody;
    public static Item heavyTankBody;
    public static Item advancedTankBody;
    public static Item tankBarrel;
    public static Item gunShield;
    public static Item lightTankTurret;
    public static Item mediumTankTurret;
    public static Item heavyTankTurret;
    public static Item trackPiece;
    public static Item towBar;
    public static Item caterpillarTrack;
    public static Item symbolGerman;
    public static Item symbolAmerican;
    public static Item symbolBritish;
    public static Item symbolRussian;
}
