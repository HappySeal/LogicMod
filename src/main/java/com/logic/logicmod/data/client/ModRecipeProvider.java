package com.logic.logicmod.data.client;

import com.logic.logicmod.Logic;
import com.logic.logicmod.crafting.recipe.GateBuilderRecipe;
import com.logic.logicmod.setup.ModBlocks;
import com.logic.logicmod.setup.ModItems;
import com.logic.logicmod.setup.ModTags;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.data.*;
import net.minecraft.item.Items;
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

        CookingRecipeBuilder.smelting(
                Ingredient.of(ModItems.BITIUM.get()),
                ModItems.TRANSISTOR.get(),
                0.7F,
                100)
                .unlockedBy("has_item",has(ModItems.BITIUM.get()))
                .save(consumer, modId("transistor_from_smelting"));

        ShapedRecipeBuilder
                .shaped(ModItems.REDSTONE_BYTE.get(), 1)
                .define('#',ModItems.BITIUM.get())
                .define('R', Items.REDSTONE.getItem())
                .pattern("###")
                .pattern("#R#")
                .pattern("###")
                .unlockedBy("has_item",has(ModItems.BITIUM.get()))
                .save(consumer);

        CookingRecipeBuilder.blasting(
                Ingredient.of(Blocks.SAND.getBlock()),
                ModItems.WAFER.get(),
                0.7f,
                100)
                .unlockedBy("has_item",has(Blocks.SAND.getBlock()))
                .save(consumer,modId("wafer_from_smelting"));
    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(Logic.MODID, path);
    }


}

