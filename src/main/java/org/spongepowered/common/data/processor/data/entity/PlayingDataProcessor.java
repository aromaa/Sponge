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
package org.spongepowered.common.data.processor.data.entity;

import net.minecraft.entity.passive.EntityVillager;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.ImmutablePlayingData;
import org.spongepowered.api.data.manipulator.mutable.PlayingData;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.common.data.manipulator.mutable.entity.SpongePlayingData;
import org.spongepowered.common.data.processor.common.AbstractEntitySingleDataProcessor;
import org.spongepowered.common.data.value.SpongeImmutableValue;
import org.spongepowered.common.data.value.SpongeMutableValue;

import java.util.Optional;

public class PlayingDataProcessor
        extends AbstractEntitySingleDataProcessor<EntityVillager, Boolean, PlayingData, ImmutablePlayingData> {

    public PlayingDataProcessor() {
        super(EntityVillager.class, Keys.IS_PLAYING);
    }

    @Override
    protected boolean set(EntityVillager entity, Boolean value) {
        entity.setPlaying(value);
        return true;
    }

    @Override
    protected Optional<Boolean> getVal(EntityVillager entity) {
        return Optional.of(entity.isPlaying());
    }

    @Override
    protected Value.Mutable<Boolean> constructMutableValue(Boolean value) {
        return new SpongeMutableValue<>(this.key, value);
    }

    @Override
    protected Value.Immutable<Boolean> constructImmutableValue(Boolean value) {
        return SpongeImmutableValue.cachedOf(this.key, value);
    }

    @Override
    protected PlayingData createManipulator() {
        return new SpongePlayingData();
    }

    @Override
    public DataTransactionResult removeFrom(ValueContainer<?> container) {
        return DataTransactionResult.failNoData();
    }

}
