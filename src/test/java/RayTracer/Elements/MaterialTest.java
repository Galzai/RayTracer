package RayTracer.Elements;
import java.awt.Color;

import org.junit.jupiter.api.Test;

public class MaterialTest {
    @Test
    public void materialInitialization() {
        Color diffuse = new Color(255,0,0);
        Color specular = new Color(0,255,0);
        Color reflection = new Color(0,0,255);
//        Color background = new Color(0,0,255);
        double transparency = 0.5;
        double phong = 1;
        Material material = new Material(diffuse, specular, reflection, phong, transparency);
//        material.

    }
}
