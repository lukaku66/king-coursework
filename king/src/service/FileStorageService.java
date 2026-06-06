package service;

import model.*;
import java.io.*;
import java.util.ArrayList;

/**
 * FileStorageService — Stub implementation for testing.
 * Save/Load methods are no-ops until full implementation is ready.
 */
public class FileStorageService {

    private static final String DATA_DIR = "data/";

    public FileStorageService() {
        ensureDirectoryExists();
    }

    /** Returns a new empty GameDataManager (no file loading yet). */
    public GameDataManager loadAll() {
        return new GameDataManager();
    }

    /** No-op save. Data will not persist across restarts. */
    public void saveAll(GameDataManager manager) {
        System.out.println("[FileStorageService] Save not implemented yet. Data not persisted.");
    }

    private void ensureDirectoryExists() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
