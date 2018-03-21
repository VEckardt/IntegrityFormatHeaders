/*
 * Copyright:      Copyright 2015 (c) Parametric Technology GmbH
 * Product:        PTC Integrity Lifecycle Manager
 * Author:         Volker Eckardt, Senior Consultant ALM
 * Purpose:        Custom Developed Code
 * **************  File Version Details  **************
 * Revision:       $Revision$
 * Last changed:   $Date$
 */
package formatheaders.commands;

import formatheaders.api.IntegrityCommands;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 *
 * @author veckardt
 */
public class FXLogger {

    private static TextArea logArea;
    private static IntegrityCommands api;

    /**
     *
     * @param api
     * @param logArea
     */
    public static void init(IntegrityCommands api, TextArea logArea) {
        FXLogger.api = api;
        FXLogger.logArea = logArea;
    }

    /**
     * Writes a message 
     * @param text
     */
    public static void log(String text) {
        writeLogArea("", text);
        api.log(text, 1);
    }

    /**
     * Writes a debug message 
     * @param text
     */
    public static void debug(String text) {
        api.log(text, 1);
    }

    /**
     * Writes a message prefixed with ERROR
     * @param text
     */
    public static void error(String text) {
        writeLogArea("ERROR", text);
        api.log("ERROR: " + text, 1);
    }

    /**
     * Writes a message prefixed with INFO
     * @param text
     */
    public static void info(String text) {
        writeLogArea("INFO", text);
        api.log("INFO: " + text, 1);
    }

    private static void writeLogArea(String tag, String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                logArea.appendText("\n" + (tag.isEmpty() ? "" : tag + ": ") + text);
            }
        });
    }

}
