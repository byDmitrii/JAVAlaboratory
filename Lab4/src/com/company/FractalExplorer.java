package com.company;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;

public class FractalExplorer {
    // Ширина и высота отображения в пикселях
    private int displaySize;
    //Ссылка JImageDisplay, для обновления отображения в разных методах в процессе вычисления фрактала
    private JImageDisplay display;
    //Объект FractalGenerator, использующий ссылку на базовый класс для отображения других типов фракталов в будущем.
    private FractalGenerator fractal;
    //Объект Rectangle2D.Double, указывающий диапазона комплексной плоскости, которая выводится на экран
    private Rectangle2D.Double range;
    /*
     Конструктор, который принимает размер дисплея, сохраняет его и
     инициализирует объекты диапазона и фрактал-генератора.
     */
    public FractalExplorer(int size) {
        displaySize = size;
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);
    }
    /*
    Этот метод инициализирует графический интерфейс Swing с помощью JFrame, содержащий объект
    JImageDisplay и кнопку для сброса дисплея.
    */
    public void createAndShowGUI()
    {
        //Устанавливаем фрейм на использование java.awt.BorderLayout для его содержимого
        display.setLayout(new BorderLayout());
        JFrame myframe = new JFrame("FE");

        //Добавляем объект отображения изображения в BorderLayout.CENTER
        myframe.add(display, BorderLayout.CENTER);

        //Создаем кнопку сброса
        JButton resetButton = new JButton("Reset");

        //Экземпляр ResetHandler на кнопке сброса
        ResetHandler handler = new ResetHandler();
        resetButton.addActionListener(handler);

        //Добавляем кнопку сброса в позицию BorderLayout.SOUTH
        myframe.add(resetButton, BorderLayout.SOUTH);
        //Экземпляр MouseHandler в компоненте фрактального отображения
        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);
        //Устанавливает операцию закрытия фрейма по умолчанию на "exit"
        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Разместить содержимое фрейма так, чтобы оно было видно, и запретить изменение размера окна
        myframe.setTitle("FRACTAL =)");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        myframe.setBounds(dimension.width/2 - 300,dimension.height/2 - 300, 600, 600);
        myframe.pack();
        myframe.setVisible(true);
        myframe.setResizable(false);
    }
    /*
    Приватный вспомогательный метод для вывода фрактала на экран. Этот метод
    должен циклически проходить через каждый пиксель в отображении (т.е.
    значения x и y будут меняться от 0 до размера отображения)
    и вычислять количество итераций для соответствующих координат в области
    отображения фракталов.
    */
    private void drawFractal()
    {
        //Перебираем каждый пиксель на дисплее
        for (int x=0; x<displaySize; x++){
            for (int y=0; y<displaySize; y++){
                /*
                Находим соответствующие координаты xCoord и yCoord
                в области отображения фрактала.
                */
                double xCoord = fractal.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = fractal.getCoord(range.y, range.y + range.height, displaySize, y);
                /*
                Вычисляем количество итераций для координат в
                области отображения фрактала.
                */
                int iteration = fractal.numIterations(xCoord, yCoord);
                //Если количество итераций равно -1 (точка не выходит за границы), установиливаем черный пиксель
                if (iteration == -1){
                    display.drawPixel(x, y, 0);
                } else {
                    // Инача выбераем значение цвета на основе числа итераций
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    //Обновляем отображение цветом для каждого пикселя
                    display.drawPixel(x, y, rgbColor);
                }
            }
        }
        /*
        Когда все пиксели будут нарисованы, обновляем JImageDisplay в соответствии с
        цветом для каждого пикселя.
        */
        display.repaint();
    }
    //Внутренний класс для обработки событий ActionListener от кнопки сброса
    private class ResetHandler implements ActionListener
    {
        /*
        Обработчик сбрасывает диапазон до начального диапазона, заданного
        генератором, а затем перерисовывает фрактал.
        */
        public void actionPerformed(ActionEvent e)
        {
            fractal.getInitialRange(range);
            drawFractal();
        }
    }
    //Внутренний класс для обработки событий java.awt.event.MouseListener с дисплея.
    private class MouseHandler extends MouseAdapter
    {
        /*
         При получении события о щелчке мышью, класс должен
         отобразить пиксельные кооринаты щелчка в область фрактала, а затем вызвать
         метод генератора recenterAndZoomRange() с координатами, по которым
         щелкнули, и масштабом 0.5. Таким образом, нажимая на какое-либо место на
         фрактальном отображении, вы увеличиваете его!
         */
        @Override //переопределяем метод базового класса
        public void mouseClicked(MouseEvent e)
        {
            //Получить координату x области отображения щелчка мыши
            int x = e.getX();
            double xCoord = fractal.getCoord(range.x,
                    range.x + range.width, displaySize, x);

            //Получить координату y области отображения щелчка мышью
            int y = e.getY();
            double yCoord = fractal.getCoord(range.y,range.y + range.height, displaySize, y);

            /*
            Вызов метода генератора RecenterAndZoomRange () с
            координатами, по которым щелкнули, и масштаблм 0,5.
            */
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            //Перерисовываем фрактал после изменения области отображения
            drawFractal();
        }
    }
    /*
    Статический метод main () для запуска FractalExplorer. Инициализирует новый
    экземпляр FractalExplorer с размером дисплея 500, вызывает
    createAndShowGUI () в объекте проводника, а затем вызывает
    drawFractal () в проводнике, чтобы увидеть исходный вид.
    */
    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(500);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}
