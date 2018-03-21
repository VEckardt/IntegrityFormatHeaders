/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formatheaders.commands;

import com.mks.api.response.APIException;
import com.mks.api.response.WorkItem;
import com.mks.api.response.WorkItemIterator;
import static formatheaders.commands.FXLogger.info;
import static formatheaders.commands.FXLogger.log;
import formatheaders.FormatHeadersController;
import static formatheaders.FormatHeadersController.previewMode;
import java.io.IOException;
import javafx.application.Platform;
import javafx.concurrent.Task;

import formatheaders.api.IntegrityCommands;
import static formatheaders.api.WorkItemHandler.getCategory;
import static formatheaders.api.WorkItemHandler.getReferenceMode;
import static formatheaders.api.WorkItemHandler.getSection;
import static formatheaders.api.WorkItemHandler.getText;
import static formatheaders.api.WorkItemHandler.getTextWithHtmlLevel;

/**
 *
 * @author veckardt
 */
public class FormatHeaders extends Task<Void> {

    // Constructor variables
    private final IntegrityCommands api;
    private final String startItemId;
    // mode can be Preview or empty
    private final String mode;
    // was and is rather a try out, checkbox is disabled
    private final Boolean formatWithBoldOnly;

    public FormatHeaders(IntegrityCommands api, String startItemId, String mode, Boolean formatWithBold) {
        // variable initialization
        this.api = api;
        this.startItemId = startItemId;
        this.mode = mode;
        this.formatWithBoldOnly = formatWithBold;
    }

    public void generate() throws APIException, IOException {
        // set to 5% done
        Platform.setImplicitExit(false);
        updateProgress(1, 20);
        // run action
        if (generateAction()) {
            // send to postive
            updateProgress(20, 20);
        } else {
            // indicate negative result
            updateProgress(0, 20);
        }
    }

    public Boolean generateAction() throws IOException {

        int lineCounter = 0;
        int cntShareMode = 0;

        // define preview prefix
        String modePrefix = (mode.isEmpty() ? "" : "(" + mode + ") ");

        // let the bar jump
        updateProgress(-1, 0);

        // get all document Headers only
        WorkItemIterator wit = api.getDocumentHeaders(startItemId);

        // for all document items
        while (wit.hasNext()) {
            try {
                WorkItem wi = wit.next();
                String id = wi.getId();
                api.log("id: " + id, 1);
                String oldText = getText(wi);
                api.log("oldText: " + oldText, 2);
                // api.log("Text2  : " + getText2(wi), 2);
                // get the document node text to be set into the Header field instead
                String newText = getTextWithHtmlLevel(wi, formatWithBoldOnly);
                api.log("newText: " + newText, 2);

                // any difference?
                if (!oldText.contentEquals(FormatHeadersController.mksTag + newText)) {

                    // line counter increase
                    lineCounter++;

                    // if Share - dont do anything
                    if (getReferenceMode(wi).contentEquals("Share")) {
                        // we can not update in this mode
                        cntShareMode++;
                        log("Skipping section " + getSection(wi) + " " + getCategory(wi) + " (" + id + "): is in Share state");
                    } else {
                        // if preview, then just a log entry
                        if (mode.contentEquals(previewMode)) {
                            log(modePrefix + "Would update section " + getSection(wi) + " " + getCategory(wi) + " (" + id + ") with " + newText);
                        } else {
                            // this is the update itself
                            api.editRichContentField(id, "Text", newText);
                            log("Updated section " + getSection(wi) + " " + getCategory(wi) + " (" + id + ") with " + newText);
                        }
                    }
                }

            } catch (APIException ex) {
                // Logger.getLogger(UITools2Controller.class.getName()).log(Level.SEVERE, null, ex);
                log(ex.getMessage());
                updateMessage("ERROR: " + ex.getMessage());
                return false;
            }
        }

        updateProgress(20, 20);

        // anything done?
        if (lineCounter > 0) {

            // construct return message
            String message = modePrefix + "Formatting of " + (lineCounter - cntShareMode) + " items finished successfully.";

            if (lineCounter - cntShareMode == 0) {
                message = modePrefix + "Nothing to format";
            }

            // any Shared Items which we ignored?
            if (cntShareMode > 0) {
                message += "\n" + modePrefix + "Identified " + cntShareMode + " items in 'Share' state, not updating.";
            }

            // set retrun message
            log(message);
            updateMessage(message);
            // give positive return
            return true;
        } else {
            // not really much done
            String message = "All header formatting is correct, and aligned with the section levels.";
            info(message);
            updateMessage(message);
            return true;
        }
        // return negative
        // return false;
    }

    // when it is a java tread
    @Override
    protected Void call() throws Exception {
        generate();
        return null;
    }

}
