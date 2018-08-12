package Controllers;

import Antivirus.Antivirus;
import AutoControl.RobotDefault;
import CCleaner.CCleaner;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.awt.*;
import java.awt.im.InputContext;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Locale;

import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_WINDOWS;


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
    private String nameAntivirus = "Eset Security";
    private String webSitePath = "http://obnovlenie-nod32.work/kody-aktivacii-ess-9/";


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
            Platform.runLater(()->showSuccessWindow());
            return null;
        }

        private void startClean() throws AWTException {
            CCleaner cleaner = new CCleaner();
            cleaner.cmdClean(folderCCleaner);
//            cleaner.cmdBootRegistry();
        }

        private void startAntivirusUpdate() throws AWTException {
            Antivirus antivirus = new Antivirus();
            antivirus.updateKey(nameAntivirus);
        }


    }

    private void inputLanguageTest() {
        System.out.println(InputContext.getInstance().getLocale());
        InputContext inputContext = InputContext.getInstance();
        if (inputContext.getLocale().equals(rusLocal)) {
            try {
                new RobotDefault().pressButtons(VK_WINDOWS, VK_SPACE);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

    private void showSuccessWindow() {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Выполнение");
            alert.setHeaderText(null);
            alert.setContentText("      Success " +
                    "\n      Выполнение чистки завершено");

            alert.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
