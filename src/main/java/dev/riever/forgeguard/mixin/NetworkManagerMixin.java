package dev.riever.forgeguard.mixin;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.riever.forgeguard.ForgeGuardNetworkManager;
import dev.riever.forgeguard.ForwardedProfile;

@Mixin(net.minecraft.network.NetworkManager.class)
public class NetworkManagerMixin implements ForgeGuardNetworkManager {

    @Unique
    private ForwardedProfile forgeguard$forwardedProfile;

    @Override
    public ForwardedProfile forgeguard$getForwardedProfile() {
        return forgeguard$forwardedProfile;
    }

    @Override
    public boolean forgeguard$hasForwardedProfile() {
        return forgeguard$forwardedProfile != null;
    }

    @Override
    public void forgeguard$setForwardedProfile(ForwardedProfile forwardedProfile) {
        forgeguard$forwardedProfile = forwardedProfile;
    }

    @Inject(method = "getSocketAddress", at = @At("HEAD"), cancellable = true)
    public void forgeguard$onGetSocketAddress(CallbackInfoReturnable<SocketAddress> cir) {
        InetSocketAddress address = forgeguard$forwardedProfile != null ? forgeguard$forwardedProfile.address() : null;
        if (address != null) {
            cir.setReturnValue(address);
        }
    }
}
