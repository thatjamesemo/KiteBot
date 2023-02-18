package com.thatjamesemo;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ConfigFile {
    private File configFile;

    public ConfigFile(long serverID) {
        this.configFile = new File("config/" + serverID + ".json");
    }

    public void setOption(String option, Object value) {
        Map<String, Object> config = readConfig();
        config.put(option, value);
        writeConfig(config);
    }

    public Object getOption(String option) {
        Map<String, Object> config = readConfig();
        return config.get(option);
    }

    // Long.parseLong((String) configFile.getOption("rsvp-channel"));
    public long getOptionAsLong(String option) {
        Map<String, Object> config = readConfig();
        Object value = config.get(option);

        return Long.parseLong((String) value);
    }

    public String getOptionAsString(String option) {
        Map<String, Object> config = readConfig();
        Object value = config.get(option);

        return String.valueOf(value);
    }

    private Map<String, Object> readConfig() {
        if (!configFile.exists()) {
            return new HashMap<>();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString().trim();
            if (json.isEmpty()) {
                return new HashMap<String, Object>();
            }
            Type type = new TypeToken<Map<String, Object>>() {
            }.getType();
            return new Gson().fromJson(json, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<String, Object>();
        }
    }

    private void writeConfig(Map<String, Object> config) {
        if (!configFile.getParentFile().exists()) {
            configFile.getParentFile().mkdirs();
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Long.class, new JsonSerializer<Long>() {
                    @Override
                    public JsonElement serialize(Long aLong, Type type, JsonSerializationContext jsonSerializationContext) {
                        return new JsonPrimitive(aLong);
                    }
                }).setPrettyPrinting().create();

        try (Writer writer = new FileWriter(configFile)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
