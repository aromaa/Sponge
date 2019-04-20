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
package org.spongepowered.common.data.processor.value.entity;

import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.common.data.processor.common.AbstractSpongeValueProcessor;
import org.spongepowered.common.data.value.SpongeImmutableValue;
import org.spongepowered.common.data.value.SpongeMutableValue;
import org.spongepowered.common.interfaces.entity.IMixinSkinnable;

import java.util.Optional;

public class UpdateGameProfileValueProcessor extends AbstractSpongeValueProcessor<IMixinSkinnable, Boolean> {

    public UpdateGameProfileValueProcessor() {
        super(IMixinSkinnable.class, Keys.UPDATE_GAME_PROFILE);
    }

    @Override
    protected Value.Mutable<Boolean> constructMutableValue(Boolean actualValue) {
        return new SpongeMutableValue<>(Keys.UPDATE_GAME_PROFILE, actualValue);
    }

    @Override
    protected boolean set(IMixinSkinnable container, Boolean value) {
        container.setUpdateGameProfile(value);
        return true;
    }

    @Override
    protected Optional<Boolean> getVal(IMixinSkinnable container) {
        return Optional.of(container.shouldUpdateGameProfile());
    }

    @Override
    protected Value.Immutable<Boolean> constructImmutableValue(Boolean value) {
        return SpongeImmutableValue.cachedOf(Keys.UPDATE_GAME_PROFILE, value);
    }

    @Override
    public DataTransactionResult removeFrom(ValueContainer<?> container) {
        return DataTransactionResult.failNoData();
    }
}