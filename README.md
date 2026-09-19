# Survival Reimagined — Fabric Port

Fabric port of **Survival Reimagined**, based on the original NeoForge/MCreator project by ShotUGMG.

This README doubles as the porting checklist so it is easy to see what already matches the original mod and what still needs work.

> **Status legend**
>
> - ✅ Implemented / working in Fabric
> - 🟡 Partially implemented or still needs parity work
> - ⬜ Not ported yet
> - 🔎 Needs a dedicated upstream-vs-Fabric audit

## Current focus

The Forge and Metal Refining Table are now largely functional. The current porting focus is the **native resource / ore progression** that feeds those machines, followed by the remaining native machines and systems. Compat content should come later.

---

## Core machines

### Forge

- ✅ Forge block, block entity, menu, and GUI
- ✅ Original Forge GUI textures / progress visuals
- ✅ Fuel meter and processing progress
- ✅ Native fuel values
- ✅ Bronze alloying
- ✅ Steel alloying
- ✅ Diamond plating alloy
- ✅ Netherite alloying
- ✅ Titanium + Uranium -> Turanite alloying
- ✅ Rough ingot -> ingot molding
- ✅ Bronze / Steel / Diamond tool-part casting
- ✅ Metal plate casting
- ✅ Rune blank casting
- ✅ Quick Lime recipe
- ✅ Dark Cinder Coal recipe
- ✅ Metal/tool recycling / melt-back recipes
- ✅ Dark Cinder Coal fuel support
- ✅ Bronze / Steel / Diamond / Netherite plate support
- 🟡 Some inputs are not naturally obtainable yet because their ore/resource chains are still being ported
- ⬜ Create / other-mod compatibility recipes

### Metal Refining Table

- ✅ Block, block entity, menu, and GUI
- ✅ Original slot layout / GUI assets
- ✅ Hammer slot and hammer durability consumption
- ✅ Preview-output behavior
- ✅ Rough Copper -> Copper Ingot
- ✅ Rough Iron -> Iron Ingot
- ✅ Rough Gold -> Gold Ingot
- ✅ Rough Tin -> Tin Ingot
- ✅ Bronze tool assembly
- ✅ Steel tool assembly
- ✅ Diamond tool assembly
- ✅ Rune socketing
- ✅ Sapphire / Amber / Ruby rune inputs registered
- 🟡 Gem and Silver resource acquisition is not fully ported yet

### Other machines

- ⬜ Advanced Alloy Forge
- ⬜ Mineral Processing Table
- ⬜ Rune Magic Infuser
- 🔎 Any additional original machines not listed above need auditing

---

## Molds and forging progression

### Ingot / tool molds

- ✅ Wooden Ingot template
- ✅ Wet Ingot Clay Mold
- ✅ Fired Ingot Mold
- ✅ Wet Sword Blade Mold
- ✅ Wet Pickaxe Head Mold
- ✅ Wet Axe Head Mold
- ✅ Wet Shovel Head Mold
- ✅ Wet Hoe Blade Mold
- ✅ Wet Hammer Head Mold
- ✅ Wet Saw Blade Mold
- ✅ Wet Knife Mold
- ✅ Fired tool molds
- ✅ Wet molds dry naturally
- ✅ Wet molds break when stepped on and drop 2–3 Clay Balls
- ✅ Normal breaking of wet molds drops the wet mold item
- 🟡 Wet mold visuals may still need a full per-mold parity pass

### Plate molds

- ✅ Wooden Plate
- ✅ Wet Clay Metal Plate Mold
- ✅ Fired Metal Plate Mold
- ✅ Lava-assisted drying behavior for plate mold
- ✅ Bronze Plate
- ✅ Steel Plate
- ✅ Diamond Plate
- ✅ Netherite Plate
- ✅ Original-style plate inventory transforms restored

### Rune molds

- ✅ Wooden Rune
- ✅ Wet Rune Clay Mold
- ✅ Fired Rune Mold
- ✅ Gold empty rune casting
- ✅ Silver empty rune casting
- 🟡 Silver itself is registered but its full natural resource chain is not yet ported

---

## Tool parts and finished tools

### Bronze

- ✅ Sword Blade
- ✅ Pickaxe Head
- ✅ Axe Head
- ✅ Shovel Head
- ✅ Hoe Blade
- ✅ Hammer Head
- ✅ Saw Blade
- ✅ Knife Blade
- ✅ Copper Handle
- ✅ Small Copper Handle
- ✅ Bronze Sword
- ✅ Bronze Pickaxe
- ✅ Bronze Axe
- ✅ Bronze Shovel
- ✅ Bronze Hoe
- ✅ Bronze Hammer
- ✅ Bronze Saw
- ✅ Bronze Knife

