package de.tinysite.stpnb;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import org.junit.Before;
import org.junit.Test;


public class StpParserFilesBatchTest extends StpParserTest {

    private static final Logger LOG = Logger.getLogger(StpParserFilesBatchTest.class.getSimpleName());
    
    @Before
    public void before() {
        assumeTrue(isSampleDirDefined());    
    }

    private static boolean isSampleDirDefined() {
        return System.getProperty("STP_SAMPLES_DIR") != null;
    }

    @Test
    public void testSilverstripeTemplates() throws IOException {
        Path templatePath = Paths.get(System.getProperty("STP_SAMPLES_DIR"));
        assertTrue(Files.isDirectory(templatePath));
        Files.walkFileTree(templatePath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if(!file.toString().toLowerCase().endsWith(".ss")) {
                    return FileVisitResult.CONTINUE;
                }
                LOG.log(Level.INFO, "Process file: " + file.toString());
                String fileContent = com.google.common.io.Files.toString(file.toFile(), Charset.defaultCharset());
                getParserTree(fileContent);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException ex) {
                LOG.log(Level.WARNING, "Failed to visit file " + file.toString());
                return FileVisitResult.CONTINUE;
            }
            
        });
    }
}
