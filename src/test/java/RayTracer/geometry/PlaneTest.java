package RayTracer.geometry;

import RayTracer.TestUtils;
import RayTracer.graphics.Camera;
import RayTracer.graphics.Intersection;
import RayTracer.graphics.Ray;
import RayTracer.graphics.Viewport;
import RayTracer.math.Vector3D;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PlaneTest {
    @Test
    public void planeIntersection_intersects() {
        Plane plane = new Plane(new Vector3D(0, 0, 1), 5, null);
        Vector3D origin = new Vector3D(0.0, 0.0, 0.0);

        // all rays with z > 0 should intersect:
        Ray ray = new Ray(origin, new Vector3D(0, 0, 1));
        Intersection intersection = plane.findIntersection(ray);
        assertNotEquals(intersection, null);
        ray = new Ray(origin, new Vector3D(1, 1, 2));
        intersection = plane.findIntersection(ray);
        assertNotEquals(intersection, null);
        ray = new Ray(origin, new Vector3D(10.1, 0.1, 1.1));
        intersection = plane.findIntersection(ray);
        assertNotEquals(intersection, null);
    }

    @Test
    public void planeIntersection_noIntersection() {
        Plane plane = new Plane(new Vector3D(0, 0, 1), 5, null);
        Vector3D origin = new Vector3D(0.0, 0.0, 0.0);
        // rays with z <= 0 should not intersect

        // parallel to plane:
        Ray ray = new Ray(origin, new Vector3D(0, 1, 0));
        Intersection intersection = plane.findIntersection(ray);
        assertEquals(intersection, null);
        ray = new Ray(origin, new Vector3D(10.1, 0.1, 0.0));
        intersection = plane.findIntersection(ray);
        assertEquals(intersection, null);

        // negative direction:
        ray = new Ray(origin, new Vector3D(0, 0, -1));
        intersection = plane.findIntersection(ray);
        assertEquals(intersection, null);
        ray = new Ray(origin, new Vector3D(41, 2, -10));
        intersection = plane.findIntersection(ray);
        assertEquals(intersection, null);
    }

    @Test
    public void simpleRenderPlane() throws IOException {
        int imageWidth = 400;
        int imageHeight = 400;
        double screenWidth = 2.0;
        Vector3D origin = new Vector3D(0.0, 0.0, 0.0);
        Vector3D lookAt = new Vector3D(10, 0, 1);
        Vector3D up = new Vector3D(0, 1, 0);
        Camera camera = new Camera(origin, lookAt, up, 0.5, screenWidth, false);
        Viewport viewport = new Viewport(imageWidth, imageHeight, camera);
        Plane plane = new Plane(new Vector3D(0, 0, 1), 5, null);
        Sphere sphere = new Sphere(new Vector3D(5, 0, 0), 2, null);


        BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        for (int j = imageHeight; j >= 1; --j) {
            for (int i = 1; i <= imageWidth; ++i) {
                Vector3D direction = viewport.pixelToScreenPoint(i, j);
                Ray ray = new Ray(origin, direction);
                if (plane.findIntersection(ray) != null) {
                    img.setRGB(i - 1, imageWidth - j, Color.red.getRGB());
                } else {
                    img.setRGB(i - 1, imageWidth - j, Color.blue.getRGB());
                }

                if (sphere.findIntersection(ray) != null) {
                    img.setRGB(i - 1, imageWidth - j, Color.gray.getRGB());

                }
            }
        }

        File f = new File(TestUtils.OUTPUT_PATH + "simplePlane.png");
        ImageIO.write(img, "png", f);
    }
}
