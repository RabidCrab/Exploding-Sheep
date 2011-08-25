package me.RabidCrab.ExplodingSheep.Events;

import java.util.List;

import me.RabidCrab.ExplodingSheep.ExplodingSheep;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * Upon a player command, figure out what they want
 * @author RabidCrab
 */
public class ExplodingSheepCommandExecutor implements CommandExecutor {

	public static ExplodingSheep plugin;
	public static List<LivingEntity> entities;
	
	public ExplodingSheepCommandExecutor(ExplodingSheep instance) 
	{
		plugin = instance;
	}

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(label.equalsIgnoreCase("esheep") && sender != null && args != null)
		{
			// Get the player, we'll need it
			Player player = (Player)sender;
			
			// All of the commands have to do with settings. Make sure the user has the correct settings
            // before even displaying the help text
			if (!player.hasPermission("explodingsheep.modifysettings") && !player.isOp())
			{
			    player.sendMessage("You do not have permission to modify settings!");
			    return true;
			}
			    
			if (args.length < 1)
			{
			    displayGeneralHelp(player);
			    return true;
			}
			
			if (args[0].equalsIgnoreCase("enable"))
            {
                ExplodingSheep.configuration.setExplodingSheepEnabled(true);
                player.sendMessage("Exploding sheep enabled.");
                return true;
            }
			if (args[0].equalsIgnoreCase("disable"))
            {
                ExplodingSheep.configuration.setExplodingSheepEnabled(false);
                player.sendMessage("Exploding sheep disabled.");
                return true;
            }
			if (args[0].equalsIgnoreCase("leap") && args.length == 2)
            {
			    if (args[1].equalsIgnoreCase("enable"))
			    {
			        ExplodingSheep.configuration.setSheepLeap(true);
			        player.sendMessage("Exploding sheep leap enabled.");
			    }
			    else
			    {
			        ExplodingSheep.configuration.setSheepLeap(false);
			        player.sendMessage("Exploding sheep leap disabled.");
			    }
			    
			    return true;
            }
			
			if (args[0].equalsIgnoreCase("radius") && args.length == 2)
			{
			    ExplodingSheep.configuration.setExplosionRadius(Integer.parseInt(args[1]));
			    player.sendMessage("Exploding sheep radius set to " + args[1].toString() + ".");
			    return true;
			}
			
			if (args[0].equalsIgnoreCase("explodechance") && args.length == 2)
            {
			    int percentage = Integer.parseInt(args[1]);
			    
			    if (percentage < 1 || percentage > 100)
			    {
			        player.sendMessage("The value must be between 1 and 100!");
			        return true;
			    }
			    
                ExplodingSheep.configuration.setExplosionRadius(percentage);
                player.sendMessage("Exploding sheep radius set to " + args[1].toString() + ".");
                return true;
            }
		}

		return false;
	}
	
	/**
	 * Display the general help to a specific player
	 */
	private void displayGeneralHelp(Player player)
	{
	    List<String> helpList = ExplodingSheep.configuration.getGeneralCommandsHelp();
	    
	    if (helpList == null)
	    {
	        player.sendMessage(ExplodingSheep.configuration.getGeneralHelpNotFound());
	        return;
	    }

        if (helpList.size() > 0)
    	    for (String helpText : ExplodingSheep.configuration.getGeneralCommandsHelp())
        	    player.sendMessage(helpText);
        else
            player.sendMessage(ExplodingSheep.configuration.getGeneralHelpNotFound()); 
	}
}