package com.profittracker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

/**
 * The ProfitTrackerConfig class is used to provide user preferences to the Pthe feerofitTrackerPlugin.
 */
@ConfigGroup("ptconfig")
public interface ProfitTrackerConfig extends Config
{
    /**
     * Regex for validating League Player IDs.
     * Example valid ID: 68a0ae63-32a2-4e89-997b-4b26d5950112
     */
    String LEAGUE_PLAYER_ID_REGEX =
            "^[0-9a-fA-F]{8}-" +
                    "[0-9a-fA-F]{4}-" +
                    "[0-9a-fA-F]{4}-" +
                    "[0-9a-fA-F]{4}-" +
                    "[0-9a-fA-F]{12}$";

    @ConfigSection(
            name = "Visual",
            description = "Settings for what the plugin features look like.",
            position = 0,
            closedByDefault = false
    )
    String visualSettings = "Visual";

    @ConfigSection(
            name = "Behavior",
            description = "Settings for calculation behavior.",
            position = 1,
            closedByDefault = false
    )
    String behaviorSettings = "Behavior";

    @ConfigItem(
            keyName = "goldDrops",
            name = "Show value changes (gold drops)",
            description = "Show each profit increase or decrease.",
            section = visualSettings
    )
    default boolean goldDrops()
    {
        return true;
    }

    @ConfigItem(
            keyName = "unhideGoldDrops",
            name = "Unhide value changes",
            description = "Prevents other plugins from hiding value changes if they are enabled.",
            section = visualSettings
    )
    default boolean unhideGoldDrops()
    {
        return true;
    }

    @ConfigItem(
            keyName = "autoStart",
            name = "Automatically start tracking",
            description = "Automatically begin tracking profit on session start.",
            section = behaviorSettings
    )
    default boolean autoStart()
    {
        return true;
    }

    @ConfigItem(
            keyName = "shortDrops",
            name = "Shorten drop numbers",
            description = "Shorten drop numbers like 1.2K instead of 1,223, or 10M instead of 10,000,000.",
            section = visualSettings
    )
    default boolean shortDrops()
    {
        return true;
    }

    @ConfigItem(
            keyName = "iconStyle",
            name = "Icon style",
            description = "Dynamically adjust the coin icon based on the drop value, or select a specific icon.",
            section = visualSettings
    )
    default ProfitTrackerIconType iconStyle()
    {
        return ProfitTrackerIconType.DYNAMIC;
    }

    @ConfigItem(
            keyName = "estimateUntradeables",
            name = "Estimate untradeable item values",
            description = "Some untradeable items will utilize equivalent values of the best items they can convert into.",
            section = behaviorSettings
    )
    default boolean estimateUntradeables()
    {
        return true;
    }

    @ConfigItem(
            keyName = "onlineOnlyRate",
            name = "Online only rate",
            description = "Show profit rate only for time spent logged in.",
            section = visualSettings
    )
    default boolean onlineOnlyRate()
    {
        return false;
    }

    //LeaguePlayerID─────────────────────────────────────

    @ConfigItem(
            keyName = "leaguePlayerId",
            name = "League player ID",
            description = "Enter your League player ID (e.g. 68a0ae63-32a2-4e89-997b-4b26d5950112)",
            section = behaviorSettings,
            position = 10
    )
    default String leaguePlayerId()
    {
        return "";
    }

    @ConfigItem(
            keyName = "submitLeagueId",
            name = "✓ Save League ID",
            description = "Click to save your League player ID. Validation results and status messages will appear in the in-game chat log and as desktop notifications.",
            section = behaviorSettings,
            position = 11
    )
    default boolean submitLeagueId()
    {
        return false;
    }

    @ConfigItem(
            keyName = "leagueIdNote",
            name = "ℹ️ Check in-game chat for validation status",
            description = "After clicking Save, validation results will appear in your in-game chat log.",
            section = behaviorSettings,
            position = 12
    )
    default String leagueIdNote()
    {
        return "";
    }
}
