package net.kozibrodka.vehicles.model;

import net.kozibrodka.tmt.TURBO_MODEL_125.*;

public class ModelSherman_Classic extends ModelVehicle {
    public ModelSherman_Classic(){
        short c = 256;
        short c1 = 256;

        bodyModel = new ModelRendererTurbo[2];
        bodyModel[0] = new ModelRendererTurbo(this,0, 0, c, c1);
        bodyModel[1] = new ModelRendererTurbo(this,0, 68, c, c1);
        bodyModel[0].addBox(-40F, -20F, -24F, 80, 20, 48, 0.0F);
        bodyModel[1].addTrapezoid(-40F, -36F, -24F, 80, 16, 48, 0.0F, -4F, 4);
        turretModel = new ModelRendererTurbo[1];
        turretModel[0] = new ModelRendererTurbo(this,0, 132, c, c1);
        turretModel[0].addTrapezoid(-16F, -48F, -16F, 32, 12, 32, 0.0F, -2F, 4);
        barrelModel = new ModelRendererTurbo[1];
        barrelModel[0] = new ModelRendererTurbo(this,0, 176, c, c1);
        barrelModel[0].addBox(-2F, -2F, -2F, 60, 4, 4, 0.0F);
        barrelModel[0].setRotationPoint(15F, -42F, 0.0F);

        this.flipAll();
    }
}
