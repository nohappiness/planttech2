package net.kaneka.planttech2.crops;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

import static net.minecraftforge.registries.ForgeRegistries.ITEMS;

public class DropEntry
{
    public static final DropEntry EMPTY = DropEntry.of(() -> Items.AIR, 0, 0);

    private final int min, max;
    private final Supplier<Item> drop;

    private DropEntry(Supplier<Item> item, int min, int max)
    {
        this.drop = item;
        this.min = min;
        this.max = max;
    }

    public int getAmountDropped(int traitBase, int traitMax, Random rand)
    {
        int minAmount = Math.max((int) (((float) traitBase / (float) traitMax) * this.max * 0.75F), this.min);
        int maxAmount = Math.max(1, Math.min((int) (((float) traitBase / (float) traitMax) * this.max * 1.5F), this.max));
        int range = maxAmount - minAmount;
        return rand.nextInt(range + 1) + minAmount;
    }

    public ItemStack getDroppedStack(int traitBase, int traitMax, Random rand)
    {
        int amountDropped = getAmountDropped(traitBase, traitMax, rand);
        if (amountDropped > 0)
        {
            return new ItemStack(drop.get(), amountDropped);
        }
        return ItemStack.EMPTY;
    }

    public int getMin()
    {
        return this.min;
    }

    public int getMax()
    {
        return this.max;
    }

    public Supplier<Item> getItem()
    {
        return drop;
    }

    public ItemStack getItemStack()
    {
        return new ItemStack(getItem().get());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DropEntry entry = (DropEntry) o;
        return min == entry.min &&
                max == entry.max &&
                drop.equals(entry.drop);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(min, max, drop);
    }

    public static DropEntry of(Supplier<Item> item, int min, int max)
    {
        return new DropEntry(item, min, max);
    }

    public static class Serializer implements JsonSerializer<DropEntry>, JsonDeserializer<DropEntry>
    {
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {}

        @Override
        public JsonElement serialize(DropEntry entry, Type typeOfSrc, JsonSerializationContext context)
        {
            JsonObject obj = new JsonObject();
            obj.addProperty("item", entry.getItem().get().getRegistryName().toString());
            obj.addProperty("min", entry.getMin());
            obj.addProperty("max", entry.getMax());
            return obj;
        }

        @Override
        public DropEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            final ResourceLocation itemLocation;
            int min = 1, max = 1;
            if (json.isJsonPrimitive())
            {
                itemLocation = new ResourceLocation(JSONUtils.getString(json, "drops"));
            } else if (json.isJsonObject())
            {
                JsonObject obj = json.getAsJsonObject();
                itemLocation = new ResourceLocation(JSONUtils.getString(obj, "item"));
                if (obj.has("min"))
                {
                    min = JSONUtils.getInt(obj, "min");
                }
                if (obj.has("max"))
                {
                    max = JSONUtils.getInt(obj, "max");
                }
            } else
            {
                throw new JsonSyntaxException("Expected either a string or an object, got " + JSONUtils.toString(json));
            }
            if (min < 0) { throw new JsonSyntaxException("min has a negative value"); }
            if (max < 0) { throw new JsonSyntaxException("max has a negative value"); }
            if (min > max) { throw new JsonSyntaxException("min is bigger than max"); }

            return DropEntry.of(new SeedObjectSupplier<>(itemLocation, ITEMS), min, max);
        }

        public void write(DropEntry entry, PacketBuffer buffer)
        {
            buffer.writeResourceLocation(entry.getItem().get().getRegistryName());
            buffer.writeInt(entry.getMin());
            buffer.writeInt(entry.getMax());
        }

        public DropEntry read(PacketBuffer buffer)
        {
            final ResourceLocation itemLoc = buffer.readResourceLocation();
            final int min = buffer.readInt();
            final int max = buffer.readInt();
            return DropEntry.of(new SeedObjectSupplier<>(itemLoc, ITEMS), min, max);
        }
    }
}
