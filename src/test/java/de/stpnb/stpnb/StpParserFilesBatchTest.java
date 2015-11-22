package de.stpnb.stpnb;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import org.junit.Before;
import org.junit.Test;


//TODO LOG
public class StpParserFilesBatchTest extends StpParserTest {
    @Before
    public void before() {
        assumeTrue(isSampleDirDefined());    
    }

    private boolean isSampleDirDefined() {
        return System.getProperty("SAMPLES_DIR") != null;
    }

    @Test
    public void testSilverstripeTemplates() throws IOException {
        Path templatePath = Paths.get(System.getProperty("SAMPLES_DIR"));
        assertTrue(Files.isDirectory(templatePath));
        Files.walkFileTree(templatePath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if(!file.toString().toLowerCase().endsWith(".ss")) {
                    return FileVisitResult.CONTINUE;
                }
                System.out.println(file.toString());
                String fileContent = com.google.common.io.Files.toString(file.toFile(), Charset.defaultCharset());
                getParserTree(fileContent);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException ex) {
                System.out.println("Failed to visit file " + file.toString());
                return FileVisitResult.CONTINUE;
            }
            
        });
    }
}
