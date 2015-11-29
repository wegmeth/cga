package assignment05.shaders;

import java.util.ArrayList;

import org.joml.Matrix4f;

import assignment05.entities.Material;
import assignment05.entities.PointLight;

public class StaticShaderProgram extends ShaderProgram {

	private static final String VERTEX_FILE = "res/assignment05/shaders/vertexShader.glsl";
	private static final String FRAGMENT_FILE = "res/assignment05/shaders/fragmentShader.glsl";
	private static final int MAX_LIGHTS = 8;

	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;

	private int[] location_lightPos;
	private int[] location_lightColDiffuse;
	private int[] location_lightColAmbient;
	private int[] location_lightColSpecular;


	private int location_matEmission;
	private int location_matAmbient;
	private int location_matDiffuse;
	private int location_matSpecular;
	private int location_matShininess;

	public StaticShaderProgram() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		this.location_modelMatrix = super.getUniformLocation("modelMatrix");
		this.location_viewMatrix = super.getUniformLocation("viewMatrix");
		this.location_projectionMatrix = super
				.getUniformLocation("projectionMatrix");


		this.location_lightPos = new int[MAX_LIGHTS];
		this.location_lightColDiffuse= new int[MAX_LIGHTS];
		this.location_lightColSpecular= new int[MAX_LIGHTS];
		this.location_lightColAmbient= new int[MAX_LIGHTS];

		// TODO: Aufgabe 5.1: Speicherplatz f�r uniform-Variablen holen (Licht und Material) und Adressen speichern
		this.location_matEmission = super.getUniformLocation("matEmission");
		this.location_matAmbient = super.getUniformLocation("matAmbient");
		this.location_matDiffuse = super.getUniformLocation("matDiffuse");
		this.location_matSpecular = super.getUniformLocation("matSpecular");
		this.location_matShininess = super.getUniformLocation("matShininess");


		// TODO: Aufgabe 5.3: Arrays mit MAX_LIGHTS initialisieren und für die
		// Adressen der Variablen verwenden
		for(int i =0; i<MAX_LIGHTS;i++){
			this.location_lightPos[i] = super.getUniformLocation("lightPos["+i+"]");
			this.location_lightColDiffuse[i] = super.getUniformLocation("lightColDiffuse["+i+"]");
			this.location_lightColAmbient[i] = super.getUniformLocation("lightColAmbient["+i+"]");
			this.location_lightColSpecular[i] = super.getUniformLocation("lightColSpecular["+i+"]");
		}

	}

	public void loadMaterial(Material mat){
		// TODO: Aufgabe 5.1: Werte der Materialeigenschaften in uniform-Variablen speichern
	    	super.loadFloat(this.location_matShininess, mat.getShininess());
	    	super.loadVector(this.location_matAmbient,mat.getAmbient());
	    	super.loadVector(this.location_matDiffuse,mat.getDiffuse());
	    	super.loadVector(this.location_matEmission,mat.getEmission());
	    	super.loadVector(this.location_matSpecular,mat.getSpecular());

	}

	public void loadLights(ArrayList<PointLight> lights) {
		// TODO: Aufgabe 5.1: Werte des ersten Lichts in uniform-Variablen
		// speichern

		// TODO: Aufgabe 5.3: Werte aller Lichter in uniform-Arrays speichern
		for(int i =0; i<MAX_LIGHTS; i++){
			if(!lights.get(i).isEnabled()) continue;
			super.loadVector(this.location_lightPos[i], lights.get(i).getLightPos());
			super.loadVector(this.location_lightColAmbient[i], lights.get(i).getLightColAmbient());
			super.loadVector(this.location_lightColDiffuse[i], lights.get(i).getLightColDiffuse());
			super.loadVector(this.location_lightColSpecular[i], lights.get(i).getLightColSpecular());
		}


	}

	public void loadModelMatrix(Matrix4f matrix) {
		super.loadMatrix(this.location_modelMatrix, matrix);
	}

	public void loadViewMatrix(Matrix4f view) {
		super.loadMatrix(this.location_viewMatrix, view);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(this.location_projectionMatrix, projection);
	}

}
