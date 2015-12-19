#version 330 core

in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec3 color;
void main()
{
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);
    vec3 unitCameraVector = normalize(toCameraVector);

    float normalDot = dot(unitNormal, unitLightVector);
    float brightness = max(normalDot, 0.2);
    vec3 diffuse = brightness * vec3(1, 1, 0);

    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

    //Calculate the specular factor
    float specularFactor = dot(reflectedLightDirection, unitCameraVector);
    specularFactor = max(specularFactor, 0.0);

    //Dampen the specular lighting according to the material
    float dampedFactor = pow(specularFactor, 1);
    vec3 specular = 2 * dampedFactor * vec3(1, 1, 0);

	//Set the pixel output colour
	color.rgb = vec3(1, 1, 1) * diffuse + specular;
}
