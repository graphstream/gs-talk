/**
 *  Demo
 *  Author: Thibaut
 *  Description: 
 */

model Demo

global {
	
	/*
	 * Init 100 humans, randomly located over a square of 1000 per side
	 */
	geometry shape <- square(1000);
	int length <- 100; // the distance between two agents under which they are considered neighbours 
	init {
		gs_clear_senders;
		gs_add_sender gs_host:"localhost" gs_port:2001 gs_sender_id:"humans";
			
		create human number:100{
			location <- any_location_in(shape);
			tar <- any_location_in(shape);
		}
	}
}

/*
 * The `human` species : each agent move randmoly over the environment and keep updated a list of his neighbours
 */
species human skills:[moving]{
	list<human> humans_connected <- [];
	point tar;
	init {
		gs_add_node gs_sender_id:"humans" gs_node_id:name;
		gs_add_node_attribute gs_sender_id:"humans" gs_node_id:name gs_attribute_name:"x" gs_attribute_value:location.x;
		gs_add_node_attribute gs_sender_id:"humans" gs_node_id:name gs_attribute_name:"y" gs_attribute_value:location.y*-1;
	}
	
	/*
	 * Choose a random target and move to it.
	 */
	reflex move {
		if(tar.x = location.x and tar.y = location.y){
			tar <- any_location_in(world.shape);
		}
		do goto target:tar speed: 5;
		gs_add_node_attribute gs_sender_id:"humans" gs_node_id:name gs_attribute_name:"x" gs_attribute_value:location.x;
		gs_add_node_attribute gs_sender_id:"humans" gs_node_id:name gs_attribute_name:"y" gs_attribute_value:location.y*-1;
	}
	
	/*
	 * Build a list of his neighbours
	 */
	reflex connect {
		// A loop to remove the neighbours who are no longer neighbours
		int i <- 0;
		loop while: i<length(humans_connected) {
			if(distance_to(self, humans_connected[i]) > length){
				gs_remove_edge gs_sender_id:"humans" gs_edge_id:(name + humans_connected[i].name);
				remove index:i from: humans_connected;
			}
			else{
				i <- i + 1;
			}
		}
		
		// A loop to add the new neighbours
		list<human> humans <- agents_at_distance(length);
		i <- 0;
		loop while: i<length(humans) {
			if(self != humans[i] ){
				if(!contains(humans_connected, humans[i])){
					humans_connected <- humans_connected + humans[i];
					gs_add_edge gs_sender_id:"humans" gs_edge_id:(name + humans[i].name) gs_node_id_from:name gs_node_id_to:humans[i].name gs_is_directed:true;
				}
			}
			i <- i + 1;
		}
	}
	
	/*
	 * Visualization
	 */
	aspect base {
		draw circle(10.0) color: rgb("black") ;
	}
}

/*
 * Simple experiment: just a display of moving agents
 */
experiment exp type: gui {
	output {
		display batch_road {
			species human aspect: base;
		}
	}
}