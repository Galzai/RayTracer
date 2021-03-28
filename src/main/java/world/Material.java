package world;

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

//    public main.Color getOutputColor(main.Color backgroundColor) {
////        int red = backgroundColor.getRed() * transparency
//    }


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
