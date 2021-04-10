package RayTracer;

import RayTracer.geometry.AxisAlignedBox;
import RayTracer.geometry.Plane;
import RayTracer.geometry.Sphere;
import RayTracer.geometry.Surface;
import RayTracer.graphics.*;
import RayTracer.math.Vector3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for ray tracing exercise.
 */
public class RayTracer {

    private int imageWidth;
	private int imageHeight;

    public RayTracer() {
		// Default values:
		this.imageWidth = 500;
		this.imageHeight = 500;
	}

	public RayTracer(int imageWidth, int imageHeight ) {
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}

    /**
     * Runs the ray tracer. Takes scene file, output image file and image size as input.
     */
    public static void main(String[] args) {
        try {
            RayTracer tracer = new RayTracer();
            if (args.length < 2)
                throw new RayTracerException("Not enough arguments provided. Please specify an input scene file and an output image file for rendering.");

            String sceneFileName = args[0];
            String outputFileName = args[1];

            if (args.length > 3) {
                tracer.imageWidth = Integer.parseInt(args[2]);
                tracer.imageHeight = Integer.parseInt(args[3]);
            }
            // Parse scene file:
            Scene scene = tracer.parseScene(sceneFileName);
            // Render scene:
            tracer.renderScene(scene, outputFileName);

        } catch (RayTracerException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Parses the scene file and creates the scene. Change this function so it generates the required objects.
     */
    public Scene parseScene(String sceneFileName) throws IOException, RayTracerException {
        FileReader input = new FileReader(sceneFileName);
        BufferedReader buffRead = new BufferedReader(input);
        String line = null;
        Camera camera = null;
        java.util.List<Material> materials = new ArrayList<>();
        java.util.List<Surface> surfaces = new ArrayList<>();
        List<Light> lights = new ArrayList<>();
        int maxRecursionDepth = 0;
        double shadowRaysRoot = 0;
        ComputationalColor backGroundColor = null;
        boolean ambientEnabled = false;
        int lineNum = 0;

        System.out.println("Started parsing scene file " + sceneFileName);


        while ((line = buffRead.readLine()) != null) {
            line = line.trim();
            ++lineNum;

            if (line.isEmpty() || (line.charAt(0) == '#')) {  // This line in the scene file is a comment
                continue;
            }
            String code = line.substring(0, 3).toLowerCase();
            // Split according to white space characters:
            String[] params = line.substring(3).trim().toLowerCase().split("\\s+");

            switch (code) {
                case "cam":
                    camera = parseCamera(params, lineNum);
                    System.out.println(String.format("Parsed camera parameters (line %d)", lineNum));
                    break;

                case "set":
                    if (params.length < 5) {
                        throw new RayTracerException(String.format("ERROR: not enough parameters were given for settings (line %d)", lineNum));
                    }
                    backGroundColor = parseColor(params, 0, 1, 2);
                    shadowRaysRoot = Double.parseDouble(params[3]);
                    maxRecursionDepth = Integer.parseInt(params[4]);
                    if (params.length > 5) {
                        ambientEnabled = true;
                    }
                    System.out.println(String.format("Parsed general settings (line %d)", lineNum));
                    break;

                case "mtl":
                    materials.add(parseMaterial(params, lineNum, ambientEnabled));
                    System.out.println(String.format("Parsed material (line %d)", lineNum));
                    break;

                case "sph":
                    surfaces.add(parseSphere(params, lineNum, materials));
                    System.out.println(String.format("Parsed sphere (line %d)", lineNum));
                    break;

                case "pln":
                    surfaces.add(parsePlane(params, lineNum, materials));
                    System.out.println(String.format("Parsed plane (line %d)", lineNum));
                    break;

                case "box":
                    surfaces.add(parseBox(params, lineNum, materials));
                    System.out.println(String.format("Parsed Box (line %d)", lineNum));
                    break;

                case "lgt":
                    lights.add(parseLight(params, lineNum));
                    System.out.println(String.format("Parsed light (line %d)", lineNum));
                    break;

                default:
                    System.out.println(String.format("ERROR: Did not recognize object: %s (line %d)", code, lineNum));
            }

        }
        // TODO maybe move construction of viewPort inside of camera
		Viewport viewPort = new Viewport(imageWidth, imageHeight, camera);
		buffRead.close();
        System.out.println("Finished parsing scene file " + sceneFileName);
		return new Scene(camera, viewPort, backGroundColor, lights, surfaces, shadowRaysRoot, maxRecursionDepth, ambientEnabled);

	}

    /**
     * Renders the loaded scene and saves it to the specified file location.
     */
    public void renderScene(Scene scene, String outputFileName) throws IOException {
        long startTime = System.currentTimeMillis();
        BufferedImage image = scene.renderScene();
        long endTime = System.currentTimeMillis();
        Long renderTime = endTime - startTime;
        System.out.println("Finished rendering scene in " + renderTime.toString() + " milliseconds.");
        saveImage(image, outputFileName);
        System.out.println("Saved file " + outputFileName);
    }


    public void saveImage(BufferedImage image, String fileName) {
        try {
            ImageIO.write(image, "png", new File(fileName));

        } catch (IOException e) {
            System.out.println("ERROR SAVING FILE: " + e.getMessage());
        }

    }


    private Camera parseCamera(String[] params, int lineNum) {
        if (params.length < 11) {
            throw new RuntimeException(String.format("Not enough parameters were given for camera (line %d)", lineNum));
        }
        Vector3D position = parseVector(params, 0, 1, 2);
        Vector3D lookAt = parseVector(params, 3, 4, 5);
        Vector3D up = parseVector(params, 6, 7, 8);
        double focalLength = Double.parseDouble(params[9]);
        double screenWidth = Double.parseDouble(params[10]);
        boolean fisheye = false;  // default value
        double fisheyeCoeff = 0.5;  // default value
        if (params.length > 11) {
            fisheye = Boolean.parseBoolean(params[11]);
            if (params.length > 12) {
                fisheyeCoeff = Double.parseDouble(params[12]);
            }
        }
        return new Camera(position, lookAt, up, focalLength, screenWidth, fisheye, fisheyeCoeff);
    }

    private Material parseMaterial(String[] params, int lineNum, boolean ambientEnabled) throws RayTracerException {
        if (params.length < 11) {
            throw new RayTracerException(String.format("Not enough parameters were given for material (line %d)", lineNum));
        }
        ComputationalColor diffuse = parseColor(params, 0, 1, 2);
        ComputationalColor specular = parseColor(params, 3, 4, 5);
        ComputationalColor reflection = parseColor(params, 6, 7, 8);
        ComputationalColor ambient = null;
        double phongCoeff = Double.parseDouble(params[9]);
        double transparency = Double.parseDouble(params[10]);
        if (ambientEnabled) {
            ambient = parseColor(params, 11, 12, 13);
        }
        return new Material(diffuse, specular, reflection, phongCoeff, transparency, ambient);
    }

    private Sphere parseSphere(String[] params, int lineNum, List<Material> materials) throws RayTracerException {
        if (params.length < 5) {
            throw new RayTracerException(String.format("Not enough parameters were given for sphere (line %d)", lineNum));
        }
        Vector3D center = parseVector(params, 0, 1, 2);
        double radius = Double.parseDouble(params[3]);
        int materialIndex = Integer.parseInt(params[4]);
        if (materialIndex - 1 >= materials.size() || materialIndex < 0) {
            throw new RayTracerException(String.format("Wrong material index were given (lind %d)", lineNum));
        }
        return new Sphere(center, radius, materials.get(materialIndex - 1));
    }

    private Plane parsePlane(String[] params, int lineNum, List<Material> materials) throws RayTracerException {
        if (params.length < 5) {
            throw new RayTracerException(String.format("Not enough parameters were given for plane (line %d)", lineNum));
        }
        Vector3D normal = parseVector(params, 0, 1, 2);
        double offset = Double.parseDouble(params[3]);
        int materialIndex = Integer.parseInt(params[4]);
        if (materialIndex - 1 >= materials.size() || materialIndex < 0) {
            throw new RayTracerException(String.format("Wrong material index were given (lind %d)", lineNum));
        }
        return new Plane(normal, offset, materials.get(materialIndex - 1));
    }

    private AxisAlignedBox parseBox(String[] params, int lineNum, List<Material> materials) throws RayTracerException {
        if (params.length < 5) {
            throw new RayTracerException(String.format("Not enough parameters were given for box (line %d)", lineNum));
        }
        Vector3D center = parseVector(params, 0, 1, 2);
        double scale = Double.parseDouble(params[3]);
        int materialIndex = Integer.parseInt(params[4]);
        if (materialIndex - 1 >= materials.size() || materialIndex < 0) {
            throw new RayTracerException(String.format("Wrong material index were given (lind %d)", lineNum));
        }
        return new AxisAlignedBox(center, new Vector3D(scale, scale, scale), materials.get(materialIndex - 1));
    }

    private Light parseLight(String[] params, int lineNum) throws RayTracerException {
        if (params.length < 9) {
            throw new RayTracerException(String.format("Not enough parameters were given for light (line %d)", lineNum));
        }
        Vector3D position = parseVector(params, 0, 1, 2);
        ComputationalColor color = parseColor(params, 3, 4, 5);
        double specularIntensity = Double.parseDouble(params[6]);
        double shadowIntensity = Double.parseDouble(params[7]);
        double radius = Double.parseDouble(params[8]);
        return new Light(position, color, specularIntensity, shadowIntensity, radius);
    }

    private ComputationalColor parseColor(String[] params, int redIndex, int greenIndex, int blueIndex) {
        return new ComputationalColor(Double.parseDouble(params[redIndex]), Double.parseDouble(params[greenIndex]), Double.parseDouble(params[blueIndex]));
    }

    private Vector3D parseVector(String[] params, int xIndex, int yIndex, int zIndex) {
        return new Vector3D(Double.parseDouble(params[xIndex]), Double.parseDouble(params[yIndex]), Double.parseDouble(params[zIndex]));
    }

    public static class RayTracerException extends Exception {
        public RayTracerException(String msg) {
            super(msg);
        }
    }


}
