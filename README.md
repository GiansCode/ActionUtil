# ActionUtil Documentation

Documentation for the [ActionUtil plugin](https://www.spigotmc.org/resources/actionutil.60408/) created for [Avertix Management](https://avertix.io).

Time parsing is done using the [TimeAPI](https://www.spigotmc.org/resources/timeapi.23076/) library.

## Action Format

Actions are declared as strings in the following format:\
`[CHANCE=<chance>][DELAY=<delay>][ACTION] Arg1;Arg2;Arg3`

All text within square brackets is case insensitive.

* `ACTION` is the name of the action to execute
* Arguments are separated using a semicolon (`;`)
	* All arguments are required unless explicitly stated in the later documentation
	* When omitting an optional argument, place the two semicolons surrounding the argument directly next to eachother. For example, for an action with three arguments where the second is optional, you could do `[ACTION] FirstArg;;ThirdArg`
 
* The `CHANCE` and `DELAY` brackets are optional, with the default being that the action is executed 100% of the time with no delay.

* `<chance>` is an integer less than 100, and represents the percentage chance of the action to be executed
	- Examples: 50, 20, 77
	- Setting this value to any number above or equal to 100 will cause the action to be executed every time, and is redundant

* `<delay>` is a string representation of the time the server should wait before executing the action
	* Examples:
		* 3s = 3 seconds
		* 5m = 5 minutes
		* 2m8s = 2 minutes, eight seconds
	* Supported formats:
		* Seconds: s, sec, secs, second, seconds
		* Minutes: m, min, mins, minute, minutes
		* Hours: h, hr, hrs, hour, hours
		* A full list of supported time formats from the used library can be found [here](https://www.spigotmc.org/resources/timeapi.23076/), although it is unlikely that any time above hours would need to be used in-game

## Action List

Actions will be presented in the following format:

* Friendly action name
	* Action name (to be used in action strings)
	* Description
	* Arguments:
		* argument name: explanation if needed
		* *optional argument: explanation if needed [default value]
	* Example(s)

All string arguments are automatically coloured using the `&` character, and put through the PlaceholderAPI plugin to set placeholders.

* Message
	* `MESSAGE`
	* Sends a message to the player
	* Arguments:
		* message
	* Example: `[MESSAGE] &cHello &2World`

* Broadcast
	* `BROADCAST`
	* Sends a message to every online player
	* Arguments:
		* message
	* Example: `[BROADCAST] &aHello Players`

* Player Command
	* `PLAYERCOMMAND`
	* Makes the player execute a command
	* Arguments:
		* command
	* Example: `[PLAYERCOMMAND] balance`

* Console Command
	* `CONSOLECOMMAND`
	* Executes a command as the console
	* Arguments:
		* command
	* Example: `[CONSOLECOMMAND] eco give %player_name% 1000`

* Teleport
	* `TELEPORT`
	* Teleports a player to the given location
	* Arguments:
		* world: the world name
		* *x: [player's current X coorindate]
		* *y: [player's current Y coordinate]
		* *z: [player's current Z coordinate]
		* *yaw: [player's current yaw]
		* *pitch: [player's current pitch]
	* Example: `[TELEPORT] hub;0;60;0;90;90`

* Bungee
	* `BUNGEE`
	* Sends the player to the specified BungeeCord server
	* Arguments:
		* server
	* Example: `[BUNGEE] lobby`

* Sound
	* `SOUND`
	* Plays a sound to the player
	* Arguments:
		* sound: list found [here](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html) (may be different between versions)
		* volume
		* pitch
	* Example: `[SOUND] ENTITY_ARROW_HIT_PLAYER;2.0;2.0` 

* Sound Broadcast
	* `SOUNDBROADCAST`
	* Plays a sound to every online player
	* Arguments:
		* sound: list found [here](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html) (may be different between versions)
		* volume
		* pitch
	* Example: `[SOUNDBROADCAST] ENTITY_ARROW_HIT_PLAYER;2.0;2.0`

* Title Message
	* `TITLEMESSAGE`
	* Sends a title message to the player
	* Arguments:
		* title
		* subtitle
		* fade-in ticks
		* display ticks
		* fade-out ticks
	* Example: `[TITLEMESSAGE] Hello;%player_name%;20;60;20`

* Title Broadcast
	* `TITLEBROADCAST`
	* Sends a title message to every player
	* Arguments:
		* title
		* subtitle
		* fade-in ticks
		* display ticks
		* fade-out ticks
	* Example: `[TITLEBROADCAST] Hello;Players;20;60;20`

* Actionbar Message
	* `ACTIONBARMESSAGE`
	* Sends a message to the player through the actionbar
	* Arguments:
		* message
	* Example: `[ACTIONBARMESSAGE] &cThis shows in the actionbar`

* Actionbar Broadcast
	* `ACTIONBARBROADCAST`
	* Sends a message to every player in the server through the actionbar
	* Arguments:
		* message
	* Example: `[ACTIONBARBROADCAST] &cEveryone sees this in the actionbar`

* Centre Message
	* `CENTERMESSAGE`
	* Sends a centered message to the player\
	  NB: This is not guaranteed to display perfectly
	* Arguments:
		* message
	* Example: `[CENTERMESSAGE] &bThis is centered`

* Centre Broadcast
	* `CENTERBROADCAST`
	* Sends a centered message to every online player\
	  NB: This is not guaranteed to display perfectly
	* Arguments:
		* message
	* Example: `[CENTERBROADCAST] &bThis is centered for everyone`

* Json Message
	* `JSONMESSAGE`
	* Sends a Json message to the player. Executes Minecraft's /tellraw command, so usage may change between versions
	* Arguments:
		* json
	* Example: `[JSONMESSAGE] {"text":"Go to Google","color":"red","clickEvent":{"action":"open_url","value":"google.com"}}`

* Json Broadcast
	* `JSONBROADCAST`
	* Sends a Json message to every player on the server. Executes Minecraft's /tellraw command, so usage may change between versions
	* Arguments:
		* json
	* Example: `[JSONMESSAGE] {"text":"Go to Google","color":"red","clickEvent":{"action":"open_url","value":"google.com"}`

## Developer Integration

TODO

### Registering an Action

Declare a class that implements the `io.samdev.actionutil.action.Action` marker interface. 

The class must declare a static method named `execute`, where the first argument is an `org.bukkit.entity.Player`. Any arguments for your action may be added after this and will be parsed using the action input automatically provided that they are of a valid type (see next section).

Once your class is created, call `ActionUtil.registerActionClass()` with the class, the action name, and the argument types. For example:
```java
ActionUtil.registerActionClass(MyAction.class, "MYACTION", String.class, int.class);
```

The usage of the method in this way allows multiple actions to be registered to a single class.

### Class Translators

By default, ActionUtil is able to translate certain class types from strings to their object be used in actions.

Currently supported types:
- String
- Integer
- Decimal (using double)
- Boolean (using "true" and "false")
- org.bukkit.Sound
- org.bukkit.World

If you wish to create your own translator for a custom type, make a call to `ActionUtil.registerTranslator()` with an instance of an `io.samdev.actionutil.translator.Translator<T>`.

The only method needed to be fulfilled by the Translator interface is `T translate(Player, String) throws TranslationException`. As hinted by the method declaration, an `io.samdev.actionutil.translator.TranslationException` should be thrown in the case of a user entering an invalid input.
 
