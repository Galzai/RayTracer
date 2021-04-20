package RayTracer.graphics;

import RayTracer.math.Vector3D;

/**
 * Rays from origin in direction of direction
 */
public class Ray {
    private Vector3D origin;
    private Vector3D direction;
    private static final double EPSILON = 0.0001;

    /**
     * Constructs a ray from origin in direction
     *
     * @param origin    origin vector
     * @param direction direction vector (not necessarily normalized!)
     */
    public Ray(Vector3D origin, Vector3D direction) {
        this.origin = origin;
        this.direction = direction.normalize();
    }

    /**
     * Returns the origin of the vector
     */
    public Vector3D origin() {
        return origin;
    }

    /**
     * Returns the direction of the vector
     */
    public Vector3D direction() {
        return direction;
    }

    /**
     * Returns the point on ray at distance t from origin
     *
     * @param t distance on ray
     * @return vector of point
     */
    public Vector3D at(Double t) {
        return origin.add(direction.scalarMult(t));
    }

    /**
     * move origin of the ray by epsilon in the direction of the ray.
     * @return new ray with a new origin.
     */
    public Ray moveOriginByEpsilon() {
        return new Ray(at(Ray.EPSILON), this.direction);
    }
}
