package Antivirus;

import AutoControl.RobotDefault;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Antivirus extends RobotDefault {

    private short countOfKeys = 5;
    private String[] antivirusKeys = new String[countOfKeys];

    public Antivirus() throws AWTException {
        super();
    }

    public Antivirus(Robot robot, int defDelay) throws AWTException {
        super(robot, defDelay);
    }

    private String[] downloadKeys() {
        URL url;
        InputStream is;
        BufferedReader br;
        String line;

        try {
            url = new URL("http://obnovlenie-nod32.work/kody-aktivacii-ess-9/");
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));
            Pattern p = Pattern.compile("[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}(\\W|\\w)*");
            Matcher m;
            short count = 0;
            while ((line = br.readLine()) != null) {
                m = p.matcher(line);
                if (m.matches()) {
                    antivirusKeys[count] = line.trim().replaceAll("<(\\W|\\w)*>", "");
                    System.out.println(antivirusKeys[count]);
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return antivirusKeys;
    }

    private void openAntivirus(String name){
        pressWindows();
        delay(1000);
        printText(name);
        delay(2000);
        pressEnter();
        delay(10000);
    }

    private void openActivationAntivirus(String password){
        for (int i = 0; i < 10
                ; i++) {
            pressTab();
            delay(1000);
        }
        delay(1000);
        pressEnter();
        delay(5000);
        for (int i = 0; i < 2; i++) {
            pressTab();
            delay(1000);
        }
        pressEnter();
        for (int i = 0; i < 4; i++) {
            pressTab();
            delay(1000);
        }
        printText(password);
        pressEnter();
        delay(15000);
        for (int i = 0; i < 3; i++) {
            pressTab();
            delay(1000);
        }
        pressEnter();
    }

    public void updateKey(String name){
        downloadKeys();

        openAntivirus(name);

        openActivationAntivirus(antivirusKeys[0]);
    }


}
