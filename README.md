# ProxyProtocolNF

A [NeoForge](https://neoforged.net) mod that adds support for the Proxy Protocol in Minecraft version `1.21.1`.

## Usage

This mod may be installed on either side, but will only actually do something on the server.\
Once installed, start the server and a configuration will be generated at `config/proxyprotocolnf-common.toml`:

```toml
# Determines whether the Proxy Protocol is enabled.
enabled = true
# A list of allowed proxy IP addresses that can connect to the server.
allowedProxyAddresses = ["127.0.0.1", "::1"]
```

> [!IMPORTANT]  
> **Only clients that use the Proxy Protocol will be able to connect if enabled.**

Please note that the CIDR notation is **not supported**, meaning you must specify the exact IP address.\
If left empty, any IP address will be allowed to connect to the server. **It is highly recommended that you set this.**

## Credits

A big thank you goes out to [PanSzelescik] for the Proxy Protocol Support mod, which this mod is based on.\
You can find the original mod [here][mod] on CurseForge, and the source code [here][source] on GitHub.

[PanSzelescik]: https://redirect.github.com/PanSzelescik
[mod]: https://www.curseforge.com/minecraft/mc-mods/proxy-protocol-support
[source]: https://github.com/PanSzelescik/proxy-protocol-support
