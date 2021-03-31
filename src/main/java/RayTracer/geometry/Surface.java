package RayTracer.geometry;

import RayTracer.graphics.Material;
import RayTracer.graphics.Ray;
import RayTracer.math.Vector3D;

public interface Surface {

    /**
     * TODO maybe change to static
     * @param ray
     * @return Returns the intersection distance if the ray intersects the sphere, otherwise returns -1.
     */
    public double findIntersectionDistance(Ray ray);


    /**
     *
     * @param ray
     * @return Returns the intersection point between the ray and the sphere if exists, else returns null.
     */
    public Vector3D findIntersectionPoint(Ray ray);


    public Material getMaterial();

}
