package net.mcreator.survivalreimagined.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public enum SurvivalReimaginedModToolMaterials implements Tier {
    BRONZE (
           BlockTags.INCORRECT_FOR_IRON_TOOL,
            350,
            6.0F,
            0.0F,
            14,
            Ingredient.of(SurvivalReimaginedModItems.BRONZE_INGOT.get())
    ),
    STEEL (
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            475,
            4.0F,
            0.0F,
            16,
            Ingredient.of(SurvivalReimaginedModItems.STEEL_INGOT.get())
    );


    private final TagKey<Block> incorrectBlocksForDrops;
    private final int durability;
    private final float miningSpeed;
    private final float attackDamageBonus;
    private final int enchantmentValue;
    private final Ingredient repairIngredient;

    SurvivalReimaginedModToolMaterials(TagKey<Block> incorrectBlocksForDrops, int durability, float miningSpeed, float attackDamageBonus, int enchantmentValue, Ingredient repairIngredient) {
        this.incorrectBlocksForDrops = incorrectBlocksForDrops;
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackDamageBonus = attackDamageBonus;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }
    @Override public int getUses() { return this.durability; }
    @Override public float getSpeed() { return this.miningSpeed; }
    @Override public float getAttackDamageBonus() { return this.attackDamageBonus; }
    @Override public TagKey<Block> getIncorrectBlocksForDrops() { return this.incorrectBlocksForDrops; }
    @Override public int getEnchantmentValue() { return this.enchantmentValue; }
    @Override public Ingredient getRepairIngredient() {return this.repairIngredient; }

}
