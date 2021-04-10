# RayTracer.RayTracer
Ray tracing homework for Tel Aviv university's computer graphic course
## Ambient bonus
### To enable ambient lighthing you need to add another boolean as true in the settings section of the scene file: 
    Settings: 	bgr  	bgg  	bgb	sh_rays	rec_max ambient_enbaled  
### You will also need to add the ambient constant for each channel on each material
    dr    	dg    	db	sr   	sg   	sb 	rr   	rg  	rb	phong 	trans	ar	ag	ab 

## How is ambient intensity estimated? 
The ambient light estimation is simply an average of all the light sources (per channel)  
We then add k_a * I_a to the resulting color for each surface pixel rendered (where k_a is the surface ambient vector and I_a is the ambient lighting vector)  