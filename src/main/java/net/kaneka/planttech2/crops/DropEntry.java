package net.kaneka.planttech2.crops;

import com.google.gson.*;
import net.kaneka.planttech2.utilities.ISerializable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

import static net.minecraftforge.registries.ForgeRegistries.ITEMS;

public class DropEntry implements ISerializable
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

    @Override
    public CompoundTag save()
    {
        CompoundTag compound = new CompoundTag();
        compound.putInt("min", min);
        compound.putInt("max", max);
        compound.putString("drop", drop.get().getRegistryName().toString());
        return compound;
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

    public static DropEntry of(CompoundTag compound)
    {
        return new DropEntry(() -> ITEMS.getValue(new ResourceLocation(compound.getString("drop"))), compound.getInt("min"), compound.getInt("max"));
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
                itemLocation = new ResourceLocation(json.getAsJsonArray().get(0).getAsString());
            } else if (json.isJsonObject())
            {
                JsonObject obj = json.getAsJsonObject();
                itemLocation = new ResourceLocation(obj.get( "item").getAsString());
                if (obj.has("min"))
                {
                    min = obj.get("min").getAsInt();
                }
                if (obj.has("max"))
                {
                    max = obj.get("max").getAsInt();
                }
            } else
            {
                throw new JsonSyntaxException("Expected either a string or an object, got " /*+ GsonHelper.getType(json)*/);
            }
            if (min < 0) { throw new JsonSyntaxException("min has a negative value"); }
            if (max < 0) { throw new JsonSyntaxException("max has a negative value"); }
            if (min > max) { throw new JsonSyntaxException("min is bigger than max"); }

            return DropEntry.of(RegistryEntrySupplier.of(itemLocation, ITEMS), min, max);
        }

        public void write(DropEntry entry, FriendlyByteBuf buffer)
        {
            buffer.writeResourceLocation(entry.getItem().get().getRegistryName());
            buffer.writeInt(entry.getMin());
            buffer.writeInt(entry.getMax());
        }

        public DropEntry read(FriendlyByteBuf buffer)
        {
            final ResourceLocation itemLoc = buffer.readResourceLocation();
            final int min = buffer.readInt();
            final int max = buffer.readInt();
            return DropEntry.of(RegistryEntrySupplier.of(itemLoc, ITEMS), min, max);
        }
    }
}
