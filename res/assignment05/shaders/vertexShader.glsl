#version 330 core
// TODO: Aufgabe 5.2: Legen Sie eine uniform-Variable für die Lichtposition an. Achten Sie darauf, die gleichen
// Namen wie beim Anlegen im StaticShaderProgram zu verwenden
// TODO: Aufgabe 5.2: Berechnen Sie je einen Vektor der zur Lichtquelle zeigt, zur Kamera 
//zeigt und die Flächennormale. Hinweis: Die Flächennormale muss korrekt transformiert werden.
// TODO: Aufgabe 5.2: Geben Sie die drei Vektoren als out-Variable an den FragmentShader weiter.
// TODO: Aufgabe 5.3: Erweitern Sie die uniform-Variable für die Lichtposition zu 
//einem Array der Größe 8. Erweitern Sie auch die out-Variable für den Vektor zur Lichtquelle
// TODO: Aufgabe 5.3: und führen Sie die Berechnung für alle 8 Lichtquellen in einer Schleife durch.

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

out vec3 DiffuseTerm[8], AmbientEmissiveTerm[8], SpecularTerm[8];
out vec3 Normal, LightDir[8], EyeDir,matEmissionAmbientTerm;
//Materials
uniform vec3 matDiffuse, matAmbient, matEmissive, matSpecular;
//Licht
uniform vec3 lightColDiffuse[8], 
			 lightColAmbient[8], lightColSpecular[8],lightPos[8];
 
void main(void){
	vec4 worldPosition = modelMatrix * vec4(position, 1.0);
	gl_Position = projectionMatrix * viewMatrix  * worldPosition;
	
	vec4 v = vec4(position,1.0);
	vec4 n = vec4(normal,1.0);
	
	mat4 normalMat = transpose(inverse(viewMatrix*modelMatrix));
	Normal = (normalMat*n).xyz;
	vec4 P = (viewMatrix * viewMatrix * v );

	matEmissionAmbientTerm = matAmbient +  matEmissive;

	int i=0;
	
	//Formel!! Me + Ma + La + schleifeAusI(Ma * La,i + Md * cos a * Ld,i + Ms * cos^k beta * Ls,i)
	while(i<8){
		AmbientEmissiveTerm[i] = matAmbient * lightColAmbient[i];
		DiffuseTerm[i] = matDiffuse * lightColDiffuse[i];
		SpecularTerm[i] = matSpecular * lightColSpecular[i];	

		vec4 lp = vec4(lightPos[i],1.0);
		LightDir[i] = ((projectionMatrix * lp) - P).xyz;
		i++;	
	}
	EyeDir = -P.xyz;
}