package me.adam.pl1;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.reflect.StructureModifier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main
extends JavaPlugin
implements Listener {
    ProtocolManager protocolManager;

    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.protocolManager.addPacketListener((PacketListener)new PacketAdapter((Plugin)this, ListenerPriority.NORMAL, new PacketType[]{PacketType.Play.Client.TAB_COMPLETE}){

            public void onPacketReceiving(PacketEvent e) {
                if (!e.getPlayer().hasPermission("kps.tab.bypass") && e.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
                    String message = ((String)e.getPacket().getSpecificModifier((Class)String.class).read(0)).toLowerCase();
                    String messagetabplugins = main.this.getConfig().getString("message-tabplugins");
                    String playerName = e.getPlayer().getName();
                    String optabnotify = main.this.getConfig().getString("message-opnotify-tab");
                    if (optabnotify.contains("%player%")) {
                        optabnotify = optabnotify.replaceAll("%player%", playerName);
                        if (message.startsWith("/") && !message.contains(" ")) {
                            e.setCancelled(true);
                        } else if (message.startsWith("/help") && message.contains(" ") || message.startsWith("/bukkit:help") && message.contains(" ")) {
                            e.setCancelled(true);
                            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)messagetabplugins));
                            Bukkit.broadcast((String)ChatColor.translateAlternateColorCodes((char)'&', (String)optabnotify), (String)"kps.notify");
                        } else if (message.startsWith("/?") && message.contains(" ") || message.startsWith("/bukkit:?") && message.contains(" ")) {
                            e.setCancelled(true);
                            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)messagetabplugins));
                            Bukkit.broadcast((String)ChatColor.translateAlternateColorCodes((char)'&', (String)optabnotify), (String)"kps.notify");
                        } else if (message.startsWith("/about") && message.contains(" ") || message.startsWith("/bukkit:about") && message.contains(" ")) {
                            e.setCancelled(true);
                            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)messagetabplugins));
                            Bukkit.broadcast((String)ChatColor.translateAlternateColorCodes((char)'&', (String)optabnotify), (String)"kps.notify");
                        } else if (message.startsWith("/ver") && message.contains(" ") || message.startsWith("/bukkit:ver") && message.contains(" ")) {
                            e.setCancelled(true);
                            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)messagetabplugins));
                            Bukkit.broadcast((String)ChatColor.translateAlternateColorCodes((char)'&', (String)optabnotify), (String)"kps.notify");
                        }
                    }
                }
            }
        });
    }

    public void onDisable() {
    }

    @EventHandler
    public void preCommand(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        String playerName = player.getName();
        String oplistnotify = this.getConfig().getString("message-opnotify-list");
        if (oplistnotify.contains("%player%")) {
            oplistnotify = oplistnotify.replaceAll("%player%", playerName);
            String messagelistplugins = this.getConfig().getString("message-listplugins");
            if (!player.hasPermission("kps.list.bypass")) {
                if (e.getMessage().toLowerCase().startsWith("/help") || e.getMessage().toLowerCase().startsWith("/?") || e.getMessage().toLowerCase().startsWith("/about") || e.getMessage().toLowerCase().startsWith("/ver") || e.getMessage().toLowerCase().startsWith("/pl") || e.getMessage().toLowerCase().startsWith("/plugins")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)messagelistplugins));
                    Bukkit.broadcast((String)ChatColor.translateAlternateColorCodes((char)'&', (String)oplistnotify), (String)"kps.notify");
                    e.setCancelled(true);
                    return;
                }
                if (e.getMessage().toLowerCase().startsWith("/bukkit:help") || e.getMessage().toLowerCase().startsWith("/bukkit:?") || e.getMessage().toLowerCase().startsWith("/bukkit:about") || e.getMessage().toLowerCase().startsWith("/bukkit:ver") || e.getMessage().toLowerCase().startsWith("/bukkit:pl") || e.getMessage().toLowerCase().startsWith("/bukkit:plugins")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)messagelistplugins));
                    Bukkit.broadcast((String)ChatColor.translateAlternateColorCodes((char)'&', (String)oplistnotify), (String)"kps.notify");
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String onjoinmessage = this.getConfig().getString("message-join");
        p.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)onjoinmessage));
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("kps")) {
            p.sendMessage((Object)ChatColor.RED + "==================KPS=======================");
            p.sendMessage((Object)ChatColor.DARK_AQUA + "Name: KPluginSecure                            ");
            p.sendMessage((Object)ChatColor.DARK_AQUA + "Author: @HTML                                            ");
            p.sendMessage((Object)ChatColor.DARK_AQUA + "Version: 1.7                                              ");
            p.sendMessage((Object)ChatColor.DARK_AQUA + "Website: http://bit.ly/1T1QppI                                           ");
            p.sendMessage((Object)ChatColor.DARK_AQUA + "Reload command: /kpsreload                            ");
            p.sendMessage((Object)ChatColor.RED + "===========================================");
        } else if (cmd.getName().equalsIgnoreCase("kpsreload")) {
            if (!p.hasPermission("kps.reload")) {
                String messagenopermsreload = this.getConfig().getString("message-noperms-reload");
                p.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)messagenopermsreload));
                return true;
            }
            this.reloadConfig();
            String reloadmessage = this.getConfig().getString("reload-message");
            p.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)reloadmessage));
        }
        return true;
    }

}
