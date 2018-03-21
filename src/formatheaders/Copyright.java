/*
 * Copyright:      Copyright 2015 (c) Parametric Technology GmbH
 * Product:        PTC Integrity Lifecycle Manager
 * Author:         V. Eckardt, Senior Consultant ALM
 * Purpose:        Custom Developed Code
 * **************  File Version Details  **************
 * Revision:       $Revision: 1.4 $
 * Last changed:   $Date: 2015/11/28 23:34:14CET $
 */

package formatheaders;

/**
 *
 * @author veckardt
 */
public class Copyright {

    public static final String COPYRIGHT = "(c)";
    public static String copyright = "Copyright " + COPYRIGHT + " 2015 PTC Inc.";
    public static String copyrightHtml = "Copyright &copy; 2015 PTC Inc.";
    public static String programName = "Integrity Doc Formatter";
    public static String programVersion = "10.8.6";
    public static String author = "Author: Volker Eckardt";
    public static String email = "email: veckardt@ptc.com";

    public static void write() {
        System.out.println("* " + programName + " - Version " + programVersion);
        System.out.println("* A utility to load and synchronize Test Step ionformation with Integrity");
        System.out.println("* Developed for and tested with Integrity 10.6");
        System.out.println("*");
        System.out.println("* " + copyright);
        System.out.println("* " + author + ", " + email + "\n");
    }

    // public static void usage() {
    //     System.out.println("*");
    //     System.out.println("* Usage: ");
    //     System.out.println("*   <path-to-javaw>\\javaw -jar <path-to-jar>\\IntegrityCustomGateway.jar");
    //     System.out.println("* Example:");
    //     System.out.println("*   C:\\Program Files\\Java\\jdk1.7.0_21\\bin\\javaw -jar C:\\IntegrityClient10\\lib\\IntegrityCustomGateway.jar");
    //     System.out.println("* Additional Notes:");
    //     System.out.println("*   - a configuration file 'CustomGateway.properties' can be used to specify default values");
    //     System.out.println("*   - a log file is created in directory '%temp%', the filename is 'IntegrityCustomGateway_YYYY-MM-DD.log'");
    //     System.out.println("*");
    // }
}
