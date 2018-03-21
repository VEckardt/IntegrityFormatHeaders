/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package formatheaders;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author veckardt
 */
public class FormatHeaders extends Application {
    
    
    public static Stage stage;
    public static String title = Copyright.programName + " - v" + Copyright.programVersion;
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FormatHeaders.fxml"));

        Scene scene = new Scene(root);
        FormatHeaders.stage = stage;
        Image applicationIcon = new Image(getClass().getResourceAsStream("resources/tools2.png"));
        stage.getIcons().add(applicationIcon);
        
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
