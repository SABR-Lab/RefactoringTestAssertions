package org.example;

import org.apache.maven.shared.invoker.MavenCommandLineBuilder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Builds commands for tests using JProfiler
 */
public class TestCommandBuilder {
    private final String repoPath;
    private final String jprofilerHome;

    public TestCommandBuilder(String repoPath, String jprofilerHome) {
        this.repoPath = repoPath;
        this.jprofilerHome = jprofilerHome;
    }

    /**
     * Builds command
     */
    public String[] buildTestCommand(String testFilePath, String profileFile) {
        if (Files.exists(Paths.get(repoPath, "pom.xml"))) {
            return buildMavenCommand(testFilePath, profileFile);
        } else if (Files.exists(Paths.get(repoPath, "build.gradle")) ||
                Files.exists(Paths.get(repoPath, "build.gradle.kts"))) {
            return buildGradleCommand(testFilePath, profileFile);
        } else {
            // List what files exist for debugging
            try {
                System.err.println("[DEBUG] Repository contents:");
                Files.list(Paths.get(repoPath))
                        .limit(10)
                        .forEach(path -> System.err.println("  " + path.getFileName()));
            } catch (Exception e) {
                System.err.println("[DEBUG] Could not list repository contents");
            }

            throw new RuntimeException("No Maven (pom.xml) or Gradle (build.gradle) file found in: " + repoPath);
        }
    }

    private String[] buildMavenCommand(String testFilePath, String profileFile) {
        List<String> command = new ArrayList<>();

        command.add("mvn");
        command.add("test");
        command.add("-Dtest=" + extractTestClassName(testFilePath));

        // Skip Apache RAT license checking
        command.add("-Drat.skip=true");
        command.add("-Dcheckstyle.skip=true");    // Skip code style
        command.add("-Dspotbugs.skip=true");      // Skip static analysis
        command.add("-Dpmd.skip=true");           // Skip PMD
        command.add("-DskipTests=false");         // Ensure tests still run

        String jvmArgs = buildJProfilerAgentArgs(profileFile);
        command.add("-DargLine=" + jvmArgs);
        command.add("-q");

        return command.toArray(new String[0]);
    }

    // Make gradle method later . . .
    // TODO: Still faulty not as concrete as Maven since I still get errors for Gradle repos
    private String[] buildGradleCommand(String testFilePath, String profileFile) {
        List<String> command = new ArrayList<>();

        command.add("./gradlew");
        command.add("test");

        // Extract test class name
        String testClassName = extractTestClassName(testFilePath);
        command.add("--tests");
        command.add(testClassName);

        // Add JProfiler agent
        String jvmArgs = buildJProfilerAgentArgs(profileFile);
        command.add("-Dorg.gradle.jvmargs=" + jvmArgs);

        // Suppress output
        command.add("--quiet");

        return command.toArray(new String[0]);
    }

    private String buildJProfilerAgentArgs(String profileFile) {
        String agentPath = jprofilerHome + "/bin/macos/libjprofilerti.jnilib";

        if (!new File(agentPath).exists()) {
            throw new RuntimeException("JProfiler native agent not found: " + agentPath);
        }

        System.out.println("[DEBUG] Using JProfiler native agent: " + agentPath);

        // CHANGE: Use offline mode that auto-saves snapshots
        return String.format(
                "-agentpath:%s=offline,id=test_%d,file=%s",
                agentPath,
                System.currentTimeMillis(), // Unique session ID
                profileFile  // Where to save the snapshot
        );
    }

    private String extractTestClassName(String testFilePath) {
        String fileName = Paths.get(testFilePath).getFileName().toString();
        return fileName.replace(".java", "");
    }

}


/* OLD Version of BuildTestCommand
String agentPath = jprofilerHome + "/bin/macos/libjprofilerti.jnilib";

        if (!new File(agentPath).exists()) {
            throw new RuntimeException("JProfiler native agent not found: " + agentPath);
        }

        System.out.println("[DEBUG] Using JProfiler native agent: " + agentPath);

        // Minimal JProfiler configuration
        return String.format(
                "-agentpath:%s=port=8849,nowait",
                agentPath
        );
 */
