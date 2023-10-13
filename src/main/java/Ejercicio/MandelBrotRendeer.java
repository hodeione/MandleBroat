package Ejercicio;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.*;

public class MandelBrotRendeer extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int MAX_ITER = 1000;

    private BufferedImage image;
    private JPanel panel;
    private JSpinner spinner;
    private boolean isRendering = false;

    public MandelBrotRendeer() {
        initUI();
    }

    private void initUI() {

        setTitle("Conjunto del Mandelbrot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    g.drawImage(image, 0, 0, this);
            }
        }
        };
        add(panel, BorderLayout.CENTER);

        spinner = new JSpinner(new SpinnerNumberModel(2, 1, 1000, 4));
        add(spinner, BorderLayout.SOUTH);

        JButton botonRenderizado = new JButton("Renderizar");
        botonRenderizado.addActionListener(e -> startRending());
        add(botonRenderizado, BorderLayout.NORTH);
    }
    private void startRending(){
        if (!isRendering){
            final int[] numIteraciones = {(int) spinner.getValue()};
          isRendering = true;

          new Thread(()->{
              while (isRendering){
                  renderMandelbrot(numIteraciones[0]);
                  try {
                      Thread.sleep(1000/ numIteraciones[0]);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  numIteraciones[0]++;
              }
          }).start();
        }
    }

    private void renderMandelbrot(int numIteraciones) {
        int numTrabajadores = (int) spinner.getValue();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        ExecutorService executor = Executors.newFixedThreadPool(numTrabajadores);

            for (int i = 0; i < numTrabajadores; i++) {
                int desde = i * HEIGHT / numTrabajadores;
                int hasta = (i + 1) * HEIGHT / numTrabajadores;

               executor.execute(() -> calcularMandelbrot(desde, hasta));
            }
            executor.shutdown();
    }

    private void calcularMandelbrot(int desde, int hasta) {

        for (int y = desde; y < hasta; y++) {
            for (int x = 0; x < WIDTH; x++) {
                double zx = 0;
                double zy = 0;
                double cX = (x - WIDTH / 2) * 4.0 / WIDTH;
                double cY = (y - HEIGHT / 2) * 4.0 / HEIGHT;
                int iter = 0;

                while (zx * zx + zy * zy < 4 && iter < MAX_ITER) {
                    double tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter++;
                }
                Color color = iter < MAX_ITER ? Color.BLACK : Color.getHSBColor((float) iter / MAX_ITER, MAX_ITER, 154);
                image.setRGB(x, y, color.getRGB());
            }
            SwingUtilities.invokeLater(() -> panel.repaint(0,desde,WIDTH,hasta-desde)); }
    }

    public void paintMandelbrot(List<Color> colors, int desde) {
        for (int y = 0; y <  colors.size(); y++) {
            image.setRGB(y, desde +y, colors.get(y).getRGB());

        }
    }

}