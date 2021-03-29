package RayTracer.Elements;

import  RayTracer.math.Vector3D;

/**
 * Rays from origin in direction of direction
 */
public class Ray {
    
    private Vector3D origin;
    private Vector3D direction;

    /**
     * Constructs a ray from origin in direction
     * @param origin origin vector
     * @param direction direction vector
     */
    public Ray(Vector3D origin, Vector3D  direction){
        this.origin = origin;
        this.direction = direction;
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
    
}
