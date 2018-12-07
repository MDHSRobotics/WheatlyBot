package org.usfirst.frc.team4141.robot.commands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDJoystick;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.subsystems.GearSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.DigitalInput;


/**
 * GearPlaceCommand is a command class based off the MDCommand.
 * This command calls the GearSubsystem, which in this case 
 * uses motors to open or close a gear claw within the range of
 * limit switches
 * 
 * @see GearSubsystem
 */
public class GearPlaceCommand extends MDCommand {
	
	private GearSubsystem gearSubsystem;
	
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
	public GearPlaceCommand(MDRobotBase robot) {
		super(robot, "GearPlaceCommand");

		gearSubsystem = (GearSubsystem)getRobot().getSubsystem("gearSubsystem"); 
		requires(gearSubsystem);
	}

	// ------------------------------------------------ //
	
	/**
	 * When the command first starts the previous state of the GearSubsystem is inquired
	 */
	private MDJoystick xbox = null;
	private boolean previouslyOpen;
	
	protected void initialize() {
		super.initialize();
		xbox = getRobot().getOi().getJoysticks().get("xbox");
		previouslyOpen = gearSubsystem.getPreviousState();
		SmartDashboard.putBoolean("gearState", previouslyOpen);
		}
	
	protected void execute() {
		if(previouslyOpen) {		//If it is fully open
				gearSubsystem.gearClose();
		}
		if(!previouslyOpen) {	//If it is fully closed
				gearSubsystem.gearOpen();
		}
	}

	/**
	 * The robot stops when the limit switches are triggered
	 */
	protected boolean isFinished() {
		if(previouslyOpen && gearSubsystem.getLimitSwitchClose()) {		//If the claw was open but is now closed
			return true;
		}
		else if(!previouslyOpen && gearSubsystem.getLimitSwitchOpen()) {	//If the claw was closed but is now open
			return true;
		}
		else {
			return false;
		}
	}
	
	 /**
	 * This signifies the end of the command by stopping the GearSubsystem motors
	 * and toggling the state of the GearSubsystem
	 */
	@Override
		protected void end() {
		super.end();
		gearSubsystem.stop();
		gearSubsystem.toggleGearState();	//Toggles the previous state of the claw for the next command call
		}
}
