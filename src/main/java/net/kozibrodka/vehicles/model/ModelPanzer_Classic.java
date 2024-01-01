package net.kozibrodka.vehicles.model;

import net.kozibrodka.tmt.TURBO_MODEL_125.*;
public class ModelPanzer_Classic extends ModelVehicle {
    public ModelPanzer_Classic(){
        short c = 256;
        short c1 = 256;

        bodyModel = new ModelRendererTurbo[4];
        bodyModel[0] = new ModelRendererTurbo(this,0, 0, c, c1);
        bodyModel[1] = new ModelRendererTurbo(this,0, 68, c, c1);
        bodyModel[2] = new ModelRendererTurbo(this,0, 184, c, c1);
        bodyModel[3] = new ModelRendererTurbo(this,0, 184, c, c1);
        bodyModel[0].addBox(-40F, -20F, -24F, 80, 20, 48, 0.0F);
        bodyModel[1].addTrapezoid(-40F, -32F, -24F, 72, 12, 48, 0.0F, -2F, 4);
        bodyModel[2].addBox(-36F, -32F, -26F, 72, 20, 2, 0.0F);
        bodyModel[3].addBox(-36F, -32F, -26F, 72, 20, 2, 0.0F);
        bodyModel[3].rotateAngleY = 3.141593F;
        turretModel = new ModelRendererTurbo[1];
        turretModel[0] = new ModelRendererTurbo(this,0, 132, c, c1);
        turretModel[0].addTrapezoid(-16F, -44F, -16F, 32, 12, 32, 0.0F, -2F, 4);
        barrelModel = new ModelRendererTurbo[1];
        barrelModel[0] = new ModelRendererTurbo(this,0, 176, c, c1);
        barrelModel[0].addBox(-2F, -2F, -2F, 60, 4, 4, 0.0F);
        barrelModel[0].setRotationPoint(15F, -38F, 0.0F);

        this.flipAll();
    }
}