### Steel

- ✅ Sword Blade
- ✅ Pickaxe Head
- ✅ Axe Head
- ✅ Shovel Head
- ✅ Hoe Blade
- ✅ Hammer Head
- ✅ Saw Blade
- ✅ Knife Blade
- ✅ Bronze Handle
- ✅ Small Bronze Handle
- ✅ Steel Sword
- ✅ Steel Pickaxe
- ✅ Steel Axe
- ✅ Steel Shovel
- ✅ Steel Hoe
- ✅ Steel Hammer
- ✅ Steel Saw
- ✅ Steel Knife

### Diamond

- ✅ Sword Blade
- ✅ Pickaxe Head
- ✅ Axe Head
- ✅ Shovel Head
- ✅ Hoe Blade
- ✅ Hammer Head
- ✅ Saw Blade
- ✅ Knife Blade
- ✅ Obsidian Handle
- ✅ Small Obsidian Handle
- ✅ Vanilla Diamond Sword assembly
- ✅ Vanilla Diamond Pickaxe assembly
- ✅ Vanilla Diamond Axe assembly
- ✅ Vanilla Diamond Shovel assembly
- ✅ Vanilla Diamond Hoe assembly
- ✅ Diamond Hammer
- ✅ Diamond Saw
- ✅ Diamond Knife

### Other tools / equipment

- ✅ Wooden Hammer
- ✅ Wooden Saw
- ✅ Wooden Knife
- 🔎 Chisels
- 🔎 Armor progression
- 🔎 Other specialty tools from the original
- 🔎 Tool repair / tag / special-speed parity still needs a dedicated pass

---

## Metals and metallurgy

### Working core metals

- ✅ Copper rough-ingot support
- ✅ Iron rough-ingot support
- ✅ Gold rough-ingot support
- ✅ Tin
- ✅ Manganese
- ✅ Bronze
- ✅ Steel
- ✅ Diamond Plated Ingot
- ✅ Rough Netherite
- ✅ Titanium
- ✅ Uranium
- ✅ Turanite

### Storage blocks

- ✅ Raw Manganese Block
- ✅ Manganese Block
- ✅ Bronze Block
- ✅ Steel Block
- ✅ Raw Tin Block
- ✅ Tin Block
- ✅ Raw Titanium Block
- ✅ Titanium Block
- ✅ Raw Uraninite Block
- ✅ Uranium Block
- ✅ Turanite Block
- 🔎 Remaining original metal storage blocks need auditing

### Not fully ported resource families

- ✅ Silver ore/resource chain (native Silver + Argentite, refining, storage, Forge ingot molding)
- ⬜ Redstone metal chain
- ⬜ Remaining original advanced alloy/material families
- ⬜ Create/compat metal families

---

## Ores and world generation

### Existing / ported

- ✅ Existing Fabric rock/flint/copper world features
- ✅ Tin ore family already present in the port
- ✅ Titanium Ore block
- ✅ Deepslate Titanium Ore block
- ✅ Titanium ore loot
- ✅ Raw Titanium Nuggets
- ✅ Uraninite Ore block
- ✅ Deepslate Uraninite Ore block
- ✅ Uraninite ore loot
- ✅ Raw Uraninite Nuggets
- ✅ Uraninite world generation using the original deep distribution

### Partial / pending

- 🟡 Titanium has ore blocks and loot, but the current original 1.4 source does not define a Titanium worldgen feature; no distribution has been invented for the Fabric port
- ⬜ Shale Titanium Ore
- ⬜ Shale Uraninite Ore
- ⬜ Basalt Uraninite Ore
- ⬜ Uranophane variants
- ⬜ Ilmenite variants
- ⬜ Silver / Argentite ore family
- 🟡 Sapphire ore family (stone/deepslate worldgen + Rough Sapphire; polishing awaits Mineral Processing Table)
- 🟡 Ruby ore family (stone/deepslate worldgen + Rough Ruby; polishing awaits Mineral Processing Table)
- 🟡 Amber ore family (stone/deepslate worldgen + Rough Amber; polishing awaits Mineral Processing Table)
- ⬜ Kimberlite ore/geology family
- ⬜ Remaining coal/mineral ore variants
- ⬜ Full Shale / Basalt geology and biome integration

---

## Rocks and placed-rock behavior

Player-facing rocks and their placed forms should behave as **one logical item/block pair**, matching the original.

