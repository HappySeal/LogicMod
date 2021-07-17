package com.logic.logicmod.data.client;

import com.google.common.collect.ImmutableList;
import com.logic.logicmod.setup.ModBlocks;
import com.logic.logicmod.setup.ModItems;
import com.logic.logicmod.setup.RegistryHandler;
import com.mojang.datafixers.util.Pair;
import jdk.nashorn.internal.ir.annotations.Immutable;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator generator) {
        super(generator);
    }


    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(
                Pair.of(ModBlockLootTable::new, LootParameterSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((a, b) -> LootTableManager.validate(validationtracker, a, b));
    }

    public static class ModBlockLootTable extends BlockLootTables {
        @Override
        protected Iterable<Block> getKnownBlocks() {
            return RegistryHandler.BLOCKS.getEntries().stream()
                    .map(RegistryObject::get)
                    .collect(Collectors.toList());
        }

        @Override
        protected void addTables() {
            dropSelf(ModBlocks.BITIUM_BLOCK.get());
            dropSelf(ModBlocks.GATE_BUILDER.get());

            addOre(ModBlocks.BITIUM_ORE.get(), ModItems.BITIUM.get(), 4f, 8f, Enchantments.BLOCK_FORTUNE);
        }



        private void addOre(Block ore, Item item, float rangeLow, float rangeHigh, Enchantment enchantment) {
            add(ore, (p) -> createSilkTouchDispatchTable(p,
                    applyExplosionDecay(p, ItemLootEntry.lootTableItem(item))
                            .apply(SetCount.setCount(RandomValueRange.between(rangeLow, rangeHigh)))
                            .apply(ApplyBonus.addOreBonusCount(enchantment))
            ));
        }
    }

}
