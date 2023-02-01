package controller;

import model.IImageProcess;
import view.IView;

/**
 * This is an abstract class because it acts as the parent class for the rest of the commands.
 * This way the future commands that we need to add can easily be added.
 */
public abstract class CommandImpl implements ICommand {
  protected String[] in;
  protected IView view;

  /**
   * This constructor will check if the user's text from the command line or the image is null as
   * well as if the command they typed is too long to be a valid command.
   *
   * @param in The users text in the command line in the form of a split array of Strings.
   * @param view The view that the message will send to.
   */
  public CommandImpl(String[] in, IView view) {
    if (in == null || in.length > 4 || view == null) {
      throw new IllegalArgumentException("The Command you put in was null or contained too many" +
              "arguments, all commands require only 3 or 4 arguments.");
    }
    this.in = in;
    this.view = view;
  }

  @Override
  public void run(IImageProcess model) {
    // This is empty as this is just a parent class for the rest of the commands.
  }

  /**
   * This method will send a message to the model.
   *
   * @param message The message that will be sent to the model.
   * @throws IllegalStateException if the message fails to send.
   */
  protected void sendMessage(String message) throws IllegalStateException {
    this.view.render(message + "\n");
  }
}
