package RayTracer.geometry;

import RayTracer.TestUtils;
import RayTracer.graphics.*;
import RayTracer.math.Vector3D;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SphereTest {


    @Test
    public void sphereIntersection() {
        Sphere sphere = new Sphere(new Vector3D(0, 0, 2), 0.5, null);
        Vector3D origin = new Vector3D(0.0, 0.0, 0.0);
        Vector3D direction = new Vector3D(0, 0, 1);
        Ray ray = new Ray(origin, direction);
        Intersection intersection = sphere.findIntersection(ray);
        assertNotEquals(intersection, null);
    }

    @Test
    public void sphereIntersection_noIntersection() {
        Sphere sphere = new Sphere(new Vector3D(0, 0, 2), 0.5, null);
        Vector3D origin = new Vector3D(0.0, 0.0, 0.0);
        // opposite direction
        Vector3D direction = new Vector3D(0, 0, -1);
        Ray ray = new Ray(origin, direction);
        Intersection intersection = sphere.findIntersection(ray);
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
        Camera camera = new Camera(origin, lookAt, up, 0.5, screenWidth,false);
        Viewport viewport = new Viewport(imageWidth, imageHeight, camera);
        Sphere sphere = new Sphere(new Vector3D(0, 0, 2), 1, null);
        Sphere sphere2 = new Sphere(new Vector3D(2, 0, 2), 1, null);
        BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        for (int j = imageHeight; j >= 1; --j) {
            for (int i = 1; i <= imageWidth; ++i) {
                Vector3D direction = viewport.pixelToScreenPoint(i, j).subtract(origin);
                Ray ray = new Ray(origin, direction);
                if (sphere.findIntersection(ray) != null) {
                    img.setRGB(i - 1, imageHeight - j, Color.red.getRGB());
                } else if (sphere2.findIntersection(ray) != null) {
                    img.setRGB(i - 1, imageHeight - j, Color.green.getRGB());
                } else {
                    img.setRGB(i - 1, imageHeight - j, Color.blue.getRGB());
                }
            }

        }


        File f = new File(TestUtils.OUTPUT_PATH + "simple sphere.png");
        ImageIO.write(img, "png", f);
    }

    @Test
    public void threeSpheresRenderingTest() throws IOException {
        int imageWidth = 400;
        int imageHeight = 225;
        double screenWidth = 10;
        Vector3D origin = new Vector3D(-2, 2, 1);
        Vector3D lookAt = new Vector3D(0, 0, -1);
        Vector3D up = new Vector3D(0, 1, 0);
        Camera camera = new Camera(origin, lookAt, up, 5, screenWidth, false);
        Viewport viewport = new Viewport(imageWidth, imageHeight, camera);
        Sphere center = new Sphere(new Vector3D(0.0, 0.0, -1.0), 0.5, null);
        Sphere right = new Sphere(new Vector3D(1.0, 0.0, -1.0), 0.5, null);
        Sphere left = new Sphere(new Vector3D(-1.0, 0.0, -1.0), 0.5, null);


        BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        for (int j = imageHeight; j >= 1; --j) {
            for (int i = 1; i <= imageWidth; ++i) {
                Vector3D direction = viewport.pixelToScreenPoint(i, j).subtract(origin);
                Ray ray = new Ray(origin, direction);
                boolean background = true;
                if (center.findIntersection(ray) != null) {
                    img.setRGB(i - 1, imageHeight - j, Color.green.getRGB());
                } else if (left.findIntersection(ray) != null) {
                    img.setRGB(i - 1, imageHeight - j, Color.red.getRGB());
                } else if (right.findIntersection(ray) != null) {
                    img.setRGB(i - 1, imageHeight - j, Color.blue.getRGB());
                } else {
                    img.setRGB(i - 1, imageHeight - j, Color.white.getRGB());
                }
            }

        }

        File f = new File(TestUtils.OUTPUT_PATH + "three spheres.png");
        ImageIO.write(img, "png", f);
    }

    @Test
    public void poolTest() throws IOException {
        int imageWidth = 400;
        int imageHeight = 225;
        double screenWidth = 10;
        Vector3D origin = new Vector3D(0, 10, 0);
        Vector3D lookAt = new Vector3D(0, -100, 0);
        Vector3D up = new Vector3D(0, 0, -1);
        Camera camera = new Camera(origin, lookAt, up, 5,screenWidth, false);
        Viewport viewport = new Viewport(imageWidth, imageHeight, camera);
        Sphere yellow = new Sphere(new Vector3D(0.0, 0.0, -4.0), 1, null);
        Sphere black = new Sphere(new Vector3D(1.0, 0.0, -2.0), 1, null);
        Sphere white = new Sphere(new Vector3D(-1.0, 0.0, -2.0), 1, null);


        BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        for (int j = imageHeight; j >= 1; --j) {
            for (int i = 1; i <= imageWidth; ++i) {
                Vector3D direction = viewport.pixelToScreenPoint(i, j).subtract(origin);
                Ray ray = new Ray(origin, direction);
                boolean background = true;
                if (yellow.findIntersection(ray) != null) {
                    img.setRGB(i - 1, imageHeight - j, Color.yellow.getRGB());
                } else if (black.findIntersection(ray) != null) {
                    img.setRGB(i - 1, imageHeight - j, Color.black.getRGB());
                } else if (white.findIntersection(ray) != null) {
                    img.setRGB(i - 1, imageHeight - j, Color.white.getRGB());
                } else {
                    img.setRGB(i - 1, imageHeight - j, Color.green.getRGB());
                }
            }

        }

        File f = new File(TestUtils.OUTPUT_PATH + "pool.png");
        ImageIO.write(img, "png", f);
    }


//    @Test
//    public void insideSphereTest() throws IOException {
//        int imageWidth = 400;
//        int imageHeight = 225;
//        double screenWidth = 10;
//        Vector3D origin = new Vector3D(0,0,0);
//        Vector3D lookAt = new Vector3D(1,0,5);
//        Vector3D up = new Vector3D(0,1,0);
//        Camera camera = new Camera(origin, lookAt, up, 4, screenWidth);
//        Viewport viewport = new Viewport(imageWidth, imageHeight, camera);
//
//
//        ComputationalColor dpinkColor =  new ComputationalColor(255,20,147);
//        ComputationalColor spinkColor =  new ComputationalColor(0.5, 0.5, 0.5);;
//        Material pinkMaterial = new Material(dpinkColor, spinkColor, null, 30.0, 0.5);
//
//
//        AxisAlignedBox box = new AxisAlignedBox(new Vector3D(0,0,0), new Vector3D(5,5,5), pinkMaterial);
//        Sphere sphere = new Sphere(new Vector3D(0,    0, 0), 5, pinkMaterial);
//
//
//        Vector3D lightPosition1 = new Vector3D(0, 0, 0);
//        ComputationalColor lightColor = new ComputationalColor(255,192,203);
//        Light light = new Light(lightPosition1, lightColor, 1.0, 1, 1.0);
//
//
//        ComputationalColor backgroundColor = new ComputationalColor(0.47, 0.27, 0.95);
//        Scene scene = new Scene((camera), viewport, backgroundColor);
//        scene.addLight(light);
//        scene.addSurface(sphere);
//
//        scene.renderScene(TestUtils.OUTPUT_PATH + "insideObject.png");
//    }

}
