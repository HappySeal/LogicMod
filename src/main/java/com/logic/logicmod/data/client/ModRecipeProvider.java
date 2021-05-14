package com.logic.logicmod.data.client;

import com.logic.logicmod.Logic;
import com.logic.logicmod.setup.ModBlocks;
import com.logic.logicmod.setup.ModItems;
import net.minecraft.data.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;


import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator p_i48262_1_) {
        super(p_i48262_1_);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapelessRecipeBuilder
                .shapeless(ModItems.BITIUM.get(),9)
                .requires(ModBlocks.BITIUM_BLOCK.get())
                .unlockedBy("has_item",has(ModItems.BITIUM.get()))
                .save(consumer);

        ShapedRecipeBuilder
                .shaped(ModBlocks.BITIUM_BLOCK.get(),1)
                .define('#',ModItems.BITIUM.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item",has(ModItems.BITIUM.get()))
                .save(consumer);

        CookingRecipeBuilder.smelting(
                Ingredient.of(ModBlocks.BITIUM_ORE.get()),
                ModItems.BITIUM.get(),
                1.0F,
                200)
                .unlockedBy("has_item",has(ModBlocks.BITIUM_ORE.get()))
                .save(consumer, modId("bitium_from_smelting"));
    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(Logic.MODID, path);
    }


}

