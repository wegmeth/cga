#version 330 core
layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;

out vec3 colour;

uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;


void main(void){
		colour = (modelMatrix * vec4(normal, 0)).xyz;
	gl_Position = projectionMatrix * modelMatrix * vec4(position,1.0);

}
