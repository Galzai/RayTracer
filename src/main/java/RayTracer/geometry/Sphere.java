package RayTracer.geometry;

import RayTracer.graphics.Ray;
import RayTracer.math.Vector;
import RayTracer.math.Vector3D;

import java.util.List;
import java.util.Optional;

public class Sphere implements Surface {
    private Vector3D center;
    private double radius;

    /**
     * Constructor for sphere represented as center vector and a radius
     *
     * @param center
     * @param radius
     */
    public Sphere(Vector3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     *
     * @param ray
     * @return Returns the intersection distance if the ray intersects the sphere, otherwise returns -1.
     */
    @Override
    public double findIntersectionDistance(Ray ray) {
        Vector3D oc = ray.origin().subtract(center);
        double b = 2 * ray.direction().dotProduct(oc);
        double c = oc.dotProduct(oc) - radius * radius;
        double delta = Math.pow(b, 2) - 4 * c;
        if (delta > 0) {
            double t1 = (-b + Math.sqrt(delta)) / 2;
            double t2 = (- b - Math.sqrt(delta)) / 2;
            if (t1 > 0 && t2 > 0) {
                return Math.min(t1,t2);
            }
        }
        return -1;
    }

    /**
     *
     * @param ray
     * @return Returns the intersection point between the ray and the sphere if exists, else returns null.
     */
    @Override
    public Vector3D findIntersectionPoint(Ray ray) {
        double intersectionDistance = findIntersectionDistance(ray);
        if (intersectionDistance > 0) {
            return ray.at(intersectionDistance);
        }
        return null;
    }
}
