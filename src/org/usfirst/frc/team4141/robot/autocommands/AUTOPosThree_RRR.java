package org.usfirst.frc.team4141.robot.autocommands;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;

public class AUTOPosThree_RRR extends Auto2018_CommandGroupBase {
	

	public AUTOPosThree_RRR(MDRobotBase robot, String name) {
		super(robot, name);
		
		// The switch color is on the same side as our starting position => use Near scenario
		// And the scale color is on our side => so pass in true as 2nd argument
		nearScenario(3, true);

	}
	
}
