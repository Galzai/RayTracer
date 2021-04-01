package RayTracer.graphics;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import RayTracer.geometry.Surface;
import RayTracer.math.Vector3D;

public class Scene {
    
    private Camera camera;
    private Viewport viewport;
    private ComputationalColor  backgroundColor;
    private PhongShader shader;

    private ArrayList<Light> lights;
    public ArrayList<Surface> surfaces;

    /**
     * Construct a scene object with pre-populated arrays
     * 
     * @param camera
     * @param viewport
     * @param backgroundColor
     * @param lights
     * @param surfaces
     */
    public Scene(Camera camera, Viewport viewport, ComputationalColor backgroundColor,
     ArrayList<Light> lights, ArrayList<Surface> surfaces ) {

         this.camera = camera;
         this.viewport = viewport;
         this.backgroundColor = backgroundColor;
         this.lights = lights;
         this.surfaces = surfaces;
         this.shader = new PhongShader(this);
    }

    public Camera getCamera() {
        return this.camera;
    }

    public Viewport getViewport() {
        return this.viewport;
    }

    public ComputationalColor getBackgroundColor() {
        return this.backgroundColor;
    }

    public ArrayList<Light> getLights() {
        return this.lights;
    }

    public ArrayList<Surface> getSurfaces() {
        return this.surfaces;
    }

    /**
     * Construct a scene with empty lights and surfaces
     * 
     * @param camera
     * @param viewport
     * @param backgroundColor
     */
    public Scene(Camera camera, Viewport viewport, ComputationalColor backgroundColor) {
        this(camera, viewport, backgroundColor, new ArrayList<Light>(), new ArrayList<Surface>());
    }

    /**
     * Add a new light to the scene
     * 
     * @param light
     */
    public void addLight(Light light) {
        this.lights.add(light);
    }

    /**
     *  Add a new surface to the scene
     * 
     * @param surface
     */
    public void addSurface(Surface surface) {
        this.surfaces.add(surface);
    }

    /**
     * Finds the closest surface that intersects with the ray and its intersection
     * 
     * @param ray ray to intersect with
     * @return intersection data
     */
    public Intersection IntersectRay(Ray ray) {
        // We are searching for the closest intersecting surface
        double minRayVal = Double.MAX_VALUE;
        Intersection minIntersection = null;
        Intersection curIntersection = null;

        for (Surface surface : surfaces) {
            curIntersection = surface.findIntersection(ray);
            if ((curIntersection != null) && (curIntersection.getRayVal() <= minRayVal)) {
                minIntersection = curIntersection;
                minRayVal = minIntersection.getRayVal();   
            }
        }
        return minIntersection;
    }
    
    /**
     * Checks if any surface intersects the given ray
     *  
     * @param ray ray to intersect with
     * @return true if any surface intersection exists
     */
    public Boolean IntersectionExists(Ray ray) {
        for(Surface surface : surfaces) {
            if (surface.findIntersection(ray) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * TODO: This will need to be expanded and modified
     * @param intersection
     * @param ray
     * @return
     */
    public ComputationalColor getColor(Intersection intersection, Ray ray) {
        if (intersection == null) return backgroundColor;
        return this.shader.shade(intersection, ray);
    }

    /**
     * Render the scene to a file
     * @param path path to file
     */
    public void renderScene(String path) throws IOException {

        BufferedImage img = new BufferedImage(viewport.getImageWidth(), viewport.getImageHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = viewport.getImageHeight(); y >= 1; --y) {
            for (int x = 1; x <= viewport.getImageWidth(); ++x) {

                // Find direction for current pixel
                Vector3D direction = viewport.pixelToScreenPoint(x, y).subtract(this.camera.position());
                Ray ray = new Ray(this.camera.position(), direction);
                Intersection intersection = IntersectRay(ray);
                img.setRGB(x - 1, viewport.getImageHeight() - y, getColor(intersection, ray).toColor().getRGB());
            }
        }
        File f = new File(path);
        ImageIO.write(img, "png", f);
    }
}