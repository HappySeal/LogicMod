package com.logic.logicmod.block.CircuitTable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class GateBuilderBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(2, 5, 4, 14, 6, 12),
            Block.box(1, 1, 3, 15, 15, 13),
            Block.box(0, 0, 0, 16, 16, 3),
            Block.box(0, 0, 13, 4, 16, 16),
            Block.box(4, 0, 13, 12, 9, 16),
            Block.box(12, 0, 13, 16, 16, 16)
    ).reduce((v1, v2) -> {
        return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
    }).get();
    //
    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(4, 5, 2, 12, 6, 14),
            Block.box(3, 1, 1, 13, 15, 15),
            Block.box(0, 0, 0, 3, 16, 16),
            Block.box(13, 0, 12, 16, 16, 16),
            Block.box(13, 0, 4, 16, 9, 12),
            Block.box(13, 0, 0, 16, 16, 4)
    ).reduce((v1, v2) -> {
        return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
    }).get();
    //
    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(2, 5, 4, 14, 6, 12),
            Block.box(1, 1, 3, 15, 15, 13),
            Block.box(0, 0, 13, 16, 16, 16),
            Block.box(12, 0, 0, 16, 16, 3),
            Block.box(4, 0, 0, 12, 9, 3),
            Block.box(0, 0, 0, 4, 16, 3)
    ).reduce((v1, v2) -> {
        return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
    }).get();
    //
    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(4, 5, 2, 12, 6, 14),
            Block.box(3, 1, 1, 13, 15, 15),
            Block.box(13, 0, 0, 16, 16, 16),
            Block.box(0, 0, 0, 3, 16, 4),
            Block.box(0, 0, 4, 3, 9, 12),
            Block.box(0, 0, 12, 3, 16, 16)
    ).reduce((v1, v2) -> {
        return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
    }).get();


    public GateBuilderBlock(Properties prop) {
        super(prop);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new GateBuilderTileEntity();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(FACING)) {
            case EAST:
                return SHAPE_E;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            default:
                return SHAPE_N;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        }
        interactWith(world, pos, player);
        return ActionResultType.CONSUME;
    }

    private void interactWith(World world, BlockPos pos, PlayerEntity player) {
        TileEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof GateBuilderTileEntity && player instanceof ServerPlayerEntity) {
            GateBuilderTileEntity te = (GateBuilderTileEntity) tileEntity;
            NetworkHooks.openGui((ServerPlayerEntity) player, te, te::encodeExtraData);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean b) {
        if (!state.is(newState.getBlock())) {
            TileEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof IInventory) {
                InventoryHelper.dropContents(world, pos, (IInventory) tileEntity);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, b);
        }
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprication")
    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }


    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
