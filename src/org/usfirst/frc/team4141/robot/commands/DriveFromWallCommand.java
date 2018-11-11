package org.usfirst.frc.team4141.robot.commands;

import java.util.Date;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.subsystems.AutonomousSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;


public class DriveFromWallCommand extends MDCommand {

	private long start;
	private double autoSpeed;
	private long autoDuration;
	private double setAngle;
	private double actualAngle;
	private double driveAngle;    
	private AutonomousSubsystem autoSubsystem;
	private MDDriveSubsystem driveSubsystem;
	
	// ------------------------------------------------ //
	
	public DriveFromWallCommand(MDRobotBase robot, String name) {
		super(robot, name);
		
		autoSubsystem = (AutonomousSubsystem)getRobot().getSubsystem("autoSubsystem");
		requires(autoSubsystem);
		driveSubsystem = (MDDriveSubsystem)getRobot().getSubsystem("driveSystem");
		requires(driveSubsystem);
		
		
		// TODO Auto-generated constructor stub
	}
	
	// ------------------------------------------------ //
	
	@Override  
	protected void initialize() {
		driveSubsystem.gyroReset();
		autoDuration = autoSubsystem.getAutoDuration();
		autoSpeed = autoSubsystem.getAutoSpeed();
		start =(new Date()).getTime();	
		setAngle = driveSubsystem.getAngle();
		log(Level.DEBUG, "initialize()", "setAngle="+setAngle);
	}
	
	@Override
	protected boolean isFinished() {
		long now = (new Date()).getTime();
		return  (now >=(start+autoDuration));
			//long now = (new Date()).getTime();
			//return  (now >=(start+autoDuration));
		}
	
	@Override
	protected void execute() {
		actualAngle = driveSubsystem.getAngle();
		driveAngle = setAngle - actualAngle; //driveAngle = setAngle - actualAngle
	  	log(Level.DEBUG,"execute","setAngle="+setAngle+", actualAngle="+actualAngle+", driveAngle="+driveAngle);

		driveSubsystem.move(autoSpeed,driveAngle);
	}
	
	@Override
	protected void end() {
		driveSubsystem.stop();
	}
	
}


