package com.pantrypro;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import com.pantrypro.application.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TestUtils {
    private static final File DB_SRC = new File("src/main/assets/db/PantryProDB.script");

    public static File copyDB() throws IOException {
        final File target = File.createTempFile("temp-db", ".script");
        Files.copy(DB_SRC.toPath(), target.toPath(), REPLACE_EXISTING);
        Main.setDBPathName(target.getAbsolutePath().replace(".script", ""));
        return target;
    }
}

