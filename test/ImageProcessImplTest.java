
import org.junit.Before;
import org.junit.Test;

import model.IImageProcess;
import model.Image;
import model.ImageProcessImpl;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class will test the ImageProcess class.
 */
public class ImageProcessImplTest {
  // image class
  Pixel p1;
  Pixel pred;
  Pixel pgreen;
  Pixel pblue;
  Pixel[][] plist1;
  Pixel[][] plist2;
  Image i1;
  Image i2;

  // image process class
  IImageProcess mp1;

  @Before
  public void setup() {
    // image
    p1 = new Pixel(1, 2, 3);
    pred = new Pixel(255, 0, 0);
    pgreen = new Pixel(0, 255, 0);
    pblue = new Pixel(0, 0, 255);

    plist1 = new Pixel[][]{{p1, pred, pgreen, pblue}, {p1, pred, pgreen, pblue},
      {p1, pred, pgreen, pblue}, {p1, pred, pgreen, pblue}};
    plist2 = new Pixel[][]{{pred, pgreen, pblue}, {p1, p1, p1}, {p1, p1, pblue}};

    i1 = new Image(4, 4, 255, plist1);
    i2 = new Image(3, 3, 255, plist2);

    // image process
    mp1 = new ImageProcessImpl();
    mp1.addImage("image1", i1);
    mp1.addImage("image2", i2);
  }

  @Test
  public void testGet() {
    assertEquals(i1.toString(), mp1.getImage("image1").toString());
    assertEquals(null, mp1.getImage("image111"));
  }

  @Test
  public void testFlipThrow() {
    try {
      mp1.flip("", "image1", "image1Horizontal");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the direction/imageName/destImageName " +
              "cannot be empty or null.", e.getMessage());
    }
    try {
      mp1.flip(null, "image1", "image1Horizontal");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the direction/imageName/destImageName " +
              "cannot be empty or null.", e.getMessage());
    }
    try {
      mp1.flip("horizontal-flip", "", "image1Horizontal");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the direction/imageName/destImageName " +
              "cannot be empty or null.", e.getMessage());
    }
    try {
      mp1.flip("vertical-flip", null, "image1Horizontal");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the direction/imageName/destImageName " +
              "cannot be empty or null.", e.getMessage());
    }
    try {
      mp1.flip("horizontal-flip", "image1", "");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the direction/imageName/destImageName " +
              "cannot be empty or null.", e.getMessage());
    }
    try {
      mp1.flip("vertical-flip", "image1", null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the direction/imageName/destImageName " +
              "cannot be empty or null.", e.getMessage());
    }
    // cannot find the name
    try {
      mp1.flip("vertical-flip", "image123", "image found");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The image cannot be found.", e.getMessage());
    }
  }

  @Test
  public void testFlip() {
    mp1.flip("horizontal-flip", "image1", "image1Horizontal");
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n",
            mp1.getImage("image1Horizontal").toString());

    mp1.flip("vertical-flip", "image2", "image2vertical");
    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "1 2 3 1 2 3 0 0 255 \n" +
            "1 2 3 1 2 3 1 2 3 \n" +
            "255 0 0 0 255 0 0 0 255 \n", mp1.getImage("image2vertical").toString());
  }

  @Test
  public void testBrightThrow() {
    try {
      mp1.brighten(10, "", "image1Brighten");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the imageName/destImageName cannot be empty or null.", e.getMessage());
    }
    try {
      mp1.brighten(10, null, "image1Brighten");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the imageName/destImageName cannot be empty or null.", e.getMessage());
    }
    try {
      mp1.brighten(10, "image1", null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the imageName/destImageName cannot be empty or null.", e.getMessage());
    }
    try {
      mp1.brighten(10, "image1", "");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the imageName/destImageName cannot be empty or null.", e.getMessage());
    }
    // cannot find the name
    try {
      mp1.brighten(10, "image123", "image found");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The image cannot be found.", e.getMessage());
    }
  }

  @Test
  public void testBrighten() {
    mp1.brighten(10, "image1", "image1Brighten");
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "11 12 13 255 10 10 10 255 10 10 10 255 \n" +
            "11 12 13 255 10 10 10 255 10 10 10 255 \n" +
            "11 12 13 255 10 10 10 255 10 10 10 255 \n" +
            "11 12 13 255 10 10 10 255 10 10 10 255 \n",
            mp1.getImage("image1Brighten").toString());

    mp1.brighten(-10, "image2", "image2Brighten");
    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "245 0 0 0 245 0 0 0 245 \n" +
            "0 0 0 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 0 0 245 \n", mp1.getImage("image2Brighten").toString());
  }

  @Test
  public void testGrayThrow() {
    // cannot find the name
    try {
      mp1.componentVisualization("red-component", "image123", "image found");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The image cannot be found.", e.getMessage());
    }
    // null or empty
    try {
      mp1.componentVisualization("", "image1", "image1gray");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the direction/imageName/destImageName cannot be empty or null.",
              e.getMessage());
    }
    try {
      mp1.componentVisualization(null, "image1", "image1gray");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the direction/imageName/destImageName cannot be empty or null.",
              e.getMessage());
    }
    try {
      mp1.componentVisualization("red-component", "", "image1gray");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the direction/imageName/destImageName cannot be empty or null.",
              e.getMessage());
    }
    try {
      mp1.componentVisualization("red-component", null, "image1gray");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the direction/imageName/destImageName cannot be empty or null.",
              e.getMessage());
    }
    try {
      mp1.componentVisualization("red-component", "image1", "");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the direction/imageName/destImageName cannot be empty or null.",
              e.getMessage());
    }
    try {
      mp1.componentVisualization("red-component", "image1", null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the direction/imageName/destImageName cannot be empty or null.",
              e.getMessage());
    }
  }

  @Test
  public void testGray() {
    mp1.componentVisualization("red-component", "image1", "image1red");
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "1 1 1 255 255 255 0 0 0 0 0 0 \n" +
            "1 1 1 255 255 255 0 0 0 0 0 0 \n" +
            "1 1 1 255 255 255 0 0 0 0 0 0 \n" +
            "1 1 1 255 255 255 0 0 0 0 0 0 \n", mp1.getImage("image1red").toString());

    mp1.componentVisualization("luma-component", "image2", "image2luma");
    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "54 0 0 0 182 0 0 0 18 \n" +
            "0 1 0 0 1 0 0 1 0 \n" +
            "0 1 0 0 1 0 0 0 18 \n", mp1.getImage("image2luma").toString());
  }

}