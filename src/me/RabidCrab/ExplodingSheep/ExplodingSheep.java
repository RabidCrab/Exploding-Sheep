package me.RabidCrab.ExplodingSheep;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import me.RabidCrab.ExplodingSheep.Events.ExplodingSheepCommandExecutor;
import me.RabidCrab.ExplodingSheep.ConfigurationFile;
import me.RabidCrab.Listeners.ExplodingSheepListener;

import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The big cheese. Start hooking onto the various items
 * @author RabidCrab
 */
public class ExplodingSheep extends JavaPlugin
{
    Logger log = Logger.getLogger("Minecraft");
    private final ExplodingSheepCommandExecutor commandExecutor = new ExplodingSheepCommandExecutor(this);
    public static ConfigurationFile configuration;
    private final ExplodingSheepListener sheepListener = new ExplodingSheepListener(this);
    public static boolean isSpoutEnabled;
    
    @Override
    public void onEnable()
    {
        try
        {
            configuration = new ConfigurationFile(new File("plugins" + File.separator + "ExplodingSheep" + File.separator + "Config.yml"));
        } 
        catch (IOException e)
        {
            log.info(e.getMessage());
        }
        
        if (this.getServer().getPluginManager().getPlugin("Spout") != null)
            isSpoutEnabled = true;
        else
            isSpoutEnabled = false;
        
        // Register the player chat
        this.getServer().getPluginManager().registerEvent(Event.Type.ENTITY_DAMAGE, sheepListener, Event.Priority.High, this);
        
        // Hook onto Bukkit's command event
        this.getCommand("esheep").setExecutor(commandExecutor);
    }
    
    @Override
    public void onDisable()
    {
        // No resources to unload... yet.
    }
}
