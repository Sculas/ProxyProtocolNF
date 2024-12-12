package xyz.sculas.proxyprotocolnf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.haproxy.HAProxyCommand;
import io.netty.handler.codec.haproxy.HAProxyMessage;
import xyz.sculas.proxyprotocolnf.mixin.ConnectionAccessor;

import java.net.InetSocketAddress;

public class ProxyProtocolHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Try to read the HAProxy header.
        if (!(msg instanceof HAProxyMessage message)) {
            super.channelRead(ctx, msg);
            return;
        }
        // Remove the handler once the header is read.
        ctx.pipeline().remove(this);
        // Check if the message is a proxy command.
        if (message.command() != HAProxyCommand.PROXY) return;
        // Check if the address is allowed.
        final var actualAddress = new InetSocketAddress(message.sourceAddress(), message.sourcePort());
        if (ProxyProtocolConfig.hasProxyAddresses) {
            final var proxyAddress = ctx.channel().remoteAddress();
            if (!ProxyProtocolConfig.isAddressAllowed(proxyAddress)) {
                if (ctx.channel().isOpen()) {
                    ProxyProtocolNF.LOGGER.warn("Connection from {} rejected (not allowed)", proxyAddress);
                    ctx.channel().close();
                }
                return;
            }
        }
        // Set the actual address.
        try {
            ((ConnectionAccessor) ctx.channel().pipeline().get("packet_handler")).setAddress(actualAddress);
        } catch (Exception e) {
            ProxyProtocolNF.LOGGER.error("Unable to set address due to exception", e);
        }
    }
}
