package org.usfirst.frc.team4141.robot.autocommands;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;

public class AUTOPosThree_RLR extends Auto2018_CommandGroupBase {
	

	public AUTOPosThree_RLR(MDRobotBase robot, String name) {
		super(robot, name);
		
		// The switch color is on the same side as our starting position => use Near scenario
		// But the scale color is on the opposite side => so pass in false as 2nd argument
		nearScenario(3, false);

	}
	
}
