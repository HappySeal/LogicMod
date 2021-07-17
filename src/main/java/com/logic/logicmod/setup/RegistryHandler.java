package com.logic.logicmod.setup;

import com.logic.logicmod.Logic;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Logic.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Logic.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Logic.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Logic.MODID);
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Logic.MODID);


    public static void init() {
        ITEMS.register(getModEventBus());
        BLOCKS.register(getModEventBus());
        CONTAINERS.register(getModEventBus());
        TILE_ENTITIES.register(getModEventBus());
        RECIPE_SERIALIZER.register(getModEventBus());

        ModItems.register();
        ModBlocks.register();
        ModContainerType.register();
        getModEventBus().addGenericListener(IRecipeSerializer.class, ModRecipes::register);
        ModTileEntitiyType.register();
    }

    private static IEventBus getModEventBus() {
        return FMLJavaModLoadingContext.get().getModEventBus();
    }

    @Mod.EventBusSubscriber(modid = Logic.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class Client{
        private Client(){}

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){
            ModContainerType.registerScreens(event);
        }
    }
}
