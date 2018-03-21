/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formatheaders;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import jfx.messagebox.MessageBox;

/**
 *
 * @author veckardt
 */
public class FormatHeadersEventHandler implements EventHandler<WorkerStateEvent> {

    final Label statusLabel;

    public FormatHeadersEventHandler(Task task, Label statusLabel, ProgressBar progressBar) {
        this.statusLabel = statusLabel;
        this.statusLabel.textProperty().bind(task.messageProperty());
        progressBar.progressProperty().unbind();
        progressBar.setProgress(0);
        progressBar.progressProperty().bind(task.progressProperty());
    }

    @Override
    public void handle(WorkerStateEvent t) {
        if (statusLabel.getText().startsWith("ERROR")) {
            MessageBox.show(FormatHeaders.stage,
                    statusLabel.getText(),
                    "Formatting incomplete",
                    MessageBox.ICON_ERROR | MessageBox.OK);
        } else {
            MessageBox.show(FormatHeaders.stage,
                    statusLabel.getText(),
                    "Formatting succeeded",
                    MessageBox.ICON_INFORMATION | MessageBox.OK);
        }
//                    eriTask.stateProperty().addListener(new ChangeListener<Worker.State>() {
//                        @Override
//                        public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState, Worker.State newState) {
//
//                            // if (newState == Worker.State.SUCCEEDED) {
//                            // System.out.println("This is ok, this thread " + Thread.currentThread() + " is the JavaFX Application thread.");
//                            // runButton.setText("Voila!");
//                            log("Done" + newState + result);
//                            // }
//                        }
//                    });        
    }

}
