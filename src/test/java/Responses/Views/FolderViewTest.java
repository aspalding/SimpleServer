package Responses.Views;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class FolderViewTest {

    @Test
    public void testLinkBuild() throws Exception {
        FolderView fv = new FolderView("/.");
        assertEquals("<li><a href=\"/TestMedia\">andrew</a></li>\n" ,fv.buildLink("andrew", new File(System.getProperty("user.dir") + "/TestMedia")));
    }

    @Test
    public void testWeirdLinkBuild() throws Exception {
        FolderView fv = new FolderView("/.");
        assertEquals("<li><a href=\"/TestMedia/apples/file\">andrew</a></li>\n" ,fv.buildLink("andrew", new File(System.getProperty("user.dir") + "/TestMedia/apples/file")));
    }
}