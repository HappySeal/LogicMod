package com.logic.logicmod.setup;

import com.logic.logicmod.block.CircuitTable.GateBuilderTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModTileEntitiyType {

    public static RegistryObject<TileEntityType<GateBuilderTileEntity>> GATE_BUILDER = register(
            "gate_builder",
            GateBuilderTileEntity::new,
            ModBlocks.GATE_BUILDER
    );

    public static void register(){}

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register (String name, Supplier<T> supplier, RegistryObject<? extends Block> block){
        return RegistryHandler.TILE_ENTITIES.register(name, () -> {
            return TileEntityType.Builder.of(supplier,block.get()).build(null);
        });
    }
}
