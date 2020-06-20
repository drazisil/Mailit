package com.drazisil.mailit;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Mailit extends JavaPlugin {

    public static Mailit plugin;
    public static Logger logger;

    private MailboxManager mailboxManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        plugin = this;
        logger = plugin.getLogger();
        mailboxManager = new MailboxManager();

        plugin.getServer().getPluginManager().registerEvents(new PlayerListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryListener(), plugin);

        PluginCommand cmdSend = plugin.getCommand("send");
        if (cmdSend != null) cmdSend.setExecutor(new CommandSend());

        PluginCommand cmdMail = plugin.getCommand("mail");
        if (cmdMail != null) cmdMail.setExecutor(new CommandMail());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public MailboxManager getMailboxManager() {
        return this.mailboxManager;
    }
}
