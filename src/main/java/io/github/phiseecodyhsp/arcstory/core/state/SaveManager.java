package io.github.phiseecodyhsp.arcstory.core.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class SaveManager {

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private static final Path SAVE_DIR = Paths.get(System.getProperty("user.home"), ".arcstory");
    private static final Path SAVE_FILE = SAVE_DIR.resolve("save.json");

    private SaveManager() {}

    public static GameState load() throws IOException {
        if (!Files.exists(SAVE_FILE)) {
            return new GameState();
        }
        return mapper.readValue(SAVE_FILE.toFile(), GameState.class);
    }

    public static void save(GameState state) throws IOException {
        if (!Files.exists(SAVE_DIR)) {
            Files.createDirectories(SAVE_DIR);
        }
        mapper.writeValue(SAVE_FILE.toFile(), state);
    }

    public static Path getSavePath() {
        return SAVE_FILE;
    }
}
