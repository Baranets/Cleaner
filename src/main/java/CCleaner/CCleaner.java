package CCleaner;

import AutoControl.RobotDefault;

import java.awt.*;

import static java.awt.event.KeyEvent.*;

public class CCleaner extends RobotDefault {


    public CCleaner() throws AWTException {
        super();
    }

    public CCleaner(Robot robot, int defDelay) throws AWTException {
        super(robot, defDelay);
    }

    public void cmdClean(String folderCCleaner) {
        pressButton(VK_WINDOWS);
        delay(1000);

        printText("cmd");
        delay(1000);

        pressEnter();
        delay(1000);

        printText("cd " + folderCCleaner);
        pressEnter();
        delay(250);

        printText("CCleaner.exe /auto");
        pressEnter();
        delay(10000);
        pressButtons(VK_ALT,VK_F4);
    }

    //TODO COMMING SOON
    public void cmdBootRegistry() {

            pressButton(VK_WINDOWS);
            delay(1000);
            printText("CCleaner");
            pressEnter();
            delay(5000);
            pressButton(VK_PAGE_DOWN);
            pressButton(VK_TAB);
            pressEnter();


    }
}
