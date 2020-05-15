package net.kaneka.planttech2.entities.capabilities.player;

public interface IPlayerRenderRGB
{
    float[] getRGB();

    float getCurrentRed();

    float getCurrentGreen();

    float getCurrentBlue();

    float getCurrentFogDensity();

    void setRGB(float[] rgb);

    void setRGB(float r, float g, float b);

    void setCurrentRed(float value);

    void changeCurrentRed(float amount);

    void setCurrentGreen(float value);

    void changeCurrentGreen(float amount);

    void setCurrentBlue(float value);

    void changeCurrentBlue(float amount);

    void setCurrentFogDensity(float value);
}
