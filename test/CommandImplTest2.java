import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import controller.ControllerImpl;
import controller.IController;
import model.IImageProcess;
import model.Image;
import model.ImageProcessImpl;
import model.Pixel;
import view.IView;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * This class will test new features added to the controller.
 * Specifically loading and saving new formats.
 */
public class CommandImplTest2 {
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
  public void testppm() {
    // save as bmp, jpg and png
    Appendable output = new StringBuilder("");
    IView view1 = new ViewImpl((ImageProcessImpl) mp1, output);
    Readable input = new StringReader("load Images/image1.ppm ImageTest\n" +
            "horizontal-flip ImageTest ImageTestHorizontal\n" +
            // save as bmp
            "save Images/ImageTestHorizontal.bmp ImageTestHorizontal\n" +
            "load Images/ImageTestHorizontal.bmp ImageTestbmp\n" +
            // save as jpg
            "save Images/ImageTestHorizontal.jpg ImageTestHorizontal\n" +
            "load Images/ImageTestHorizontal.jpg ImageTestjpg\n" +
            // save as png
            "save Images/ImageTestHorizontal.png ImageTestHorizontal\n" +
            "load Images/ImageTestHorizontal.png ImageTestpng\n");

    IController controller1 = new ControllerImpl(mp1, view1, input);
    controller1.runProgram();

    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n",
            mp1.getImage("ImageTestHorizontal").toString());

    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n",
            mp1.getImage("ImageTestbmp").toString());
    assertEquals("P3\n" +
                    "4 4\n" +
                    "255\n" +
                    "0 71 67 55 190 186 165 37 36 94 0 0 \n" +
                    "0 71 67 55 190 186 165 37 36 94 0 0 \n" +
                    "0 71 67 55 190 186 165 37 36 94 0 0 \n" +
                    "0 71 67 55 190 186 165 37 36 94 0 0 \n",
            mp1.getImage("ImageTestjpg").toString());
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n",
            mp1.getImage("ImageTestpng").toString());
  }

  @Test
  public void testbmp() {
    // save as bmp, jpg and png
    Appendable output = new StringBuilder("");
    IView view1 = new ViewImpl((ImageProcessImpl) mp1, output);
    Readable input = new StringReader("save Images/Image1.bmp image1\n" +
            "load Images/image1.bmp ImageTest\n" +
            "horizontal-flip ImageTest ImageTestHorizontal\n" +
            // save as ppm
            "save Images/ImageTestHorizontal.ppm ImageTestHorizontal\n" +
            "load Images/ImageTestHorizontal.ppm ImageTestppm\n" +
            // save as jpg
            "save Images/ImageTestHorizontal.jpg ImageTestHorizontal\n" +
            "load Images/ImageTestHorizontal.jpg ImageTestjpg\n" +
            // save as png
            "save Images/ImageTestHorizontal.png ImageTestHorizontal\n" +
            "load Images/ImageTestHorizontal.png ImageTestpng\n");

    IController controller1 = new ControllerImpl(mp1, view1, input);
    controller1.runProgram();

    assertEquals("P3\n" +
                    "4 4\n" +
                    "255\n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n",
            mp1.getImage("ImageTestHorizontal").toString());

    assertEquals("P3\n" +
                    "4 4\n" +
                    "255\n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n",
            mp1.getImage("ImageTestppm").toString());
    assertEquals("P3\n" +
                    "4 4\n" +
                    "255\n" +
                    "0 71 67 55 190 186 165 37 36 94 0 0 \n" +
                    "0 71 67 55 190 186 165 37 36 94 0 0 \n" +
                    "0 71 67 55 190 186 165 37 36 94 0 0 \n" +
                    "0 71 67 55 190 186 165 37 36 94 0 0 \n",
            mp1.getImage("ImageTestjpg").toString());
    assertEquals("P3\n" +
                    "4 4\n" +
                    "255\n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n",
            mp1.getImage("ImageTestpng").toString());
  }

  @Test
  public void testjpg() {
    // save as ppm, bmp, jpg and png
    Appendable output = new StringBuilder("");
    IView view1 = new ViewImpl((ImageProcessImpl) mp1, output);
    Readable input = new StringReader("save Images/Image1.jpg image1\n" +
            "load Images/image1.jpg ImageTest\n" +
            "horizontal-flip ImageTest ImageTestHorizontal\n" +
            // save as ppm
            "save Images/ImageTestHorizontal.ppm ImageTestHorizontal\n" +
            "load Images/ImageTestHorizontal.ppm ImageTestppm\n" +
            // save as bmp
            "save Images/ImageTestHorizontal.bmp ImageTestHorizontal\n" +
            "load Images/ImageTestHorizontal.bmp ImageTestbmp\n" +
            // save as png
            "save Images/ImageTestHorizontal.png ImageTestHorizontal\n" +
            "load Images/ImageTestHorizontal.png ImageTestpng\n");

    IController controller1 = new ControllerImpl(mp1, view1, input);
    controller1.runProgram();

    assertEquals("P3\n" +
                    "4 4\n" +
                    "255\n" +
                    "0 74 64 49 193 183 176 37 42 98 0 0 \n" +
                    "0 74 64 49 193 183 176 37 42 98 0 0 \n" +
                    "0 74 64 49 193 183 176 37 42 98 0 0 \n" +
                    "0 74 64 49 193 183 176 37 42 98 0 0 \n",
            mp1.getImage("ImageTestHorizontal").toString());

    assertEquals("P3\n" +
                    "4 4\n" +
                    "255\n" +
                    "0 74 64 49 193 183 176 37 42 98 0 0 \n" +
                    "0 74 64 49 193 183 176 37 42 98 0 0 \n" +
                    "0 74 64 49 193 183 176 37 42 98 0 0 \n" +
                    "0 74 64 49 193 183 176 37 42 98 0 0 \n",
            mp1.getImage("ImageTestppm").toString());
    assertEquals("P3\n" +
                    "4 4\n" +
                    "255\n" +
                    "0 74 64 49 193 183 176 37 42 98 0 0 \n" +
                    "0 74 64 49 193 183 176 37 42 98 0 0 \n" +
                    "0 74 64 49 193 183 176 37 42 98 0 0 \n" +
                    "0 74 64 49 193 183 176 37 42 98 0 0 \n",
            mp1.getImage("ImageTestbmp").toString());
    assertEquals("P3\n" +
                    "4 4\n" +
                    "255\n" +
                    "0 74 64 49 193 183 176 37 42 98 0 0 \n" +
                    "0 74 64 49 193 183 176 37 42 98 0 0 \n" +
                    "0 74 64 49 193 183 176 37 42 98 0 0 \n" +
                    "0 74 64 49 193 183 176 37 42 98 0 0 \n",
            mp1.getImage("ImageTestpng").toString());
  }

  @Test
  public void testpng() {
    // save as ppm bmp, jpg
    Appendable output = new StringBuilder("");
    IView view1 = new ViewImpl((ImageProcessImpl) mp1, output);
    Readable input = new StringReader("save Images/Image1.png image1\n" +
            "load Images/image1.png ImageTest\n" +
            "horizontal-flip ImageTest ImageTestHorizontal\n" +
            // save as ppm
            "save Images/ImageTestHorizontal.ppm ImageTestHorizontal\n" +
            "load Images/ImageTestHorizontal.ppm ImageTestppm\n" +
            // save as jpg
            "save Images/ImageTestHorizontal.jpg ImageTestHorizontal\n" +
            "load Images/ImageTestHorizontal.jpg ImageTestjpg\n" +
            // save as bmp
            "save Images/ImageTestHorizontal.bmp ImageTestHorizontal\n" +
            "load Images/ImageTestHorizontal.bmp ImageTestbmp\n");

    IController controller1 = new ControllerImpl(mp1, view1, input);
    controller1.runProgram();

    assertEquals("P3\n" +
                    "4 4\n" +
                    "255\n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n",
            mp1.getImage("ImageTestHorizontal").toString());

    assertEquals("P3\n" +
                    "4 4\n" +
                    "255\n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n",
            mp1.getImage("ImageTestppm").toString());
    assertEquals("P3\n" +
                    "4 4\n" +
                    "255\n" +
                    "0 71 67 55 190 186 165 37 36 94 0 0 \n" +
                    "0 71 67 55 190 186 165 37 36 94 0 0 \n" +
                    "0 71 67 55 190 186 165 37 36 94 0 0 \n" +
                    "0 71 67 55 190 186 165 37 36 94 0 0 \n",
            mp1.getImage("ImageTestjpg").toString());
    assertEquals("P3\n" +
                    "4 4\n" +
                    "255\n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
                    "0 0 255 0 255 0 255 0 0 1 2 3 \n",
            mp1.getImage("ImageTestbmp").toString());
  }














}