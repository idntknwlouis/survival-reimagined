package net.mcreator.survivalreimagined.procedures;

import net.mcreator.survivalreimagined.network.PlayerVariables;
import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

public class GasMaskHelmetTickEvent {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null)
            return;

        PlayerVariables playerVars = SurvivalReimaginedModVariables.getPlayerVariables(entity);

        if (playerVars.GasMaskDamage == 0) {
            playerVars.GasMaskDamage = 200;
            playerVars._syncDirty = true;
        }
        if (playerVars.GasMaskDamage <= 200) {
            playerVars.GasMaskDamage = playerVars.GasMaskDamage - 1;
            playerVars._syncDirty = true;
        }
        if (playerVars.GasMaskHeal == 0) {
            playerVars.GasMaskHeal = 20;
            playerVars._syncDirty = true;
        }
        if (playerVars.GasMaskHeal <= 20) {
            playerVars.GasMaskHeal = playerVars.GasMaskHeal - 1;
            playerVars._syncDirty = true;
        }
        if (world.getBiome(BlockPos.containing(x,y,z)).is(ResourceKey.create(Registries.BIOME, ResourceLocation.parse("survival_reimagined:radiated_forest")))) {
            var enchantmentHolder = world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse("survival_reimagined:perpetual_filtering")));
            if (EnchantmentHelper.getItemEnchantmentLevel(enchantmentHolder, itemstack) != 0) {

                if (playerVars.GasMaskHeal == 0) {
                    if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("FilterPercentage") < 100) {
                        {
                            final String _tagName = "FilterPercentage";
                            final double _tagValue = (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("FilterPercentage") + 1);
                            CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
                        }
                        if (world instanceof Level _level) {
                            if (!_level.isClientSide()) {
                                _level.playSound(null, BlockPos.containing(x,y,z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("survival_reimagined:filter_mends")), SoundSource.MASTER,0.3f,1.5f);
                            } else {
                                _level.playLocalSound(x,y,z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("survival_reimagined:filter_mends")), SoundSource.MASTER, 0.3f, 1.5f, false);
                            }
                        }
                    }
                }
            }
            if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("FilterPercentage") > 0) {
                if (playerVars.GasMaskDamage == 0) {
                    {
                        final String _tagName = "FilterPercentage";
                        final double _tagValue = (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("FilterPercentage") - 1);
                        CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
                    }
                    if (world instanceof Level _level) {
                        if (!_level.isClientSide()) {
                            _level.playSound(null, BlockPos.containing(x,y,z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("survival_reimagined:gas_mask_breath")), SoundSource.MASTER, 0.8f, 0.8f);
                        } else {
                            _level.playLocalSound(x,y,z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("survival_reimagined:gas_mask_breath")), SoundSource.MASTER, 0.8f, 0.8f, false);
                        }
                    }
                }
            }
        }

    }
}
