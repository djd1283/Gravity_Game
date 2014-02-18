package gameengine;


/**
 * Used as an interface by the GameEngine to give an instance graphics, audio, and input capabilities
 * @author David
 *
 */
public interface Plugin
{
	/**
	 * Used to initialize OpenGL and other game components. Called by the GameEngine once,
	 * just before runPlugin is called by loop continuously.
	 */
	public void setupPlugin();
	
	/**
	 * Used by the GameEngine to give this Plugin continuous control over graphics and updating.
	 * After exiting this method, a number of milliseconds 1000 / 'framesPerSecond'(a parameter upon
	 * starting the GameEngine) is waited before this method is called again.
	 * @return A boolean indicating whether the plugin wishes to end the game engine. Returns true if the
	 * Plugin wishes to terminate the GameEngine.
	 */
	public boolean runPlugin();
}
