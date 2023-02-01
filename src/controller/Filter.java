package controller;

import model.IImageProcess;
import view.IView;

/**
 * This is a class for the command to process the an image using filter.
 */
public class Filter extends CommandImpl {

  /**
   * This constructor will check if the user's text from the command line or the image is null as
   * well as if the command they typed is too long to be a valid command.
   *
   * @param in   The users text in the command line in the form of a split array of Strings.
   * @param view The view that the message will send to.
   */
  public Filter(String[] in, IView view) {
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
      if (in[0].equals("blur")) {
        model.filter(in[0], in[1], in[2]);
        sendMessage(in[0] + " has been applied to " + in[1]);
      } else if (in[0].equals("sharpen")) {
        model.filter(in[0], in[1], in[2]);
        sendMessage(in[0] + " has been applied to " + in[1]);
      }
    } catch (IllegalArgumentException e) {
      sendMessage(e.getMessage());
    }
  }
}
