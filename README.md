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
| Rune Magic Infuser | ❌ | ❌ | ❌ | — |
| Clay / Tool Molds | ✅ | ✅ | 🟡 | — |
| Plate Molds / Plates | ✅ | ✅ | ✅ | — |
| Rune Molds | ✅ | ✅ | ✅ | — |
| Bronze Tools | ✅ | ✅ | ✅ | — |
| Steel Tools | ✅ | ✅ | ✅ | — |
| Diamond Tool Assembly | ✅ | ✅ | ✅ | — |
| Silver / Argentite | ✅ | ✅ | ✅ | ✅ |
| Sapphire | 🟡 | 🟡 | ✅ | ✅ |
| Ruby | 🟡 | 🟡 | ✅ | ✅ |
| Amber | 🟡 | 🟡 | ✅ | ✅ |
| Titanium | 🟡 | ✅ | ✅ | ❌ |
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
| Kimberlite Geology | 🟡 | 🟡 | ✅ | ✅ |
| Gem Polishing | 🟡 | ✅ | ✅ | — |
| Rune Socketing | ✅ | ✅ | ✅ | — |
| Rune Effects / Tooltips | 🟡 | 🟡 | ✅ | — |
| Fuels / Forge Materials | ✅ | ✅ | ✅ | 🟡 |
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
2. Port Kimberlite geology
3. Finish remaining native resource chains
4. Finish remaining machines
5. Broad core gameplay parity audit
6. Release-ready cleanup and testing
7. Compat content

## World Generation Notes

- Overworld depth parity is enabled for testing: `min_y = -256`, `height = 512` (Y -256 through Y 255), matching the original project.
