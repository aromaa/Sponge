/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.common.mixin.core.block;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.manipulator.immutable.ImmutableAttachedData;
import org.spongepowered.api.data.manipulator.immutable.ImmutableConnectedDirectionData;
import org.spongepowered.api.data.manipulator.immutable.ImmutableDisarmedData;
import org.spongepowered.api.data.manipulator.immutable.ImmutablePoweredData;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.util.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.common.data.ImmutableDataCachingUtil;
import org.spongepowered.common.data.manipulator.immutable.block.ImmutableSpongeAttachedData;
import org.spongepowered.common.data.manipulator.immutable.block.ImmutableSpongeConnectedDirectionData;
import org.spongepowered.common.data.manipulator.immutable.block.ImmutableSpongeDisarmedData;
import org.spongepowered.common.data.manipulator.immutable.block.ImmutableSpongePoweredData;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Mixin(BlockTripWire.class)
public abstract class MixinBlockTripWire extends MixinBlock {

    @Override
    public ImmutableList<ImmutableDataManipulator<?, ?>> getManipulators(IBlockState blockState) {
        return ImmutableList.<ImmutableDataManipulator<?, ?>>of(getIsDisarmedFor(blockState),
                getIsAttachedFor(blockState), getIsPoweredFor(blockState), getConnectedDirectionData(blockState));
    }

    @Override
    public boolean supports(Class<? extends ImmutableDataManipulator<?, ?>> immutable) {
        return ImmutableDisarmedData.class.isAssignableFrom(immutable)
                || ImmutableAttachedData.class.isAssignableFrom(immutable) || ImmutablePoweredData.class.isAssignableFrom(immutable)
                || ImmutableConnectedDirectionData.class.isAssignableFrom(immutable);
    }

    @Override
    public Optional<BlockState> getStateWithData(IBlockState blockState, ImmutableDataManipulator<?, ?> manipulator) {
        if (manipulator instanceof ImmutableDisarmedData) {
            final boolean disarmed = ((ImmutableDisarmedData) manipulator).disarmed().get();
            return Optional.of((BlockState) blockState.with(BlockTripWire.DISARMED, disarmed));
        }
        if (manipulator instanceof ImmutableAttachedData) {
            return Optional.of((BlockState) blockState);
        }
        if (manipulator instanceof ImmutablePoweredData) {
            return Optional.of((BlockState) blockState.with(BlockTripWire.POWERED, ((ImmutablePoweredData) manipulator).powered().get()));
        }
        if (manipulator instanceof ImmutableConnectedDirectionData) {
            return Optional.of((BlockState) blockState);
        }
        return super.getStateWithData(blockState, manipulator);
    }

    @Override
    public <E> Optional<BlockState> getStateWithValue(IBlockState blockState, Key<? extends Value<E>> key, E value) {
        if (key.equals(Keys.DISARMED)) {
            final boolean disarmed = (Boolean) value;
            return Optional.of((BlockState) blockState.with(BlockTripWire.DISARMED, disarmed));
        }
        if (key.equals(Keys.SUSPENDED)) {
            return Optional.of((BlockState) blockState);
        }
        if (key.equals(Keys.ATTACHED)) {
            return Optional.of((BlockState) blockState);
        }
        if (key.equals(Keys.POWERED)) {
            return Optional.of((BlockState) blockState.with(BlockTripWire.POWERED, (Boolean) value));
        }
        if (key.equals(Keys.CONNECTED_DIRECTIONS) || key.equals(Keys.CONNECTED_EAST) || key.equals(Keys.CONNECTED_NORTH)
                || key.equals(Keys.CONNECTED_SOUTH) || key.equals(Keys.CONNECTED_WEST)) {
            return Optional.of((BlockState) blockState);
        }
        return super.getStateWithValue(blockState, key, value);
    }

    private ImmutableDisarmedData getIsDisarmedFor(IBlockState blockState) {
        return ImmutableDataCachingUtil.getManipulator(ImmutableSpongeDisarmedData.class, blockState.get(BlockTripWire.DISARMED));
    }

    private ImmutableAttachedData getIsAttachedFor(IBlockState blockState) {
        return ImmutableDataCachingUtil.getManipulator(ImmutableSpongeAttachedData.class, blockState.get(BlockTripWire.ATTACHED));
    }

    private ImmutablePoweredData getIsPoweredFor(IBlockState blockState) {
        return ImmutableDataCachingUtil.getManipulator(ImmutableSpongePoweredData.class, blockState.get(BlockTripWire.POWERED));
    }

    private ImmutableConnectedDirectionData getConnectedDirectionData(IBlockState blockState) {
        final Set<Direction> directions = new HashSet<>();
        final Boolean north = blockState.get(BlockTripWire.NORTH);
        final Boolean east = blockState.get(BlockTripWire.EAST);
        final Boolean west = blockState.get(BlockTripWire.WEST);
        final Boolean south = blockState.get(BlockTripWire.SOUTH);
        if (north) {
            directions.add(Direction.NORTH);
        }
        if (south) {
            directions.add(Direction.SOUTH);
        }
        if (west) {
            directions.add(Direction.WEST);
        }
        if (east) {
            directions.add(Direction.EAST);
        }
        return ImmutableDataCachingUtil.getManipulator(ImmutableSpongeConnectedDirectionData.class, directions);
    }
}
