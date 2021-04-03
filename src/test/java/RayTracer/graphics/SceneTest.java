package RayTracer.graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import RayTracer.TestUtils;
import RayTracer.geometry.AxisAlignedBox;
import RayTracer.geometry.OrientedBox;
import RayTracer.geometry.Plane;
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
        Camera camera = new Camera(origin, lookAt, up, 5, screenWidth);
        Viewport viewport = new Viewport(imageWidth, imageHeight, camera);

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
        scene.renderScene(TestUtils.OUTPUT_PATH + "pooltest.png");

    }


    @Test
    public void AABBSceneTest() throws IOException{
        int imageWidth = 400;
        int imageHeight = 225;
        double screenWidth = 10;
        Vector3D origin = new Vector3D(1,2,1);
        Vector3D lookAt = new Vector3D(1,0,5);
        Vector3D up = new Vector3D(2,1,3);
        Camera camera = new Camera(origin, lookAt, up, 4, screenWidth);
        Viewport viewport = new Viewport(imageWidth, imageHeight, camera);

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


        AxisAlignedBox box = new AxisAlignedBox(new Vector3D(0,0,7), new Vector3D(5,3,4), pinkMaterial);
        Sphere sphere = new Sphere(new Vector3D(0.0,    3.0, 7.0), 1, whiteMaterial);


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
        scene.addSurface(sphere);

        scene.renderScene(TestUtils.OUTPUT_PATH + "box.png");

    }



    @Test
    public void NiceSceneTest() throws IOException{
        int imageWidth = 400;
        int imageHeight = 225;
        double screenWidth = 10;
        Vector3D origin = new Vector3D(-2,-2,-2);
        Vector3D lookAt = new Vector3D(-1,0,0);
        Vector3D up = new Vector3D(0,-1,0);
        Camera camera = new Camera(origin, lookAt, up, 3, screenWidth);
        Viewport viewport = new Viewport(imageWidth, imageHeight, camera);

        ComputationalColor dYellowColor = new ComputationalColor(0.95, 0.95, 0.07);
        ComputationalColor sYellowColor = new ComputationalColor(1.0, 1.0, 1.0);
        Material yellowMaterial = new Material(dYellowColor, sYellowColor, null, 30.0, 0.0);
        ComputationalColor dBlackColor = new ComputationalColor(0.1, 0.1, 0.1);
        Material blackMaterial = new Material(dBlackColor, sYellowColor, null, 30.0, 0.0);
        ComputationalColor dWhiteColor = new ComputationalColor(0.2, 0.1, 0.4);
        ComputationalColor sWhiteColor = new ComputationalColor(0.5, 0.5, 0.5);
        Material whiteMaterial = new Material(dWhiteColor, sWhiteColor, null, 30.0, 0.0);
        ComputationalColor dpinkColor =  new ComputationalColor(255,20,147);
        ComputationalColor spinkColor =  new ComputationalColor(0.5, 0.5, 0.5);
        ComputationalColor drandomColor =  new ComputationalColor(10,100,187);
        ComputationalColor srandomColor =  new ComputationalColor(0.1, 0.2, 0.4);


        Material pinkMaterial = new Material(dpinkColor, spinkColor, null, 30.0, 0.0);
        Material randomMaterial = new Material(drandomColor, srandomColor, null, 20, 0.0);

        OrientedBox OBB = new OrientedBox(new Vector3D(0,0,5), new Vector3D(2,2,2), new Vector3D(Math.PI / 3,Math.PI / 5,Math.PI / 4), pinkMaterial);
        AxisAlignedBox AABB = new AxisAlignedBox(new Vector3D(1, 2, 3), new Vector3D(1,2,3), randomMaterial);
        Sphere sphere1 = new Sphere(new Vector3D(3,3,3), 2, yellowMaterial);
        Plane plane = new Plane(new Vector3D(-5,2,-5), 4, whiteMaterial);
        Sphere sphere2 = new Sphere(new Vector3D(0, 5, 5), 3, blackMaterial);



        Vector3D lightPosition1 = new Vector3D(5, 0, 5);
        ComputationalColor lightColor = new ComputationalColor(255,192,203);
        Light light = new Light(lightPosition1, lightColor, 1.0, 0.9, 1.0);

        Vector3D lightPosition2 = new Vector3D(0.0, 1.0, 0.0);
        ComputationalColor lightColor2 = new ComputationalColor(255,192,203);
        Light light2 = new Light(lightPosition2, lightColor2, 1.0, 0.5, 1.0);

        Vector3D lightPosition3 = new Vector3D(-1, -5, 3);
        ComputationalColor lightColor3 = new ComputationalColor(2,192,203);
        Light light3 = new Light(lightPosition3, lightColor3, 0.5, 0.5, 0.1);

        ComputationalColor backgroundColor = new ComputationalColor(0.47, 0.27, 0.95);
        Scene scene = new Scene((camera), viewport, backgroundColor);
        scene.addLight(light);
        scene.addLight(light2);
        scene.addLight(light3);
        scene.addSurface(sphere1);
        scene.addSurface(sphere2);
        scene.addSurface(OBB);
        scene.addSurface(AABB);
        scene.addSurface(plane);

        scene.renderScene(TestUtils.OUTPUT_PATH + "scene.png");

    }

    @Test
    public void boxSceneTest() throws IOException{
        int imageWidth = 400;
        int imageHeight = 225;
        double screenWidth = 10;
        Vector3D origin = new Vector3D(0,0,0);
        Vector3D lookAt = new Vector3D(0,0,1);
        Vector3D up = new Vector3D(0,
                1,0);
        Camera camera = new Camera(origin, lookAt, up, 3, screenWidth);
        Viewport viewport = new Viewport(imageWidth, imageHeight, camera);


        ComputationalColor dWhiteColor = new ComputationalColor(0.2, 0.1, 0.4);
        ComputationalColor sWhiteColor = new ComputationalColor(0.5, 0.5, 0.5);
        Material whiteMaterial = new Material(dWhiteColor, sWhiteColor, null, 30.0, 0.0);
        ComputationalColor dpinkColor =  new ComputationalColor(255,20,147);
        ComputationalColor spinkColor =  new ComputationalColor(0.5, 0.5, 0.5);
        Material pinkMaterial = new Material(dpinkColor, spinkColor, null, 30.0, 0.0);

        OrientedBox OBB = new OrientedBox(new Vector3D(0,0,5), new Vector3D(2,2,2), new Vector3D(0,0,0), pinkMaterial);
        AxisAlignedBox AABB = new AxisAlignedBox(new Vector3D(0,0,5), new Vector3D(2,2,2), pinkMaterial);



        Vector3D lightPosition1 = new Vector3D(0, 0, -5);
        ComputationalColor lightColor = new ComputationalColor(255,192,203);
        Light light = new Light(lightPosition1, lightColor, 1.0, 0.9, 1.0);

        ComputationalColor backgroundColor = new ComputationalColor(0.47, 0.27, 0.95);
        Scene scene = new Scene((camera), viewport, backgroundColor);
        scene.addLight(light);
        scene.addSurface(AABB);


        scene.renderScene(TestUtils.OUTPUT_PATH+ "box.png");

    }
    
}
