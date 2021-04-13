package RayTracer.graphics;

public class Material {
    private ComputationalColor diffuseColor;
    private ComputationalColor specularColor;
    private ComputationalColor reflectionColor;
    private ComputationalColor ambientColor;

    double phongSpecularityCoeff;
    double transparency;

    public Material(ComputationalColor diffuseColor, ComputationalColor specularColor, ComputationalColor reflectionColor, double phongSpecularityCoeff, double transparency, ComputationalColor ambientColor) {
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.reflectionColor = reflectionColor;
        this.phongSpecularityCoeff = phongSpecularityCoeff;
        this.transparency = transparency;
        this.ambientColor = ambientColor;
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

    public ComputationalColor getAmbientColor() {
        return ambientColor;
    }

    public double getPhongSpecularityCoeff() {
        return phongSpecularityCoeff;
    }

    public double getTransparency() {
        return transparency;
    }

}
