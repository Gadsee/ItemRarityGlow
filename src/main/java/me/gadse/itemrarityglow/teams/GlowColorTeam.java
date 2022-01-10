package me.gadse.itemrarityglow.teams;

import me.gadse.itemrarityglow.ItemRarityGlow;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public enum GlowColorTeam {
    BLACK(ChatColor.BLACK),
    DARK_BLUE(ChatColor.DARK_BLUE),
    DARK_GREEN(ChatColor.DARK_GREEN),
    DARK_AQUA(ChatColor.DARK_AQUA),
    DARK_RED(ChatColor.DARK_RED),
    DARK_PURPLE(ChatColor.DARK_PURPLE),
    GOLD(ChatColor.GOLD),
    GRAY(ChatColor.GRAY),
    DARK_GRAY(ChatColor.DARK_GRAY),
    BLUE(ChatColor.BLUE),
    GREEN(ChatColor.GREEN),
    AQUA(ChatColor.AQUA),
    RED(ChatColor.RED),
    PURPLE(ChatColor.LIGHT_PURPLE),
    YELLOW(ChatColor.YELLOW),
    WHITE(ChatColor.WHITE);

    private String teamName;
    private final ChatColor chatColor;
    private Team team;

    GlowColorTeam(ChatColor chatColor) {
        this.teamName = "GLOW_" + name();
        if (this.teamName.length() > 16)
            this.teamName = this.teamName.substring(0, 16);
        this.chatColor = chatColor;
    }

    public void injectTeam(ItemRarityGlow plugin) {
        ScoreboardManager scoreboardManager = plugin.getServer().getScoreboardManager();
        if (scoreboardManager == null)
            return;

        try {
            team = scoreboardManager.getMainScoreboard().registerNewTeam(teamName);
        } catch (IllegalArgumentException e) {
            team = scoreboardManager.getMainScoreboard().getTeam(teamName);
            if (team == null)
                throw new IllegalStateException("Team has been created by another plugin.");
        }

        team.setColor(chatColor);
        team.setPrefix(chatColor.toString());
        team.setDisplayName(teamName);
        team.setSuffix("");
    }

    public Team getTeam() {
        return team;
    }

    public void unregisterTeam() {
        team.unregister();
    }
}
