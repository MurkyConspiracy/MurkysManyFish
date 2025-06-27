package com.disruptioncomplex.entity.data;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

import java.util.ArrayList;
import java.util.List;

public record MagnetFishingData(MagnetBobberData assetInfo) {
    public static final PacketCodec<RegistryByteBuf, MagnetFishingData> PACKET_CODEC =
            PacketCodec.tuple(
                    MagnetBobberData.PACKET_CODEC, MagnetFishingData::assetInfo,
                    MagnetFishingData::new
            );

    public record MagnetBobberData(float depth,
                                   float maxDepth,
                                   List<Float> availableDepths) {

        
        public static final PacketCodec<RegistryByteBuf, MagnetBobberData> PACKET_CODEC =
                PacketCodec.of(MagnetBobberData::encode, MagnetBobberData::decode);

        private static void encode(MagnetBobberData data, RegistryByteBuf buf) {
            buf.writeFloat(data.depth);
            buf.writeFloat(data.maxDepth);
            
            // Encode the list of depths
            buf.writeVarInt(data.availableDepths.size());
            for (Float depthValue : data.availableDepths) {
                buf.writeFloat(depthValue);
            }
        }

        private static MagnetBobberData decode(RegistryByteBuf buf) {
            float depth = buf.readFloat();
            float maxDepth = buf.readFloat();
            
            // Decode the list of depths
            int depthCount = buf.readVarInt();
            List<Float> availableDepths = new ArrayList<>(depthCount);
            for (int i = 0; i < depthCount; i++) {
                availableDepths.add(buf.readFloat());
            }
            
            return new MagnetBobberData(depth, maxDepth, availableDepths);
        }

        // Factory method to create data with depth filtering
        public static MagnetBobberData create(float depth, float maxDepth, List<Float> allDepths) {
            // Only include depths that are within maxDepth range
            List<Float> availableDepths = allDepths.stream()
                .filter(depthValue -> maxDepth >= depthValue)
                .sorted() // Optional: sort depths for consistent ordering
                .toList();
            
            return new MagnetBobberData(depth, maxDepth, availableDepths);
        }

    }
}