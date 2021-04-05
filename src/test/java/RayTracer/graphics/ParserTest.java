package RayTracer.graphics;

import RayTracer.TestUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void parsePoolSceneTest() throws IOException {
        SceneParser parser = new SceneParser();
        Scene scene = parser.parseScene(TestUtils.SCENES_PATH + "Pool.txt", 500, 500);
        assertEquals(scene.getLights().size(), 5);
        assertEquals(scene.getSurfaces().size(), 7);
        assertEquals(scene.getLights().size(), 5);
        scene.renderScene(TestUtils.OUTPUT_PATH + "pool.png");

    }
    @Test
    public void parseSphereSceneTest() throws IOException {
        SceneParser parser = new SceneParser();
        Scene scene = parser.parseScene(TestUtils.SCENES_PATH + "Spheres.txt", 500, 500);
        scene.renderScene(TestUtils.OUTPUT_PATH + "spheres.png");

    }

    @Test
    public void parseTransparencySceneTest() throws IOException {
        SceneParser parser = new SceneParser();
        Scene scene = parser.parseScene(TestUtils.SCENES_PATH + "Transparency.txt", 500, 500);
        scene.renderScene(TestUtils.OUTPUT_PATH + "Transparency.png");

    }

    @Test
    public void parseRoomSceneTest() throws IOException {
        SceneParser parser = new SceneParser();
        Scene scene = parser.parseScene(TestUtils.SCENES_PATH + "Room1.txt", 500, 500);
        scene.renderScene(TestUtils.OUTPUT_PATH + "Room1.png");

    }


    @Test
    public void parseMirrorSceneTest() throws IOException {
        SceneParser parser = new SceneParser();
        Scene scene = parser.parseScene(TestUtils.SCENES_PATH + "Mirror.txt", 500, 500);
        scene.renderScene(TestUtils.OUTPUT_PATH + "Mirror.png");

    }
}
