package net.mcreator.survivalreimagined.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import com.google.common.collect.Iterables;

import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import net.mcreator.survivalreimagined.procedures.GasMaskHelmetTickEvent;

public class GasMaskItem extends ArmorItem{
    public static final Holder<ArmorMaterial> ARMOR_MATERIAL = Registry.registerForHolder(
            BuiltInRegistries.ARMOR_MATERIAL,
            ResourceLocation.fromNamespaceAndPath("survival_reimagined", "gas_mask_helmet"),
            new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {map.put(Type.HELMET, 2);}), 9,
                    SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(new ItemStack(Items.LEATHER)),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("survival_reimagined", "gasmask"))), 0.0f, 0.0f
            )
    );
    public GasMaskItem(ArmorItem.Type type, Item.Properties properties) {
        super(ARMOR_MATERIAL, type, properties);
    }
    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, tooltip, flag);

        if (context.registries() != null) {
            Optional<Holder.Reference<Enchantment>> enchantmentHolder = context.registries()
                    .lookupOrThrow(Registries.ENCHANTMENT).get(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("survival_reimagined", "perpetual_filtering")));

            int enchantmentLevel = enchantmentHolder.map(enchantment -> EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack)).orElse(0);
            if (enchantmentLevel != 0) {
                tooltip.add(Component.literal("§7 Protection: Permanent"));
            } else {
                double filerPercentage = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("FilterPercentage");
                tooltip.add(Component.literal("§7 Protection: " + Math.round(filerPercentage) + "%"));
            }
        }
    }

    public static class Helmet extends GasMaskItem {
        public Helmet() {
            super(Type.HELMET, new Item.Properties().durability(Type.HELMET.getDurability(11)));
        }

        @Override
        public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
            super.inventoryTick(itemstack, world, entity, slot, selected);
            if (entity instanceof Player player && Iterables.contains(player.getArmorSlots(), itemstack)) {
                GasMaskHelmetTickEvent.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
            }
        }
    }
}