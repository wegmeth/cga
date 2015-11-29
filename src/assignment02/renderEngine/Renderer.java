package assignment02.renderEngine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;


import assignment02.entities.Entity;
import assignment02.shaders.StaticShaderProgram;

public class Renderer {
	private Matrix4f projectionMatrix;
	
	public Renderer(StaticShaderProgram shader){
		// set OpenGL parameters for rendering
		glClearColor(0, 0, 0, 1);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glEnable(GL_DEPTH_TEST);

		this.createProjectionMatrix();
	}

	public void animate(Entity entity) {
		entity.increasePosition(new Vector3f(0,0, 0));
		entity.increaseRotation(new Vector3f(0, 0.1f, 0));
	}

	public void render(Entity entity, StaticShaderProgram shader){

		// Clear framebuffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


		// start rendering //

		// use shader
		shader.start();
		// TODO: bind VAO and activate vbo
		glBindVertexArray(entity.getModel().getVaoID());
		
		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLE_STRIP, entity.getModel().getVertexCount(),GL_UNSIGNED_INT,0);

		// load model and projection matrix into shader
		shader.loadModelMatrix(entity.getModelMatrix());
		shader.loadProjectionMatrix(projectionMatrix);

		// TODO: render scene
		
		
		// a good programmer should clean up
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		shader.stop();
	}
	
	private void createProjectionMatrix(){

		projectionMatrix = new Matrix4f(
				1.428f, 0, 0, 0,
				0, 2.539f, 0, 0,
				0, 0, -1, -1,
				0, 0, -2, 0);
	}

}
