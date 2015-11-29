package assignment04.shaders;

import org.joml.Matrix4f;

public class StaticShaderProgram extends ShaderProgram {
	
	private static final String VERTEX_FILE = "res/assignment04/shaders/vertexShader.glsl";
	private static final String FRAGMENT_FILE = "res/assignment04/shaders/fragmentShader.glsl";
	
	private int location_modelMatrix;
    private int location_viewMatrix;
    private int location_projectionMatrix;
	
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
		location_modelMatrix = super.getUniformLocation("modelMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
	}
	
	public void loadModelMatrix(Matrix4f matrix){
		super.loadMatrix(location_modelMatrix, matrix);
	}

    public void loadViewMatrix (Matrix4f view){
        super.loadMatrix(location_viewMatrix, view);
    }
	
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(location_projectionMatrix, projection);
	}

	

}
