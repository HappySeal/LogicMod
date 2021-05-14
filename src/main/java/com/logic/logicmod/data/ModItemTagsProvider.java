package com.logic.logicmod.data;

import com.logic.logicmod.Logic;
import com.logic.logicmod.setup.ModItems;
import com.logic.logicmod.setup.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;


public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator generator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(generator, blockTagProvider, Logic.MODID, existingFileHelper);
    }


    @Override
    protected void addTags() {
        copy(ModTags.Blocks.ORES_BITIUM,ModTags.Items.ORES_BITIUM);
        copy(Tags.Blocks.ORES, Tags.Items.ORES);


        copy(ModTags.Blocks.STORAGE_BLOCKS_BITIUM,ModTags.Items.STORAGE_BLOCKS_BITIUM);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);


        tag(ModTags.Items.GEMS_BITIUM).add(ModItems.BITIUM.get());
        tag(Tags.Items.GEMS).addTag(ModTags.Items.GEMS_BITIUM);
    }
}
