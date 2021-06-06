package net.kaneka.planttech2.crops;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.function.BiPredicate;

import com.google.gson.*;
import net.kaneka.planttech2.utilities.ISerializable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;

public class ParentPair implements BiPredicate<String, String>, ISerializable
{
    private final String parent1;
    private final String parent2;
    private final float mutationChance;

    private ParentPair(String parent1, String parent2, float mutationChance)
    {
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.mutationChance = mutationChance;
    }

    @Override
    public CompoundNBT write()
    {
        CompoundNBT compound = new CompoundNBT();
        compound.putString("parent1", parent1);
        compound.putString("parent2", parent2);
        compound.putFloat("mutation", mutationChance);
        return compound;
    }

    @Override
    public boolean test(String parent1, String parent2)
    {
        return (this.parent1.equals(parent1) && this.parent2.equals(parent2)) || (this.parent1.equals(parent2) && this.parent2.equals(parent1));
    }

    public String getParent(int id)
    {
        switch (id)
        {
            case 0:
                return this.parent1;
            case 1:
                return this.parent2;
        }
        return "";
    }

    public String getFirstParent()
    {
        return this.parent1;
    }

    public String getSecondParent()
    {
        return this.parent2;
    }

    public float getMutationChance()
    {
        return mutationChance;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentPair that = (ParentPair) o;
        return Float.compare(that.mutationChance, mutationChance) == 0 &&
                parent1.equals(that.parent1) &&
                parent2.equals(that.parent2);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(parent1, parent2, mutationChance);
    }

    @Override
    public String toString()
    {
        return "ParentPair{" +
                "parent1='" + parent1 + '\'' +
                ", parent2='" + parent2 + '\'' +
                ", mutationChance=" + mutationChance +
                '}';
    }

    public static ParentPair of(CompoundNBT compound)
    {
        return new ParentPair(compound.getString("parent1"), compound.getString("parent2"), compound.getFloat("mutation"));
    }

    public static ParentPair of(String firstParent, String secondParent, float mutationChance)
    {
        return new ParentPair(firstParent, secondParent, mutationChance);
    }

    public static class Serializer implements JsonSerializer<ParentPair>, JsonDeserializer<ParentPair>
    {
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {}

        @Override
        public JsonElement serialize(ParentPair src, Type typeOfSrc, JsonSerializationContext context)
        {
            JsonObject obj = new JsonObject();
            obj.addProperty("parent_1", src.getFirstParent());
            obj.addProperty("parent_2", src.getSecondParent());
            obj.addProperty("chance", src.getMutationChance());
            return obj;
        }

        @Override
        public ParentPair deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            JsonObject obj = json.getAsJsonObject();
            String parent1 = JSONUtils.getAsString(obj, "parent_1");
            String parent2 = JSONUtils.getAsString(obj, "parent_2");
            float mutateChance = JSONUtils.getAsFloat(obj, "chance");
            return new ParentPair(parent1, parent2, mutateChance);
        }

        public ParentPair read(PacketBuffer buf)
        {
            String parent1 = buf.readUtf();
            String parent2 = buf.readUtf();
            float chance = buf.readFloat();
            return ParentPair.of(parent1, parent2, chance);
        }

        public void write(ParentPair pair, PacketBuffer buf)
        {
            buf.writeUtf(pair.getFirstParent());
            buf.writeUtf(pair.getSecondParent());
            buf.writeFloat(pair.getMutationChance());
        }
    }
}
