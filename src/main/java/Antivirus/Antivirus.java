package Antivirus;

import AutoControl.RobotDefault;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Класс предназначен для работы с Antivirus'ом, скачиванием и обновлением ключа активации
public class Antivirus extends RobotDefault {

    private String urlWebSite = "";     //Хранит URL на сайт с keys

    public Antivirus() throws AWTException {
        super();
    }

    public Antivirus(String urlWebSite) throws AWTException {
        super();
        this.urlWebSite = urlWebSite;
    }

    public Antivirus(Robot robot, int defDelay) throws AWTException {
        super(robot, defDelay);
    }

    //Метод загружает из интернета html страницу и по строчно ищет строчку с ключами после чего преобразует в key
    private ArrayList<String> downloadKeys() {
        URL url;
        InputStream is;
        BufferedReader br;
        String line;
        ArrayList<String> keys = new ArrayList<>();

        try {
            url = new URL(urlWebSite);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));
            Pattern p = Pattern.compile("(\\W|\\w)*[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}(\\W|\\w)*");
            Matcher m;
            while ((line = br.readLine()) != null) {
                m = p.matcher(line);
                if (m.matches()) {
                    String tempLine = line.trim().replaceAll("</ *[A-Za-z0-9]* *>", "").trim();
                    keys.add(tempLine.substring(tempLine.length() - 24));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keys;
    }

    //Открывает антивирус через Пуск(Windows)
    private void openAntivirus(String name) {
        pressWindows();
        delay(1000);
        printText(name);
        delay(2000);
        pressEnter();
        delay(10000);
    }

    //Открывает окно активации ESET
    private void openActivationAntivirus(String password) {
        for (int i = 0; i < 10; i++) {
            pressTab();
            delay(300);
        }
        delay(1000);
        pressEnter();
        delay(5000);
        for (int i = 0; i < 2; i++) {
            pressTab();
            delay(300);
        }
        pressEnter();
        for (int i = 0; i < 4; i++) {
            pressTab();
            delay(300);
        }
        printText(password);
        pressEnter();
        delay(15000);
        for (int i = 0; i < 3; i++) {
            pressTab();
            delay(300);
        }
        pressEnter();
    }

    //Обновить key Антивируса
    public void updateKey(String name) {
            //Коллекция со скаченными keys
            ArrayList<String> antivirusKeys = downloadKeys();

            openAntivirus(name);

            openActivationAntivirus(antivirusKeys.get(0));

    }
}
