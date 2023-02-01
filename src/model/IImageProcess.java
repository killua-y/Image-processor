package model;

/**
 * this interface contain the operations for process the image.
 */
public interface IImageProcess {
  /**
   * The operation to flip the image.
   *
   * @param direction     represent the direction of slip(vertical or horizontal)
   *                      true for horizontal, false for vertical.
   * @param imageName     represent the image being processed.
   * @param destImageName the name of the new image after processed
   * @throws IllegalArgumentException throw exception when the three take in value is null or empty
   */
  public void flip(String direction, String imageName, String destImageName)
          throws IllegalArgumentException;

  /**
   * The operation to brighten/darken the image.
   *
   * @param increment     the brighten value
   * @param imageName     represent the image being processed.
   * @param destImageName the name of the new image after processed
   * @throws IllegalArgumentException throw exception when the three take in value is null or empty
   */
  public void brighten(int increment, String imageName, String destImageName)
          throws IllegalArgumentException;

  /**
   * This will visualize a component of visualization to an image.
   *
   * @param type          Which visualization component to show: red-component, green-component,
   *                      blue-component, luma-component, intensity-component, value-component.
   * @param imageName     represent the image being processed.
   * @param destImageName the name of the new image being processed.
   * @throws IllegalArgumentException throw exception when the three take in value is null or empty
   */
  public void componentVisualization(String type, String imageName, String destImageName)
          throws IllegalArgumentException;

  /**
   * processing the image with filtering processing algorithms and add it into the hashmap.
   * @param type          the type of filtering processing algorithms
   * @param imageName     represent the image being processed.
   * @param destImageName the name of the new image being processed.
   * @throws IllegalArgumentException throw exception when the three take in value is null or empty
   */
  public void filter(String type, String imageName, String destImageName)
          throws IllegalArgumentException;

  /**
   * This get the image inside the hashmap for test.
   *
   * @param imageName The name of the image to retrieve (this is the name the user made, not the
   *                  file path).
   * @return the image we want to get.
   */
  public Image getImage(String imageName);

  /**
   * This will add an image inside the hashmap for test.
   *
   * @param imageName The name to store the image under.
   * @param image     The image that needs to be stored.
   */
  public void addImage(String imageName, Image image);

}
