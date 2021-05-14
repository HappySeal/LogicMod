package com.logic.logicmod;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class Redstone_Byte extends Block {

    public static final BooleanProperty BIT = BooleanProperty.create("bit");
    public static final DirectionProperty FACING = DirectionProperty.create("facing");


    public Redstone_Byte(Properties properties) {
        super(properties);


        setRegistryName("redstone_byte");

/*
        registerDefaultState(
                defaultBlockState()
                        .setValue(BIT,Boolean.TRUE)
                        .setValue(FACING, Direction.NORTH)
        );
*/
    }


    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
        System.out.print("hello i am clicked :D");
        return ActionResultType.SUCCESS;
    }

}
