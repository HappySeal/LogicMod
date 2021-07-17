package com.logic.logicmod.setup;

import com.logic.logicmod.block.CircuitTable.GateBuilderBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> material) {
        return RegistryHandler.BLOCKS.register(name, material);
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerBlock(name, block);
        RegistryHandler.ITEMS.register(name, () ->
                new BlockItem(
                        ret.get(),
                        new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)
                )
        );
        return ret;
    }


    public static final RegistryObject<GateBuilderBlock> GATE_BUILDER = register("gate_builder", () ->
            new GateBuilderBlock(
                    AbstractBlock.Properties
                        .of(Material.METAL)
                        .strength(4.0f, 3.0f)
                        .harvestLevel(1)
                        .sound(SoundType.METAL)
                        .harvestTool(ToolType.PICKAXE)
            )
    );

    public static final RegistryObject<Block> BITIUM_ORE = register("bitium_ore", () ->
            new Block(
                    AbstractBlock.Properties
                            .of(Material.STONE)
                            .strength(4.0f, 3.0f)
                            .harvestLevel(2)
                            .sound(SoundType.STONE)
                            .harvestTool(ToolType.PICKAXE)
                            .requiresCorrectToolForDrops()
            )
    );

    public static final RegistryObject<Block> BITIUM_BLOCK = register("bitium_block", () ->
            new Block(
                    AbstractBlock.Properties
                            .of(Material.STONE)
                            .strength(4.0f, 3.0f)
                            .harvestLevel(2)
                            .sound(SoundType.STONE)
                            .harvestTool(ToolType.PICKAXE)
                            .requiresCorrectToolForDrops()
            )
    );


    public static void register() {
    }
}
