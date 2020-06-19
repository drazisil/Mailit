package com.drazisil.mailit;

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

        plugin.getServer().getPluginManager().registerEvents(new ItemListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PLayerListener(), plugin);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public MailboxManager getMailboxManager() {
        return this.mailboxManager;
    }
}
