package me.RabidCrab.ExplodingSheep;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.bukkit.ChatColor;
import org.bukkit.util.config.Configuration;

import me.RabidCrab.Entities.YMLFile;

/**
 * Currently the file with all of the configuration information.
 * May split file up later if there's any issues of size or scope.
 * @author RabidCrab
 */
public class ConfigurationFile extends YMLFile
{
    Callable<String[]> arguments;
    
    public ConfigurationFile(File file) throws IOException
    {
        super(file);
    }

    @Override
    protected void populateFile(Configuration file)
    {
        
        // application information I'll need eventually for updates and whatnot
        super.configurationFile.setProperty("explodingsheep.application.files.config.Version", "0.1");
        super.configurationFile.setProperty("explodingsheep.application.Version", "0.1");
        
        // Settings with no embedding
        super.configurationFile.setProperty("explodingsheep.default.SheepExplode", true);
        super.configurationFile.setProperty("explodingsheep.default.ExplosionRadius", 2);
        super.configurationFile.setProperty("explodingsheep.default.SheepLeap", true);
        super.configurationFile.setProperty("explodingsheep.default.ExplosionPercentage", 25);
        
        List<String> allowedWorlds = new ArrayList<String>();
        allowedWorlds.add("world");
        allowedWorlds.add("world_nether");
        
        super.configurationFile.setProperty("explodingsheep.default.worlds", allowedWorlds);
        
        // Help settings
        List<String> generalCommandsHelp = new ArrayList<String>();
        
        generalCommandsHelp.add(ChatColor.GREEN + "/esheep " + ChatColor.LIGHT_PURPLE + "[enable/disable]" + ChatColor.WHITE + " - Enables/disables the sheep to explode");
        generalCommandsHelp.add(ChatColor.GREEN + "/esheep " + ChatColor.LIGHT_PURPLE + "[enable/disable]" + ChatColor.WHITE + " - Toggles the sheep leaping towards their killer.");
        generalCommandsHelp.add(ChatColor.GREEN + "/esheep radius " + ChatColor.LIGHT_PURPLE + "[1-10]" + ChatColor.WHITE + " - Sets the radius of explosion.");
        
        super.configurationFile.setProperty("explodingsheep.help.GeneralCommands", generalCommandsHelp);
        super.configurationFile.setProperty("explodingsheep.help.GeneralHelpNotFound", "No help list found.");
        
        super.configurationFile.save();
    }
    
    /**
     * This has to do with parameters. I may at some point need to add them, who knows
     */
    private String getStringFromFile(String location)
    {
        return this.configurationFile.getString(location);
    }
    
    public boolean getExplodingSheepEnabled()
    {
        return super.configurationFile.getBoolean("explodingsheep.default.SheepExplode", false);
    }
    
    public void setExplodingSheepEnabled(boolean explodingSheepEnabled)
    {
        super.configurationFile.setProperty("explodingsheep.default.SheepExplode", explodingSheepEnabled);
        this.save();
    }
    
    public boolean getSheepLeap()
    {
        return super.configurationFile.getBoolean("explodingsheep.default.SheepLeap", true);
    }
    
    public void setSheepLeap(boolean canSheepLeap)
    {
        super.configurationFile.setProperty("explodingsheep.default.SheepLeap", canSheepLeap);
        this.save();
    }
    
    public int getExplosionRadius()
    {
        return super.configurationFile.getInt("explodingsheep.default.ExplosionRadius", 2);
    }
    
    public void setExplosionRadius(int explosionRadius)
    {
        super.configurationFile.setProperty("explodingsheep.default.ExplosionRadius", explosionRadius);
        this.save();
    }
    
    public int getExplosionPercentage()
    {
        return super.configurationFile.getInt("explodingsheep.default.ExplosionPercentage", 25);
    }
    
    public void setExplosionPercentage(int explosionPercentage)
    {
        super.configurationFile.setProperty("explodingsheep.default.ExplosionPercentage", explosionPercentage);
        this.save();
    }
    
    public String getGeneralHelpNotFound()
    {
        return getStringFromFile("explodingsheep.help.GeneralHelpNotFound");
    }
    
    public void setGeneralHelpNotFound(String generalHelpNotFound)
    {
        super.configurationFile.setProperty("explodingsheep.help.GeneralHelpNotFound", generalHelpNotFound);
        this.save();
    }
    
    public List<String> getGeneralCommandsHelp()
    {
        return super.configurationFile.getStringList("explodingsheep.help.GeneralCommands", new ArrayList<String>());
    }
    
    public List<String> getAllowedWorlds()
    {
        return super.configurationFile.getStringList("explodingsheep.default.worlds", new ArrayList<String>());
    }
    
    /**
     * Save any changes made to the configuration file
     */
    public void save()
    {
        super.configurationFile.save();
    }
}