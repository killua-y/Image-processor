import org.junit.Before;
import org.junit.Test;

import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This is a class to test the Pixel class.
 */
public class PixelTest {
  Pixel p0;
  Pixel p1;

  @Before
  public void setup() {
    p0 = new Pixel(0, 0, 0);
    p1 = new Pixel(1, 2, 3);
  }

  @Test
  public void constructorTest() {
    try {
      Pixel test1 = new Pixel(-1, 1, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("RGB cannot be negative.", e.getMessage());
    }
    try {
      Pixel test1 = new Pixel(1, -10, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("RGB cannot be negative.", e.getMessage());
    }
    try {
      Pixel test1 = new Pixel(1, 1, -40);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("RGB cannot be negative.", e.getMessage());
    }
  }

  @Test
  public void testGetColor() {
    assertEquals(1, p1.getRed());
    assertEquals(2, p1.getGreen());
    assertEquals(3, p1.getBlue());
  }

  @Test
  public void testToString() {
    assertEquals("1 2 3 ", p1.toString());
    assertEquals("0 0 0 ", p0.toString());
  }

  @Test
  public void testIsEquai() {
    Pixel t1 = new Pixel(1, 2, 3);
    assertEquals(true, t1.isEqual(p1));
    assertEquals(false, t1.isEqual(p0));
  }

  @Test
  public void testOverCap() {
    Pixel t1 = new Pixel(256, 1, 255);
    Pixel t2 = new Pixel(255, 1, 255);
    assertEquals(true, t1.overCap(255));
    assertEquals(false, p1.overCap(255));
    assertEquals(false, t2.overCap(255));
  }

  @Test
  public void testBrighten() {
    // p1 = (1, 2, 3);
    assertEquals("254 255 255 ", p1.brighten(253, 255).toString());
    assertEquals("11 12 13 ", p1.brighten(10, 255).toString());
    assertEquals("0 1 2 ", p1.brighten(-1, 255).toString());
    assertEquals("0 0 1 ", p1.brighten(-2, 255).toString());
    assertEquals("1 2 3 ", p1.brighten(0, 255).toString());
  }

  @Test
  public void Visualization() {
    // red
    assertEquals("1 1 1 ", p1.visualize("red-component", 255).toString());
    // green
    assertEquals("2 2 2 ", p1.visualize("green-component", 255).toString());
    // blue
    assertEquals("3 3 3 ", p1.visualize("blue-component", 255).toString());
    // value
    assertEquals("3 3 3 ", p1.visualize("value-component", 255).toString());
    // intensity
    assertEquals("2 2 2 ", p1.visualize("intensity-component", 255).toString());
    // luma
    assertEquals("0 1 0 ", p1.visualize("luma-component", 255).toString());
    // throw
    try {
      p1.visualize("something else", 255);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid visualization type", e.getMessage());
    }
  }
}