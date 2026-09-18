# The Chrispy Mod (chrispymod)

Minecraft mod for **Fabric** and **NeoForge**, built from a MultiLoader template (fork of Jared's, adapted to use Neo Loom: `org.relativitymc.neo-loom`, a Fabric Loom fork that supports NeoForge). Targets Minecraft 26.3 on Java 25. Package root: `com.cjcj55.chrispymod`.

## Reference material

This mod is a rewrite of the author's earlier Chrispy Mod, so content is being ported from older repos rather than designed from scratch.

- Older NeoForge version: https://github.com/cjcj55/The-Chrispy-Mod-NeoForge
- CurseForge page (describes the original Forge 1.15.2 to 1.19.2 releases): https://www.curseforge.com/minecraft/mc-mods/chrispy-mod
- Original content: eight materials (Ruby, Opal, Blue Emerald, Cobalt, Tangerine, Paryth, Lightning, Flame) with tools and armor, Lightning/Flame swords, Fire/Lightning wands, an Enchanted Golden Apple recipe, eleven stonecutter brick variants, colored redstone lamps, and candy canes.
- Older repos use older Minecraft and loader APIs. Treat them as a source of ideas, assets and recipes, and rewrite code against the current 26.3 APIs instead of copying it.
- Do not mention the older versions in the README.

## Commands

- `./gradlew build` builds both loaders (this is what CI runs).
- `./gradlew :fabric:runClient` / `:neoforge:runClient` launches the game.
- `./gradlew :neoforge:runClientData` runs datagen.
- Requires a Java 25 JDK for both Gradle and the IDE.

## Architecture

- `common/` holds all shared mod code. It compiles against vanilla only, so it must NOT import `net.fabricmc.*` or `net.neoforged.*`.
- `fabric/` and `neoforge/` are thin loaders: entrypoint plus loader-specific code. They may use anything in `common`; `common` may never use them.
- `common` is not a jar dependency. Its `src/main/java` and resources are added to each loader's own compile and resource tasks (`commonJava` / `commonResources` configurations in `buildSrc`). Each loader jar therefore contains its own copy of the common classes.
- Loader-specific behaviour needed from common code (registries, networking, config, events) has no abstraction layer yet. Add one (e.g. an interface in `common` implemented per loader and found via `ServiceLoader`) rather than importing loader classes.
- Entrypoints: `fabric/.../ChrispyModFabric` (`ModInitializer`) and `neoforge/.../ChrispyModNeoForge` (`@Mod`). Both call `ChrispyMod.initialize()` in `common`.
- Mod ID lives in `ChrispyMod.MOD_ID` and `gradle.properties` (`mod_id`); keep them in sync.

## Build wiring

- All versions and mod metadata live in `gradle.properties`.
- `fabric.mod.json` and `neoforge.mods.toml` are templates: `${...}` placeholders are filled by `processResources` in `buildSrc/src/main/groovy/multiloader-common.gradle`.
- A new `gradle.properties` field is only available in those templates if it is also added to the `replaceProperties` map in that file (the comment in `gradle.properties` calls it `expandProps`, but the variable is `replaceProperties`).
- `buildSrc/.../multiloader-common.gradle` is shared by all modules; `multiloader-loader.gradle` is applied only to loaders.
- The Neo Loom plugin is on a floating `1.17-SNAPSHOT` version (root `build.gradle`), so plugin behaviour can change between builds.

## Access widening

- One file: `common/src/main/resources/chrispymod.classtweaker` (`classTweaker v1 official`).
- Fabric uses it directly (`accessWidener` in `fabric.mod.json`).
- NeoForge converts it to an access transformer at jar time via `convertAw2At` in `neoforge/build.gradle`. `neoforge.mods.toml` references `accesstransformer.cfg`, which is generated rather than checked in.

## Datagen

- Datagen runs from NeoForge only (`neoforge/.../datagen/DataGenerators`, currently an empty stub).
- Output goes to `common/src/main/generated`, which is committed (only `.cache/` is gitignored). `common` includes it as a resource dir, so Fabric gets the generated assets too.
- Regenerate with `:neoforge:runClientData` and commit the result.

## Known placeholders (not intentional)

- `gradle.properties`: `mod_description`, `mod_homepage`, `mod_github`, and `mod_icon` (points to `assets/examplemod/icon.png`, which does not exist) are still template values.
- `fabric.mod.json` has a placeholder `"suggests": {"another-mod": "*"}`.
- `forge_version*` properties are unused; the `forge` module is commented out in `settings.gradle`.
- There is no `pack.mcmeta`, `assets/`, `data/`, or mixin config yet.
- `LICENSE` copyright holder is "Kyronis", not the mod author.

## Conventions

- Put new code in `common` by default; only go to a loader module when a loader API is required.
- Prefer editing existing files; keep loader modules minimal.
- Version bumps (Minecraft, Fabric, NeoForge) go in `gradle.properties`, including `minecraft_version_range`.
