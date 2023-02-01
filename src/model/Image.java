package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to represent an image for our program.
 */
public class Image extends ImageProcessImpl {
  private final int imageWidth;
  private final int imageHeight;
  private final int cap;
  private final Pixel[][] pixels;

  /**
   * A Constructor for the Image class. This represents an image.
   *
   * @param imageWidth  Represents the width dimension of the image.
   * @param imageHeight Represents the height dimension of the image.
   * @param cap         Represents the cap for the RGB values.
   * @param pixels      Represents the array of pixels that make up the image.
   */
  public Image(int imageWidth, int imageHeight, int cap, Pixel[][] pixels) {
    if (imageWidth < 0 || imageHeight < 0 || cap < 0 || pixels == null) { // Checking for null
      throw new IllegalArgumentException("Image cannot have negative dimensions, cap, or pixels");
    }

    // Nested for loop that will check that every pixel in the given array doesn't exceed the cap
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        if (pixels[i][j].overCap(cap)) {
          throw new IllegalArgumentException("Pixel is over the cap.");
        } else if (pixels.length != imageHeight || pixels[i].length != imageWidth) {
          throw new IllegalArgumentException("Image dimensions are not a square.");
        }
      }
    }

    this.imageHeight = imageHeight;
    this.imageWidth = imageWidth;
    this.cap = cap;
    this.pixels = pixels;
  }

  @Override
  public String toString() {
    StringBuilder pixelString = new StringBuilder();
    pixelString.append("P3\n");
    pixelString.append(this.imageHeight + " " + this.imageWidth + "\n");
    pixelString.append(this.cap + "\n");
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        if (i < pixels.length && j < pixels[i].length - 1) {
          pixelString.append(pixels[i][j].toString());
        } else {
          pixelString.append(pixels[i][j].toString()).append("\n"); // If end of row reached
        }
      }
    }
    //pixelString.deleteCharAt(pixelString.length() - 1);
    return pixelString.toString();
  }

  /**
   * This will return the Pixel at the given x and y coordinates.
   *
   * @param x the x coordinate of the pixel desired.
   * @param y the y coordinate of the pixel desired.
   * @return The pixel at the given coordinates.
   */
  public Pixel getPixel(int x, int y) {
    if (x < 0 || x >= this.imageHeight || y < 0 || y >= this.imageWidth) {
      throw new IllegalArgumentException("The given pixel coordinates don't exist.");
    }
    return new Pixel(pixels[x][y].getRed(), pixels[x][y].getGreen(), pixels[x][y].getBlue());
  }

  /**
   * A getter to return the width of the image.
   *
   * @return Will return an integer which represents the width dimension of the image.
   */
  public int getImageWidth() {
    int width = this.imageWidth;
    return width;
  }

  /**
   * A getter to return the height of the image.
   *
   * @return Will return an integer which represents the height dimension of the image.
   */
  public int getImageHeight() {
    int height = this.imageHeight;
    return height;
  }

  /**
   * Method to get the cap value of the image.
   *
   * @return Will return the cap value in the form of an integer.
   */
  public int getCap() {
    int tempCap = this.cap;
    return tempCap;
  }

  /**
   * A method to return all the pixels of an image in the form of a 2d array of RGB values.
   *
   * @return Will be a 2d array of RGB values in the form of objects aka Pixels.
   */
  public Pixel[][] getAllPixels() {
    Pixel[][] copy = new Pixel[this.imageHeight][this.imageWidth];
    for (int i = 0; i < copy.length; i++) {
      for (int j = 0; j < copy[i].length; j++) {
        Pixel cur = this.pixels[i][j];
        copy[i][j] = new Pixel(cur.getRed(), cur.getGreen(), cur.getBlue());
      }
    }
    return copy;
  }


  /**
   * This will flip an image horizontally.
   *
   * @return a new image being flipped horizontally
   */
  public Image flipHorizontal() {
    Pixel[][] resultPixel = new Pixel[this.getImageHeight()][this.getImageWidth()];
    Pixel[][] thisPixel = this.getAllPixels();

    for (int i = 0; i < this.getImageHeight(); i++) {
      for (int j = 0; j < this.getImageWidth(); j++) {
        resultPixel[i][j] = thisPixel[i][this.getImageWidth() - j - 1];
      }
    }
    Image result = new Image(this.getImageWidth(), this.getImageHeight(), this.getCap(),
            resultPixel);
    return result;
  }

  /**
   * This will flip an image Vertically.
   *
   * @return a new image being flipped Vertically
   */
  public Image flipVertical() {
    Pixel[][] resultPixel = new Pixel[this.getImageHeight()][this.getImageWidth()];
    Pixel[][] thisPixel = this.getAllPixels();

    for (int i = 0; i < this.getImageHeight(); i++) {
      for (int j = 0; j < this.getImageWidth(); j++) {
        resultPixel[i][j] = thisPixel[this.getImageHeight() - i - 1][j];
      }
    }

    Image result = new Image(this.getImageWidth(), this.getImageHeight(), this.getCap(),
            resultPixel);
    return result;
  }


  /**
   * Brighten the every pixel in the image.
   *
   * @param value the brighten value
   * @return the new image being processed
   */
  public Image brighten(int value) {
    Pixel[][] resultPixel = new Pixel[this.getImageHeight()][this.getImageWidth()];
    Pixel[][] thisPixel = this.getAllPixels();

    for (int i = 0; i < this.getImageHeight(); i++) {
      for (int j = 0; j < this.getImageWidth(); j++) {
        resultPixel[i][j] = thisPixel[i][j].brighten(value, this.getCap());
      }
    }

    Image result = new Image(this.getImageWidth(), this.getImageHeight(), this.getCap(),
            resultPixel);
    return result;
  }

  /**
   * Visualize the value, intensity or luma of the image.
   *
   * @param type which grayscale to use
   * @return the new image being processed
   */
  public Image visualize(String type) {
    Pixel[][] resultPixel = new Pixel[this.getImageHeight()][this.getImageWidth()];
    Pixel[][] thisPixel = this.getAllPixels();

    for (int i = 0; i < this.getImageHeight(); i++) {
      for (int j = 0; j < this.getImageWidth(); j++) {
        resultPixel[i][j] = thisPixel[i][j].visualize(type, this.getCap());
      }
    }

    Image result = new Image(this.getImageWidth(), this.getImageHeight(), this.getCap(),
            resultPixel);
    return result;
  }

  /**
   * a helper that ignore the out of bound pixel.
   *
   * @param height    the height of the pixel
   * @param width     the width of the pixel
   * @param pixelList the pixel list we get from
   * @return the pixel with the given height and width, if it is out of bound,
   *         return a pixel with 0 for the red, green, and blue value;
   */
  private Pixel getPixelAt(int height, int width, Pixel[][] pixelList) {
    int red;
    int green;
    int blue;
    try {
      red = pixelList[height][width].getRed();
      green = pixelList[height][width].getGreen();
      blue = pixelList[height][width].getBlue();
    } catch (ArrayIndexOutOfBoundsException e) {
      red = 0;
      green = 0;
      blue = 0;
    }
    Pixel result = new Pixel(red, green, blue);
    return result;
  }

  /**
   * processing the image with filtering processing algorithms.
   *
   * @param type type of processing
   * @return the image after processed
   */
  public Image filter(String type) {
    Pixel[][] resultPixel = new Pixel[this.getImageHeight()][this.getImageWidth()];
    Pixel[][] thisPixel = this.getAllPixels();

    for (int i = 0; i < this.getImageHeight(); i++) {
      for (int j = 0; j < this.getImageWidth(); j++) {
        if (type.equals("blur")) {
          ArrayList<Pixel> pixelList = new ArrayList<Pixel>(9);
          for (int h = i - 1; h <= i + 1; h++) {
            for (int w = j - 1; w <= j + 1; w++) {
              pixelList.add(getPixelAt(h, w, thisPixel));
            }
          }
          resultPixel[i][j] = thisPixel[i][j].filter(pixelList, type, this.getCap());
        } else if (type.equals("sharpen")) {
          ArrayList<Pixel> pixelList = new ArrayList<Pixel>(25);
          for (int h = i - 2; h <= i + 2; h++) {
            for (int w = j - 2; w <= j + 2; w++) {
              pixelList.add(getPixelAt(h, w, thisPixel));
            }
          }
          resultPixel[i][j] = thisPixel[i][j].filter(pixelList, type, this.getCap());
        }

      }
    }

    Image result = new Image(this.getImageWidth(), this.getImageHeight(), this.getCap(),
            resultPixel);
    return result;
  }

  /**
   * The method to create a Map contain all the number of RBG, intensity,
   * and the total number of pixel of an image.
   * @return the Map
   */
  public Map<String, Double> histogram() {
    Map<String, Integer> result = new HashMap<String, Integer>();
    for (int ii = 0; ii <= 255; ii ++) {
      result.put("red" + ii, 0);
      result.put("green" + ii, 0);
      result.put("blue" + ii, 0);
      result.put("intensity" + ii, 0);
    }

    for (int i = 0; i < this.getImageHeight(); i++) {
      for (int j = 0; j < this.getImageWidth(); j++) {
        Pixel current = this.getPixel(i, j);
        result.put("red" + current.getRed(), result.get("red" + current.getRed()) + 1);
        result.put("green" + current.getGreen(), result.get("green" + current.getGreen()) + 1);
        result.put("blue" + current.getBlue(), result.get("blue" + current.getBlue()) + 1);
        // get the intensity of the current pixel
        int intensity = (int) Math.round((current.getRed() * 0.2126) +
                (current.getGreen() * 0.7152) +
                (current.getBlue() * 0.0722));
        result.put("intensity" + intensity, result.get("intensity" + intensity) + 1);
      }
    }

    int totalPixel = this.getImageHeight() * this.getImageWidth();
    Map<String, Double> ratio = new HashMap<String, Double>();
    for (Map.Entry<String, Integer> s : result.entrySet()) {
      if (s.getValue() == 0) {
        ratio.put(s.getKey(), 0.0);
      } else {
        ratio.put(s.getKey(), ((double)s.getValue() / (double)totalPixel));
      }
    }

    return ratio;
  }

}
















