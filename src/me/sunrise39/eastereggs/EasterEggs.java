package me.sunrise39.eastereggs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class EasterEggs extends JavaPlugin {

	private static EasterEggs instance;
	private Map<Location, ArrayList<String>> easterEggs = new HashMap<Location, ArrayList<String>>();
	
	
	@Override
	public void onEnable() {
		instance = this;
		getCommand("easteregg").setExecutor(new CommandEasterEgg());
		getServer().getPluginManager().registerEvents(new EventPlayerInteract(), this);
		
		EasterEggs.getInstance().saveConfig();
		init();
	}
	
	public void init() {
		List<String> locs = EasterEggs.getInstance().getConfig().getStringList("easter-eggs");

		for (String str : locs) {
			String[] saved = str.split(":");
			String world = saved[0];
			double x = Double.parseDouble(saved[1]);
			double y = Double.parseDouble(saved[2]);
			double z = Double.parseDouble(saved[3]);
			Location location = new Location(Bukkit.getWorld(world), x, y, z);
			
			String co = saved[4].toString();
			co = co.toString().substring(1, co.length() - 1);
			String[] commands = co.split(", ");
			
			/*String[] trim = new String[commands.length];
			for (int i = 0; i < commands.length; i++) {
				trim[i] = commands[i].trim();
			}*/
			
			ArrayList<String> command = new ArrayList<String>(Arrays.asList(commands));
			
			easterEggs.put(location, command);
			Bukkit.getConsoleSender().sendMessage("§4" + easterEggs.toString());
		}
	}
	
	public static EasterEggs getInstance() {
		return instance;
	}
	
	public Map<Location, ArrayList<String>> getEasterEggs(){
		return this.easterEggs;
	}
	
	public ArrayList<String> getEasterEggCommands(Location egg){
		return getEasterEggs().get(egg);
	}
}