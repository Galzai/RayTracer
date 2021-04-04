package RayTracer.graphics;

import RayTracer.geometry.AxisAlignedBox;
import RayTracer.geometry.Plane;
import RayTracer.geometry.Sphere;
import RayTracer.geometry.Surface;
import RayTracer.math.Vector3D;
import RayTracer.graphics.Viewport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SceneParser {
    String[] currentLine = null;

    public Scene parseScene(String filepath, int imageWidth, int imageHeight) throws IOException {
        FileReader input = new FileReader(filepath);
        BufferedReader buffRead = new BufferedReader(input);
        String line = null;
        Camera camera = null;
        List<Material> materials = new ArrayList<>();
        List<Surface> surfaces = new ArrayList<>();
        List<Light> lights = new ArrayList<>();
        int maxRecursionDepth = 0;
        double shadowRaysRoot = 0;
        ComputationalColor backGroundColor = null;


        while ((line = buffRead.readLine()) != null) {
            line = line.trim();
            if (line.length() == 0 || line.charAt(0) == '#' || line == System.lineSeparator()) {  // skip comments and empty lines
                continue;
            }
            this.currentLine = line.split("\\s+"); // split by whitespaces

            switch (currentLine[0]) {
                case "cam":
                    camera = parseCamera();
                    break;
                case "set":
                    backGroundColor = parseColor(1, 2, 3);
                    shadowRaysRoot = Double.parseDouble(currentLine[4]);
                    maxRecursionDepth = Integer.parseInt(currentLine[5]);
                    break;
                case "mtl":
                    materials.add(parseMaterial());
                    break;
                case "sph":
                    surfaces.add(parseSphere(materials));
                    break;
                case "pln":
                    surfaces.add(parsePlane(materials));
                    break;
                case "box":
                    surfaces.add(parseBox(materials));
                    break;
                case "lgt":
                    lights.add(parseLight());
                    break;
                default:
                    //TODO throw exception
            }

        }
        Viewport viewPort = new Viewport(imageWidth, imageHeight, camera);
        buffRead.close();
        return new Scene(camera, viewPort, backGroundColor, lights, surfaces, shadowRaysRoot, maxRecursionDepth);

    }

    private Camera parseCamera() {
        Vector3D position = parseVector(1, 2, 3);
        Vector3D lookAt = parseVector(4, 5, 6);
        Vector3D up = parseVector(7, 8, 9);
        double focalLength = Double.parseDouble(currentLine[10]);
        double screenWidth = Double.parseDouble(currentLine[11]);
        boolean fisheye = false;  // default value
        double fisheyeCoeff = 0.5;  // default value
        if (currentLine.length > 12) {
            fisheye = Boolean.parseBoolean(currentLine[12]);
            if (currentLine.length > 13) {
                fisheyeCoeff = Double.parseDouble(currentLine[13]);
            }
        }
        return new Camera(position, lookAt, up, focalLength, screenWidth, fisheye, fisheyeCoeff);
    }

    private Material parseMaterial() {
        ComputationalColor diffuse = parseColor(1, 2, 3);
        ComputationalColor specular = parseColor(4, 5, 6);
        ComputationalColor reflection = parseColor(7, 8, 9);
        double phongCoeff = Double.parseDouble(currentLine[10]);
        double transparency = Double.parseDouble(currentLine[11]);
        return new Material(diffuse, specular, reflection, phongCoeff, transparency);
    }

    private Sphere parseSphere(List<Material> materials) {
        Vector3D center = parseVector(1, 2, 3);
        double radius = Double.parseDouble(currentLine[4]);
        int materialIndex = Integer.parseInt(currentLine[5]);
        return new Sphere(center, radius, materials.get(materialIndex - 1));
    }

    private Plane parsePlane(List<Material> materials) {
        Vector3D normal = parseVector(1, 2, 3);
        double offset = Double.parseDouble(currentLine[4]);
        int materialIndex = Integer.parseInt(currentLine[5]);
        return new Plane(normal, offset, materials.get(materialIndex - 1));
    }

    private AxisAlignedBox parseBox(List<Material> materials) {
        Vector3D center = parseVector(1, 2, 3);
        double scale = Double.parseDouble(currentLine[4]);
        int materialIndex = Integer.parseInt(currentLine[5]);
        return new AxisAlignedBox(center, new Vector3D(scale, scale, scale), materials.get(materialIndex - 1));
    }

    private Light parseLight() {
        Vector3D position = parseVector(1, 2, 3);
        ComputationalColor color = parseColor(4, 5, 6);
        double specularIntensity = Double.parseDouble(currentLine[7]);
        double shadowIntensity = Double.parseDouble(currentLine[8]);
        double radius = Double.parseDouble(currentLine[9]);
        return new Light(position, color, specularIntensity, shadowIntensity, radius);
    }

    private ComputationalColor parseColor(int redIndex, int greenIndex, int blueIndex) {
        return new ComputationalColor(Double.parseDouble(currentLine[redIndex]), Double.parseDouble(currentLine[greenIndex]), Double.parseDouble(currentLine[blueIndex]));
    }

    private Vector3D parseVector(int xIndex, int yIndex, int zIndex) {
        return new Vector3D(Double.parseDouble(currentLine[xIndex]), Double.parseDouble(currentLine[yIndex]), Double.parseDouble(currentLine[zIndex]));
    }
}
