package net.kozibrodka.vehicles.model;
import net.kozibrodka.tmt.TURBO_MODEL_125.*;

public class ModelJeep_Classic extends ModelTruck{

    public ModelJeep_Classic(){
        char c = '\200';
        char c1 = '\200';
        bodyModel = new ModelRendererTurbo[12];
        bodyModel[0] = new ModelRendererTurbo(this, 0, 0, c, c1);
        bodyModel[1] = new ModelRendererTurbo(this,0, 94, c, c1);
        bodyModel[2] = new ModelRendererTurbo(this,0, 40, c, c1);
        bodyModel[3] = new ModelRendererTurbo(this,0, 40, c, c1);
        bodyModel[4] = new ModelRendererTurbo(this,60, 12, c, c1);
        bodyModel[5] = new ModelRendererTurbo(this,0, 43, c, c1);
        bodyModel[6] = new ModelRendererTurbo(this,60, 0, c, c1);
        bodyModel[7] = new ModelRendererTurbo(this,60, 0, c, c1);
        bodyModel[8] = new ModelRendererTurbo(this,32, 49, c, c1);
        bodyModel[9] = new ModelRendererTurbo(this,90, 28, c, c1);
        bodyModel[10] = new ModelRendererTurbo(this,90, 22, c, c1);
        bodyModel[11] = new ModelRendererTurbo(this,90, 22, c, c1);
        bodyModel[0].addBox(8F, -16F, -14F, 16, 12, 28, 0.0F);
        bodyModel[1].addBox(-24F, -6F, -16F, 32, 2, 32, 0.0F);
        bodyModel[2].addBox(8F, -10F, -16F, 16, 1, 2, 0.0F);
        bodyModel[3].addBox(8F, -10F, 14F, 16, 1, 2, 0.0F);
        bodyModel[4].addBox(-6F, -14F, -14F, 1, 8, 28, 0.0F);
        bodyModel[5].addBox(-24F, -16F, -14F, 4, 10, 28, 0.0F);
        bodyModel[6].addBox(-24F, -16F, -16F, 32, 10, 2, 0.0F);
        bodyModel[7].addBox(-24F, -16F, -16F, 32, 10, 2, 0.0F);
        bodyModel[7].doMirror(false, false, true);
        bodyModel[8].addBox(8F, -24F, -16F, 1, 8, 32, 0.0F);
        bodyModel[9].addBox(-4F, -4F, -1F, 8, 8, 2, 0.0F);
        bodyModel[9].rotateAngleY = 1.570796F;
        bodyModel[9].setRotationPoint(-25F, -12F, 0.0F);
        bodyModel[10].addBox(-20F, -4F, -15F, 8, 4, 2, 0.0F);
        bodyModel[11].addBox(-20F, -4F, 13F, 8, 4, 2, 0.0F);

//        this.leftFrontWheelModel = new ModelRendererTurbo[1];
//        this.rightFrontWheelModel = new ModelRendererTurbo[1];
//        leftFrontWheelModel[0] = new ModelRendererTurbo(this, 90, 28, c, c1);
//        rightFrontWheelModel[0] = new ModelRendererTurbo(this,90, 28, c, c1);
//        leftFrontWheelModel[0].addBox(-4F, -4F, -1F, 8, 8, 2, 0.0F);
//        leftFrontWheelModel[0].setRotationPoint(16F, -4F, 15F);
//        rightFrontWheelModel[0].addBox(-4F, -4F, -1F, 8, 8, 2, 0.0F);
//        rightFrontWheelModel[0].setRotationPoint(16F, -4F, -15F);

        this.leftFrontWheelModel = new ModelRendererTurbo[1];
        this.rightFrontWheelModel = new ModelRendererTurbo[1];
        leftFrontWheelModel[0] = new ModelRendererTurbo(this, 90, 28, c, c1);
        rightFrontWheelModel[0] = new ModelRendererTurbo(this,90, 28, c, c1);
        leftFrontWheelModel[0].addBox(-4F, -4F, -1F, 8, 8, 2, 0.0F);
        leftFrontWheelModel[0].setRotationPoint(16F, -4F, 15F);
        rightFrontWheelModel[0].addBox(-4F, -4F, -1F, 8, 8, 2, 0.0F);
        rightFrontWheelModel[0].setRotationPoint(16F, -4F, -15F);

        this.flipAll();
    }
}
