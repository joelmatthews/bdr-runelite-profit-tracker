package com.profittracker;

import com.profittracker.services.leagueservice.LeagueService;
import net.runelite.api.Client;
import net.runelite.client.Notifier;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.events.ConfigChanged;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class ProfitTrackerPluginTest {

    private ProfitTrackerPlugin plugin;
    private Client client;
    private ProfitTrackerConfig config;
    private ConfigManager configManager;
    private Notifier notifier;
    private ChatMessageManager chatMessageManager;
    private LeagueService leagueService;


    @Before
    public void setUp() {
        // Mock all dependencies
        client = mock(Client.class);
        config = mock(ProfitTrackerConfig.class);
        configManager = mock(ConfigManager.class);
        notifier = mock(Notifier.class);
        chatMessageManager = mock(ChatMessageManager.class);
        leagueService = mock(LeagueService.class);

        // Create plugin instance and inject mocks via reflection
        plugin = new ProfitTrackerPlugin();
        injectMocks();
    }

    private void injectMocks() {
        try {
            setField("client", client);
            setField("config", config);
            setField("configManager", configManager);
            setField("notifier", notifier);
            setField("chatMessageManager", chatMessageManager);
            setField("leagueService", leagueService);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mocks", e);
        }
    }

    private void setField(String fieldName, Object value) throws Exception {
        java.lang.reflect.Field field = ProfitTrackerPlugin.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(plugin, value);
    }

    private ConfigChanged createConfigChangedEvent(String group, String key, String newValue) {
        ConfigChanged event = mock(ConfigChanged.class);
        when(event.getGroup()).thenReturn(group);
        when(event.getKey()).thenReturn(key);
        when(event.getNewValue()).thenReturn(newValue);
        return event;
    }

    @Test
    public void onConfigChanged_DuplicateLeagueId_ShouldNotifyAlreadyExists() throws Exception {
        // Arrange
        String validId = "68a0ae63-32a2-4e89-997b-4b26d5950112";
        ConfigChanged event = createConfigChangedEvent("ptconfig", "submitLeagueId", "true");
        when(config.leaguePlayerId()).thenReturn(validId);
        when(leagueService.addLeaguePlayerId(validId)).thenThrow(new IllegalArgumentException("League player id " + validId + " already exists in leagueData")); // duplicate throws exception

        // Act
        plugin.onConfigChanged(event);

        // Assert
        verify(configManager).setConfiguration("ptconfig", "submitLeagueId", false);
        verify(leagueService).addLeaguePlayerId(validId);
        verify(notifier).notify("League player ID already exists in the list");
        verify(chatMessageManager).queue(any());
        verify(configManager).setConfiguration("ptconfig", "leaguePlayerId", "");
    }
}
