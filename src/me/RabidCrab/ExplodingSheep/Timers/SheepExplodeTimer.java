package me.RabidCrab.ExplodingSheep.Timers;

import java.util.TimerTask;

import org.bukkit.event.entity.EntityDamageEvent;

import me.RabidCrab.Listeners.ExplodingSheepListener;

/**
 * Used by me.RabidCrab.Listeners.ExplodingSheepListenere to execute the timeout of a sheep's life
 * @author RabidCrab
 */
public class SheepExplodeTimer extends TimerTask
{
    private EntityDamageEvent event;
    private ExplodingSheepListener instance;
    
    public SheepExplodeTimer(ExplodingSheepListener instance, EntityDamageEvent event)
    {
        this.instance = instance;
        this.event = event;
    }
    
    @Override
    public void run()
    {
        instance.DetonateSheep(event);
    }
}
