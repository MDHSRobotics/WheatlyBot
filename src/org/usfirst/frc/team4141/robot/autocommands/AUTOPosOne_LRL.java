package org.usfirst.frc.team4141.robot.autocommands;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;

public class AUTOPosOne_LRL extends Auto2018_CommandGroupBase {
	

	public AUTOPosOne_LRL(MDRobotBase robot, String name) {
		super(robot, name);
		
		// The switch color is on the same side as our starting position => use Near scenario
		// But the scale color is on the oppsosite side => so pass in false as 2nd argument
		nearScenario(1, false);

	}
	
}

 