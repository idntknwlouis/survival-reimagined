# Survival Reimagined — Fabric Port

Fabric port of **Survival Reimagined**, based on the original NeoForge/MCreator project by ShotUGMG.

This table tracks broad feature parity against the original mod.

> ✅ = implemented / working  
> 🟡 = partial / still needs parity work  
> ❌ = not ported yet  
> 🔎 = needs audit

| System | Functional | Recipes / Logic | World Gen | Assets / UI |
|---|:---:|:---:|:---:|:---:|
| Forge | ✅ | ✅ | — | ✅ |
| Metal Refining Table | ✅ | ✅ | — | ✅ |
| Mineral Processing Table | ❌ | ❌ | — | ❌ |
| Advanced Alloy Forge | ❌ | ❌ | — | ❌ |
| Rune Magic Infuser | ❌ | ❌ | — | ❌ |
| Clay / Tool Molds | ✅ | ✅ | — | 🟡 |
| Plate Molds / Plates | ✅ | ✅ | — | ✅ |
| Rune Molds | ✅ | ✅ | — | ✅ |
| Bronze Tools | ✅ | ✅ | — | ✅ |
| Steel Tools | ✅ | ✅ | — | ✅ |
| Diamond Tool Assembly | ✅ | ✅ | — | ✅ |
| Silver / Argentite | ✅ | ✅ | ✅ | ✅ |
| Sapphire | 🟡 | 🟡 | ✅ | ✅ |
| Ruby | 🟡 | 🟡 | ✅ | ✅ |
| Amber | 🟡 | 🟡 | ✅ | ✅ |
| Titanium | 🟡 | ✅ | ❌ | ✅ |
| Uraninite / Uranium | ✅ | ✅ | ✅ | ✅ |
| Turanite | ✅ | ✅ | — | ✅ |
| Tin | ✅ | ✅ | ✅ | ✅ |
| Manganese | ✅ | ✅ | ✅ | ✅ |
| Bronze | ✅ | ✅ | — | ✅ |
| Steel | ✅ | ✅ | — | ✅ |
| Diamond Plating | ✅ | ✅ | — | ✅ |
| Netherite Progression | ✅ | ✅ | — | ✅ |
| Rocks / Surface Rocks | ✅ | ✅ | ✅ | ✅ |
| Flint Placement | ✅ | ✅ | ✅ | ✅ |
| Shale Geology | ❌ | ❌ | ❌ | ❌ |
| Basalt Geology Extensions | ❌ | ❌ | ❌ | ❌ |
| Kimberlite Geology | ❌ | ❌ | ❌ | ❌ |
| Gem Polishing | ❌ | ❌ | — | ❌ |
| Rune Socketing | ✅ | ✅ | — | ✅ |
| Rune Effects / Tooltips | 🟡 | 🟡 | — | ✅ |
| Fuels / Forge Materials | ✅ | ✅ | 🟡 | ✅ |
| Crops / Plants | 🟡 | 🟡 | 🟡 | 🟡 |
| Food / Cooking | 🔎 | 🔎 | — | 🔎 |
| Armor | 🔎 | 🔎 | — | 🔎 |
| Mobs / Entities | 🔎 | 🔎 | 🔎 | 🔎 |
| Carcass / Butchering | 🔎 | 🔎 | — | 🔎 |
| Structures | 🔎 | 🔎 | 🔎 | 🔎 |
| Biomes | 🔎 | 🔎 | 🔎 | 🔎 |
| Radiation Systems | ❌ | ❌ | ❌ | ❌ |
| Decorative / Utility Blocks | 🔎 | 🔎 | — | 🔎 |
| Sounds / Particles | 🟡 | — | — | 🟡 |
| Advancements | 🔎 | 🔎 | — | 🔎 |
| Compat Content | ❌ | ❌ | ❌ | ❌ |

## Notes

- **Titanium** has functioning ore blocks and processing, but the current original 1.4 source does not define a Titanium worldgen feature, so none has been invented for the Fabric port.
- **Sapphire / Ruby / Amber** currently generate and drop their rough forms. Turning rough gems into finished gems belongs to the **Mineral Processing Table**, which is not ported yet.
- **Compat content** stays last; native Survival Reimagined content is the priority.
- Technical placed rock/flint block forms are hidden from the creative tab where appropriate, matching the original behavior.

## Current priority

1. Mineral Processing Table
2. Rough gem polishing
3. Shale / Basalt / Kimberlite geology
4. Remaining native resource chains
5. Remaining machines
6. Broad gameplay parity audit
7. Compat content last
