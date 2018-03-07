package org.usfirst.frc.team4141.robot.autocommands;
import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.subsystems.AutonomousSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;


public class WaitCommand extends MDCommand {
	
	private AutonomousSubsystem autoSubsystem;
	private MDDriveSubsystem driveSubsystem;		// Needed to keep talking to drive to avoid Motor Safety errors
	
	private double m_elapsedTime;				// Time (in seconds) that this command has executed
	private Timer m_timer; 						// Timer for this command
	private double m_waitTime;
	
	private int counter;
	
	// ------------------------------------------------ //
	
	/**
	 * Constructor for the WaitCommand
	 *  
	 * @param robot - the robot object
	 * @param name - name of this WaitCommand
	 */
	public WaitCommand(MDRobotBase robot, String name) {
		super(robot, name);
		
		autoSubsystem = (AutonomousSubsystem) robot.getSubsystem("autoSubsystem");
		
		// Make sure that the Drive Subsystem is active - we need it to avoid MotorSafety errors
		if(!getRobot().getSubsystems().containsKey("driveSystem")){
			log(Level.ERROR, "initialize()", "Drive subsystem not found");
			throw new IllegalArgumentException("Drive Subsystem not found");
		}
		driveSubsystem = (MDDriveSubsystem)getRobot().getSubsystems().get("driveSystem"); 
		requires(driveSubsystem);
	
		m_elapsedTime = 0.;
		m_timer = new Timer();
	}

	// Initialize is called when the command first starts
	 
	protected void initialize() {
		// Get the amount of time to wait from the config setting in Autonomous Subsystem		
		m_waitTime = autoSubsystem.getDelayStartTime();
		
		counter = 0;
		m_elapsedTime = 0.;
		m_timer.reset();
		m_timer.start();
		System.out.println("Starting "+ this.getName() +"; Target wait time= " + m_waitTime + " seconds");
	}
		
	// isFinished is called every 20ms to determine whether the robot has traveled the requested distance
	
	protected boolean isFinished() {
		if(m_elapsedTime >= m_waitTime){
			m_timer.stop();
			System.out.println("Finished "+ this.getName() + "; Elapsed time= " + m_elapsedTime + " seconds");
			return true;
		}
		else {
			return false;
		}
	}
	
	// Execute is called every 20ms - It ensures that the robot is still traveling and computes current distance
	
	protected void execute() {
		
		m_elapsedTime = m_timer.get();							// Return number of seconds since the timer was started
		
		if (++counter >= 50) {
			System.out.println("Executing Wait Command: Elapsed time= " + m_elapsedTime + " seconds");
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
