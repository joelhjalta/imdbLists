package com.flimflam;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Tester extends Application {
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	public void start(final Stage stage) throws Exception {
		ImageView imageView = new ImageView(new Image("http://www.kalahandi.info/wp-content/uploads/2016/05/sorry-image-not-available.png"));
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setOnMouseClicked(event -> {
        	Application a = new Application() {

                @Override
                public void start(Stage stage)
                {
                }
            };
        	a.getHostServices().showDocument("http://www.google.com");
        });
        VBox vb = new VBox(imageView);
//        setGraphic(imageView);
        Scene scene = new Scene(vb, 1200, 900);
		scene.getStylesheets().add("com/flimflam/application.css");
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
	}
}