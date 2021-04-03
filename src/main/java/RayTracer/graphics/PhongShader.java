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
        return !scene.hasDirectView(intersection, light); // some object in between blocks the light
    }

    /**
     * Calculates the results of light contibuton to surface shading
     *
     * @param intersection intersection results
     * @param ray          light the found the intersection
     * @return light color
     */
    public ComputationalColor shade(Intersection intersection, Ray ray) {

        // our results
        Double red = 0.0;
        Double green = 0.0;
        Double blue = 0.0;

        Material material = intersection.getSurface().getMaterial();

        // Check effect of each light in scene
        for (Light light : scene.getLights()) {
            Double shadowMultiplier = 1.0;
            if (isShadowed(intersection, light)) {
                shadowMultiplier = 1.0 - light.getShadowIntensity();
            }
            Vector3D lightDirection = light.getPosition().subtract(intersection.getIntersectionPoint()).normalize();
            // H = L + V
            Vector3D highlightVec = (ray.direction().scalarMult(-1.0)).add(lightDirection).normalize();

            // Specular and diffuse intensity
            Double diffuseIntensity = Math.max(intersection.getNormal().dotProduct(lightDirection), 0.0);
            Double specularIntensity = Math.pow(Math.max(intersection.getNormal().dotProduct(highlightVec), 0.0), material.getPhongSpecularityCoeff()) * light.getSpecularIntensity();
            
            red +=
                    shadowMultiplier * light.getColor().getRed() *
                            ((material.getDiffuseColor().getRed() * diffuseIntensity) +
                                    (material.getSpecularColor().getRed() * specularIntensity));

            green +=
                    shadowMultiplier * light.getColor().getGreen() *
                            ((material.getDiffuseColor().getGreen() * diffuseIntensity) +
                                    (material.getSpecularColor().getGreen() * specularIntensity));

            blue +=
                    shadowMultiplier * light.getColor().getBlue() *
                            ((material.getDiffuseColor().getBlue() * diffuseIntensity) +
                                    (material.getSpecularColor().getBlue() * specularIntensity));

        }
        //TODO clip the color after all computation is done
        return new ComputationalColor(red, green, blue);
    }

}
