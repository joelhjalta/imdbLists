package com.flimflam;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import javafx.util.Pair;

class PairValueFactory implements Callback<TableColumn.CellDataFeatures<Pair<WebView, Object>, Object>, ObservableValue<Object>> {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ObservableValue<Object> call(TableColumn.CellDataFeatures<Pair<WebView, Object>, Object> data) {
        Object value = data.getValue().getValue();
        return (value instanceof ObservableValue)
                ? (ObservableValue) value
                : new ReadOnlyObjectWrapper<>(value);
    }
}