package assignment05.entities;

import org.joml.Vector3f;

/**
 * Created by bryan on 11.11.2015.
 */
public class Material {
	private Vector3f emission = new Vector3f(0, 0, 0);
	private Vector3f ambient = new Vector3f(1, 1, 1);
	private Vector3f diffuse = new Vector3f(1, 1, 1);
	private Vector3f specular = new Vector3f(1, 1, 1);

	private float shininess = 1.0f;

	public static Material[] materials = new Material[] {
						new Material(
							new Vector3f(0, 0, 0),
							new Vector3f(0f, 0.0f, 0.0f),
							new Vector3f(1.0f, 1.0f, 1.0f),
							new Vector3f(1.0f, 1.0f,1.0f),
							60.0f),
						new Material(
							new Vector3f(0, 0, 0),
							new Vector3f(0.0f, 0.0f, 0.1f),
							new Vector3f(0.1f, 0.1f, 1.0f),
							new Vector3f(1.0f, 1.0f,1.0f),
							40.0f),
						new Material(
							new Vector3f(0, 0, 0),
							new Vector3f(0.0f, 0, 0),
							new Vector3f(1.0f, 0.0f, 0.0f),
							new Vector3f(1, 1, 1),
							10.0f),
						new Material(
							new Vector3f(0, 0, 0),
							new Vector3f(0, 0, 0),
							new Vector3f(0.0f, 1.0f, 0.0f),
							new Vector3f(0, 0, 1),
							20.0f) };
	public static int currentMaterial = 0;

	public Material(Vector3f emission, Vector3f ambient, Vector3f diffuse,
			Vector3f specular, float shininess) {
		this.emission = emission;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.shininess = shininess;
	}

	public Vector3f getEmission() {
		return this.emission;
	}

	public void setEmission(Vector3f emission) {
		this.emission = emission;
	}

	public Vector3f getAmbient() {
		return this.ambient;
	}

	public void setAmbient(Vector3f ambient) {
		this.ambient = ambient;
	}

	public Vector3f getDiffuse() {
		return this.diffuse;
	}

	public void setDiffuse(Vector3f diffuse) {
		this.diffuse = diffuse;
	}

	public Vector3f getSpecular() {
		return this.specular;
	}

	public void setSpecular(Vector3f specular) {
		this.specular = specular;
	}

	public float getShininess() {
		return this.shininess;
	}

	public void setShininess(float shininess) {
		this.shininess = shininess;
	}
}
