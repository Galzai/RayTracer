package RayTracer.geometry;

import RayTracer.graphics.Intersection;
import RayTracer.graphics.Material;
import RayTracer.graphics.Ray;
import RayTracer.math.Vector3D;

public interface Surface {

    /**
     *
     * @param ray ray to intersect with
     * @return Returns the intersection data between the ray and the sphere if exists, else returns null.
     */
    public Intersection findIntersection(Ray ray);

    public Material getMaterial();

}
