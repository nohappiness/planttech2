package net.kaneka.planttech2.commands;

import com.google.common.collect.ImmutableMap;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.google.common.collect.ImmutableList.of;
import static net.minecraft.util.text.TextFormatting.*;

public class DevListCommand
{
    // Vanity name, [list of UUIDs]
    public static final Map<String, List<String>> AUTHORS = new ImmutableMap.Builder<String, List<String>>()
            .put("Kaneka", of("df361308-a4b8-469c-a3b6-63bb8b2ec7e3"))
            .put("Poopoodice", of("f0c9774d-e79f-4a58-b524-3f3d014e58ed", "b3fd2b3b-df09-4790-8187-650219671a69"))
            .put("Setrion", of("c5beef70-7f28-44c8-a1cc-91a1cc8836e6"))
            .build();
    public static final Map<String, List<String>> ARTISTS = new ImmutableMap.Builder<String, List<String>>()
            .put("VeyezX (Vex)", of("8ea51d9d-ccc9-4ce4-a9fd-9e8b5d1ceaf3"))
            .put("BeerCav", of("bc58f1ec-9853-4dd0-a0bb-da38cfaf6fe6"))
            .put("DinoSoup", of("1dc30eef-5be5-4afa-9e1e-9a5c20df653f"))
            .put("MoonlitLamps", of())
            .put("andrew0030", of("0b37421b-e74e-4852-bf57-23907d295ea1"))
            .build();
    public static final Map<String, List<String>> SPECIAL_THANKS = new ImmutableMap.Builder<String, List<String>>()
        .put("Sciwhiz12", of())
        .build();

    public static LiteralArgumentBuilder<CommandSource> register()
    {
        return Commands.literal("devlist").executes((context) ->
        {
            int online = 0;
            CommandSource src = context.getSource();
            src.sendFeedback(new StringTextComponent("---- Plant Tech 2 Developers ----").mergeStyle(DARK_GREEN, BOLD, UNDERLINE), false);
            printTitle(src, "Artists:");
            online += printAllFromList(ARTISTS, src);
            printTitle(src, "Authors:");
            online += printAllFromList(AUTHORS, src);
            printTitle(src, "Special Thanks:");
            online += printAllFromList(AUTHORS, src, true);
            return online;
        });
    }

    private static void printTitle(CommandSource source, String message)
    {
        source.sendFeedback(new StringTextComponent(message).mergeStyle(AQUA, BOLD), false);
    }

    private static int printAllFromList(Map<String, List<String>> map, CommandSource src)
    {
        return printAllFromList(map, src, false);
    }

    private static int printAllFromList(Map<String, List<String>> map, CommandSource src, boolean contributionDone)
    {
        int online = 0;
        for (Map.Entry<String, List<String>> entry : map.entrySet())
        {
            StringTextComponent name = new StringTextComponent(entry.getKey());
            if (isOnline(src.getServer(), entry.getValue()))
            {
                name.append(new StringTextComponent(" (online)").mergeStyle(GREEN));
                online++;
            }
            if (contributionDone)
                name = new StringTextComponent("Thanks " + name + getContribution(entry.getKey()));
            src.sendFeedback(name, false);
        }
        return online;
    }

    // Returns true if any of the given UUIDs in the list is online
    private static boolean isOnline(MinecraftServer server, List<String> uuidList)
    {
        return uuidList.stream().anyMatch(uuid -> server.getPlayerList().getPlayerByUUID(UUID.fromString(uuid)) != null);
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
