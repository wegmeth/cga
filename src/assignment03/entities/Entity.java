package assignment03.entities;

import org.joml.Matrix4f;

import assignment03.renderEngine.RawModel;

public class Entity {

    private RawModel model;
	private Matrix4f modelMatrix = new Matrix4f();

	public Entity(RawModel model) {
		this.model = new RawModel(model);
	}

	public RawModel getModel() {
		return this.model;
	}

	public Matrix4f getModelMatrix() {
		return this.modelMatrix;
	}

	public void setModelMatrix(Matrix4f m){
		this.modelMatrix = new Matrix4f(m);
	}
}
