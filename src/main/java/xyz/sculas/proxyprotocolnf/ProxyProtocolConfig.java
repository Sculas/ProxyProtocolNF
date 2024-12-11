package xyz.sculas.proxyprotocolnf;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

@EventBusSubscriber(modid = ProxyProtocolNF.MODID, bus = EventBusSubscriber.Bus.MOD)
@SuppressWarnings("deprecation") // defineListAllowEmpty
public class ProxyProtocolConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    private static final ModConfigSpec.BooleanValue ENABLED = BUILDER.
            comment(" Determines whether the Proxy Protocol is enabled.").
            define("enabled", true);
    private static final ModConfigSpec.ConfigValue<List<? extends String>> PROXY_IPS = BUILDER.
            comment(" A list of allowed proxy IP addresses that can connect to the server.").
            defineListAllowEmpty("allowedProxyAddresses", List.of("127.0.0.1", "::1"), ProxyProtocolConfig::validateAddress);

    public static final ModConfigSpec SPEC = BUILDER.build();
    public static boolean isEnabled;
    public static List<InetAddress> allowedProxyAddresses;
    public static boolean hasProxyAddresses;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        isEnabled = ENABLED.get();
        allowedProxyAddresses = PROXY_IPS.get().stream().map(ProxyProtocolConfig::parseAddress).toList();
        hasProxyAddresses = !allowedProxyAddresses.isEmpty();
    }

    public static boolean isAddressAllowed(SocketAddress address) {
        if (!(address instanceof InetSocketAddress inetAddress)) return false;
        return allowedProxyAddresses.contains(inetAddress.getAddress());
    }

    private static boolean validateAddress(final Object address) {
        return ProxyProtocolConfig.parseAddress((String) address) != null;
    }

    private static InetAddress parseAddress(final String address) {
        try {
            return InetAddress.getByName(address);
        } catch (final Exception e) {
            return null;
        }
    }
}
