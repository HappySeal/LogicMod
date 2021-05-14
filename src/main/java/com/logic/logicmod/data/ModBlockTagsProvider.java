package com.logic.logicmod.data;

import com.logic.logicmod.Logic;
import com.logic.logicmod.setup.ModBlocks;
import com.logic.logicmod.setup.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;


public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generator,ExistingFileHelper existingFileHelper) {
        super(generator, Logic.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.Blocks.ORES_BITIUM).add(ModBlocks.BITIUM_ORE.get());
        tag(Tags.Blocks.ORES).addTags(ModTags.Blocks.ORES_BITIUM);


        tag(ModTags.Blocks.STORAGE_BLOCKS_BITIUM).add(ModBlocks.BITIUM_BLOCK.get());
        tag(Tags.Blocks.STORAGE_BLOCKS).addTags(ModTags.Blocks.STORAGE_BLOCKS_BITIUM);
    }
}
