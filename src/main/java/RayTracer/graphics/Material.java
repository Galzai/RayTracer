package RayTracer.graphics;

public class Material {
    private ComputationalColor diffuseColor;
    private ComputationalColor specularColor;
    private ComputationalColor reflectionColor;
    double phongSpecularityCoeff;
    double transparency;

    public Material(ComputationalColor diffuseColor, ComputationalColor specularColor, ComputationalColor reflectionColor, double phongSpecularityCoeff, double transparency) {
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
    public ComputationalColor getOutputColor(ComputationalColor backgroundColor) {
//        Color outputColor = Color.multiplyByFraction(Color.sumColors(diffuseColor, specularColor), 1 - transparency);
//        outputColor = Color.sumColors(outputColor, Color.multiplyByFraction(backgroundColor, transparency));
//        outputColor = Color.sumColors(outputColor, reflectionColor);
//        return outputColor;
        return new ComputationalColor(1,2,3);
    }


    public ComputationalColor getDiffuseColor() {
        return diffuseColor;
    }

    public ComputationalColor getSpecularColor() {
        return specularColor;
    }

    public ComputationalColor getReflectionColor() {
        return reflectionColor;
    }

    public double getPhongSpecularityCoeff() {
        return phongSpecularityCoeff;
    }

    public double getTransparency() {
        return transparency;
    }

}
