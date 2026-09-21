# Survival Reimagined: Fabricated

Official Fabric port of [**Survival Reimagined**](https://modrinth.com/mod/survival-reimagined), based on the original NeoForge project by ShotUGMG.

This table tracks broad feature parity against the original mod.
> ✅ = implemented / working  
> 🟡 = partial / still needs parity work  
> ❌ = not ported yet  
> 🔎 = needs audit

| System | Function | Recipes / Logic | Assets / UI | World Gen |
|---|:--------:|:---:|:---:|:---:|
| Forge |    ✅     | ✅ | ✅ | — |
| Metal Refining Table |    ✅     | ✅ | ✅ | — |
| Mineral Processing Table |    ✅     | ✅ | ✅ | — |
| Advanced Alloy Forge |    ✅     | ✅ | ✅ | — |
| Rune Magic Infuser |    ✅     | ✅ | ✅ | — |
| Clay / Tool Molds |    ✅     | ✅ | ✅ | — |
| Plate Molds / Plates |    ✅     | ✅ | ✅ | — |
| Rune Molds |    ✅     | ✅ | ✅ | — |
| Bronze Tools |    ✅     | ✅ | ✅ | — |
| Steel Tools |    ✅     | ✅ | ✅ | — |
| Diamond Tool Assembly |    ✅     | ✅ | ✅ | — |
| Silver / Argentite |    ✅     | ✅ | ✅ | ✅ |
| Sapphire |    ✅     | ✅ | ✅ | ✅ |
| Ruby |    ✅     | ✅ | ✅ | ✅ |
| Amber |    ✅     | ✅ | ✅ | ✅ |
| Titanium |    ✅     | ✅ | ✅ | ✅ |
| Uraninite / Uranium |    ✅     | ✅ | ✅ | ✅ |
| Turanite |    ✅     | ✅ | ✅ | — |
| Tin |    🟡     | 🟡 | 🟡 | 🟡 |
| Manganese |    🟡     | 🟡 | 🟡 | 🟡 |
| Bronze |    ✅     | ✅ | ✅ | — |
| Steel |    ✅     | ✅ | ✅ | — |
| Diamond Plating |    ✅     | ✅ | ✅ | — |
| Netherite Progression |    ✅     | ✅ | ✅ | — |
| Rocks / Surface Rocks |    ✅     | ✅ | ✅ | ✅ |
| Flint Placement |    ✅     | ✅ | ✅ | ✅ |
| Shale Geology |    ✅     | ✅ | ✅ | ✅ |
| Basalt Geology Extensions |    🟡     | ✅ | 🟡 | 🟡 |
| Native Mineral Ores |    🟡     | 🟡 | 🟡 | 🟡 |
| Kimberlite Geology |    🟡     | ✅ | 🟡 | 🟡 |
| Gem Polishing |    ✅     | ✅ | ✅ | — |
| Rune Socketing |    ✅     | ✅ | ✅ | — |
| Rune Effects / Tooltips |    ✅     | ✅ | ✅ | — |
| Fuels / Forge Materials |    ✅     | ✅ | ✅ | ✅ |
| Crops / Plants |    🟡    | 🟡 | 🟡 | 🟡 |
| Food / Cooking |    ❌    | ❌ | 🟡 | — |
| Armor |    ❌    | ❌ | ❌ | — |
| Mobs / Entities |    ❌    | ❌ | ❌ | ❌ |
| Carcass / Butchering |    ❌    | ❌ | ❌ | — |
| Structures |    ❌    | ❌ | ❌ | ❌ |
| Biomes |    —     | —  | —  | 🟡 |
| Radiation Systems |    ❌     | ❌ | ❌ | ❌ |
| Decorative / Utility Blocks |    🟡    | 🟡 | 🟡 | — |
| Sounds / Particles |    🟡    | — | 🟡 | — |
| Advancements |    🟡    | 🟡 | 🟡 | — |
| Compat Content |    ❌     | ❌ | ❌ | ❌ |

## Current Roadmap

1. Finish remaining crops, food, armor, mobs, structures, and radiation systems
2. Advancement parity and cleanup
3. Decorative / utility block parity
4. Release-ready testing and balancing
5. Compat content

## World Generation Notes

- Overworld depth parity is enabled for testing: `min_y = -256`, `height = 512` (Y -256 through Y 255), matching the original project.


## Recent Core Port Progress

- Tin parity audit completed: Tin/Cassiterite blocks, items, processing recipes, loot, mining tiers, and Cassiterite worldgen match the original; restored Cassiterite to `c:tin_ores` and the common ore tag so infused ore-mining bonuses apply correctly.
- Broad core gameplay parity audit completed. The remaining major gaps are now classified: Armor, Mobs / Entities, Carcass / Butchering, Structures, and Radiation are not yet ported; Food / Cooking is missing its gameplay/recipe systems but retains some crop/food assets; Advancements and Decorative / Utility Blocks are partial. Fabric currently has only the Radiant Forest of the original custom biomes, with Tropical Coast and Wisteria Forest still missing.
- Shale geology parity is runtime-verified: Radiated Shale surface layering, Shale underground replacement, mining/loot integration, and Radiant Forest Uraninite/Uranophane vein generation all work as intended.
- Rune Magic Infuser parity restored: original textured Fuse button, target/rune/lapis validation, Gold/Silver XP + lapis costs, infusion sounds, direct rune recognition, and common `c:runes` compatibility.
- Rune effects restored and hardened for Fabric tool/weapon detection, including Diamond Unbreaking progression, Ocean's Wrath, Sapphire underwater mining, Amber/Ruby/Lapis/Emerald effects, action-bar feedback, and gray rune effect tooltips.
- Advanced Alloy Forge parity restored: setup requirement, smoke/sounds, staged progress arrow, fuel gauge/capacity markers, reactor rods, Advanced Reactor Rod, upgrade slots, Fuel/Yield/Efficiency upgrades, and Block Packaging recipes.
- AAF upgrade item models/textures and explanatory tooltips restored.
- Uranium Rod parity restored with translucent rendering, radiation particles, and ambient hum.
- Titanium nugget/processing parity restored, including smelting/blasting, forge output, and common titanium tags.
- Dark Cinder block, loot, Nether worldgen, and Dark Cinder Coal forge recipe restored; Fuels / Forge Materials is now complete.
- Kimberlite geology and ore variants are implemented; missing English names were restored.
- Native mineral Stone/Deepslate ore families for Hematite, Magnetite, Calaverite, Pyrolusite, Uranophane, Ilmenite, Anthracite, and Liginite are ported with assets, loot, mining tags, worldgen, resource blocks, and recipes; runtime-tested successfully. Shale Uranophane generation is also wired.
- Core parity audit found additional active original geology still missing from Fabric: Cassiterite, Manganite, Azurite, Malachite, Nitre, Sulfur, Salt deposits, plus Basalt/Kimberlite stalagmite and stalactite formations. Spinel assets exist in the original but are not actively registered for worldgen, so Spinel is treated as dormant content for now.
- Native Copper and Native Gold worldgen parity restored, including the original extra Badlands Gold pass. Crop worldgen selectors were also corrected to the original biome lists, removing duplicate/unrestricted Rye, Spelt, and Wild Carrot generation.
- Dedicated mold geometry restored for Hoe, Knife, Plate, Clay Plate, and Clay Ingot molds.
