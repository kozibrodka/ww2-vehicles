package net.kozibrodka.ww2.model_cannon;

import net.kozibrodka.tmt.TURBO_MODEL_125.ModelRendererTurbo;
import net.kozibrodka.ww2.model.ModelCannon;

public class ModelBofors extends ModelCannon
{
    public ModelBofors() {
        (this.baseModel = new ModelRendererTurbo[5])[0] = new ModelRendererTurbo(this, 0, 0, 128, 64);
        this.baseModel[1] = new ModelRendererTurbo(this, 0, 0, 128, 64);
        this.baseModel[2] = new ModelRendererTurbo(this, 0, 0, 128, 64);
        this.baseModel[3] = new ModelRendererTurbo(this, 0, 0, 128, 64);
        this.baseModel[4] = new ModelRendererTurbo(this, 0, 0, 128, 64);
        this.baseModel[0].addBox(-16.0f, -4.0f, -8.0f, 32, 2, 16, 0.0f);
        this.baseModel[1].addBox(-18.0f, -6.0f, -10.0f, 6, 6, 2, 0.0f);
        this.baseModel[2].addBox(-18.0f, -6.0f, 8.0f, 6, 6, 2, 0.0f);
        this.baseModel[3].addBox(12.0f, -6.0f, -10.0f, 6, 6, 2, 0.0f);
        this.baseModel[4].addBox(12.0f, -6.0f, 8.0f, 6, 6, 2, 0.0f);
        (this.seatModel = new ModelRendererTurbo[2])[0] = new ModelRendererTurbo(this, 0, 18, 128, 64);
        this.seatModel[1] = new ModelRendererTurbo(this, 0, 36, 128, 64);
        this.seatModel[0].addBox(-6.0f, -18.0f, -2.0f, 12, 14, 4, 0.0f);
        this.seatModel[1].addBox(-6.0f, -8.0f, 2.0f, 12, 2, 12, 0.0f);
        this.gunModel = new ModelRendererTurbo[1];
        (this.gunModel[0] = new ModelRendererTurbo(this, 0, 50, 128, 64)).addBox(0.0f, 0.0f, 0.0f, 30, 4, 4, 0.0f);
        this.barrelModel = new ModelRendererTurbo[1][1];
        (this.barrelModel[0][0] = new ModelRendererTurbo(this, 0, 58, 128, 64)).addBox(30.0f, 1.0f, 1.0f, 20, 2, 2, 0.0f);
        this.ammoModel = new ModelRendererTurbo[0][0];
        this.barrelX = -6;
        this.barrelY = 12;
        this.barrelZ = 6;
        this.flipAll();
    }
}
