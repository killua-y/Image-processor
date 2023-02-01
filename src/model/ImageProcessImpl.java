package model;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the implementation of IImageProcess.
 */
public class ImageProcessImpl implements IImageProcess {
  protected final Map<String, Image> images;

  /**
   * This class will initialize a model with an empty hashMap of image names and images.
   */
  public ImageProcessImpl() {
    this.images = new HashMap<>();
  }


  @Override
  public void flip(String direction, String imageName, String destImageName)
          throws IllegalArgumentException {
    if (direction == null || direction.equals("")
            || imageName == null || imageName.equals("")
            || destImageName == null || destImageName.equals("")) {
      throw new IllegalArgumentException("the direction/imageName/destImageName " +
              "cannot be empty or null.");
    }
    if (!this.images.containsKey(imageName)) {
      throw new IllegalArgumentException("The image cannot be found.");
    }
    Image result = null;

    if (direction.equals("horizontal-flip")) {
      result = this.images.get(imageName).flipHorizontal();
    } else if (direction.equals("vertical-flip")) {
      result = this.images.get(imageName).flipVertical();
    }

    this.images.put(destImageName, result);
  }

  @Override
  public void brighten(int increment, String imageName, String destImageName)
          throws IllegalArgumentException {
    if (imageName == null || imageName.equals("")
            || destImageName == null || destImageName.equals("")) {
      throw new IllegalArgumentException("the imageName/destImageName " +
              "cannot be empty or null.");
    }
    if (!this.images.containsKey(imageName)) {
      throw new IllegalArgumentException("The image cannot be found.");
    }

    Image result = null;
    result = this.images.get(imageName).brighten(increment);

    this.images.put(destImageName, result);
  }

  @Override
  public void componentVisualization(String type, String imageName, String destImageName)
          throws IllegalArgumentException {
    if (type == null || type.equals("")
            || imageName == null || imageName.equals("")
            || destImageName == null || destImageName.equals("")) {
      throw new IllegalArgumentException("the direction/imageName/destImageName " +
              "cannot be empty or null.");
    }
    if (!this.images.containsKey(imageName)) {
      throw new IllegalArgumentException("The image cannot be found.");
    }

    Image result = null;
    result = this.images.get(imageName).visualize(type);

    this.images.put(destImageName, result);
  }

  @Override
  public void filter(String type, String imageName, String destImageName)
          throws IllegalArgumentException {
    if (type == null || type.equals("")
            || imageName == null || imageName.equals("")
            || destImageName == null || destImageName.equals("")) {
      throw new IllegalArgumentException("the direction/imageName/destImageName " +
              "cannot be empty or null.");
    }
    if (!this.images.containsKey(imageName)) {
      throw new IllegalArgumentException("The image cannot be found.");
    }

    Image result = null;
    result = this.images.get(imageName).filter(type);

    this.images.put(destImageName, result);
  }

  @Override
  public Image getImage(String imageName) {
    return this.images.getOrDefault(imageName, null);
  }


  @Override
  public void addImage(String imageName, Image image) {
    this.images.put(imageName, image);
  }


}
