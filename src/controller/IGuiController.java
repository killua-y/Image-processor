package controller;

import view.IGuiView;

/**
 * This interface is for a controller specifically for the GUI.
 */
public interface IGuiController {

  /**
   * This method is for loading an image from the users local files to the program.
   * The user will be prompted to select the file path of where the image they want to load is.
   */
  public void load();

  /**
   * This method will save an image to whichever file location the user selects.
   *
   * @param imageName The name of the image from the loaded images list to save.
   */
  public void save(String imageName);

  /**
   * This method is for selecting which image to currently show on the GUI and which image to show
   * the histogram for. It will also dictate which image you are going to save and modify upon.
   *
   * @param imageName The name of the image the user clicks on to select.
   */
  public void selectAndDisplayImage(String imageName);

  /**
   * This method is for when the program is first ran, what to portray on the GUI.
   *
   * @param view This represents the view that will be initialized when the program is first ran.
    */
  public void startup(IGuiView view);

  /**
   * This method will apply whatever modification that the user clicks onto the currently selected
   * image.
   */
  public void apply(String imageName, String command);
}
