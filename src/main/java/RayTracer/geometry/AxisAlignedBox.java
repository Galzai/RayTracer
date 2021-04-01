package RayTracer.geometry;

import RayTracer.graphics.Intersection;
import RayTracer.graphics.Material;
import RayTracer.graphics.Ray;
import RayTracer.math.MathUtils;
import RayTracer.math.Vector3D;

public class AxisAlignedBox implements Surface {
    private Material material;
    private Vector3D minBounds;
    private Vector3D maxBounds;

    public AxisAlignedBox(Vector3D center, Vector3D axisScales, Material material) {
        this.material = material;
        this.minBounds = center.subtract(axisScales.scalarMult(0.5));
        this.maxBounds = center.add(axisScales.scalarMult(0.5));
    }

    @Override
    public Intersection findIntersection(Ray ray) {
        double tXMin = (this.minBounds.get(0) - ray.origin().get(0)) / ray.direction().get(0);
        double tXMax = (this.maxBounds.get(0) - ray.origin().get(0)) / ray.direction().get(0);
        double tMin = Math.min(tXMin, tXMax);
        double tMax = Math.max(tXMin, tXMax);

        double tYMin = (this.minBounds.get(1) - ray.origin().get(1)) / ray.direction().get(1);
        double tYMax = (this.maxBounds.get(1) - ray.origin().get(1)) / ray.direction().get(1);
        double tYEnter = Math.min(tYMin, tYMax);
        double tYExit = Math.max(tYMin, tYMax);

        if ((tMin > tYExit) || (tYEnter > tMax)) {
            return null;
        }
        tMin = Math.max(tMin, tYEnter);
        tMax = Math.min(tMax, tYExit);

        double tZMin = (this.minBounds.get(2) - ray.origin().get(2)) / ray.direction().get(2);
        double tZMax = (this.maxBounds.get(2) - ray.origin().get(2)) / ray.direction().get(2);
        double tZEnter = Math.min(tZMin, tZMax);
        double tZExit = Math.max(tZMin, tZMax);

        if ((tMin > tZMax) || (tZMin > tMax)) {
            return null;
        }

        tMin = Math.max(tMin, tZEnter);
        tMax = Math.min(tMax, tZExit);

        if (tMin < 0) {
            // opposite direction
            return null;
        }

        Vector3D intersectionPoint = ray.at(tMin);
        Vector3D normal = null;

        if (Math.abs(intersectionPoint.get(0) - this.minBounds.get(0)) <= MathUtils.EPSILON)
            normal = new Vector3D(-1,0,0);
        else if (Math.abs(intersectionPoint.get(0) - this.maxBounds.get(0)) <= MathUtils.EPSILON)
            normal = new Vector3D(1,0,0);
        else if (Math.abs(intersectionPoint.get(1) - this.minBounds.get(1)) <= MathUtils.EPSILON)
            normal = new Vector3D(0,-1,0);
        else if (Math.abs(intersectionPoint.get(1) - this.maxBounds.get(1)) <= MathUtils.EPSILON)
            normal = new Vector3D(0,1,0);
        else if (Math.abs(intersectionPoint.get(2) - this.minBounds.get(2)) <= MathUtils.EPSILON)
            normal = new Vector3D(0,0,-1);
        else if (Math.abs(intersectionPoint.get(2) - this.maxBounds.get(2)) <= MathUtils.EPSILON)
            normal = new Vector3D(0,0,1);

        return new Intersection(intersectionPoint, normal, this, tMin);
    }

    @Override
    public Material getMaterial() {
        return material;
    }
}
