module com.texttospeechgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires freetts;


    opens com.texttospeechgui to javafx.fxml;
    exports com.texttospeechgui;
}