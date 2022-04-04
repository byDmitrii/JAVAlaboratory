package com.company;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import javax.swing.JFileChooser.*;
import javax.swing.filechooser.*;
import javax.imageio.ImageIO.*;
import java.awt.image.*;

/**
 * Класс, необходимый для отображения графического интерфейса
 * и обработки действий пользователя
 */
public class FractalExplorer
{
    /** Строки **/
    private int rowsRemaining;
    /** Ширина и высота отображения - целочисленная, поэтому int **/
    private int displaySize;

    /** JImageDisplay для обновления отображения изображения **/
    private JImageDisplay display;

    /** Объект для разных типов фракталов (задел на будущее в том числе) **/
    private FractalGenerator fractal;

    /**
     * Объект, определяющий размер текущего диапазона просмотра
     * (То, что показывается в настоящий момент)
     */
    private Rectangle2D.Double range;
    private JButton saveButton;
    private JButton resetButton;
    private JComboBox myComboBox;

    /**
     * конструктор, принимающий размер дисплея и сохраняющий его,
     * после чего инициализирующий объекты диапазона и генератора фрактала
     */
    public FractalExplorer(int size) {
        displaySize = size;
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);

    }

    // Метод инициализации Swing и кнопки сброса и сохранения
    public void createAndShowGUI()
    {
        display.setLayout(new BorderLayout());
        JFrame Frame = new JFrame("FE");
        // Добавляет и центрует объект изображения
        Frame.add(display, BorderLayout.CENTER);

        // Кнопка сброса
        JButton resetButton = new JButton("Reset");
        Frame.add(resetButton, BorderLayout.SOUTH);
        ButtonHandler resetHandler = new ButtonHandler();
        // Обработка события нажатия на кнопку
        resetButton.addActionListener(resetHandler);
        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);

        // Создание объекта combo-box
        JComboBox myComboBox = new JComboBox();

        // Элементы combo-box
        FractalGenerator mandelbrotFractal = new Mandelbrot();
        myComboBox.addItem(mandelbrotFractal);
        FractalGenerator tricornFractal = new Tricorn();
        myComboBox.addItem(tricornFractal);
        FractalGenerator burningShipFractal = new BurningShip();
        myComboBox.addItem(burningShipFractal);

        // Обработка нажатия будет ButtonHandler
        ButtonHandler fractalChooser = new ButtonHandler();
        myComboBox.addActionListener(fractalChooser);

        /*
         Создаём панель и добавляем на неё combo-box.
         Добавляем также текст пояснения "Fractal:"
         Наконец, прописываем расположение этой панели наверху
         */
        JPanel myPanel = new JPanel();
        JLabel myLabel = new JLabel("Fractal =)");
        myPanel.add(myLabel);
        myPanel.add(myComboBox);
        Frame.add(myPanel, BorderLayout.NORTH);

        // Кнопка сохранения

        JButton saveButton = new JButton("Save");
        JPanel myBottomPanel = new JPanel();
        myBottomPanel.add(saveButton);
        myBottomPanel.add(resetButton);
        Frame.add(myBottomPanel, BorderLayout.SOUTH);

        // Обработка события будет ButtonHandler

        ButtonHandler saveHandler = new ButtonHandler();
        saveButton.addActionListener(saveHandler);

        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Frame.pack();
        Frame.setVisible(true);
        Frame.setResizable(false);

    }

    /*
      Вспомогательный метод для отображения фрактала.
      Он проходится по пикселям на дисплее и вычисляет количество итераций для координат во фрактале
      Если кол-во итераций = -1, он устанавливает чёрный цвет пикселя,
      иначе же выбирает значение в зависимости от количества итераций.
      Когда всё готово - обновляет дисплей
     */
    private void drawFractal()
    {
        for (int x=0; x<displaySize; x++){
            for (int y=0; y<displaySize; y++){
                double xCoord = fractal.getCoord(range.x,
                        range.x + range.width, displaySize, x);
                double yCoord = fractal.getCoord(range.y,
                        range.y + range.height, displaySize, y);
                int i = fractal.numIterations(xCoord, yCoord);
                if (i == -1){
                    display.drawPixel(x, y, 0);
                }
                else{
                    float hue = 0.7f + (float) i / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    display.drawPixel(x, y, rgbColor);
                }
            }
        }
        display.repaint();
    }

    /*
    Включает или отключает кнопки интерфейса
    и поле со списком в зависимости от заданного значения
    */
    private void enableUI(boolean val) {
        myComboBox.setEnabled(val);
        resetButton.setEnabled(val);
        saveButton.setEnabled(val);
    }

    //Внутренний класс для обработки событий ActionListener

    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();

            // Выборка элемента из combo-box

            if (e.getSource() instanceof JComboBox) {
                JComboBox mySource = (JComboBox) e.getSource();
                fractal = (FractalGenerator) mySource.getSelectedItem();
                fractal.getInitialRange(range);
                drawFractal();
            }

            //Если нажатие на кнопку сброса, сбрасывает приближение

            else if (command.equals("Reset")) {
                fractal.getInitialRange(range);
                drawFractal();
            }
            //Если нажатие на кнопку сохранения, сохраняет текущее отображение фрактала
            else if (command.equals("Save png")) {

                JFileChooser myFileChooser = new JFileChooser();

                // Сохрание только png
                FileFilter extensionFilter = new FileNameExtensionFilter("PNG Images", "png");
                myFileChooser.setFileFilter(extensionFilter);

                // Проверка через filechooser

                myFileChooser.setAcceptAllFileFilterUsed(false);

                // Выбираем путь сохранения

                int userSelection = myFileChooser.showSaveDialog(display);

                if (userSelection == JFileChooser.APPROVE_OPTION) {

                    // Файл и его имя
                    java.io.File file = myFileChooser.getSelectedFile();
                    String file_name = file.toString();

                    // Попытка сохранения на диск
                    try {
                        BufferedImage showImage = display.getImage();
                        javax.imageio.ImageIO.write(showImage, "png", file);
                    }
                    // Обработка и вывод исключений
                    catch (Exception exception) {
                        JOptionPane.showMessageDialog(display, exception.getMessage(), "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                    }
                }
                // Пользователь решил не сохрнаять файл
                else return;
            }
        }
    }

    //Внутренний класс для обработки событий MouseAdapter

    private class MouseHandler extends MouseAdapter
    {
        //Приближение при клике

        @Override
        public void mouseClicked(MouseEvent e)
        {
            // Принимает x координату нажатия
            int x = e.getX();
            double xCoord = fractal.getCoord(range.x, range.x + range.width, displaySize, x);
            // Принимает y координату нажатия
            int y = e.getY();
            double yCoord = fractal.getCoord(range.y, range.y + range.height, displaySize, y);

            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);

            // Перерисовывает фрактал после приближения
            drawFractal();
        }
    }

    private class FractalWorker extends SwingWorker<Object,Object> {
        // Целочисленная y-координата вычисляемой строки
        int yCoordinate;
        /* Массив чисел типа int для хранения
        вычисленных значений RGB для каждого пикселя в этой строке */
        int[] arrayRGB;
        /* Конструктор принимает координату y
        в качестве аргумента и сохраняет ее */
        private FractalWorker(int row){
            yCoordinate = row;
        }
        protected Object doInBackground(){
            arrayRGB = new int[displaySize];
            //Цикл сохраняет каждое значение RGB в соответствующем элементе целочисленного массива
            for (int i = 0; i < arrayRGB.length; i++) {
                //Нахождение координат
                double xCoord = fractal.getCoord(range.x,
                        range.x + range.width, displaySize, i);
                double yCoord = fractal.getCoord(range.y,
                        range.y + range.height, displaySize, yCoordinate);
                // Вычисление количества итераций для координат
                int iteration = fractal.numIterations(xCoord, yCoord);
                //Если -1, то значения RGB int в черный цвет
                if (iteration == -1){
                    arrayRGB[i] = 0;
                }
                else {
                    // В противном случае выберите
                    // значение оттенка на основе числа итераций
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    //Обновление массива
                    arrayRGB[i] = rgbColor;
                }
            }
            return null;
        }

        /*
        Вызывается после завершения фоновой задачи. Рисует пиксели
        для текущей строки и обновляет отображение
        */
        protected void done() {
            //Рисование в пикселях,
            // которые были вычислены в doInBackground ()
            for (int i = 0; i < arrayRGB.length; i++) {
                display.drawPixel(i, yCoordinate, arrayRGB[i]);
            }
            //метод перерисовки (рекомендуется для сложных анимаций и компонентов)
            display.repaint(0, 0, yCoordinate, displaySize, 1);
            //Уменьшение значения «rows remaining» на 1, как
            //последний шаг данной операции. Затем, если после уменьшения значение «rows
            //remaining» равно 0, вызов метода enableUI (true).
            rowsRemaining--;
            if (rowsRemaining == 0) {
                enableUI(true);
            }
        }
    }


    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(600);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}