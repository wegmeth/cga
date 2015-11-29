package assignment02.renderEngine;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import assignment02.entities.Entity;

import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Loader {

	private int vaoID;
	private int bunnyIBO;
	private int vboID;

	public RawModel loadToVAO(FloatBuffer vertices, IntBuffer indices,
			int vertexCount) {
//		// TODO: setup VAO //
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);

		// TODO: setup VBO //
		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_DYNAMIC_DRAW);
		glVertexAttribPointer(0,3, GL_FLOAT, false,0, 0);
		glEnableVertexAttribArray(0);

//		// TODO: setup IBO //
		bunnyIBO = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bunnyIBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
//

		return new RawModel(vaoID, vertexCount);
	}

	public void cleanUp() {
		glDeleteVertexArrays(vaoID);
		glDeleteBuffers(bunnyIBO);
		glDeleteBuffers(vboID);
	}

	public Entity loadPyramid(Vector3f position, Vector3f orientation,
			Vector3f scale) {
		RawModel model = PyramidLoader.loadPyramidmodel(this);
		return new Entity(model, position, orientation, scale);
	}
}
