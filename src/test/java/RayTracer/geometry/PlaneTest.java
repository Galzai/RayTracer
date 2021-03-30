package RayTracer.geometry;

import RayTracer.graphics.Ray;
import RayTracer.math.Vector3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PlaneTest {
    @Test
    public void planeIntersection_intersects() {
        Plane plane = new Plane(new Vector3D(0, 0, 1), 5, null);
        Vector3D origin = new Vector3D(0.0, 0.0, 0.0);

        // all rays with z > 0 should intersect:
        Ray ray = new Ray(origin, new Vector3D(0, 0, 1));
        Vector3D intersection = ray.findIntersectionPoint(plane);
        assertNotEquals(intersection, null);
        ray = new Ray(origin, new Vector3D(1, 1, 2));
        intersection = ray.findIntersectionPoint(plane);
        assertNotEquals(intersection, null);
        ray = new Ray(origin, new Vector3D(10.1, 0.1, 1.1));
        intersection = ray.findIntersectionPoint(plane);
        assertNotEquals(intersection, null);
    }
    @Test
    public void planeIntersection_noIntersection() {
        Plane plane = new Plane(new Vector3D(0, 0, 1), 5, null);
        Vector3D origin = new Vector3D(0.0, 0.0, 0.0);
        // rays with z <= 0 should not intersect

        // parallel to plane:
        Ray ray = new Ray(origin, new Vector3D(0, 1, 0));
        Vector3D intersection = ray.findIntersectionPoint(plane);
        assertEquals(intersection, null);
        ray = new Ray(origin, new Vector3D(10.1, 0.1, 0.0));
        intersection = ray.findIntersectionPoint(plane);
        assertEquals(intersection, null);

        // negative direction:
        ray = new Ray(origin, new Vector3D(0, 0, -1));
        intersection = ray.findIntersectionPoint(plane);
        assertEquals(intersection, null);
        ray = new Ray(origin, new Vector3D(41, 2, -10));
        intersection = ray.findIntersectionPoint(plane);
        assertEquals(intersection, null);
    }
}
