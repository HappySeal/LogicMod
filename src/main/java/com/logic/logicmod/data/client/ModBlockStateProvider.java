package com.logic.logicmod.data.client;

import com.logic.logicmod.Logic;
import com.logic.logicmod.setup.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Logic.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.BITIUM_ORE.get());
        simpleBlock(ModBlocks.BITIUM_BLOCK.get());
    }
}
