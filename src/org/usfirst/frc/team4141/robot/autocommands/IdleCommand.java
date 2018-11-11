package org.usfirst.frc.team4141.robot.autocommands;
import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.subsystems.AutonomousSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;


public class IdleCommand extends MDCommand {
	
	private AutonomousSubsystem autoSubsystem;
	private MDDriveSubsystem driveSubsystem;		// Needed to keep talking to drive to avoid Motor Safety errors
	
	private double m_elapsedTime;				// Time (in seconds) that this command has executed
	private Timer m_timer; 						// Timer for this command
	
	private int counter;
	
	// ------------------------------------------------ //
	
	/**
	 * Constructor for the IdleCommand
	 *  
	 * @param robot - the robot object
	 * @param name - name of this IdleCommand
	 */
	public IdleCommand(MDRobotBase robot, String name) {
		super(robot, name);
		
		autoSubsystem = (AutonomousSubsystem) robot.getSubsystem("autoSubsystem");
		
		// Make sure that the Drive Subsystem is active - we need it to avoid MotorSafety errors

		driveSubsystem = (MDDriveSubsystem)getRobot().getSubsystem("driveSystem"); 
		requires(driveSubsystem);
	
		m_elapsedTime = 0.;
		m_timer = new Timer();
	}

	// Initialize is called when the command first starts
	 
	protected void initialize() {
		
		counter = 0;
		m_elapsedTime = 0.;
		m_timer.reset();
		m_timer.start();
		System.out.println("Starting "+ this.getName());
	}
		
	// isFinished is called every 20ms to determine whether the robot has traveled the requested distance
	
	protected boolean isFinished() {

		// Never stop - let the Driver Station terminate the command at the end of the Autonomous stage
		return false;
	}
	
	// Execute is called every 20ms - It ensures that the robot is still traveling and computes current distance
	
	protected void execute() {
		
		m_elapsedTime = m_timer.get();							// Return number of seconds since the timer was started
		
		if (++counter >= 50) {
			System.out.println("Executing Idle Command: Elapsed time= " + m_elapsedTime + " seconds");
			counter = 0;
		}

		// Make sure that we continue to be stopped - this is important to avoid MotorSafety errors
		driveSubsystem.stop();   // Make sure that we continue to be stopped
		
	}
	
	// End is called when the command is terminated 

	@Override
		protected void end() {
		}
}
