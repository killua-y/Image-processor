import java.io.IOException;
import java.io.InputStreamReader;

import controller.ControllerImpl;
import controller.GuiControllerImpl;
import controller.IController;
import controller.IGuiController;
import model.ImageProcessImpl;
import view.GuiViewImpl;
import view.IView;
import view.ViewImpl;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method
 *  as required.
 */
public class Main {

  /**
   * This is a main method to run the program.
   * @param args represents necessary arguments to run the program.
   */
  public static void main(String []args) throws IOException {
    StringBuilder builder = new StringBuilder();
    ImageProcessImpl model = new ImageProcessImpl();
    GuiViewImpl view;
    IView view2;
    IGuiController controller;
    IController controller2;

    StringBuilder contents = new StringBuilder();

    for (String s : args) {
      contents.append(s);
    }

    if (contents.toString().equals("")) {
      controller = new GuiControllerImpl(model);
      view = new GuiViewImpl();
      controller.startup(view);
    } else if (args[3].equals("-text")) {
      view2 = new ViewImpl(model);
      controller2 = new ControllerImpl(model, view2, new InputStreamReader(System.in));
      controller2.runProgram();
    }
  }
}

