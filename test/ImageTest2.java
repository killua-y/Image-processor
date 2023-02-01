import org.junit.Before;
import org.junit.Test;

import model.Image;
import model.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * The test for the new method in the image class.
 */
public class ImageTest2 {
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
  public void testBlur() {
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "48 0 1 95 48 0 47 95 47 0 47 95 \n" +
            "64 1 1 127 64 0 63 127 63 0 63 127 \n" +
            "64 1 1 127 64 0 63 127 63 0 63 127 \n" +
            "48 0 1 95 48 0 47 95 47 0 47 95 \n", i1.filter("blur").toString());
  }

  @Test
  public void testSharpen() {
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "96 0 3 255 96 0 95 255 94 0 95 255 \n" +
            "160 0 4 255 160 0 158 255 157 0 159 255 \n" +
            "160 0 4 255 160 0 158 255 157 0 159 255 \n" +
            "96 0 3 255 96 0 95 255 94 0 95 255 \n", i1.filter("sharpen").toString());
  }

  @Test
  public void testColorTrans() {
    // for greyscale
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "1 2 3 254 0 0 0 254 0 0 0 254 \n" +
            "1 2 3 254 0 0 0 254 0 0 0 254 \n" +
            "1 2 3 254 0 0 0 254 0 0 0 254 \n" +
            "1 2 3 254 0 0 0 254 0 0 0 254 \n", i1.visualize("greyscale").toString());

    // for sepia stone
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "1 2 3 255 0 0 0 255 0 0 0 254 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 254 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 254 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 254 \n", i1.visualize("sepia").toString());
  }
}