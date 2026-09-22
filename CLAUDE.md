# The Chrispy Mod (chrispymod)

Minecraft mod for **Fabric** and **NeoForge**, built from a MultiLoader template (fork of Jared's, adapted to use Neo Loom: `org.relativitymc.neo-loom`, a Fabric Loom fork that supports NeoForge). Targets Minecraft 26.3 on Java 25. Package root: `com.cjcj55.chrispymod`.

## Reference material

This mod is a rewrite of the author's earlier Chrispy Mod, so content is being ported from older repos rather than designed from scratch.

- Older Chrispy Mod repos (Minecraft/loader versions not yet confirmed; inspect `gradle.properties` in each before relying on it):
  - https://github.com/cjcj55/The-Chrispy-Mod-NeoForge (NeoForge MDK-based; likely the most recent)
  - https://github.com/cjcj55/The_Chrispy_Mod (about 61 commits, has a `bbmodels/` directory of Blockbench models)
  - https://github.com/cjcj55/The-Chrispy-Mod ("A vanilla-friendly mod adding more to the world around you"; single commit, includes changelog.txt and CREDITS.txt)
  - https://github.com/cjcj55/The-Chrispy-Mod-OLD-1.15.2 and https://github.com/cjcj55/TheChrispyMod-LEGACY (returned 404 when fetched; may be private, renamed or deleted)
- Meme mods (separate projects, low priority): https://github.com/cjcj55/Chrispys-Meme-Mod (about 6 commits) and https://github.com/cjcj55/Chrispy-Meme-Mod (single commit)
- CurseForge page (describes the original Forge 1.15.2 to 1.19.2 releases): https://www.curseforge.com/minecraft/mc-mods/chrispy-mod
- Original content: eight materials (Ruby, Opal, Blue Emerald, Cobalt, Tangerine, Paryth, Lightning, Flame) with tools and armor, Lightning/Flame swords, Fire/Lightning wands, an Enchanted Golden Apple recipe, eleven stonecutter brick variants, colored redstone lamps, and candy canes.
- Older repos use older Minecraft and loader APIs. Treat them as a source of ideas, assets and recipes, and rewrite code against the current 26.3 APIs instead of copying it.
- Do not mention the older versions in the README.

## Commands

- `./gradlew build` builds both loaders (this is what CI runs).
- `./gradlew :fabric:runClient` / `:neoforge:runClient` launches the game.
- `./gradlew :neoforge:runClientData` runs datagen (writes `common/src/main/generated`).
- Requires a Java 25 JDK for both Gradle and the IDE.

## Architecture

- `common/` holds all shared mod code. It compiles against vanilla only, so it must NOT import `net.fabricmc.*` or `net.neoforged.*`.
- `fabric/` and `neoforge/` are thin loaders: entrypoint plus loader-specific code. They may use anything in `common`; `common` may never use them.
- `common` is not a jar dependency. Its `src/main/java` and resources are added to each loader's own compile and resource tasks (`commonJava` / `commonResources` configurations in `buildSrc`). Each loader jar therefore contains its own copy of the common classes.
- Package layout under `com.cjcj55.chrispymod.common`: `block/{ModBlocks,AlloyFurnaceBlock,LavaSpongeBlock,WetLavaSpongeBlock}`, `block/entity/{ModBlockEntities,AlloyFurnaceBlockEntity}`, `item/{ModItems,ModToolMaterials,ModArmorMaterials,ModFoodProperties}`, `item/custom/*`, `menu/{ModMenuTypes,AlloyFurnaceMenu}`, `client/AlloyFurnaceScreen`, `recipe/{ModRecipeTypes,AlloyFurnaceRecipe,AlloyFurnaceRecipeInput}`, `tag/ModTags`, `creativetab/ModCreativeModeTabs`, `worldgen/{ModFeatures,ModPlacedFeatures}`.
- Blocks, items, block entity types and recipe types/serializers are registered with plain vanilla `Registry.register` from static initialization, so the loader must call `ModBlocks.register()`, `ModItems.register()`, `ModBlockEntities.register()`, `ModRecipeTypes.register()` at a moment each registry is open. Fabric does it in `onInitialize`. NeoForge freezes registries before mod construction, so `ChrispyModNeoForge` calls them from `RegisterEvent` (one call per registry key). Block items are registered by `ModItems.register()`, not by `ModBlocks`.
- `MenuType`'s constructor is private in 26.3, so unlike the above it can't be built with a plain `Registry.register`. Each loader builds it with its own helper (NeoForge's `IMenuTypeExtension`, Fabric's `ExtendedMenuType`) and assigns the result to the mutable `ModMenuTypes.ALLOY_FURNACE` field — the one deliberate exception to "no loader imports in common", and the template to follow if another menu is added. `AlloyFurnaceMenu`'s client-side constructor takes no loader data (an empty placeholder container that the server fills in via the normal slot sync, like vanilla's `FurnaceMenu`), so no extra networking was needed. Screen-to-`MenuType` binding is registered per loader too (NeoForge's `RegisterMenuScreensEvent`, Fabric's `MenuScreens.register` — public there only because fabric-api transitively widens it), from client-only entrypoints (NeoForge: the event only fires client-side; Fabric: `FabricClient` under the `"client"` key in `fabric.mod.json`).
- The single creative tab's contents are hand-ordered in `ModCreativeModeTabs` (materials, gear one row per material, food, decor). Any new item must be added there: `:neoforge:runClientData` fails if an item is missing or listed twice. The tab is described in `common` but built in each loader (`FabricCreativeModeTabs`, `NeoForgeCreativeModeTabs`), because vanilla's builder needs a fixed grid position and the loaders' builders auto-place. `CreativeModeTab.Output` is not accessible from vanilla-only code, so common passes items through a `Consumer`.
- Ore biome injection is the only worldgen that differs: `neoforge/.../datagen/ModBiomeModifiers` (data-driven) and `fabric/.../FabricWorldGeneration` (`BiomeModifications`). Both just iterate `ModPlacedFeatures.OVERWORLD` / `.NETHER`, so a new ore only needs entries in `ModFeatures`/`ModPlacedFeatures`, not loader changes.
- Mod ID lives in `ChrispyMod.MOD_ID` and `gradle.properties` (`mod_id`); keep them in sync.
- Loader-specific behaviour still needed from common code later (networking, config, events) has no general abstraction layer yet; the `ModMenuTypes` mutable-field pattern above is the closest precedent. Add a proper one (e.g. an interface in `common` implemented per loader and found via `ServiceLoader`) rather than importing loader classes directly.

