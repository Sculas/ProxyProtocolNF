package xyz.sculas.proxyprotocolnf.mixin;

import io.netty.channel.Channel;
import io.netty.handler.codec.haproxy.HAProxyMessageDecoder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.sculas.proxyprotocolnf.ProxyProtocolConfig;
import xyz.sculas.proxyprotocolnf.ProxyProtocolHandler;

@Mixin(targets = "net/minecraft/server/network/ServerConnectionListener$1")
public class ServerConnectionListenerMixin {
    @Inject(method = "initChannel(Lio/netty/channel/Channel;)V", at = @At("TAIL"))
    private void injectHandler(Channel channel, CallbackInfo ci) {
        if (!ProxyProtocolConfig.isEnabled) return;
        channel.pipeline().
                addAfter("timeout", "haproxy-decoder", new HAProxyMessageDecoder()).
                addAfter("haproxy-decoder", "haproxy-handler", new ProxyProtocolHandler());
    }
}
