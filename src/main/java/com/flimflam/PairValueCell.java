package com.flimflam;

import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.util.Pair;

class PairValueCell extends TableCell<Pair<WebView, Object>, Object> {
    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
                setText(null);
                ImageView imageView = new ImageView((Image) item);
                imageView.setFitWidth(400);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
                setGraphic(imageView);
        } else {
            setText(null);
            setGraphic(null);
        }
    }
}