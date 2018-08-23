package CCleaner;

import AutoControl.RobotDefault;

import java.awt.*;

import static java.awt.event.KeyEvent.*;

//Класс предназначенный для взаимодействия со сторонним ПО
public class CCleaner extends RobotDefault {

    public CCleaner() throws AWTException {
        super();
    }

    public CCleaner(Robot robot, int defDelay) throws AWTException {
        super(robot, defDelay);
    }

    //Метод выполняет чистку временных файлов при помощи строннего ПО (CCleaner)
    public void cmdClean(String folderCCleaner, String exeCCleaner) {
        //Открывает командную строку (cmd) через пуск
        pressWindows();
        delay(1000);

        printText("cmd");
        delay(1000);

        pressEnter();
        delay(1000);

        //Переход в дерикторию с программой CCleaner
        printText("cd " + folderCCleaner);
        pressEnter();
        delay(250);

        //Вызов CCleaner(64).exe с ключом auto для ислопнения чистки PC
        printText(exeCCleaner + " /auto");
        pressEnter();
        delay(10000);

        //Закрытие командной строки (cmd)
        pressButtons(VK_ALT, VK_F4);
    }

    //TODO COMMING SOON
    public void cmdBootRegistry() {

        pressButton(VK_WINDOWS);
        delay(1000);
        printText("CCleaner");
        pressEnter();
        delay(5000);
        for (int i = 0; i < 100; i++) {
            delay(1000);
            System.out.println(i);
            pressButton(VK_TAB);
        }
    }

}
