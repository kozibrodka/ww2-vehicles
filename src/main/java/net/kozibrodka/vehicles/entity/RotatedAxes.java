package net.kozibrodka.vehicles.entity;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class RotatedAxes {

    private float rotationYaw;
    private float rotationPitch;
    private float rotationRoll;
    private Matrix4f rotationMatrix;

    public RotatedAxes() {
        this.rotationMatrix = new Matrix4f();
    }

    public RotatedAxes(Matrix4f mat) {
        this.rotationMatrix = mat;
        this.convertMatrixToAngles();
    }

    public RotatedAxes(float yaw, float pitch, float roll) {
        this.setAngles(yaw, pitch, roll);
    }

    public void setAngles(float yaw, float pitch, float roll) {
        this.rotationYaw = yaw;
        this.rotationPitch = pitch;
        this.rotationRoll = roll;
        this.convertAnglesToMatrix();
    }

    public float getYaw() {
        return this.rotationYaw;
    }

    public float getPitch() {
        return this.rotationPitch;
    }

    public float getRoll() {
        return this.rotationRoll;
    }

    public Vector3f getXAxis() {
        return new Vector3f(this.rotationMatrix.m00, this.rotationMatrix.m10, this.rotationMatrix.m20);
    }

    public Vector3f getYAxis() {
        return new Vector3f(this.rotationMatrix.m01, this.rotationMatrix.m11, this.rotationMatrix.m21);
    }

    public Vector3f getZAxis() {
        return new Vector3f(this.rotationMatrix.m02, this.rotationMatrix.m12, this.rotationMatrix.m22);
    }

    public void rotateLocalYaw(float rotateBy) {
        this.rotationMatrix.rotate(rotateBy * (float)Math.PI / 180.0F, (new Vector3f(this.rotationMatrix.m01, this.rotationMatrix.m11, this.rotationMatrix.m21)).normalise((Vector3f)null));
        this.convertMatrixToAngles();
    }

    public void rotateLocalPitch(float rotateBy) {
        this.rotationMatrix.rotate(rotateBy * (float)Math.PI / 180.0F, (new Vector3f(this.rotationMatrix.m00, this.rotationMatrix.m10, this.rotationMatrix.m20)).normalise((Vector3f)null));
        this.convertMatrixToAngles();
    }

    public void rotateLocalRoll(float rotateBy) {
        this.rotationMatrix.rotate(rotateBy * (float)Math.PI / 180.0F, (new Vector3f(this.rotationMatrix.m02, this.rotationMatrix.m12, this.rotationMatrix.m22)).normalise((Vector3f)null));
        this.convertMatrixToAngles();
    }

    public void rotateGlobalYaw(float rotateBy) {
        this.rotationMatrix.rotate(rotateBy * (float)Math.PI / 180.0F, new Vector3f(0.0F, 1.0F, 0.0F));
        this.convertMatrixToAngles();
    }

    public void rotateGlobalPitch(float rotateBy) {
        this.rotationMatrix.rotate(rotateBy * (float)Math.PI / 180.0F, new Vector3f(1.0F, 0.0F, 0.0F));
        this.convertMatrixToAngles();
    }

    public void rotateGlobalRoll(float rotateBy) {
        this.rotationMatrix.rotate(rotateBy * (float)Math.PI / 180.0F, new Vector3f(0.0F, 0.0F, 1.0F));
        this.convertMatrixToAngles();
    }

    public Vector3f findGlobalVectorLocally(Vector3f in) {
        Matrix4f mat = new Matrix4f();
        mat.m00 = in.x;
        mat.m10 = in.y;
        mat.m20 = in.z;
        mat.rotate(-this.rotationYaw * (float)Math.PI / 180.0F, new Vector3f(0.0F, 1.0F, 0.0F));
        mat.rotate(-this.rotationPitch * (float)Math.PI / 180.0F, new Vector3f(1.0F, 0.0F, 0.0F));
        mat.rotate(-this.rotationRoll * (float)Math.PI / 180.0F, new Vector3f(0.0F, 0.0F, 1.0F));
        return new Vector3f(mat.m00, mat.m10, mat.m20);
    }

    public Vector3f findLocalVectorGlobally(Vector3f in) {
        Matrix4f mat = new Matrix4f();
        mat.m00 = in.x;
        mat.m10 = in.y;
        mat.m20 = in.z;
        mat.rotate(this.rotationRoll * (float)Math.PI / 180.0F, new Vector3f(0.0F, 0.0F, 1.0F));
        mat.rotate(this.rotationPitch * (float)Math.PI / 180.0F, new Vector3f(1.0F, 0.0F, 0.0F));
        mat.rotate(this.rotationYaw * (float)Math.PI / 180.0F, new Vector3f(0.0F, 1.0F, 0.0F));
        return new Vector3f(mat.m00, mat.m10, mat.m20);
    }

    private void convertAnglesToMatrix() {
        this.rotationMatrix = new Matrix4f();
        this.rotationMatrix.rotate(this.rotationRoll * (float)Math.PI / 180.0F, new Vector3f(0.0F, 0.0F, 1.0F));
        this.rotationMatrix.rotate(this.rotationPitch * (float)Math.PI / 180.0F, new Vector3f(1.0F, 0.0F, 0.0F));
        this.rotationMatrix.rotate(this.rotationYaw * (float)Math.PI / 180.0F, new Vector3f(0.0F, 1.0F, 0.0F));
    }

    private void convertMatrixToAngles() {
        double xx = (double)this.rotationMatrix.m00;
        double xy = (double)this.rotationMatrix.m10;
        double xz = (double)this.rotationMatrix.m20;
        double zx = (double)this.rotationMatrix.m02;
        double zy = (double)this.rotationMatrix.m12;
        double zz = (double)this.rotationMatrix.m22;
        double sqrtX = Math.sqrt(xx * xx + xz * xz);
        sqrtX = sqrtX < 1.0D ? sqrtX : 1.0D;
        double sqrtZ = Math.sqrt(zx * zx + zz * zz);
        sqrtZ = sqrtZ < 1.0D ? sqrtZ : 1.0D;
        this.rotationYaw = (float)Math.atan2(zx, zz) * 180.0F / (float)Math.PI;
        this.rotationPitch = -((float)Math.atan2(zy, sqrtZ)) * 180.0F / (float)Math.PI;
        Matrix4f rollOnlyMatrix = this.rotationMatrix.rotate(this.rotationYaw * (float)Math.PI / 180.0F, new Vector3f(0.0F, 1.0F, 0.0F), (Matrix4f)null).rotate(this.rotationPitch * (float)Math.PI / 180.0F, new Vector3f(1.0F, 0.0F, 0.0F));
        this.rotationRoll = (float)Math.atan2((double)rollOnlyMatrix.m10, (double)rollOnlyMatrix.m00) * 180.0F / (float)Math.PI;
    }
}
