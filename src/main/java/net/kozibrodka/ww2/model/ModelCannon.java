package net.kozibrodka.ww2.model;

import net.kozibrodka.ww2.entity.EntityCannon;
import net.kozibrodka.tmt.TURBO_MODEL_125.ModelBase;
import net.kozibrodka.tmt.TURBO_MODEL_125.ModelRendererTurbo;

public class ModelCannon extends ModelBase {
    public ModelRendererTurbo[] baseModel;
    public ModelRendererTurbo[] seatModel;
    public ModelRendererTurbo[] gunModel;
    public ModelRendererTurbo[][] barrelModel;
    public ModelRendererTurbo[][] ammoModel;
    public int barrelX;
    public int barrelY;
    public int barrelZ;

    public void renderBase(float f, float f1, float f2, float f3, float f4, float f5, EntityCannon aa) {
        for(int i = 0; i < this.baseModel.length; ++i) {
            this.baseModel[i].render(f5);
        }

    }

    public void renderGun(float f, float f1, float f2, float f3, float f4, float f5, EntityCannon aa) {
        int i;
        for(i = 0; i < this.seatModel.length; ++i) {
            this.seatModel[i].render(f5);
        }

        for(i = 0; i < this.gunModel.length; ++i) {
            this.gunModel[i].setPosition((float)this.barrelX, (float)this.barrelY, (float)this.barrelZ);
            this.gunModel[i].rotateAngleZ = -aa.gunPitch / 180.0F * (float)Math.PI;
            this.gunModel[i].render(f5);
        }

        int j;
        for(i = 0; i < this.barrelModel.length; ++i) {
            for(j = 0; j < this.barrelModel[i].length; ++j) {
                this.barrelModel[i][j].setPosition(-aa.barrelRecoil[i] * (float)Math.cos((double)(-aa.gunPitch * (float)Math.PI / 180.0F)) + (float)this.barrelX, -aa.barrelRecoil[i] * (float)Math.sin((double)(-aa.gunPitch * (float)Math.PI / 180.0F)) + (float)this.barrelY, (float)this.barrelZ);
                this.barrelModel[i][j].rotateAngleZ = -aa.gunPitch / 180.0F * (float)Math.PI;
                this.barrelModel[i][j].render(f5);
            }
        }

        for(i = 0; i < this.ammoModel.length; ++i) {
                for(j = 0; j < this.ammoModel[i].length; ++j) {
                    this.ammoModel[i][j].setPosition((float)this.barrelX, (float)this.barrelY, (float)this.barrelZ);
                    this.ammoModel[i][j].rotateAngleZ = -aa.gunPitch / 180.0F * (float)Math.PI;
                    this.ammoModel[i][j].render(f5);
                }
        }

    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
    }

    public void flipAll() {
        int i;
        for(i = 0; i < this.baseModel.length; ++i) {
            this.baseModel[i].doMirror(false, true, true);
            this.baseModel[i].setRotationPoint(this.baseModel[i].rotationPointX, -this.baseModel[i].rotationPointY, -this.baseModel[i].rotationPointZ);
        }

        for(i = 0; i < this.seatModel.length; ++i) {
            this.seatModel[i].doMirror(false, true, true);
            this.seatModel[i].setRotationPoint(this.seatModel[i].rotationPointX, -this.seatModel[i].rotationPointY, -this.seatModel[i].rotationPointZ);
        }

        for(i = 0; i < this.gunModel.length; ++i) {
            this.gunModel[i].doMirror(false, true, true);
            this.gunModel[i].setRotationPoint(this.gunModel[i].rotationPointX, -this.gunModel[i].rotationPointY, -this.gunModel[i].rotationPointZ);
        }

        int j;
        for(i = 0; i < this.barrelModel.length; ++i) {
            for(j = 0; j < this.barrelModel[i].length; ++j) {
                this.barrelModel[i][j].doMirror(false, true, true);
                this.barrelModel[i][j].setRotationPoint(this.barrelModel[i][j].rotationPointX, -this.barrelModel[i][j].rotationPointY, -this.barrelModel[i][j].rotationPointZ);
            }
        }

        for(i = 0; i < this.ammoModel.length; ++i) {
            for(j = 0; j < this.ammoModel[i].length; ++j) {
                this.ammoModel[i][j].doMirror(false, true, true);
                this.ammoModel[i][j].setRotationPoint(this.ammoModel[i][j].rotationPointX, -this.ammoModel[i][j].rotationPointY, -this.ammoModel[i][j].rotationPointZ);
            }
        }

    }
}
