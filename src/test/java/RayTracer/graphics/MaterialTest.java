package RayTracer.graphics;
import java.awt.Color;

import org.junit.jupiter.api.Test;

public class MaterialTest {
    @Test
    public void materialInitialization() {
        ComputationalColor diffuse = new ComputationalColor(255,0,0);
        ComputationalColor specular = new ComputationalColor(0,255,0);
        ComputationalColor reflection = new ComputationalColor(0,0,255);
//        Color background = new Color(0,0,255);
        double transparency = 0.5;
        double phong = 1;
        Material material = new Material(diffuse, specular, reflection, phong, transparency);
//        material.

    }
}
