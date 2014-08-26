import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArgsParser{
    private static final Logger ARGS_LOG = Logger.getLogger( ArgsParser.class.getName() );
    private static final String DEFAULT_ROOT = System.getProperty("user.dir");
    private static final int DEFAULT_PORT = 5000;

    public static String root = DEFAULT_ROOT;
    public static int port = DEFAULT_PORT;

    public static void parseArguments(List<String> args){
        int index, indexOfArgument;
        for(String arg : args){
            index = args.indexOf(arg);
            indexOfArgument = index + 1;

            if(arg.equals("-p") && indexOfArgument < args.size())
                port = parsePort(args.get(indexOfArgument));
            else if(arg.equals("-d") && indexOfArgument < args.size())
                root = parseRoot(args.get(indexOfArgument));
        }
    }

    public static String parseRoot(String input){
        if(Files.isDirectory(Paths.get(input)))
            return input;
        else {
            ARGS_LOG.log(Level.INFO, "Invalid directory, using default");
            return DEFAULT_ROOT;
        }
    }

    public static int parsePort(String input){
        int result = 0;
        try {
            result = Integer.parseInt(input);
        } catch (NumberFormatException e){
            ARGS_LOG.log(Level.INFO, "Invalid port, using default");
        }

        if(isValidPort(result))
            return result;
        else
            return DEFAULT_PORT;
    }

    private static boolean isValidPort(int input){
        return input > 0 && input < 65535;
    }

}