## Build wiring

- All versions and mod metadata live in `gradle.properties`.
- `fabric.mod.json` and `neoforge.mods.toml` are templates: `${...}` placeholders are filled by `processResources` in `buildSrc/src/main/groovy/multiloader-common.gradle`.
- A new `gradle.properties` field is only available in those templates if it is also added to the `replaceProperties` map in that file (the comment in `gradle.properties` calls it `expandProps`, but the variable is `replaceProperties`).
- `buildSrc/.../multiloader-common.gradle` is shared by all modules; `multiloader-loader.gradle` is applied only to loaders.
- JEI is a dev-only dependency (`localRuntime` in `fabric/build.gradle` and `neoforge/build.gradle`, version `jei_version` in `gradle.properties`, from the Blamejared maven). It is loaded by `runClient` but is not in the built jars or the published POM. When bumping Minecraft, pick the JEI version listed for it at https://maven.blamejared.com/mezz/jei/.
- Each loader also has `compileOnly "mezz.jei:jei-<mc>-common-api"` so its own `compat/` package (`AlloyFurnaceRecipeCategory`, plugin class) can compile against the JEI API without bundling it. This code is duplicated between `fabric/.../compat` and `neoforge/.../compat` rather than shared through `common`, because plugin discovery differs per loader: NeoForge scans for the `@JeiPlugin` annotation, Fabric reads the `"jei_mod_plugin"` entrypoint key in `fabric.mod.json` (both implement plain `IModPlugin`). Neither is invoked at all if JEI isn't installed, so there's no hard dependency despite the direct `mezz.jei.api.*` imports.
- The Neo Loom plugin is on a floating `1.17-SNAPSHOT` version (root `build.gradle`), so plugin behaviour can change between builds.

## Access widening

- One file: `common/src/main/resources/chrispymod.classtweaker` (`classTweaker v1 official`).
- Fabric uses it directly (`accessWidener` in `fabric.mod.json`).
- NeoForge converts it to an access transformer at jar time via `convertAw2At` in `neoforge/build.gradle`. `neoforge.mods.toml` references `accesstransformer.cfg`, which is generated rather than checked in.

## Datagen

- Datagen runs from NeoForge only (`:neoforge:runClientData`); all providers live in `neoforge/.../neoforge/datagen`.
- Output goes to `common/src/main/generated`, which is committed (only `.cache/` is gitignored). `common` includes it as a resource dir, so Fabric gets the same assets and data. Regenerate and commit after changing content.
- Hand-written assets stay in `common/src/main/resources` (textures under `assets/chrispymod/textures/{item,block,entity/equipment}`). Everything else (models, item definitions, blockstates, lang, equipment assets, recipes, loot tables, tags, worldgen) is generated from code, so edit the providers, not the JSON.
- Names, models, loot and tags mostly iterate `ModBlocks.blocks()`, `ModItems.toolSets()` and `ModItems.armorSets()`, so a new plain block or tool set needs few provider changes. Special cases live in the providers (`ORE_DROPS`, recipe patterns, name overrides).
- In 26.3 loot tables, recipes, recipe advancements, features and placed features are datapack registries. They are generated with `RegistrySetBuilder` through `GatherDataEvent#createWorldRegistryObjects` / `createReloadableRegistryObjects`, restricted to the `chrispymod` namespace. Save every recipe with an explicit `chrispymod:` id: vanilla helpers such as `oreSmelting` and `nineBlockStorageRecipes` save under `minecraft:` and their output is silently dropped.
- `data/chrispymod/neoforge/biome_modifier` is generated into the shared folder and is inert on Fabric.

