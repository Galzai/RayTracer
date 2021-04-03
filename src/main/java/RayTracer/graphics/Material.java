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
