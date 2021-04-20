package RayTracer.graphics;

import RayTracer.geometry.Surface;
import RayTracer.math.Vector3D;

public class Intersection {
    // Vectors
    private Vector3D intersectionPoint;
    private Vector3D normal;
    // Surface of intersection
    private Surface surface;
    // Value of intersection along ray
    private Double rayVal;

    /**
     * Constructs a new Intersection object
     *
     * @param intersectionPoint point of intersection
     * @param normal            normal of the surface at the point
     * @param surface           the surface the intersection occured at
     * @param rayVal            the point along the ray the intersection occured at
     */
    public Intersection(Vector3D intersectionPoint, Vector3D normal, Surface surface, Double rayVal) {

        this.intersectionPoint = intersectionPoint;
        this.normal = normal;
        this.surface = surface;
        this.rayVal = rayVal;
    }

    // Getters
    public Vector3D getIntersectionPoint() {
        return this.intersectionPoint;
    }

    public Vector3D getNormal() {
        return this.normal;
    }

    public Surface getSurface() {
        return this.surface;
    }

    public Double getRayVal() {
        return this.rayVal;
    }
}
