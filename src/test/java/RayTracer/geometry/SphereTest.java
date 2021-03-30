package RayTracer.geometry;

import RayTracer.graphics.Camera;
import RayTracer.graphics.Ray;
import RayTracer.graphics.Viewport;
import RayTracer.math.Vector;
import RayTracer.math.Vector3D;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SphereTest {

    @Test
    public void sphereIntersection() {
        Sphere sphere = new Sphere(new Vector3D(0, 0, 2), 0.5, null);
        Vector3D origin = new Vector3D(0.0, 0.0, 0.0);
        Vector3D direction = new Vector3D(0, 0, 1);
        Ray ray = new Ray(origin, direction);
        Vector3D intersection = ray.findIntersectionPoint(sphere);
        assertNotEquals(intersection, null);
    }

    @Test
    public void sphereIntersection_noIntersection() {
        Sphere sphere = new Sphere(new Vector3D(0, 0, 2), 0.5, null);
        Vector3D origin = new Vector3D(0.0, 0.0, 0.0);
        // opposite direction
        Vector3D direction = new Vector3D(0, 0, -1);
        Ray ray = new Ray(origin, direction);
        Vector3D intersection = ray.findIntersectionPoint(sphere);
        assertEquals(intersection, null);
    }

    @Test
    public void simpleRenderSphere() throws IOException {
        int imageWidth = 400;
        int imageHeight = 400;
        double screenWidth = 2.0;
        Vector3D origin = new Vector3D(0.0, 0.0, 0.0);
        Vector3D lookAt = new Vector3D(0, 0, 1);
        Vector3D up = new Vector3D(0, 1, 0);
        Camera camera = new Camera(origin, lookAt, up, 0.5, false);
        Viewport viewport = new Viewport(screenWidth, imageWidth, imageHeight, camera);
        Sphere sphere = new Sphere(new Vector3D(0, 0, 2), 1, null);
        Sphere sphere2 = new Sphere(new Vector3D(2, 0, 2), 1, null);




        BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        for (int j = imageHeight; j >= 1; --j) {
            for (int i = 1; i <= imageWidth; ++i) {
                Vector3D direction = viewport.pixelToScreenPoint(i, j);
                Ray ray = new Ray(origin, direction);
                if (ray.findIntersectionPoint(sphere) != null) {
                    img.setRGB(i - 1, j - 1, Color.red.getRGB());
                }
                else if (ray.findIntersectionPoint(sphere2) != null) {
                    img.setRGB(i-1, j-1, Color.green.getRGB());
                }
                else {
                    img.setRGB(i - 1, j - 1, Color.blue.getRGB());
                }
            }

        }

        File f = new File("C:\\Users\\Tal\\Desktop\\university\\CS\\year3\\03 - Graphics and Computer Vision\\HW\\RayTracer\\src\\test\\resources\\simple sphere.png");
        ImageIO.write(img, "png", f);
    }

    @Test
    public void threeSpheresRenderingTest() throws IOException {
        int imageWidth = 400;
        int imageHeight = 225;
        double screenWidth = 10;
        Vector3D origin = new Vector3D(-2,2,1);
        Vector3D lookAt = new Vector3D(0,0,-1);
        Vector3D up = new Vector3D(0,1,0);
        Camera camera = new Camera(origin, lookAt, up, 5, false);
        Viewport viewport = new Viewport(screenWidth, imageWidth, imageHeight, camera);
        Sphere center = new Sphere(new Vector3D(0.0,    0.0, -1.0), 0.5, null);
        Sphere right = new Sphere(new Vector3D(1.0,    0.0, -1.0), 0.5, null);
        Sphere left = new Sphere(new Vector3D(-1.0,    0.0, -1.0), 0.5, null);




        BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        for (int j = imageHeight; j >= 1; --j) {
            for (int i = 1; i <= imageWidth; ++i) {
                Vector3D direction = viewport.pixelToScreenPoint(i, j);
                Ray ray = new Ray(origin, direction);
                boolean background = true;
                if (ray.findIntersectionPoint(center) != null) {
                    img.setRGB(i - 1, j - 1, Color.green.getRGB());
                }

                else if (ray.findIntersectionPoint(left) != null) {
                    img.setRGB(i - 1, j - 1, Color.red.getRGB());
                }

                else if (ray.findIntersectionPoint(right) != null) {
                    img.setRGB(i - 1, j - 1, Color.blue.getRGB());
                }
                else {
                    img.setRGB(i - 1, j - 1, Color.white.getRGB());
                }
            }

        }

        File f = new File("C:\\Users\\Tal\\Desktop\\university\\CS\\year3\\03 - Graphics and Computer Vision\\HW\\RayTracer\\src\\test\\resources\\Output2.png");
        ImageIO.write(img, "png", f);
    }
}
