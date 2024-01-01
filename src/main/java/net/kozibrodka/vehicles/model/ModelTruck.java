package net.kozibrodka.vehicles.model;

import net.kozibrodka.tmt.TURBO_MODEL_125.ModelBase;
import net.kozibrodka.tmt.TURBO_MODEL_125.ModelRendererTurbo;
import net.kozibrodka.vehicles.entity.EntityTruck;
import net.kozibrodka.vehicles.entity.EntityVehicle;

public class ModelTruck extends ModelBase {


    public ModelRendererTurbo[] bodyModel = new ModelRendererTurbo[0];
    public ModelRendererTurbo[] leftFrontWheelModel = new ModelRendererTurbo[0];
    public ModelRendererTurbo[] rightFrontWheelModel = new ModelRendererTurbo[0];
    public ModelRendererTurbo[] leftBackWheelModel = new ModelRendererTurbo[0];
    public ModelRendererTurbo[] rightBackWheelModel = new ModelRendererTurbo[0];
    public ModelRendererTurbo[] steeringWheelModel = new ModelRendererTurbo[0];


//    public CollisionBox[] collisionBoxes;


    public void render(float f, float f1, float f2, float f3, float f4, float f5, EntityTruck vehicle) {
        int i;
        for(i = 0; i < this.bodyModel.length; ++i) {
            this.bodyModel[i].render(f5);
        }

        for(i = 0; i < this.leftBackWheelModel.length; ++i) {
            this.leftBackWheelModel[i].render(f5);
        }

        for(i = 0; i < this.rightBackWheelModel.length; ++i) {
            this.rightBackWheelModel[i].render(f5);
        }

        for(i = 0; i < this.leftFrontWheelModel.length; ++i) {
            this.leftFrontWheelModel[i].rotateAngleY = -vehicle.wheelsYaw * (float)Math.PI / 180.0F * 3.0F;
            this.leftFrontWheelModel[i].render(f5);
        }

        for(i = 0; i < this.rightFrontWheelModel.length; ++i) {
            this.rightFrontWheelModel[i].rotateAngleY = -vehicle.wheelsYaw * (float)Math.PI / 180.0F * 3.0F;
            this.rightFrontWheelModel[i].render(f5);
        }

        for(i = 0; i < this.steeringWheelModel.length; ++i) {
            this.steeringWheelModel[i].rotateAngleX = vehicle.wheelsYaw * (float)Math.PI / 180.0F * 3.0F;
            this.steeringWheelModel[i].render(f5);
        }

    }


    public void flipAll() {
        int i;
        for(i = 0; i < this.bodyModel.length; ++i) {
            this.bodyModel[i].doMirror(false, true, true);
            this.bodyModel[i].setRotationPoint(this.bodyModel[i].rotationPointX, -this.bodyModel[i].rotationPointY, -this.bodyModel[i].rotationPointZ);
        }

        for(i = 0; i < this.leftFrontWheelModel.length; ++i) {
            this.leftFrontWheelModel[i].doMirror(false, true, true);
            this.leftFrontWheelModel[i].setRotationPoint(this.leftFrontWheelModel[i].rotationPointX, -this.leftFrontWheelModel[i].rotationPointY, -this.leftFrontWheelModel[i].rotationPointZ);
        }

        for(i = 0; i < this.rightFrontWheelModel.length; ++i) {
            this.rightFrontWheelModel[i].doMirror(false, true, true);
            this.rightFrontWheelModel[i].setRotationPoint(this.rightFrontWheelModel[i].rotationPointX, -this.rightFrontWheelModel[i].rotationPointY, -this.rightFrontWheelModel[i].rotationPointZ);
        }

        for(i = 0; i < this.leftBackWheelModel.length; ++i) {
            this.leftBackWheelModel[i].doMirror(false, true, true);
            this.leftBackWheelModel[i].setRotationPoint(this.leftBackWheelModel[i].rotationPointX, -this.leftBackWheelModel[i].rotationPointY, -this.leftBackWheelModel[i].rotationPointZ);
        }

        for(i = 0; i < this.rightBackWheelModel.length; ++i) {
            this.rightBackWheelModel[i].doMirror(false, true, true);
            this.rightBackWheelModel[i].setRotationPoint(this.rightBackWheelModel[i].rotationPointX, -this.rightBackWheelModel[i].rotationPointY, -this.rightBackWheelModel[i].rotationPointZ);
        }

        for(i = 0; i < this.steeringWheelModel.length; ++i) {
            this.steeringWheelModel[i].doMirror(false, true, true);
            this.steeringWheelModel[i].setRotationPoint(this.steeringWheelModel[i].rotationPointX, -this.steeringWheelModel[i].rotationPointY, -this.steeringWheelModel[i].rotationPointZ);
        }
    }


    public void translateAll(int y) {
        ModelRendererTurbo[] arr$ = this.bodyModel;
        int len$ = arr$.length;

        int i$;
        ModelRendererTurbo mod;
        for(i$ = 0; i$ < len$; ++i$) {
            mod = arr$[i$];
            mod.rotationPointY += (float)y;
        }

        arr$ = this.leftFrontWheelModel;
        len$ = arr$.length;

        for(i$ = 0; i$ < len$; ++i$) {
            mod = arr$[i$];
            mod.rotationPointY += (float)y;
        }

        arr$ = this.rightFrontWheelModel;
        len$ = arr$.length;

        for(i$ = 0; i$ < len$; ++i$) {
            mod = arr$[i$];
            mod.rotationPointY += (float)y;
        }

        arr$ = this.leftBackWheelModel;
        len$ = arr$.length;

        for(i$ = 0; i$ < len$; ++i$) {
            mod = arr$[i$];
            mod.rotationPointY += (float)y;
        }

        arr$ = this.rightBackWheelModel;
        len$ = arr$.length;

        for(i$ = 0; i$ < len$; ++i$) {
            mod = arr$[i$];
            mod.rotationPointY += (float)y;
        }

        arr$ = this.steeringWheelModel;
        len$ = arr$.length;

        for(i$ = 0; i$ < len$; ++i$) {
            mod = arr$[i$];
            mod.rotationPointY += (float)y;
        }

    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
    }
}
