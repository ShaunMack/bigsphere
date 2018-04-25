package com.javaminecraft;

import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BigSphere extends JavaPlugin {
    public static final Logger LOG = Logger.getLogger("Minecraft");
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player) {
            if (label.equalsIgnoreCase("bigsphere")){
                executeCommand(sender, args);
            }
            return true;
        }
        return false;
    }
    public void executeCommand(CommandSender sender, String[]args){
        double dx = 30;
        if(args.length > 0){
            try {
                dx = Double.parseDouble(args[0]);
                if((dx < 5 ) || (dx > 200)){
                    dx = 30;
                }
            } catch (NumberFormatException exception){
                // Do nuttin
            }
        }
        Player me = (Player) sender;
        Location spot = me.getLocation();
        World world = me.getWorld();
        
        for(double x = spot.getX() - dx; x <= spot.getX() + dx; x++){
            for(double y = spot.getY() - dx; y <= spot.getY() + dx; y++){
                for(double z = spot.getZ() - dx; z <= spot.getZ() + dx; z++){
                    Location loc = new Location(world, x, y, z);
                    if (y < 2){
                        continue;
                    }
                    double xd = x - spot.getX();
                    double yd = y - spot.getY();
                    double zd = z - spot.getZ();
                    double distance = Math.sqrt(xd * xd + yd * yd + zd * zd);
                    if (distance <= dx+0.5 && distance > dx-0.5) {
                        Block current = world.getBlockAt(loc);
                        current.setType(Material.GLASS);
                    }
                }
            }
        }
        world.playSound(spot, Sound.DIG_GRAVEL, 30, 5);
        LOG.info("[BigSphere] Testing Alpha: Make Big Sphere: done at: " + (int) spot.getX() + " ," + (int) spot.getY() + " ," + (int) spot.getZ() + ", with sound effects.");
        me.sendMessage("Making sphere around your location with radius " + dx + ".");
    }
}