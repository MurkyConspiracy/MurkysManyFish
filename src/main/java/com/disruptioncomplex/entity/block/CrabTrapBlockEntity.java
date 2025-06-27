package com.disruptioncomplex.entity.block;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.block.CrabTrapBlock;
import com.disruptioncomplex.entity.ModBlockEntityHandler;
import com.disruptioncomplex.item.ModItemHandler;
import com.disruptioncomplex.datagen.FishingLootTableProvider;
import com.disruptioncomplex.gui.CrabTrapScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;
import java.util.Random;


public class CrabTrapBlockEntity extends LootableContainerBlockEntity implements Inventory {

    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(20, ItemStack.EMPTY);
    public long lastUpdateTick;
    private static final Random random = new Random();
    private static final int TICK_TO_LOOT = MurkysManyFish.CONFIG.CrabTrapBlockTickInterval();
    private long randomTickTime = 0;
    private static final int RANDOM_TICK_TIME_RANGE = MurkysManyFish.CONFIG.CrabTrapBlockTickRange();


    public static void serverTick(World world, BlockPos ignoredPos, BlockState state, CrabTrapBlockEntity blockEntity) {
        if (!world.isClient) {
            long currentTime = world.getTime();
            //Debugging logger for tick calculations
            if (currentTime - blockEntity.lastUpdateTick >= TICK_TO_LOOT - blockEntity.randomTickTime) {
                //MurkysManyFish.LOGGER.info("TTL: {}\tRTR: {}\tCT: {}\tLUP: {}", TICK_TO_LOOT, blockEntity.randomTickTime, currentTime, blockEntity.lastUpdateTick);
                if (state.get(CrabTrapBlock.IN_VALID_WATER)) {
                    addCrabTrapLoot(blockEntity, world);
                }
                blockEntity.lastUpdateTick = currentTime;
                blockEntity.randomTickTime = random.nextInt(TICK_TO_LOOT) - RANDOM_TICK_TIME_RANGE;
            }
        }
    }


    @SuppressWarnings("ConstantConditions")
    private static void addCrabTrapLoot(CrabTrapBlockEntity blockEntity, World world) {

        if (world.isClient) return;
        //MurkysManyFish.LOGGER.info("Adding Crab Trap Loot");
        LootWorldContext lootWorldContext;
        LootTable crabTable;
        LootTable garbageTable;
        List<ItemStack> loot;


        lootWorldContext = new LootWorldContext.Builder((ServerWorld) blockEntity.getWorld())
                .build(LootContextTypes.EMPTY);
        if (blockEntity.inventory.getFirst().isOf(ModItemHandler.EARTHWORM) || blockEntity.inventory.getFirst().isOf(ModItemHandler.CENTIPEDE)) {
            crabTable = Objects.requireNonNull(world.getServer()).getReloadableRegistries().getLootTable(FishingLootTableProvider.CRAB_TRAP_LOOT);
            garbageTable = Objects.requireNonNull(world.getServer()).getReloadableRegistries().getLootTable(FishingLootTableProvider.CRAB_TRAP_GARBAGE);
            loot = (crabTable.generateLoot(lootWorldContext, random.nextLong()));
            if (random.nextInt(100) < 100 * MurkysManyFish.CONFIG.CrabTrapBlockGarbageChance()) {
                loot.addAll(garbageTable.generateLoot(lootWorldContext, random.nextLong()));
            }
            blockEntity.inventory.getFirst().decrement(1);
        } else {
            crabTable = Objects.requireNonNull(world.getServer()).getReloadableRegistries().getLootTable(FishingLootTableProvider.CRAB_TRAP_GARBAGE);
            loot = crabTable.generateLoot(lootWorldContext, random.nextLong());
        }

        int inventoryStart = 2; // Start index for slots
        int inventoryEnd = 20;  // End index for slots

        // Iterate over the loot and place them in the inventory between slots 2 and 20 (inclusive)
        for (ItemStack lootItem : loot) {
            boolean addedToExistingStack = false;
            for (int i = inventoryStart; i < inventoryEnd; i++) {
                ItemStack existingStack = blockEntity.inventory.get(i);
                if (!existingStack.isEmpty() && ItemStack.areItemsAndComponentsEqual(existingStack, lootItem)) {
                    int transferAmount = Math.min(lootItem.getCount(), existingStack.getMaxCount() - existingStack.getCount());
                    existingStack.increment(transferAmount);
                    lootItem.decrement(transferAmount);
                    if (lootItem.isEmpty()) {
                        addedToExistingStack = true;
                        break;
                    }
                }
            }

            if (!lootItem.isEmpty() && !addedToExistingStack) {
                for (int i = inventoryStart; i < inventoryEnd; i++) {
                    if (blockEntity.inventory.get(i).isEmpty()) {
                        blockEntity.inventory.set(i, lootItem);
                        break; // Move to the next loot item
                    }
                }
            }

            }
        blockEntity.markDirty();
        world.updateListeners(blockEntity.getPos(), blockEntity.getCachedState(), blockEntity.getCachedState(), Block.NOTIFY_ALL);


    }


    protected CrabTrapBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.lastUpdateTick = 0;
    }

    public CrabTrapBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntityHandler.CRAB_TRAP_BLOCK_ENTITY, pos, state);
    }


    @Override
    public int size() {
        return 20;
    }


    @Override
    protected Text getContainerName() {
        return Text.translatable("item.murkys-many-fish.crab_trap_block");
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.readLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory, registries);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        if (!this.writeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory, registries);
        }
    }


    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            return true;
        } else {
            return super.onSyncedBlockEvent(type, data);
        }
    }

    private final ViewerCountManager stateManager = new ViewerCountManager() {
        @Override
        protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
        }

        @Override
        protected void onContainerClose(World world, BlockPos pos, BlockState state) {
        }

        @Override
        protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
            CrabTrapBlockEntity.this.onViewerCountUpdate(world, pos, state, oldViewerCount, newViewerCount);
        }

        @Override
        protected boolean isPlayerViewing(PlayerEntity player) {
            if (player.currentScreenHandler instanceof CrabTrapScreenHandler) {
                Inventory inventory = ((CrabTrapScreenHandler)player.currentScreenHandler).getInventory();
                return inventory == CrabTrapBlockEntity.this;
            }
            return false;
        }

    };

    @Override
    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    @Override
    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }


    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new CrabTrapScreenHandler(syncId, playerInventory, this);
    }


    protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int ignoredOldViewerCount, int newViewerCount) {
        Block block = state.getBlock();
        world.addSyncedBlockEvent(pos, block, 1, newViewerCount);
    }

    @Override
    public void clear() {
        this.inventory.clear();
        this.markDirty();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.inventory) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(this.inventory, slot, amount);
        if (!result.isEmpty()) {
            this.markDirty();
        }
        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack stack = Inventories.removeStack(this.inventory, slot);
        this.markDirty();
        return stack;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
        this.markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return pos.isWithinDistance(player.getPos(), 4.5);
    }
}