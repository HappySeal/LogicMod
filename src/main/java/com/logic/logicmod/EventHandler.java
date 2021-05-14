package com.logic.logicmod;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandler {
    public static void init() {
    }

    @SubscribeEvent
    public void rightClick(PlayerInteractEvent.RightClickBlock event){
        System.out.println("oh right click");
    }

}
