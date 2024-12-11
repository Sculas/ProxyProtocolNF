package xyz.sculas.proxyprotocolnf;

import com.mojang.logging.LogUtils;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(ProxyProtocolNF.MODID)
public class ProxyProtocolNF {
    public static final String MODID = "proxyprotocolnf";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ProxyProtocolNF(ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, ProxyProtocolConfig.SPEC);
    }
}
