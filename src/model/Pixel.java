package model;

import java.util.ArrayList;

/**
 * This class represents a Pixel object. Every image is made up of an array of pixels.
 */
public class Pixel extends ImageProcessImpl {
  private final int red;
  private final int green;
  private final int blue;

  /**
   * This constructor will make a pixel and make sure that none of the RGB values are less than 0.
   *
   * @param red   the red value of the pixel.
   * @param green the green value of the pixel.
   * @param blue  the blue value of the pixel.
   * @throws IllegalArgumentException if any of the RGB values input are < 0.
   */
  public Pixel(int red, int green, int blue) throws IllegalArgumentException {
    if (red < 0 || green < 0 || blue < 0) {
      throw new IllegalArgumentException("RGB cannot be negative."); // Not upper bound because the
      // user gives the cap amount, that is checked while initializing the image.
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * This is a getter that will return the red value.
   *
   * @return the red value of the pixel.
   */
  public int getRed() {
    int temp = this.red;
    return temp;
  }

  /**
   * This is a getter that will return the green value.
   *
   * @return the green value of the pixel.
   */
  public int getGreen() {
    int temp = this.green;
    return temp;
  }

  /**
   * This is a getter that will return the blue value.
   *
   * @return the blue value of the pixel.
   */
  public int getBlue() {
    int temp = this.blue;
    return temp;
  }

  @Override
  public String toString() {
    int red = this.red;
    int green = this.green;
    int blue = this.blue;
    return Integer.valueOf(red) + " " + Integer.valueOf(green) + " " + Integer.valueOf(blue) + " ";
  }

  /**
   * This method will check if two pixels are equal to each other.
   *
   * @param pixel A pixel to compare to.
   * @return Will return true if they are equal and false if they are not equal.
   */
  public boolean isEqual(Pixel pixel) {
    if (this == pixel) {
      return true;
    }
    return (this.red == pixel.red && this.green == pixel.green && this.blue == pixel.blue);
  }

  /**
   * Method to check if any RGB value exceeds the cap/limit.
   *
   * @param cap The limit of what any RGB value must not exceed.
   * @return Will return true if the value exceeds the cap and false if it does not.
   */
  public boolean overCap(int cap) {
    return (red > cap || green > cap || blue > cap);
  }

  /**
   * This method is for the "Brighten" command which will make a pixel brighter or darker based
   * on the given increment value that the user supplies.
   *
   * @param value the value to increase the brightness or decrease by, indicated by a positive or
   *              negative value.
   * @param cap   the cap value for RGB.
   * @return a new pixel with the effects made upon it.
   */
  public Pixel brighten(int value, int cap) {
    Pixel copy = new Pixel(this.red, this.green, this.blue); // To prevent modification

    if (value < 0) {
      return new Pixel(Math.max(0, this.red + value), Math.max(0, this.green + value),
              Math.max(0, this.blue + value));
    } else if (value > 0) {
      return new Pixel(Math.min(cap, this.red + value), Math.min(cap, this.green + value),
              Math.min(cap, this.blue + value));
    } else {
      return copy;
    }
  }

  /**
   * This method is for each RGB component visualization and intensity, value, and luma component
   * visualization.
   *
   * @param type which of the 6 visualizations.
   * @return The new pixel with the given type of effect.
   * @throws IllegalArgumentException if given an invalid type.
   */
  public Pixel visualize(String type, int cap) throws IllegalArgumentException {
    if (type.equals("red-component")) {
      int red = this.red;
      return new Pixel(red, red, red);
    } else if (type.equals("green-component")) {
      int green = this.green;
      return new Pixel(green, green, green);
    } else if (type.equals("blue-component")) {
      int blue = this.blue;
      return new Pixel(blue, blue, blue);
    } else if (type.equals("value-component")) {
      int largestValue = Math.max(Math.max(this.red, this.green), this.blue);
      return new Pixel(largestValue, largestValue, largestValue);
    } else if (type.equals("intensity-component")) {
      int average = (this.red + this.green + this.blue) / 3;
      return new Pixel(average, average, average);
    } else if (type.equals("luma-component")) {
      int red = (int) (this.red * 0.2126);
      int green = (int) (this.green * 0.7152);
      int blue = (int) (this.blue * 0.0722);
      return new Pixel(red, green, blue);
    } else if (type.equals("greyscale")) {
      return this.colorTrans(type, cap);
    } else if (type.equals("sepia")) {
      return this.colorTrans(type, cap);
    } else {
      throw new IllegalArgumentException("Invalid visualization type");
    }
  }

  /**
   * processing the pixel with filtering processing algorithms.
   *
   * @param pixelsList the pixels around the pixel that will be processed
   * @param type       type of processing
   * @param cap        the cap of the image
   * @return the pixel after being processed
   */
  public Pixel filter(ArrayList<Pixel> pixelsList, String type, int cap) {
    double red = 0;
    double green = 0;
    double blue = 0;

    if (type.equals("blur")) {
      double[] blurList = {0.0625, 0.125, 0.0625, 0.125, 0.25, 0.125, 0.0625, 0.125, 0.0625};
      for (int i = 0; i < 9; i++) {
        red += pixelsList.get(i).red * blurList[i];
        green += pixelsList.get(i).green * blurList[i];
        blue += pixelsList.get(i).blue * blurList[i];
      }
    } else if (type.equals("sharpen")) {
      double[] sharpenList = {-0.125, -0.125, -0.125, -0.125, -0.125,
        -0.125, 0.25, 0.25, 0.25, -0.125,
        -0.125, 0.25, 1, 0.25, -0.125,
        -0.125, 0.25, 0.25, 0.25, -0.125,
        -0.125, -0.125, -0.125, -0.125, -0.125};
      for (int i = 0; i < 25; i++) {
        red += pixelsList.get(i).red * sharpenList[i];
        green += pixelsList.get(i).green * sharpenList[i];
        blue += pixelsList.get(i).blue * sharpenList[i];
      }
    }

    // make sure RGB is not negative
    red = Math.max(0, red);
    green = Math.max(0, green);
    blue = Math.max(0, blue);

    // make sure RGB is not greater than the cap
    red = Math.min(cap, red);
    green = Math.min(cap, green);
    blue = Math.min(cap, blue);
    Pixel result = new Pixel((int) red, (int) green, (int) blue);
    return result;
  }

  /**
   * Process the pixel using color transformations.
   *
   * @param type the type of color transformations
   * @param cap  the cap of the image
   * @return the pixel after processed
   */
  private Pixel colorTrans(String type, int cap) {
    double red = 0;
    double green = 0;
    double blue = 0;
    double[] colorTransList = new double[0];
    if (type.equals("greyscale")) {
      colorTransList = new double[]
      {0.2126, 0.7152, 0.0722, 0.2126, 0.7152, 0.0722, 0.2126, 0.7152, 0.0722};
    } else if (type.equals("sepia")) {
      colorTransList = new double[]
      {0.393, 0.769, 0.189, 0.349, 0.7152, 0.0722,0.2126, 0.7152, 0.0722};
    }
    for (int i = 0; i < 9; i++) {
      if (i <= 2) {
        red += colorTransList[i] * this.getRed();
      } else if (i <= 5) {
        green += colorTransList[i] * this.getGreen();
      } else if (i <= 8) {
        blue += colorTransList[i] * this.getBlue();
      }
    }

    // make sure RGB is not negative
    red = Math.max(0, red);
    green = Math.max(0, green);
    blue = Math.max(0, blue);

    // make sure RGB is not greater than the cap
    red = Math.min(cap, red);
    green = Math.min(cap, green);
    blue = Math.min(cap, blue);
    Pixel result = new Pixel((int) red, (int) green, (int) blue);
    return result;
  }
}
