package com.logic.logicmod.setup;

import com.logic.logicmod.Logic;
import com.logic.logicmod.crafting.recipe.GateBuilderRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Map;

public class ModRecipes {

    public static class Types {
        public static final IRecipeType<GateBuilderRecipe> ASSEMBLING = IRecipeType.register(Logic.MODID + ":assembling");
    }

    public static void register(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        registerRecipe(event, Types.ASSEMBLING, GateBuilderRecipe.serializer);
    }

    private static void registerRecipe(RegistryEvent.Register<IRecipeSerializer<?>> event, IRecipeType<?> type, IRecipeSerializer<?> serializer){
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(type.toString()), type);
        event.getRegistry().register(serializer);
    }

    public Map<ResourceLocation, IRecipe<?>> getRecipes(IRecipeType<?> type, RecipeManager manager){
        final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes = ObfuscationReflectionHelper.getPrivateValue(RecipeManager.class, manager, "field_199522_d");

        return recipes.get(type);
    }
}
