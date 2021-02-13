#version 320 es
layout (location = 0) in vec4 vPosition;
layout (location = 1) uniform mat4 MVP;

void main(){
gl_Position = MVP * vPosition;
}