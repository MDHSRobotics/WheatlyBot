package org.usfirst.frc.team4141.robot.commands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;

public class MDPrintCommand extends MDCommand {
	
	private MDDriveSubsystem driveSubsystem;		// Needed to keep talking to drive to avoid Motor Safety errors
	
	private String message;

	public MDPrintCommand(MDRobotBase robot, String name, String message) {
		super(robot,name);
		this.setMessage(message);
		
		// Make sure that the Drive Subsystem is active - we need it to avoid MotorSafety errors
		driveSubsystem = (MDDriveSubsystem)getRobot().getSubsystem("driveSystem"); 
		requires(driveSubsystem);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	} 
	
	@Override
	protected void execute() {
		super.execute();
		log("execute", message);
		System.out.println("MDPrintCommand executing " + message);
		
		// Make sure that we continue to be stopped - this is important to avoid MotorSafety errors
		driveSubsystem.stop();   // Make sure that we continue to be stopped
	}

}
