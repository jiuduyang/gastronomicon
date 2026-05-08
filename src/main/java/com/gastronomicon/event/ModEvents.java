package com.gastronomicon.event;

import com.gastronomicon.Gastronomicon;
import com.gastronomicon.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Gastronomicon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    // ========== 龙息蜜饯：范围喷溅效果 ==========
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        
        LivingEntity attacker = event.getSource().getEntity() instanceof LivingEntity ? 
                (LivingEntity) event.getSource().getEntity() : null;
        
        if (attacker instanceof Player player) {
            CompoundTag nbt = player.getPersistentData();
            if (nbt.contains("gastronomicon.dragon_breath_time")) {
                long eatTime = nbt.getLong("gastronomicon.dragon_breath_time");
                long currentTime = player.level().getGameTime();

                if (currentTime - eatTime < 1200) {
                    BlockPos pos = event.getEntity().blockPosition();
                    AreaEffectCloud cloud = new AreaEffectCloud(
                            player.level(), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                    cloud.setRadius(3.0f);
                    cloud.setDuration(60);
                    cloud.setParticle(ParticleTypes.DRAGON_BREATH);
                    cloud.setWaitTime(0);
                    cloud.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 0));
                    
                    player.level().addFreshEntity(cloud);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) return;
        
        Player player = event.getEntity();
        ItemStack heldItem = player.getMainHandItem();

        if (heldItem.is(ModItems.VOID_CORE.get()) && player.isShiftKeyDown()) {
            CompoundTag nbt = player.getPersistentData();
            long lastDash = nbt.getLong("gastronomicon.void_dash_cooldown");
            long currentTick = player.level().getGameTime();

            if (currentTick - lastDash < 60) {
                player.displayClientMessage(Component.translatable("effect.gastronomicon.void_core.cooldown"), true);
                return;
            }

            Vec3 lookVec = player.getLookAngle();
            BlockPos forward1 = player.blockPosition().offset(
                    (int) Math.round(lookVec.x), 
                    (int) Math.round(lookVec.y), 
                    (int) Math.round(lookVec.z));
            BlockPos forward2 = forward1.offset(
                    (int) Math.round(lookVec.x), 
                    (int) Math.round(lookVec.y), 
                    (int) Math.round(lookVec.z));
            
            BlockState state1 = player.level().getBlockState(forward1);
            BlockState state2 = player.level().getBlockState(forward2);

            if (state1.isSolidRender(player.level(), forward1) && 
                    (state2.canBeReplaced() || state2.isAir())) {
                player.teleportTo(forward2.getX() + 0.5, forward2.getY(), forward2.getZ() + 0.5);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ENDER_PEARL_THROW, player.getSoundSource(), 1.0f, 1.0f);

                nbt.putLong("gastronomicon.void_dash_cooldown", currentTick);
            } else {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ENDERMAN_TELEPORT, player.getSoundSource(), 1.0f, 0.5f);
                player.displayClientMessage(Component.translatable("effect.gastronomicon.void_core.fail"), true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) return;
        
        Player player = event.player;
        CompoundTag nbt = player.getPersistentData();

        if (nbt.contains("gastronomicon.heartbroth_eaten_time")) {
            long eatTime = nbt.getLong("gastronomicon.heartbroth_eaten_time");
            long currentTime = System.currentTimeMillis();
            
            if (currentTime - eatTime < 5000) {
                if (player.isInWater() || player.isInWaterRainOrBubble()) {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
                    
                    if (player.level().getGameTime() % 10 == 0) {
                        ((ServerLevel) player.level()).sendParticles(ParticleTypes.SMOKE,
                                player.getX(), player.getY() + 1, player.getZ(), 5, 0.5, 0.5, 0.5, 0.1);
                    }
                }
            }
        }

        if (nbt.contains("gastronomicon.warden_eaten_time")) {
            long eatTime = nbt.getLong("gastronomicon.warden_eaten_time");
            long currentTick = player.level().getGameTime();

            if (currentTick - eatTime < 1200) {
                if (currentTick % 20 == 0) {
                    player.level().getEntitiesOfClass(LivingEntity.class, 
                            player.getBoundingBox().inflate(15.0),
                            entity -> entity instanceof net.minecraft.world.entity.monster.Monster)
                            .forEach(entity -> {
                                entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0));
                            });
                }

            }
        }

        if (nbt.contains("gastronomicon.blighted_end_time")) {
            long endTime = nbt.getLong("gastronomicon.blighted_end_time");
            long currentTick = player.level().getGameTime();
            
            if (currentTick >= endTime) {
                player.setHealth(1.0f);
                player.addEffect(new MobEffectInstance(MobEffects.WITHER, 600, 1));
                nbt.remove("gastronomicon.blighted_end_time");
            }
        }

        if (nbt.contains("gastronomicon.glutton_start")) {
            long startTime = nbt.getLong("gastronomicon.glutton_start");
            long currentTick = player.level().getGameTime();

            if (currentTick - startTime < 6000) {
                player.getFoodData().setSaturation(20.0f);
            } else {
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 3600, 2));
                nbt.remove("gastronomicon.glutton_start");
            }
        }

        if (nbt.contains("gastronomicon.prismarine_eaten_time")) {
            long eatTime = nbt.getLong("gastronomicon.prismarine_eaten_time");
            long currentTick = player.level().getGameTime();

            if (currentTick - eatTime < 1200) {
                if (player.isInWater()) {
                    player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 210, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 100, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 0));
                } else {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        
        if (event.getEntity() instanceof Player player) {
            CompoundTag nbt = player.getPersistentData();
            
            if (nbt.contains("gastronomicon.chronos_pos") && nbt.contains("gastronomicon.chronos_eaten_time")) {
                long eatTime = nbt.getLong("gastronomicon.chronos_eaten_time");
                long currentTick = player.level().getGameTime();

                if (currentTick - eatTime < 1200) {
                    event.setCanceled(true);

                    player.setHealth(player.getMaxHealth() * 0.5f);

                    int x = nbt.getInt("gastronomicon.chronos_x");
                    int y = nbt.getInt("gastronomicon.chronos_y");
                    int z = nbt.getInt("gastronomicon.chronos_z");
                    String dim = nbt.getString("gastronomicon.chronos_dim");

                    player.teleportTo(x + 0.5, y, z + 0.5);

                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 2));

                    player.getFoodData().setFoodLevel(1);

                    nbt.remove("gastronomicon.chronos_pos");
                    nbt.remove("gastronomicon.chronos_eaten_time");
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPickupXp(net.minecraftforge.event.entity.player.PlayerXpEvent.PickupXp event) {
        Player player = event.getEntity();
        CompoundTag nbt = player.getPersistentData();
        
        if (nbt.contains("gastronomicon.toffee_eaten_time")) {
            long eatTime = nbt.getLong("gastronomicon.toffee_eaten_time");
            long currentTick = player.level().getGameTime();

            if (currentTick - eatTime < 600) {
                int xpValue = event.getOrb().getValue();
                float currentSaturation = player.getFoodData().getSaturationLevel();
                player.getFoodData().setSaturation(currentSaturation + xpValue * 0.5f);

                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurtForHeal(LivingHurtEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        
        if (event.getSource().getEntity() instanceof Player player) {
            CompoundTag nbt = player.getPersistentData();
            
            if (nbt.contains("gastronomicon.glutton_start")) {
                long startTime = nbt.getLong("gastronomicon.glutton_start");
                long currentTick = player.level().getGameTime();

                if (currentTick - startTime < 6000) {
                    float healAmount = event.getAmount() * 0.2f;
                    player.heal(healAmount);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onItemUseStart(LivingEntityUseItemEvent.Start event) {
        if (event.getEntity() instanceof Player player) {
            CompoundTag nbt = player.getPersistentData();
            
            if (nbt.contains("gastronomicon.glutton_start")) {
                long startTime = nbt.getLong("gastronomicon.glutton_start");
                long currentTick = player.level().getGameTime();

                if (currentTick - startTime < 6000) {
                    ItemStack item = event.getItem();
                    if (!item.getItem().toString().contains("gastronomicon")) {
                        event.setCanceled(true);
                        player.displayClientMessage(
                                Component.translatable("effect.gastronomicon.gluttons_feast.forbidden"), true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onItemFinish(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack item = event.getItem();
            CompoundTag nbt = player.getPersistentData();
            long currentTick = player.level().getGameTime();

            if (item.is(ModItems.DRAGON_BREATH_CONFIT.get())) {
                nbt.putLong("gastronomicon.dragon_breath_time", currentTick);
            }

            if (item.is(ModItems.ANCIENT_HEARTBROTH.get())) {
                nbt.putLong("gastronomicon.heartbroth_eaten_time", System.currentTimeMillis());
            }

            if (item.is(ModItems.WARDENS_OCULUS.get())) {
                nbt.putLong("gastronomicon.warden_eaten_time", currentTick);
            }

            if (item.is(ModItems.BLIGHTED_GOLDEN_APPLE.get())) {
                player.setHealth(player.getMaxHealth());
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 4));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 2));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 1));

                nbt.putLong("gastronomicon.blighted_end_time", currentTick + 1200);
            }

            if (item.is(ModItems.GLUTTONS_FEAST.get())) {
                nbt.putLong("gastronomicon.glutton_start", currentTick);
            }

            if (item.is(ModItems.CHRONOS_CAKE.get())) {
                nbt.putLong("gastronomicon.chronos_eaten_time", currentTick);
                nbt.putInt("gastronomicon.chronos_x", player.getBlockX());
                nbt.putInt("gastronomicon.chronos_y", player.getBlockY());
                nbt.putInt("gastronomicon.chronos_z", player.getBlockZ());
                nbt.putString("gastronomicon.chronos_dim", player.level().dimension().location().toString());
            }

            if (item.is(ModItems.PHILOSOPHERS_TOFFEE.get())) {
                nbt.putLong("gastronomicon.toffee_eaten_time", currentTick);
            }

            if (item.is(ModItems.PRISMARINE_POWER_CUBE.get())) {
                nbt.putLong("gastronomicon.prismarine_eaten_time", currentTick);
            }
        }
    }
}
