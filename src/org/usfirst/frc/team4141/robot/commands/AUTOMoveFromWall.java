package org.usfirst.frc.team4141.robot.commands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommandGroup;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;

public class AUTOMoveFromWall extends MDCommandGroup{
	public AUTOMoveFromWall(MDRobotBase robot){	
		super(robot,"Auto1");
		addSequential(new MDPrintCommand(getRobot(),"auto1-1","[AUTO] Move from Wall: Begin"));
		addSequential(new DriveFromWallCommand(getRobot(),"Move From Wall."));
		addSequential(new MDPrintCommand(getRobot(),"auto1-3","[AUTO] Move from Wall: End"));
	}

}


