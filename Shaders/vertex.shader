#version 330 core

//Input vertex data, different for all executions of this shader.
layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;

//Uniform variables
uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

//Out variables
out vec3 surfaceNormal;
out vec3 toLightVector;

mat4 view_projection;
mat4 model_view_projection;

void main() {

    //Set up the vertices
    vec4 vertex = vec4(position, 1.0);

    //Calculate the world position
    vec4 worldPosition = model * vertex;
    vec4 viewPosition = view * worldPosition;
    gl_Position = projection * viewPosition;

    //Send the surface normal
    surfaceNormal = (model * vec4(normal, 0.0)).xyz;
    toLightVector = vec3(0,0,0) - worldPosition.xyz;
}
