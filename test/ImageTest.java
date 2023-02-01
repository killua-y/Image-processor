import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import model.Image;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class is for testing the Image class.
 */
public class ImageTest {
  Pixel p1;
  Pixel pred;
  Pixel pgreen;
  Pixel pblue;
  Pixel[][] plist1;
  Pixel[][] plist2;
  Image i1;
  Image i2;

  @Before
  public void setup() {
    p1 = new Pixel(1, 2, 3);
    pred = new Pixel(255, 0, 0);
    pgreen = new Pixel(0, 255, 0);
    pblue = new Pixel(0, 0, 255);

    plist1 = new Pixel[][]{{p1, pred, pgreen, pblue}, {p1, pred, pgreen, pblue},
      {p1, pred, pgreen, pblue}, {p1, pred, pgreen, pblue}};
    plist2 = new Pixel[][]{{pred, pgreen, pblue}, {p1, p1, p1}, {p1, p1, pblue}};

    i1 = new Image(4, 4, 255, plist1);
    i2 = new Image(3, 3, 255, plist2);
  }

  @Test
  public void testConstructor() {
    Pixel overcapp = new Pixel(256, 1, 2);
    Pixel[][] pixels1 = new Pixel[][]{{p1, pred, pgreen}, {p1, pred, overcapp}, {p1, pred, pgreen}};
    Pixel[][] pixels2 = new Pixel[][]{{p1, pred, pgreen}, {p1, pred}, {p1, pred, pgreen}};
    Pixel[][] pixels3 = new Pixel[][]{{p1, pred, pgreen}, {p1, pred, pgreen},
      {p1, pred, pgreen}, {p1, pred, pgreen}};
    // for negative dimensions, cap, or pixels
    try {
      Image t1 = new Image(1, -1, 255, plist1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Image cannot have negative dimensions, cap, or pixels", e.getMessage());
    }
    try {
      Image t1 = new Image(-1, 1, 255, plist1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Image cannot have negative dimensions, cap, or pixels", e.getMessage());
    }
    try {
      Image t1 = new Image(1, -1, -255, plist1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Image cannot have negative dimensions, cap, or pixels", e.getMessage());
    }
    try {
      Image t1 = new Image(1, 1, 255, null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Image cannot have negative dimensions, cap, or pixels", e.getMessage());
    }

    // for over cap pixel.
    try {
      Image t1 = new Image(3, 3, 255, pixels1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Pixel is over the cap.", e.getMessage());
    }
    try {
      Image t1 = new Image(3, 3, 255, pixels2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Image dimensions are not a square.", e.getMessage());
    }

    // for dimensions are not a square.
    try {
      Image t1 = new Image(3, 3, 255, pixels3);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Image dimensions are not a square.", e.getMessage());
    }
  }

  @Test
  public void testToString() {
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n", i1.toString());
  }

  @Test
  public void testGetPixel() {
    // for throw
    try {
      i1.getPixel(4, 3);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The given pixel coordinates don't exist.", e.getMessage());
    }
    try {
      i1.getPixel(3, 4);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The given pixel coordinates don't exist.", e.getMessage());
    }
    try {
      i1.getPixel(-1, 3);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The given pixel coordinates don't exist.", e.getMessage());
    }
    try {
      i1.getPixel(3, -1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The given pixel coordinates don't exist.", e.getMessage());
    }

    // for test works
    assertEquals("0 0 255 ", i1.getPixel(3, 3).toString());
    assertEquals("1 2 3 ", i1.getPixel(0, 0).toString());
  }

  @Test
  public void testGet() {
    assertEquals(4, i1.getImageHeight());
    assertEquals(4, i1.getImageWidth());
    assertEquals(255, i1.getCap());
  }

  @Test
  public void testFlip() {
    // the image before flip
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n", i1.toString());
    // horizontal
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n", i1.flipHorizontal().toString());

    // vertical
    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "1 2 3 1 2 3 0 0 255 \n" +
            "1 2 3 1 2 3 1 2 3 \n" +
            "255 0 0 0 255 0 0 0 255 \n", i2.flipVertical().toString());

  }

  @Test
  public void testBrighten() {
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "11 12 13 255 10 10 10 255 10 10 10 255 \n" +
            "11 12 13 255 10 10 10 255 10 10 10 255 \n" +
            "11 12 13 255 10 10 10 255 10 10 10 255 \n" +
            "11 12 13 255 10 10 10 255 10 10 10 255 \n", i1.brighten(10).toString());

    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "0 0 0 155 0 0 0 155 0 0 0 155 \n" +
            "0 0 0 155 0 0 0 155 0 0 0 155 \n" +
            "0 0 0 155 0 0 0 155 0 0 0 155 \n" +
            "0 0 0 155 0 0 0 155 0 0 0 155 \n", i1.brighten(-100).toString());

    assertEquals(i1.toString(), i1.brighten(0).toString());
  }

  @Test
  public void testVisualization() {
    // red
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "1 1 1 255 255 255 0 0 0 0 0 0 \n" +
            "1 1 1 255 255 255 0 0 0 0 0 0 \n" +
            "1 1 1 255 255 255 0 0 0 0 0 0 \n" +
            "1 1 1 255 255 255 0 0 0 0 0 0 \n", i1.visualize("red-component").toString());

    // green
    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "0 0 0 255 255 255 0 0 0 \n" +
            "2 2 2 2 2 2 2 2 2 \n" +
            "2 2 2 2 2 2 0 0 0 \n", i2.visualize("green-component").toString());

    // blue
    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "0 0 0 0 0 0 255 255 255 \n" +
            "3 3 3 3 3 3 3 3 3 \n" +
            "3 3 3 3 3 3 255 255 255 \n", i2.visualize("blue-component").toString());

    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "255 255 255 255 255 255 255 255 255 \n" +
            "3 3 3 3 3 3 3 3 3 \n" +
            "3 3 3 3 3 3 255 255 255 \n", i2.visualize("value-component").toString());

    // intensity
    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "85 85 85 85 85 85 85 85 85 \n" +
            "2 2 2 2 2 2 2 2 2 \n" +
            "2 2 2 2 2 2 85 85 85 \n", i2.visualize("intensity-component").toString());

    // luma
    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "54 0 0 0 182 0 0 0 18 \n" +
            "0 1 0 0 1 0 0 1 0 \n" +
            "0 1 0 0 1 0 0 0 18 \n", i2.visualize("luma-component").toString());
  }

  //Histogram
  @Test
  public void testHistogram() {
    Map<String, Double> test = i1.histogram();
    assertEquals(0.25, test.get("red255"), 0.01);
  }
}