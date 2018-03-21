/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formatheaders.api;

import com.mks.api.Option;
import com.mks.api.response.APIException;
import com.mks.api.response.Response;
import com.mks.api.response.WorkItemIterator;
import com.ptc.services.common.api.Command;
import com.ptc.services.common.api.IntegrityAPI;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains the api Commands which are called
 * 
 * @author veckardt
 */
public class IntegrityCommands extends IntegrityAPI {

    // Syystemenvironment
    static Map<String, String> env = System.getenv();

    public IntegrityCommands() {
        // This defines the Log File name
        super(env, "IntegrityFormatHeaders");
    }

    //
    // get doc structure 
    // headers only
    // incl. 4 fields
    //
    public WorkItemIterator getDocumentHeaders(String docID) {
        try {
            Command cmd = new Command(Command.IM, "viewsegment");
            cmd.addOption("fields", "Section,Text::rich,Category,Reference Mode");
            cmd.addOption("filterQueryDefinition", "(field[Category] = \"Heading\")");
            cmd.addSelection(docID);
            Response response = this.executeCmd(cmd);
            WorkItemIterator wit = response.getWorkItems();
            wit.next();
            return wit;
        } catch (APIException ex) {
            Logger.getLogger(IntegrityCommands.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // update item's text field
    public void editRichContentField(String selection, String fieldName, String fieldValue) throws APIException {
        Command cmd = new Command(Command.IM, "editissue");
        cmd.addOption(new Option("richContentField", fieldName + "=" + fieldValue));
        cmd.addSelection(selection);
        Response response = this.executeCmd(cmd);
    }

}
