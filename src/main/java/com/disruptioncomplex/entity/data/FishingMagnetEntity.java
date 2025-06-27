package com.disruptioncomplex.entity.data;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.MurkysManyFishClient;
import com.disruptioncomplex.entity.ModEntityHandler;
import com.disruptioncomplex.item.ModItemHandler;
import com.disruptioncomplex.util.MagnetHookAccessor;
import com.mojang.logging.LogUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a fishing magnet entity used for magnet fishing mechanics.
 * This entity is thrown by players using a Magnet Fishing Rod and can:
 * - Sink in water to catch items
 * - Track its depth in water
 * - Hook and pull entities
 * - Generate fishing loot
 */
public class FishingMagnetEntity extends ProjectileEntity {
    // Logging and utilities
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Random velocityRandom = Random.create();

    // Entity tracking data
    private static final TrackedData<Integer> HOOK_ENTITY_ID = DataTracker.registerData(FishingMagnetEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> CAUGHT_FISH = DataTracker.registerData(FishingMagnetEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    //private static final TrackedData<Float> MAGNET_DEPTH = DataTracker.registerData(FishingMagnetEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<MagnetFishingData> MAGNET_DATA =
            DataTracker.registerData(FishingMagnetEntity.class, ModTrackedDataHandlers.MAGNET_FISHING_DATA_HANDLER);

    // Fishing state variables
    private boolean caughtFish;
    private int outOfOpenWaterTicks;
    private boolean inOpenWater = true;
    @Nullable
    private Entity hookedEntity;
    private FishingMagnetEntity.State state = FishingMagnetEntity.State.FLYING;

    // Timers and counters
    private int removalTimer;      // Controls when entity should be removed
    private final int hookCountdown;     // Time until a hook attempt
    private final int fishTravelCountdown; // Controls fish movement animation

    // Fishing mechanics modifiers
    private final int luckBonus;   // Increases chance of better loot


    /**
     * Base constructor for the fishing magnet entity.
     *
     * @param type                   The entity type
     * @param world                  The world instance
     * @param luckBonus              Bonus to fishing luck (min 0)
     * @param waitTimeReductionTicks Reduction in waiting time between catches (min 0)
     */
    public FishingMagnetEntity(EntityType<? extends FishingMagnetEntity> type, World world, int luckBonus, int waitTimeReductionTicks) {
        super(type, world);
        this.luckBonus = Math.max(0, luckBonus);
        this.hookCountdown = 0;
        this.fishTravelCountdown = 0;
    }

    /**
     * Simplified constructor with default values for luck and wait time.
     *
     * @param entityType The entity type
     * @param world      The world instance
     */
    public FishingMagnetEntity(EntityType<? extends FishingMagnetEntity> entityType, World world) {
        this(entityType, world, 0, 0);
    }

    /**
     * Constructor for when a player throws the fishing magnet.
     * Handles initial positioning and velocity calculations.
     *
     * @param thrower                The player throwing the magnet
     * @param world                  The world instance
     * @param luckBonus              Bonus to fishing luck
     * @param waitTimeReductionTicks Reduction in waiting time between catches
     */
    public FishingMagnetEntity(PlayerEntity thrower, World world, int luckBonus, int waitTimeReductionTicks) {
        this(ModEntityHandler.FISHING_MAGNET, world, luckBonus, waitTimeReductionTicks);
        MurkysManyFish.LOGGER.info("Creating Fishing Magnet Entity - Thrower: {}, World: {}", thrower.getName().getString(), world.isClient ? "CLIENT" : "SERVER");
        this.setOwner(thrower);
        float f = thrower.getPitch();
        float g = thrower.getYaw();
        float h = MathHelper.cos(-g * (float) (Math.PI / 180.0) - (float) Math.PI);
        float i = MathHelper.sin(-g * (float) (Math.PI / 180.0) - (float) Math.PI);
        float j = -MathHelper.cos(-f * (float) (Math.PI / 180.0));
        float k = MathHelper.sin(-f * (float) (Math.PI / 180.0));
        double d = thrower.getX() - i * 0.3;
        double e = thrower.getEyeY();
        double l = thrower.getZ() - h * 0.3;
        this.refreshPositionAndAngles(d, e, l, g, f);
        Vec3d vec3d = new Vec3d(-i, MathHelper.clamp(-(k / j), -5.0F, 5.0F), -h);
        double m = vec3d.length();
        vec3d = vec3d.multiply(
                0.6 / m + this.random.nextTriangular(0.5, 0.0103365),
                0.6 / m + this.random.nextTriangular(0.5, 0.0103365),
                0.6 / m + this.random.nextTriangular(0.5, 0.0103365)
        );
        this.setVelocity(vec3d);
        this.setYaw((float) ((MathHelper.atan2(vec3d.y, vec3d.z) * 180.0F) / (float) Math.PI));
        this.setPitch((float) (MathHelper.atan2(vec3d.y, vec3d.horizontalLength()) * 180.0F / (float) Math.PI));
        this.lastYaw = this.getYaw();
        this.lastPitch = this.getPitch();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(HOOK_ENTITY_ID, 0);
        builder.add(CAUGHT_FISH, false);
        builder.add(MAGNET_DATA, createDefaultMagnetData());


    }

    private MagnetFishingData createDefaultMagnetData() {
        //MurkysManyFish.LOGGER.info("Creating default magnet data");
        // Create default asset info for each field

        // Create default data with empty item stacks and depth 0
        MagnetFishingData.MagnetBobberData defaultBobberData = new MagnetFishingData.MagnetBobberData(
                0, // depth
                0,
                new ArrayList<>()
        );
        //MurkysManyFish.LOGGER.info("Creating default magnet data - Done");

        return !this.getWorld().isClient() ?
                new MagnetFishingData(defaultBobberData) : this.dataTracker
                == null ?
                null :
                this.getMagnetData();


    }


    public float getDepth() {
        return this.getMagnetData().assetInfo().depth();
    }

    public void setDepth(float depth) {
        MagnetFishingData currentData = this.getMagnetData();
        MagnetFishingData.MagnetBobberData newBobberData = new MagnetFishingData.MagnetBobberData(
                depth,
                currentData.assetInfo().maxDepth(),
                currentData.assetInfo().availableDepths()
        );
        this.setMagnetData(new MagnetFishingData(newBobberData));
    }

    public float getMaxDepth() {
        return this.getMagnetData().assetInfo().maxDepth();
    }

    public void setMaxDepth(int maxDepth) {
        MagnetFishingData currentData = this.getMagnetData();
        MagnetFishingData.MagnetBobberData newBobberData = new MagnetFishingData.MagnetBobberData(
                currentData.assetInfo().depth(),
                maxDepth,
                currentData.assetInfo().availableDepths()
        );
        this.setMagnetData(new MagnetFishingData(newBobberData));
    }


    @Override
    protected boolean deflectsAgainstWorldBorder() {
        return true;
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (HOOK_ENTITY_ID.equals(data)) {
            int i = this.getDataTracker().get(HOOK_ENTITY_ID);
            this.hookedEntity = i > 0 ? this.getWorld().getEntityById(i - 1) : null;
        }

        if (CAUGHT_FISH.equals(data)) {
            this.caughtFish = this.getDataTracker().get(CAUGHT_FISH);
            if (this.caughtFish) {
                this.setVelocity(this.getVelocity().x, -0.4F * MathHelper.nextFloat(this.velocityRandom, 0.6F, 1.0F), this.getVelocity().z);
            }
        }

        //MurkysManyFish.LOGGER.info("Data tracker update - Data: {}, Value: {}", data, this.getDataTracker().get(data));
        if (MAGNET_DATA.equals(data)) {
            MagnetFishingData magnetData = this.getDataTracker().get(MAGNET_DATA);
            // Handle magnet data changes here,
            // For example, update rendering or behavior based on the new data
            MurkysManyFishClient.MAGNET_DEPTH = this.getDataTracker().get(MAGNET_DATA).assetInfo().depth();
            onMagnetDataChanged(magnetData);
        }


        super.onTrackedDataSet(data);
    }

    private void onMagnetDataChanged(MagnetFishingData magnetData) {
        if (this.getWorld().isClient) {
            return;
        }
        if (this.getWorld().getTickOrder() % 100 == 0) {
            MurkysManyFish.LOGGER.debug(magnetData.toString());
        }

    }

    public MagnetFishingData getMagnetData() {
        return this.getDataTracker().get(MAGNET_DATA);
    }

    public void setMagnetData(MagnetFishingData magnetData) {
        MagnetFishingData currentData = getMagnetData();
        if (!currentData.equals(magnetData)) {
            this.dataTracker.set(MAGNET_DATA, magnetData);
        }

    }


    /**
     * Determines if this entity should be rendered based on distance from the player.
     * Standard Minecraft render distance squared is 4096.0 blocks.
     *
     * @param distance The squared distance to the player camera
     * @return true if entity should be rendered, false otherwise
     */
    @Override
    public boolean shouldRender(double distance) {
        return distance < 4096.0;
    }

    /**
     * The main update method called every game tick.
     * Handles entity lifecycle including movement, physics, and fishing mechanics.
     * <p>
     * Key responsibilities:
     * - Updates entity position and physics
     * - Manages water interaction and depth tracking
     * - Controls fishing mechanics and state
     * - Validates player connection and requirements
     * - Handles particle effects and sound
     * - Manages hooked entity interactions
     * <p>
     * States:
     * - FLYING: Initial throw state
     * - HOOKED_IN_ENTITY: Attached to an entity
     * - SINKING: Moving through water
     * - GROUNDED: Resting on bottom
     */
    @Override
    public void tick() {
        this.velocityRandom.setSeed(this.getUuid().getLeastSignificantBits() ^ this.getWorld().getTime());
        super.tick();
        PlayerEntity playerEntity = this.getPlayerOwner();
        if (playerEntity == null) {
            this.discard();
        } else if (this.getWorld().isClient || this.checkIfValid(playerEntity)) {
            if (this.isOnGround()) {
                this.removalTimer++;
                if (this.removalTimer >= 1200) {
                    this.discard();
                    return;
                }
            } else {
                this.removalTimer = 0;
            }

            if (this.state == FishingMagnetEntity.State.GROUNDED) {
                return;
            }

            float f = 0.0F;
            BlockPos blockPos = this.getBlockPos();
            FluidState fluidState = this.getWorld().getFluidState(blockPos);
            if (fluidState.isIn(FluidTags.WATER)) {
                f = fluidState.getHeight(this.getWorld(), blockPos);
            }

            boolean bl = f > 0.0F;
            if (this.state == FishingMagnetEntity.State.FLYING) {
                if (this.hookedEntity != null) {
                    this.setVelocity(Vec3d.ZERO);
                    this.setState(FishingMagnetEntity.State.HOOKED_IN_ENTITY);
                    return;
                }

                if (bl) {
                    this.setVelocity(this.getVelocity().multiply(0.3, 0.2, 0.3));
                    this.setState(State.SINKING);
                    return;
                }

                this.checkForCollision();
            } else {
                if (this.state == FishingMagnetEntity.State.HOOKED_IN_ENTITY) {
                    if (this.hookedEntity != null) {
                        if (!this.hookedEntity.isRemoved() && this.hookedEntity.getWorld().getRegistryKey() == this.getWorld().getRegistryKey()) {
                            this.setPosition(this.hookedEntity.getX(), this.hookedEntity.getBodyY(0.8), this.hookedEntity.getZ());
                        } else {
                            this.updateHookedEntityId(null);
                            this.setState(FishingMagnetEntity.State.FLYING);
                        }
                    }

                    return;
                }

                if (this.state == State.SINKING) {
                    MurkysManyFishClient.IS_MAGNET_FISHING_ROD_ACTIVE = true;
                    Vec3d vec3d = this.getVelocity();

                    // Apply constant downward force for sinking
                    this.setVelocity(vec3d.x * 0.9, -0.01, vec3d.z * 0.9);

                    // Calculate water boundaries only once when entering a sinking state
                    if (this.waterSurfaceY == 0 || this.waterBottomY == 0) {
                        this.calculateWaterBoundaries();
                    }

                    // Calculate depth percentage
                    float depthPercentage = this.calculateDepthPercentage();

                    // Check if we've reached the ground
                    double distanceToGround = this.getY() - this.waterBottomY;


                    // Update tracked data so clients receive the update
                    if (!this.getWorld().isClient) {
                        this.setDepth(depthPercentage);
                    }

                    if (distanceToGround <= 0.125 || this.getVelocity().y == 0.0) {
                        this.setState(State.GROUNDED);
                        if (!this.getWorld().isClient) {
                            this.setDepth(100f);
                        }
                    }

                    // Only check open water when necessary
                    if (this.hookCountdown <= 0 && this.fishTravelCountdown <= 0) {
                        this.inOpenWater = true;
                    } else {
                        // Only perform expensive water check every 10 ticks or when conditions change
                        if (this.age % 10 == 0 || this.outOfOpenWaterTicks == 0) {
                            this.inOpenWater = this.inOpenWater && this.outOfOpenWaterTicks < 10 && this.isOpenOrWaterAround(blockPos);
                        }
                    }

                    if (bl) {
                        this.outOfOpenWaterTicks = Math.max(0, this.outOfOpenWaterTicks - 1);
                        if (this.caughtFish) {
                            this.setVelocity(this.getVelocity().add(0.0, -0.1 * this.velocityRandom.nextFloat() * this.velocityRandom.nextFloat(), 0.0));
                        }

                        if (!this.getWorld().isClient) {
                            this.tickFishingLogic(blockPos);
                        }
                    } else {
                        this.outOfOpenWaterTicks = Math.min(10, this.outOfOpenWaterTicks + 1);
                    }
                }
            }

            if (!fluidState.isIn(FluidTags.WATER)) {
                this.setVelocity(this.getVelocity().add(0.0, -0.03, 0.0));
            }

            this.move(MovementType.SELF, this.getVelocity());
            this.tickBlockCollision();
            this.updateRotation();
            if (this.state == FishingMagnetEntity.State.FLYING && (this.isOnGround() || this.horizontalCollision)) {
                this.setVelocity(Vec3d.ZERO);
            }

            this.setVelocity(this.getVelocity().multiply(0.92));
            this.refreshPosition();
        }
    }

    private static String getRange(float maxDepth) {
        if (maxDepth < 5)
            return "NA";
        else if (maxDepth < 10)
            return "LOW";
        else if (maxDepth < 15)
            return "MEDIUM";
        else if (maxDepth < 20)
            return "HIGH";
        else
            return "EXTREME";
    }

    private void assignMagnetLoot() {
        if (this.getWorld().isClient) {
            return;
        }

        switch (getRange(this.getMagnetData().assetInfo().maxDepth())) {

            case "NA":
                break;
            case "LOW":


            default:
                break;

        }


    }

    // Add these fields to your class
    private double waterSurfaceY = 0;
    private double waterBottomY = 0;

    // Updated helper methods
    private void calculateWaterBoundaries() {
        BlockPos currentPos = this.getBlockPos();

        // Find water surface (highest water block)
        BlockPos surfacePos = currentPos;

        // First, go up to find the surface
        while (this.getWorld().getFluidState(surfacePos).isIn(FluidTags.WATER)) {
            surfacePos = surfacePos.up();
            // Safety check to prevent infinite loops
            if (surfacePos.getY() > currentPos.getY() + 64) break;
        }
        // The water surface is one block below the first non-water block
        this.waterSurfaceY = surfacePos.getY() - 0.1; // Slight offset to account for fluid level

        // Find water bottom (lowest water block)
        BlockPos bottomPos = currentPos;
        while (this.getWorld().getFluidState(bottomPos).isIn(FluidTags.WATER)) {
            bottomPos = bottomPos.down();
            // Safety check to prevent infinite loops
            if (bottomPos.getY() < currentPos.getY() - 64) break;
        }
        // The water bottom is one block above the first non-water block
        this.waterBottomY = bottomPos.getY() + 1.0;

    }


    private float calculateDepthPercentage() {
        if (this.waterSurfaceY == 0 || this.waterBottomY == 0) {
            return 0.0f;
        }

        double totalDepth = this.waterSurfaceY - this.waterBottomY;
        if (totalDepth <= 0) {
            return 0.0f;
        }

        double currentDepth = this.waterSurfaceY - this.getY();
        float percentage = (float) ((currentDepth / totalDepth) * 100.0);

        return MathHelper.clamp(percentage, 0.0f, 100.0f);
    }

    // Override state change method to reset water surface calculation
    private void setState(State newState) {
        if (this.state != newState) {
            this.state = newState;
            // Reset water boundary calculations when entering a sinking state
            if (newState == State.SINKING) {
                this.waterSurfaceY = 0;
                this.waterBottomY = 0;
            }
        }
    }

    private boolean checkIfValid(PlayerEntity player) {
        ItemStack itemStack = player.getMainHandStack();
        ItemStack itemStack2 = player.getOffHandStack();
        boolean bl = itemStack.isOf(ModItemHandler.MAGNET_FISHING_ROD);
        boolean bl2 = itemStack2.isOf(ModItemHandler.MAGNET_FISHING_ROD);
        if (!player.isRemoved() && player.isAlive() && (bl || bl2) && !(this.squaredDistanceTo(player) > 1024.0)) {
            return true;
        } else {
            this.discard();
            return false;
        }
    }

    private void checkForCollision() {
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.hitOrDeflect(hitResult);
    }

    @Override
    protected boolean canHit(Entity entity) {
        return super.canHit(entity) || entity.isAlive() && entity instanceof ItemEntity;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!this.getWorld().isClient) {
            this.updateHookedEntityId(entityHitResult.getEntity());
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.setVelocity(this.getVelocity().normalize().multiply(blockHitResult.squaredDistanceTo(this)));
    }

    private void updateHookedEntityId(@Nullable Entity entity) {
        this.hookedEntity = entity;
        this.getDataTracker().set(HOOK_ENTITY_ID, entity == null ? 0 : entity.getId() + 1);
    }

    private void tickFishingLogic(BlockPos pos) {

        ServerWorld serverWorld = (ServerWorld) this.getWorld();
        if (!this.getWorld().isClient &&
                serverWorld.getTickOrder() % 30 == 0 &&
                this.state == State.SINKING
        ) {
            this.playSound(SoundEvents.ENTITY_FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);

            serverWorld.spawnParticles(
                    ParticleTypes.BUBBLE, this.getX(), this.waterSurfaceY, this.getZ(), (int) (1.0F + this.getWidth() * 20.0F), this.getWidth(), 0.0, this.getWidth(), 0.2F
            );
            serverWorld.spawnParticles(
                    ParticleTypes.FISHING, this.getX(), this.waterSurfaceY, this.getZ(), (int) (1.0F + this.getWidth() * 20.0F), this.getWidth(), 0.0, this.getWidth(), 0.2F
            );
            serverWorld.spawnParticles(
                    ParticleTypes.BUBBLE_POP, this.getX(), this.waterSurfaceY, this.getZ(), (int) (1.0F + this.getWidth() * 20.0F), this.getWidth(), 0.0, this.getWidth(), 0.2F
            );

        }

    }

    private boolean isOpenOrWaterAround(BlockPos pos) {
        FishingMagnetEntity.PositionType positionType = FishingMagnetEntity.PositionType.INVALID;

        for (int i = -1; i <= 2; i++) {
            FishingMagnetEntity.PositionType positionType2 = this.getPositionType(pos.add(-2, i, -2), pos.add(2, i, 2));
            switch (positionType2) {
                case ABOVE_WATER:
                    if (positionType == FishingMagnetEntity.PositionType.INVALID) {
                        return false;
                    }
                    break;
                case INSIDE_WATER:
                    if (positionType == FishingMagnetEntity.PositionType.ABOVE_WATER) {
                        return false;
                    }
                    break;
                case INVALID:
                    return false;
            }

            positionType = positionType2;
        }

        return true;
    }

    private FishingMagnetEntity.PositionType getPositionType(BlockPos start, BlockPos end) {
        return BlockPos.stream(start, end)
                .map(this::getPositionType)
                .reduce((positionType, positionType2) -> positionType == positionType2 ? positionType : PositionType.INVALID)
                .orElse(PositionType.INVALID);
    }

    private FishingMagnetEntity.PositionType getPositionType(BlockPos pos) {
        BlockState blockState = this.getWorld().getBlockState(pos);
        if (!blockState.isAir() && !blockState.isOf(Blocks.LILY_PAD)) {
            FluidState fluidState = blockState.getFluidState();
            return fluidState.isIn(FluidTags.WATER) && fluidState.isStill() && blockState.getCollisionShape(this.getWorld(), pos).isEmpty()
                    ? FishingMagnetEntity.PositionType.INSIDE_WATER
                    : FishingMagnetEntity.PositionType.INVALID;
        } else {
            return FishingMagnetEntity.PositionType.ABOVE_WATER;
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
    }

    public int use(ItemStack usedItem) {
        PlayerEntity playerEntity = this.getPlayerOwner();
        if (!this.getWorld().isClient && playerEntity != null && this.checkIfValid(playerEntity)) {
            int i = 0;
            if (this.hookedEntity != null) {
                this.pullHookedEntity(this.hookedEntity);
                /*
                I don't know what this does, commenting out for now while I figure the error out
                 */
                //Criteria.FISHING_ROD_HOOKED.trigger((ServerPlayerEntity)playerEntity, usedItem, this, Collections.emptyList());
                this.getWorld().sendEntityStatus(this, EntityStatuses.PULL_HOOKED_ENTITY);
                i = this.hookedEntity instanceof ItemEntity ? 3 : 5;
            } else if (this.hookCountdown > 0) {
                LootWorldContext lootWorldContext = new LootWorldContext.Builder((ServerWorld) this.getWorld())
                        .add(LootContextParameters.ORIGIN, this.getPos())
                        .add(LootContextParameters.TOOL, usedItem)
                        .add(LootContextParameters.THIS_ENTITY, this)
                        .luck(this.luckBonus + playerEntity.getLuck())
                        .build(LootContextTypes.FISHING);
                LootTable lootTable = Objects.requireNonNull(this.getWorld().getServer()).getReloadableRegistries().getLootTable(LootTables.FISHING_GAMEPLAY);
                List<ItemStack> list = lootTable.generateLoot(lootWorldContext);
                /*
                 I don't know what this does, commenting out for now while I figure the error out
                 */
                //Criteria.FISHING_ROD_HOOKED.trigger((ServerPlayerEntity)playerEntity, usedItem, this, list);

                for (ItemStack itemStack : list) {
                    ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), itemStack);
                    double d = playerEntity.getX() - this.getX();
                    double e = playerEntity.getY() - this.getY();
                    double f = playerEntity.getZ() - this.getZ();
                    itemEntity.setVelocity(d * 0.1, e * 0.1 + Math.sqrt(Math.sqrt(d * d + e * e + f * f)) * 0.08, f * 0.1);
                    this.getWorld().spawnEntity(itemEntity);
                    playerEntity.getWorld()
                            .spawnEntity(
                                    new ExperienceOrbEntity(playerEntity.getWorld(), playerEntity.getX(), playerEntity.getY() + 0.5, playerEntity.getZ() + 0.5, this.random.nextInt(6) + 1)
                            );
                    if (itemStack.isIn(ItemTags.FISHES)) {
                        playerEntity.increaseStat(Stats.FISH_CAUGHT, 1);
                    }
                }

                i = 1;
            }

            if (this.isOnGround()) {
                i = 2;
            }

            this.discard();
            return i;
        } else {
            return 0;
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PULL_HOOKED_ENTITY
                && this.getWorld().isClient
                && this.hookedEntity instanceof PlayerEntity playerEntity
                && playerEntity.isMainPlayer()) {
            this.pullHookedEntity(this.hookedEntity);
        }

        super.handleStatus(status);
    }

    protected void pullHookedEntity(Entity entity) {
        Entity entity2 = this.getOwner();
        if (entity2 != null) {
            Vec3d vec3d = new Vec3d(entity2.getX() - this.getX(), entity2.getY() - this.getY(), entity2.getZ() - this.getZ()).multiply(0.1);
            entity.setVelocity(entity.getVelocity().add(vec3d));
        }
    }

    @Override
    protected Entity.MoveEffect getMoveEffect() {
        return Entity.MoveEffect.NONE;
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        MurkysManyFishClient.IS_MAGNET_FISHING_ROD_ACTIVE = false;
        this.setPlayerFishHook(null);
        super.remove(reason);
    }

    @Override
    public void onRemoved() {
        this.setPlayerFishHook(null);
    }

    @Override
    public void setOwner(@Nullable Entity entity) {
        super.setOwner(entity);
        this.setPlayerFishHook(this);
    }


    // Update your existing setPlayerFishHook method or create a new one
    private void setPlayerFishHook(@Nullable FishingMagnetEntity fishingMagnetEntity) {
        PlayerEntity playerEntity = this.getPlayerOwner();
        if (playerEntity != null) {
            //playerEntity.fishHook = fishingMagnetEntity;
            // Also set the magnet hook
            ((MagnetHookAccessor) playerEntity).setMagnetHook(fishingMagnetEntity);
        }
    }


    @Nullable
    public PlayerEntity getPlayerOwner() {
        return this.getOwner() instanceof PlayerEntity playerEntity ? playerEntity : null;
    }


    @Override
    public boolean canUsePortals(boolean allowVehicles) {
        return false;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket(EntityTrackerEntry entityTrackerEntry) {
        Entity entity = this.getOwner();
        return new EntitySpawnS2CPacket(this, entityTrackerEntry, entity == null ? this.getId() : entity.getId());
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        if (this.getPlayerOwner() == null) {
            int i = packet.getEntityData();
            LOGGER.error("Failed to recreate fishing hook on client. {} (id: {}) is not a valid owner.", this.getWorld().getEntityById(i), i);
            this.discard();
        }
    }

    enum PositionType {
        ABOVE_WATER,
        INSIDE_WATER,
        INVALID
    }

    enum State {
        FLYING,
        HOOKED_IN_ENTITY,
        SINKING,
        GROUNDED
    }
}