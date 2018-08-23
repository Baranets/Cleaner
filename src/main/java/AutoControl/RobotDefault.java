package AutoControl;

import java.awt.*;
import java.awt.event.KeyEvent;

//Дополнительная надстройка над awt.Robot
public class RobotDefault extends Robot {

    private Robot robot;
    private int defDelay = 10; //Задержка между действиями по умолчанию

    public RobotDefault() throws AWTException {
        super();
        robot = new Robot();
    }

    public RobotDefault(Robot robot, int defDelay) throws AWTException {
        super();
        this.robot = robot;
        this.defDelay = defDelay;
    }

    //Метод реализующий нажатие одной из кнопок
    public void pressButton(int keycode){
        robot.keyPress(keycode);
        robot.delay(defDelay);
        robot.keyRelease(keycode);
    }

    //Метод реализующий сочитание двух клавиш
    public void pressButtons(int keycode1, int keycode2){
        robot.keyPress(keycode1);
        robot.keyPress(keycode2);

        robot.delay(defDelay);

        robot.keyRelease(keycode1);
        robot.keyRelease(keycode2);
    }

    //Метод реализующий нажатие кнопки Enter
    public void pressEnter(){
        pressButton(KeyEvent.VK_ENTER);
    }

    //Метод реализующий нажатие кнопки Windows
    public void pressWindows(){
        pressButton(KeyEvent.VK_WINDOWS);
    }

    //Метод реализующий нажатие кнопки Tab
    public void pressTab(){
        pressButton(KeyEvent.VK_TAB);
    }

    //Метод реализующий имитацию набора текста с клавиатуры
    public void printText(String text){
        try {
            char[] chars = text.toCharArray();
            for (char chr : chars) {
                if (chr == ':')
                    pressButtons(KeyEvent.VK_SHIFT,KeyEvent.VK_SEMICOLON);
                else
                    pressButton(KeyEvent.getExtendedKeyCodeForChar(chr));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //getter
    public Robot getRobot() {
        return robot;
    }

    //setter
    public void setRobot(Robot robot) {
        this.robot = robot;
    }
}
