#version 320 es
layout (location = 0) in vec3 vPosition;

uniform mat4 M;
uniform mat4 V;
uniform mat4 P;


void main(){

gl_Position = P*V*M * vec4(vPosition,1.0);

}
