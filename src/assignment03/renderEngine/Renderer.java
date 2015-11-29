package assignment03.renderEngine;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.MatrixStack;
import org.joml.Vector3f;

import assignment03.entities.Entity;
import assignment03.shaders.StaticShaderProgram;

public class Renderer {

	private Matrix4f projectionMatrix = new Matrix4f(0.8033f, 0, 0, 0, 0,
			1.326f, -0.3714f, -0.3714f, 0, -0.5304f, -0.9285f, -0.9285f, 0,
			-0.0000001702f, 5.365f, 5.385f);

	public Renderer() {
		// set OpenGL parameters for rendering
		glClearColor(0, 0, 0, 1);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glEnable(GL_DEPTH_TEST);
	}

	public void render(ArrayList<Entity> entities, StaticShaderProgram shader) {

		// start rendering //

		// use shader
		shader.start();

		for (Entity e : entities) {

			// bind VAO and activate vbo
			RawModel model = e.getModel();
			glBindVertexArray(model.getVaoID());
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);

			// load model and projection matrix into shader
			shader.loadModelMatrix(e.getModelMatrix());

			Matrix4f view = new Matrix4f();
			view.translate(0, -1.5f, -2.5f);
			Matrix4f viewProj = new Matrix4f(this.projectionMatrix);
			viewProj.mul(view);

			shader.loadProjectionMatrix(viewProj);

			// render scene
			glDrawElements(GL_TRIANGLES, model.getVertexCount(),
					GL_UNSIGNED_INT, 0);

			// a good programmer should clean up
			glDisableVertexAttribArray(0);
			glDisableVertexAttribArray(1);
			glBindVertexArray(0);

		}
		shader.stop();
	}

	long time = 0;
	private float rotationPerMsGlobal = (360.0f / 10f) / 1000f; // Winkel/ms
	private float rotationPerMsPair = (360.0f * 0.3f) / 1000f; // Winkel/ms
	private float currentRotationGlobal = 0f;
	private float currentRotationPair = 0f;

	public void animate(ArrayList<Entity> entities, long delta) {

		this.currentRotationGlobal += this.rotationPerMsGlobal * delta;
		this.currentRotationGlobal = this.currentRotationGlobal % 360;

		this.currentRotationPair += this.rotationPerMsPair * delta;
		this.currentRotationPair = this.currentRotationPair % 360;

		MatrixStack ms = new MatrixStack(3);
		float radius45 = (float) Math.toRadians(90);

		float[] pos = new float[2];
		pos[0] = 1;
		pos[1] = -1;

		Vector3f[] positions = new Vector3f[4];
		positions[0] = new Vector3f(2, 0, 2);
		positions[1] = new Vector3f(-2, 0, 2);
		positions[2] = new Vector3f(-2, 0, -2);
		positions[3] = new Vector3f(2, 0, -2);

		ms.rotateY((float) Math.toRadians(this.currentRotationGlobal));
		for (int pairs = 0; pairs < 4; pairs++) {
			ms.pushMatrix();
			ms.translate(positions[pairs]);
			ms.rotateY((float) Math.toRadians(this.currentRotationPair));
			for (int single = pairs*2; single <=  1+(pairs*2); single++) {
				ms.pushMatrix();
				ms.translate(pos[single % 2], 0, 0);
				ms.rotateY(radius45 * pos[single % 2]);

				entities.get(single).setModelMatrix(ms.getDirect());
				ms.popMatrix();
			}
			ms.popMatrix();
		}
	}

	// TODO: Aufgabe 2: Lassen sie das Paar tanzen (rotieren um die Y-Achse)

	// TODO: Aufgabe 3: Rendern sie 2x2 Paare (also insgesamt 8) von
	// Armadillos, die jeweils f�r sich tanzen

	// TODO: Aufgabe 4: Lassen Sie die gesamte Szene zus�tzlich rotieren
	// (ergibt einen sch�nen Walzer)
	// }
}
