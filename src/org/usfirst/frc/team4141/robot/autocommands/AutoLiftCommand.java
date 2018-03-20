package org.usfirst.frc.team4141.robot.autocommands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.subsystems.LiftSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;

import edu.wpi.first.wpilibj.Timer;


/**
 * RopeRiseCommand is a command class based off the MDCommand.
 * This command calls the ropeSubsystem, which in this case 
 * uses motors to lift the robot up until a desired location
 * is met by the driver. 
 * 
 * @see RopeSubsystem
 */
public class AutoLiftCommand extends MDCommand {
	
	private double m_elapsedTime;				// Time (in seconds) that this command has executed			// True if moving forward; False if moving backward
	private Timer m_timer; 						// Timer for this command
	private int counter;
	
	private double m_power;
	private double m_duration;
	
	private LiftSubsystem liftSubsystem;
	private MDDriveSubsystem driveSubsystem;		// Needed to keep talking to drive to avoid Motor Safety errors
	
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
	public AutoLiftCommand(MDRobotBase robot, String name, double duration, double power) {
		super(robot, name);
		if(!getRobot().getSubsystems().containsKey("liftSubsystem")){
			log(Level.ERROR, "initialize()", "lift subsystem not found");
			throw new IllegalArgumentException("lift Subsystem not found");
		}
		liftSubsystem = (LiftSubsystem)getRobot().getSubsystem("liftSubsystem"); 
		requires(liftSubsystem);
		
		driveSubsystem = (MDDriveSubsystem)getRobot().getSubsystems().get("driveSystem"); 
		requires(driveSubsystem);
		
		m_power = power;
		m_duration = duration;
		m_timer = new Timer();
	}

	// ------------------------------------------------ //
	
	/**
	 * When the command first starts nothing happens.
	 */
	protected void initialize(){
		counter = 0;
		m_elapsedTime = 0.;
		m_timer.reset();
		m_timer.start();
		System.out.println("Starting " + this.getName() + "; Power = " + m_power + " percent" + "Target Duration= " + m_duration);

	}
	/**
	 * The robot does not stop the procedure until it is told by the driver.
	 * 
	 * @return false because the command never ends by itself.
	 */
	protected boolean isFinished() {
		if (m_elapsedTime >= m_duration) {
			System.out.println("Finished " + this.getName()+ "; Power = " + m_power + "Elapsed time = " + m_elapsedTime);
			return true;
		}
		else return false;
	}
	
	/**
	 * This method runs the rope system in the upwards direction until 
	 * it reads no input from the driver. 
	 */
	protected void execute() {
		m_elapsedTime = m_timer.get();							// Return number of seconds since the timer was started
		
		if (++counter >= 50) {
			System.out.println("Executing Lift Command: Elapsed time= " + m_elapsedTime + "; Target duration= " + m_duration);
			counter = 0;
		}
		liftSubsystem.autoLift(m_power);
//		log(Level.DEBUG,"execute()","Lifting");
		driveSubsystem.stop();   // Make sure that we continue to be stopped
	}
	
	/**
	 * This signifies the end of the command by stopping the ropeSubsystem motors
	 * due to the halt of input by the driver.
	 */
	@Override
		protected void end() {
		super.end();
		liftSubsystem.stop();
			
		}
}
