package me.RabidCrab.Listeners;

import java.util.Random;
import java.util.Timer;

import me.RabidCrab.ExplodingSheep.ExplodingSheep;
import me.RabidCrab.ExplodingSheep.Timers.SheepExplodeTimer;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * Listens for the entity damage of a sheep. If a sheep is getting hurt, it's time to bring the hurt to the perpetrator
 * @author RabidCrab
 */
public class ExplodingSheepListener extends EntityListener
{
    private static ExplodingSheep plugin;
    private Timer explosionTimer = new Timer();
    
    // Create the listener
    public ExplodingSheepListener(ExplodingSheep instance) 
    {
        plugin = instance;
    }
    
    public void onEntityDamage(EntityDamageEvent event)
    {
        if (plugin != null)
        {
            // Oh please be a sheep
            if (event.getEntity() instanceof org.bukkit.entity.Sheep)
            {
                // As if any sheep are victims. They all deserve what they get coming to them
                Sheep victim = (org.bukkit.entity.Sheep)event.getEntity();

                // If the sheep is going to die, bring it back to full health and detonate the bugger
                if (((org.bukkit.entity.Sheep)event.getEntity()).getHealth() <= event.getDamage())
                {
                    LivingEntity killer = GetKiller(event);
                    
                    // Make sure a killer is found, and make sure it isn't a wolf
                    if (killer != null && !(killer instanceof Wolf)) 
                    {
                        // We don't want this to hurt the chances of someone getting an explosion in their face
                        try
                        {
                            if (((Player)killer).hasPermission("explodingsheep.immune"))
                                return;
                        }
                        catch (Exception e) {}
                        
                        // Time to whip out the random number generator and get the chance that this sheep is going to blow.
                        Random rand = new Random();
                        if (rand.nextInt(100) <= ExplodingSheep.configuration.getExplosionPercentage())
                        {
                            // And the final check to make sure this sheep is allowed to detonate in this world
                            if (ExplodingSheep.configuration.getAllowedWorlds().contains(victim.getWorld().getName()))
                            {
                                // Heal the sheep and set it to "attack" the killer
                                victim.setHealth(200);
                                victim.setTarget(killer);
                                
                                // If the sheep leaping is enabled, make the miniature wolly mammoth fly
                                if (ExplodingSheep.configuration.getSheepLeap())
                                {
                                    Vector targetVelocity = killer.getLocation().toVector();
                                    targetVelocity.subtract(victim.getLocation().toVector());
                                    targetVelocity.multiply(0.35);
                                    targetVelocity.add(new Vector(0,50,0));
                                    victim.setVelocity(targetVelocity);
                                }
                                
                                // Play the cry of an angry sheep if they have spout
                                if (ExplodingSheep.isSpoutEnabled)
                                    SpoutManager.getSoundManager().playCustomSoundEffect(plugin, (SpoutPlayer)GetKiller(event), "http://www.saiogames.com/minecraft/sounds/explodingSheep1.wav", false, event.getEntity().getLocation());
                                
                                // Begin the countdown timer to detonate this baby into a fleshy ball of death
                                explosionTimer.schedule(new SheepExplodeTimer(this, event), 4000);
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * After the timer is finished, this is executed
     * @param event
     */
    public void DetonateSheep(EntityDamageEvent event)
    {
        // The explosion
        event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), ExplodingSheep.configuration.getExplosionRadius(), false);
        
        // Make sure the sheep goes away in the explosion.
        event.getEntity().remove();
    }
    
    /**
     * Pulled from http://forums.bukkit.org/threads/work-around-for-lack-of-entitydamagebyprojectileevent.31727/#post-581023
     * @param event
     * @return The killer of the sheep.
     */
    private LivingEntity GetKiller(EntityDamageEvent event)
    {
        //check for damage by entity (and arrow)
        if(event instanceof EntityDamageByEntityEvent)
        {
            EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) event;
            
            if(!(nEvent.getDamager() instanceof Arrow))
            {
                if (nEvent.getDamager() instanceof LivingEntity)
                    return (LivingEntity)nEvent.getDamager();
                else
                    return null;
            }
            
            //This will retrieve the arrow object
            Arrow a = (Arrow) nEvent.getDamager();
            
            //This will retrieve the person who shot the arrow
            return a.getShooter();
        }

        return null;
    }
}
