package controller;

import model.IImageProcess;
import view.IView;

/**
 * The visualization class extends CommandImpl because CommandImpl acts as a parent class.
 * This class will account for RGB component visualizations along with value, intensity, and luma.
 */
public class Visualization extends CommandImpl {
  private String[] in;

  /**
   * This constructor will check if the user's text from the command line or the image is null as
   * well as if the command they typed is too long to be a valid command.
   *
   * @param in   The users text in the command line in the form of a split array of Strings.
   * @param view The view that the message will send to.
   */
  public Visualization(String[] in, IView view) throws IllegalArgumentException {
    super(in, view);
    if (in == null) {
      throw new IllegalArgumentException("The input cannot be null");
    }
    this.in = in;
  }

  @Override
  public void run(IImageProcess model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    } else if (in.length != 3) {
      throw new IllegalArgumentException("Invalid Command.");
    }
    try {
      if (in[0].equals("red-component")) {
        model.componentVisualization(in[0], in[1], in[2]);
        sendMessage(in[0] + " has been applied to " + in[1]);
      } else if (in[0].equals("green-component")) {
        model.componentVisualization(in[0], in[1], in[2]);
        sendMessage(in[0] + " has been applied to " + in[1]);
      } else if (in[0].equals("blue-component")) {
        model.componentVisualization(in[0], in[1], in[2]);
        sendMessage(in[0] + " has been applied to " + in[1]);
      } else if (in[0].equals("value-component")) {
        model.componentVisualization(in[0], in[1], in[2]);
        sendMessage(in[0] + " has been applied to " + in[1]);
      } else if (in[0].equals("intensity-component")) {
        model.componentVisualization(in[0], in[1], in[2]);
        sendMessage(in[0] + " has been applied to " + in[1]);
      } else if (in[0].equals("luma-component")) {
        model.componentVisualization(in[0], in[1], in[2]);
        sendMessage(in[0] + " has been applied to " + in[1]);
      } else if (in[0].equals("greyscale")) {
        model.componentVisualization(in[0], in[1], in[2]);
        sendMessage(in[0] + " has been applied to " + in[1]);
      } else if (in[0].equals("sepia")) {
        model.componentVisualization(in[0], in[1], in[2]);
        sendMessage(in[0] + " has been applied to " + in[1]);
      }
    } catch (IllegalArgumentException e) {
      sendMessage(e.getMessage());
    }
  }
}
