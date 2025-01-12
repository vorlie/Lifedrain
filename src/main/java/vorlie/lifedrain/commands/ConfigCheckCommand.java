package vorlie.lifedrain.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vorlie.lifedrain.config.ConfigManager;
import static vorlie.lifedrain.LifeDrain.MOD_ID;

public class ConfigCheckCommand {
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Command registration
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("check_config")
                        .requires(source -> source.hasPermissionLevel(1))  // Permission level check
                        .executes(ConfigCheckCommand::executeCheckConfigCommand)  // Execute the method directly
        );
    }

    // Execute the command
    public static int executeCheckConfigCommand(CommandContext<ServerCommandSource> context) {
        LOGGER.info("Checking config for missing values...");

        // Check and add missing values in the config
        ConfigManager.load();  // Reload the config file
        ConfigManager.addMissingFields(ConfigManager.getConfigJson());  // Add missing fields

        // Send feedback to the player (command source)
        context.getSource().sendFeedback(() -> Text.literal(
                "LifeDrain: Config file has been checked and updated. " + "To apply changes, run /reload."
        ), false);

        // Save the updated config back to the file
        ConfigManager.save();

        return 1;  // Return value to indicate command execution success
    }
}
