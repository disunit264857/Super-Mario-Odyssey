#version 320 es
layout (location = 0) in vec2 vPosition;
layout (location = 1) in vec2 vUv;
layout (location = 2) uniform mat4 P;

out vec2 uv;

void main(){
gl_Position = P*vec4(vPosition,-0.5,1.0);
uv = vUv;
}