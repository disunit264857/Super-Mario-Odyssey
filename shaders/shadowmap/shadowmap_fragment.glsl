#version 320 es

precision mediump float;

out float fragDepth;

void main(){
fragDepth = gl_FragCoord.z;
}