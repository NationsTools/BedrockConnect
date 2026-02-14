package main.com.pyratron.pugmatt.bedrockconnect.server;

public class ServerInfo {
    private String name;
    private String address;
    private int port;
    private String imageUrl;
    private String category; // "featured" (NationsGlory) or "other"

    public ServerInfo(String name, String address, int port, String imageUrl, String category) {
        this.name = name;
        this.address = address;
        this.port = port;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }
}
