package org.usfirst.frc.team4141.robot; 

//===================================================================== Imported Systems ===================================================================== //
//import java.util.Hashtable;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDCommandGroup;
import org.usfirst.frc.team4141.MDRobotBase.sensors.MD_BuiltInAccelerometer;
import org.usfirst.frc.team4141.MDRobotBase.sensors.MD_IMU;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.MDTalonSRX;
import org.usfirst.frc.team4141.MDRobotBase.config.DoubleConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.config.IntegerConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.config.StringConfigSetting;
import org.usfirst.frc.team4141.robot.commands.MDPrintCommand;
import org.usfirst.frc.team4141.robot.subsystems.AutonomousSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.CoreSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.GearSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.LiftSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem.MotorPosition;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem.Type;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

/**
 * This system is the entire brain of the robot.
 * A brain takes to different parts of the body and tells it what to do (right?).
 * We assign motors and what positions to different subsystems.
 * A robot is composed of subsystems
 * A robot will typically have one (1) drive system and several other fit to purpose subsystems
 */

public class Robot extends MDRobotBase {

	// This runs as soon as we press enable in Driver Station (the first thing it does essentially).
	
	public void teleopInit() {
		super.teleopInit();

	}
	

	
	// ================================================================================ Robot Configuration ========================================================================== //
	
	@Override
	protected void configureRobot() {		
	
		// The name needs to be updated every year to keep track.
		// The AutoCommand changes every year and is based off the competition and team agreement.
		
		debug("\nEnter configured Robot");
		add( new CoreSubsystem(this, "core")
				 .add("name",new StringConfigSetting("WheatlyBot")) // <--- Name
				 .add("autoCommand",new StringConfigSetting("AUTOCommand")) // <--- Default AutoCommand
				 .configure()
		);	
		

		// ================================================== Drive Subsystem Configuration ==================================================================== //				
		
		/*********************************************************************************
		 * 																				 *
		 * 	There are two (2) different types of drive systems.							 *
		 * 	TankDrive: Typically the one used by default. Simple as it gets.			 *
		 * 	MecanumDrive: Allows moving left and right without turning but not as fast.	 *
		 *	Each drive type is programmed and hot-swappable below.						 *
		 * 																				 *
		 *********************************************************************************
		 */

		MDDriveSubsystem driveSubsystem = new MDDriveSubsystem(this, "driveSystem", Type.TankDrive);
		add(driveSubsystem);
		  
			  
		driveSubsystem.add("accelerometer", new MD_BuiltInAccelerometer());
		driveSubsystem.add("IMU", new MD_IMU()) 
				.add(MotorPosition.frontLeft, new MDTalonSRX(1))
				.add(MotorPosition.frontRight, new MDTalonSRX(3))
				.add(MotorPosition.rearLeft, new MDTalonSRX(2))
				.add(MotorPosition.rearRight, new MDTalonSRX(4))
				.add("Ramp Time In seconds", new DoubleConfigSetting(0.0, 10.0, 1.0))
				.add("forwardSpeed", new DoubleConfigSetting(0.0, 1.0, 0.25)) //High Speed - Turn Factor
		 	    .add("rotateSpeed", new DoubleConfigSetting(0.0, 1.0, 1.0)) //Slow Speed - Turn Factor
				.add("governor", new DoubleConfigSetting(0.0, 1.0, 1.0)); //Speed Governor

				driveSubsystem.configure();
			
		// =================================================== Other Subsystems ======================================================================== //		
				
		/************************************************************************************
		 * 																					*
		 * 	Developing a subsystem is very simple.											*
		 * 	We need the name, the string, and whatever motors and configurations we want.	*
		 * 	Each new subsystem must be identified to the correct							*
		 * 																					*
		 ************************************************************************************
		 */		
				
		add(new AutonomousSubsystem(this, "autoSubsystem")
				.add("delayStartTime", new DoubleConfigSetting(0.0, 15.0, 0.0))
				.configure()
		);
				
		add(new GearSubsystem(this, "gearSubsystem")
				.add(GearSubsystem.gearMotorLeft, new MDTalonSRX(7))
				.add(GearSubsystem.gearMotorRight, new MDTalonSRX(8))
				.configure());
		
		add(new LiftSubsystem(this, "liftSubsystem")
				.add(LiftSubsystem.liftMotor1, new MDTalonSRX(5))
				.add(LiftSubsystem.liftMotor2, new MDTalonSRX(6))
				.add("liftSpeed", new DoubleConfigSetting(0.0, 1.0, 1.0))
				.add("governor", new DoubleConfigSetting(0.0, 1.0, 1.0)) //Speed Governor
				.configure()
		);
				
		debug("\n \n \n Done configuring the Robot.");
		debug("Printing the state of the Robot...");
		debug(this.toString());
		
	}
			
		public void autonomousInit() {
			super.autonomousInit();
		}
	
}