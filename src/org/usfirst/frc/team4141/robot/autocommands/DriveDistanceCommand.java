package org.usfirst.frc.team4141.robot.autocommands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;


/**
 * RopeRiseCommand is a command class based off the MDCommand.
 * This command calls the ropeSubsystem, which in this case 
 * uses motors to lift the robot up until a desired location
 * is met by the driver. 
 * 
 * @see RopeSubsystem
 */
public class DriveDistanceCommand extends MDCommand {
	
	private double m_distanceInFeet;
	private double m_speed;
	private double m_distanceTraveled;
	private long m_commandStartTime;
	private double speedFactor = 2;
	
	private MDDriveSubsystem driveSubsystem;
	
	// ------------------------------------------------ //
	
	/**
	 * The constructor is used to hold the parameters robot and name.
	 * Within the constructor is a fail-safe to check that the ropeSubsystem
	 * is connected and ready to be used. If the ropeSubsystem is not connected 
	 * the Robot will not enable.
	 *  
	 * @param robot the default name used after the MDRobotBase in the constructor
	 * @param name the default name used after the string in the constructor
	 * @return true if the ropeSubsystem is found, false if not.
	 */
	public DriveDistanceCommand(MDRobotBase robot, String name, double distanceInFeet, double speed) {
		super(robot, name);
		if(!getRobot().getSubsystems().containsKey("driveSubsystem")){
			log(Level.ERROR, "initialize()", "Drive subsystem not found");
			throw new IllegalArgumentException("Drive Subsystem not found");
		}
		driveSubsystem = (MDDriveSubsystem)getRobot().getSubsystems().get("driveSubsystem "); 
		requires(driveSubsystem );
		
		m_distanceInFeet = distanceInFeet;
		m_speed = speed;
		m_distanceTraveled = 0;
	}

	// ------------------------------------------------ //
	
	
	 //method initalized when comand first starts
	 
	protected void initialize() {
		m_distanceTraveled = 0;
		m_commandStartTime = System.currentTimeMillis();
		
	}
		
	
	protected boolean isFinished() {
		if(m_distanceTraveled >= m_distanceInFeet){
			return true;
		}
		else {
			return false;
		}
	}
	
	
	protected void execute() {
			driveSubsystem.forward(m_speed);
			long elapsedTime = System.currentTimeMillis() - m_commandStartTime;
			m_distanceTraveled = elapsedTime * m_speed * speedFactor;
			System.out.println("Executing Drive Distance Command: elapsed time= " + elapsedTime + "Distance traveled= " + m_distanceTraveled);
	}
	
	/**
	 * This signifies the end of the command by stopping the driveSubsystem motors
	 * due to the halt of input by the driver.
	 */
	@Override
		protected void end() {
			
			driveSubsystem.stop();
			
		}
}
