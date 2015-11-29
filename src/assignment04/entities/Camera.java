package assignment04.entities;

import assignment04.renderEngine.DisplayManager;
import assignment04.renderEngine.Loader;
import assignment04.renderEngine.RawModel;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Created by Bryan on 28.10.2015.
 */
public class Camera {


    private static final float FOV = 70;
	private static final Vector3f UP = new Vector3f(0, 1, 0);
	private static final Vector3f TARGET = new Vector3f(0, 0, 0);
    

    private Matrix4f viewMatrix = new Matrix4f().lookAt(6,2,4,0,0,0,0,1,0);
    private Matrix4f projectionMatrix = new Matrix4f();
    private Matrix4f projectionMatrixSceneView = new Matrix4f();
    private Vector3f camPos = new Vector3f();
    private Vector3f originToCameraDir = new Vector3f();

    private float theta = (float)Math.PI * 0.001f;
    private float phi = (float)Math.PI * 0.001f;
    private float camDist = 3;

    private Loader loader;

    public Camera(Loader loader){
        this.loader = loader;
        this.projectionMatrix = createProjectionMatrix(4.0f, 20.0f);
        this.projectionMatrixSceneView = createProjectionMatrix(2.0f, 200.0f);
        this.updateViewMatrix();
    }

    public Matrix4f getProjectionMatrix(){
        return new Matrix4f(this.projectionMatrix);
    }

    public Matrix4f getProjectionMatrixSceneView(){
        return new Matrix4f(this.projectionMatrixSceneView);
    }

    public Matrix4f getViewMatrix(){
        return new Matrix4f(this.viewMatrix);
    }

    public void incrementTheta(float dTheta){
        this.theta += dTheta;
    }

    public void incrementPhi(float dPhi){
        this.phi += dPhi;
    }

    public void updateViewMatrix(){
        // Compute new camera position //
        /* TODO: Berechnen Sie den neuen 
    	Vektor vom Ursprung zur Kamera aus den Winkeln phi und theta 
    	(Polarkoordinten zu kartesischen Koordinaten) */
    	

        // TODO: Berechnen Sie die neue Kameraposition
    	/*aus dem Vektor vom Ursprung zur Kamera 
    	und der Entfernung der Kamera vom Ursprung */


        // TODO: Berechnen und setzen Sie die neue ViewMatrix der Kamera mit der
    	/*errechneten Kamera Position. Hinweis: Die Kamera blickt auf den 
    	 * Ursprung und die Y-Achse zeigt für die Kamera nach oben
    	 */
    	
    	
		float x = (float) (Math.sin(this.theta) * Math.cos(this.phi));
		float y = (float) (Math.cos(this.theta));
		float z = (float) (Math.sin(this.theta) * Math.sin(this.phi));
		
		// Compute new camera position //
		this.originToCameraDir = new Vector3f(x, y, z).mul(camDist);

		//Korrigieren der Kameraentfernung
		camPos = new Vector3f(originToCameraDir).mul(this.camDist);

		this.viewMatrix = new Matrix4f().lookAt(camPos, TARGET, UP);
    }

    
    
    public void incrementDist(float dy){
        camDist = Math.max(camDist +(dy/5.0f), 3.0f);
    }

    private Matrix4f createProjectionMatrix(float near, float far){
        float aspectRatio= (float) DisplayManager.getWidth()/ (float) DisplayManager.getHeight() ;
        float y_scale = (float) (1f /Math.tan(Math.toRadians(FOV/2f))) * aspectRatio;
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far - near;

        Matrix4f m = new Matrix4f();
        m.m00 = x_scale;
        m.m11 = y_scale;
        m.m22 = -((far + near) / frustum_length);
        m.m23 = -1;
        m.m32 = -((2 * near * far) / frustum_length);
        m.m33 = 0;
        return m;
    }

    public RawModel getFrustumModel(){
        float frustumVertices[] = {-1,-1,-1,
                1,-1,-1,
                1, 1,-1,
                -1, 1,-1,
                -1,-1, 1,
                1,-1, 1,
                1, 1, 1,
                -1, 1, 1};
        float frustumNormals[] = {-1,-1,-1,
                1,-1,-1,
                1, 1,-1,
                -1, 1,-1,
                -1,-1, 1,
                1,-1, 1,
                1, 1, 1,
                -1, 1, 1};
        int frustumIndices[] = {
                0, 1,
                1, 2,
                2, 3,
                3, 0,
                4, 5,
                5, 6,
                6, 7,
                7, 4,
                0, 4,
                1, 5,
                2, 6,
                3, 7};
        return loader.loadToVAO(frustumVertices, frustumNormals, frustumIndices);

    }

}

