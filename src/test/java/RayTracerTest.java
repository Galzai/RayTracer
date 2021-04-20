import RayTracer.RayTracer;
import RayTracer.graphics.Scene;
import org.junit.jupiter.api.Test;
import RayTracer.TestUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RayTracerTest {
    @Test
    public void parsePoolSceneTest() throws IOException, RayTracer.RayTracerException {
        RayTracer tracer = new RayTracer();
        Scene scene = tracer.parseScene(TestUtils.SCENES_PATH + "Pool.txt");
        assertEquals(scene.getLights().size(), 5);
        assertEquals(scene.getSurfaces().size(), 7);
        assertEquals(scene.getLights().size(), 5);
        tracer.renderScene(scene, TestUtils.OUTPUT_PATH + "pool.png");
    }

    @Test
    public void parsePoolFisheyeSceneTest() throws IOException, RayTracer.RayTracerException {
        RayTracer tracer = new RayTracer();
        Scene scene = tracer.parseScene(TestUtils.SCENES_PATH + "Pool_fish.txt");
        tracer.renderScene(scene, TestUtils.OUTPUT_PATH + "pool_fish.png");

    }
    @Test
    public void parseSphereSceneTest() throws IOException, RayTracer.RayTracerException {
        RayTracer tracer = new RayTracer();
        Scene scene = tracer.parseScene(TestUtils.SCENES_PATH + "Spheres.txt");
        tracer.renderScene(scene,TestUtils.OUTPUT_PATH + "spheres.png");

    }

    @Test
    public void parseTransparencySceneTest() throws IOException, RayTracer.RayTracerException {
        RayTracer tracer = new RayTracer();
        Scene scene = tracer.parseScene(TestUtils.SCENES_PATH + "Transparency.txt");
        tracer.renderScene(scene, TestUtils.OUTPUT_PATH + "Transparency.png");

    }

    @Test
    public void parseRoomSceneTest() throws IOException, RayTracer.RayTracerException {
        RayTracer tracer = new RayTracer();
        Scene scene = tracer.parseScene(TestUtils.SCENES_PATH + "Room1.txt");
        tracer.renderScene(scene,TestUtils.OUTPUT_PATH + "Room1.png");

    }

    @Test
    public void parseRoomSceneFishTest() throws IOException, RayTracer.RayTracerException {
        RayTracer tracer = new RayTracer();
        Scene scene = tracer.parseScene(TestUtils.SCENES_PATH + "Room1_fish.txt");
        tracer.renderScene(scene,TestUtils.OUTPUT_PATH + "Room1_fish.png");

    }

    @Test
    public void parseMirrorSceneTest() throws IOException, RayTracer.RayTracerException {
        RayTracer tracer = new RayTracer();
        Scene scene = tracer.parseScene(TestUtils.SCENES_PATH + "Mirror.txt");
        tracer.renderScene(scene,TestUtils.OUTPUT_PATH + "Mirror.png");

    }


    @Test
    public void parseBoxSceneTest() throws IOException, RayTracer.RayTracerException {
        RayTracer tracer = new RayTracer();
        Scene scene = tracer.parseScene(TestUtils.SCENES_PATH + "chess.txt");
        tracer.renderScene(scene,TestUtils.OUTPUT_PATH + "star.png");

    }
    @Test
    public void parseAmbientPoolSceneTest() throws IOException, RayTracer.RayTracerException {
        RayTracer tracer = new RayTracer();
        Scene scene = tracer.parseScene(TestUtils.SCENES_PATH + "PoolAmbient.txt");
        assertEquals(scene.getLights().size(), 5);
        assertEquals(scene.getSurfaces().size(), 7);
        assertEquals(scene.getLights().size(), 5);
        tracer.renderScene(scene, TestUtils.OUTPUT_PATH + "PoolAmbient.png");
    }

    @Test
    public void parseChessTest() throws IOException, RayTracer.RayTracerException {
        RayTracer tracer = new RayTracer(1000, 1000);
        Scene scene = tracer.parseScene(TestUtils.SCENES_PATH + "chess.txt");
        tracer.renderScene(scene, TestUtils.OUTPUT_PATH + "chess.png");
    }
}
