package assignment02.renderEngine;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class PyramidLoader {

	public static RawModel loadPyramidmodel(Loader loader) {

		float[] verticesArray = {
				-0.5f, 0f, 0.5f,
				0.5f, 0f, 0.5f,
				0.5f, 0f, -0.5f,
				-0.5f, 0f, -0.5f,
				0f, 1f, 0f
			};

		int[] indicesArray = {
				0, 1, 2,
				2, 3, 0,
				4, 0, 1,
				4, 1, 2,
				4, 2, 3,
				4, 3, 0
		};

		int vertexCount = indicesArray.length;

		IntBuffer positionsBuffer = BufferUtils.createIntBuffer(indicesArray.length);
		positionsBuffer.put(indicesArray);
		positionsBuffer.flip();

		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(verticesArray.length);
		verticesBuffer.put(verticesArray);
		verticesBuffer.flip();


		return loader.loadToVAO(verticesBuffer, positionsBuffer, vertexCount);
	}
}
