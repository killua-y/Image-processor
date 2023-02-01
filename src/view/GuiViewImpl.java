package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.ImageIcon;

import controller.IGuiController;
import model.Image;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * This class will visualize the GUI to the user and also manage the action listeners for all
 * buttons and interactions that the user makes.
 */
public class GuiViewImpl extends JFrame implements IGuiView, ActionListener {
  private JPanel mainPanel;
  private JScrollPane mainScrollPane;
  private JLabel iLabel;
  private JButton loadButton;
  private JButton saveButton;
  private JLabel brightenDisplay;
  private JLabel darkenDisplay;
  private JLabel optionDisplay;
  private DefaultListModel imageList;
  private JList<String> listOfStrings;
  private JPanel barPanel;
  private JPanel currentBarGraph;
  private JButton darkenButton;
  private JButton brightenButton;
  private JButton optionButton;

  /**
   * Constructor for IGuiViewImpl.
   * Note: Code taken from sample provided code and adapted
   * to make the following.
   */
  public GuiViewImpl() {
    super();
    setTitle("Image Processor");
    setSize(1000, 800);
    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //scroll bars around this main panel
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);
    addVisuals();
    pack();
    setVisible(true);
  }

  private void addVisuals() {
    JPanel imagePanel;
    JPanel uiPanel;
    JPanel finalLowerUI;

    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    mainScrollPane = new JScrollPane(mainPanel);

    //text area
    JTextArea textArea = new JTextArea(10, 20);
    textArea.setBorder(BorderFactory.createTitledBorder("How to use the Image Processor"));
    mainPanel.add(textArea);
    String startup = "Please read the instructions below on how to use the program:\n" +
            "• Load an image through the load file button below.\n" +
            "• Save an image that you modified by hitting the save file button.\n" +
            "• Horizontally Flip an image by hitting the modifications button.\n" +
            "• Vertically Flip an image by hitting the modifications button.\n" +
            "• Brighten or Darken an image by hitting the respective button below then entering " +
            " a value to brighten or darken by(positive value to brighten and " +
            "negative for darken).\n" +
            "• Visualize the Red Component by hitting the modifications button.\n" +
            "• Visualize the Green Component by hitting the modifications button.\n" +
            "• Visualize the Blue Component by hitting the modifications button.\n" +
            "• Visualize the Value Component by hitting the modifications button.\n" +
            "• Visualize the Intensity Component by hitting the modifications button.\n" +
            "• Visualize the Luma Component by hitting the modifications button.\n" +
            "• Greyscale an image by hitting the modifications button.\n" +
            "• Sepia Tone an Image by hitting the modifications button.\n" +
            "• Blur an Image by hitting the modifications button.\n" +
            "• Sharpen an Image an image by hitting the modifications button.\n" +
            "Instructions:\nPlease load an image first by following the load syntax and then you " +
            "can modify it however you please!\n" +
            "Note: The images provided we created using https://www.pixilart.com/";
    textArea.setText(startup);

    //show an image with a scrollbar
    imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("The Image you're Modifying and a " +
            "Histogram of your Current Image's Value and Frequency"));
    iLabel = new JLabel();
    JScrollPane imageScrollPane;
    imageScrollPane = new JScrollPane(iLabel);
    imageScrollPane.setPreferredSize(new Dimension(600, 600));
    imagePanel.add(imageScrollPane);

    mainPanel.add(imagePanel);

    // HISTOGRAM
    BarChart barGraph = new BarChart();
    barPanel = new JPanel();
    currentBarGraph = new JPanel();
    barPanel = barGraph.drawBar(null);
    currentBarGraph.add(barPanel);
    mainPanel.add(currentBarGraph);

    // COMMANDS
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Program Functions"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);

    //Brighten
    JPanel inputDialogPanel = new JPanel();
    inputDialogPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(inputDialogPanel);

    brightenButton = new JButton("Click to enter Brighten Value");
    brightenButton.setActionCommand("Brighten");
    brightenButton.addActionListener(this);
    inputDialogPanel.add(brightenButton);

    brightenDisplay = new JLabel("");
    inputDialogPanel.add(brightenDisplay);

    //Darken
    JPanel inputDialogPanel2 = new JPanel();
    inputDialogPanel2.setLayout(new FlowLayout());
    dialogBoxesPanel.add(inputDialogPanel2);

    darkenButton = new JButton("Click to enter Darken Value");
    darkenButton.setActionCommand("Darken");
    darkenButton.addActionListener(this);
    inputDialogPanel2.add(darkenButton);

    darkenDisplay = new JLabel("");
    inputDialogPanel2.add(darkenDisplay);

    //Modifications
    JPanel optionsDialogPanel = new JPanel();
    optionsDialogPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(optionsDialogPanel);

    optionButton = new JButton("Click to select modifications");
    optionButton.setActionCommand("Option");
    optionButton.addActionListener(this);
    optionsDialogPanel.add(optionButton);

    optionDisplay = new JLabel("");
    optionsDialogPanel.add(optionDisplay);

    // Section for load and save button
    finalLowerUI = new JPanel();
    finalLowerUI.setLayout(new BorderLayout());
    JPanel filePanel;
    filePanel = new JPanel();
    filePanel.setLayout(new FlowLayout());
    loadButton = new JButton("Load a file");
    loadButton.setActionCommand("Load a file");
    filePanel.add(loadButton);

    saveButton = new JButton("Save a file");
    saveButton.setActionCommand("Save a file");
    filePanel.add(saveButton);
    uiPanel = new JPanel();
    uiPanel.add(filePanel, BorderLayout.PAGE_START);

    JPanel result = new JPanel();
    JPanel selectionListPanel = new JPanel();
    selectionListPanel.setPreferredSize(new Dimension(200,400));
    selectionListPanel.setBorder(BorderFactory.createTitledBorder("Select your Image"));
    selectionListPanel.setLayout(new BoxLayout(selectionListPanel, BoxLayout.X_AXIS));
    result.add(selectionListPanel, BorderLayout.PAGE_START);
    imageList = new DefaultListModel();
    listOfStrings = new JList<>(imageList);
    listOfStrings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    selectionListPanel.add(new JScrollPane(listOfStrings));
    uiPanel.add(result, BorderLayout.CENTER);
    mainPanel.add(uiPanel, BorderLayout.CENTER);

    getContentPane().add(mainScrollPane, BorderLayout.CENTER);
  }

  /**
   * This class will visualize and tell the program what to do when the brighten, darken, or
   * modifications button are hit.
   * @param arg the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent arg) {
    switch (arg.getActionCommand()) {
      case "Brighten":
        brightenDisplay.setText(JOptionPane.showInputDialog("Please enter the brighten value"));
        break;
      case "Darken":
        darkenDisplay.setText(JOptionPane.showInputDialog("Please enter the darken value"));
        break;
      case "Option":
        String[] options = {"Horizontal Flip", "Vertical Flip", "Red Component", "Green Component",
          "Blue Component", "Value Component", "Intensity Component", "Luma Component",
          "Greyscale", "Sepia", "Blur", "Sharpen"};
        int retvalue = JOptionPane.showOptionDialog(GuiViewImpl.this,
                "Please choose a modification to apply", "Options",
                JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[4]);
        optionDisplay.setText(options[retvalue]);
        break;
      default:
        render("The button is invalid");
        break;
    }
  }

  @Override
  public void render(String message) {
    JOptionPane.showMessageDialog(this, message, "Error Message",
            JOptionPane.PLAIN_MESSAGE);
  }

  /**
   * This class will initialize all other actionListeners.
   * Note: Used lambda functions to instantiate action listeners as Professor Lerner recommended.
   *
   * @param controller This will intake a GUI controller
   */
  @Override
  public void intializeGUIListeners(IGuiController controller) {
    loadButton.addActionListener(x -> controller.load()); //loads image
    saveButton.addActionListener(x -> controller.save(listOfStrings.getSelectedValue())); //save
    listOfStrings.addListSelectionListener(x ->
            controller.selectAndDisplayImage(listOfStrings.getSelectedValue())); //load selected img
    darkenButton.addActionListener(x -> controller.apply(listOfStrings.getSelectedValue(),
            "darken"));
    brightenButton.addActionListener(x -> controller.apply(listOfStrings.getSelectedValue(),
            "brighten"));
    optionButton.addActionListener(x -> controller.apply(listOfStrings.getSelectedValue(),
            "Option"));
  }

  /**
   * This class will be adding the image to be displayed onto the screen based on whatever the
   * user selects from the list of possible images.
   * @param saveName Will be the name of the image that it is saved under
   *                 in order to select it.
   * @param toLoad Will be the image to load for the GUI to display.
   */
  @Override
  public void selectImage(String saveName, Image toLoad) {
    this.imageDisplay(saveName, toLoad);

    if (!this.imageList.contains(saveName)) {
      this.imageList.add(0, saveName);
      this.listOfStrings.setSelectedIndex(0);
    }

    for (int i = 0; i < this.imageList.size(); i++) {
      if (saveName.equals(this.imageList.get(i))) {
        this.listOfStrings.setSelectedIndex(i);
      }
    }
  }

  /**
   * This method will be drawing the actual image onto the screen into the GUI.
   * Note: Code used from previous provided code example on how to load images
   * @param saveName Will be the name of the image that it is saved under
   *                 in order to select it.
   * @param toLoad Will be the image to load for the GUI to display.
   */
  @Override
  public void imageDisplay(String saveName, Image toLoad) {
    int width = toLoad.getImageWidth();
    int height = toLoad.getImageHeight();
    BufferedImage image = new BufferedImage(width, height, TYPE_INT_RGB);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Color c = new Color(
                toLoad.getPixel(i, j).getRed(),
                toLoad.getPixel(i, j).getGreen(),
                toLoad.getPixel(i, j).getBlue());
        image.setRGB(j, i, c.getRGB());
      }
    }
    ImageIcon icon = new ImageIcon(image);

    ImageIcon result = icon;
    this.iLabel.setIcon(result);

    currentBarGraph.remove(barPanel);
    BarChart barChart = new BarChart();
    barPanel = barChart.drawBar(toLoad);
    currentBarGraph.add(barPanel);
  }
}
