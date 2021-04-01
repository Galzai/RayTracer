package RayTracer.geometry;

import RayTracer.graphics.Intersection;
import RayTracer.graphics.Ray;
import RayTracer.math.Vector3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BoxTest {
    @Test
    public void BoxIntersection() {
        AxisAlignedBox box = new AxisAlignedBox(new Vector3D(0,0,5), new Vector3D(5,3,4), new Vector3D(0,0,0), null);
        Vector3D origin = new Vector3D(0.0, 0.0, 0.0);
        Vector3D direction = new Vector3D(0, 0, 1);
        Ray ray = new Ray(origin, direction);
        Intersection intersection = box.findIntersection(ray);
        assertNotEquals(intersection, null);
    }

    @Test
    public void sphereIntersection_noIntersection() {
        AxisAlignedBox box = new AxisAlignedBox(new Vector3D(0,0,5), new Vector3D(1,1,1), new Vector3D(0,0,0), null);
        Vector3D origin = new Vector3D(0.0, 0.0, 0.0);
        // opposite direction
        Vector3D direction = new Vector3D(0, 0, -1);
        Ray ray = new Ray(origin, direction);
        Intersection intersection = box.findIntersection(ray);
        assertEquals(intersection, null);
    }
}
