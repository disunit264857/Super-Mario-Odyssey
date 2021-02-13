#version 320 es
layout (location = 0) in vec3 vPosition;

layout (location = 1) in vec2 vUv;
layout (location = 2) in vec3 vNormal;

layout (location = 3) uniform mat4 M;
layout (location = 4) uniform mat4 V;

layout (location = 5) uniform mat4 P;
layout (location = 6) uniform mat4 S;

out vec3 fNormal;
out vec2 uv;
out vec3 fPosition;
out vec4 shadowCoord;

void main(){

gl_Position = P*V*M * vec4(vPosition,1.0);
fPosition = (V*M*vec4(vPosition,1.0)).xyz;
uv = vUv;
fNormal =normalize((M * vec4(vNormal,0)).xyz);
shadowCoord = S*M*vec4(vPosition,1.0);
}