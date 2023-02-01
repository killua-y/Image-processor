package controller;

/**
 * This is an interface for the controller of the program. The controller will be running the
 * actual program along with loading and saving images.
 */
public interface IController {
  /**
   * This method will run the controller and intake any input from the user. It will then be passed
   * to its corresponding model which then communicates with the view on what to write to the user.
   */
  public void runProgram();

  /**
   * This method is for loading an image from the users local files to the program.
   *
   * @param imageLocation This is the path locally of where the image is stored.
   * @param imageName This is the name of the image that the user chooses.
   */
  public void load(String imageLocation, String imageName);

  /**
   * This method is for saving an image.
   *
   * @param imageSaveName represents the file name that the image will be saved as locally.
   * @param imageName this represents the image that the user wants to save.
   */
  public void save(String imageSaveName, String imageName);
}
