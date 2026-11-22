package com.sixeyes.morexplore;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
    public static final SoundEvent BLOCK_CHEST_CREAK = registerSound("chest_creak");

    private static SoundEvent registerSound(String id) {
        Identifier identifier = Morexplore.id(id);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier));
    }

    public static void initialize() {
        Morexplore.LOGGER.info("Registering " + Morexplore.MOD_ID + " Sounds");
    }
}
