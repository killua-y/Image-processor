package view;

import controller.IGuiController;
import model.Image;

/**
 * This interface is for the View of the GUI. It will be capable of
 * adding different buttons and listeners to the GUI.
 */
public interface IGuiView extends IView {
  /**
   * This method will initialize the buttons and action listeners
   * for the GUI.
   *
   * @param controller The controller of the GUI.
   */
  public void intializeGUIListeners(IGuiController controller);

  /**
   * This method will allow the user to select an image to modify.
   *
   * @param saveName Will be the name of the image that it is saved under
   *                 in order to select it.
   * @param toLoad Will be the image to load for the GUI to display.
   */
  public void selectImage(String saveName, Image toLoad);

  /**
   * This method will allow the GUI to display the given image.
   *
   * @param saveName Will be the name of the image that it is saved under
   *                 in order to select it.
   * @param toLoad Will be the image to load for the GUI to display.
   */
  public void imageDisplay(String saveName, Image toLoad);
}
