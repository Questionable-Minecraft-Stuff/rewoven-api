/*
 * Copyright (c) 2021 Legacy Rewoven
 * Copyright (c) 2020 - 2021 Legacy Fabric
 * Copyright (c) 2016 - 2021 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.legacyrewoven.impl.networking.server;

import io.github.legacyrewoven.api.networking.v1.ServerPlayNetworking;
import io.github.legacyrewoven.impl.networking.GlobalReceiverRegistry;

import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.PacketByteBuf;

public final class ServerNetworkingImpl {
	public static final GlobalReceiverRegistry<ServerPlayNetworking.PlayChannelHandler> PLAY = new GlobalReceiverRegistry<>();

	public static ServerPlayNetworkAddon getAddon(ServerPlayNetworkHandler handler) {
		return ((ServerPlayNetworkHandlerExtensions) handler).getAddon();
	}

	public static Packet<?> createPlayC2SPacket(String channel, PacketByteBuf buf) {
		return new CustomPayloadS2CPacket(channel, buf);
	}
}
