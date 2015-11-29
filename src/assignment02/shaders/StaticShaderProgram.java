package assignment02.shaders;

import org.joml.Matrix4f;

public class StaticShaderProgram extends ShaderProgram{
	
	private static final String VERTEX_FILE = "res/assignment02/shaders/vertexShader.glsl";
	private static final String FRAGMENT_FILE = "res/assignment02/shaders/fragmentShader.glsl";
	
	private int location_modelMatrix;
	private int location_projectionMatrix;
	
	public StaticShaderProgram() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_modelMatrix = super.getUniformLocation("modelMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
	}
	
	public void loadModelMatrix(Matrix4f matrix){
		super.loadMatrix(location_modelMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(location_projectionMatrix, projection);
	}
	

}
