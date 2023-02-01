package view;

import java.io.IOException;

import model.ImageProcessImpl;

/**
 * An abstract class for the class ViewImpl.
 */
public abstract class AView implements IView {
  private ImageProcessImpl model;
  private final Appendable out;
  /**
   * the construct takes in an image model state.
   * @param model the image model state.
   * @param out the output goes to controller
   * @throws IllegalArgumentException if the model or output is null
   */

  public AView(ImageProcessImpl model, Appendable out) throws IllegalArgumentException {
    if (model == null || out == null) {
      throw new IllegalArgumentException("The model or output cannot be null");
    }
    this.model = model;
    this.out = out;
  }

  /**
   * the construct takes in an image model state.
   * @param model the image model state.
   * @throws IllegalArgumentException if the model or output is null
   */
  public AView(ImageProcessImpl model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("The model or output cannot be null");
    }
    this.model = model;
    this.out = System.out;
  }

  @Override
  public void render(String message) {
    try {
      this.out.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Cannot transmit output message.");
    }
  }
}
