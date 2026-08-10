package net.kozibrodka.ww2.model_cannon;

import net.kozibrodka.tmt.TURBO_MODEL_125.*;
import net.kozibrodka.ww2.model.ModelCannon;


import net.minecraft.client.model.*;

public class ModelPak40 extends ModelCannon
{
    int textureX;
    int textureY;

    public ModelPak40() {
        this.textureX = 1024;
        this.textureY = 1024;
        (this.baseModel = new ModelRendererTurbo[9])[0] = new ModelRendererTurbo((ModelBase)this, 57, 1, this.textureX, this.textureY);
        this.baseModel[1] = new ModelRendererTurbo((ModelBase)this, 113, 1, this.textureX, this.textureY);
        this.baseModel[2] = new ModelRendererTurbo((ModelBase)this, 209, 1, this.textureX, this.textureY);
        this.baseModel[3] = new ModelRendererTurbo((ModelBase)this, 337, 1, this.textureX, this.textureY);
        this.baseModel[4] = new ModelRendererTurbo((ModelBase)this, 969, 1, this.textureX, this.textureY);
        this.baseModel[5] = new ModelRendererTurbo((ModelBase)this, 209, 9, this.textureX, this.textureY);
        this.baseModel[6] = new ModelRendererTurbo((ModelBase)this, 161, 1, this.textureX, this.textureY);
        this.baseModel[0].addBox(0.0f, 0.0f, 0.0f, 4, 4, 44, 0.0f);
        this.baseModel[0].setRotationPoint(-4.0f, -1.0f, -22.0f);
        this.baseModel[1].addBox(0.0f, 0.0f, 0.0f, 6, 8, 6, 0.0f);
        this.baseModel[1].setRotationPoint(-3.0f, -6.0f, -3.0f);
        this.baseModel[2].addBox(0.0f, 0.0f, 0.0f, 58, 2, 5, 0.0f);
        this.baseModel[2].setRotationPoint(-49.0f, 7.0f, 21.0f);
        this.baseModel[2].rotateAngleX = -0.05235988f;
        this.baseModel[2].rotateAngleY = -0.2617994f;
        this.baseModel[2].rotateAngleZ = 0.122173f;
        this.baseModel[3].addBox(0.0f, 0.0f, 0.0f, 58, 2, 5, 0.0f);
        this.baseModel[3].setRotationPoint(-48.0f, 7.0f, -25.0f);
        this.baseModel[3].rotateAngleX = 0.05235988f;
        this.baseModel[3].rotateAngleY = 0.2617994f;
        this.baseModel[3].rotateAngleZ = 0.122173f;
        this.baseModel[4].addBox(0.0f, 0.0f, 0.0f, 13, 4, 7, 0.0f);
        this.baseModel[4].setRotationPoint(-59.0f, 6.0f, 23.0f);
        this.baseModel[4].rotateAngleY = -0.2617994f;
        this.baseModel[5].addBox(0.0f, 0.0f, 0.0f, 13, 4, 7, 0.0f);
        this.baseModel[5].setRotationPoint(-59.0f, 6.0f, -29.0f);
        this.baseModel[5].rotateAngleY = 0.2617994f;
        this.baseModel[6].addBox(0.0f, 0.0f, 0.0f, 1, 11, 40, 0.0f);
        this.baseModel[6].setRotationPoint(6.1f, -4.0f, -20.0f);
        this.baseModel[6].rotateAngleZ = 0.1919862f;

        this.baseModel[7] = new ModelRendererTurbo((ModelBase)this, 1, 1, this.textureX, this.textureY);
        this.baseModel[7].addBox(0.0f, 0.0f, 0.0f, 18, 18, 4, 0.0f);
        this.baseModel[7].setRotationPoint(-12.0f, -8.0f, -25.0f);
        this.baseModel[8] =  new ModelRendererTurbo((ModelBase)this, 49, 1, this.textureX, this.textureY);
        this.baseModel[8].addBox(0.0f, 0.0f, 0.0f, 18, 18, 4, 0.0f);
        this.baseModel[8].setRotationPoint(-12.0f, -8.0f, 21.0f);

//        this.leftTrackModel = new ModelRendererTurbo[1];
//        (this.leftTrackModel[0] = new ModelRendererTurbo((ModelBase)this, 1, 1, this.textureX, this.textureY)).func_78790_a(0.0f, 0.0f, 0.0f, 18, 18, 4, 0.0f);
//        this.leftTrackModel[0].func_78793_a(-12.0f, -8.0f, -25.0f);
//        this.rightTrackModel = new ModelRendererTurbo[1];
//        (this.rightTrackModel[0] = new ModelRendererTurbo((ModelBase)this, 49, 1, this.textureX, this.textureY)).func_78790_a(0.0f, 0.0f, 0.0f, 18, 18, 4, 0.0f);
//        this.rightTrackModel[0].func_78793_a(-12.0f, -8.0f, 21.0f);


        (this.seatModel = new ModelRendererTurbo[7])[0] = new ModelRendererTurbo((ModelBase)this, 145, 1, this.textureX, this.textureY);
        this.seatModel[1] = new ModelRendererTurbo((ModelBase)this, 121, 1, this.textureX, this.textureY);
        this.seatModel[2] = new ModelRendererTurbo((ModelBase)this, 633, 1, this.textureX, this.textureY);
        this.seatModel[3] = new ModelRendererTurbo((ModelBase)this, 673, 1, this.textureX, this.textureY);
        this.seatModel[4] = new ModelRendererTurbo((ModelBase)this, 889, 1, this.textureX, this.textureY);
        this.seatModel[5] = new ModelRendererTurbo((ModelBase)this, 249, 9, this.textureX, this.textureY);
        this.seatModel[6] = new ModelRendererTurbo((ModelBase)this, 281, 9, this.textureX, this.textureY);
        this.seatModel[0].addBox(0.0f, 0.0f, 0.0f, 10, 2, 13, 0.0f);
        this.seatModel[0].setRotationPoint(-5.0f, -8.0f, -6.0f);
        this.seatModel[1].addBox(11.5f, -18.5f, -9.0f, 1, 14, 18, 0.0f);
        this.seatModel[1].setRotationPoint(0.0f, 0.0f, 0.0f);
        this.seatModel[1].rotateAngleZ = 0.296706f;
        this.seatModel[2].addBox(-14.0f, -18.4f, 14.0f, 17, 14, 1, 0.0f);
        this.seatModel[2].setRotationPoint(0.0f, 0.0f, 0.0f);
        this.seatModel[2].rotateAngleX = 0.2094395f;
        this.seatModel[2].rotateAngleY = -0.7853982f;
        this.seatModel[2].rotateAngleZ = 0.2094395f;
        this.seatModel[3].addBox(-14.0f, -18.4f, -15.0f, 17, 14, 1, 0.0f);
        this.seatModel[3].setRotationPoint(0.0f, 0.0f, 0.0f);
        this.seatModel[3].rotateAngleX = -0.2094395f;
        this.seatModel[3].rotateAngleY = 0.7853982f;
        this.seatModel[3].rotateAngleZ = 0.2094395f;
        this.seatModel[4].addBox(-23.0f, -1.0f, 3.0f, 15, 2, 15, 0.0f);
        this.seatModel[4].setRotationPoint(0.0f, 0.0f, 0.0f);
        this.seatModel[5].addBox(-12.0f, -4.0f, 0.0f, 12, 3, 3, 0.0f);
        this.seatModel[5].setRotationPoint(0.0f, 0.0f, 0.0f);
        this.seatModel[5].rotateAngleZ = 0.1745329f;
        this.seatModel[6].addBox(-23.0f, -1.9f, 0.0f, 11, 3, 3, 0.0f);
        this.seatModel[6].setRotationPoint(0.0f, 0.0f, 0.0f);
        this.barrelModel = new ModelRendererTurbo[8][1];
        this.barrelModel[0][0] = new ModelRendererTurbo((ModelBase)this, 465, 1, this.textureX, this.textureY);
        this.barrelModel[1][0] = new ModelRendererTurbo((ModelBase)this, 713, 1, this.textureX, this.textureY);
        this.barrelModel[2][0] = new ModelRendererTurbo((ModelBase)this, 777, 1, this.textureX, this.textureY);
        this.barrelModel[3][0] = new ModelRendererTurbo((ModelBase)this, 801, 1, this.textureX, this.textureY);
        this.barrelModel[4][0] = new ModelRendererTurbo((ModelBase)this, 849, 1, this.textureX, this.textureY);
        this.barrelModel[5][0] = new ModelRendererTurbo((ModelBase)this, 145, 1, this.textureX, this.textureY);
        this.barrelModel[6][0] = new ModelRendererTurbo((ModelBase)this, 937, 1, this.textureX, this.textureY);
        this.barrelModel[7][0] = new ModelRendererTurbo((ModelBase)this, 0, 25, this.textureX, this.textureY);
        this.barrelModel[0][0].addBox(-16.0f, -17.0f, -2.0f, 76, 4, 4, 0.0f);
        this.barrelModel[0][0].setRotationPoint(0.0f, 0.0f, 0.0f);
        this.barrelModel[1][0].addBox(-4.0f, -12.0f, -2.0f, 24, 4, 4, 0.0f);
        this.barrelModel[1][0].setRotationPoint(0.0f, 0.0f, 0.0f);
        this.barrelModel[2][0].addBox(-12.0f, -13.0f, -3.0f, 8, 6, 6, 0.0f);
        this.barrelModel[2][0].setRotationPoint(0.0f, 0.0f, 0.0f);
        this.barrelModel[3][0].addBox(-30.0f, -13.0f, -1.5f, 19, 1, 3, 0.0f);
        this.barrelModel[3][0].setRotationPoint(0.0f, 0.0f, 0.0f);
        this.barrelModel[3][0].rotateAngleZ = 0.01745329f;
        this.barrelModel[4][0].addBox(-22.0f, -16.0f, 4.0f, 25, 5, 1, 0.0f);
        this.barrelModel[4][0].setRotationPoint(0.0f, 0.0f, 0.0f);
        this.barrelModel[5][0].addBox(0.0f, -15.0f, 2.0f, 1, 3, 2, 0.0f);
        this.barrelModel[5][0].setRotationPoint(0.0f, 0.0f, 0.0f);
        this.barrelModel[6][0].addBox(-13.5f, -20.2f, -1.5f, 15, 3, 3, 0.0f);
        this.barrelModel[6][0].setRotationPoint(0.0f, 0.0f, 0.0f);
        this.barrelModel[7][0].addBox(60.0f, -17.5f, -2.5f, 8, 5, 5, 0.0f);
        this.barrelModel[7][0].setRotationPoint(0.0f, 0.0f, 0.0f);

//        this.translateAll(0, 0, 0);
        this.flipAll();
    }
}

