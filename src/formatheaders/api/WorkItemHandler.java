/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formatheaders.api;

import com.mks.api.response.WorkItem;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author veckardt
 */
public class WorkItemHandler {

    public static int offset = 0;

    // return Text content
    public static String getText(WorkItem wi) {
        return wi.getField("Text").getString();
    }

    // return Categry content
    public static String getCategory(WorkItem wi) {
        return wi.getField("Category").getString();
    }

    // return Section content
    public static String getSection(WorkItem wi) {
        return wi.getField("Section").getString();
    }

    // return Ref Mode content
    public static String getReferenceMode(WorkItem wi) {
        return wi.getField("Reference Mode").getString();
    }

    // return Section Level as int, + offset if defined
    public static int getSectionLevel(WorkItem wi) {
        String section = wi.getField("Section").getString() + "";
        return section.replaceAll("[^.]", "").length() + 1 + offset;
    }

    // get correct header text, incl. html tags
    public static String getTextWithHtmlLevel(WorkItem wi, Boolean formatWithBoldOnly) {

        String tag = "h" + getSectionLevel(wi);
        if (formatWithBoldOnly) {
            return "<div><p><b>" + removeTags(wi.getField("Text").getString()).trim() + "</b></p></div>";
        } else {
            return "<" + tag + ">" + removeTags(wi.getField("Text").getString()).trim() + "</" + tag + ">";
        }
    }

    private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

    // generic routine to remove ANY tag
    // charrage return will be conserved by adding a blank
    public static String removeTags(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        // this char may also be part of the text
        string = string.replace("&#160;", " ");
        // to conserve the charrage return
        string = string.replace("</", " </");
        String trimmedString = string.trim();

        Matcher m = REMOVE_TAGS.matcher(trimmedString);
        return m.replaceAll("");
    }
}
