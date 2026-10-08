package com.cjcj55.chrispymod.common.item.custom;

import com.cjcj55.chrispymod.common.ChrispyMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.LodestoneTracker;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.function.Consumer;

public class WarpWandItem extends Item {
    /**
     * Enforced manually (not via {@link net.minecraft.world.item.ItemCooldowns}) because the vanilla item-cooldown
     * system blocks every interaction with the stack, including shift-click bind/unbind, for its whole duration.
     */
    private static final long TELEPORT_COOLDOWN_TICKS = 200;
    private static final double RANGE = 64.0;
    private static final String LAST_TELEPORT_TAG = "LastTeleportTick";
    private static final String TOOLTIP_BIND_KEY = "item." + ChrispyMod.MOD_ID + ".warp_wand.tooltip.bind";
    private static final String TOOLTIP_TELEPORT_KEY = "item." + ChrispyMod.MOD_ID + ".warp_wand.tooltip.teleport";
    private static final String BOUND_MESSAGE_KEY = "item." + ChrispyMod.MOD_ID + ".warp_wand.bound";
    private static final String UNBOUND_MESSAGE_KEY = "item." + ChrispyMod.MOD_ID + ".warp_wand.unbound";
    private static final String RECHARGING_MESSAGE_KEY = "item." + ChrispyMod.MOD_ID + ".warp_wand.recharging";

    public WarpWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay,
                                 Consumer<Component> tooltipAdder, TooltipFlag flag) {
        tooltipAdder.accept(Component.translatable(TOOLTIP_BIND_KEY).withStyle(ChatFormatting.GRAY));
        tooltipAdder.accept(Component.translatable(TOOLTIP_TELEPORT_KEY).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.SUCCESS;
        }
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            LodestoneTracker current = stack.get(DataComponents.LODESTONE_TRACKER);
            if (current != null && current.target().isPresent()) {
                stack.remove(DataComponents.LODESTONE_TRACKER);
                player.sendSystemMessage(Component.translatable(UNBOUND_MESSAGE_KEY));
            } else {
                GlobalPos warpPoint = GlobalPos.of(serverLevel.dimension(), player.blockPosition());
                stack.set(DataComponents.LODESTONE_TRACKER, new LodestoneTracker(Optional.of(warpPoint), false));
                player.sendSystemMessage(Component.translatable(BOUND_MESSAGE_KEY));
            }
            return InteractionResult.SUCCESS;
        }

        long gameTime = serverLevel.getGameTime();
        long lastTeleport = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getLongOr(LAST_TELEPORT_TAG, -TELEPORT_COOLDOWN_TICKS);
        if (gameTime - lastTeleport < TELEPORT_COOLDOWN_TICKS) {
            player.sendSystemMessage(Component.translatable(RECHARGING_MESSAGE_KEY));
            return InteractionResult.FAIL;
        }

        LodestoneTracker tracker = stack.get(DataComponents.LODESTONE_TRACKER);
        boolean teleported = tracker != null && tracker.target().isPresent()
                ? recall(serverLevel, player, tracker.target().get())
                : teleportToCrosshair(serverLevel, player);

        if (teleported) {
            stack.hurtAndBreak(1, player, hand);
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putLong(LAST_TELEPORT_TAG, gameTime));
            showCooldownSwipe(player, stack);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    /**
     * Sends the hotbar swipe animation directly instead of calling {@code ItemCooldowns#addCooldown}, which would
     * also gate every future interaction with the stack server-side (including shift-click bind/unbind) for the
     * whole duration. This packet only updates the client's own rendering-only cooldown copy.
     */
    private void showCooldownSwipe(Player player, ItemStack stack) {
        if (player instanceof ServerPlayer serverPlayer) {
            Identifier group = serverPlayer.getCooldowns().getCooldownGroup(stack);
            serverPlayer.connection.send(new ClientboundCooldownPacket(group, (int) TELEPORT_COOLDOWN_TICKS));
        }
    }

    private boolean recall(ServerLevel serverLevel, Player player, GlobalPos target) {
        ServerLevel targetLevel = serverLevel.getServer().getLevel(target.dimension());
        if (targetLevel == null) {
            return false;
        }
        teleport(player, serverLevel, targetLevel, Vec3.atBottomCenterOf(target.pos()));
        return true;
    }

    private boolean teleportToCrosshair(ServerLevel serverLevel, Player player) {
        HitResult hit = player.pick(RANGE, 1.0f, false);
        if (!(hit instanceof BlockHitResult blockHit) || hit.getType() != HitResult.Type.BLOCK) {
            return false;
        }
        BlockPos destination = blockHit.getBlockPos().relative(blockHit.getDirection());
        teleport(player, serverLevel, serverLevel, Vec3.atBottomCenterOf(destination));
        return true;
    }

    private void teleport(Player player, ServerLevel originLevel, ServerLevel targetLevel, Vec3 position) {
        Vec3 origin = player.position();
        playTeleportSound(originLevel, origin);
        player.teleport(new TeleportTransition(targetLevel, position, Vec3.ZERO,
                player.getYRot(), player.getXRot(), TeleportTransition.DO_NOTHING));
        playTeleportSound(targetLevel, position);
    }

    private void playTeleportSound(ServerLevel level, Vec3 position) {
        level.playSound(null, position.x, position.y, position.z,
                SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0f, 1.0f);
    }
}
