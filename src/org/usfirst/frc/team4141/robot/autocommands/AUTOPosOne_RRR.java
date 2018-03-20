package org.usfirst.frc.team4141.robot.autocommands;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;

public class AUTOPosOne_RRR extends Auto2018_CommandGroupBase {
	

	public AUTOPosOne_RRR(MDRobotBase robot, String name) {
		super(robot, name);
		
		// The switch color is on the opposite side => use Far scenario
		// And the scale color is on the opposite side => so pass in false as 2nd argument
		farScenario(1, false);

	}
	
}

 