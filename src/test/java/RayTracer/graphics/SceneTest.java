package RayTracer.graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.io.IOException;

import RayTracer.TestUtils;
import RayTracer.geometry.AxisAlignedBox;
import org.junit.jupiter.api.Test;

import RayTracer.geometry.Sphere;
import RayTracer.math.Vector3D;
public class SceneTest {

    @Test
    public void basicSceneTest() throws IOException{
        int imageWidth = 400;
        int imageHeight = 225;
        double screenWidth = 10;
        Vector3D origin = new Vector3D(0,10,0);
        Vector3D lookAt = new Vector3D(0,-100,0);
        Vector3D up = new Vector3D(0,0,-1);
        Camera camera = new Camera(origin, lookAt, up, 5, false);
        Viewport viewport = new Viewport(screenWidth, imageWidth, imageHeight, camera);

        ComputationalColor dYellowColor = new ComputationalColor(0.95, 0.95, 0.07);
        ComputationalColor sYellowColor = new ComputationalColor(1.0, 1.0, 1.0);
        Material yellowMaterial = new Material(dYellowColor, sYellowColor, null, 30.0, 0.0);

        ComputationalColor dBlackColor = new ComputationalColor(0.1, 0.1, 0.1);
        Material BlackMaterial = new Material(dBlackColor, sYellowColor, null, 30.0, 0.0);

        ComputationalColor dWhiteColor = new ComputationalColor(0.95, 0.95, 0.07);
        ComputationalColor sWhiteColor = new ComputationalColor(0.5, 0.5, 0.5);
        Material whiteMaterial = new Material(dWhiteColor, sWhiteColor, null, 30.0, 0.0);

        Sphere yellow = new Sphere(new Vector3D(0.0,    0.0, -4.0), 1, yellowMaterial);
        Sphere black = new Sphere(new Vector3D(1.0,    0.0, -2.0), 1, BlackMaterial);
        Sphere white = new Sphere(new Vector3D(-1.0,    0.0, -2.0), 1, whiteMaterial);

        Vector3D lightPosition1 = new Vector3D(0.0, 3.0, 0.0);
        ComputationalColor lightColor = new ComputationalColor(0.5, 0.5, 0.3);
        Light light = new Light(lightPosition1, lightColor, 1.0, 0.9, 1.0);

        Vector3D lightPosition2 = new Vector3D(0.0, 0.0, 8.0);
        ComputationalColor lightColor2 = new ComputationalColor(0.2, 0.5, 0.3);
        Light light2 = new Light(lightPosition2, lightColor2, 1.0, 0.5, 1.0);

        ComputationalColor backgroundColor = new ComputationalColor(0.07, 0.07, 0.95);
        Scene scene = new Scene((camera), viewport, backgroundColor);
        scene.addLight(light);
        scene.addLight(light2);
        scene.addSurface(yellow);
        scene.addSurface(black);
        scene.addSurface(white);
        scene.renderScene("C:\\Users\\purec\\Desktop\\raytest\\pooltest.png");

    }


    @Test
    public void boxesSceneTest() throws IOException{
        int imageWidth = 400;
        int imageHeight = 225;
        double screenWidth = 10;
        Vector3D origin = new Vector3D(0,2,0);
        Vector3D lookAt = new Vector3D(0,0,5);
        Vector3D up = new Vector3D(0,1,0);
        Camera camera = new Camera(origin, lookAt, up, 5, false);
        Viewport viewport = new Viewport(screenWidth, imageWidth, imageHeight, camera);

        ComputationalColor dYellowColor = new ComputationalColor(0.95, 0.95, 0.07);
        ComputationalColor sYellowColor = new ComputationalColor(1.0, 1.0, 1.0);
        Material yellowMaterial = new Material(dYellowColor, sYellowColor, null, 30.0, 0.0);
        ComputationalColor dBlackColor = new ComputationalColor(0.1, 0.1, 0.1);
        Material BlackMaterial = new Material(dBlackColor, sYellowColor, null, 30.0, 0.0);
        ComputationalColor dWhiteColor = new ComputationalColor(0.95, 0.95, 0.07);
        ComputationalColor sWhiteColor = new ComputationalColor(0.5, 0.5, 0.5);
        Material whiteMaterial = new Material(dWhiteColor, sWhiteColor, null, 30.0, 0.0);


        ComputationalColor dpinkColor =  new ComputationalColor(255,20,147);
        ComputationalColor spinkColor =  new ComputationalColor(0.5, 0.5, 0.5);;
        Material pinkMaterial = new Material(dpinkColor, spinkColor, null, 30.0, 0.0);


        AxisAlignedBox box = new AxisAlignedBox(new Vector3D(0,0,5), new Vector3D(5,3,4), new Vector3D(0,0,0), pinkMaterial);


        Vector3D lightPosition1 = new Vector3D(5, 0, 5);
        ComputationalColor lightColor = new ComputationalColor(255,192,203);
        Light light = new Light(lightPosition1, lightColor, 1.0, 0.9, 1.0);

        Vector3D lightPosition2 = new Vector3D(0.0, 1.0, 0.0);
        ComputationalColor lightColor2 = new ComputationalColor(255,192,203);
        Light light2 = new Light(lightPosition2, lightColor2, 1.0, 0.5, 1.0);

        ComputationalColor backgroundColor = new ComputationalColor(0.47, 0.27, 0.95);
        Scene scene = new Scene((camera), viewport, backgroundColor);
        scene.addLight(light);
        scene.addLight(light2);
        scene.addSurface(box);

        scene.renderScene(TestUtils.getOutputPath() + "box.png");

    }
    
}
