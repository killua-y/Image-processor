package view;

/**
 * This class is only here to fulfill the view requirement for a new GUI upon first being run.
 */
public class EmptyView implements IView {

  /**
   * This render method won't actually do anything.
   *
   * @param message The message to display.
   */
  @Override
  public void render(String message) {
    return;
  }
}
