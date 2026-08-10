package net.kozibrodka.ww2.model_cannon;

import net.kozibrodka.tmt.TURBO_MODEL_125.ModelRendererTurbo;
import net.kozibrodka.ww2.model.ModelCannon;

public class ModelFlakvierling extends ModelCannon
{
    public ModelFlakvierling() {
        (this.baseModel = new ModelRendererTurbo[4])[0] = new ModelRendererTurbo(this, 0, 0, 128, 64);
        this.baseModel[1] = new ModelRendererTurbo(this, 0, 18, 128, 64);
        this.baseModel[2] = new ModelRendererTurbo(this, 0, 18, 128, 64);
        this.baseModel[3] = new ModelRendererTurbo(this, 0, 18, 128, 64);
        this.baseModel[0].addBox(-12.0f, -4.0f, -8.0f, 24, 2, 16, 0.0f);
        this.baseModel[1].addBox(-20.0f, -4.0f, -4.0f, 8, 4, 8, 0.0f);
        this.baseModel[2].addBox(4.0f, -4.0f, 8.0f, 8, 4, 8, 0.0f);
        this.baseModel[3].addBox(4.0f, -4.0f, -16.0f, 8, 4, 8, 0.0f);
        float offY = (float) Math.PI; //300
//        float offY = 0;
        this.baseModel[0].rotateAngleY = offY;
        this.baseModel[1].rotateAngleY = offY;
        this.baseModel[2].rotateAngleY = offY;
        this.baseModel[3].rotateAngleY = offY;
        (this.seatModel = new ModelRendererTurbo[5])[0] = new ModelRendererTurbo(this, 0, 30, 128, 64);
        this.seatModel[1] = new ModelRendererTurbo(this, 96, 0, 128, 64);
        this.seatModel[2] = new ModelRendererTurbo(this, 96, 0, 128, 64);
        this.seatModel[3] = new ModelRendererTurbo(this, 0, 48, 128, 64);
        this.seatModel[4] = new ModelRendererTurbo(this, 0, 48, 128, 64);
        this.seatModel[0].addBox(-8.0f, -6.0f, -8.0f, 16, 2, 16, 0.0f);
        this.seatModel[1].addBox(6.0f, -26.0f, -20.0f, 2, 20, 14, 0.0f);
        this.seatModel[2].addBox(6.0f, -26.0f, 6.0f, 2, 20, 14, 0.0f);
        this.seatModel[3].addBox(-12.0f, -10.0f, 4.0f, 12, 1, 12, 0.0f);
        this.seatModel[4].addBox(-8.0f, -9.0f, 5.0f, 2, 3, 2, 0.0f);
        this.gunModel = new ModelRendererTurbo[1];
        (this.gunModel[0] = new ModelRendererTurbo(this, 48, 18, 128, 64)).addBox(0.0f, 0.0f, 0.0f, 12, 12, 12, 0.0f);
        this.barrelModel = new ModelRendererTurbo[4][1];
        this.barrelModel[0][0] = new ModelRendererTurbo(this, 64, 0, 128, 64);
        this.barrelModel[1][0] = new ModelRendererTurbo(this, 64, 0, 128, 64);
        this.barrelModel[2][0] = new ModelRendererTurbo(this, 64, 0, 128, 64);
        this.barrelModel[3][0] = new ModelRendererTurbo(this, 64, 0, 128, 64);
        this.barrelModel[0][0].addBox(12.0f, 1.0f, 1.0f, 20, 2, 2, 0.0f);
        this.barrelModel[1][0].addBox(12.0f, 9.0f, 1.0f, 20, 2, 2, 0.0f);
        this.barrelModel[2][0].addBox(12.0f, 1.0f, 9.0f, 20, 2, 2, 0.0f);
        this.barrelModel[3][0].addBox(12.0f, 9.0f, 9.0f, 20, 2, 2, 0.0f);
        this.ammoModel = new ModelRendererTurbo[0][0];
        this.barrelX = 0;
        this.barrelY = 16;
        this.barrelZ = 6;
        this.flipAll();
    }
}
