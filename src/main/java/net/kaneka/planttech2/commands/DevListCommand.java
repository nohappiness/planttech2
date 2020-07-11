package net.kaneka.planttech2.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DevListCommand
{
    private static int onlineMembersCount = 0;
    //K: username, V: full UUID
    public static final HashMap<String, String> DEVELOPERS = new HashMap<String, String>() {{
            put("VeyezX(Vex)", "8ea51d9d-ccc9-4ce4-a9fd-9e8b5d1ceaf3");
            put("Kaneka", "df361308-a4b8-469c-a3b6-63bb8b2ec7e3");
            put("pupudice", "f0c9774d-e79f-4a58-b524-3f3d014e58ed");
            put("poopoodice", "b3fd2b3b-df09-4790-8187-650219671a69");
            put("BeerCav", "bc58f1ec-9853-4dd0-a0bb-da38cfaf6fe6");
            put("DinoSoup", "1dc30eef-5be5-4afa-9e1e-9a5c20df653f");
            put("MoonlitLamps", "");
            put("andrew0030", "0b37421b-e74e-4852-bf57-23907d295ea1");
            put("Setrion", "c5beef70-7f28-44c8-a1cc-91a1cc8836e6");
    }};
    public static final ArrayList<String> ARTISTS = new ArrayList<String>() {{
            add("VeyezX(Vex)");
            add("BeerCav");
            add("DinoSoup");
            add("MoonlitLamps");
            add("andrew0030");
    }};
    public static final ArrayList<String> AUTHORS = new ArrayList<String>() {{
            add("Kaneka");
            add("Poopoodice(pupudice)");
            add("Setrion");
    }};
    public DevListCommand() {}
    public static ArgumentBuilder<CommandSource, LiteralArgumentBuilder<CommandSource>> register(CommandDispatcher<CommandSource> dispatcher)
    {
        return Commands.literal("devlist").executes((context) ->
        {
            CommandSource src = context.getSource();
            send(src, getFormattedText("----Plant Tech 2 Developers----", Color.func_240744_a_(TextFormatting.DARK_GREEN), true, true));
            send(src, getFormattedText("Artist:", Color.func_240744_a_(TextFormatting.AQUA), true, false));
            printAllFromList(ARTISTS, src);
            send(src, getFormattedText("Authors:", Color.func_240744_a_(TextFormatting.AQUA), true, false));
            printAllFromList(AUTHORS, src);
            return onlineMembersCount;
        });
    }

    private static void printAllFromList(ArrayList<String> list, CommandSource src)
    {
        for (String username : list)
        {
            StringTextComponent status = new StringTextComponent("");
            if (isOnline(src.getWorld(), username))
            {
                status = getFormattedText("(Online)", Color.func_240744_a_(TextFormatting.GREEN), false, false);
                onlineMembersCount++;
            }
            send(src, getText(username, status));
        }
    }
    private static UUID getUUID(String username)
    {
        return UUID.fromString(DEVELOPERS.get(username));
    }

    private static boolean isOnline(ServerWorld world, String username)
    {
        if (username.equals("Poopoodice(pupudice)"))
        {
            return world.getPlayerByUuid(getUUID("poopoodice")) != null || world.getPlayerByUuid(getUUID("pupudice")) != null;
        }
        if (username.equals("MoonlitLamps"))
        {
            return false;
        }
        return world.getPlayerByUuid(getUUID(username)) != null;
    }

    private static String getText(String name, StringTextComponent status)
    {
        return name + "  " + status.getText();
    }

    private static void send(CommandSource src, StringTextComponent msg)
    {
        src.sendFeedback(msg, false);
    }

    private static void send(CommandSource src, String msg)
    {
        src.sendFeedback(new StringTextComponent(msg), false);
    }

    private static StringTextComponent getFormattedText(String text, Color colour, boolean bold, boolean underline)
    {
        return (StringTextComponent) new StringTextComponent(text).func_230530_a_(Style.EMPTY.setColor(colour).setBold(bold).setUnderlined(underline));
    }
}
