# Note Block Range

Client-side Fabric mod for Minecraft 1.21.10 that changes only vanilla note
block sound range.

## What it changes

The mod detects sound ids matching:

```text
minecraft:block.note_block.*
```

For those sounds, it increases:

- the client-side attenuation distance used by `SoundEngine.play(...)`
- the integrated singleplayer server's packet broadcast range used by
  `ServerLevel.playSeededSound(...)`

The default range is 256 blocks.

No other game sounds are changed.

## Configuration

The hearing range can be changed from 16 to 512 blocks in 16-block steps.

If Mod Menu is installed, open:

```text
Mods -> Note Block Range -> Configure
```

The value is saved to:

```text
config/noteblockrange.json
```

Mod Menu is optional. Without it, the mod still works and uses the saved config
or the default value.

## Important limitation

Vanilla note blocks are triggered server-side in `NoteBlock.triggerEvent(...)`.
For normal instruments, Minecraft 1.21.10 calls `Level.playSeededSound(...)`
with volume `3.0f`.

On the server, `ServerLevel.playSeededSound(...)` sends a
`ClientboundSoundPacket` only to players inside `SoundEvent.getRange(volume)`.
For variable-range sounds this is:

```text
volume > 1.0 ? 16 * volume : 16
```

That means vanilla note block packets are sent only within 48 blocks.

In singleplayer, this mod also changes the integrated server's broadcast range,
so configured values above 48 blocks can work there. On an external unmodified
server, the client can still only change attenuation for packets it receives;
it cannot hear note block packets the remote server never sent.

## Build

```sh
./gradlew build
```

On Windows:

```powershell
.\gradlew.bat build
```

The built mod jar is written to:

```text
build/libs/noteblock-range-1.0.0.jar
```

## Test

Install Fabric Loader for Minecraft 1.21.10 and put the built jar into the
client's `mods` folder. Fabric API is not required. Mod Menu is optional and
only needed for the in-game configuration screen.

In singleplayer, set a range in Mod Menu, trigger vanilla note blocks, and move
away from them until the configured distance is reached. On an unmodified remote
server, clients beyond 48 blocks will not receive the sound packet.
