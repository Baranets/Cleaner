package Controllers;

import Antivirus.Antivirus;
import AutoControl.RobotDefault;
import CCleaner.CCleaner;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.awt.*;
import java.awt.im.InputContext;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Locale;

import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_WINDOWS;

//Контроллер MainWindow.fxml файла
public class StartController {

    @FXML
    private Label pathCCleanerLabel, pathAntivirusLabel;
    @FXML
    private Button buttonCleaner, buttonAntivirus;
    @FXML
    private CheckBox checkBoxCleaner, checkBoxAntivirus, checkBoxURL;
    @FXML
    private TextField urlTextField;

    private final Locale rusLocal = new Locale("ru", "RU");
    private final Locale enLocal = new Locale("en", "US");
    private String folderCCleaner = "C:/Program Files/CCleaner";
    private String fileCCleaner = "CCleaner64.exe";
    private String folderAntivirus = "C:/Program Files/ESET/ESET Security";
    private String fileAntivirus = "egui.exe"; //TODO Реализовать работу через данный файл (антивирус не запускается)
    private String nameAntivirus = "Eset Security";
    private String webSitePath = "https://fornod.net/";

    public void initialize() {
        pathCCleanerLabel.setText(folderCCleaner + "/" + fileCCleaner);
        pathAntivirusLabel.setText(folderAntivirus + "/" + fileAntivirus);
        urlTextField.setText(webSitePath);
    }

    @FXML
    //Метод для выбора пути к файлу CCleaner(64).exe
    public void chooseCCleanerFolder() {
        folderCCleaner = chooseFile(pathCCleanerLabel);
    }

    @FXML
    //Метод для выбора пути к файлу запуска Антивируса
    public void chooseAntivirusFolder() {
        folderAntivirus = chooseFile(pathAntivirusLabel);
    }

    @FXML
    //Метод изменяющий состояние элементов настроек в UI отвечающие за CCleaner
    public void changeStateCleaner() {
        changeStateCheckBox(checkBoxCleaner,pathCCleanerLabel,buttonCleaner);
    }

    @FXML
    //Метод изменяющий состояние элементов настроек в UI отвечающие за Antivirus
    public void changeStateAntivirus() {
        changeStateCheckBox(checkBoxAntivirus,pathAntivirusLabel,buttonAntivirus);
    }

    @FXML
    //Метод изменяющий состояние элементов настроек в UI отвечающие за сайты с ключами к Antivirus
    public void changeStateURL() {
        changeStateCheckBox(checkBoxURL,urlTextField);
    }

    //Реализация метода отвечающего за изменения состояния элементов UI в зависимости от состояния CheckBox
    private void changeStateCheckBox(CheckBox checkBox, Parent ... parent) {
        boolean isEnable = checkBox.isSelected();
        for (Parent p: parent) {
            p.setDisable(!isEnable);
        }
    }

    @FXML
    //Основной метод отвечающий за запуск выполнения очистки и смены ключа Antivirus
    public void start() {
        webSitePath = urlTextField.getText();

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

    //Класс для создания второстепенного потока
    public class Clear extends Task {

        @Override
        protected Object call() throws Exception {

            //Проверка состояния CheckBox отвечающего за CCleaner
            if (checkBoxCleaner.isSelected())
                startClean();
            //Проверка состояния CheckBox отвечающего за Antivirus
            if (checkBoxAntivirus.isSelected())
                startAntivirusUpdate();

            //После выполнения работы отобразить уведомление об этом
            Platform.runLater(StartController.this::showSuccessWindow);
            return null;
        }

        //Запуск чистки системы при помощи CCleaner
        private void startClean() throws AWTException {
            CCleaner cleaner = new CCleaner();
            cleaner.cmdClean(folderCCleaner, fileCCleaner);
//            cleaner.cmdBootRegistry();
        }

        //Обновление ключа активации Antivirus
        private void startAntivirusUpdate() throws AWTException {
            Antivirus antivirus = new Antivirus(webSitePath);
            antivirus.updateKey(nameAntivirus);
        }
    }

    //Проверка языка ввода операционной системы
    private void inputLanguageTest() {
        InputContext inputContext = InputContext.getInstance();
        if (inputContext.getLocale().equals(rusLocal)) {
            try {
                new RobotDefault().pressButtons(VK_WINDOWS, VK_SPACE);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

    //Отображает окно уведомления о завершении выполнения работы
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
