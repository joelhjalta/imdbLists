package com.flimflam;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import javafx.util.Pair;

class PairKeyFactory implements Callback<TableColumn.CellDataFeatures<Pair<WebView, Object>, WebView>, ObservableValue<WebView>> {
    @Override
    public ObservableValue<WebView> call(TableColumn.CellDataFeatures<Pair<WebView, Object>, WebView> data) {
        return new ReadOnlyObjectWrapper<>(data.getValue().getKey());
    }
}