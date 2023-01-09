package org.jukeboxmc.multiworld;

import org.jukeboxmc.command.CommandManager;
import org.jukeboxmc.multiworld.command.MultiWorldCommand;
import org.jukeboxmc.plugin.Plugin;
import org.jukeboxmc.plugin.PluginManager;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class MultiWorld extends Plugin {

    @Override
    public void onEnable() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        CommandManager commandManager = pluginManager.getCommandManager();
        commandManager.registerCommand( new MultiWorldCommand( this ) );
    }

    public String getPrefix() {
        return "§7[§eMultiWorld§7] ";
    }

}
