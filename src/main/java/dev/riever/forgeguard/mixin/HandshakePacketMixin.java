package dev.riever.forgeguard.mixin;

import java.io.IOException;

import net.minecraft.network.PacketBuffer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import dev.riever.forgeguard.ForgeGuardHandshakePacket;
import dev.riever.forgeguard.ForwardedData;

@Mixin(net.minecraft.network.handshake.client.C00Handshake.class)
public class HandshakePacketMixin implements ForgeGuardHandshakePacket {

    @Unique
    private ForwardedData forgeguard$forwardedData;

    @Redirect(
        method = "readPacketData",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/PacketBuffer;readStringFromBuffer(I)Ljava/lang/String;"))
    private String onReadPacketData(PacketBuffer buf, int maxLength) throws IOException {
        String data = buf.readStringFromBuffer(Short.MAX_VALUE);
        forgeguard$forwardedData = ForwardedData.parse(data);
        if (forgeguard$forwardedData != null) {
            String host = forgeguard$forwardedData.host();
            if (host.length() > maxLength) {
                throw new IOException("Host too long (" + host.length() + " > " + maxLength + ")");
            }
            return host;
        }
        if (data.length() > maxLength) {
            throw new IOException("Packet data too long (" + data.length() + " > " + maxLength + ")");
        }
        return data;
    }

    @Override
    public ForwardedData forgeguard$getForwardedData() {
        return forgeguard$forwardedData;
    }
}
