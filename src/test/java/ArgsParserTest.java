import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ArgsParserTest {

    @Test
    public void testParseArgumentsPort() throws Exception {
        String[] cat = {"-p", "8080"};
        List<String> args = Arrays.asList(cat);

        ArgsParser.parseArguments(args);

        assertEquals(ArgsParser.port, 8080);
    }

    @Test
    public void testParseArgumentsRoot() throws Exception {
        String[] cat = {"-d", "/Users/andrew"};
        List<String> args = Arrays.asList(cat);

        ArgsParser.parseArguments(args);

        assertEquals(ArgsParser.root, "/Users/andrew");
    }

    @Test
    public void testParseArgumentsBoth() throws Exception {
        String[] cat = {"-p", "8080", "-d", "/Users/andrew"};
        List<String> args = Arrays.asList(cat);

        ArgsParser.parseArguments(args);

        assertEquals(ArgsParser.port, 8080);
        assertEquals(ArgsParser.root, "/Users/andrew");
    }

    @Test
    public void testParseArgumentsInvalid() throws Exception {
        String[] cat = {"-p", "-d"};
        List<String> args = Arrays.asList(cat);

        ArgsParser.parseArguments(args);

        assertEquals(ArgsParser.port, 5000);
    }

    @Test
    public void testParseArgumentsInvalidPort() throws Exception {
        String[] cat = {"-p", "-2000"};
        List<String> args = Arrays.asList(cat);

        ArgsParser.parseArguments(args);

        assertEquals(ArgsParser.port, 5000);
    }

    @Test
    public void testParseArgumentsInvalidDirectory() throws Exception {
        String[] cat = {"-d", "C:"};
        List<String> args = Arrays.asList(cat);

        ArgsParser.parseArguments(args);

        assertEquals(ArgsParser.root, System.getProperty("user.dir"));
    }



}