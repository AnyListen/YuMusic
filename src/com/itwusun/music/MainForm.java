package com.itwusun.music;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import netscape.javascript.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MainForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        init(primaryStage);
        primaryStage.show();
    }

    public void closeForm(){
        Platform.exit();
    }

    private WebView webView = new WebView();
    private void init(Stage primaryStage) {
        String defaultURL = System.getProperty("user.dir");
        System.out.println(defaultURL);
        Path path = Paths.get(defaultURL, "html", "index.html");
        defaultURL = "file:///" + path.toString();
        webView.setMinWidth(1120);
        webView.setMinHeight(670);
        webView.getEngine().load(defaultURL);

        webView.getEngine().getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                JSObject win = (JSObject) webView.getEngine().executeScript("window");
                win.setMember("iMusic", new MainForm());
            }
        });

        JSObject window = (JSObject) webView.getEngine().executeScript("window");
        window.setMember("iMusic", new MainForm());

        primaryStage.setScene(new Scene(webView));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
