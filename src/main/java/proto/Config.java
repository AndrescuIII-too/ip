package proto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import proto.exception.ProtoInvalidData;

public class Config {
    private static final Path CONFIG_PATH = Proto.DATA_PATH.resolve("config.txt");
    private static final Path DEFAULT_SAVE_PATH = Proto.DATA_PATH.resolve("tasks.txt");
    private Path savePath;

    private Config(Path savePath) {
        this.savePath = savePath;
    }

    protected Path getSavePath() {
        return this.savePath;
    }

    protected void setSavePath(Path path) throws IOException {
        assert path.toFile().isFile();
        if (!this.savePath.equals(path)) {
            this.savePath = path;
            this.save();
        }
    }

    protected static Config initialize() throws IOException, ProtoInvalidData {
        File file = Config.CONFIG_PATH.toFile();

        // Ensure path and file exists
        file.getParentFile().mkdirs();

        // Initialize empty config file
        if (file.createNewFile() || Files.size(Config.CONFIG_PATH) == 0) {
            Config config = new Config(Config.DEFAULT_SAVE_PATH);
            config.save();
            return config;
        }

        return Config.load(file);
    }

    private void save() throws IOException {
        try (FileWriter writer = new FileWriter(Config.CONFIG_PATH.toFile())) {
            writer.write("save-path: " + this.savePath.toString() + "\n");
        }
    }

    private static String extractLine(BufferedReader readStream, String fieldName)
            throws IOException, ProtoInvalidData {
        String line;
        if ((line = readStream.readLine()) == null) {
            throw new ProtoInvalidData("config file is invalid");
        }

        Pattern pattern = Pattern.compile("^" + fieldName + ": (.*)$");
        Matcher match = pattern.matcher(line);

        if (!match.find()) {
            throw new ProtoInvalidData("config file is invalid");
        }
        return match.group(1);
    }

    private static Config load(File file) throws IOException, ProtoInvalidData {
        try (FileReader reader = new FileReader(file)) {
            BufferedReader readStream = new BufferedReader(reader);
            String savePath = Config.extractLine(readStream, "save-path");
            return new Config(Path.of(savePath));
        }
    }
}
