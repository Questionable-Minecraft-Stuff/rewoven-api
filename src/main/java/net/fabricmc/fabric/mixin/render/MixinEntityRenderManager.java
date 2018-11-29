/*
 * Copyright (c) 2016, 2017, 2018 FabricMC
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

package net.fabricmc.fabric.mixin.render;

import net.fabricmc.fabric.client.render.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(EntityRenderManager.class)
public class MixinEntityRenderManager {
	@Shadow
	private Map<Class<? extends Entity>, EntityRenderer<? extends Entity>> RENDER_MAP;

	@Inject(method = "<init>(Lnet/minecraft/client/texture/TextureManager;Lnet/minecraft/client/render/item/ItemRenderer;)V", at = @At("RETURN"))
	public void init(TextureManager textureManager, ItemRenderer itemRenderer, CallbackInfo info) {
		EntityRendererRegistry.INSTANCE.initialize((EntityRenderManager) (Object) this, textureManager, itemRenderer, RENDER_MAP);
	}
}