package org.usfirst.frc.team4141.robot.autocommands;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;

public class AUTOPosOne_RLR extends Auto2018_CommandGroupBase {
	

	public AUTOPosOne_RLR(MDRobotBase robot, String name) {
		super(robot, name);

		// The switch color is on the opposite side => use Far scenario
		// But the scale color is on our side => so pass in true as 2nd argument
		farScenario(1, true);

	}
	
}

 