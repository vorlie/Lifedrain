package vorlie.lifedrain.utils;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;

public class UpdateChecker {
    private static final String MOD_ID = "lifedrain";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final String GITHUB_RELEASES_URL = "https://api.github.com/repos/vorlie/Lifedrain/releases/latest";
    private static final String MODRINTH_URL_FORMAT = "https://modrinth.com/mod/lifedrain/version/%s";

    public static String getCurrentVersion() {
        return FabricLoader.getInstance()
                .getModContainer(MOD_ID)
                .map(ModContainer::getMetadata)
                .map(metadata -> metadata.getVersion().getFriendlyString())
                .orElse("Unknown");
    }

    public static void checkForUpdates() {
        Thread updateThread = new Thread(() -> {
            try {
                LOGGER.info("[LifeDrain] Checking for updates...");
                String latestVersion = fetchLatestVersionFromGitHub();
                String currentVersion = getCurrentVersion();

                // Only notify if the current version is less than the latest version and not a dev version
                if (isNewVersionAvailable(currentVersion, latestVersion)) {
                    LOGGER.info("[LifeDrain] A new version is available: {}", latestVersion);
                    notifyPlayer(latestVersion, currentVersion);
                } else {
                    LOGGER.info("[LifeDrain] No update found. Current version is up-to-date.");
                }
            } catch (Exception e) {
                LOGGER.error("[LifeDrain] Error checking for updates: {}", e.getMessage(), e);
            }
        });

        // Set the name of the thread
        updateThread.setName("LifeDrain thread");

        // Start the thread
        updateThread.start();
    }

    private static String fetchLatestVersionFromGitHub() throws IOException {
        LOGGER.debug("[LifeDrain] Fetching latest version from GitHub...");
        try {
            URI githubApiUri = new URI(GITHUB_RELEASES_URL);
            HttpURLConnection connection = (HttpURLConnection) githubApiUri.toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "LifeDrain-UpdateChecker");

            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder jsonResponse = new StringBuilder();
            while (scanner.hasNext()) {
                jsonResponse.append(scanner.nextLine());
            }
            scanner.close();

            // Parse the "tag_name" field from the JSON response
            String response = jsonResponse.toString();
            return response.split("\"tag_name\":\"")[1].split("\"")[0];
        } catch (Exception e) {
            throw new IOException("[LifeDrain] Failed to fetch the latest version from GitHub: " + e.getMessage(), e);
        }
    }

    private static boolean isNewVersionAvailable(String currentVersion, String latestVersion) {
        // If the current version is development or higher than the latest release, skip the update message
        return !currentVersion.equals(latestVersion) && !currentVersion.toLowerCase().contains("-dev")
                && compareVersions(currentVersion, latestVersion) < 0;
    }

    private static int compareVersions(String currentVersion, String latestVersion) {
        // Compare version strings
        String[] currentParts = currentVersion.split("-");
        String[] latestParts = latestVersion.split("-");

        // Compare the numeric parts (before the dash)
        String[] currentVersionParts = currentParts[0].split("\\.");
        String[] latestVersionParts = latestParts[0].split("\\.");

        for (int i = 0; i < Math.min(currentVersionParts.length, latestVersionParts.length); i++) {
            int currentPart = Integer.parseInt(currentVersionParts[i]);
            int latestPart = Integer.parseInt(latestVersionParts[i]);
            if (currentPart < latestPart) {
                return -1; // current version is lower
            } else if (currentPart > latestPart) {
                return 1; // current version is higher
            }
        }
        return 0; // versions are equal
    }

    private static void notifyPlayer(String latestVersion, String currentVersion) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            String modrinthLink = String.format(MODRINTH_URL_FORMAT, latestVersion); // Create the Modrinth link based on the latest version

            // Prepare the base message with the version number and Modrinth link
            MutableText message = Text.literal("[LifeDrain] A new version is available: ")
                    .formatted(Formatting.GREEN)
                    .append(Text.literal(latestVersion).formatted(Formatting.AQUA))
                    .append(Text.literal(". Download it from "))
                    .append(Text.literal("Modrinth")
                            .styled(style -> style.withColor(Formatting.BLUE)
                                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, modrinthLink))));

            // If current version contains "dev", notify the player that it's a dev version
            if (currentVersion.toLowerCase().contains("-dev")) {
                message.append(Text.literal(" (Development version, things may not work properly!)").formatted(Formatting.RED));
            }

            // Send the update notification to the player
            client.player.sendMessage(message, false);

            LOGGER.info("[LifeDrain] Update message sent to player.");
        }
    }
}
