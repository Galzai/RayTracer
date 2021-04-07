import java.io.IOException;

import RayTracer.TestUtils;
import RayTracer.graphics.Scene;
import RayTracer.graphics.SceneParser;


public class detachedTest {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        SceneParser parser = new SceneParser();
        Scene scene = parser.parseScene(TestUtils.SCENES_PATH + "Pool.txt", 500, 500);
        scene.renderScene(TestUtils.OUTPUT_PATH + "pool.png");
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("elapsed time " + estimatedTime);
    }
}
