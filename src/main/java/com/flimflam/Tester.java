package com.flimflam;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class Tester {

    static final String NUM_REGEX=
            "-?((([0-9]{1,3})(,[0-9]{3})*)|[0-9]*)(\\.[0-9]+)?([Ee][0-9]*)?";
        public static boolean isNum(String s) {
                return s!=null && s.length()>0 && s.matches(NUM_REGEX);  
        }
        public static void main(String[]args) {
            String[] values={
                    "",
                    "0",
                    "0.1",
                    ".1",
                    "-.5E5",
                    "-12,524.5E5",
                    "-452,456,456,466.5E5",
                    "-452,456,456,466E5",
                    "22,22,2.14123415e1",
                    "const",
                    "\"position\""
            };
            for (String value : values) {
                System.out.println(value+" is a number: "
                +isNum(value));
            }
        }
}