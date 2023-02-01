package controller;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import javax.imageio.ImageIO;

import model.IImageProcess;
import model.Image;
import model.Pixel;
import view.IView;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * This class represents the implementation of a controller for an image processor. It will intake
 * data from the user via the command line console, it will delegate the commands to the model,
 * and send messages back to the user through the view.
 * Will check if the model, view, input, or commands are null as well.
 */
public class ControllerImpl implements IController {
  private final Map<String, Function<String[], ICommand>> commands = new HashMap<>();
  private IImageProcess model;
  private IView view;
  private Readable in;

  /**
   * This is the constructor for the controller of the program. It will initialize the program with
   * the given model and will intake the user input. The HashMap containing all the possible
   * commands is also initialized.
   *
   * @param model represents the model which we will modify based on the users commands.
   * @param view  represents the view which will be used to communicate with the user.
   * @param in    represents the users input to control the program and use commands through the
   *              command line.
   * @throws IllegalArgumentException if any of the above parameters are null.
   */
  public ControllerImpl(IImageProcess model, IView view, Readable in) throws
          IllegalArgumentException {
    if (model == null || view == null || in == null) {
      throw new IllegalArgumentException("The model, view, and input cannot be null.");
    }
    this.model = model;
    this.view = view;
    this.in = in;

    this.commands.put("brighten", x -> new Brighten(x, this.view));
    this.commands.put("vertical-flip", x -> new Flip(x, this.view));
    this.commands.put("horizontal-flip", x -> new Flip(x, this.view));
    this.commands.put("red-component", x -> new Visualization(x, this.view));
    this.commands.put("blue-component", x -> new Visualization(x, this.view));
    this.commands.put("green-component", x -> new Visualization(x, this.view));
    this.commands.put("value-component", x -> new Visualization(x, this.view));
    this.commands.put("luma-component", x -> new Visualization(x, this.view));
    this.commands.put("intensity-component", x -> new Visualization(x, this.view));
    this.commands.put("greyscale", x -> new Visualization(x, this.view));
    this.commands.put("sepia", x -> new Visualization(x, this.view));
    this.commands.put("blur", x -> new Filter(x, this.view));
    this.commands.put("sharpen", x -> new Filter(x, this.view));
  }

  @Override
  public void runProgram() throws IllegalArgumentException {
    Scanner s = new Scanner(this.in);

    String startup = "Please use the following syntax below to utilize commands:\n" +
            "• Loading a photo into the program:   load imageLocation imageName\n" +
            "• Save an image:                      save fileSaveName imageName\n" +
            "• Horizontally Flip an image:         horizontal-flip imageName modifiedImageName\n" +
            "• Vertically Flip an image:           vertical-flip imageName modifiedImageName\n" +
            "• Brighten or Darken an image:        brighten IntegerIncrementAmount(Positive for " +
            "brighten and negative for darken) imageName modifiedImageName\n" +
            "• Visualize the Red Component:        red-component imageName modifiedImageName\n" +
            "• Visualize the Green Component:      green-component imageName modifiedImageName\n" +
            "• Visualize the Blue Component:       blue-component imageName modifiedImageName\n" +
            "• Visualize the Value Component:      value-component imageName modifiedImageName\n" +
            "• Visualize the Intensity Component:  intensity-component imageName " +
            "modifiedImageName\n" +
            "• Visualize the Luma Component:         luma-component imageName modifiedImageName\n" +
            "• Greyscale an image:                 greyscale imageName modifiedImageName\n" +
            "• Sepia Tone an Image:                sepia imageName modifiedImageName\n" +
            "• Blur an Image:                      blur imageName modifiedImageName\n" +
            "• Sharpen an Image:                   sharpen imageName modifiedImageName\n" +
            "Please load an image first by following the load syntax and then you can modify it" +
            " however you please!\n" +
            "Note: The images provided we created using https://www.pixilart.com/";

    this.view.render(startup);
    this.view.render("\n");

    while (s.hasNext()) {
      ArrayList<String> command = new ArrayList<String>();
      command.add(s.nextLine());
      runHelper(command);
    }
  }

  private void runHelper(ArrayList<String> stringArrayList) {

    while (!stringArrayList.isEmpty()) {
      String next = stringArrayList.get(0);
      String[] in = next.split(" ");
      stringArrayList.remove(0);

      if (in != null && in.length == 2 && in[0].equals("-file")) {
        FileReader fileReader = null;
        try {
          fileReader = new FileReader(in[1]);
        } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
        }
        BufferedReader buffReader = new BufferedReader(fileReader);
        ArrayList<String> commandTXT = new ArrayList<String>();
        while (true) {
          try {
            if (!buffReader.ready()) {
              break;
            }
          } catch (IOException e) {
            throw new RuntimeException(e);
          }

          try {
            commandTXT.add(buffReader.readLine());
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }

        runHelper(commandTXT);

      } else if (in != null && in.length == 3 && in[0].equals("load")) {
        this.load(in[1], in[2]);
        this.view.render("Image has been loaded as " + in[2] + ".\n");
      } else if (in != null && in.length == 3 && in[0].equals("save")) {
        this.save(in[1], in[2]);
        this.view.render("Image has been saved as " + in[1] + ".\n");
      } else {
        Function<String[], ICommand> command = this.commands.getOrDefault(in[0],
                null);
        if (command == null) {
          throw new IllegalArgumentException("The command given was invalid.");
        } else {
          try {
            command.apply(in).run(this.model);
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
          }
        }
      }
    }
  }


