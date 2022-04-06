package com.company;

//Класс для хранения ссылки и ее глубины
public class URLDepthPair {
    private String url;
    private int depth;
    private int visited;
    public URLDepthPair(String URL, int dep) {
        url = URL;
        depth=dep;
        visited=1;
    }
    // получаем полный путь для сайта
    public String getURL() {
        return url;
    }
    // получаем глубину сайта
    public int getDepth() {
        return depth;
    }
    public void incrementVisited() {
        visited++;
    }
    // возврат адреса сайта + глубина
    public String toString() {
        return "<URL href=\"" + url + "\" visited=\"" + visited + "\" depth=\"" + depth + "\" \\>";
    }
}