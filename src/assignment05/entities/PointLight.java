package assignment05.entities;

import org.joml.Vector3f;

/**
 * Created by Bryan on 03.11.2015.
 */
public class PointLight {
    private String name;
    private Vector3f lightPos;

    private Vector3f lightColAmbient;
    private Vector3f lightColDiffuse;
    private Vector3f lightColSpecular;
    private boolean enabled = true;

    public PointLight(String name, Vector3f lightPos, Vector3f lightColAmbient, Vector3f lightColDiffuse,Vector3f lightColSpecular, Boolean enabled){
        this.name = name;
        this.lightPos = lightPos;
        this.lightColAmbient = lightColAmbient;
        this.lightColDiffuse = lightColDiffuse;
        this.lightColSpecular = lightColSpecular;
        this.enabled = enabled;
    }

    public Vector3f getLightPos() {
        return lightPos;
    }

    public void setLightPos(Vector3f newPos){this.lightPos = newPos;}

    public Vector3f getLightColAmbient() {
        return lightColAmbient;
    }

    public Vector3f getLightColDiffuse() {
        return lightColDiffuse;
    }

    public Vector3f getLightColSpecular() {
        return lightColSpecular;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName(){
        return this.name;
    }
}
