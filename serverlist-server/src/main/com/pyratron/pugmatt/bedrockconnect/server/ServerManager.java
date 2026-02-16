package main.com.pyratron.pugmatt.bedrockconnect.server;

import main.com.pyratron.pugmatt.bedrockconnect.BedrockConnect;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ServerManager {
    private static final String FILE_NAME = "servers.json";
    private List<ServerInfo> featuredServers = new ArrayList<>();
    private List<ServerInfo> otherServers = new ArrayList<>();

    public ServerManager() {
        loadServers();
    }

    public void loadServers() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            createDefaultServers();
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray featured = (JSONArray) jsonObject.get("featured");
            if (featured != null) {
                for (Object obj : featured) {
                    JSONObject server = (JSONObject) obj;
                    featuredServers.add(new ServerInfo(
                            (String) server.get("name"),
                            (String) server.get("address"),
                            ((Long) server.get("port")).intValue(),
                            (String) server.get("image"),
                            "featured"
                    ));
                }
            }

            JSONArray other = (JSONArray) jsonObject.get("other");
            if (other != null) {
                for (Object obj : other) {
                    JSONObject server = (JSONObject) obj;
                    otherServers.add(new ServerInfo(
                            (String) server.get("name"),
                            (String) server.get("address"),
                            ((Long) server.get("port")).intValue(),
                            (String) server.get("image"),
                            "other"
                    ));
                }
            }

            BedrockConnect.logger.info("Loaded " + featuredServers.size() + " featured servers and " + otherServers.size() + " other servers.");

        } catch (Exception e) {
            BedrockConnect.logger.error("Failed to load servers.json", e);
        }
    }

    private void createDefaultServers() {
        JSONObject root = new JSONObject();
        JSONArray featured = new JSONArray();
        JSONArray other = new JSONArray();

        // NationsGlory Servers
        addServer(featured, "Hub NationsGlory", "bedrock.nationsglory.fr", 19132, 
            BedrockConnect.getConfig().getImageAssets().getNationsGloryIcon("hub"));
        addServer(featured, "NationsGlory Alpha", "bedrock.nationsglory.fr", 19100, 
            BedrockConnect.getConfig().getImageAssets().getNationsGloryIcon("alpha"));
        addServer(featured, "NationsGlory Sigma", "bedrock.nationsglory.fr", 19102, 
            BedrockConnect.getConfig().getImageAssets().getNationsGloryIcon("sigma"));
        addServer(featured, "NationsGlory Omega", "bedrock.nationsglory.fr", 19103, 
            BedrockConnect.getConfig().getImageAssets().getNationsGloryIcon("omega"));
        addServer(featured, "NationsGlory Delta", "bedrock.nationsglory.fr", 19101, 
            BedrockConnect.getConfig().getImageAssets().getNationsGloryIcon("delta"));
        addServer(featured, "NationsGlory Epsilon", "bedrock.nationsglory.fr", 19104, 
            BedrockConnect.getConfig().getImageAssets().getNationsGloryIcon("epsilon"));

        // Other Servers
        addServer(other, "The Hive", "geo.hivebedrock.network", 19132, 
            BedrockConnect.getConfig().getImageAssets().getPopularServerIcon("hive"));
        addServer(other, "CubeCraft", "mco.cubecraft.net", 19132, 
            BedrockConnect.getConfig().getImageAssets().getPopularServerIcon("cubecraft"));
        addServer(other, "Lifeboat", "mco.lbsg.net", 19132, 
            BedrockConnect.getConfig().getImageAssets().getPopularServerIcon("lifeboat"));
        addServer(other, "Mineville", "play.inpvp.net", 19132, 
            BedrockConnect.getConfig().getImageAssets().getPopularServerIcon("mineville"));
        addServer(other, "Galaxite", "play.galaxite.net", 19132, 
            BedrockConnect.getConfig().getImageAssets().getPopularServerIcon("galaxite"));
        addServer(other, "Enchanted Dragons", "play.enchanted.gg", 19132, 
            BedrockConnect.getConfig().getImageAssets().getPopularServerIcon("enchanted_dragons"));

        root.put("featured", featured);
        root.put("other", other);

        try (FileWriter file = new FileWriter(FILE_NAME)) {
            file.write(root.toJSONString());
            file.flush();
            BedrockConnect.logger.info("Created default servers.json");
            loadServers(); // Reload to populate lists
        } catch (Exception e) {
            BedrockConnect.logger.error("Failed to create servers.json", e);
        }
    }

    private void addServer(JSONArray list, String name, String address, int port, String image) {
        JSONObject server = new JSONObject();
        server.put("name", name);
        server.put("address", address);
        server.put("port", port);
        server.put("image", image);
        list.add(server);
    }

    public List<ServerInfo> getFeaturedServers() {
        return featuredServers;
    }

    public List<ServerInfo> getOtherServers() {
        return otherServers;
    }
}
