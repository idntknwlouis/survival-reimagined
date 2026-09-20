# Survival Reimagined: Fabricated

Official Fabric port of [**Survival Reimagined**](https://modrinth.com/mod/survival-reimagined), based on the original NeoForge project by ShotUGMG.

This table tracks broad feature parity against the original mod.
> ✅ = implemented / working  
> 🟡 = partial / still needs parity work  
> ❌ = not ported yet  
> 🔎 = needs audit

| System | Functional | Recipes / Logic | Assets / UI | World Gen |
|---|:---:|:---:|:---:|:---:|
| Forge | ✅ | ✅ | ✅ | — |
| Metal Refining Table | ✅ | ✅ | ✅ | — |
| Mineral Processing Table | ✅ | ✅ | ✅ | — |
| Advanced Alloy Forge | ❌ | ❌ | ❌ | — |
| Rune Magic Infuser | 🟡 | 🟡 | ✅ | — |
| Clay / Tool Molds | ✅ | ✅ | ✅ | — |
| Plate Molds / Plates | ✅ | ✅ | ✅ | — |
| Rune Molds | ✅ | ✅ | ✅ | — |
| Bronze Tools | ✅ | ✅ | ✅ | — |
| Steel Tools | ✅ | ✅ | ✅ | — |
| Diamond Tool Assembly | ✅ | ✅ | ✅ | — |
| Silver / Argentite | ✅ | ✅ | ✅ | ✅ |
| Sapphire | ✅ | ✅ | ✅ | ✅ |
| Ruby | ✅ | ✅ | ✅ | ✅ |
| Amber | ✅ | ✅ | ✅ | ✅ |
| Titanium | ✅ | ✅ | ✅ | ✅ |
| Uraninite / Uranium | ✅ | ✅ | ✅ | ✅ |
| Turanite | ✅ | ✅ | ✅ | — |
| Tin | ✅ | ✅ | ✅ | ✅ |
| Manganese | ✅ | ✅ | ✅ | ✅ |
| Bronze | ✅ | ✅ | ✅ | — |
| Steel | ✅ | ✅ | ✅ | — |
| Diamond Plating | ✅ | ✅ | ✅ | — |
| Netherite Progression | ✅ | ✅ | ✅ | — |
| Rocks / Surface Rocks | ✅ | ✅ | ✅ | ✅ |
| Flint Placement | ✅ | ✅ | ✅ | ✅ |
| Shale Geology | ❌ | ❌ | ❌ | ❌ |
| Basalt Geology Extensions | ✅ | ✅ | ✅ | ✅ |
| Native Mineral Ores | ✅ | ✅ | ✅ | ✅ |
| Kimberlite Geology | ✅ | ✅ | ✅ | ✅ |
| Gem Polishing | ✅ | ✅ | ✅ | — |
| Rune Socketing | ✅ | ✅ | ✅ | — |
| Rune Effects / Tooltips | 🟡 | 🟡 | ✅ | — |
| Fuels / Forge Materials | ✅ | ✅ | ✅ | ✅ |
| Crops / Plants | 🟡 | 🟡 | 🟡 | 🟡 |
| Food / Cooking | 🔎 | 🔎 | 🔎 | — |
| Armor | 🔎 | 🔎 | 🔎 | — |
| Mobs / Entities | 🔎 | 🔎 | 🔎 | 🔎 |
| Carcass / Butchering | 🔎 | 🔎 | 🔎 | — |
| Structures | 🔎 | 🔎 | 🔎 | 🔎 |
| Biomes | 🔎 | 🔎 | 🔎 | 🔎 |
| Radiation Systems | ❌ | ❌ | ❌ | ❌ |
| Decorative / Utility Blocks | 🔎 | 🔎 | 🔎 | — |
| Sounds / Particles | 🟡 | — | 🟡 | — |
| Advancements | 🔎 | 🔎 | 🔎 | — |
| Compat Content | ❌ | ❌ | ❌ | ❌ |

## Current Roadmap

1. Finish Shale geology
2. Finish remaining native resource chains
3. Finish remaining machines
4. Broad core gameplay parity audit
5. Release-ready cleanup and testing
6. Compat content

## World Generation Notes

- Overworld depth parity is enabled for testing: `min_y = -256`, `height = 512` (Y -256 through Y 255), matching the original project.


## Recent Core Port Progress

- Titanium nugget/processing parity restored, including smelting/blasting, forge output, and common titanium tags.
- Dark Cinder block, loot, Nether worldgen, and Dark Cinder Coal forge recipe restored; Fuels / Forge Materials is now complete.

- Kimberlite geology and ore variants are implemented; missing English names were restored.
- Native mineral Stone/Deepslate ore families for Hematite, Magnetite, Calaverite, Pyrolusite, Uranophane, Ilmenite, Anthracite, and Liginite are ported with assets, loot, mining tags, worldgen, resource blocks, and recipes; runtime-tested successfully. Shale Uranophane generation is also wired.
- Filled rune tooltips restored for all Gold/Silver Sapphire, Amber, Diamond, Emerald, Ruby, and Lapis runes.
- Dedicated mold geometry restored for Hoe, Knife, Plate, Clay Plate, and Clay Ingot molds.
