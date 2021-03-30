package RayTracer.graphics;

import RayTracer.geometry.Surface;
import  RayTracer.math.Vector3D;

import java.util.Optional;

/**
 * Rays from origin in direction of direction
 */
public class Ray {
    
    private Vector3D origin;
    private Vector3D direction;

    /**
     * Constructs a ray from origin in direction
     * @param origin origin vector
     * @param direction direction vector (not necessarily normalized!)
     */
    public Ray(Vector3D origin, Vector3D  direction){
        this.origin = origin;
        this.direction = direction.normalize();
    }

    /**
     * Returns the origin of the vector
     */
    public Vector3D origin(){
        return origin;
    }

    /**
     * Returns the direction of the vector
     */
    public Vector3D direction(){
        return direction;
    }

    /**
     * Returns the point on ray at distance t from origin
     * @param t distance on ray
     * @return vector of point
     */
    public Vector3D at(Double t){
        return origin.add(direction.scalarMult(t));
    }

    /**
     *
     * @param surface
     * @return Vector3D of intersection point if exists, otherwise returns null
     */
    public Vector3D findIntersectionPoint(Surface surface) {
        return surface.findIntersectionPoint(this);
    }
    
}
