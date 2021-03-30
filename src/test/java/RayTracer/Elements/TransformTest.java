package RayTracer.Elements;
import RayTracer.math.Matrix3D;
import RayTracer.math.Vector3D;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
public class TransformTest {
    @Test
    public void basicViewportTest() {
        Vector3D origin = new Vector3D(0.0, 0.0, 0.0);
//        Viewport port = new Viewport(500.0, 1.0, 1.0, 500, 500, origin);
//        assertEquals(port.pixelToScreenPoint(0, 0).get(0), -250.0, 0.001);
    }

    @Test
    public void conversionTest() {
        Vector3D up = new Vector3D(0.0, 1.0, 0.0);
        Vector3D lookAt = new Vector3D(0.0, 0.0, 2.0);
        Vector3D origin = new Vector3D(0.0, 0.0, 0.0);
        Camera cam = new Camera(origin, lookAt, up, false);
        assertEquals(up.get(1), cam.v().get(1), 0.001);
        assertEquals(up.get(1), cam.v().get(1), 0.001);
        assertEquals(up.get(1), cam.v().get(1), 0.001);
    } 

    @Test
    public void transformTest() {
        Vector3D up = new Vector3D(0.0, 1.0, 0.0);
        Vector3D towards = new Vector3D(0.0, 0.0, 1.0);
        Vector3D right = new Vector3D(1.0, 0.0, 0.0);

        Matrix3D transform = Matrix3D.createTransformationMatrix(right, up, towards);
        Vector3D vec = new Vector3D(2.0, 1.0, 2.0);
        Vector3D res = transform.vecMult(vec);
        assertEquals(res.toString(), vec.toString());
    } 
}
