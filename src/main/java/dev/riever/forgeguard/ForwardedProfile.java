package dev.riever.forgeguard;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;

import com.github.bsideup.jabel.Desugar;
import com.mojang.authlib.properties.Property;

@Desugar
public record ForwardedProfile(InetSocketAddress address, UUID id, List<Property> properties) {

    public ForwardedProfile(InetAddress address, UUID id, List<Property> properties) {
        this(new InetSocketAddress(address, 0), id, properties);
    }
}
