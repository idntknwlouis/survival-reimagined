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
| Tin |    ✅     | ✅ | ✅ | ✅ |
| Manganese |    ✅     | ✅ | ✅ | ✅ |
| Bronze |    ✅     | ✅ | ✅ | — |
| Steel |    ✅     | ✅ | ✅ | — |
| Diamond Plating |    ✅     | ✅ | ✅ | — |
| Netherite Progression |    ✅     | ✅ | ✅ | — |
| Rocks / Surface Rocks |    ✅     | ✅ | ✅ | ✅ |
| Flint Placement |    ✅     | ✅ | ✅ | ✅ |
| Shale Geology |    ✅     | ✅ | ✅ | ✅ |
| Basalt Geology Extensions |    ✅     | ✅ | ✅ | ✅ |
| Native Mineral Ores |    ✅     | ✅ | ✅ | ✅ |
| Kimberlite Geology |    ✅     | ✅ | ✅ | ✅ |
| Gem Polishing |    ✅     | ✅ | ✅ | — |
| Rune Socketing |    ✅     | ✅ | ✅ | — |
| Rune Effects / Tooltips |    ✅     | ✅ | ✅ | — |
| Fuels / Forge Materials |    ✅     | ✅ | ✅ | ✅ |
| Crops / Plants |    ✅    | ✅ | ✅ | 🟡 |
| Food / Cooking |    🟡    | 🟡 | ✅ | — |
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

1. Finish Food / Cooking parity
2. Finish Armor, Mobs / Entities, Carcass / Butchering, Structures, and Radiation Systems
3. Advancement parity and cleanup
4. Decorative / utility block parity
5. Release-ready testing and balancing
6. Compat content

## World Generation Notes

- Overworld depth parity is enabled for testing: `min_y = -256`, `height = 512` (Y -256 through Y 255), matching the original project.


## Recent Core Port Progress

- Food / Cooking first-half parity pass restored the basic crop-derived food set: Wheat/Rye/Spelt flour and dough, Rye/Spelt bread, Corn on the Cob and cooked/burnt variants, plus Burnt/Charred Potato. Item nutrition, names, models, textures, and creative-tab visibility now match the original. Millstone/Campfire processing behavior and the broader meat/advanced cooking chains remain for the second half.
- Crops / Plants gameplay parity is runtime-verified: Rye, Spelt, Hemp, Wild Carrot, Wheat/Wild Wheat, Potatoes/Wild Potatoes, Strawberry, Raspberry, and full three-part Corn placement/growth/harvest now work, with restored crop block/item names and textures. Natural generation for the previously disabled Wild Wheat, Wild Potato, Strawberry, and Raspberry families remains pending, so the World Gen column stays partial.
- Native Mineral Ores are runtime-verified and complete: Hematite, Magnetite, Calaverite, Pyrolusite, Uranophane, Ilmenite, Anthracite, and Liginite Stone/Deepslate families, resource blocks, loot, recipes, mining/common tags, and worldgen work as intended.
- Kimberlite Geology is runtime-verified and complete: Kimberlite generation matches the original (`count 8`, radius `12`, `Y -256..-128`), Sapphire/Diamond/Emerald/Ruby/Lapis/Amber replacement targets and loot match, and Kimberlite stalagmites/stalactites use the vanilla-style single `kimberlite_pointed_stone` block.
- Native Mineral Ores parity pass restored the missing Desert/Badlands `extra_calaverite_feature` hook and completed the original common ore/metal-rock tags for Hematite/Magnetite, Calaverite, Ilmenite, and Uranophane families. Existing ore heights/counts and processing outputs match the original; runtime verification of the final tag/worldgen pass is pending.

- Basalt Geology Extensions are runtime-verified and complete: the original Basalt layer depth (`Y -128..-64`) is restored, Hematite, Magnetite, Calaverite, Uranophane, Ilmenite, Anthracite, and Liginite Basalt variants use their original shared ore features without duplicate Fabric-only passes, and vanilla-style Basalt pointed-stone stalagmite/stalactite worldgen works in-game.
- Manganese parity is runtime-verified: Manganese, Manganite, and Pyrolusite blocks/items, ore loot, smelting/blasting and compression recipes, common tags, AAF alloy inputs, and worldgen are aligned with the original. Fabric keeps the corrected `c:manganese_ores` spelling and includes it in `c:ores`; duplicate Fabric-only Basalt Manganite worldgen was removed.
- Tin parity is complete: Tin/Cassiterite blocks, items, processing recipes, loot, mining tiers, common tags, AAF inputs, and Cassiterite worldgen match the original; Cassiterite is restored to `c:tin_ores` and the common ore tag so infused ore-mining bonuses apply correctly.
- Broad core gameplay parity audit completed. The remaining major gaps are now classified: Armor, Mobs / Entities, Carcass / Butchering, Structures, and Radiation are not yet ported; Food / Cooking is missing its gameplay/recipe systems but retains some crop/food assets; Advancements and Decorative / Utility Blocks are partial. Fabric currently has only the Radiant Forest of the original custom biomes, with Tropical Coast and Wisteria Forest still missing.
- Shale geology parity is runtime-verified: Radiated Shale surface layering, Shale underground replacement, mining/loot integration, and Radiant Forest Uraninite/Uranophane vein generation all work as intended.
- Rune Magic Infuser parity restored: original textured Fuse button, target/rune/lapis validation, Gold/Silver XP + lapis costs, infusion sounds, direct rune recognition, and common `c:runes` compatibility.
- Rune effects restored and hardened for Fabric tool/weapon detection, including Diamond Unbreaking progression, Ocean's Wrath, Sapphire underwater mining, Amber/Ruby/Lapis/Emerald effects, action-bar feedback, and gray rune effect tooltips.
- Advanced Alloy Forge parity restored: setup requirement, held-item/empty-hand interaction handling, smoke/sounds, staged progress arrow, fuel gauge/capacity markers, reactor rods, Advanced Reactor Rod, upgrade slots, Fuel/Yield/Efficiency upgrades, and Block Packaging recipes.
- AAF upgrade item models/textures and explanatory tooltips restored.
- Uranium Rod parity restored with translucent rendering, radiation particles, and ambient hum.
- Titanium nugget/processing parity restored, including smelting/blasting, forge output, and common titanium tags.
- Dark Cinder block, loot, Nether worldgen, and Dark Cinder Coal forge recipe restored; Fuels / Forge Materials is now complete.
- Kimberlite geology and ore variants are implemented; missing English names were restored.
- Native mineral Stone/Deepslate ore families for Hematite, Magnetite, Calaverite, Pyrolusite, Uranophane, Ilmenite, Anthracite, and Liginite are ported with assets, loot, mining tags, worldgen, resource blocks, and recipes; runtime-tested successfully. Shale Uranophane generation is also wired.
- Core parity audit found additional active original geology still missing from Fabric: Cassiterite, Manganite, Azurite, Malachite, Nitre, Sulfur, Salt deposits, plus Basalt/Kimberlite stalagmite and stalactite formations. Spinel assets exist in the original but are not actively registered for worldgen, so Spinel is treated as dormant content for now.
- Native Copper and Native Gold worldgen parity restored, including the original extra Badlands Gold pass. Crop worldgen selectors were also corrected to the original biome lists, removing duplicate/unrestricted Rye, Spelt, and Wild Carrot generation.
- Dedicated mold geometry restored for Hoe, Knife, Plate, Clay Plate, and Clay Ingot molds.
