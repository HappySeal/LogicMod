package com.logic.logicmod.data.client;

import com.logic.logicmod.Logic;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {


    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Logic.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels(){
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));


        buildBlock("bitium_block");
        buildBlock("bitium_ore");


        buildItem(itemGenerated, "bitium");
        buildItem(itemGenerated, "redstone_byte");
        buildItem(itemGenerated, "transistor");
        buildItem(itemGenerated, "wafer");

    }




    private void buildBlock(String name) {
        withExistingParent(name,modLoc("block/"+name));
    }

    private void buildItem(ModelFile itemGenerated, String name) {
        getBuilder(name).parent(itemGenerated).texture("layer0", "item/"+name);
    }


}
