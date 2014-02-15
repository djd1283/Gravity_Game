package gameengine;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * Used to give an instance that implements the Plugin interface graphics, audio, and input capability through
 * easy-to-use inheritable methods.
 * @author David
 */
public class GameEngine
{
	private Plugin plugin;
	
	public GameEngine(Plugin plugin, int framesPerSecond) throws LWJGLException
	{
		this.plugin = plugin;
		setupDisplay();
		runGameEngine(framesPerSecond);
	}
	
	/**
	 * Used to initialize LWJGL using the Display class.
	 * @throws LWJGLException if the Light Weight Java Gaming Library detects an error.
	 */
	public void setupDisplay() throws LWJGLException
	{
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setupDisplayMode();
		Display.setFullscreen(true);
		Display.setVSyncEnabled(true);
		Display.create();
	}
	
	/**
	 * Used to detect the display mode needed to enable fullscreen for this GameEngine.
	 * Sets the display mode for the Display class.
	 * @throws LWJGLException if the Light Weight Java Gaming Library detects an error.
	 */
	public void setupDisplayMode() throws LWJGLException
	{
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		
        DisplayMode displayMode = null;
        DisplayMode[] modes = Display.getAvailableDisplayModes();

         for (int i = 0; i < modes.length; i++)
         {
             if (modes[i].getWidth() == dimension.width
             && modes[i].getHeight() == dimension.height
             && modes[i].isFullscreenCapable())
               {
                    displayMode = modes[i];
               }
         }
         Display.setDisplayMode(displayMode);
	}
	
	/**
	 * Used for setup and looping of the GameEngine and its target Plugin.
	 * 
	 */
	public void runGameEngine(int fps)
	{
		plugin.setupPlugin();
		
		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_E))
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			plugin.runPlugin();
			Display.update();
			Display.sync(1000 / fps);
		}
		Display.destroy();
	}
}
