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
package org.spongepowered.common.data.manipulator.mutable.block;

import static org.spongepowered.common.data.util.ComparatorUtil.intComparator;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.ImmutableRedstonePoweredData;
import org.spongepowered.api.data.manipulator.mutable.RedstonePoweredData;
import org.spongepowered.api.data.value.BoundedValue;
import org.spongepowered.common.data.manipulator.immutable.block.ImmutableSpongeRedstonePoweredData;
import org.spongepowered.common.data.manipulator.mutable.common.AbstractBoundedComparableData;

public class SpongeRedstonePoweredData extends AbstractBoundedComparableData<Integer, RedstonePoweredData, ImmutableRedstonePoweredData>
    implements RedstonePoweredData {

    public SpongeRedstonePoweredData() {
        this(0);
    }

    public SpongeRedstonePoweredData(int value) {
        this(value, 0, 15);
    }

    public SpongeRedstonePoweredData(int value, int minimum, int maximum) {
        super(RedstonePoweredData.class, value, Keys.POWER, intComparator(), ImmutableSpongeRedstonePoweredData.class, minimum, maximum, 0);
    }

    @Override
    public BoundedValue.Mutable<Integer> power() {
        return getValueGetter();
    }
}
