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
		if(!getRobot().getSubsystems().containsKey("autoSubsystem")) {
			log(Level.ERROR, "initialize()",  "Autonomous Subsystem not found");
			throw new IllegalArgumentException("Autonomous Subsystem not found");
		}
		if(!getRobot().getSubsystems().containsKey("driveSystem")) {
			log(Level.ERROR, "initialize()",  "Drive Subsystem not found");
			throw new IllegalArgumentException("Drive Subsystem not found");
		}
		
		autoSubsystem = (AutonomousSubsystem)getRobot().getSubsystems().get("autoSubsystem");
		requires(autoSubsystem);
		driveSubsystem = (MDDriveSubsystem)getRobot().getSubsystems().get("driveSystem");
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


