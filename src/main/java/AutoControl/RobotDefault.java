package AutoControl;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RobotDefault extends Robot {

    private Robot robot;
    private int defDelay = 10;

    public RobotDefault() throws AWTException {
        super();
        robot = new Robot();
    }



    public RobotDefault(Robot robot, int defDelay) throws AWTException {
        super();
        this.robot = robot;
        this.defDelay = defDelay;
    }


    public void pressButton(int keycode){
        robot.keyPress(keycode);
        robot.delay(defDelay);
        robot.keyRelease(keycode);
    }

    public void pressButtons(int keycode1, int keycode2){
        robot.keyPress(keycode1);
        robot.keyPress(keycode2);

        robot.delay(defDelay);

        robot.keyRelease(keycode1);
        robot.keyRelease(keycode2);
    }

    public void pressEnter(){
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(defDelay);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public void pressWindows(){
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.delay(defDelay);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
    }

    public void pressTab(){
        robot.keyPress(KeyEvent.VK_TAB);
        robot.delay(defDelay);
        robot.keyRelease(KeyEvent.VK_TAB);
    }

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

    public Robot getRobot() {
        return robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }
}
