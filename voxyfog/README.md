# VoxyFog for Minecraft 26.3

Adds a configurable distance fog fade to Voxy's terrain. This port uses the
original source from [ricardotimmr/voxyfog-ricardo](https://github.com/ricardotimmr/voxyfog-ricardo)
at commit `04693f5` (VoxyFog 0.1.2). Original author: not_tim.

## Requirements

- Minecraft 26.3, Java 25 and Fabric Loader 0.20.1 or newer
- Fabric API for 26.3
- Voxy 0.2.20-beta and its matching Sodium version (0.9.2 for 26.3)
- Mod Menu 21.0.0 is optional and provides the settings screen

Voxy remains optional for loading the addon; without it, VoxyFog has no effect.
Voxy, Sodium, Fabric API and Mod Menu are not bundled in the addon JAR.

## Configuration

Open **Mods → VoxyFog → Configure**, or use these client commands:

```text
/voxyfog status
/voxyfog enabled true
/voxyfog enabled false
/voxyfog fogstart 48
```

Fog start is measured in chunks (2–256, default 48). Settings are saved to
`config/voxy-renderdistance-fog.json`; existing configuration files are retained.
The addon applies when Voxy's fog mode is **Fog** or **Fog and Fade**.
Modes **Fade** and **Off** retain Voxy's chosen behavior.

## Build and run

From the repository root, `./gradlew build` builds both mods as separate JARs.
This folder can also be built independently:

```powershell
cd voxyfog
.\gradlew.bat build
.\gradlew.bat runClient -PwithVoxy
```

`-PwithVoxy` adds the pinned Voxy, Sodium and Mod Menu versions to the development
runtime. Without it, `runClient` runs the addon with Fabric API alone.
The output is `build/libs/VoxyFog-0.2.0+26.3.jar` relative to this folder.

## Port changes

- Updated to the 26.3 `FogRenderer.setupFog` return value and GUI extraction API.
- Read Voxy's new fog modes instead of the removed `useEnvironmentalFog` field.
- Updated the internal fog cap injection for Voxy 0.2.20-beta.
- Kept the two mods independent; Note Block Range does not require Fabric API.
