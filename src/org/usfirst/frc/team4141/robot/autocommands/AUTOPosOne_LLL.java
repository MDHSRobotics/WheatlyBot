package org.usfirst.frc.team4141.robot.autocommands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommandGroup;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;



public class AUTOPosOne_LLL extends MDCommandGroup {
	

	public AUTOPosOne_LLL(MDRobotBase robot, String name) {
		super(robot, name);
		addSequential(new DriveDistanceCommand(robot, "DriveDistanceCommand", 10, .5));
		addSequential(new TurnCommand(robot, "TurnCommand", 180, .5));
		addSequential(new DriveDistanceCommand(robot, "DriveDistanceCommand", 10, .5));
		addSequential(new TurnCommand(robot, "TurnCommand", 90, .5));
		addSequential(new DriveDistanceCommand(robot, "DriveDistanceCommand", 10, .5));

	}
	
}

