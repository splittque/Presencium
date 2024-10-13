# Presencium
This is a simple mod that shows in Discord the status of minecraft with prepared states.
All languages that are represented in Discord are supported.

Supported Fabric and Forge.

![Alt text](https://cdn.modrinth.com/data/iNU1UQcw/images/ad292492d7ac969f767e59b69b5149416e02abbb.png)
#### States
You can check out states in [gallery](https://modrinth.com/mod/presencium/gallery)

- In game: state, if you don't playing anywhere
- In singleplayer: state if you playing in singleplayer and world with open to lan
- In multiplayer: state if you playing in multiplayer
- In Realms: state if you playing in Realms

#### Settings
You can check out settings in [gallery](https://modrinth.com/mod/presencium/gallery)

- Show server IP: hide or show server ip address
- RPC: on or off discord rich presence
- Debug: more information in logs

## Build
**Requires Java 21+**

**Project uses gradle 8.8**

1. Download and open project
2. Change the code

_for common:_
3. Clean with task **clean** _**(gradlew :common:clean)**_
4. Build with task **build** _**(gradlew :common:build)**_

_for fabric:_
4. Test with task **runClient** _**(gradlew :fabric:runClient)**_
5. Build with task **build** _**(gradlew :fabric:build)**_

_for forge:_
4. Test with task **runClient** _**(gradlew :forge:runClient)**_
5. Build with task **jarJar** _**(gradlew :forge:jarJar)**_