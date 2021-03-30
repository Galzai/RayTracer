package RayTracer.geometry;

import RayTracer.graphics.Ray;
import RayTracer.math.Vector3D;

import java.util.Optional;

public interface Surface {
    public double findIntersectionDistance(Ray ray);

    public Vector3D findIntersectionPoint(Ray ray);
}
