package RayTracer.Elements;
import RayTracer.math.Vector3D;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
public class ViewportTest {
    @Test
    public void basicViewportTest() {
        Vector3D origin = new Vector3D(0.0, 0.0, 0.0);
        Viewport port = new Viewport(500.0, 1.0, 1.0, 500, 500, origin);
        assertEquals(port.pixelToScreenPoint(0, 0).get(0), -250.0, 0.001);
    } 
}
