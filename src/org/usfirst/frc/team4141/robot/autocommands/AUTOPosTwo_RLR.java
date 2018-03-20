package org.usfirst.frc.team4141.robot.autocommands;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;

public class AUTOPosTwo_RLR extends Auto2018_CommandGroupBase {
	

	public AUTOPosTwo_RLR(MDRobotBase robot, String name) {
		super(robot, name);
		
		// We are starting in position two => use Mid scenario
		// 1st argument = whether our alliance color is on the left side of the switch
		// 2nd argument = whether our alliance color is on the left side of the scale
		midScenario(false, true);

	}
	
}
