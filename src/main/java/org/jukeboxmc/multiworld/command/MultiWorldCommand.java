package org.jukeboxmc.multiworld.command;

import com.nukkitx.protocol.bedrock.data.command.CommandParamType;
import org.jukeboxmc.Server;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandData;
import org.jukeboxmc.command.CommandParameter;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.multiworld.MultiWorld;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "multiworld" )
@Description ( "Load or unload a world or teleport to another world" )
@Permission ( "multiworld.command.execute" )
public class MultiWorldCommand extends Command {

    private final MultiWorld plugin;

    public MultiWorldCommand( MultiWorld plugin ) {
        super( CommandData.builder()
                .setAliases( "mw" )
                .setParameters( new CommandParameter[]{
                                new CommandParameter( "list", List.of( "list" ), false )
                        },
                        new CommandParameter[]{
                                new CommandParameter( "tp", List.of("tp"), false ),
                                new CommandParameter( "worldName", CommandParamType.TEXT, false )
                        },
                        new CommandParameter[]{
                                new CommandParameter( "load", List.of("load"), false ),
                                new CommandParameter( "worldName", CommandParamType.TEXT, false )
                        },
                        new CommandParameter[]{
                                new CommandParameter( "unload", List.of("unload"), false ),
                                new CommandParameter( "worldName", CommandParamType.TEXT, false )
                        }
                ).build() );
        this.plugin = plugin;
    }

    @Override
    public void execute( CommandSender commandSender, String s, String[] args ) {
        if ( args.length == 1 ) {
            if ( args[0].equalsIgnoreCase( "list" ) ) {
                StringBuilder builder = new StringBuilder();
                builder.append( "§7======== §8| §eAll loaded worlds §8| §7========" ).append( "\n" );
                for ( World world : Server.getInstance().getWorlds() ) {
                    builder.append( "§7- §a" ).append( world.getName() ).append( "\n" );
                }
                builder.setLength( builder.length() - 1 );
                commandSender.sendMessage( builder.toString() );
            } else {
                commandSender.sendMessage( this.getHelpPage() );
            }
        } else if ( args.length == 2 ) {
            String worldName = args[1].toLowerCase();
            if ( args[0].equalsIgnoreCase( "tp" ) ) {
                if ( commandSender instanceof Player player ) {
                    if ( Server.getInstance().isWorldLoaded( worldName ) ) {
                        World world = Server.getInstance().getWorld( worldName );
                        if ( world != null ) {
                            player.teleport( world.getSpawnLocation() );
                            player.sendMessage( this.plugin.getPrefix() + "§7You have been teleported to the world §e" + worldName );
                        } else {
                            player.sendMessage( this.plugin.getPrefix() + "§cAn error has occurred #1" );
                        }
                    } else {
                        commandSender.sendMessage( this.plugin.getPrefix() + "§cThe world §e" + worldName + " §cis not loaded." );
                    }
                } else {
                    commandSender.sendMessage( this.plugin.getPrefix() + "§cYou must be a player." );
                }
            } else if ( args[0].equalsIgnoreCase( "load" ) ) {
                if ( !Server.getInstance().isWorldLoaded( worldName ) ) {
                    World world = Server.getInstance().loadWorld( worldName );
                    if ( world != null ) {
                        commandSender.sendMessage( this.plugin.getPrefix() + "§7The world §e" + worldName + " §7loaded successfully." );
                    } else {
                        commandSender.sendMessage( this.plugin.getPrefix() + "§cAn error has occurred #2" );
                    }
                } else {
                    commandSender.sendMessage( this.plugin.getPrefix() + "§cThe world §e" + worldName + " §cis already loaded." );
                }
            } else if ( args[0].equalsIgnoreCase( "unload" ) ) {
                if ( Server.getInstance().isWorldLoaded( worldName ) ) {
                    Server.getInstance().unloadWorld( worldName );
                    commandSender.sendMessage( this.plugin.getPrefix() + "§7The world §e" + worldName + " §7was unloaded." );
                } else {
                    commandSender.sendMessage( this.plugin.getPrefix() + "§cThe world §e" + worldName + " §cis already unloaded." );
                }
            } else {
                commandSender.sendMessage( this.getHelpPage() );
            }
        } else {
            commandSender.sendMessage( this.getHelpPage() );
        }
    }

    private String getHelpPage() {
        return """
                §7======== §8| §eMultiWorld §8| §7========
                §7- /mw list | Show all loaded worlds.
                §7- /mw tp <worldName> | Teleport to another world.
                §7- /mw load <worldName> | Load a world.
                §7- /mw unload <worldName> | Unload a world.""";
    }
}
