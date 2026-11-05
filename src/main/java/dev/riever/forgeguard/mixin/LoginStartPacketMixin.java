package dev.riever.forgeguard.mixin;

import net.minecraft.network.login.INetHandlerLoginServer;
import net.minecraft.server.network.NetHandlerLoginServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import dev.riever.forgeguard.ForgeGuardNetworkManager;
import dev.riever.forgeguard.ForwardedProfile;

@Mixin(net.minecraft.network.login.client.C00PacketLoginStart.class)
public class LoginStartPacketMixin {

    @Shadow
    private GameProfile field_149305_a;

    @Inject(method = "processPacket(Lnet/minecraft/network/login/INetHandlerLoginServer;)V", at = @At("HEAD"))
    private void onProcessPacket(INetHandlerLoginServer handler, CallbackInfo ci) {
        if (!(handler instanceof NetHandlerLoginServer)) {
            return;
        }
        ForgeGuardNetworkManager networkManager = (ForgeGuardNetworkManager) ((NetHandlerLoginServer) handler).field_147333_a;
        if (networkManager.forgeguard$hasForwardedProfile()) {
            ForwardedProfile profile = networkManager.forgeguard$getForwardedProfile();
            field_149305_a = new GameProfile(profile.id(), field_149305_a.getName());
            PropertyMap properties = field_149305_a.getProperties();

            for (Property property : profile.properties()) {
                properties.put(property.getName(), property);
            }
        }
    }
}
