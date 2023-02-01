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
import static org.junit.Assert.fail;

/**
 * test class for the controller.
 */
public class ControllerImplTest {
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
  public void testSave() {
    Appendable output = new StringBuilder("");
    IView view1 = new ViewImpl((ImageProcessImpl) mp1, output);
    Readable input1 = new StringReader("save Images/image1.ppm image1");

    IController controller1 = new ControllerImpl(mp1, view1, input1);
    controller1.runProgram();

    Readable input2 = new StringReader("load Images/image1.ppm imageTest");
    IController controller2 = new ControllerImpl(mp1, view1, input2);
    controller2.runProgram();

    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n", mp1.getImage("imageTest").toString());
  }

  @Test
  public void testSave2() {
    Appendable output = new StringBuilder("");
    IView view1 = new ViewImpl((ImageProcessImpl) mp1, output);
    Readable input1 = new StringReader("save Wrong image1");

    IController controller1 = new ControllerImpl(mp1, view1, input1);

    try {
      controller1.runProgram();
    } catch (IllegalArgumentException e) {
      assertEquals("This is an invalid file format.", e.getMessage());
    }
  }

  @Test
  public void testLoad() {
    Appendable output = new StringBuilder("");
    IView view1 = new ViewImpl((ImageProcessImpl) mp1, output);
    Readable input = new StringReader("load Images/pixels.ppm imageTest");

    IController controller1 = new ControllerImpl(mp1, view1, input);
    controller1.runProgram();

    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "255 0 0 0 0 255 0 255 0 \n" +
            "125 125 125 125 0 125 0 125 125 \n" +
            "0 255 0 0 0 255 255 0 0 \n", mp1.getImage("imageTest").toString());
    assertEquals(null, mp1.getImage("imageWrong"));
  }

  @Test
  public void testLoad2() {
    Appendable output = new StringBuilder("");
    IView view1 = new ViewImpl((ImageProcessImpl) mp1, output);
    Readable input = new StringReader("load wrong imageTest");

    IController controller1 = new ControllerImpl(mp1, view1, input);
    try {
      controller1.runProgram();
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid file format submitted.", e.getMessage());
    }
  }

  @Test
  public void testFlip() {
    Appendable output = new StringBuilder("");
    IView view1 = new ViewImpl((ImageProcessImpl) mp1, output);

    // horizontally
    Readable input1 = new StringReader("horizontal-flip image1 image1horizontal");
    IController controller1 = new ControllerImpl(mp1, view1, input1);
    controller1.runProgram();
    assertEquals("P3\n" +
                    "4 4\n" +
                    "255\n" +
                    "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
                    "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
                    "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
                    "1 2 3 255 0 0 0 255 0 0 0 255 \n",
            mp1.getImage("image1").toString());

    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n",
            mp1.getImage("image1horizontal").toString());

    // vertically
    Readable input2 = new StringReader("vertical-flip image1 image1vertical");
    IController controller2 = new ControllerImpl(mp1, view1, input2);
    controller2.runProgram();

    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n", mp1.getImage("image1vertical").toString());
  }

  @Test
  public void testBrighten() {
    Appendable output = new StringBuilder("");
    IView view1 = new ViewImpl((ImageProcessImpl) mp1, output);

    // brighten
    Readable input1 = new StringReader("brighten 100 image1 image1bright");
    IController controller1 = new ControllerImpl(mp1, view1, input1);
    controller1.runProgram();
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "101 102 103 255 100 100 100 255 100 100 100 255 \n" +
            "101 102 103 255 100 100 100 255 100 100 100 255 \n" +
            "101 102 103 255 100 100 100 255 100 100 100 255 \n" +
            "101 102 103 255 100 100 100 255 100 100 100 255 \n",
            mp1.getImage("image1bright").toString());

    // darken
    Readable input2 = new StringReader("brighten -100 image1 image1dar");
    IController controller2 = new ControllerImpl(mp1, view1, input2);
    controller2.runProgram();

    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "0 0 0 155 0 0 0 155 0 0 0 155 \n" +
            "0 0 0 155 0 0 0 155 0 0 0 155 \n" +
            "0 0 0 155 0 0 0 155 0 0 0 155 \n" +
            "0 0 0 155 0 0 0 155 0 0 0 155 \n", mp1.getImage("image1dar").toString());
  }

  @Test
  public void testVisualization() {
    Appendable output = new StringBuilder("");
    IView view1 = new ViewImpl((ImageProcessImpl) mp1, output);

    // red
    Readable input1 = new StringReader("red-component image1 image1red");
    IController controller1 = new ControllerImpl(mp1, view1, input1);
    controller1.runProgram();
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "1 1 1 255 255 255 0 0 0 0 0 0 \n" +
            "1 1 1 255 255 255 0 0 0 0 0 0 \n" +
            "1 1 1 255 255 255 0 0 0 0 0 0 \n" +
            "1 1 1 255 255 255 0 0 0 0 0 0 \n", mp1.getImage("image1red").toString());

    // green
    Readable input2 = new StringReader("green-component image1 image1green");
    IController controller2 = new ControllerImpl(mp1, view1, input2);
    controller2.runProgram();

    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "2 2 2 0 0 0 255 255 255 0 0 0 \n" +
            "2 2 2 0 0 0 255 255 255 0 0 0 \n" +
            "2 2 2 0 0 0 255 255 255 0 0 0 \n" +
            "2 2 2 0 0 0 255 255 255 0 0 0 \n", mp1.getImage("image1green").toString());

    // blue
    Readable input3 = new StringReader("blue-component image1 image1blue");
    IController controller3 = new ControllerImpl(mp1, view1, input3);
    controller3.runProgram();

    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "3 3 3 0 0 0 0 0 0 255 255 255 \n" +
            "3 3 3 0 0 0 0 0 0 255 255 255 \n" +
            "3 3 3 0 0 0 0 0 0 255 255 255 \n" +
            "3 3 3 0 0 0 0 0 0 255 255 255 \n", mp1.getImage("image1blue").toString());

    // value
    Readable input6 = new StringReader("value-component image1 image1value");
    IController controller6 = new ControllerImpl(mp1, view1, input6);
    controller6.runProgram();

    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "3 3 3 255 255 255 255 255 255 255 255 255 \n" +
            "3 3 3 255 255 255 255 255 255 255 255 255 \n" +
            "3 3 3 255 255 255 255 255 255 255 255 255 \n" +
            "3 3 3 255 255 255 255 255 255 255 255 255 \n",
            mp1.getImage("image1value").toString());

    // intensity
    Readable input4 = new StringReader("intensity-component image1 image1intensity");
    IController controller4 = new ControllerImpl(mp1, view1, input4);
    controller4.runProgram();

    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "2 2 2 85 85 85 85 85 85 85 85 85 \n" +
            "2 2 2 85 85 85 85 85 85 85 85 85 \n" +
            "2 2 2 85 85 85 85 85 85 85 85 85 \n" +
            "2 2 2 85 85 85 85 85 85 85 85 85 \n",
            mp1.getImage("image1intensity").toString());

    // luma
    Readable input5 = new StringReader("luma-component image1 image1luma");
    IController controller5 = new ControllerImpl(mp1, view1, input5);
    controller5.runProgram();

    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "0 1 0 54 0 0 0 182 0 0 0 18 \n" +
            "0 1 0 54 0 0 0 182 0 0 0 18 \n" +
            "0 1 0 54 0 0 0 182 0 0 0 18 \n" +
            "0 1 0 54 0 0 0 182 0 0 0 18 \n", mp1.getImage("image1luma").toString());
  }

  @Test
  public void testReadTXT() {
    Appendable output = new StringBuilder("");
    IView view1 = new ViewImpl((ImageProcessImpl) mp1, output);

    // use vertically flip to test "horizontal-flip image1 image1horizontal"
    Readable input1 = new StringReader("-file res/commands.txt");
    IController controller1 = new ControllerImpl(mp1, view1, input1);
    controller1.runProgram();
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n" +
            "0 0 255 0 255 0 255 0 0 1 2 3 \n",
            mp1.getImage("image1horizontal").toString());

    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 255 \n",
            mp1.getImage("image1vertical").toString());
  }

  @Test
  public void testReadTXTAllCommands() {
    Appendable output = new StringBuilder("");
    IView view1 = new ViewImpl((ImageProcessImpl) mp1, output);

    // Will try running all commands in the program to make sure no exceptions are thrown
    Readable input1 = new StringReader("-file res/runAllCommands.txt");
    IController controller1 = new ControllerImpl(mp1, view1, input1);
    controller1.runProgram();

    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "255 0 0 0 0 255 0 255 0 \n" +
            "125 125 125 125 0 125 0 125 125 \n" +
            "0 255 0 0 0 255 255 0 0 \n", mp1.getImage("pixels").toString());
  }

  @Test
  public void testFilter() {
    Appendable output = new StringBuilder("");
    IView view1 = new ViewImpl((ImageProcessImpl) mp1, output);

    // for blur
    Readable input1 = new StringReader("blur image1 image1blur");
    IController controller1 = new ControllerImpl(mp1, view1, input1);
    controller1.runProgram();
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "48 0 1 95 48 0 47 95 47 0 47 95 \n" +
            "64 1 1 127 64 0 63 127 63 0 63 127 \n" +
            "64 1 1 127 64 0 63 127 63 0 63 127 \n" +
            "48 0 1 95 48 0 47 95 47 0 47 95 \n", mp1.getImage("image1blur").toString());

    // for sharpen
    Readable input2 = new StringReader("sharpen image1 image1sharpen");
    IController controller2 = new ControllerImpl(mp1, view1, input2);
    controller2.runProgram();
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "96 0 3 255 96 0 95 255 94 0 95 255 \n" +
            "160 0 4 255 160 0 158 255 157 0 159 255 \n" +
            "160 0 4 255 160 0 158 255 157 0 159 255 \n" +
            "96 0 3 255 96 0 95 255 94 0 95 255 \n",
            mp1.getImage("image1sharpen").toString());
  }

  @Test
  public void testColorTrans() {
    Appendable output = new StringBuilder("");
    IView view1 = new ViewImpl((ImageProcessImpl) mp1, output);

    Readable input1 = new StringReader("greyscale image1 image1greyscale\n" +
            "sepia image1 image1sepia");

    IController controller1 = new ControllerImpl(mp1, view1, input1);
    controller1.runProgram();
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "1 2 3 254 0 0 0 254 0 0 0 254 \n" +
            "1 2 3 254 0 0 0 254 0 0 0 254 \n" +
            "1 2 3 254 0 0 0 254 0 0 0 254 \n" +
            "1 2 3 254 0 0 0 254 0 0 0 254 \n",
            mp1.getImage("image1greyscale").toString());
    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "1 2 3 255 0 0 0 255 0 0 0 254 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 254 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 254 \n" +
            "1 2 3 255 0 0 0 255 0 0 0 254 \n",
            mp1.getImage("image1sepia").toString());
  }



}