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

import io.github.sleeplessdevelopment.oopsallinfested.OopsAllInfested;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.InfestedBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.loot.context.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@Mixin(Block.class)
abstract class BlockMixin implements ItemConvertible {
  private BlockMixin() {
    throw new AssertionError();
  }

  @Inject(
    method = "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/loot/context/LootContext$Builder;)V",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;onStacksDropped(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)V", shift = Shift.AFTER),
    locals = LocalCapture.CAPTURE_FAILHARD)
  private static void spawnSilverfish(final BlockState state, final LootContext.Builder builder, final CallbackInfo ci, final ServerWorld world, final BlockPos pos) {
    if (!InfestedBlock.isInfestable(state)) OopsAllInfested.spawn(world, pos, ItemStack.EMPTY);
  }

  @Inject(
    method = "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;onStacksDropped(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)V", shift = Shift.AFTER))
  private static void spawnSilverfish(final BlockState state, final World world, final BlockPos pos, final CallbackInfo ci) {
    if (!InfestedBlock.isInfestable(state)) OopsAllInfested.spawn(world, pos, ItemStack.EMPTY);
  }

  @Inject(
    method = "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;)V",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;onStacksDropped(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)V", shift = Shift.AFTER))
  private static void spawnSilverfish(final BlockState state, final World world, final BlockPos pos, @Nullable final BlockEntity blockEntity, final CallbackInfo ci) {
    if (!InfestedBlock.isInfestable(state)) OopsAllInfested.spawn(world, pos, ItemStack.EMPTY);
  }

  @Inject(
    method = "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;onStacksDropped(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)V", shift = Shift.AFTER))
  private static void spawnSilverfish(final BlockState state, final World world, final BlockPos pos, @Nullable final BlockEntity blockEntity, final Entity entity, final ItemStack stack, final CallbackInfo ci) {
    if (!InfestedBlock.isInfestable(state)) OopsAllInfested.spawn(world, pos, stack);
  }
}
