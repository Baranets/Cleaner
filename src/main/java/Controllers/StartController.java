package Controllers;

import AutoControl.RobotDefault;
import CCleaner.CCleaner;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import javafx.stage.FileChooser;


import java.awt.*;
import java.awt.im.InputContext;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.awt.event.KeyEvent.*;


public class StartController {

    @FXML
    private Label pathCCleanerLabel, pathAntivirusLabel;
    @FXML
    private Button buttonCleaner, buttonAntivirus;
    @FXML
    private CheckBox checkBoxCleaner, checkBoxAntivirus;


    private final Locale rusLocal = new Locale("ru", "RU");
    private final Locale enLocal = new Locale("en", "US");
    private String folderCCleaner = "C:/Program Files/CCleaner";
    private String fileCCleaner = "CCleaner.exe";
    private String folderAntivirus = "C:/Program Files/ESET/ESET Security";
    private String fileAntivirus = "egui.exe";
    private String webSitePath = "http://obnovlenie-nod32.work/kody-aktivacii-ess-9/";
    private short countOfKeys = 5;
    private String[] antivirusKeys = new String[countOfKeys];


    public void initialize() {
        pathCCleanerLabel.setText(folderCCleaner + "/" + fileCCleaner);
        pathAntivirusLabel.setText(folderAntivirus + "/" + fileAntivirus);
    }

    public void start() throws AWTException {
        inputLanguageTest();

        new Thread(new Clear()).start();
    }

    private String chooseFile(Label label) {
        String folder = "";
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilterEXE = new FileChooser.ExtensionFilter("EXE files(*.exe)", "*.exe");
        fileChooser.getExtensionFilters().addAll(extensionFilterEXE);
        try {
            File chosenFile = fileChooser.showOpenDialog(null);
            if (chosenFile != null) {
                folder = chosenFile.toURI().toURL().toString();
                folder = folder.replace("file:/", "")
                        .replace("%20", " ");

                label.setText(folder);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return folder;
    }

    @FXML
    public void chooseCCleanerFolder() {
        folderCCleaner = chooseFile(pathCCleanerLabel);
    }

    @FXML
    public void chooseAntivirusFolder() {
        folderAntivirus = chooseFile(pathAntivirusLabel);
    }

    public void getKey() {
        URL url;
        InputStream is = null;
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
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }
    }

    public void changeStateCleaner() {
        boolean isEnable = !checkBoxCleaner.isSelected();
        pathCCleanerLabel.setDisable(isEnable);
        buttonCleaner.setDisable(isEnable);
    }

    public void changeStateAntivirus() {
        boolean isEnable = !checkBoxAntivirus.isSelected();
        pathAntivirusLabel.setDisable(isEnable);
        buttonAntivirus.setDisable(isEnable);
    }

    public class Clear extends Task {

        @Override
        protected Object call() throws Exception {

            if (checkBoxCleaner.isSelected())
                startClean();
            if (checkBoxAntivirus.isSelected())
                startAntivirusUpdate();

            return null;
        }

        private void startClean() throws AWTException {
            CCleaner cleaner = new CCleaner();
            cleaner.cmdClean(folderCCleaner);
//            cleaner.cmdBootRegistry();
        }

        private void startAntivirusUpdate(){

        }





    }

    private void inputLanguageTest() {
        System.out.println(InputContext.getInstance().getLocale());
        InputContext inputContext = InputContext.getInstance();
        if (inputContext.getLocale().equals(rusLocal)) {
            try {
                new RobotDefault().pressButtons(VK_WINDOWS,VK_SPACE);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

}
