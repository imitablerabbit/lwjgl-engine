#version 330 core

in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

vec3 lightColor = vec3(1, 1, 1);
vec3 fragmentColor = vec3(1, 0, 0);
float dampening = 5;
float reflectivity = 1;

out vec4 color;
void main()
{
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);
    vec3 unitCameraVector = normalize(toCameraVector);

    float normalDot = dot(unitNormal, unitLightVector);
    float brightness = max(normalDot, 0.2);
    vec3 diffuse = brightness * lightColor;

    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

    //Calculate the specular factor
    float specularFactor = dot(reflectedLightDirection, unitCameraVector);
    specularFactor = max(specularFactor, 0.0);

    //Dampen the specular lighting according to the material
    float dampedFactor = pow(specularFactor, dampening);
    vec3 specular = reflectivity * dampedFactor * lightColor;

	//Set the pixel output colour
	color.rgb = (fragmentColor * diffuse) + specular;
	color.a = 1;
}
