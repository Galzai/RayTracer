package RayTracer.Elements;
import RayTracer.math.Vector3D;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
public class RayTest {
    @Test
    public void basicRayTest() {
        Vector3D origin = new Vector3D(1.0, 1.0, 0.0);
        Vector3D dir = new Vector3D(1.0, 0.0, 0.0);
        Ray ray = new Ray(origin, dir);
        assertEquals(ray.at(2.0).get(0), 3.0, 0.001);
    } 
}
