import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Appends timestamped action log entries to log.txt across sessions.
 *
 * MEL-CODE CHANGE: Renamed file from "Logger1" to "Logger1.java" - Java
 * requires the file name to match the public class name with a .java
 * extension, or the class will not compile.
 */
public class Logger1{
    private static final String FILE_NAME = "log.txt";
    public static void log(String message){
        try{
            FileWriter writer = new FileWriter(FILE_NAME,true);
            writer.write(LocalDateTime.now() + " - " + message + "\n");
            writer.close();
        }
        catch (IOException e){
            System.out.println("Error writing to log file.");
        }
    }
}