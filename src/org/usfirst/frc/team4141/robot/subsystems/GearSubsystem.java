package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

/********************************************************************
 * 																	*	
 * 	RopeSubsystem is a subsystem class based off the MDSubsystem.	*
 * 	This subsystem allows the use of a motor to either lift or 		*
 * 	lower the robot on a rope.										*		
 * 																	*
 ********************************************************************
 */

public class GearSubsystem extends MDSubsystem {
	
	private double gearSpeed=0.75;
	private SpeedController gearSpeedController;
	public static String gearMotorName="gearSpeedController";
	private double governor = 1.0;
	
	/**
	 * False means the gear claw is closed.
	 * True means the gear claw is open
	 */
	boolean gearState = false;
	
	public DigitalInput limitSwitchOpen = new DigitalInput(1);
	public DigitalInput limitSwitchClose = new DigitalInput(2);

	// ------------------------------------------------ //
	
	/**
	 * This method houses the configuring of a SpeedController as a fail-safe to check 
	 * that the SpeedController is connected and ready to be used. If the SpeedController
	 * is not connected the Robot will not enable.
	 *  
	 * @return true if the SpeedController is found, false if not.
	 */
	
	public MDSubsystem configure(){
		super.configure();
		
		if(getMotors()==null 
				|| !getMotors().containsKey(gearMotorName))
			throw new IllegalArgumentException("Invalid motor configuration for Gear system.");
		gearSpeedController = (SpeedController)(getMotors().get(gearMotorName));
	return this;
	
	
}
	
	/**
	 * The constructor is used to hold the parameters robot and name.
	 *  
	 * @param robot the default name used after the MDRobotBase in the constructor
	 * @param name the default name used after the string in the constructor
	 */
	public GearSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
		// System.out.println("\n \n \n \n \n \n \n \n \n Constructing gear subsystem " + name);
	}
	
	// ------------------------------------------------ //

	/**
	 * False means the gear claw is closed.
	 * True means the gear claw is open
	 */
	public void toggleGearState() {
		gearState = !gearState;
	}
	
	public boolean getGearState() {
		return gearState;
	}
	
	public boolean getLimitSwitchOpen() {
		return limitSwitchOpen.get();
	}
	
	public boolean getLimitSwitchClose() {
		return limitSwitchClose.get();
	}
	
	public void gearOpen() {
		gearSpeedController.set(gearSpeed);
	}
	
	public void gearClose() {
		gearSpeedController.set(-gearSpeed);
	}
	
	public void stop(){
		gearSpeedController.set(0);
		
	}
	// ------------------------------------------------ //

	/**
	 * This method is used for defining the variable gearSpeed to show up 
	 * on the MDConsole. 
	 */
	@Override
	protected void setUp() {
		if(getConfigSettings().containsKey("gearSpeed")) gearSpeed = getConfigSettings().get("gearSpeed").getDouble();
		if(getConfigSettings().containsKey("governor")) governor = getConfigSettings().get("governor").getDouble();
	}

	/**
	 * This method allows us to change the values of the variable on the fly, 
	 * without going and re-deploying the code every time we want to change the value.
	 * Instead we can now do it with the new and improved MDConsole.
	 */
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		if(changedSetting.getName().equals("gearSpeed")) gearSpeed = changedSetting.getDouble();
		if(changedSetting.getName().equals("governor")) governor = changedSetting.getDouble();
	}

	/**
	 * This method holds the default command used in a subsystem.
	 * Which is not being used in this subsystem. 
	 */
	@Override
	protected void initDefaultCommand() {

	}

}
