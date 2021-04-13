package RayTracer.graphics;

import RayTracer.geometry.Surface;
import RayTracer.math.Vector3D;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Scene {

    private Camera camera;
    private Viewport viewport;
    private ComputationalColor backgroundColor;
    private PhongShader shader;

    private List<Light> lights;
    public List<Surface> surfaces;
    private double shadowRaysRoot;
    private int maxRecursionDepth;
    private boolean ambientEnabled = false;

    private ComputationalColor ambientLightIntensity;

    /**
     * Construct a scene object with pre-populated arrays
     *
     * @param camera
     * @param viewport
     * @param backgroundColor
     * @param lights
     * @param surfaces
     * @param ambientEnabled
     */
    public Scene(Camera camera, Viewport viewport, ComputationalColor backgroundColor,
                 List<Light> lights, List<Surface> surfaces, double shadowRaysRoot, int maxRecursionDepth, boolean ambientEnabled) {

        this.camera = camera;
        this.viewport = viewport;
        this.backgroundColor = backgroundColor;
        this.lights = lights;
        this.surfaces = surfaces;
        this.shader = new PhongShader(this);
        this.shadowRaysRoot = shadowRaysRoot;
        this.maxRecursionDepth = maxRecursionDepth;
        if (!ambientEnabled || (lights.size() == 0)) {
            this.ambientLightIntensity = null;
        } else {
            this.ambientLightIntensity = estimateAmbientIntensity();
            this.ambientEnabled = true;
        }
    }

    /**
     * Construct a scene with empty lights and surfaces
     *
     * @param camera
     * @param viewport
     * @param backgroundColor
     * @param shadowRaysRoot
     * @param maxRecursionDepth
     */
    public Scene(Camera camera, Viewport viewport, ComputationalColor backgroundColor, double shadowRaysRoot, int maxRecursionDepth) {
        this(camera, viewport, backgroundColor, new ArrayList<Light>(), new ArrayList<Surface>(), shadowRaysRoot, maxRecursionDepth, false);
    }

    /**
     * Construct a scene with empty lights and surfaces and without recursion or shadow rays
     *
     * @param camera
     * @param viewport
     * @param backgroundColor
     */
    public Scene(Camera camera, Viewport viewport, ComputationalColor backgroundColor) {
        this(camera, viewport, backgroundColor, new ArrayList<Light>(), new ArrayList<Surface>(), 0, 0, false);
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

    public List<Light> getLights() {
        return this.lights;
    }

    public List<Surface> getSurfaces() {
        return this.surfaces;
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
     * Add a new surface to the scene
     *
     * @param surface
     */
    public void addSurface(Surface surface) {
        this.surfaces.add(surface);
    }

    public ComputationalColor getAmbientLightIntensity() {
        return this.ambientLightIntensity;
    }

    public boolean isAmbientEnabled() {
        return this.ambientEnabled;
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
     * Finds the closest surface that intersects with the ray that isnt ignoreSurface and its intersection
     *
     * @param ray ray to intersect with
     * @param ray ignoreSurface surface to ignore intersections with
     * @return intersection data
     */
    public Intersection intersectRayWithoutSurface(Ray ray, Surface ignoreSurface) {
        // We are searching for the closest intersecting surface
        double minRayVal = Double.MAX_VALUE;
        Intersection minIntersection = null;
        Intersection curIntersection = null;

        for (Surface surface : surfaces) {
            if (surface == ignoreSurface) {
                continue;
            }

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
    public boolean IntersectionExists(Ray ray) {
        for (Surface surface : surfaces) {
            if (surface.findIntersection(ray) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is a surface between an existing intersection and the light
     *
     * @param intersection
     * @param light
     * @return
     */
    public boolean intersectionIsShadowed(Intersection intersection, Light light) {
        return intersectionIsShadowed(intersection, light.getPosition());
    }

    /**
     * Checks if there is a surface between an existing intersection and the light
     *
     * @param intersection
     * @param lightPosition
     * @return
     */
    public boolean intersectionIsShadowed(Intersection intersection, Vector3D lightPosition) {
        Vector3D lightDirection = lightPosition.subtract(intersection.getIntersectionPoint()).normalize();
        Ray ray = new Ray(intersection.getIntersectionPoint(), lightDirection);
        ray = ray.moveOriginByEpsilon();
        double distanceToLight = intersection.getIntersectionPoint().calculateDistance(lightPosition);
        for (Surface surface : surfaces) {
            if (surface == intersection.getSurface()) {
                continue;
            }
            Intersection potentialIntersection = surface.findIntersection(ray);
            if (potentialIntersection != null) {
                // only if there is a surface between the ray's origin and the destination
                if (distanceToLight > ray.origin().calculateDistance(ray.at(potentialIntersection.getRayVal()))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Calculates the percentage of rays the lights casts on the intersection
     *
     * @param intersection
     * @param light
     * @return The percentage of rays the lights casts on the intersection
     */
    public double calcLightPercentage(Intersection intersection, Light light) {
        Vector3D lightDirection = light.getPosition().subtract(intersection.getIntersectionPoint()).normalize();
        Ray ray = new Ray(intersection.getIntersectionPoint(), lightDirection);
        ray = ray.moveOriginByEpsilon();
        List<Vector3D> lightPoints = getLightPoints(ray, light);
        int shadowedRays = 0;
        for (Vector3D point : lightPoints) {
            if (intersectionIsShadowed(intersection, point)) {
                shadowedRays++;
            }
        }
        return 1 - (double) shadowedRays / lightPoints.size();
    }

    /**
     * Calculates the light intensity that is casted upon the intersection. Each surface that is located between the
     * light position and the intersection decreases the light intensity (increases the shadow intensity) according
     * to it's transparency coefficient. The more transparent the surfaces between the light and the intersection are,
     * The more light gets to the intersection.
     *
     * @param intersection
     * @param lightPosition
     * @return light intensity of the light that reaches the intersection
     */
    public double calcLightIntensity(Intersection intersection, Vector3D lightPosition) {
        double lightIntensity = 1;
        Vector3D lightDirection = lightPosition.subtract(intersection.getIntersectionPoint()).normalize();
        Ray ray = new Ray(intersection.getIntersectionPoint(), lightDirection);
        ray = ray.moveOriginByEpsilon();
        double distance = intersection.getIntersectionPoint().calculateDistance(lightPosition);
        for (Surface surface : surfaces) {
            if (surface == intersection.getSurface()) {
                continue;
            }
            Intersection potentialIntersection = surface.findIntersection(ray);
            if (potentialIntersection != null) {
                // only if there is a surface between the ray's origin and the destination
                if (distance > ray.origin().calculateDistance(ray.at(potentialIntersection.getRayVal()))) {
                    // The light intensity is multiplied by the transparency coefficient of the each surface's material
                    // the less transparent the surface is, the more shadow is casted upon the original intersection
                    lightIntensity *= potentialIntersection.getSurface().getMaterial().getTransparency();
                }
            }
            if (lightIntensity == 0) {
                break;
            }
        }
        return lightIntensity;
    }

    /**
     * calculates the intensity of the lights that is casted upon the intersection, with soft shadows computations and
     * shadow intensity adjustments regarding to transparent objects.
     *
     * @param intersection
     * @param light
     * @return intensity of the lights that is casted upon the intersection
     */
    public double calcLightIntensity(Intersection intersection, Light light) {
        Vector3D lightDirection = light.getPosition().subtract(intersection.getIntersectionPoint()).normalize();
        Ray ray = new Ray(intersection.getIntersectionPoint(), lightDirection);
        ray = ray.moveOriginByEpsilon();
        List<Vector3D> lightPoints = getLightPoints(ray, light);
        double lightIntensity = 0;
        for (Vector3D point : lightPoints) {
            lightIntensity += calcLightIntensity(intersection, point);
        }
        return lightIntensity / lightPoints.size();
    }

    /**
     * returns a list with light's positions across the lights area for computing soft shadows
     *
     * @param ray
     * @param light
     * @return returns list with light's positions across the lights area
     */

    private List<Vector3D> getLightPoints(Ray ray, Light light) {
        Random random = new Random();
        double unit = light.getRadius() / this.shadowRaysRoot;
        Vector3D nonParallelVec = ray.direction().generateNonParallel();
        Vector3D right = ray.direction().findPerpendicular(nonParallelVec).normalize();
        Vector3D up = right.crossProduct(ray.direction()).normalize();
        // go left and down to the bottom left corner:
        Vector3D lowerLeftVec = light.getPosition().subtract(right.scalarMult(light.getRadius() / 2)).subtract(up.scalarMult(light.getRadius() / 2));
        // scale for convenience:
        right = right.scalarMult(unit);
        up = up.scalarMult(unit);
        List<Vector3D> lightPositions = new ArrayList<>();
        for (int i = 0; i < this.shadowRaysRoot; i++) {
            for (int j = 0; j < this.shadowRaysRoot; j++) {
                double rightOffset = random.nextDouble();
                double upOffset = random.nextDouble();
                Vector3D nextVector = lowerLeftVec.add(right.scalarMult(j + rightOffset)).add(up.scalarMult(i + upOffset));
                lightPositions.add(nextVector);
            }
        }
        return lightPositions;
    }

    /**
     * TODO: This will need to be expanded and modified
     *
     * @param intersection
     * @param ray
     * @return
     */
    public ComputationalColor getColor(Intersection intersection, Ray ray) {
        if (intersection == null) return backgroundColor;
        return this.shader.shade(intersection, ray, maxRecursionDepth);
    }

    /**
     * Calculats the effective theta of the fisheye lens
     *
     * @param radius radius of point ray to screen relative to origin
     * @return effective radius
     */
    private double calculateEffectiveTheta(double radius) {
        // calculate values we will most likely use
        double fishEyefactor = (this.camera.fisheyeCoeff() * radius) / this.camera.focalLength();
        if ((this.camera.fisheyeCoeff() > 0.0) && (this.camera.fisheyeCoeff() <= 1.0)) {
            return Math.atan(fishEyefactor) / this.camera.fisheyeCoeff();
        } else if ((this.camera.fisheyeCoeff() >= -1.0) && (this.camera.fisheyeCoeff() < 0.0)) {
            return Math.asin(fishEyefactor) / this.camera.fisheyeCoeff();
        } else {
            return radius / this.camera.focalLength();
        }
    }

    /**
     * Calculates what radius would a ray need to be fired if the point received is screenpoint
     *
     * @param direction
     * @param screenPoint
     * @return ray after changing to effective radius
     */
    private Ray calculateFisheyeRay(Vector3D direction, Vector3D screenPoint) {
        // Get what angle is required to recieve the effective radius
        double theta = calculateEffectiveTheta(screenPoint.subtract(this.viewport.getScreenCenter()).euclideanNorm());
        // We cant see behind or horizontal to the sensor
        if ((theta >= Math.PI / 2) || (theta <= -1 * Math.PI / 2)) {
            return null;
        }
        // We need to get the new point in the same direction
        Vector3D screenPointDirection = screenPoint.subtract(this.viewport.getScreenCenter()).normalize();
        double rEffective = this.camera.focalLength() * Math.tan(theta);
        Vector3D newPoint = screenPointDirection.scalarMult(rEffective).add(this.viewport.getScreenCenter());
        Vector3D newDirection = newPoint.subtract(this.camera.position());
        Ray ray = new Ray(this.camera.position(), newDirection);
        // new ray position on screen point
        return ray;
    }

    /**
     * Return the ambient light intensity as the average of all the colors in the scenes
     * Each color per light gets intensified in relation to its shadow intensity
     * @return
     */
    private ComputationalColor estimateAmbientIntensity() {
        ComputationalColor ambientLightIntensity = ComputationalColor.BLACK;
        for (Light light : this.lights) {
            ambientLightIntensity = ambientLightIntensity.add(light.getColor());
        }
        return ambientLightIntensity.scale(1.0 / lights.size());    
    }

    /**
     * Render the scene to a file
     */
    public BufferedImage renderScene() {
        BufferedImage img = new BufferedImage(viewport.getImageWidth(), viewport.getImageHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = viewport.getImageHeight(); y >= 1; --y) {
            for (int x = 1; x <= viewport.getImageWidth(); ++x) {

                // Find direction and screen point for current pixel
                Vector3D screenPoint = viewport.pixelToScreenPoint(x, y);
                Vector3D direction = screenPoint.subtract(this.camera.position());
                Ray ray;
                // If fisheye is enabled we need to correct the direction accordingly
                if (this.camera.fisheye()) {
                    ray = calculateFisheyeRay(direction, screenPoint);
                    if (ray == null) {
                        img.setRGB(x - 1, viewport.getImageHeight() - y, ComputationalColor.BLACK.getRGB());
                        continue;
                    }
                } else {
                    ray = new Ray(this.camera.position(), direction);
                }
                Intersection intersection = IntersectRay(ray);
                img.setRGB(x - 1, viewport.getImageHeight() - y, getColor(intersection, ray).clipColor().getRGB());
            }
        }
        return img;
    }
}
