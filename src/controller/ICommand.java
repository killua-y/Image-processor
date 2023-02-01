package controller;

import model.IImageProcess;

/**
 * This is an interface for commands that the user can use by typing into the command line console.
 */
public interface ICommand {

  /**
   * This method is for the command to actually make its effect on the image.
   *
   * @param model The image that the command will be modifying.
   */
  public void run(IImageProcess model);


}
