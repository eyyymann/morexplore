package com.sixeyes.morexplore.mixin;

import com.sixeyes.morexplore.ModSounds;
import com.sixeyes.morexplore.Morexplore;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ChestBlock.class)
public class ChestBlockMixin {
    @Inject(at = @At("TAIL"), method = "<clinit>")
    private static void staticInjection(CallbackInfo info) {
        PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, entity) -> {
            if (level instanceof ServerLevel serverLevel) {
                List<BlockPos> OFFSETS = BlockPos.betweenClosedStream(-2, -2, -2, 2, 2, 2).map(BlockPos::immutable).toList();

                for (BlockPos offset : OFFSETS) {
                    BlockPos posOffset = pos.offset(offset.getX(), offset.getY(), offset.getZ());
                    if (level.getBlockState(posOffset).is(Blocks.CHEST) && !level.getBlockState(posOffset.above()).isAir()) {
                        Morexplore.LOGGER.warn(pos.toString() + " " + posOffset.toString() + String.valueOf(posOffset.distManhattan(pos)));
                        float adjustedVolume = 0.25f + (0.5f / posOffset.distManhattan(pos));
                        float adjustedPitch = 0.5f + (0.25f - (0.25f / posOffset.distManhattan(pos)));
                        level.playSound(null, posOffset, ModSounds.BLOCK_CHEST_CREAK, SoundSource.BLOCKS, adjustedVolume, adjustedPitch);
                    }
                }
            }
        });
    }
}
