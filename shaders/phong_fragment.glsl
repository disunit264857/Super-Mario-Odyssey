#version 320 es

precision mediump float;

layout(location = 18) uniform sampler2D shadowmap;
layout(location = 19) uniform vec3 sunDir;
//uniform vec3 sunColor;

layout(location = 20) uniform sampler2D map_Ka;
layout(location = 21) uniform sampler2D map_Kd;
layout(location = 23) uniform vec3 Ka;
layout(location = 24) uniform vec3 Kd;
layout(location = 26) uniform vec3 Ks;
layout(location = 25) uniform float Ns;


in vec3 fNormal;
in vec2 uv;
in vec3 fPosition;

in vec4 shadowCoord;

out vec4 fragColor;

void main(){

vec3 V = normalize(-fPosition);

vec3 t_Ka = texture(map_Ka,uv).rgb;
vec3 t_Kd = texture(map_Kd,uv).rgb;
vec3 diffuse = vec3(dot(fNormal,-normalize(sunDir))*0.5)*Kd;
vec3 specular = vec3(pow(max(dot(V,reflect(-sunDir,fNormal)),0.0),Ns))*Ks;


fragColor = vec4(t_Ka * Ka + t_Kd * diffuse +specular,1.0);
if(texture( shadowmap, shadowCoord.xy ).z<shadowCoord.z){
fragColor = vec4(1.0,0.5,0.5,1.0);
}

}