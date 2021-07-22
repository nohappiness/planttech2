package net.kaneka.planttech2.fluids.capability;

public interface IBiomassFluidEnergy
{
    int getCurrentStorage();

    int getMaxStorage();

    int extractBiomass(int amount);

    void setCurrentStorage(int value);

    void setMaxStorage(int value);

    void changeCurrentStorage(int amount);

    int recieveBiomass(int amount);

    void clearStorage();

    boolean hasEnoughBiomass(int target);
}
