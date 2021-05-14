package com.logic.logicmod.setup;

import com.logic.logicmod.Logic;
import net.minecraft.block.Block;

import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModTags {
    public static final class Blocks {
        public static final ITag.INamedTag<Block> ORES_BITIUM = forge("ores/bitium_ore");
        public static final ITag.INamedTag<Block> STORAGE_BLOCKS_BITIUM = forge("storage_blocks/bitium_block");


        public static ITag.INamedTag<Block> forge(String path) {
            return BlockTags.bind(new ResourceLocation("forge", path).toString());
        }

        public static ITag.INamedTag<Block> mod(String path) {
            return BlockTags.bind(new ResourceLocation(Logic.MODID, path).toString());
        }

    }

    public static final class Items {
        public static final ITag.INamedTag<Item> GEMS_BITIUM = forge("gems/bitium");
        public static final ITag.INamedTag<Item> ORES_BITIUM = forge("ores/bitium_ore");
        public static final ITag.INamedTag<Item> STORAGE_BLOCKS_BITIUM = forge("storage_blocks/bitium_block");


        public static ITag.INamedTag<Item> forge(String path) {
            return ItemTags.bind(new ResourceLocation("forge", path).toString());
        }

        public static ITag.INamedTag<Item> mod(String path) {
            return ItemTags.bind(new ResourceLocation(Logic.MODID, path).toString());
        }

    }
}

