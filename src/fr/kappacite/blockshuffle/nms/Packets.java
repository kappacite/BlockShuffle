package fr.kappacite.blockshuffle.nms;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;


public class Packets {

    public void sendTablist(Player player){

        IChatBaseComponent header = IChatBaseComponent.ChatSerializer.a("{text: '\n§9BlockShuffle - Par Kappacité\n'}");
        IChatBaseComponent footer = IChatBaseComponent.ChatSerializer.a("{text: '\n§aNontia - play.nontia.fr\n'}");

        PacketPlayOutPlayerListHeaderFooter packetPlayOutPlayerListHeaderFooter = new PacketPlayOutPlayerListHeaderFooter();

        try {
            Field headerField = packetPlayOutPlayerListHeaderFooter.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packetPlayOutPlayerListHeaderFooter, header);

            Field footerField = packetPlayOutPlayerListHeaderFooter.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packetPlayOutPlayerListHeaderFooter, footer);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutPlayerListHeaderFooter);

    }

    public void sendTitle(Player player, String title, String subtitle, int ticks){
        IChatBaseComponent baseTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
        IChatBaseComponent baseSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, baseTitle);
        PacketPlayOutTitle subTitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, baseSubTitle);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlePacket);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subTitlePacket);
        sendTime(player, ticks);
    }

    private void sendTime(Player player, int ticks){
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES,  null, 20, ticks, 20);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlePacket);
    }

    public void sendActionBar(Player player, String message){
        IChatBaseComponent baseTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat packet = new PacketPlayOutChat(baseTitle, (byte)2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

}
