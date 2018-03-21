/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formatheaders;

import formatheaders.commands.FXLogger;
import com.mks.api.response.APIException;
import com.mks.api.response.WorkItem;
import static formatheaders.commands.FXLogger.log;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import formatheaders.api.IntegrityCommands;
import formatheaders.commands.FormatHeaders;

/**
 *
 * @author veckardt
 */
public class FormatHeadersController implements Initializable {

    @FXML
    private Label lCurrentDoc;
    @FXML
    private TextArea logArea;
    @FXML
    private CheckBox cbFormatWithBold;
    @FXML
    private Button bOpenLogFile;
    @FXML
    private ProgressBar progressBar;
    final Label statusLabel = new Label("Status");

    // api definition
    private static final IntegrityCommands api = new IntegrityCommands();
    // value for testing purposes only
    private static String startItemId = "19708";
    public static final String mksTag = "<!-- MKS HTML -->";
    // to determine if the comamnd shall be run as a task or not
    // primarily better for debugging when not a task
    private final Boolean asTask = true;
    // the word to be shown in front of the logging if in preview mode
    public static String previewMode = "Preview";

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.exit(1);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FXLogger.init(api, logArea);
        // Should be run always from a document
        if (System.getenv("MKSSI_DOCUMENT") == null) {
            startItemId = (System.getenv("MKSSI_ISSUE0") == null ? startItemId : System.getenv("MKSSI_ISSUE0"));
        }
        startItemId = (System.getenv("MKSSI_DOCUMENT") == null ? startItemId : System.getenv("MKSSI_DOCUMENT"));
        // get item details
        WorkItem startItem = api.getWorkItem(startItemId, "Summary,Type");

        // set the item details
        lCurrentDoc.setText(startItem.getField("Summary").getValueAsString() + " (" + startItem.getId() + ")");

    }

    @FXML
    private void bFormatPreview(ActionEvent event) throws APIException, IOException {
        log("Previewing Formatting ...");
        Platform.setImplicitExit(false);
        // Hand over the details to the task definition
        FormatHeaders task = new FormatHeaders(api, startItemId, previewMode, cbFormatWithBold.isSelected());

        // shall be called in the background as task?
        if (asTask) {
            // needed to read ´thereturn message
            task.setOnSucceeded(new FormatHeadersEventHandler(task, statusLabel, progressBar));
            Thread myTaskThread = new Thread(task);
            myTaskThread.start();
        } else {
            task.generate();
            log("Doc Formatting finished.");
        }
    }

    @FXML
    private void bFormat(ActionEvent event) throws APIException, IOException {
        log("Performing Doc Formatting ...");
        
        // log(event.getSource().toString());
        Platform.setImplicitExit(false);
        // Hand over the details to the task definition
        FormatHeaders task = new FormatHeaders(api, startItemId, "", cbFormatWithBold.isSelected());

        // shall be called in the background as task?
        if (asTask) {
            // needed to read ´thereturn message
            task.setOnSucceeded(new FormatHeadersEventHandler(task, statusLabel, progressBar));
            Thread myTaskThread = new Thread(task);
            myTaskThread.start();
        } else {
            task.generate();
            log("Doc Formatting finished.");
        }
    }
}