## Minecraft 26.3 API notes

- Inspect real signatures with `javap` on the jars under `~/.gradle/caches/fabric-loom/minecraftMaven/net/minecraft/`. The vanilla `minecraft-merged-deobf` jar hides methods that NeoForge makes public; datagen classes such as `ItemModelGenerators` must be inspected in the `nfrt-*minecraft-merged-deobf` jar.
- Entity types are in `EntityTypes` (not `EntityType`). Resource locations are `Identifier`. Tools are plain `Item`s built with `Item.Properties#sword/pickaxe/shovel/axe/hoe`; armor with `Item.Properties#humanoidArmor`. Item behavior hooks are `use`, `postHurtEnemy`, `inventoryTick(stack, ServerLevel, entity, slot)`.
- Features no longer have a `ConfiguredFeature` layer: `Registries.FEATURE`, `OreFeature`, `BlockReplacement`.
- Furnace fuel is a data component (`DataComponents.COOKING_FUEL`, a `CookingFuel` record), not a lookup table; `AlloyFurnaceBlockEntity.burnDuration` shows how to resolve it to a tick count outside vanilla's own furnace class (needs a throwaway `LootContext` built from `LootContextParamSets.EMPTY`). Setting `.cookingFuel(...)` on an `Item.Properties` (as `ModItems.HELLFIRE` does) makes that item valid fuel for vanilla furnaces and the alloy furnace alike, no extra wiring needed.
- Block entity persistence uses `ValueInput`/`ValueOutput` (`loadAdditional`/`saveAdditional`), not raw NBT `CompoundTag`. Block-changes-on-break drops go through `BlockEntity.preRemoveSideEffects(BlockPos, BlockState)`, not `Block.onRemove`, which no longer exists.
- `MenuType`, like several other registry object types, now has a private constructor; see the `ModMenuTypes` note above for how each loader builds one.
- The block model / GUI render pipeline was rewritten: `GuiGraphics` is gone, replaced by `GuiGraphicsExtractor` passed into `extractContents`/`extractRenderState`-style methods on `Screen`/`AbstractContainerScreen`. `blit(Identifier, x, y, width, height, u, v, textureWidth, textureHeight)` still has the old semantics (source rect in pixels, last two args normalize against the atlas size).
- The Kaupenjoe 26.X course repos (NeoForge and Fabric, Minecraft 26.2) are a good API reference but are one version behind and use loader-specific registration (`DeferredRegister`, NeoForge's transfer API) unsuitable for `common`; verify against the compiler and adapt to plain `Registry.register`.

## Known placeholders (not intentional)

- `gradle.properties`: `mod_description`, `mod_homepage`, `mod_github`, and `mod_icon` (points to `assets/examplemod/icon.png`, which does not exist) are still template values.
- `fabric.mod.json` has a placeholder `"suggests": {"another-mod": "*"}`.
- `forge_version*` properties are unused; the `forge` module is commented out in `settings.gradle`.
- There is no `pack.mcmeta` or mixin config yet, and `neoforge.mods.toml` still uses the deprecated `logoFile` key.
- Armor equipment assets have no `humanoid_baby` layer (no baby textures yet) and armor item icons have no trim overlay.
- `AlloyFurnaceScreen`'s progress/fuel indicators are plain colored bars, not sprites cut from `alloy_furnace_gui.png`; the sprite sheet's overlay layout wasn't known when this was written. The JEI recipe category's background blit (`AlloyFurnaceRecipeCategory.draw`) reuses the in-game GUI's slot area by coordinate guess and hasn't been visually checked.
- One alloy furnace recipe exists so far (`blue_emerald_alloying`: Diamond + Emerald → Blue Emerald); add more with `ModRecipeProvider.alloyFurnaceRecipe(...)`.
- `LICENSE` copyright holder is "Kyronis", not the mod author.

## Conventions

- Put new code in `common` by default; only go to a loader module when a loader API is required.
- Prefer editing existing files; keep loader modules minimal.
- Version bumps (Minecraft, Fabric, NeoForge) go in `gradle.properties`, including `minecraft_version_range`.
