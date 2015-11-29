#version 330 core
in vec3 colour;
out vec4 out_Color;

void main() {
  out_Color = vec4(0.5 * normalize(colour) + vec3(0.5, 0.5, 0.5), 1);
}
