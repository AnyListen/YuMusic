package com.itwusun.music;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import netscape.javascript.*;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        init(primaryStage);
        primaryStage.show();
    }

    public class JsBridge {
        private Stage stage;
        private boolean mouseDragFlag = false;

        JsBridge(Stage st){
            this.stage = st;
        }

        public void closeForm(){
            this.stage.close();
        }

        public void maxForm(){
            this.stage.setMaximized(!this.stage.isMaximized());
        }

        public void minForm(){
            this.stage.setIconified(true);
        }

        public void dragEnable(){
            this.mouseDragFlag = true;
        }

        public void dragDisable(){
            this.mouseDragFlag = false;
        }
    }

    private WebView webView = new WebView();
    private double xOffset = 0;
    private double yOffset = 0;
    private JsBridge jsBridge;

    private void init(Stage primaryStage) {
        String defaultURL = System.getProperty("user.dir");
        System.out.println(defaultURL);
        Path path = Paths.get(defaultURL, "html", "index.html");
        defaultURL = "file:///" + path.toString();
        webView.setMinWidth(1120);
        webView.setMinHeight(670);
        final WebEngine webEngine = webView.getEngine();
        webEngine.load(defaultURL);
        primaryStage.setScene(new Scene(webView));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("鱼声音乐");
        primaryStage.getIcons().add(new Image(MainForm.class.getClassLoader().getResourceAsStream("16.png")));
        primaryStage.getIcons().add(new Image(MainForm.class.getClassLoader().getResourceAsStream("32.png")));
        primaryStage.getIcons().add(new Image(MainForm.class.getClassLoader().getResourceAsStream("64.png")));
        primaryStage.getIcons().add(new Image(MainForm.class.getClassLoader().getResourceAsStream("100.png")));
        jsBridge = new JsBridge(primaryStage);
        webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                JSObject win = (JSObject) webEngine.executeScript("window");
                win.setMember("iMusic", jsBridge);
            }
        });

        webView.setOnMousePressed((MouseEvent event) -> {
            event.consume();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        webView.setOnMouseDragged((MouseEvent event) -> {
            if (jsBridge.mouseDragFlag){
                event.consume();
                primaryStage.setX(event.getScreenX() - xOffset);
                if (event.getScreenY() - yOffset < 0) {
                    primaryStage.setY(0);
                }
                else {
                    primaryStage.setY(event.getScreenY() - yOffset);
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
