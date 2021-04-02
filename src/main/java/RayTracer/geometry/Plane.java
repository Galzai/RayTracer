package RayTracer.geometry;

import RayTracer.graphics.Intersection;
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
    public Intersection findIntersection(Ray ray) {
        double denominator = this.normal.dotProduct(ray.direction());
        if (Math.abs(denominator) > MathUtils.EPSILON) {
            double t = this.normal.dotProduct(this.p0.subtract(ray.origin())) / denominator;
            Vector3D intersectionPoint = ray.at(t);
            Vector3D pointNormal = this.normal.findProjection(ray.direction()).scalarMult(-1.0).normalize();
            return new Intersection(intersectionPoint, pointNormal, this, t);
        }
        return null;

    }

    @Override
    public Material getMaterial() {
        return material;
    }
}
