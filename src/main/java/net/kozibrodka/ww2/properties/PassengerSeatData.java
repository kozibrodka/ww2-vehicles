package net.kozibrodka.ww2.properties;

public class PassengerSeatData {

    public PassengerSeatData(int nr, double x, double y, double z){
        number = nr;
        offSetX = x;
        offSetY = y;
        offSetZ = z;
    }

    public double offSetX;
    public double offSetY;
    public double offSetZ;
    public int number;
}