- ✅ Stone Rock
- ✅ Andesite Rock
- ✅ Granite Rock
- ✅ Diorite Rock
- ✅ Dripstone Rock
- ✅ Calcite Rock
- ✅ Tuff Rock
- ✅ Mossy Stone Rock
- ✅ Netherrack Rock
- ✅ End Stone Rock
- ✅ Blackstone Rock
- ✅ Basalt Rock
- ✅ Deepslate Rock
- ✅ Obsidian Rock
- ✅ Technical placed-rock block items hidden from the creative tab
- ✅ Flint uses vanilla `minecraft:flint` as the player-facing item
- ✅ Vanilla Flint places the technical Flint block
- ✅ Technical Flint block item hidden from the creative tab
- 🔎 Remaining original rock types need auditing

---

## Runes and gems

### Rune blanks

- ✅ Empty Gold Rune
- ✅ Empty Silver Rune

### Gem inputs

- ✅ Sapphire item registered
- ✅ Amber item registered
- ✅ Ruby item registered
- ✅ Vanilla Diamond supported
- ✅ Vanilla Emerald supported
- ✅ Vanilla Lapis Lazuli supported

### Socketed runes

- ✅ Sapphire Silver Rune
- ✅ Sapphire Gold Rune
- ✅ Silver Amber Rune
- ✅ Gold Amber Rune
- ✅ Silver Diamond Rune
- ✅ Gold Diamond Rune
- ✅ Silver Emerald Rune
- ✅ Gold Emerald Rune
- ✅ Silver Ruby Rune
- ✅ Gold Ruby Rune
- ✅ Silver Lapis Rune
- ✅ Gold Lapis Rune
- ✅ Rune items use Rare rarity like the original
- 🟡 Original rune tooltip/effect system still needs parity auditing
- ⬜ Rune Magic Infuser

---

## Fuels and processing materials

- ✅ Lignite
- ✅ Small Lignite
- ✅ Lignite Block
- ✅ Anthracite
- ✅ Small Anthracite
- ✅ Anthracite Block
- ✅ Dark Cinder Coal
- ✅ Dark Cinder Powder registered
- ✅ Quick Lime
- 🟡 Natural acquisition for some processing materials is still pending

---

## UI / inventory parity

- ✅ Forge GUI
- ✅ Metal Refining Table GUI
- ✅ Plate inventory transforms fixed to match the original
- ✅ Technical rock/flint block items hidden from normal creative browsing
- 🔎 Creative-tab ordering still needs a full original-parity pass
- 🔎 Remaining machine GUIs not yet ported

---

## World / biome content

- ✅ Basic Fabric world features already ported for rocks/flint/copper/wild crops
- 🟡 Uraninite worldgen ported
- ⬜ Radiated Forest systems
- ⬜ Shale geology
- ⬜ Basalt geology extensions
- ⬜ Kimberlite geology
- ⬜ Remaining original custom world features
- 🔎 Structures
- 🔎 Biomes
- 🔎 Plants / vegetation
- 🔎 Cave features

---

## Gameplay systems still needing a broad audit

These are intentionally listed separately because the current porting work has focused on metallurgy and machines first.

- 🔎 Food / cooking systems
- 🔎 Farming / crops beyond currently ported wild crops
- 🔎 Mobs / entities
- 🔎 Carcass / butchering mechanics
- 🔎 Armor
- 🔎 Status effects
- 🔎 Advancements
- 🔎 Structures
- 🔎 Loot tables outside the currently ported progression
- 🔎 Sounds / particles
- 🔎 Special item tooltips and item properties
- 🔎 Radiation / reactor content
- 🔎 Decorative building blocks
- 🔎 Doors / trapdoors / utility blocks
- 🔎 Remaining recipes
- 🔎 Remaining tags
- 🔎 Remaining worldgen
- 🔎 Remaining client-side visuals

---

## Compatibility content

Port native Survival Reimagined content first.

- ⬜ Create compatibility
- ⬜ Other optional mod integrations
- ⬜ Compat-only recipes/items/blocks/tags

---

## Suggested port order

1. **Mineral Processing Table + rough gem polishing**
2. **Shale/Basalt/Kimberlite geology required by native ores**
3. **Remaining native metal/mineral chains**
4. **Mineral Processing Table**
5. **Advanced Alloy Forge**
6. **Rune Magic Infuser + rune effects/tooltips**
7. **Remaining tools, armor, and utility items**
8. **World/biome/structure content**
9. **Remaining gameplay systems**
10. **Compat content last**

---

## Notes for contributors

- The target branch for active port work is **`indev`**.
- Prefer matching the original behavior exactly rather than inventing replacement mechanics.
- If the original has an odd or incomplete behavior, document it before changing it.
- Keep native Survival Reimagined content separate from optional compat content.
- Update this checklist whenever a feature is added, fixed, or verified in-game.
