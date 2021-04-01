package RayTracer.geometry;

import RayTracer.graphics.Intersection;
import RayTracer.graphics.Material;
import RayTracer.graphics.Ray;
import RayTracer.math.MathUtils;
import RayTracer.math.Vector3D;

public class AxisAlignedBox implements Surface {
    private Vector3D center;
    private Vector3D axisScales;
    private Vector3D axisRotation;
    private Material material;

    public AxisAlignedBox(Vector3D center, Vector3D axisScales, Vector3D axisRotation, Material material) {
        this.center = center;
        this.axisScales = axisScales;
        this.axisRotation = axisRotation;
        this.material = material;
    }

    public Vector3D getCenter() {
        return center;
    }

    public double xScale() {
        return axisScales.get(0);
    }
    public double yScale() {
        return axisScales.get(1);
    }
    public double zScale() {
        return axisScales.get(2);
    }

    public double xRotation() {
        return axisRotation.get(0);
    }
    public double yRotation() {
        return axisRotation.get(1);
    }
    public double zRotation() {
        return axisRotation.get(2);
    }

    @Override
    public Intersection findIntersection(Ray ray) {
        Vector3D minBounds = center.subtract(axisScales.scalarMult(0.5));
        Vector3D maxBounds = center.add(axisScales.scalarMult(0.5));

        double tXMin = (minBounds.get(0) - ray.origin().get(0)) / ray.direction().get(0);
        double tXMax = (maxBounds.get(0) - ray.origin().get(0)) / ray.direction().get(0);
        double tMin = Math.min(tXMin, tXMax);
        double tMax = Math.max(tXMin, tXMax);

        double tYMin = (minBounds.get(1) - ray.origin().get(1)) / ray.direction().get(1);
        double tYMax = (maxBounds.get(1) - ray.origin().get(1)) / ray.direction().get(1);
        double tYEnter = Math.min(tYMin, tYMax);
        double tYExit = Math.max(tYMin, tYMax);

        if ((tMin > tYExit) || (tYEnter > tMax)) {
            return null;
        }
        tMin = Math.max(tMin, tYEnter);
        tMax = Math.min(tMax, tYExit);

        double tZMin = (minBounds.get(2) - ray.origin().get(2)) / ray.direction().get(2);
        double tZMax = (maxBounds.get(2) - ray.origin().get(2)) / ray.direction().get(2);
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

        if (Math.abs(intersectionPoint.get(0) - minBounds.get(0)) <= MathUtils.EPSILON)
            normal = new Vector3D(-1,0,0);
        else if (Math.abs(intersectionPoint.get(0) - maxBounds.get(0)) <= MathUtils.EPSILON)
            normal = new Vector3D(1,0,0);
        else if (Math.abs(intersectionPoint.get(1) - minBounds.get(1)) <= MathUtils.EPSILON)
            normal = new Vector3D(0,-1,0);
        else if (Math.abs(intersectionPoint.get(1) - maxBounds.get(1)) <= MathUtils.EPSILON)
            normal = new Vector3D(0,1,0);
        else if (Math.abs(intersectionPoint.get(2) - minBounds.get(2)) <= MathUtils.EPSILON)
            normal = new Vector3D(0,0,-1);
        else if (Math.abs(intersectionPoint.get(2) - maxBounds.get(2)) <= MathUtils.EPSILON)
            normal = new Vector3D(0,0,1);

        return new Intersection(intersectionPoint, normal, this, tMin);
    }

    @Override
    public Material getMaterial() {
        return material;
    }
}
