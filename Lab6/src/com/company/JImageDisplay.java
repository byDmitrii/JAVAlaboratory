package com.company;
import javax.swing.*;
import java.awt.image.*;
import java.awt.*;


//Класс изображения, позволяющий нам выводить на экран наш фрактал

class JImageDisplay extends JComponent
{
    /*
     Экземпляр, управляет изображением, которое мы можем отрисовывать
     */
    private BufferedImage dispImage;

    // Конструктор, принимающий ширину и высоту изображения,
    // после чего инициализирующий объект с такими шириной и высотой
    public JImageDisplay(int width, int height) {
        // Создание объекта изображения
        this.dispImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // Обращение к setPreferredSize и установка ширины и высоты
        super.setPreferredSize(new Dimension(width, height));
    }

    //Метод для получения изображения

    public BufferedImage getImage() {
        return dispImage;
    }


    //Переопредлённый метод, позволяющий выводить изображение на экран

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(dispImage, 0, 0, dispImage.getWidth(), dispImage.getHeight(), null);
    }

    // Метод устанавливающий черный цвет во все пиксели

    public void clearImage()
    {
        int[] blankArray = new int[getWidth() * getHeight()];
        dispImage.setRGB(0, 0, getWidth(), getHeight(), blankArray, 0, 1);
    }

    // Метод устанавливает пикселю определенный цвет

    public void drawPixel(int x, int y, int rgbColor)
    {
        dispImage.setRGB(x, y, rgbColor);
    }
}