/**
* Name: GeoTIFF file to Grid of Cells 
* Author:  Patrick Taillandier
* Description: Model which shows how to create a grid of cells by using a GeoTIFF File. 
* Tags:  load_file, tif, gis, grid
*/

model geotiffimport

global {
	//definiton of the file to import
	file grid_data <- file('../includes/canthodem.tif') ;
	file road_shp<-file("../includes/roads2.shp");
	file building_shp<-file("../includes/building.shp");
	//computation of the environment size from the geotiff file
	geometry shape <- envelope(grid_data);	
	
	float max_value;
	float min_value;
	init {
		max_value <- cell max_of (each.grid_value);
		min_value <- cell min_of (each.grid_value);
		ask cell {
			int val <- 255- int(255 * (  1-(grid_value - min_value) /(max_value -min_value)));
			color <- rgb(val,val,val);
		}
		create road from:road_shp;
		create building from:building_shp;
	}
}
species road{
	
	aspect default {
		draw shape color: #yellow;
	}
}
species building {

	aspect default {
		draw shape color: #red;
	}

}
//definition of the grid from the geotiff file: the width and height of the grid are directly read from the asc file. The values of the asc file are stored in the grid_value attribute of the cells.
grid cell file: grid_data;

experiment show_example type: gui {
	output {
		display test type: opengl{
			grid cell ;
			species road;
			species building;
		}
//		display "As DEM" type: opengl{
//			grid cell   elevation: grid_value*5 triangulation:true ;
//		}
	} 
}
