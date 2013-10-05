package com.acburdine.copybook;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener{
	
	@EventHandler
	public void Sign(SignChangeEvent event) {
		
		String line1 = event.getLine(1);
		String line2 = event.getLine(2);
		String line3 = event.getLine(3);
		boolean isInt = Commands.isInt(line3);
		int price = 0;
		if (isInt) {
			price = Integer.parseInt(line3);
		}
		
		if(line1.equalsIgnoreCase("[CopyBook]")) {
			Player p = event.getPlayer();
			if (!p.hasPermission("copybook.createsign")) {
				p.sendMessage(ChatColor.RED + "You don't have permission to create a CopyBook sign.");
				event.setCancelled(true);
			} else {
				if (line3.isEmpty() || !isInt) {
					p.sendMessage("You have created a sign to give the book " + line2 + ".");
				} else {
					p.sendMessage("You have created a sign to give the book " + line2 + " with a price of " + price + " coins.");
				}
				event.setLine(1, ChatColor.GREEN + line1);
			}
		}
	}

}
