package controller;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.IImageProcess;
import model.Image;
import view.EmptyView;
import view.IGuiView;

/**
 * This class will implement the interface for our GUI controller.
 * The class will control the entire program for the GUI side which is communicating with the view
 * and utilizing delegation to use the previous methods.
 */
public class GuiControllerImpl extends Component implements IGuiController {
  private IImageProcess model;
  private IGuiView view;
  private IController delegate;

  /**
   * This class will be implementing necessary methods to control the GUI.
   * @param model The model being taken in will be the image.
   * @throws IOException if the model given is null.
   */
  public GuiControllerImpl(IImageProcess model) throws IOException {
    if (model == null) {
      this.view.render("Model given cannot be null.");
    }
    this.model = model;
    this.delegate = new ControllerImpl(this.model, new EmptyView(), new StringReader(""));
  }

  // Note: Code taken from example file SwingFeaturesFrame.java and adapted for load + save
  @Override
  public void load() {
    JFileChooser fchooser = new JFileChooser(".");
    String filePath = "";
    String saveName = "";
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "PPM, JPG, PNG, and BMP images", "jpg", "png", "ppm", "bmp");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(GuiControllerImpl.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      filePath = f.getAbsolutePath();
      saveName = JOptionPane.showInputDialog("Load file as: ");
    } else {
      this.view.render("Must input a name for the image.");
      return;
    }

    this.delegate.load(filePath, saveName);
    Image toLoad = this.model.getImage(saveName);
    this.view.selectImage(saveName, toLoad);
    // this.view.selectImage(saveName, this.model.);  TODO: UPDATE HISTOGRAM
  }

  @Override
  public void startup(IGuiView view) {
    if (view == null) {
      this.view.render("View given cannot be null.");
    }
    this.view = view;
    view.intializeGUIListeners(this);
  }

  @Override
  public void selectAndDisplayImage(String imageName) {
    if (imageName == null) {
      this.view.render("Image name given cannot be null.");
    }

    Image save = this.model.getImage(imageName);
    this.view.imageDisplay(imageName, save);
  }

  @Override
  public void save(String imageName) {
    if (imageName == null) {
      this.view.render("Name is invalid.");
    }

    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog((Component) this.view);
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "PPM, JPG, PNG, and BMP images", "jpg", "png", "ppm", "bmp");
    fchooser.setFileFilter(filter);
    if (retvalue == JFileChooser.CANCEL_OPTION) {
      return;
    } else if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      if (f == null) {
        this.view.render("Invalid file type.");
        return;
      }

      String filePath = f.getAbsolutePath();
      Image saveImage = this.model.getImage(imageName);

      if (filePath == null || saveImage == null) {
        this.view.render("the file path and image to save cannot be null.");
      }

      File out = new File(filePath);
      if (filePath.endsWith(".png") || filePath.endsWith(".jpg") || filePath.endsWith(".bmp")) {
        //this.otherFormatSave(imageName);
      }
    }
  }

  private void otherFormatSave(String imageSaveName, String imageName, String type) {
  // This would be the method to save other types of images besides just ppm
  }


  @Override
  public void apply(String imageName, String command) {
    int brightenValue = 0;
    int darkenValue = 0;

    if (imageName == null) {
      this.view.render("Image could not be found.");
    }

    String filePath = JOptionPane.showInputDialog("Save file as: ");

    if (filePath == null) {
      this.view.render("Must enter a name to save the file as.");
    }

    if (command.equals("brighten")) {
      String tempValue = JOptionPane.showInputDialog("Brighten by: ");
      try {
        brightenValue = Integer.valueOf(tempValue);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Given an invalid brighten value");
      }
    } else if (command.equals("darken")) {
      String tempValue = JOptionPane.showInputDialog("Darken by: ");
      try {
        darkenValue = Integer.valueOf(tempValue) * -1;
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    }

    switch (command) {
      case "horizontal-flip":
        this.model.flip("horizontal-flip", imageName, filePath);
        break;
      case "vertical-flip":
        this.model.flip("vertical-flip", imageName, filePath);
        break;
      case "brighten":
        this.model.brighten(brightenValue, imageName, filePath);
        break;
      case "darken":
        this.model.brighten(darkenValue, imageName, filePath);
        break;
      case "red-component":
        this.model.componentVisualization("red-component", imageName, filePath);
        break;
      case "green-component":
        this.model.componentVisualization("green-component", imageName, filePath);
        break;
      case "blue-component":
        this.model.componentVisualization("blue-component", imageName, filePath);
        break;
      case "value-component":
        this.model.componentVisualization("value-component", imageName, filePath);
        break;
      case "intensity-component":
        this.model.componentVisualization("intensity-component", imageName, filePath);
        break;
      case "luma-component":
        this.model.componentVisualization("luma-component", imageName, filePath);
        break;
      case "greyscale":
        this.model.componentVisualization("greyscale", imageName, filePath);
        break;
      case "sepia":
        this.model.componentVisualization("sepia", imageName, filePath);
        break;
      case "blur":
        this.model.filter("blur", imageName, filePath);
        break;
      case "sharpen":
        this.model.filter("sharpen", imageName, filePath);
        break;
      default:
        this.view.render("The button was invalid");
        break;
    }
  }
}
