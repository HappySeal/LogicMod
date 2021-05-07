package com.logic.logicmod;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Logic.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Logic.MODID);


    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());

    }

    public static final RegistryObject<Item> BITIUM = ITEMS.register("bitium", () ->
            new Item(
                    new Item.Properties().tab(ItemGroup.TAB_MATERIALS)
            )
    );

    public static final RegistryObject<Block> BITIUM_ORE = BLOCKS.register("bitium_ore", () ->
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

    public static final RegistryObject<Block> BITIUM_BLOCK = BLOCKS.register("bitium_block", () ->
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


    public static final RegistryObject<BlockItem> BITIUM_ORE_ITEM = ITEMS.register("bitium_ore", () ->
            new BlockItem(
                    BITIUM_ORE.get(),
                    new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)
            )
    );

    public static final RegistryObject<BlockItem> BITIUM_BLOCK_ITEM = ITEMS.register("bitium_block", () ->
            new BlockItem(
                    BITIUM_BLOCK.get(),
                    new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)
            )
    );

}