  // Note: Below code taken from starter code and adapted to fit our load command.
  @Override
  public void load(String imageLocation, String imageName) throws IllegalArgumentException {
    int red = 0;
    int green = 0;
    int blue = 0;
    int cap = 255;
    int width = 0;
    int height = 0;

    String fileEnding = imageLocation.substring(imageLocation.length() - 4);

    if (imageLocation == null || imageName == null) {
      throw new IllegalArgumentException("The imageLocation and imageName cannot be null.");
    }

    if (!fileEnding.equals(".ppm") && !fileEnding.equals(".bmp") && !fileEnding.equals(".jpg") &&
            !fileEnding.equals(".png")) {
      throw new IllegalArgumentException("Invalid file format submitted.");
    }

    if (fileEnding.equals(".png") || fileEnding.equals(".bmp") || fileEnding.equals(".jpg")) {
      this.loadOtherFormat(imageLocation, imageName);
      return;
    }

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(imageLocation));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File could not be found.");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    width = sc.nextInt();
    height = sc.nextInt();
    cap = sc.nextInt();
    Pixel[][] pixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        red = sc.nextInt();
        green = sc.nextInt();
        blue = sc.nextInt();
        pixels[i][j] = new Pixel(red, green, blue);
      }
    }

    this.model.addImage(imageName, new Image(width, height, cap, pixels));
  }

  private void loadOtherFormat(String imageLocation, String imageName) {
    int height;
    int width;
    int cap = 255;

    BufferedImage toLoad = null;

    try {
      toLoad = ImageIO.read(new FileInputStream(imageLocation));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File path is invalid.");
    } catch (IOException e) {
      throw new IllegalArgumentException("File could not be found.");
    }

    try {
      height = toLoad.getHeight();
      width = toLoad.getWidth();
    } catch (Exception e) {
      throw new IllegalArgumentException("The height or width of the image is null.");
    }

    Pixel[][] pixels = new Pixel[height][width];

    for (int i = 0; i < toLoad.getHeight(); i++) {
      for (int j = 0; j < toLoad.getWidth(); j++) {
        Color c = new Color(toLoad.getRGB(i, j));
        int red = c.getRed();
        int green = c.getGreen();
        int blue = c.getBlue();
        toLoad.setRGB(j, i, c.getRGB());
      }
    }
    this.model.addImage(imageName, new Image(width, height, cap, pixels));
  }

  @Override
  public void save(String imageSaveName, String imageName) throws IllegalArgumentException {
    if (imageSaveName == null || imageName == null) {
      throw new IllegalArgumentException("The imageSaveName and imageName cannot be null.");
    }

    String type = imageSaveName.substring(imageSaveName.length() - 4);

    if (!type.equals(".ppm") && !type.equals(".png") && !type.equals(".bmp") &&
            !type.equals(".jpg")) {
      throw new IllegalArgumentException("This is an invalid file format.");
    }

    Image imageToSave = this.model.getImage(imageName);
    if (imageToSave == null) {
      throw new IllegalArgumentException("The image you are trying to save does not exist.");
    }

    if (type.equals(".png") || type.equals(".jpg") || type.equals(".bmp")) {
      this.otherFormatSave(imageSaveName, imageName, type);
      return;
    }

    DataOutputStream out;

    try {
      out = new DataOutputStream(new FileOutputStream(imageSaveName));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("The file failed to save...");
    }

    try {
      out.writeBytes(imageToSave.toString());
      out.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("The file failed to save...");
    }
  }

  private void otherFormatSave(String imageSaveName, String imageName, String type)
          throws IllegalArgumentException {
    Image imageToSave = this.model.getImage(imageName);

    BufferedImage image = new BufferedImage(imageToSave.getImageWidth(),
            imageToSave.getImageHeight(), TYPE_INT_RGB);

    File output = new File(imageSaveName);

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        int red = imageToSave.getPixel(j, i).getRed();
        int green = imageToSave.getPixel(j, i).getGreen();
        int blue = imageToSave.getPixel(j, i).getBlue();
        Color c = new Color(red, green, blue);
        image.setRGB(i, j, c.getRGB());
      }
    }

    try {
      ImageIO.write(image, type.substring(1), new FileOutputStream(output));
      return;
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File destination is invalid.");
    } catch (IOException e) {
      throw new IllegalArgumentException("File could not reach an output, the path was invalid.");
    }
  }
}
