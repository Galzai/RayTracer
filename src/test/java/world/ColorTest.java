package world;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColorTest {
    @Test
    public void colorInitialization() {
        Color intColor = new Color(255,128,255);
        assertEquals(intColor.getRed(), 1);
        assertEquals(intColor.getGreen(), 128/255.0);

        Color fColor = new Color(1.0, 0.5, 1.0);
        assertEquals(fColor.getRed(), 1);





    }
}
