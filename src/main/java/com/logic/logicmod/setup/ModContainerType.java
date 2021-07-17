package com.logic.logicmod.setup;

import com.logic.logicmod.block.CircuitTable.GateBuilderConatiner;
import com.logic.logicmod.block.CircuitTable.GateBuilderScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.network.IContainerFactory;

public class ModContainerType {
    public static final RegistryObject<ContainerType<GateBuilderConatiner>> GATE_BUILDER = register("gate_builder", GateBuilderConatiner::new);
    public static void register(){}

    @OnlyIn(Dist.CLIENT)
    public static void registerScreens(FMLClientSetupEvent event){
        ScreenManager.register(GATE_BUILDER.get(), GateBuilderScreen::new);
    }

    private static <T extends Container> RegistryObject<ContainerType<T>> register(String name, IContainerFactory<T> factory){
        return RegistryHandler.CONTAINERS.register(name, () -> IForgeContainerType.create(factory));
    }
}
