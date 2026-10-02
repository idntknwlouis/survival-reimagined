package net.mcreator.survivalreimagined.util;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Small Fabric-side replacement for the DeferredHolder/DeferredItem/DeferredBlock
 * shape used by the original MCreator NeoForge sources.
 *
 * Keeping get() and getId() avoids rewriting every generated consumer while the
 * port is in progress.
 */
public final class RegistryEntry<T> implements Supplier<T> {
	private final ResourceLocation id;
	private final T value;

	public RegistryEntry(ResourceLocation id, T value) {
		this.id = Objects.requireNonNull(id);
		this.value = Objects.requireNonNull(value);
	}

	@Override
	public T get() {
		return value;
	}

	public ResourceLocation getId() {
		return id;
	}
}
