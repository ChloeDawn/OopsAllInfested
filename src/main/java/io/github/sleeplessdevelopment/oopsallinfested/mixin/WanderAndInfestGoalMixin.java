/*
 * Copyright (C) 2019 InsomniaKitten
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.sleeplessdevelopment.oopsallinfested.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.InfestedBlock;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@Mixin(targets = "net/minecraft/entity/mob/SilverfishEntity$WanderAndInfestGoal")
abstract class WanderAndInfestGoalMixin extends WanderAroundGoal {
  @Shadow private boolean canInfest;

  private WanderAndInfestGoalMixin() {
    super(null, 0.0, 0);
    throw new AssertionError();
  }

  @Inject(
    method = "canStart",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/block/InfestedBlock;isInfestable(Lnet/minecraft/block/BlockState;)Z"),
    locals = LocalCapture.CAPTURE_FAILHARD,
    cancellable = true)
  private void forceStart(final CallbackInfoReturnable<Boolean> cir, final Random random, final BlockPos pos, final BlockState state) {
    if (!InfestedBlock.isInfestable(state)) {
      this.canInfest = true;
      cir.setReturnValue(true);
    }
  }

  @Inject(
    method = "start",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/block/InfestedBlock;isInfestable(Lnet/minecraft/block/BlockState;)Z"),
    locals = LocalCapture.CAPTURE_FAILHARD,
    cancellable = true)
  private void simulateInfestation(final CallbackInfo ci, final IWorld world, final BlockPos pos, final BlockState state) {
    if (!InfestedBlock.isInfestable(state)) {
      this.mob.playSpawnEffects();
      this.mob.remove();
      ci.cancel();
    }
  }
}
