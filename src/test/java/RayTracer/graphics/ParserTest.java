package RayTracer.graphics;

import RayTracer.TestUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void parseSceneTest() throws IOException {
        SceneParser parser = new SceneParser();
        Scene scene = parser.parseScene(TestUtils.SCENES_PATH + "Pool.txt", 500, 500);
        assertEquals(scene.getLights().size(), 5);
        assertEquals(scene.getSurfaces().size(), 7);
        assertEquals(scene.getLights().size(), 5);
        scene.renderScene(TestUtils.OUTPUT_PATH + "pool.png");

    }
}
