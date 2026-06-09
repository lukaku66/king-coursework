package service;

import model.*;
import java.io.*;

/**
 * FileStorageService — Handles data persistence for the whole system.
 *
 * It saves and loads the complete GameDataManager object by Java object
 * serialization. This is suitable here because the project uses object
 * relationships such as Player-Team and Player-Hero bidirectional links.
 */
public class FileStorageService {

    private static final String DATA_DIR = "data/";
    private static final String DATA_FILE = DATA_DIR + "game_data.dat";

    public FileStorageService() {
        ensureDirectoryExists();
    }

    /** Loads all data from disk. If no saved file exists, returns an empty manager. */
    public GameDataManager loadAll() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return new GameDataManager();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = in.readObject();
            if (obj instanceof GameDataManager) {
                System.out.println("[FileStorageService] Data loaded from " + DATA_FILE);
                return (GameDataManager) obj;
            }
            System.out.println("[FileStorageService] Invalid data file. Starting with empty data.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[FileStorageService] Failed to load data: " + e.getMessage());
        }
        return new GameDataManager();
    }

    /** Saves all data to disk. */
    public void saveAll(GameDataManager manager) {
        if (manager == null) {
            System.out.println("[FileStorageService] No data to save.");
            return;
        }

        ensureDirectoryExists();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(manager);
            System.out.println("[FileStorageService] Data saved to " + DATA_FILE);
        } catch (IOException e) {
            System.out.println("[FileStorageService] Failed to save data: " + e.getMessage());
        }
    }

    private void ensureDirectoryExists() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
