package com.logic.logicmod.data;

import com.logic.logicmod.Logic;
import com.logic.logicmod.data.client.ModBlockStateProvider;
import com.logic.logicmod.data.client.ModItemModelProvider;
import com.logic.logicmod.data.client.ModLootTableProvider;
import com.logic.logicmod.data.client.ModRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;


@Mod.EventBusSubscriber(modid = Logic.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    private DataGenerators() {
    }


    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper efh = event.getExistingFileHelper();


        gen.addProvider(new ModBlockStateProvider(gen, efh));
        gen.addProvider(new ModItemModelProvider(gen, efh));

        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, efh);
        gen.addProvider(blockTags);


        ModItemTagsProvider itemTags = new ModItemTagsProvider(gen, blockTags, efh);
        gen.addProvider(itemTags);

        gen.addProvider(new ModLootTableProvider(gen));
        gen.addProvider(new ModRecipeProvider(gen));

    }


}
