package com.company;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

class Crawler<LINK_REGEX> {
    private HashMap<String, URLDepthPair> links = new HashMap<>();
    // здесь будем хранить просмотренные сайты
    private LinkedList<URLDepthPair> pool = new LinkedList<>();

    private int depth = 0;

    public Crawler(String url, int dep) {
        depth = dep;
        // Добовляем ссылку
        pool.add(new URLDepthPair(url, 0));
    }

    // вывод сайтов
    public void run() {
        while (pool.size() > 0)
        // достаём ПОСЛЕДНЮЮ ссылку из пула
            parseLink(pool.pop());

        for (URLDepthPair link : links.values())
        // выводим ссылки
            System.out.println(link);

        System.out.println();
        // количество ссылок
        System.out.printf("Found %d URLS\n", links.size());
    }

    public static Pattern LINK_REGEX = Pattern.compile(
            "<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1"
    );

    private void parseLink(URLDepthPair link) {
        // правильно ли записана ссылка?
        if (links.containsKey(link.getURL())) {
            URLDepthPair knownLink = links.get(link.getURL());
            knownLink.incrementVisited();
            return;
        }

        links.put(link.getURL(), link);

        if (link.getDepth() >= depth)
            return;
        // проверка на ошибку
        try {
            URL url = new URL(link.getURL());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            Scanner s = new Scanner(con.getInputStream());
            while (s.findWithinHorizon(LINK_REGEX, 0) != null) {
                String newURL = s.match().group(2);
                if (newURL.startsWith("/"))
                    newURL = link.getURL() + newURL;
                else if (!newURL.startsWith("http"))
                    continue;
                URLDepthPair newLink = new URLDepthPair(newURL, link.getDepth() + 1);
                pool.add(newLink);
            }
        } catch (Exception e) {}
    }

    public static void showHelp() {
        System.out.println("usage: java Crawler <URL> <depth>");
        System.exit(1);
    }
    public static void main(String[] args){
        // вводим
        Scanner scan = new Scanner(System.in);
        // для ссылки и глубины
        String[] argg = new String[2];
        System.out.println("Enter URL: ");
        argg[0]=scan.nextLine();
        System.out.println("Enter depth: ");
        argg[1]=scan.nextLine();
        if (argg.length !=2) showHelp();
        int depth = 0;
        String url=argg[0];
        // проверка правильности ввода глубиныё
        try {
            depth = Integer.parseInt(argg[1]);
        } catch (Exception e) {
            showHelp();
        }
        Crawler crawler = new Crawler(url, depth);
        crawler.run();
    }
}