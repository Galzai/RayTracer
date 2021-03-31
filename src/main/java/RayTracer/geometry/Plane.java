package RayTracer.geometry;

import RayTracer.graphics.Material;
import RayTracer.graphics.Ray;
import RayTracer.math.MathUtils;
import RayTracer.math.Vector3D;

public class Plane implements Surface {
    private Vector3D normal;
    private double offset;
    private Vector3D p0; // closest point on the plane to origin
    private Material material;

    public Plane(Vector3D normal, double offset, Material material) {
        this.normal = normal;
        this.offset = offset;
        this.material = material;
        this.p0 = normal.scalarMult(this.offset / this.normal.dotProduct(this.normal));
    }

    public Vector3D getNormal() {
        return normal;
    }

    public double getOffset() {
        return offset;
    }

    @Override
    public double findIntersectionDistance(Ray ray) {
        Vector3D intersection = findIntersectionPoint(ray);
        if (intersection != null) {
            return intersection.calculateDistance(ray.origin());
        }
        return -1;
    }

    @Override
    public Vector3D findIntersectionPoint(Ray ray) {
        double denominator = this.normal.dotProduct(ray.direction());
        if (denominator > MathUtils.EPSILON) {
            // t = (p0 - ray origin) * normal / dotProduct(ray direction, normal)
            double t = this.normal.dotProduct(this.p0.subtract(ray.origin())) / denominator;
            return ray.at(t);
        }
        return null;

    }

    @Override
    public Material getMaterial() {
        return material;
    }
}
