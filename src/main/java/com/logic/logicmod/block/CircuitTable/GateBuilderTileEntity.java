package com.logic.logicmod.block.CircuitTable;

import com.logic.logicmod.crafting.recipe.GateBuilderRecipe;
import com.logic.logicmod.setup.ModRecipes;
import com.logic.logicmod.setup.ModTileEntitiyType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableTileEntity;

import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class GateBuilderTileEntity extends LockableTileEntity {

    public static int WORK_TIME = 2*20;

    private NonNullList<ItemStack> items;



    private int progress = 0;
    private final IIntArray fields = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index){
                case 0:
                    return progress;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index){
                case 0:
                    progress = value;
                    break;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };

    public GateBuilderTileEntity() {
        super(ModTileEntitiyType.GATE_BUILDER.get());

        this.items = NonNullList.withSize(4,ItemStack.EMPTY);
    }

    void encodeExtraData(PacketBuffer buffer){
        buffer.writeByte(fields.getCount());
    }

    @Nullable
    public GateBuilderRecipe getRecipe(){
        if(this.level  == null || getItem(0).isEmpty() || getItem(1).isEmpty() || getItem(2).isEmpty()){
            return null;
        }

        return this.level.getRecipeManager().getRecipeFor(ModRecipes.Types.ASSEMBLING, this, this.level).orElse(null);
    }

    private ItemStack getWorkOutput(@Nullable GateBuilderRecipe recipe){
        if (recipe != null){
            return recipe.assemble(this);
        }

        return ItemStack.EMPTY;
    }

    private void doWork(GateBuilderRecipe recipe){
        assert this.level != null;

        ItemStack current = getItem(1);
        ItemStack output = getWorkOutput(recipe);

        if(!current.isEmpty()){
            int newCount = current.getCount() + output.getCount();

            if(!ItemStack.matches(current, output) || newCount > output.getMaxStackSize()){
                stopWork();
                return;
            }
        }

        if(progress < WORK_TIME){
            ++progress;
        }

        if(progress >= WORK_TIME){
            finishWork(recipe, current, output);
            
        }
    }

    private void finishWork(GateBuilderRecipe recipe, ItemStack current, ItemStack output) {
        if(!current.isEmpty()){
            current.grow(output.getCount());
        } else {
            setItem(1, output);
        }

        progress = 0;
        this.removeItem(0, 1);

    }

    private void stopWork() {
        progress = 0;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.logic.gate_builder");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory playerInventory) {
        return new GateBuilderConatiner(id, playerInventory, this, this.fields, IWorldPosCallable.create(playerInventory.player.level, this.worldPosition));
    }

    @Override
    public int getContainerSize() {
        return 4;
    }

    @Override
    public boolean isEmpty() {
        boolean data = true;
        for(int i = 0; i < 4; i++){
            data &= getItem(i).isEmpty();
        }
        return data;
    }

    @Override
    public ItemStack getItem(int index) {
        return items.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int amount) {
        return ItemStackHelper.removeItem(items, index, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ItemStackHelper.takeItem(items, index);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        items.set(index,stack);
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return this.level != null &&
                this.level.getBlockEntity(this.worldPosition) == this &&
                player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY()+ 0.5, this.worldPosition.getZ() + 0.5) <= 64;
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    public void load(BlockState state, CompoundNBT tags) {
        super.load(state, tags);
        this.items = NonNullList.withSize(4, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(tags, this.items);

        this.progress = tags.getInt("Progress");
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        ItemStackHelper.saveAllItems(nbt, this.items);
        nbt.putInt("Progress", this.progress);

        return nbt;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = getUpdateTag();
        ItemStackHelper.saveAllItems(nbt, this.items);
        return new SUpdateTileEntityPacket(this.worldPosition, 1, nbt);
    }

    @Nullable
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        nbt.putInt("Progress", this.progress);
        return nbt;
    }

}
