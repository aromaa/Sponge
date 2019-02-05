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
package org.spongepowered.common.data.processor.data.item;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.world.LockCode;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.ImmutableLockableData;
import org.spongepowered.api.data.manipulator.mutable.LockableData;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.common.data.manipulator.mutable.tileentity.SpongeLockableData;
import org.spongepowered.common.data.processor.common.AbstractItemSingleDataProcessor;
import org.spongepowered.common.data.util.NbtDataUtil;
import org.spongepowered.common.data.value.SpongeImmutableValue;
import org.spongepowered.common.data.value.SpongeMutableValue;

import java.util.Optional;

public final class ItemLockableDataProcessor extends AbstractItemSingleDataProcessor<String, LockableData, ImmutableLockableData> {

    public ItemLockableDataProcessor() {
        super(stack -> {
            Item item = stack.getItem();
            if (!(item instanceof ItemBlock)) {
                return false;
            }
            Block block = ((ItemBlock) item).getBlock();
            if (!(block instanceof ITileEntityProvider)) {
                return false;
            }
            TileEntity tile = ((ITileEntityProvider) block).createNewTileEntity(null, item.getMetadata(stack.getItemDamage()));
            return tile instanceof TileEntityLockable;
        } , Keys.LOCK_TOKEN);
    }

    @Override
    public DataTransactionResult removeFrom(ValueContainer<?> container) {
        if (supports(container)) {
            set((ItemStack) container, "");
            return DataTransactionResult.successNoData();
        }
        return DataTransactionResult.failNoData();
    }

    @Override
    protected boolean set(ItemStack stack, String value) {
        NBTTagCompound mainCompound = NbtDataUtil.getOrCreateCompound(stack);
        NBTTagCompound tileCompound = NbtDataUtil.getOrCreateSubCompound(mainCompound, NbtDataUtil.BLOCK_ENTITY_TAG);
        LockCode code = new LockCode(value);
        if (code.isEmpty()) {
            tileCompound.removeTag("Lock");
        } else {
            code.toNBT(tileCompound);
        }
        return true;
    }

    @Override
    protected Optional<String> getVal(ItemStack container) {
        if (container.getTagCompound() == null) {
            return Optional.of("");
        }
        NBTTagCompound tileCompound = container.getTagCompound().getCompoundTag(NbtDataUtil.BLOCK_ENTITY_TAG);
        LockCode code = LockCode.fromNBT(tileCompound);
        if (code.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(code.getLock());
    }

    @Override
    protected Value.Mutable<String> constructMutableValue(String actualValue) {
        return new SpongeMutableValue<String>(Keys.LOCK_TOKEN, actualValue);
    }

    @Override
    protected Value.Immutable<String> constructImmutableValue(String value) {
        return new SpongeImmutableValue<String>(Keys.LOCK_TOKEN, value);
    }

    @Override
    protected LockableData createManipulator() {
        return new SpongeLockableData();
    }

}
