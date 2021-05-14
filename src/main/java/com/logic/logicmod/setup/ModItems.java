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

    public static void register() {
    }
}
