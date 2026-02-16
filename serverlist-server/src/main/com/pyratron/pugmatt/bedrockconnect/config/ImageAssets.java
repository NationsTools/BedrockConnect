package main.com.pyratron.pugmatt.bedrockconnect.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import main.com.pyratron.pugmatt.bedrockconnect.BedrockConnect;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Manages image assets URLs from images.json
 * Similar to Language.java but for image resources
 */
public class ImageAssets {
    private JsonObject images;
    private JsonObject defaults;
    private JsonObject nationsglory;
    private JsonObject popularServers;

    public ImageAssets() {
        load();
    }

    private void load() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("images.json");
            if (is == null) {
                BedrockConnect.logger.error("Could not find images.json in resources");
                return;
            }

            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            Gson gson = new Gson();
            images = gson.fromJson(reader, JsonObject.class);

            if (images.has("defaults")) {
                defaults = images.getAsJsonObject("defaults");
            }
            if (images.has("nationsglory")) {
                nationsglory = images.getAsJsonObject("nationsglory");
            }
            if (images.has("popular_servers")) {
                popularServers = images.getAsJsonObject("popular_servers");
            }

            BedrockConnect.logger.info("Loaded image assets configuration");
        } catch (Exception e) {
            BedrockConnect.logger.error("Failed to load images.json", e);
        }
    }

    /**
     * Get default server icon URL
     */
    public String getDefaultServerIcon() {
        if (defaults != null && defaults.has("server_icon")) {
            return defaults.get("server_icon").getAsString();
        }
        return "https://s3.nationstools.fr/public/bedrockconnect/World.png"; // Fallback
    }

    /**
     * Get default user server icon URL
     */
    public String getUserServerIcon() {
        if (defaults != null && defaults.has("user_server")) {
            return defaults.get("user_server").getAsString();
        }
        return "https://i.imgur.com/nhumQVP.png"; // Fallback
    }

    /**
     * Get NationsGlory server icon by name
     * @param serverName Name of the server (hub, alpha, sigma, omega, delta, epsilon)
     */
    public String getNationsGloryIcon(String serverName) {
        if (nationsglory != null && nationsglory.has(serverName.toLowerCase())) {
            return nationsglory.get(serverName.toLowerCase()).getAsString();
        }
        return getDefaultServerIcon();
    }

    /**
     * Get popular server icon by name
     * @param serverName Name of the server (hive, cubecraft, lifeboat, etc.)
     */
    public String getPopularServerIcon(String serverName) {
        if (popularServers != null && popularServers.has(serverName.toLowerCase().replace(" ", "_"))) {
            return popularServers.get(serverName.toLowerCase().replace(" ", "_")).getAsString();
        }
        return getDefaultServerIcon();
    }

    /**
     * Get any image URL by path (e.g., "nationsglory.hub" or "popular_servers.hive")
     * @param path Dot-separated path to the image
     */
    public String getImage(String path) {
        try {
            String[] parts = path.split("\\.");
            JsonObject current = images;
            
            for (int i = 0; i < parts.length - 1; i++) {
                if (current.has(parts[i])) {
                    current = current.getAsJsonObject(parts[i]);
                } else {
                    return getDefaultServerIcon();
                }
            }
            
            String lastPart = parts[parts.length - 1];
            if (current.has(lastPart)) {
                return current.get(lastPart).getAsString();
            }
        } catch (Exception e) {
            BedrockConnect.logger.warn("Failed to get image for path: " + path, e);
        }
        
        return getDefaultServerIcon();
    }
}
