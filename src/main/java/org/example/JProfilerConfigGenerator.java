package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

/** Saturday
 * Starts JProfiler
 */
public class JProfilerConfigGenerator {
    /**
     * Creates a file for JProfiler to configure files
     */
    public static void createConfigFile(String repoPath) throws IOException {
        String configPath = Paths.get(repoPath, "jprofiler-config.xml").toString();
        String configContent = generateConfigXML();

        try (FileWriter writer = new FileWriter(configPath)) {
            writer.write(configContent);
            System.out.println("[DEBUG] Created JProfiler config: " + configPath);
        }


    }

    private static String generateConfigXML() {
        return """
                <?xml version="1.0" encoding="UTF-8"?>
                <jprofiler version="13.0">
                  <session>
                    <name>AssertionTracing</name>
                    <application>
                      <mainClass>org.apache.maven.surefire.booter.ForkedBooter</mainClass>
                    </application>
                    <profiling>
                      <methodCall>
                        <enabled>true</enabled>
                        <recordingMode>automatic</recordingMode>
                        <filters>
                          <!-- JUnit 5 Assertions -->
                          <include>org.junit.jupiter.api.Assertions.*</include>
                          <!-- JUnit 4 Assertions -->
                          <include>org.junit.Assert.*</include>
                          <include>junit.framework.Assert.*</include>
                          <!-- Custom Assertions -->
                          <include>org.apache.commons.lang3.LangAssertions.*</include>
                          <!-- Hamcrest -->
                          <include>org.hamcrest.MatcherAssert.*</include>
                          <!-- AssertJ -->
                          <include>org.assertj.core.api.Assertions.*</include>
                          <!-- Mockito -->
                          <include>org.mockito.Mockito.verify*</include>
                        </filters>
                      </methodCall>
                      <memory>
                        <enabled>false</enabled>
                      </memory>
                      <threads>
                        <enabled>false</enabled>
                      </threads>
                    </profiling>
                  </session>
                </jprofiler>
                """;
    }
}
