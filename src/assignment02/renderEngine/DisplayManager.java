package assignment02.renderEngine;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_DEBUG_CONTEXT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFWErrorCallback.createPrint;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.Point;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.libffi.Closure;

public class DisplayManager {

	private static int width = 1280;
	public static Point dragStartPoint  = new Point();
	private static int height = 720;
	private static float aspect = 16.0f / 9.0f;

	public static boolean drag = false;
	public static boolean drop = false;

	private static GLFWKeyCallback keyCallback;
	@SuppressWarnings("unused")
	private static GLFWMouseButtonCallback mouseCallback;
	@SuppressWarnings("unused")
	private static GLFWWindowSizeCallback windowCallback;

	@SuppressWarnings("unused")
	private static GLFWErrorCallback errorCallback; // starke Referenz auf den
													// errorCallback ist wegen
													// Garbage Collection nötig
	@SuppressWarnings("unused")
	private static Closure debug; // starke Referenz auf debug ist wegen Garbage
									// Collection nötig
	// The window handle
	private static long window;

	public static long getWindow() {
		return window;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public static GLFWKeyCallback getKeyCallback() {
		return keyCallback;
	}

	public static void createDisplay() {

		glfwSetErrorCallback(errorCallback = createPrint((System.err)));

		// GLFW Initialisieren. Die meisten GLFW-Funktionen funktionieren vorher
		// nicht.
		if (glfwInit() != GL_TRUE)
			throw new IllegalStateException("Unable to initialize GLFW");

		// Konfigurieren des Fensters
		glfwDefaultWindowHints(); // optional, die aktuellen Window-Hints sind
		// bereits Standardwerte
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // Das Fenster bleibt nach dem
		// Erzeugen versteckt.
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // Die Fenstergröße lässt
													// sich verändern.
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GL_TRUE); // Windowshint für
															// den Debug Kontext

		// Das Fenster erzeugen.
		window = glfwCreateWindow(width, height,
				"Exercise 02 - The bunny is a lie WTF?!", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Key-Callback aufsetzen. Wird jedes mal gerufen, wenn eine Taste
		// gedrückt oder losgelassen wird.
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action,
					int mods) {
				if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
					glfwSetWindowShouldClose(window, GL_TRUE);
			}
		});

		glfwSetWindowSizeCallback(window,
				windowCallback = new GLFWWindowSizeCallback() {
					@Override
					public void invoke(long window, int width, int height) {
						updateWidthHeight();

					}
				});


		glfwSetMouseButtonCallback(window,
				mouseCallback = new GLFWMouseButtonCallback() {

					@Override
					public void invoke(long window, int button, int action,
							int mods) {
						System.out.println(action);
						if (action == 1) {
							drag = true;
							DoubleBuffer xpos = BufferUtils.createDoubleBuffer(1);
							DoubleBuffer ypos = BufferUtils.createDoubleBuffer(1);
							glfwGetCursorPos(DisplayManager.getWindow(), xpos, ypos);
							dragStartPoint = new Point((int)xpos.get(0), (int)ypos.get(0));

						}else{
							dragStartPoint =new Point();;
							drag=false;
						}

					}
				});

		// Auflösung des primären Displays holen.
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Fenster zentrieren
		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2,
				(GLFWvidmode.height(vidmode) - height) / 2);

		// Den GLFW Kontext aktuell machen.
		glfwMakeContextCurrent(window);
		// V-Sync aktivieren.

		// GL Kontext unter Berücksichtigung des Betriebssystems erzeugen.
		GL.createCapabilities();

		debug = GLUtil.setupDebugMessageCallback(); // after
		// GL.createCapabilities()
		System.out.println("Your OpenGL version is " + glGetString(GL_VERSION));
		// Das Fenster sichtbar machen.
		glfwShowWindow(window);

	}

	public static void updateDisplay() {
		glfwPollEvents();
		updateWidthHeight();
		glViewport(0, 0, width, height);
		glfwSwapBuffers(window);

	}

	private static void updateWidthHeight() {
		ByteBuffer bHeight = BufferUtils.createByteBuffer(4);
		ByteBuffer bWidth = BufferUtils.createByteBuffer(4);
		glfwGetWindowSize(window, bWidth, bHeight);

		height = bHeight.asIntBuffer().get(0);
		width = (int) (height*aspect);
	}

	public static void closeDisplay() {
		glfwDestroyWindow(window);
		keyCallback.release();

	}

}