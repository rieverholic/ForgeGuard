package dev.riever.forgeguard;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.bsideup.jabel.Desugar;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;

import io.netty.util.NetUtil;

@Desugar
public record ForwardedData(String host, InetAddress address, UUID id, List<Property> properties) {

    private static final Gson GSON = new Gson();

    public static ForwardedData parse(String data) {
        String[] split = data.split("\0");
        if (split.length != 3 && split.length != 4) {
            return null;
        }
        try {
            byte[] bytes = NetUtil.createByteArrayFromIpAddressString(split[1]);
            if (bytes == null) {
                return null;
            }
            InetAddress address = InetAddress.getByAddress(bytes);
            UUID id = UUIDTypeAdapter.fromString(split[2]);
            List<Property> properties = new ArrayList<>();
            if (split.length == 4) {
                Property[] parsed = GSON.fromJson(split[3], Property[].class);
                if (parsed != null) {
                    for (Property property : parsed) {
                        if (property != null && property.getName() != null && property.getValue() != null) {
                            properties.add(property);
                        }
                    }
                }
            }
            return new ForwardedData(split[0], address, id, properties);
        } catch (IllegalArgumentException | JsonParseException | UnknownHostException e) {
            return null;
        }
    }
}
