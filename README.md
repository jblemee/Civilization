Civilization
==============
A Minecraft mod for the "Civilization" experiment

Download
-----------

This mod may be downloaded from any of the following sites:

- [Curse.com](https://www.curseforge.com/minecraft/mc-mods/civilization)
- [Modrinth.com](https://modrinth.com/mod/civilization)
- [Github Releases](https://github.com/jblemee/Civilization/releases)

Contributing information
=======

This repository can be directly cloned to get you started with the mod

If at any point you are missing libraries in your IDE, or you've run into problems you can
run `gradlew --refresh-dependencies` to refresh the local cache. `gradlew clean` to reset everything
{this does not affect your code} and then start the process again.

Additional Resources:
==========
Community Documentation: https://docs.neoforged.net/  
NeoForged Discord: https://discord.neoforged.net/

Configuration
=============

On the machine console, execute : `docker exec -i docker-minecraft-server-civilization-1  rcon-cli` to run MC Console.

## Luckperms

- Enable luckperms for the Operator and remove randomtp commands for all users:

```bash
luckperms user Plus200 permission set luckperms.* true
lp group default permission set randomtp.commands.* false
```

## RandomTP

- Disable direct access to TP. See [Luckperms](#luckperms)
- Enable random tp on first logging on `config/RandomTP/config.yaml`
- Set cooldowb between 2 TP

```yaml 
auto-teleportation: true
cooldown: 300
```

## Perma Death

Configure death duration on `config\permadeath-server.toml`


