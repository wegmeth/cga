#version 330 core

out vec4 color;


// TODO: Aufgabe 5.2: Legen Sie uniform-Variablen für alle Licht- und Materialeigenschaften an.

// TODO: Aufgabe 5.2: Berechnen Sie die out_Color nach dem OpenGL Reflection Model (Vorlesung GLSL, Folie 94). 
//Den globalen Ambient Term können Sie vernachlässigen

// TODO: Aufgabe 5.2: und nur die lokalen pro Lichtquelle verwenden). Verwenden Sie zur Berechnung des
// spekularen Anteils die original Phong-Formulierung (Vorlesung GLSL, Folie 83)

// TODO: Aufgabe 5.2: Sie benötigen dazu die in-Variablen für die 3 Vektoren aus dem Vertexshader und 
//uniform-Variablen für alle Material- und Lichteigenschaften.

// TODO: Aufgabe 5.3: Erweitern Sie die Berechnung so, dass 8 Lichtquellen verarbeitet werden können. 
//Die uniform-Variablen der Lichtquellen und die in-Variable des Vektors zur Lichtquelle

// TODO: Aufgabe 5.3: müssen zu Arrays gemacht werden.

// TODO: Aufgabe 5.3: Schreiben Sie am Besten eine Funktion, die die Lichtberechnung für eine Lichtquelle vornimmt. 
//Rufen Sie diese für jede Lichtquelle auf und summieren Sie in einer Schleife die Ergebnisse auf out_Color.



in vec3 DiffuseTerm[8],AmbientEmissiveTerm[8], SpecularTerm[8], LightDir[8];
in vec3 Normal, EyeDir, matEmissionAmbientTerm;

uniform float matShininess;


void main(void){
	vec3 N = normalize(Normal);
    vec3 V = normalize(EyeDir);

	int i=0;

//Formel!! Me + Ma + La + schleifeAusI(Ma * La,i + Md * cos a * Ld,i + Ms * cos^k beta * Ls,i)
	color = vec4(matEmissionAmbientTerm,1.0);
	while(i<5){
		vec3 L = normalize(LightDir[i]);
	    vec3 H = normalize (V+L);

		float cosA = max(0.0,dot(N,L));

	    float cosBeta = max(0.0,dot(N,H));
	    float cosBetak = pow(cosBeta,matShininess);
		
		color += vec4(AmbientEmissiveTerm[i] * cosA,1.0);
		color += vec4(DiffuseTerm[i] * cosA , 1.0);
		color +=vec4(SpecularTerm[i]*cosBetak , 1.0);
		
		i++;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}