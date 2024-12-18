module com.texttospeechgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.texttospeechgui to javafx.fxml;
    exports com.texttospeechgui;
}