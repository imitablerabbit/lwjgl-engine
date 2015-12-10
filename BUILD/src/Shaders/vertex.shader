#version 330 core

// Input vertex data, different for all executions of this shader.
layout(location = 0) in vec3 position;

//uniform variables
uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

mat4 view_projection;
mat4 model_view_projection;

void main() {
    view_projection = projection * view;
    model_view_projection = view_projection * model;
    gl_Position = model_view_projection * vec4(position.xyz, 1.0);
}
