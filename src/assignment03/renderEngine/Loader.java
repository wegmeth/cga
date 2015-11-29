package assignment03.renderEngine;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

import assignment03.entities.Entity;

public class Loader {

	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();

	public RawModel loadToVAO(float[] positions,float[] normals, int[] indices){
		// create VAO and assign data
		int vaoID = createVAO();
		createIndexBuffer(indices);
		createVertexBuffer(0,positions);
        createVertexBuffer(1,normals);
		unbindVAO();

		// save VAO in RawModel
		return new RawModel(vaoID,indices.length);
	}


	public void cleanUp(){
		for(int vao:this.vaos){
			glDeleteVertexArrays(vao);
		}
		for(int vbo:this.vbos){
			glDeleteBuffers(vbo);
		}
	}

	private int createVAO(){
		int vaoID = glGenVertexArrays();
		this.vaos.add(vaoID);
		glBindVertexArray(vaoID);
		return vaoID;
	}

	private void createVertexBuffer(int attributeNumber, float[] data){
		// generate and save new ID for the vertex buffer object
		int vboID = glGenBuffers();
		this.vbos.add(vboID);

		// activate buffer and upload data
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

		// tell OpenGL how to interpret the data
		glVertexAttribPointer(attributeNumber,3,GL_FLOAT,false,0,0);

		// unbind buffer
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	private void unbindVAO(){
		glBindVertexArray(0);
	}

	private void createIndexBuffer(int[] indices){
		// generate and save new ID for the index buffer object
		int vboID = glGenBuffers();
		this.vbos.add(vboID);

		// activate buffer and upload data
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}

	private IntBuffer storeDataInIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private FloatBuffer storeDataInFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public Entity loadEntity(String filename) {
		RawModel model = OBJLoader.loadObjModel(filename, this);
		Entity entity = new Entity(model);
		return entity;
	}

}
