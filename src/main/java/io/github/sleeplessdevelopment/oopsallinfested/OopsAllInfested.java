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

package io.github.sleeplessdevelopment.oopsallinfested;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@ParametersAreNonnullByDefault
public final class OopsAllInfested {
  private OopsAllInfested() {
    throw new UnsupportedOperationException();
  }

  public static void spawn(final World world, final BlockPos pos, final ItemStack stack) {
    if (!world.isClient && world.getGameRules().getBoolean("doTileDrops")) {
      if (0 == EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack)) {
        final SilverfishEntity entity = EntityType.SILVERFISH.create(world);
        Objects.requireNonNull(entity, "EntityType.SILVERFISH.create(world)");
        final double x = (double) pos.getX() + 0.5;
        final double y = (double) pos.getY();
        final double z = (double) pos.getZ() + 0.5;
        entity.setPositionAndAngles(x, y, z, 0.0F, 0.0F);
        world.spawnEntity(entity);
        entity.playSpawnEffects();
      }
    }
  }
}
