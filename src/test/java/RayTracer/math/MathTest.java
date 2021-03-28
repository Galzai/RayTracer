package RayTracer.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MathTest {
    @Test
    public void basicMathTest() {
        Vector3D testVec1 = new Vector3D(0.0, 1.1, 2.2);
        Vector3D testVec2 = new Vector3D(1.1, 2.5, 3.9);
        Vector3D resultVec = testVec1.add(testVec2);
        assertEquals(resultVec.get(1), testVec1.get(1) + testVec2.get(1));
    }

    @Test
    public void normalizeTest() {
        Vector3D testVec1 = new Vector3D(0.0, 1.1, 2.2);
        Vector3D resultVec = testVec1.normalize();
        assertEquals(resultVec.euclideanNorm(), 1.0, 0.001);
    }
}
