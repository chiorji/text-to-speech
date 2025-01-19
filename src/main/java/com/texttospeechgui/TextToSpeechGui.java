package com.texttospeechgui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

public class TextToSpeechGui extends Application {
    private static final int APP_WIDTH = 375;
    private static final int APP_HEIGHT = 475;
    private TextArea textArea;
    private ComboBox<String> voices, rates, volumes;

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = createScene();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setTitle("Text-To-Speech App");
        stage.setScene(scene);
        stage.show();
    }

    private Scene createScene() {
        VBox box = new VBox();
        box.getStyleClass().add("body");

        Label textToSpeechLabel = new Label("Text To Speech");
        textToSpeechLabel.setMaxWidth(Double.MAX_VALUE);
        textToSpeechLabel.setAlignment(Pos.CENTER);
        textToSpeechLabel.getStyleClass().add("text-to-speech-label");
//        box.getChildren().add(textToSpeechLabel);

        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.getStyleClass().add("text-area");

        StackPane textAreaPane = new StackPane();
        textAreaPane.setPadding(new Insets(0, 15, 0, 15));
        textAreaPane.getChildren().add(textArea);

//        box.getChildren().add(textAreaPane);

        GridPane settingsPane = createSettingComponents();
//        box.getChildren().add(settingsPane);

        Button speakButton = createImageButton();
        Button loadFromFileButton = createLoadFromFileButton();

        loadFromFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("/Users/mac/downloads"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt", "*.doc", "*.rtf"));
            File file = fileChooser.showOpenDialog(null);
            if(file == null) return;
            StringBuilder fileContent = new StringBuilder();
            try(Scanner sc = new Scanner(Paths.get(file.getPath()))) {
                while(sc.hasNextLine()) fileContent.append(sc.nextLine());
                textArea.textProperty().setValue(String.valueOf(fileContent));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        speakButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String msg = textArea.getText();
                if(msg.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot read content, text field is empty");
                    alert.setTitle("Cannot proceed");
                    alert.showAndWait();
                    return;
                }
                String voice = voices.getValue();
                String rate = rates.getValue();
                String volume = volumes.getValue();

                TextToSpeechGuiController.speak(msg, voice, rate, volume);
            }
        });


        VBox buttonControlPane = new VBox();
        buttonControlPane.setPadding(new Insets(40, 20, 0, 20));
        buttonControlPane.setSpacing(10);

        buttonControlPane.getChildren().add(speakButton);
        buttonControlPane.getChildren().add(loadFromFileButton);

        box.getChildren().addAll(textToSpeechLabel, textAreaPane, settingsPane, buttonControlPane);

        return new Scene(box, APP_WIDTH, APP_HEIGHT);
    }

    private Button createImageButton() {
        Button button = new Button("Speak");
        button.getStyleClass().add("speak-button");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER);

        ImageView imageview = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("speak.png"))));
        imageview.setFitWidth(50);
        imageview.setFitHeight(50);
        button.setGraphic(imageview);
        return button;
    }

    private GridPane createSettingComponents() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10, 0, 0, 0));

        Label voiceLabel = new Label("Voice");
        voiceLabel.getStyleClass().add("setting-label");

        Label rateLabel = new Label("Rate");
        rateLabel.getStyleClass().add("setting-label");

        Label volumeLabel = new Label("Volume");
        volumeLabel.getStyleClass().add("setting-label");

        gridPane.add(voiceLabel, 0, 0);
        gridPane.add(rateLabel, 1, 0);
        gridPane.add(volumeLabel, 2, 0);

        // center labels
        GridPane.setHalignment(voiceLabel, HPos.CENTER);
        GridPane.setHalignment(rateLabel, HPos.CENTER);
        GridPane.setHalignment(voiceLabel, HPos.CENTER);

        voices = new ComboBox<>();
        voices.getItems().addAll(TextToSpeechGuiController.getVoices());
        voices.setValue(voices.getItems().get(0));
        voices.getStyleClass().add("setting-combo-box");

        rates = new ComboBox<>();
        rates.getItems().addAll(TextToSpeechGuiController.getSpeedRates());
        rates.setValue(rates.getItems().get(0));
        rates.getStyleClass().add("setting-combo-box");

        volumes = new ComboBox<>();
        volumes.getItems().addAll(TextToSpeechGuiController.getVolumeLevels());
        volumes.setValue(volumes.getItems().get(0));
        volumes.getStyleClass().add("setting-combo-box");

        gridPane.add(voices, 0, 1);
        gridPane.add(rates, 1, 1);
        gridPane.add(volumes, 2, 1);

        gridPane.setAlignment(Pos.CENTER);

        return gridPane;
    }

    public Button createLoadFromFileButton() {
        Button button = new Button("Load text from file");
        button.setAlignment(Pos.CENTER);
        button.setMaxWidth(Double.MAX_VALUE);
        button.getStyleClass().add("load-file-button");
        return button;
    }

    public static void main(String[] args) {
        launch();
    }
}