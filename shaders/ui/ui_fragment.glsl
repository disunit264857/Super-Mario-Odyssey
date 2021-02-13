#version 320 es

precision mediump float;

layout (location=3) uniform sampler2D uiTexture;

in vec2 uv;

out vec4 fragColor;

void main(){
//fragColor = vec4(1.0);
fragColor = texture(uiTexture,uv);
}

