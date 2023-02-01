package view;

import java.io.IOException;

/**
 * This interface represents operations that should be offered by a view
 * for this image process application.
 */
public interface IView {
  /**
   * Render a specific message to the provided data destination.
   * @param message The message to display.
   * @throws IOException will throw if the message cannot be processed.
   */
  public void render(String message);
}
