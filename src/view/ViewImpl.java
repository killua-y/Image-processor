package view;

import model.ImageProcessImpl;

/**
 * This class represent the operation for the view part of this image process application.
 */
public class ViewImpl extends AView {

  /**
   * The constructor takes in an image model state.
   *
   * @param model the image model state.
   * @param output the output goes to controller.
   * @throws IllegalArgumentException if the model or output is null.
   */
  public ViewImpl(ImageProcessImpl model, Appendable output) throws IllegalArgumentException {
    super(model, output);
  }
  /**
   * The constructor takes in an image model state.
   *
   * @param model the image model state.
   * @throws IllegalArgumentException if the model or output is null.
   */

  public ViewImpl(ImageProcessImpl model) throws IllegalArgumentException {
    super(model);
  }

}
