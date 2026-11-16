package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;

public class ArmorWeightCalculations {
    private static final ResourceLocation WEIGHT_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("survival_reimagined", "armor_weight");

    private static final TagKey<Item> GOLD = createTag("gold");
    private static final TagKey<Item> BRONZE = createTag("bronze");
    private static final TagKey<Item> IRON = createTag("iron");
    private static final TagKey<Item> STEEL = createTag("steel");
    private static final TagKey<Item> DIAMOND = createTag("diamond");
    private static final TagKey<Item> NETHERITE = createTag("netherite");

    public static boolean WEIGHT_ENABLED = SurvivalReimaginedModConfig.VALUES.weight_system;

    private static TagKey<Item> createTag(String material) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "armor/" + material));
    }

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            for (ServerPlayer player : world.players()) {
                execute(player);
            }
        });
    }

    public static void execute(Player player) {
        AttributeInstance movement = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movement == null) return;
        movement.removeModifier(WEIGHT_MODIFIER_ID);

        if (!WEIGHT_ENABLED) return;
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (serverPlayer.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) return;
        double baseSpeed = movement.getBaseValue();
        double penalty = 0.0;

        if (baseSpeed - penalty > 0.085) penalty += countArmor(player, GOLD) * 0.00375;
        if (baseSpeed - penalty > 0.085) penalty += countArmor(player, BRONZE) * 0.00375;
        if (baseSpeed - penalty > 0.085) penalty += countArmor(player, IRON) * 0.00375;
        if (baseSpeed - penalty > 0.0825) penalty += countArmor(player, STEEL) * 0.004375;
        if (baseSpeed - penalty > 0.08) penalty += countArmor(player, DIAMOND) * 0.005;
        if (baseSpeed - penalty > 0.075) penalty += countArmor(player, NETHERITE) * 0.00625;

        if (penalty > 0) {
            movement.addTransientModifier(new AttributeModifier(WEIGHT_MODIFIER_ID, -penalty, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    private static int countArmor(Player player, TagKey<Item> tag) {
        int count = 0;

        EquipmentSlot[] slots = {
          EquipmentSlot.HEAD,
          EquipmentSlot.CHEST,
          EquipmentSlot.LEGS,
          EquipmentSlot.FEET
        };

        for (EquipmentSlot slot : slots) {
            ItemStack stack = player.getItemBySlot(slot);

            if (stack.is(tag)) count++;
        }
        return count;
    }
}
