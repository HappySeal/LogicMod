package com.logic.logicmod.block.CircuitTable;

import com.google.common.collect.Lists;
import com.logic.logicmod.Logic;
import com.logic.logicmod.crafting.recipe.GateBuilderRecipe;
import com.logic.logicmod.setup.ModContainerType;
import com.logic.logicmod.setup.ModRecipes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.inventory.container.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;

import java.io.Console;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class GateBuilderConatiner extends Container {
    private final IWorldPosCallable access;
    private final IInventory inventory;
    private IIntArray fields;
    private final World level;

    private ItemStack waferInput = ItemStack.EMPTY;
    private ItemStack transistorInput = ItemStack.EMPTY;
    private ItemStack byteInput = ItemStack.EMPTY;

    private final IntReferenceHolder selectedRecipeIndex = IntReferenceHolder.standalone();
    private List<GateBuilderRecipe> recipes = Lists.newArrayList();

    final Slot waferSlot;
    final Slot transistorSlot;
    final Slot byteSlot;
    final Slot resultSlot;

    private Runnable slotUpdateListener = () -> {
    };
    public final IInventory container = new Inventory(3) {
        public void setChanged() {
            super.setChanged();
            GateBuilderConatiner.this.slotsChanged(this);
            GateBuilderConatiner.this.slotUpdateListener.run();
        }
    };

    public GateBuilderConatiner(int id, PlayerInventory playerInventory, PacketBuffer buffer){
        this(id, playerInventory, new GateBuilderTileEntity(), new IntArray(buffer.readByte()), IWorldPosCallable.NULL);
    }

    private final CraftResultInventory resultContainer = new CraftResultInventory();

    public GateBuilderConatiner(int id, PlayerInventory playerInventory, IInventory inventory, IIntArray fields, final IWorldPosCallable posCallable) {
        super(ModContainerType.GATE_BUILDER.get(), id);

        this.access = posCallable;
        this.inventory = inventory;
        this.fields = fields;
        this.level = playerInventory.player.level;

        this.waferSlot = this.addSlot(new Slot(this.container, 0, 20, 16));
        this.transistorSlot = this.addSlot(new Slot(this.container, 1, 20, 34));
        this.byteSlot = this.addSlot(new Slot(this.container, 2, 20, 52));


        this.resultSlot = this.addSlot(new Slot(this.resultContainer, 3, 143, 49) {
            @Override
            public boolean mayPlace(ItemStack item) {
                return false;
            }

            @Override
            public ItemStack onTake(PlayerEntity player, ItemStack stack) {
                stack.onCraftedBy(player.level, player, stack.getCount());
                GateBuilderConatiner.this.resultContainer.awardUsedRecipes(player);
                ItemStack waferStack = GateBuilderConatiner.this.waferSlot.remove(1);
                ItemStack transistorStack = GateBuilderConatiner.this.transistorSlot.remove(1);
                ItemStack byteStack = GateBuilderConatiner.this.byteSlot.remove(1);
                if (!waferStack.isEmpty() && !transistorStack.isEmpty() && !byteStack.isEmpty() ) {
                    GateBuilderConatiner.this.setupResultSlot();
                }

//                Crafting Sound Action
//                p_i50060_3_.execute((p_216954_1_, p_216954_2_) -> {
//                    long l = p_216954_1_.getGameTime();
//                    if (CircuitTableConatiner.this.lastSoundTime != l) {
//                        p_216954_1_.playSound((PlayerEntity)null, p_216954_2_, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
//                        CircuitTableConatiner.this.lastSoundTime = l;
//                    }

//                });
                return super.onTake(player, stack);
            }
        });



//        Player Inv
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                int index = 9 + x + y * 9;
                int posX = 8 + x * 18;
                int posY = 84 + y * 18;
                this.addSlot(new Slot(playerInventory, index, posX, posY));
            }
        }
