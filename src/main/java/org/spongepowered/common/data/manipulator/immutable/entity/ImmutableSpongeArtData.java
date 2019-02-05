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
package org.spongepowered.common.data.manipulator.immutable.entity;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.ImmutableArtData;
import org.spongepowered.api.data.manipulator.mutable.ArtData;
import org.spongepowered.api.data.type.Art;
import org.spongepowered.common.data.manipulator.immutable.common.AbstractImmutableSingleCatalogData;
import org.spongepowered.common.data.manipulator.mutable.entity.SpongeArtData;
import org.spongepowered.common.data.util.DataConstants;

public class ImmutableSpongeArtData extends AbstractImmutableSingleCatalogData<Art, ImmutableArtData, ArtData> implements ImmutableArtData {

    public ImmutableSpongeArtData() {
        this(DataConstants.Catalog.DEFAULT_ART);
    }

    public ImmutableSpongeArtData(Art value) {
        super(ImmutableArtData.class, value, DataConstants.Catalog.DEFAULT_ART, Keys.ART, SpongeArtData.class);
    }

}
