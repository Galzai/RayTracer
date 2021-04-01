package RayTracer.geometry;

import RayTracer.graphics.Intersection;
import RayTracer.graphics.Material;
import RayTracer.graphics.Ray;
import RayTracer.math.MathUtils;
import RayTracer.math.Matrix3D;
import RayTracer.math.Vector3D;

import java.util.HashMap;


public class AxisAlignedBox implements Surface {
    private Vector3D center;
    private Vector3D halfScales;
    private Material material;


    public AxisAlignedBox(Vector3D center, Vector3D scales, Material material) {
        this.material = material;
        this.center = center;
        this.halfScales = scales.scalarMult(0.5);
    }

    @Override
    public Intersection findIntersection(Ray ray) {
        double tNear = Double.NEGATIVE_INFINITY;
        double tFar = Double.POSITIVE_INFINITY;
        Vector3D co = center.subtract(ray.origin());
        double r, s, t0, t1;
        Vector3D axis[] = {Matrix3D.xAxis, Matrix3D.yAxis, Matrix3D.zAxis};

        for (int i = 0; i < 3; i++) {
            r = co.get(i);
            s = ray.direction().get(i);
            t0 = (r + this.halfScales.get(i)) / s;
            t1 = (r - this.halfScales.get(i)) / s;
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
            if (Math.abs(pc.get(i) - this.halfScales.get(i)) <= MathUtils.EPSILON) {
                normal = axis[i];
                break;
            }
            if (Math.abs(pc.dotProduct(axis[i]) + this.halfScales.get(i)) <= MathUtils.EPSILON) {
                normal = axis[i].scalarMult(-1.0);
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