/*
 * Instructions:
 *  - on Mac: VM Options: -Djava.library.path=libs/LWJGL/native -XstartOnFirstThread
 */
package assignment04;

import assignment04.entities.Entity;
import assignment04.renderEngine.DisplayManager;
import assignment04.renderEngine.Loader;
import assignment04.renderEngine.Renderer;
import assignment04.shaders.StaticShaderProgram;
import assignment04.entities.Camera;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.system.libffi.Closure;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;

public class MainGameLoop {

	@SuppressWarnings("unused")
	private static GLFWErrorCallback errorCallback;
	@SuppressWarnings("unused")
	private static GLFWKeyCallback keyCallback;
	@SuppressWarnings("unused")
	private static Closure debug;

    public static void main(String[] args) {

		// Setup window
		DisplayManager.createDisplay();

		// Setup renderer and shaders
		StaticShaderProgram shader = new StaticShaderProgram();
        Loader loader              = new Loader();
        Camera camera              = new Camera(loader);
        Renderer renderer          = new Renderer(camera);

        DisplayManager.setCamera(camera);
        DisplayManager.setRenderer(renderer);

        renderer.setCameraEntity(loader.loadEntity("camera"));
        renderer.setFrustumEntity(new Entity(camera.getFrustumModel()));

		ArrayList<Entity> entities = new ArrayList<Entity>();

        // load models //
		Entity ying = loader.loadEntity("dragon");
        Entity yang = new Entity(ying.getModel());

        // add models to list //
        entities.add(ying);
        entities.add(yang);

        while(glfwWindowShouldClose(DisplayManager.getWindow()) == GL_FALSE){
			// Clear framebuffer
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Manipulate model matrices //
			renderer.animate(entities);

            // Render models //
			renderer.render(entities, shader);

			DisplayManager.updateDisplay();
		}

		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
