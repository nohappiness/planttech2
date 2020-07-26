package net.kaneka.planttech2.entities.capabilities.player;

public interface IRadiationEffect
{
    void setLevel(float level);

    float getLevel();

    void changeLevel(float amount);
}
