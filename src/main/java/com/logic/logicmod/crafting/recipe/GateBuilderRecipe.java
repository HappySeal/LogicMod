package com.logic.logicmod.crafting.recipe;

import com.google.gson.JsonObject;
import com.logic.logicmod.Logic;
import com.logic.logicmod.setup.ModBlocks;
import com.logic.logicmod.setup.ModItems;
import com.logic.logicmod.setup.ModRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;


public class GateBuilderRecipe implements IRecipe<IInventory> {
    protected final Ingredient redstone_byte;
    protected final Ingredient transistor;
    protected final Ingredient waffer;
    protected final ItemStack stack;

    public static final IRecipeSerializer<?> serializer = new Serializer();
    protected final ResourceLocation id;

    public GateBuilderRecipe(ResourceLocation location,
                             Ingredient ingredient_1,
                             Ingredient ingredient_2,
                             Ingredient ingredient_3,
                             ItemStack stack) {

        this.transistor = ingredient_1;
        this.redstone_byte = ingredient_2;
        this.waffer = ingredient_3;
        this.stack = stack;
        this.id = location;

    }

    @Override
    public boolean matches(IInventory inventory, World world) {

        return this.transistor.test(inventory.getItem(0)) &&
                this.waffer.test(inventory.getItem(2)) &&
                this.redstone_byte.test(inventory.getItem(1));
    }

    @Override
    public ItemStack assemble(IInventory p_77572_1_) {
        return this.stack.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.stack;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return this.serializer;
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.Types.ASSEMBLING;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.GATE_BUILDER.get());
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<GateBuilderRecipe> {

        public Serializer() {
            this.setRegistryName(Logic.MODID, "assembling");
        }

        public GateBuilderRecipe fromJson(ResourceLocation id, JsonObject json) {
            Ingredient ingredient_0, ingredient_1, ingredient_2;

            ingredient_0 = Ingredient.fromJson(JSONUtils.isArrayNode(json, "ingredient_0") ?
                    JSONUtils.getAsJsonArray(json, "ingredient_0") :
                    JSONUtils.getAsJsonObject(json, "ingredient_0"));
            ingredient_1 = Ingredient.fromJson(JSONUtils.isArrayNode(json, "ingredient_1") ?
                    JSONUtils.getAsJsonArray(json, "ingredient_1") :
                    JSONUtils.getAsJsonObject(json, "ingredient_1"));
            ingredient_2 = Ingredient.fromJson(JSONUtils.isArrayNode(json, "ingredient_2") ?
                    JSONUtils.getAsJsonArray(json, "ingredient_2") :
                    JSONUtils.getAsJsonObject(json, "ingredient_2"));

            String s1 = JSONUtils.getAsString(json, "result");
            int i = JSONUtils.getAsInt(json, "count");
            ItemStack itemstack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(s1)), i);


            return new GateBuilderRecipe(id, ingredient_0, ingredient_1, ingredient_2, itemstack);
        }

        @Nullable
        public GateBuilderRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer) {
            Ingredient ingredient_0 = Ingredient.fromNetwork(buffer);
            Ingredient ingredient_1 = Ingredient.fromNetwork(buffer);
            Ingredient ingredient_2 = Ingredient.fromNetwork(buffer);
            ItemStack itemstack = buffer.readItem();
            return new GateBuilderRecipe(id, ingredient_0, ingredient_1, ingredient_2, itemstack);
        }

        public void toNetwork(PacketBuffer id, GateBuilderRecipe recipe) {
            recipe.transistor.toNetwork(id);
            recipe.redstone_byte.toNetwork(id);
            recipe.waffer.toNetwork(id);
            id.writeItem(recipe.stack);
        }
    }
}
