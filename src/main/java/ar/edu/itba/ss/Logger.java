package ar.edu.itba.ss;

public class Logger {
    public enum LogType {
        INFO, WARNING, ERROR, TIMING
    }

    private static Boolean disabled = false;

    static public void log(String msg, LogType type) {

        if(disabled)
            return;

        String label;
        String caller = Thread.currentThread().getStackTrace()[3].getClassName();
        switch(type) {
            case INFO:
                label = "[INFO]";
                break;
            case WARNING:
                label = "[WARN]";
                break;
            case ERROR:
                label = "[ERROR]";
                break;
            case TIMING:
                label = "[TIMING]";
                break;
            default:
                label = "";
        }
        System.out.println(label + " " + caller + ": " + msg);
    }

    static public void log(String msg) {
        log(msg, LogType.INFO);
    }
}
