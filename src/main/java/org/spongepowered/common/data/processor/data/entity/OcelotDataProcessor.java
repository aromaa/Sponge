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

import net.minecraft.entity.passive.EntityOcelot;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.ImmutableOcelotData;
import org.spongepowered.api.data.manipulator.mutable.OcelotData;
import org.spongepowered.api.data.type.OcelotType;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.common.data.manipulator.mutable.entity.SpongeOcelotData;
import org.spongepowered.common.data.processor.common.AbstractEntitySingleDataProcessor;
import org.spongepowered.common.data.value.SpongeImmutableValue;
import org.spongepowered.common.data.value.SpongeMutableValue;
import org.spongepowered.common.entity.SpongeOcelotType;
import org.spongepowered.common.registry.type.entity.OcelotTypeRegistryModule;

import java.util.Optional;

public class OcelotDataProcessor extends
        AbstractEntitySingleDataProcessor<EntityOcelot, OcelotType, OcelotData, ImmutableOcelotData> {

    public OcelotDataProcessor() {
        super(EntityOcelot.class, Keys.OCELOT_TYPE);
    }

    @Override
    protected Value.Mutable<OcelotType> constructMutableValue(OcelotType actualValue) {
        return new SpongeMutableValue<>(Keys.OCELOT_TYPE, actualValue);
    }

    @Override
    protected boolean set(EntityOcelot entity, OcelotType value) {
        if (value instanceof SpongeOcelotType) {
            entity.setTameSkin(((SpongeOcelotType) value).type);
            return true;
        }
        return false;
    }

    @Override
    protected Optional<OcelotType> getVal(EntityOcelot entity) {
        return Optional.ofNullable(OcelotTypeRegistryModule.OCELOT_IDMAP.get(entity.getTameSkin()));
    }

    @Override
    protected Value.Immutable<OcelotType> constructImmutableValue(OcelotType value) {
        return SpongeImmutableValue.cachedOf(Keys.OCELOT_TYPE, value);
    }

    @Override
    protected OcelotData createManipulator() {
        return new SpongeOcelotData();
    }

    @Override
    public DataTransactionResult removeFrom(ValueContainer<?> container) {
        return DataTransactionResult.failNoData();
    }
}
