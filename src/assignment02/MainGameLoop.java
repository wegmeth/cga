/*
 * Instructions:
 *  - on Mac: VM Options: -Djava.library.path=libs/LWJGL/native -XstartOnFirstThread
 */
package assignment02;


import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;

import java.nio.DoubleBuffer;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.system.libffi.Closure;

import assignment02.entities.Entity;
import assignment02.renderEngine.DisplayManager;
import assignment02.renderEngine.Loader;
import assignment02.renderEngine.Renderer;
import assignment02.shaders.StaticShaderProgram;

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
		Renderer renderer          = new Renderer(shader);

		// Load model
		Loader loader = new Loader();
		Entity entity = loader.loadPyramid(
				new Vector3f(0, -0.4f, -2),
				new Vector3f(0, 0, 0),
				new Vector3f(1f, 1f, 1f));

		while(glfwWindowShouldClose(DisplayManager.getWindow()) == GL_FALSE){
			entity.increaseRotation(new Vector3f(0, 0.1f, 0));
			renderer.render(entity, shader);
			DisplayManager.updateDisplay();
			if(DisplayManager.drag){
//				entity.getModelMatrix().rotate((float) Math.toRadians(0.06f),new Vector3f(0,0,1));
				rotate(entity);
			}
		}

		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

	private static void rotate(Entity entity) {
		DoubleBuffer xpos = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer ypos = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(DisplayManager.getWindow(), xpos, ypos);
		System.out.println(xpos.get(0));

		int x = (int) xpos.get(0) - DisplayManager.dragStartPoint.x;
		int y = (int) ypos.get(0) - DisplayManager.dragStartPoint.y;

		System.out.println(x);
		System.out.println(y);

		entity.getModelMatrix().rotate((float) Math.toRadians(0.001f),new Vector3f(x,0,y));
	}

}
