package RayTracer.geometry;

import RayTracer.graphics.Intersection;
import RayTracer.graphics.Material;
import RayTracer.graphics.Ray;
import RayTracer.math.MathUtils;
import RayTracer.math.Matrix3D;
import RayTracer.math.Vector3D;

public class OrientedBox implements Surface {
    private Vector3D[] axis;
    private Vector3D center;
    private Vector3D halfScales;
    private Material material;


    public OrientedBox(Vector3D center, Vector3D scales, Vector3D rotations, Material material) {
        this.center = center;
        this.halfScales = scales.scalarMult(0.5);
        this.material = material;
        Matrix3D xRotation = Matrix3D.createXRotationMatrix(rotations.getFirst());
        Matrix3D yRotation = Matrix3D.createYRotationMatrix(rotations.getSecond());
        Matrix3D zRotation = Matrix3D.createZRotationMatrix(rotations.getThird());
        this.axis = new Vector3D[3];
        this.axis[0] = zRotation.vecMult(yRotation.vecMult(Matrix3D.xAxis));
        this.axis[1] = zRotation.vecMult(yRotation.vecMult(xRotation.vecMult(Matrix3D.yAxis)));
        this.axis[2] = zRotation.vecMult(yRotation.vecMult(xRotation.vecMult(Matrix3D.zAxis)));
    }

    @Override
    public Intersection findIntersection(Ray ray) {
        double tNear = Double.NEGATIVE_INFINITY;
        double tFar = Double.POSITIVE_INFINITY;
        Vector3D co = center.subtract(ray.origin());
        double r, s, t0, t1;

        for (int i = 0; i < 3; i++) {

            r = this.axis[i].dotProduct(co);
            s = this.axis[i].dotProduct(ray.direction());
            if (Math.abs(s) < MathUtils.EPSILON) {
                t0 = r + this.halfScales.get(i) > 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
                t1 = r - this.halfScales.get(i) > 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
            }
            else {
                t0 = (r + this.halfScales.get(i)) / s;
                t1 = (r - this.halfScales.get(i)) / s;
            }
            if (t0 > t1) {  // swap
                double tmp = t0;
                t0 = t1;
                t1 = tmp;
            }
            tNear = Math.max(tNear, t0);
            tFar = Math.min(tFar, t1);
            if (tNear > tFar || tFar < 0) {  // no intersection
                return null;
            }
        }
        if (tNear < 0) {
            return null; // opposite direction
        }
        Vector3D intersectionPoint = ray.at(tNear);
        Vector3D pc = intersectionPoint.subtract(this.center);
        Vector3D normal = null;

        for (int i = 0; i < 3; i++) {
            if (Math.abs(pc.dotProduct(this.axis[i]) - this.halfScales.get(i)) <= MathUtils.EPSILON) {
                normal = this.axis[i];
                break;
            }
            if (Math.abs(pc.dotProduct(this.axis[i]) + this.halfScales.get(i)) <= MathUtils.EPSILON) {
                normal = this.axis[i].scalarMult(-1.0);
                break;
            }
        }
        return new Intersection(intersectionPoint, normal, this, tNear);
    }


    @Override
    public Material getMaterial() {
        return material;
    }
}
