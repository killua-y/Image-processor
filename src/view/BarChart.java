package view;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;

import model.Image;
import model.Pixel;

/**
 * This class represents a Histogram which will be portraying the RGB and Intensity bar graphs
 * of the image that is currently being portrayed.
 */
public class BarChart extends JPanel {
  private final int width = 600;
  private final int height = 400;
  private int emptySpace = 25;
  private int numberOfY = 10;

  private Map<Integer, Double> bars = new HashMap<Integer, Double>();

  /**
   * This method will add a new bar to chart.
   * @param number color to display bar
   * @param value size of bar
   */
  public void addBar(Integer value, Double number) {
    bars.put(value, number);
    repaint();
  }

  private double getMax() {
    double result = 0;
    for (Map.Entry<Integer, Double> s : this.bars.entrySet()) {
      result = Math.max(result, s.getValue());
    }
    return result;
  }

  @Override
  protected void paintComponent(Graphics g) {
    // draw white background
    g.setColor(Color.WHITE);
    g.fillRect(emptySpace * 2, emptySpace, getWidth() - (3 * emptySpace),
            getHeight() - 3 * emptySpace);

    // determine longest bar
    double max = ((double)((int)(getMax() * 10)) / 10) + 0.1 ;

    // draw the y-axis, the number on the axis, and the horizontal line.
    for (int i = 0; i < numberOfY + 1; i++) {
      int x0 = emptySpace * 2;
      int y0 = getHeight() - ((i * (getHeight() - emptySpace * 3)) / numberOfY + emptySpace * 2);
      int y1 = y0;
      if (bars.size() > 0) {
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(emptySpace * 2, y0, getWidth() - emptySpace, y1);
        g.setColor(Color.BLACK);
        String yLabel = ((int) ((((i * max) / numberOfY)) * 100)) / 100.0 + "";
        FontMetrics metrics = g.getFontMetrics();
        int labelWidth = metrics.stringWidth(yLabel);
        g.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
      }
    }
    // draw the y label
    g.drawString("ratio", emptySpace / 2, emptySpace / 2);

    // draw the x-axis and number on the axis
    for (int i = 0; i <= 255; i++) {
      int x0 = i * (getWidth() - emptySpace * 3) / (255 - 1) + emptySpace * 2;
      int y0 = getHeight() - emptySpace * 2;
      if ((i % ((int) ((bars.size() / 20.0)) + 1)) == 0) {
        g.setColor(Color.BLACK);
        String xLabel = i + "";
        FontMetrics metrics = g.getFontMetrics();
        int labelWidth = metrics.stringWidth(xLabel);
        g.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
      }
    }
    g.drawLine(emptySpace * 2, getHeight() - emptySpace * 2,
            getWidth() - emptySpace, getHeight() - emptySpace * 2);


    // paint bars
    int width = (getWidth() / bars.size()) - 2;
    int x = emptySpace * 2;
    int y = emptySpace * 2;
    for (Map.Entry<Integer, Double> s : bars.entrySet()) {
      Double value = s.getValue();
      int height = (int) ((getHeight() - 3 * emptySpace) * ((double) value / max));
      g.setColor(Color.red);
      g.fillRect(x, getHeight() - height - y, width, height);
      g.setColor(Color.black);
      g.drawRect(x, getHeight() - height - y, width, height);
      x += (width + 2);
    }
  }


  @Override
  public Dimension getPreferredSize() {
    return new Dimension(width, height);
  }

  /**
   * Use the provided image to draw the four histogram.
   * @param image the given image
   * @return a panel contain the four histogram
   */
  public JPanel drawBar(Image image) {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

    JPanel frame1 = new JPanel();
    JPanel frame2 = new JPanel();

    JPanel barRed = new JPanel();
    JPanel barGreen = new JPanel();
    JPanel barBlue = new JPanel();
    JPanel barIntensity = new JPanel();

    // create the map contain all the information of an image.
    Map<String, Double> imageMap = new HashMap<String, Double>();
    if (image == null) {
      for (int ii = 0; ii <= 255; ii ++) {
        imageMap.put("red" + ii, 0.0);
        imageMap.put("green" + ii, 0.0);
        imageMap.put("blue" + ii, 0.0);
        imageMap.put("intensity" + ii, 0.0);
      }
    } else {
      imageMap = image.histogram();
    }

    BarChart redChart = new BarChart();
    BarChart greenChart = new BarChart();
    BarChart blueChart = new BarChart();
    BarChart intensityChart = new BarChart();
    for (int i = 0; i <= 255; i++) {
      redChart.addBar(i, imageMap.get("red" + i));
      greenChart.addBar(i, imageMap.get("green" + i));
      blueChart.addBar(i, imageMap.get("blue" + i));
      intensityChart.addBar(i, imageMap.get("intensity" + i));
    }

    barRed.add(redChart);
    barRed.setBorder(BorderFactory.createTitledBorder("red component histogram"));
    frame1.add(barRed);

    barGreen.add(greenChart);
    barGreen.setBorder(BorderFactory.createTitledBorder("green component histogram"));
    frame1.add(barGreen);

    barBlue.add(blueChart);
    barBlue.setBorder(BorderFactory.createTitledBorder("blue component histogram"));
    frame2.add(barBlue);

    barIntensity.add(intensityChart);
    barIntensity.setBorder(BorderFactory.createTitledBorder("intensity component histogram"));
    frame2.add(barIntensity);

    mainPanel.add(frame1);
    mainPanel.add(frame2);
    return mainPanel;
  }

  /**
   * This main method is to run the histogram to make sure it's working as inteded.
   * @param args Represents necessary arguments to run the program.
   */
  public static void main(String[] args) {
    Pixel p1;
    Pixel pred;
    Pixel pgreen;
    Pixel pblue;
    Pixel[][] plist1;
    Pixel[][] plist2;
    Image i1;
    Image i2;

    p1 = new Pixel(1, 2, 3);
    pred = new Pixel(255, 0, 0);
    pgreen = new Pixel(0, 255, 0);
    pblue = new Pixel(0, 0, 255);

    plist1 = new Pixel[][]{{p1, pred, pgreen, pblue}, {p1, pred, pgreen, pblue},
        {p1, pred, pgreen, pblue}, {p1, pred, pgreen, pblue}};

    plist2 = new Pixel[][]{{pred, pred, pred, pred}, {pred, pred, pred, pred},
        {pred, pred, pred, pred}, {pred, pred, pred, pred}};

    i1 = new Image(4, 4, 255, plist1);
    i2 = new Image(4, 4, 255, plist2);

    Map<String, Double> imageMap = i1.histogram();
    BarChart intensityChart = new BarChart();
    for (int i = 0; i <= 255; i++) {
      intensityChart.addBar(i, imageMap.get("intensity" + i));
    }
    Map<String, Double> imageMap2 = i2.histogram();
    BarChart redChart = new BarChart();
    for (int i = 0; i <= 255; i++) {
      redChart.addBar(i, imageMap2.get("red" + i));
    }


    JFrame frame = new JFrame("Bar Chart");
    BarChart chart = new BarChart();
    frame.getContentPane().add(intensityChart);
    //frame.getContentPane().add(redChart);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }


}
