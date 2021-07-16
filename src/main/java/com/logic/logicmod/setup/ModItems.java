package com.logic.logicmod.setup;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

public class ModItems {
    public static final RegistryObject<Item> BITIUM = RegistryHandler.ITEMS.register("bitium", () ->
            new Item(
                    new Item.Properties().tab(ItemGroup.TAB_MATERIALS)
            )
    );

    public static final RegistryObject<Item> REDSTONE_BYTE = RegistryHandler.ITEMS.register("redstone_byte", () ->
            new Item(
                    new Item.Properties().tab(ItemGroup.TAB_MATERIALS)
            )
    );

    public static final RegistryObject<Item> TRANSISTOR = RegistryHandler.ITEMS.register("transistor", () ->
            new Item(
                    new Item.Properties().tab(ItemGroup.TAB_MATERIALS)
            )
    );

    public static final RegistryObject<Item> WAFER = RegistryHandler.ITEMS.register("wafer", () ->
            new Item(
                    new Item.Properties().tab(ItemGroup.TAB_MATERIALS)
            )
    );

    public static void register() {
    }
}
