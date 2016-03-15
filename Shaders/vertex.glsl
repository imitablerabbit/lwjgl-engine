#version 330 core

//Input vertex data, different for all executions of this shader.
layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec2 uvs;

//Uniform variables
uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;
uniform vec3 lightPosition;
uniform float reflectivity;
uniform float dampening;

//Out variables
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCameraVector;
out vec2 uv;
out float dampening_fragment;
out float reflectivity_fragment;

// General variables
mat4 view_projection;
mat4 model_view_projection;

void main() {

    //Set up the vertices
    vec4 vertex = vec4(position, 1.0);

    //Calculate the position for the vertices
    vec4 worldPosition = model * vertex;
    vec4 viewPosition = view * worldPosition;
    gl_Position = projection * viewPosition;

    //Send the surface normal and vectors
    surfaceNormal = (model * vec4(normal, 0.0)).xyz;
    toLightVector = lightPosition - worldPosition.xyz;
    toCameraVector = (inverse(view) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;

    //Send the reflectivity and dampening
    reflectivity_fragment = reflectivity;
    dampening_fragment = dampening;
    uv = uvs;
}
