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
package org.spongepowered.common.data.manipulator.mutable.entity;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.ImmutableHealthScalingData;
import org.spongepowered.api.data.manipulator.mutable.HealthScalingData;
import org.spongepowered.api.data.value.BoundedValue;
import org.spongepowered.common.data.manipulator.immutable.entity.ImmutableSpongeHealthScalingData;
import org.spongepowered.common.data.manipulator.mutable.common.AbstractSingleData;
import org.spongepowered.common.data.value.SpongeValueFactory;

public class SpongeHealthScaleData extends AbstractSingleData<Double, HealthScalingData, ImmutableHealthScalingData> implements HealthScalingData {

    public SpongeHealthScaleData(double value) {
        super(HealthScalingData.class, value, Keys.HEALTH_SCALE);
    }

    public SpongeHealthScaleData() {
        this(20);
    }

    @Override
    public BoundedValue.Mutable<Double> healthScale() {
        return SpongeValueFactory.boundedBuilder(Keys.HEALTH_SCALE)
                .value(this.getValue())
                .minimum(1D)
                .maximum((double) Float.MAX_VALUE)
                .build();
    }

    @Override
    protected BoundedValue.Mutable<Double> getValueGetter() {
        return healthScale();
    }

    @Override
    public HealthScalingData copy() {
        return new SpongeHealthScaleData(this.getValue());
    }

    @Override
    public ImmutableHealthScalingData asImmutable() {
        return new ImmutableSpongeHealthScalingData(this.getValue());
    }
}
