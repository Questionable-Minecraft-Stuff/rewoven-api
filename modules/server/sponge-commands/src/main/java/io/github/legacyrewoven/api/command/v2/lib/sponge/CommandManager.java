/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.legacyrewoven.api.command.v2.lib.sponge;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import io.github.legacyrewoven.api.command.v2.lib.sponge.dispatcher.Dispatcher;
import io.github.legacyrewoven.api.permission.v1.PermissibleCommandSource;
import io.github.legacyrewoven.api.util.Location;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.World;

/**
 * A command dispatcher watches for commands (such as those said in chat)
 * and dispatches them to the correct command handler.
 */
public interface CommandManager extends Dispatcher {
	/**
	 * Register a given command using the given list of aliases.
	 *
	 * <p>If there is a conflict with one of the aliases (i.e. that alias
	 * is already assigned to another command), then the alias will be skipped.
	 * It is possible for there to be no alias to be available out of
	 * the provided list of aliases, which would mean that the command would not
	 * be assigned to any aliases.</p>
	 *
	 * <p>The first non-conflicted alias becomes the "primary alias."</p>
	 *
	 * @param callable  The command
	 * @param alias An array of aliases
	 * @return The registered command mapping, unless no aliases could be
	 * registered
	 */
	Optional<CommandMapping> register(CommandCallable callable, String... alias);

	/**
	 * Register a given command using the given list of aliases.
	 *
	 * <p>If there is a conflict with one of the aliases (i.e. that alias
	 * is already assigned to another command), then the alias will be skipped.
	 * It is possible for there to be no alias to be available out of
	 * the provided list of aliases, which would mean that the command would
	 * not be assigned to any aliases.</p>
	 *
	 * <p>The first non-conflicted alias becomes the "primary alias."</p>
	 *
	 * @param callable    The command
	 * @param aliases A list of aliases
	 * @return The registered command mapping, unless no aliases could be
	 * registered
	 * @throws IllegalArgumentException Thrown if {@code plugin} is not a
	 *                                  plugin instance
	 */
	Optional<CommandMapping> register(CommandCallable callable, List<String> aliases);

	/**
	 * Register a given command using a given list of aliases.
	 *
	 * <p>The provided callback function will be called with a list of aliases
	 * that are not taken (from the list of aliases that were requested) and
	 * it should return a list of aliases to actually register. Aliases may be
	 * removed, and if no aliases remain, then the command will not be
	 * registered. It may be possible that no aliases are available, and thus
	 * the callback would receive an empty list. New aliases should not be added
	 * to the list in the callback as this may cause
	 * {@link IllegalArgumentException} to be thrown.</p>
	 *
	 * <p>The first non-conflicted alias becomes the "primary alias."</p>
	 *
	 * @param callable     The command
	 * @param aliases  A list of aliases
	 * @param callback The callback
	 * @return The registered command mapping, unless no aliases could be
	 * registered
	 * @throws IllegalArgumentException Thrown if new conflicting aliases are
	 *                                  added in the callback
	 */
	Optional<CommandMapping> register(CommandCallable callable, List<String> aliases, Function<List<String>, List<String>> callback);

	/**
	 * Remove a command identified by the given mapping.
	 *
	 * @param mapping The mapping
	 * @return The previous mapping associated with the alias, if one was found
	 */
	Optional<CommandMapping> removeMapping(CommandMapping mapping);

	/**
	 * Gets the number of registered aliases.
	 *
	 * @return The number of aliases
	 */
	int size();

	/**
	 * Execute the command based on input arguments.
	 *
	 * <p>The implementing class must perform the necessary permission
	 * checks.</p>
	 *
	 * @param source    The caller of the command
	 * @param arguments The raw arguments for this command
	 * @return The result of a command being processed
	 */
	@Override
	CommandResult process(PermissibleCommandSource source, String arguments);

	/**
	 * Gets a list of suggestions based on input.
	 *
	 * <p>If a suggestion is chosen by the user, it will replace the last
	 * word.</p>
	 *
	 * @param source    The command source
	 * @param arguments The arguments entered up to this point
	 * @return A list of suggestions
	 */
	@Override
	List<String> getSuggestions(PermissibleCommandSource source, String arguments, @Nullable Location<World> targetPosition);
}
