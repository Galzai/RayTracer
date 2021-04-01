package RayTracer.geometry;

import RayTracer.graphics.Intersection;
import RayTracer.graphics.Material;
import RayTracer.graphics.Ray;
import RayTracer.math.MathUtils;
import RayTracer.math.Matrix3D;
import RayTracer.math.Vector;
import RayTracer.math.Vector3D;

import java.util.Random;

public class OrientedBox implements Surface{
    private Vector3D[] Axis;
    private Vector3D center;
    private Vector3D scales;
    private Material material;


    public OrientedBox(Vector3D center, Vector3D scales, Vector3D rotations, Material material) {
        this.center = center;
        this.scales = scales;
        this.material = material;
        Matrix3D xRotation = Matrix3D.createXRotationMatrix(rotations.get(0));
        Matrix3D yRotation = Matrix3D.createYRotationMatrix(rotations.get(1));
        Matrix3D zRotation = Matrix3D.createZRotationMatrix(rotations.get(2));
        this.Axis = new Vector3D[3];
        this.Axis[0] = zRotation.vecMult(yRotation.vecMult(Matrix3D.xAxis));
        this.Axis[1] =  zRotation.vecMult(yRotation.vecMult(xRotation.vecMult(Matrix3D.yAxis)));
        this.Axis[2] = zRotation.vecMult(yRotation.vecMult(xRotation.vecMult(Matrix3D.zAxis)));
    }

    @Override
    public Intersection findIntersection(Ray ray) {
        double tNear = Double.NEGATIVE_INFINITY;
        double tFar = Double.POSITIVE_INFINITY;
        Vector3D co = center.subtract(ray.origin());
        double r, s, t0, t1;

        for (int i = 0; i < 3; i++) {
            r = this.Axis[i].dotProduct(co);
            s = this.Axis[i].dotProduct(ray.direction());
            t0 = (r + scales.get(i) / 2) / s;
            t1 = (r - scales.get(i) / 2) / s;
            if (t0 > t1) {  // swap
                double tmp = t0;
                t0 = t1;
                t1 = tmp;
            }
            tNear = Math.max(tNear,t0);
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
            if (Math.abs(pc.dotProduct(this.Axis[i]) - this.scales.get(i) / 2) <= MathUtils.EPSILON) {
                normal = this.Axis[i];
                break;
            }
            if (Math.abs(pc.dotProduct(this.Axis[i]) + this.scales.get(i) / 2) <= MathUtils.EPSILON) {
                normal = this.Axis[i].scalarMult(-1.0);
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
