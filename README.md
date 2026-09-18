# The Chrispy Mod

[![Build](https://img.shields.io/github/actions/workflow/status/cjcj55/The-Chrispy-Mod-Multiloader/build.yml)](https://github.com/cjcj55/The-Chrispy-Mod-Multiloader/actions)

A Minecraft mod for **Fabric** and **NeoForge**, built from a single shared codebase.

- Minecraft: 26.3
- Loaders: Fabric, NeoForge
- Java: 25
- CurseForge: [The Chrispy Mod](https://www.curseforge.com/minecraft/mc-mods/chrispy-mod)

## Project layout

| Module      | Purpose                                                                 |
|-------------|-------------------------------------------------------------------------|
| `common`    | Shared mod code, compiled against vanilla Minecraft only                |
| `fabric`    | Fabric entrypoint and Fabric-specific code                              |
| `neoforge`  | NeoForge entrypoint, NeoForge-specific code, and data generation        |
| `buildSrc`  | Shared Gradle convention plugins used by all modules                    |

Most of the mod lives in `common`. It cannot access loader APIs, so anything that needs Fabric or NeoForge classes goes in the matching loader module. Loader modules can use everything in `common`.

Versions and mod metadata are configured in `gradle.properties`.

## Building and running

Requires a **Java 25** JDK (set as both the Gradle JVM and the project SDK in IntelliJ IDEA).

```
./gradlew build                  # build both loaders
./gradlew :fabric:runClient      # launch Minecraft with Fabric
./gradlew :neoforge:runClient    # launch Minecraft with NeoForge
./gradlew :neoforge:runClientData  # run data generation
```

Built jars are written to `fabric/build/libs` and `neoforge/build/libs`.

## License

Released under the [MIT License](LICENSE).

## Credits

Built on [Jared's MultiLoader Template](https://github.com/jaredlll08/MultiLoader-Template) as adapted by [moritz-htk](https://github.com/moritz-htk/MultiLoader-Template) to work with [Neo Loom](https://github.com/RelativityMC/neo-loom).
