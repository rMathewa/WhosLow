package com.onrappture.whoslow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class GlowConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("whoslow.json");

    private static GlowConfig instance = new GlowConfig();

    private boolean enabled = true;
    private int higherHealthColor = 0x55FF55;
    private int lowerHealthColor = 0xFF5555;
    private int equalHealthColor = 0xFFFF55;
    private boolean lineOfSightOnly = true;

    public static GlowConfig get() {
        return instance;
    }

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                instance = GSON.fromJson(reader, GlowConfig.class);
            } catch (IOException e) {
                instance = new GlowConfig();
            }
        }
    }

    public static void save() {
        try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
            GSON.toJson(instance, writer);
        } catch (IOException ignored) {
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getHigherHealthColor() {
        return higherHealthColor;
    }

    public void setHigherHealthColor(int color) {
        this.higherHealthColor = color;
    }

    public int getLowerHealthColor() {
        return lowerHealthColor;
    }

    public void setLowerHealthColor(int color) {
        this.lowerHealthColor = color;
    }

    public int getEqualHealthColor() {
        return equalHealthColor;
    }

    public void setEqualHealthColor(int color) {
        this.equalHealthColor = color;
    }

    public boolean isLineOfSightOnly() {
        return lineOfSightOnly;
    }

    public void setLineOfSightOnly(boolean lineOfSightOnly) {
        this.lineOfSightOnly = lineOfSightOnly;
    }
}

// #made by mathew love u all <3
