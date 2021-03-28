package RayTracer.Elements;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;
import static org.junit.jupiter.api.Assertions.*;

public class ColorTest {
    @Test
    public void colorInitialization() {
        Color intColor = new Color(255, 128, 255);
        assertEquals(intColor.getRed(), 1);
        assertEquals(intColor.getGreen(), 128 / 255.0);
        Color fColor = new Color(1.0, 0.5, 1.0);
        assertEquals(fColor.getRed(), 1);
        try {
            Color c = new Color(300, 100, 100);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        try {
            Color c = new Color(10, 0, 100);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void checkIntRepresentation() {
        Color color = new Color(255, 128, 3);
        int[] colors = color.getIntRepresentation();
        assertEquals(colors[0], 255);
        assertEquals(colors[1], 128);
        assertEquals(colors[2], 3);
        java.awt.Color color1 = new java.awt.Color(1,2,3);

    }
}
