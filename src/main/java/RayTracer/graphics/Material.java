package RayTracer.graphics;
import java.awt.Color;

public class Material {
    private Color diffuseColor;
    private Color specularColor;
    private Color reflectionColor;
    double phongSpecularityCoeff;
    double transparency;

    public Material(Color diffuseColor, Color specularColor, Color reflectionColor, double phongSpecularityCoeff, double transparency) {
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.reflectionColor = reflectionColor;
        this.phongSpecularityCoeff = phongSpecularityCoeff;
        this.transparency = transparency;
    }

    /**
     *
     * @param backgroundColor The background color of the scene
     * @return The output color according to the formula:
     *              output = (background * transparency) + (diffuse + specular) * (1 - transparency) + reflection
     *
     * TODO: implement with vector calculations!
     */
    public Color getOutputColor(Color backgroundColor) {
//        Color outputColor = Color.multiplyByFraction(Color.sumColors(diffuseColor, specularColor), 1 - transparency);
//        outputColor = Color.sumColors(outputColor, Color.multiplyByFraction(backgroundColor, transparency));
//        outputColor = Color.sumColors(outputColor, reflectionColor);
//        return outputColor;
        return new Color(1,2,3);
    }


    public Color getDiffuseColor() {
        return diffuseColor;
    }

    public Color getSpecularColor() {
        return specularColor;
    }

    public Color getReflectionColor() {
        return reflectionColor;
    }

    public double getPhongSpecularityCoeff() {
        return phongSpecularityCoeff;
    }

    public double getTransparency() {
        return transparency;
    }

}
