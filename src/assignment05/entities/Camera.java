package assignment05.entities;

import assignment05.renderEngine.DisplayManager;
import assignment05.renderEngine.Loader;
import assignment05.renderEngine.RawModel;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Created by Bryan on 28.10.2015.
 */
public class Camera {


    private static final float FOV = 70;

    private Matrix4f viewMatrix = new Matrix4f();
    private Matrix4f projectionMatrix = new Matrix4f();
    private Matrix4f projectionMatrixSceneView = new Matrix4f();
    private Vector3f camPos = new Vector3f();
    private Vector3f originToCameraDir = new Vector3f();

    private float theta = (float)Math.PI * 0.0f;
    private float phi = (float)Math.PI * 0.0f;
    private float camDist = 10.0f;

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
        originToCameraDir = new Vector3f((float)(Math.sin(phi) * Math.cos(theta)),
                (float)(Math.sin(theta)),
                (float) (Math.cos(phi) * Math.cos(theta)));

        camPos = new Vector3f(originToCameraDir).mul(camDist);

        viewMatrix = new Matrix4f().lookAt(camPos, new Vector3f(0,0,0), new Vector3f(0,1,0));
    }

    public void setDist(float dy){
        camDist = Math.max(camDist +(dy/3.0f), 7.0f);
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

