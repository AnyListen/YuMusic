package com.itwusun.music;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jodd.io.FileUtil;
import netscape.javascript.JSObject;

import java.lang.reflect.Field;
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
        private boolean isMax = false;
        private boolean isInit = false;
        private double oriX = 0;
        private double oriY = 0;
        private double oriWidth = 0;
        private double oriHeight = 0;

        JsBridge(Stage st) {
            this.stage = st;
        }

        public void closeForm() {
            this.stage.close();
        }

        public void maxForm() {
            if (this.isMax) {
                this.stage.setX(oriX);
                this.stage.setY(oriY);
                this.stage.setWidth(oriWidth);
                this.stage.setHeight(oriHeight);
                this.isMax = false;
            } else {
                this.oriX = this.stage.getX();
                this.oriY = this.stage.getY();
                this.oriWidth = this.stage.getWidth();
                this.oriHeight = this.stage.getHeight();
                Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
                this.stage.setX(primaryScreenBounds.getMinX());
                this.stage.setY(primaryScreenBounds.getMinY());
                this.stage.setWidth(primaryScreenBounds.getWidth());
                this.stage.setHeight(primaryScreenBounds.getHeight());
                this.isMax = true;
            }
            win.call("setState", this.isMax ? "max" : "nor");
        }

        public void minForm() {
            this.stage.setIconified(true);
        }

        public void dragEnable() {
            this.mouseDragFlag = true;
        }

        public void dragDisable() {
            this.mouseDragFlag = false;
        }

        public void log(String text){
            System.out.println(text);
        }

        public String getLocalText(String p){
            String defaultURL = System.getProperty("user.dir");
            Path path = Paths.get(defaultURL, "html", p.substring(2));
            System.out.println(path.toString());
            try{
                return FileUtil.readString(path.toFile());
            }
            catch (Exception ex){
                System.out.println(path.toString());
                return "";
            }
        }
    }

    private WebView webView = new WebView();
    private double xOffset = 0;
    private double yOffset = 0;
    private JsBridge jsBridge;
    private JSObject win = null;

    private void init(Stage primaryStage) {
        String defaultURL = System.getProperty("user.dir");
        Path path = Paths.get(defaultURL, "html", "index.html");
        defaultURL = "file:///" + path.toString();
        System.out.println(defaultURL);
        webView.setMinWidth(1120);
        webView.setMinHeight(670);
        webView.setStyle("-fx-background:rgba(0,0,0,0);");
        final WebEngine webEngine = webView.getEngine();
        System.out.println(webEngine.getUserAgent());
        webEngine.load(defaultURL);
        Scene scene = new Scene(webView);
        scene.setFill(null);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("鱼声音乐");
        primaryStage.getIcons().add(new Image(MainForm.class.getClassLoader().getResourceAsStream("16.png")));
        primaryStage.getIcons().add(new Image(MainForm.class.getClassLoader().getResourceAsStream("32.png")));
        primaryStage.getIcons().add(new Image(MainForm.class.getClassLoader().getResourceAsStream("64.png")));
        primaryStage.getIcons().add(new Image(MainForm.class.getClassLoader().getResourceAsStream("100.png")));
        jsBridge = new JsBridge(primaryStage);

        webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (!jsBridge.isInit){
                jsBridge.isInit = true;
                win = (JSObject) webEngine.executeScript("window");
                win.setMember("iMusic", jsBridge);
                webEngine.executeScript("console.log = function(message)\n" +
                        "{\n" +
                        "    iMusic.log(message);\n" +
                        "};");
            }
        });

        webEngine.documentProperty().addListener((ov, oldState, newState) -> {
            try {
                Field f = webEngine.getClass().getDeclaredField("page");
                f.setAccessible(true);
                com.sun.webkit.WebPage page = (com.sun.webkit.WebPage) f.get(webEngine);
                page.setBackgroundColor((new java.awt.Color(0, 0, 0, 0)).getRGB());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        webView.setOnMousePressed((MouseEvent event) -> {
            event.consume();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        webView.setOnMouseDragged((MouseEvent event) -> {
            //if (jsBridge.mouseDragFlag && !jsBridge.isMax){
            if (jsBridge.mouseDragFlag) {
                event.consume();
                primaryStage.setX(event.getScreenX() - xOffset);
                if (event.getScreenY() - yOffset < 0) {
                    primaryStage.setY(0);
                } else {
                    primaryStage.setY(event.getScreenY() - yOffset);
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
