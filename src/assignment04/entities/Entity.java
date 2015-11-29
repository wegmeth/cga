package assignment04.entities;

import assignment04.renderEngine.RawModel;
import org.joml.Matrix4f;

public class Entity {

    private RawModel model;
	private Matrix4f modelMatrix = new Matrix4f();

	public Entity(RawModel model) {
		this.model = new RawModel(model);
	}

	public RawModel getModel() {
		return model;
	}

	public Matrix4f getModelMatrix() {
		return modelMatrix;
	}

	public void setModelMatrix(Matrix4f m){
		this.modelMatrix = new Matrix4f(m);
	}
}
