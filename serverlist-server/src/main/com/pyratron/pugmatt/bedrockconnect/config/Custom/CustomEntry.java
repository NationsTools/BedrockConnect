package main.com.pyratron.pugmatt.bedrockconnect.config.Custom;

import main.com.pyratron.pugmatt.bedrockconnect.BedrockConnect;

public class CustomEntry {
    // Note: DEFAULT_ICON is now loaded from ImageAssets instead of being hardcoded
    private String getDefaultIcon() {
        return BedrockConnect.getConfig().getImageAssets().getDefaultServerIcon();
    }

    private String name;
    private String iconUrl;

    public CustomEntry(String name, String iconUrl) {
        super();

        this.name = name;
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public String getIconUrl() {
        if (iconUrl == null)
            return getDefaultIcon();
        else
            return iconUrl;
    }
}
