package org.usfirst.frc.team4141.robot.autocommands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommandGroup;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.robot.commands.DriveFromWallCommand;
import org.usfirst.frc.team4141.robot.commands.MDPrintCommand;

public class AUTODriveFromWall extends MDCommandGroup{
	public AUTODriveFromWall(MDRobotBase robot){
		super(robot,"Auto1");
		addSequential(new MDPrintCommand(getRobot(),"auto1-1","[AUTO] Move from Wall: Begin"));
		addSequential(new DriveFromWallCommand(getRobot(),"Move From Wall."));
		addSequential(new MDPrintCommand(getRobot(),"auto1-3","[AUTO] Move from Wall: End"));
	}

}


