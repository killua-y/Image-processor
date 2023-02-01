package controller;

import model.IImageProcess;
import view.IView;

/**
 * This is the class for the "brighten" command which can make an image brighter or darker based on
 * whatever the user inputs. If it's a positive value then it will brighten by that amount or if
 * it's a negative value then it will darken by that amount.
 */
public class Brighten extends CommandImpl {
  private final int degree;

  /**
   * This constructor will check if the user's text from the command line or the image is null as
   * well as if the command they typed is too long to be a valid command.
   *
   * @param in   The users text in the command line in the form of a split array of Strings.
   * @param view The view that the message will send to.
   * @throws IllegalArgumentException if the input and or view are null
   */
  public Brighten(String[] in, IView view) throws IllegalArgumentException {
    super(in, view);
    if (in == null || in.length != 4 || view == null) {
      throw new IllegalArgumentException("The input and view cannot be null or there are too" +
              " many parameters.");
    }
    try {
      this.degree = Integer.parseInt(in[1]);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("The amount to brighten or darken by could not be found.");
    }
  }

  @Override
  public void run(IImageProcess model) {
    if (model == null || in.length != 4) {
      throw new IllegalArgumentException("The model cannot be null or there is an incorrect " +
              "amount of parameters given.");
    }

    try {
      if (this.degree < 0) {
        model.brighten(this.degree, this.in[2], this.in[3]);
        sendMessage("The image has been darkened by a value of " + in[1]);
      } else if (this.degree > 0) {
        model.brighten(this.degree, this.in[2], this.in[3]);
        sendMessage("The image has been brightened by a value of " + in[1]);
      } else {
        sendMessage("A brightening value of 0 will not affect the image.");
      }
    } catch (IllegalArgumentException e) {
      sendMessage(e.getMessage());
    }
  }
}
