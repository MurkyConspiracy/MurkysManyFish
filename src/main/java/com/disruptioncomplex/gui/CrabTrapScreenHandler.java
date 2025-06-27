package com.disruptioncomplex.gui;

import com.disruptioncomplex.item.ModItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class CrabTrapScreenHandler extends ScreenHandler {
    public static final FeatureSet REQUIRED_FEATURES = FeatureSet.of(FeatureFlags.VANILLA);
    private static final int BAIT_SLOT = 0;
    private static final int FILTER_SLOT = 1;

    private final Inventory inventory;
    private static final int SLOT_COUNT = 20;

    public Inventory getInventory() {
        return inventory;
    }

    private static class CrabTrapResultSlot extends Slot {
        public CrabTrapResultSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false; // Never allow manual insertion into output slots
        }
    }

    public CrabTrapScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.CRAB_TRAP_SCREEN_HANDLER, syncId);
        this.inventory = inventory; // Make sure this line exists
        checkSize(inventory, SLOT_COUNT);
        inventory.onOpen(playerInventory.player);

        final int INPUT_SLOT_OFFSET = -24;
        final int OUTPUT_SLOT_OFFSET = -12;

        // Left column with 2 slots
        this.addSlot(new Slot(inventory, BAIT_SLOT, 44 + INPUT_SLOT_OFFSET, 17));    // Top slot
        this.addSlot(new Slot(inventory, FILTER_SLOT, 44 + INPUT_SLOT_OFFSET, 53));  // Bottom slot

        // 6x3 grid of slots
        int gridStartX = 62 + OUTPUT_SLOT_OFFSET;
        int gridStartY = 17;
        int index = 2;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 6; col++) {
                int x = gridStartX + col * 18;
                int y = gridStartY + row * 18;
                this.addSlot(new CrabTrapResultSlot(inventory, index++, x, y));
            }
        }


        // Player inventory (3x9)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Player hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    public CrabTrapScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(SLOT_COUNT));
    }
    
    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);

        if (slot.hasStack()) {
            ItemStack slotStack = slot.getStack();
            itemStack = slotStack.copy();

            if (slotIndex < SLOT_COUNT) {
                if (!this.insertItem(slotStack, SLOT_COUNT, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(slotStack, 0, SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return itemStack;
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        // Add any filter-specific logic here if needed
        if (slot.id == BAIT_SLOT) {
            return stack.isOf(ModItemHandler.EARTHWORM) || stack.isOf(ModItemHandler.CENTIPEDE);
        } else return slot.id == FILTER_SLOT;// All other slots (2-20) cannot be inserted into
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }


}