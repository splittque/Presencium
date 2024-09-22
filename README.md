# Presencium
## Descriprion
This is a simple mod that shows in Discord the status of minecraft with prepared states.
All languages that are represented in Discord are supported.

Supported Fabric and Forge.

![Alt text](https://cdn.modrinth.com/data/iNU1UQcw/images/ad292492d7ac969f767e59b69b5149416e02abbb.png)
### States
You can check out states in [gallery](https://modrinth.com/mod/presencium/gallery)

- In game: state, if you don't playing anywhere
- In singleplayer: state if you playing in singleplayer and world with open to lan
- In multiplayer: state if you playing in multiplayer
- In Realms: state if you playing in Realms

### Settings
You can check out settings in [gallery](https://modrinth.com/mod/presencium/gallery)

- Show server IP: hide or show server ip address
- RPC: on or off discord rich presence

## Build

Requires Java **21+**

Project uses gradle **8.8**

### IDE
1. Download project
2. Open project in your IDE
3. Wait initialization
3. Change the code

**common**
4. Build with task **build**

**fabric**
4. Test with task **runClient**
5. Build with task **build**

**forge**
4. Test with task **runClient**
5. Build with task **jarJar**

### non-IDE
1. Download project
2. Change the code

**common**
3. Clean with command **gradlew common:clean**
4. Build with command **gradlew common:build**

**fabric**
3. Clean with command **gradlew fabric:(version):clean**
4. Test with command **gradlew fabric:(version):runClient**
5. Build with command **gradlew fabric:(version):build**

**forge**
3. Clean with command **gradlew forge:(version):clean**
4. Test with command **gradlew forge:(version):runClient**
5. Build with command **gradlew forge:(version):jarJar**
