package net.kaneka.planttech2.registries;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ModBackgroundMusic
{
//    public static final BackgroundMusicSelector SAFE_BIOME = BackgroundMusicTracks.createGameMusic(SoundEvents.MUSIC_GAME);
//    public static final BackgroundMusicSelector ADVENTURE_BIOME = BackgroundMusicTracks.createGameMusic(ModSounds.MUSIC_ADVENTURE);
//    public static final BackgroundMusicSelector DAGEROUS_BIOME = BackgroundMusicTracks.createGameMusic(SoundEvents.MUSIC_GAME);

//    private static int MUSIC_CD = 0;
//    private static ISound MUSIC;
//    @SubscribeEvent
//    public static void onClientTick(TickEvent.ClientTickEvent event)
//    {
//        Minecraft minecraft = Minecraft.getInstance();
//        PlayerEntity player = minecraft.player;
//        if (MUSIC_CD > 0)
//            MUSIC_CD--;
//        if (player != null && event.phase == TickEvent.Phase.END)
//        {
//            if (player.level.dimension().getRegistryName().equals(ModDimensions.PLANTTOPIA_DIMENSION.getRegistryName()))
//            {
//                if (MUSIC_CD <= 0)
//                {
//                    SoundEvent sound = null;
//                    if (player.level.getBiome(player.blockPosition()) == ModBiomes.DARK_WETLANDS.getValue().get())
//                        sound = ModSounds.MUSIC_ADVENTURE;
//                    MUSIC = sound == null ? null : SimpleSound.forMusic(sound);
//                    if (MUSIC != null)
//                        minecraft.getSoundManager().play(MUSIC);
//                }
//            }
//            else
//                minecraft.getSoundManager().stop(MUSIC);
//            if (MUSIC != null && !minecraft.getSoundManager().isActive(MUSIC))
//                MUSIC = null;
//        }
//    }

    @SubscribeEvent
    public static void onSoundPlay(PlaySoundEvent event)
    {
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity player = minecraft.player;
        if (player != null && event.getSound().getSource() == SoundCategory.MUSIC)
        {
            World world = player.level;
            if (world.dimension().getRegistryName().equals(ModDimensions.PLANTTOPIA_DIMENSION.getRegistryName()))
            {
                SoundEvent sound = ModBiomes.getMusicFor(world.getBiome(player.blockPosition()));
                if (sound != null)
                    event.setResultSound(SimpleSound.forMusic(sound));
            }
        }
    }

//    public enum MusicType
//    {
//        PEACEFUL(),
//        INTENSE(SoundE), // Adventure
//        DANGEROUS();
//
//        HashSet<Supplier<SoundEvent>> sounds = new HashSet<>();
//
//        MusicType(Supplier<SoundEvent>... sounds)
//        {
//            this.sounds.addAll(Arrays.asList(sounds));
//        }
//    }
}