//        Player hotbar

        for (int x = 0; x < 9; x++) {
            int index = x;
            int posX = 8 + x * 18;
            this.addSlot(new Slot(playerInventory, index, posX, 142));
        }

        this.addDataSlot(this.selectedRecipeIndex);
    }


    @OnlyIn(Dist.CLIENT)
    public int getSelectedRecipeIndex() {
        return this.selectedRecipeIndex.get();
    }

    @OnlyIn(Dist.CLIENT)
    public List<GateBuilderRecipe> getRecipes() {
        return this.recipes;
    }

    @OnlyIn(Dist.CLIENT)
    public int getNumRecipes() {
        return this.recipes.size();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean hasInputItem() {
        return  this.waferSlot.hasItem() &&
                this.transistorSlot.hasItem() &&
                this.byteSlot.hasItem() &&
                !this.recipes.isEmpty();
    }

//    No progress arrow thf no need
//    public int getProgressArrowScale(){
//        int progress = fields.get(0);
//        if(progress > 0) {
//            return progress * 24 / CircuitTableTileEntity.WORK_TIME;
//        }else{
//            return 0;
//        }
//    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return this.inventory.stillValid(player);
    }

//    BROKEN AS FUCK
    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);//bruh

        Scanner cc = new Scanner(System.in);
        cc.nextInt();
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            if (index == 3) {
                item.onCraftedBy(itemstack1, player.level, player);
                if (!this.moveItemStackTo(itemstack1, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index < 3) {
                if (!this.moveItemStackTo(itemstack1, 4, 40, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.level.getRecipeManager().getRecipeFor(ModRecipes.Types.ASSEMBLING, new Inventory(itemstack1), this.level).isPresent()) {
                if (!this.moveItemStackTo(itemstack1, 0, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 5 && index < 31) {//sjsj
                if (!this.moveItemStackTo(itemstack1, 31, 40, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 31 && index < 40 && !this.moveItemStackTo(itemstack1, 2, 29, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
            this.broadcastChanges();
        }

        return itemstack;
    }

    public boolean clickMenuButton(PlayerEntity player, int index) {
        if (this.isValidRecipeIndex(index)) {
            this.selectedRecipeIndex.set(index);
            this.setupResultSlot();
        }

        return true;
    }

    private boolean isValidRecipeIndex(int index) {
        return index >= 0 && index < this.recipes.size();
    }

    @Override
    public void slotsChanged(IInventory inventory) {
//        LogManager.getLogger().info("SLOTS CHANGED WEE WOO");
        ItemStack waferStack = GateBuilderConatiner.this.waferSlot.getItem();
        ItemStack transistorStack = GateBuilderConatiner.this.transistorSlot.getItem();
        ItemStack byteStack = GateBuilderConatiner.this.byteSlot.getItem();

        if (waferStack.getItem() != this.waferInput.getItem() ||
            transistorStack.getItem() != this.transistorInput.getItem() ||
            byteStack.getItem() != this.byteInput.getItem() ) {


            this.waferInput = waferStack.copy();
            this.transistorInput = transistorStack.copy();
            this.byteInput = byteStack.copy();

//            LogManager.getLogger().info("slots copied");

            this.setupRecipeList(inventory, waferStack, transistorStack, byteStack);
        }

    }

    private void setupRecipeList(IInventory inventory, ItemStack stack1, ItemStack stack2, ItemStack stack3) {
        this.recipes.clear();
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        if (!stack1.isEmpty() && !stack2.isEmpty() && !stack3.isEmpty()) {
            this.recipes = this.level.getRecipeManager().getRecipesFor(ModRecipes.Types.ASSEMBLING, inventory, this.level);
//            LogManager.getLogger().info(this.recipes);
        }

    }

    private void setupResultSlot() {
        if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
            GateBuilderRecipe circuitTableRecipe = this.recipes.get(this.selectedRecipeIndex.get());
            this.resultContainer.setRecipeUsed(circuitTableRecipe);
            this.resultSlot.set(circuitTableRecipe.assemble(this.container));
        } else {
            this.resultSlot.set(ItemStack.EMPTY);
        }

        this.broadcastChanges();
    }

    public ContainerType<?> getType() {
        return ModContainerType.GATE_BUILDER.get();
    }

    @OnlyIn(Dist.CLIENT)
    public void registerUpdateListener(Runnable runnable) {
        this.slotUpdateListener = runnable;
    }


    public void removed(PlayerEntity player) {
        super.removed(player);
        this.resultContainer.removeItemNoUpdate(1);
        this.access.execute((p_217079_2_, p_217079_3_) -> {
            this.clearContainer(player, player.level, this.container);
        });
    }
}
