package controller;

import model.IImageProcess;
import view.IView;

/**
 * This is a class for the command to flip an image horizontally or vertically.
 */
public class Flip extends CommandImpl {
  private String[] in;

  /**
   * This is a constructor for flipping images which will intake the users input and then
   * flip the image accordingly.
   * @param in represents the users input.
   * @param view represents what will be communicated back to the user.
   * @throws IllegalArgumentException if the user input or view are null or if the user input
   *                                  doesn't match the expected input length, aka 3 pieces of text.
   */
  public Flip(String[] in, IView view) throws IllegalArgumentException {
    super(in, view);
    if (in == null || view == null || in.length != 3) {
      throw new IllegalArgumentException("The command is either invalid, or was given an invalid" +
              "view.");
    }
    this.in = in;
  }

  @Override
  public void run(IImageProcess model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    try {
      if (in[0].equals("horizontal-flip")) {
        model.flip("horizontal-flip", in[1], in[2]);
        sendMessage("A horizontal flip has been applied to the image " + in[1]);
      } else if (in[0].equals("vertical-flip")) {
        model.flip("vertical-flip", in[1], in[2]);
        sendMessage("A vertical flip has been applied to the image " + in[1]);
      }
    } catch (IllegalArgumentException e) {
      sendMessage(e.getMessage());
    }
  }
}
