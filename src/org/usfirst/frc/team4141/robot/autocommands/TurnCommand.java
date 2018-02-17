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
public class TurnCommand extends MDCommand {
	
	private double m_targetAngle;
	private double m_speed;
	private double m_currentAngle;
	
	private MDDriveSubsystem driveSubsystem;
	
	// ------------------------------------------------ //
	
	
	public TurnCommand(MDRobotBase robot, String name, double targetAngle, double speed) {
		super(robot, name);
		if(!getRobot().getSubsystems().containsKey("driveSubsystem")){
			log(Level.ERROR, "initialize()", "Drive subsystem not found");
			throw new IllegalArgumentException("Drive Subsystem not found");
		}
		driveSubsystem = (MDDriveSubsystem)getRobot().getSubsystems().get("driveSubsystem "); 
		requires(driveSubsystem );
		
		m_targetAngle = targetAngle;
		m_speed = speed;
		m_currentAngle = 0;
	}

	// ------------------------------------------------ //
	
	
	 //method initalized when comand first starts
	 
	protected void initialize() {
	//	m_commandStartTime = System.currentTimeMillis();
	}
		
	
	protected boolean isFinished() {
		if(m_targetAngle < 0){
			if(m_currentAngle <= m_targetAngle ){
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if(m_currentAngle >= m_targetAngle){
				return true;
			}
			else {
				return false;
			}
		}
		
	}
	
	
	protected void execute() {
//			driveSubsystem.forward(m_speed);
//			long elapsedTime = System.currentTimeMillis() - m_commandStartTime;
//			m_distanceTraveled = elapsedTime * m_speed * speedFactor;
//			System.out.println("Executing Drive Distance Command: elapsed time= " + elapsedTime + "Distance traveled= " + m_distanceTraveled);
		m_currentAngle = m_currentAngle + 1;
		//line 78 is temporary
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
