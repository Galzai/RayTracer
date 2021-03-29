package RayTracer.Elements;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColorTest {
    @Test
    public void colorInitialization() {
        ComputationalColor intColor = new ComputationalColor(255, 128, 255);
        assertEquals(intColor.getRed(), 1);
        assertEquals(intColor.getGreen(), 128 / 255.0);
        ComputationalColor fColor = new ComputationalColor(1.0, 0.5, 1.0);
        assertEquals(fColor.getRed(), 1);
        try {
            ComputationalColor c = new ComputationalColor(300, 100, 100);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        try {
            ComputationalColor c = new ComputationalColor(10, 0, 100);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void checkIntRepresentation() {
        ComputationalColor color = new ComputationalColor(255, 128, 3);
        int[] colors = color.getIntRepresentation();
        assertEquals(colors[0], 255);
        assertEquals(colors[1], 128);
        assertEquals(colors[2], 3);
        java.awt.Color color1 = new java.awt.Color(1,2,3);

    }
}
