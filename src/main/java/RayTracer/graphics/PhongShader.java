package RayTracer.graphics;

import RayTracer.math.Vector3D;

public class PhongShader {

    private Scene scene;

    public PhongShader(Scene scene) {
        this.scene = scene;
    }

    /**
     * Returns true if the light from the source to the point is shadowed
     *
     * @param intersection intersection data
     * @param light        light source to check
     * @return true if light is shaded
     */
    private boolean isShadowed(Intersection intersection, Light light) {

        // We cast a ray from the point to the light, if it intersects with anything that  means
        // that the point is shaded
        return scene.intersectionIsShadowed(intersection, light); // some object in between blocks the light
    }

    private double calcSoftShadowsPercentage(Intersection intersection, Light light) {

        // We cast a multiple rays from the point to the light's area and compute the shadow rays percentage
        return scene.calcLightPercentage(intersection, light); // some object in between blocks the light
    }

    /**
     * Calculates the results of diffuse and specular light contibuton to surface shading
     *
     * @param intersection intersection results
     * @param ray          light that found the intersection
     * @return light color
     */
    public ComputationalColor shadeDiffuseSpecular(Intersection intersection, Ray ray) {
        // our results
        ComputationalColor diffuseSpecular = new ComputationalColor(0,0,0);
        Material material = intersection.getSurface().getMaterial();
        // Check effect of each light in scene
        for (Light light : scene.getLights()) {
            double shadowIntensity = light.getShadowIntensity();
            double lightIntensity = 1;
            if (shadowIntensity != 0) {  // if shadowIntensity == 0 there is no effect on the shadows anyway
                double shadowPercentage = calcSoftShadowsPercentage(intersection, light);
                lightIntensity = 1 - shadowIntensity + shadowIntensity * shadowPercentage;
            }

            Vector3D lightDirection = light.getPosition().subtract(intersection.getIntersectionPoint()).normalize();
            // 2 * ( L dot N) * N
            Vector3D reflectionNormal = intersection.getNormal().scalarMult(2.0 *lightDirection.dotProduct(intersection.getNormal()));
            // R = 2 * ( V dot N) * N - L
            Vector3D reflectionDirection = reflectionNormal.subtract(lightDirection);

            // Specular and diffuse intensity
            Double diffuseIntensity = Math.max(intersection.getNormal().dotProduct(lightDirection), 0.0);
            Double specularIntensity = Math.pow(Math.max(ray.direction().scalarMult(-1.0).dotProduct(reflectionDirection), 0.0), material.getPhongSpecularityCoeff()) * light.getSpecularIntensity();

            ComputationalColor diffuse = material.getDiffuseColor().scale(diffuseIntensity);
            ComputationalColor specular = material.getSpecularColor().scale(specularIntensity);
            ComputationalColor lightColor = light.getColor().scale(lightIntensity);
            diffuseSpecular = diffuseSpecular.add(lightColor.mult(diffuse.add(specular)));
        }

        return diffuseSpecular;
    }

        /**
     * Calculates the results of light contribution to surface shading
     *
     * @param intersection intersection results
     * @param ray          light the found the intersection
     * @return light color
     */
    public ComputationalColor shadeReflection(Intersection intersection, Ray ray, Integer recursionDepth) {
        // 2 * ( V dot N) * N
        Vector3D reflectionNormal = intersection.getNormal().scalarMult(2.0 * ray.direction().dotProduct(intersection.getNormal()));
        // R = V - -2 * ( V dot N) * N
        Vector3D reflectionDirection = ray.direction().subtract(reflectionNormal).normalize();
        Ray reflectionRay = new Ray(intersection.getIntersectionPoint(), reflectionDirection);
        reflectionRay = reflectionRay.moveOriginByEpsilon();
        Intersection nextIntersection = scene.intersectRayWithoutSurface(reflectionRay, intersection.getSurface());
        return shade(nextIntersection, reflectionRay, recursionDepth - 1);
    }

    /**
     * Calculates the results of light contribution to surface shading
     *
     * @param intersection intersection results
     * @param ray          light the found the intersection
     * @return light color
     */
    public ComputationalColor shade(Intersection intersection, Ray ray, Integer recursionDepth) {
        if (intersection == null) {
            return scene.getBackgroundColor();
        }
        if (recursionDepth <= 0) {
            return new ComputationalColor(0.0, 0.0, 0.0);
        }
        Material material = intersection.getSurface().getMaterial();
        Double transparency = material.getTransparency();
        ComputationalColor result = shadeDiffuseSpecular(intersection, ray).scale(1.0 - transparency);
        // Reflection addition
        if (material.getReflectionColor() != ComputationalColor.BLACK) {  // only if material is reflective
            ComputationalColor reflection = shadeReflection(intersection, ray, recursionDepth).mult(material.getReflectionColor());
            result = result.add(reflection);
        }
        // transparency addition
        if (transparency > 0) { // only if surface is transparent
            Ray newRay = new Ray(intersection.getIntersectionPoint(), ray.direction());
            newRay = newRay.moveOriginByEpsilon();
            Intersection nextIntersection = scene.intersectRayWithoutSurface(newRay, intersection.getSurface());
            ComputationalColor transparentColor = shade(nextIntersection, newRay, recursionDepth).scale(transparency);
            result = result.add(transparentColor);
        }
        return result;
    }

}
