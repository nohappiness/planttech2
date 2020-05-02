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

    private static final HashSet<String> TALL_PLANTS = new HashSet<String>()
    {
        {
            add("mutated_lilac");
            add("mutated_rose_bush");
            add("mutated_peony");
        }
    };

    private static final HashSet<String> TALL_PLANTS_SAME = new HashSet<String>()
    {
        {

        }
    };

    public static void main(String[] args) throws IOException
    {
        for (int i=0;i<3;i++)
        {
            for (String name : PLANTS)
            {
                createForNormalFlower(i, name, true);
            }
            for (String name : TALL_PLANTS)
            {
                if (i == 1)
                {
                    createFile(name, true, true);
                    createFile(name, true, false);
                }
                else
                {
                    createForDoubleFlower(i, name, true);
                }
            }
        }
    }

    private static void createForNormalFlower(int type, String name, boolean overwrite) throws IOException
    {
        createFile(type, name, overwrite, false, false);
    }

    private static void createForDoubleFlower(int type, String name, boolean overwrite) throws IOException
    {
        createFile(type, name, overwrite, true, false);
    }

    private static void createForDoubleSameFlower(int type, String name, boolean overwrite) throws IOException
    {
        createFile(type, name, overwrite, true, true);
    }

    private static void createFile(int type, String name, boolean overwrite, boolean isDoubleBlock, boolean isDoubleTheSame) throws IOException
    {
        String directory = "src/main/resources/assets/planttech2" + (type == 0 ? "/blockstates" : type == 1 ? "/models/block/plants" : "/models/item");
        File file = new File(directory + "/" + name + ".json");
        System.out.println(directory);
        if (overwrite || !file.createNewFile())
        {
            FileWriter w = new FileWriter(file);
            WRITER = new BufferedWriter(w);
            switch (type)
            {
                case 0:
                    write("{");
                    write("  \"variants\": {");
                    if (isDoubleTheSame)
                    {
                        write("        \"is_top=true\": { \"model\": " + "\"planttech2:block/plants/" + name + "\" },");
                        write("        \"is_top=false\": { \"model\": " + "\"planttech2:block/plants/" + name + "\" }");
                    }
                    else if (isDoubleBlock)
                    {
                        write("        \"is_top=true\": { \"model\": " + "\"planttech2:block/plants/" + name + "_top\" },");
                        write("        \"is_top=false\": { \"model\": " + "\"planttech2:block/plants/" + name + "_bottom\" }");
                    }
                    else
                    {
                        write("        \"\": { \"model\": " + "\"planttech2:block/plants/" + name + "\" }");
                    }
                    write("    }");
                    write("}");
                    break;
                case 1:
                    write("{");
                    write("    \"parent\": \"block/cross\",");
                    write("    \"textures\": {");
                    if (isDoubleTheSame)
                    {
                        write("        \"cross\": \"planttech2:blocks/plants/" + name + "\"");
                    }
                    else
                    {
                        write("        \"cross\": \"planttech2:blocks/plants/" + name + "\"");
                    }
                    write("    }");
                    write("}");
                    break;
                case 2:
                    write("{");
                    write("    \"parent\": \"item/generated\",");
                    write("    \"textures\": {");
                    write("        \"layer0\": \"planttech2:blocks/plants/" + name + (isDoubleBlock ? "_top" : "") + "\"");
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

    private static void createFile(String name, boolean overwrite, boolean isTop) throws IOException
    {
        String directory = "src/main/resources/assets/planttech2/models/block/plants";
        File file = new File(directory + "/" + name + (isTop ? "_top" : "_bottom")+ ".json");
        if (overwrite || !file.createNewFile())
        {
            FileWriter w = new FileWriter(file);
            WRITER = new BufferedWriter(w);
            write("{");
            write("    \"parent\": \"block/cross\",");
            write("    \"textures\": {");
            if (isTop)
            {
                write("        \"cross\": \"planttech2:blocks/plants/" + name + "_top\"");
            }
            else
            {
                write("        \"cross\": \"planttech2:blocks/plants/" + name + "_bottom\"");
            }
            write("    }");
            write("}");
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
