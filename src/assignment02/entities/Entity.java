package assignment02.entities;

import org.joml.Vector3f;
import org.joml.Matrix4f;

import assignment02.renderEngine.RawModel;

public class Entity {

	private RawModel model;
	
	private Vector3f position;
	private Vector3f rotation;
	private Vector3f scale;
	private Matrix4f modelMatrix = new Matrix4f(
			0.7604f, 0, -0.6494f, 0,
			0, 1, 0, 0,
			0.6494f, 0, 0.7604f, 0,
			0, -0.4f, -2f, 1
			);

	public Entity(RawModel model, Vector3f position, Vector3f rotation, Vector3f scale) {
		this.model = model;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public void increasePosition(Vector3f position){
		this.position.add(position);
	}

	public void increaseRotation(Vector3f rotation){
		this.rotation.add(rotation);
	}

	public RawModel getModel() {
		return model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}

	public Matrix4f getModelMatrix() {
		return this.modelMatrix;
	}
}
