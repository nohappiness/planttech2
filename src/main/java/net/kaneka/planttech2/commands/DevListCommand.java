package net.kaneka.planttech2.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.google.common.collect.ImmutableList.of;

public class DevListCommand
{
    // Vanity name, [list of UUIDs]
    public static final Map<String, List<String>> AUTHORS = Map.of(
            "Kaneka", of("df361308-a4b8-469c-a3b6-63bb8b2ec7e3")
            ,"pupudice", of("f0c9774d-e79f-4a58-b524-3f3d014e58ed", "b3fd2b3b-df09-4790-8187-650219671a69")
            ,"Setrion", of("c5beef70-7f28-44c8-a1cc-91a1cc8836e6")
            ,"mthwzrd", of("d23dfef7-36a5-40aa-b851-6b8201e0c779"));
    public static final Map<String, List<String>> ARTISTS = Map.of(
            "VeyezX (Vex)", of("8ea51d9d-ccc9-4ce4-a9fd-9e8b5d1ceaf3")
            ,"BeerCav", of("bc58f1ec-9853-4dd0-a0bb-da38cfaf6fe6")
            ,"DinoSoup", of("1dc30eef-5be5-4afa-9e1e-9a5c20df653f")
            ,"MoonlitLamps", of()
            ,"andrew0030", of("0b37421b-e74e-4852-bf57-23907d295ea1")
            ,"TEMHOTAOKEAHA", of("7f82b856-345b-4b8c-a55b-6a7a0324c1b5"));

    public static LiteralArgumentBuilder<CommandSourceStack> register()
    {
        return Commands.literal("devlist").executes((context) ->
        {
            int online = 0;
            CommandSourceStack src = context.getSource();
            src.sendSuccess(new TextComponent("---- Plant Tech 2 Developers ----").withStyle(ChatFormatting.DARK_GREEN, ChatFormatting.BOLD, ChatFormatting.UNDERLINE), false);
            printTitle(src, "Artists:");
            online += printAllFromList(ARTISTS, src);
            printTitle(src, "Authors:");
            online += printAllFromList(AUTHORS, src);
            printTitle(src, "Special Thanks:");
            online += printAllFromList(AUTHORS, src, true);
            return online;
        });
    }

    private static void printTitle(CommandSourceStack source, String message)
    {
        source.sendSuccess(new TextComponent(message).withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD), false);
    }

    private static int printAllFromList(Map<String, List<String>> map, CommandSourceStack src)
    {
        return printAllFromList(map, src, false);
    }

    private static int printAllFromList(Map<String, List<String>> map, CommandSourceStack src, boolean contributionDone)
    {
        int online = 0;
        for (Map.Entry<String, List<String>> entry : map.entrySet())
        {
            TextComponent name = new TextComponent(entry.getKey());
            if (isOnline(src.getServer(), entry.getValue()))
            {
                name.append(new TextComponent(" (online)").withStyle(ChatFormatting.GREEN));
                online++;
            }
            if (contributionDone)
                name = new TextComponent("Thanks " + name + getContribution(entry.getKey()));
            src.sendSuccess(name, false);
        }
        return online;
    }

    // Returns true if any of the given UUIDs in the list is online
    private static boolean isOnline(MinecraftServer server, List<String> uuidList)
    {
        return uuidList.stream().anyMatch(uuid -> server.getPlayerList().getPlayer(UUID.fromString(uuid)) != null);
    }

    public static String getContribution(String username)
    {
        switch (username)
        {
            default:
                return "";
            case "Sciwhiz12":
                return "for greatly improving the code!";
        }
    }
}
