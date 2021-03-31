package RayTracer.geometry;

import RayTracer.graphics.Material;
import RayTracer.graphics.Ray;
import RayTracer.math.Vector;
import RayTracer.math.Vector3D;

import java.util.List;
import java.util.Optional;

public class Sphere implements Surface {
    private Vector3D center;
    private double radius;
    private Material material;

    /**
     * Constructor for sphere represented as center vector and a radius
     *
     * @param center
     * @param radius
     */
    public Sphere(Vector3D center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
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
        Vector3D oc = ray.origin().subtract(this.center);
        double b = 2 * ray.direction().dotProduct(oc);
        double c = oc.dotProduct(oc) - this.radius * this.radius;
        double delta = Math.pow(b, 2) - 4 * c;
        if (delta > 0) {
            double t1 = (-b + Math.sqrt(delta)) / 2;
            double t2 = (- b - Math.sqrt(delta)) / 2;
            if (t1 > 0 && t2 > 0) {
                return ray.at(Math.min(t1,t2));
            }
        }
        return null;
    }

    @Override
    public Material getMaterial() {
        return material;
    }
}
