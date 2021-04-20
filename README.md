# RayTracer.RayTracer
Ray tracing homework for Tel Aviv university's computer graphic course

## Shadow-Transparency bonus
When computing the shadows casted on an intersection point, we used objects' transparency coefficients in the following manner:
* We defined light intensity that reaches an intersection from a single light source as the product of the transparency coefficients
of all the surfaces that are between the light and the intersection. Therefore, each surface that is located between the light position 
and the intersection decreases the light intensity (increases the shadow intensity) according to it's transparency coefficient. 
i.e. instead of a binary dichotomy (pixel is shadowed or lightened), we used a continuous scale for light/shadow intensity 
* The soft shadows that are created from a light source are now computed as the sum of all the light intensities of the 
different light points inside the light's square, divided by the area of the square.

The effects of this can be seen in the transparency.png image - there are different degrees of shadows when the 
light rays go through transparent objects.  

## Ambient bonus
### To enable ambient lighthing you need to add another boolean as true in the settings section of the scene file: 
    Settings: 	bgr  	bgg  	bgb	sh_rays	rec_max ambient_enbaled  
### You will also need to add the ambient constant for each channel on each material
    dr    	dg    	db	sr   	sg   	sb 	rr   	rg  	rb	phong 	trans	ar	ag	ab 

## How is ambient intensity estimated? 
The ambient light estimation is simply an average of all the light sources (per channel)  
We then add k_a * I_a to the resulting color for each surface pixel rendered (where k_a is the surface ambient vector and I_a is the ambient lighting vector)  