/**
* Name: OSM file to Agents
* Author:  Patrick Taillandier
* Description: Model which shows how to import a OSM File in GAMA and use it to create Agents. In this model, a filter is done to take only into account the roads 
* and the buildings contained in the file. 
* Tags:  load_file, osm, gis
*/
model simpleOSMLoading

global {

//map used to filter the object to build from the OSM file according to attributes. for an exhaustive list, see: http://wiki.openstreetmap.org/wiki/Map_Features
	map filtering <- map(["tourism"::["yes"], "building"::["yes"], "office"::["yes"], "shop"::["yes"]]);
//	map filtering <- map(["highway"::["yes"]]);
	//OSM file to load
	file<geometry> osmfile;

	//compute the size of the environment from the envelope of the OSM file
	geometry shape <- envelope(osmfile);

	init {
	//possibility to load all of the attibutes of the OSM data: for an exhaustive list, see: http://wiki.openstreetmap.org/wiki/Map_Features
		create osm_agent from: osmfile with:
		[tourism_str::string(read("tourism")), building_str::string(read("building")), office_str::string(read("office")), shop_str::string(read("shop"))];

		//from the created generic agents, creation of the selected agents
		ask osm_agent {
		//			if (length(shape.points) = 1 and tourism_str != nil)
		//			{
		//				create node_agent with: [shape::shape, type:: tourism_str];
		//			} else
		//			{
		//				if (tourism_str != nil)
		//				{
		//					create road with: [shape::shape, type:: tourism_str];
		//				} else 
			if (length(shape.points) > 1 and (building_str != nil or tourism_str != nil or office_str != nil or shop_str != nil)) {
				create building with: [shape::shape];
			}

			//			}
			//			do the generic agent die
			do die;
		}

		ask building[196] {
			do die;
		}

//		ask building {
//			write shape;
//		}
		save building to:"../includes/building.shp" type:shp;
	}

}

species osm_agent {
	string tourism_str;
	string building_str;
	string office_str;
	string shop_str;
}

species road {
	rgb color <- rnd_color(255);
	string type;

	aspect default {
		draw shape color: color;
	}

}

species node_agent {
	string type;

	aspect default {
		draw shape color: #red;
	}

}

species building {

	aspect default {
		draw shape color: #red;
	}

}

experiment "Load OSM" type: gui {
	parameter "File:" var: osmfile <- file<geometry>(osm_file("../includes/map.osm", filtering));
	output {
		display map type: opengl {
			species building refresh: false;
			species road refresh: false;
			species node_agent refresh: false;
		}

	}

}

experiment "Load OSM from Internet" type: gui parent: "Load OSM" {
	parameter "File:" var: osmfile <- file<geometry>(osm_file("http://download.geofabrik.de/europe/andorra-latest.osm.pbf", filtering));
}
