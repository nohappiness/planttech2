package net.kaneka.planttech2.filehelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashSet;

public class PlantJSONGenerator
{
    private static BufferedWriter WRITER;
    private static final HashSet<String> PLANTS = new HashSet<String>()
    {
        {
            add("mutated_dandelion");
            add("mutated_poppy");
            add("mutated_blue_orchid");
            add("mutated_allium");
            add("mutated_azure_bluet");
            add("mutated_red_tulip");
            add("mutated_orange_tulip");
            add("mutated_white_tulip");
            add("mutated_pink_tulip");
            add("mutated_oxeye_daisy");
            add("mutated_cornflower");
            add("mutated_lily_of_the_valley");
        }
    };

    public static void main(String[] args) throws IOException
    {
        for (int i=0;i<3;i++)
        {
            for (String name: PLANTS)
            {
                createFile(i, name,true);
            }
        }
    }

    private static void createFile(int type, String name, boolean overwrite) throws IOException
    {
        String directory = "src/main/resources/assets/planttech2" + (type == 0 ? "/blockstates" : type == 1 ? "/models/block/plants" : "/models/item");
        File file = new File(directory + "/" + name + ".json");
        if (overwrite || !file.createNewFile())
        {
            FileWriter w = new FileWriter(file);
            WRITER = new BufferedWriter(w);
            switch (type)
            {
                case 0:
                    write("{");
                    write("  \"variants\": {");
                    write("        \"\": { \"model\": " + "\"planttech2:block/plants/" + name + "\" }");
                    write("    }");
                    write("}");
                    break;
                case 1:
                    write("{");
                    write("    \"parent\": \"block/cross\",");
                    write("    \"textures\": {");
                    write("        \"cross\": \"planttech2:blocks/plants/" + name + "\"");
                    write("    }");
                    write("}");
                    break;
                case 2:
                    write("{");
                    write("    \"parent\": \"item/generated\",");
                    write("    \"textures\": {");
                    write("        \"layer0\": \"planttech2:blocks/plants/" + name + "\"");
                    write("    }");
                    write("}");
                default:
                    break;
            }
            WRITER.close();
            w.close();
        }
        else
        {
            throw new FileAlreadyExistsException("File " + name + "already exist in " + directory);
        }
    }

    private static void write(String str) throws IOException
    {
        WRITER.write(str);
        WRITER.newLine();
    }
}
