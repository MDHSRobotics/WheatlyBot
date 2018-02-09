package org.usfirst.frc.team4141.robot;


import java.util.Hashtable;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.sensors.MD_BuiltInAccelerometer;
import org.usfirst.frc.team4141.MDRobotBase.sensors.MD_IMU;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.DoubleConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.config.StringConfigSetting;
import org.usfirst.frc.team4141.robot.commands.ArcadeDriveCommand;
import org.usfirst.frc.team4141.robot.commands.MDPrintCommand;
import org.usfirst.frc.team4141.robot.commands.RiseCommand;
import org.usfirst.frc.team4141.robot.subsystems.AutonomousSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.ClawSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.CoreSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.LiftSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem.MotorPosition;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem.Type;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends MDRobotBase {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */

	public void teleopInit() {
		super.teleopInit();
		RiseCommand riseCommand = new RiseCommand(this);
		riseCommand.start();

	}
	@Override
	protected void configureRobot() {


		//A commands needs to be configured for the autonomous mode.
		//In some cases it is desirable to have more than 1 auto command and make a decision at game time which command to use
//		setAutonomousCommand(new MDCommand[]{
//				new MDPrintCommand(this,"AutonomousCommand","AutonomousCommand message")
//			}, "AutonomousCommand"  //specify the default
//		);
		debug("\n \n \n enter configured robot");
		//Subsystem to manage robot wide config settings
		add( new CoreSubsystem(this, "core")
				 .add("name",new StringConfigSetting("GladosBot"))					//go ahead name your robot
				 .add("autoCommand",new StringConfigSetting("[Insert Command Here]"))		//name of autoCommand you wish to start with
				 .configure()
		);		
		
		//A robot is composed of subsystems
		//A robot will typically have 1 drive system and several other fit to purpose subsystems		
		//The Drive system is a special subsystem in that it has specific logic handle the speed controllers
		//We have 2 types of drive systems, tank drive and mecanum drive
		//uncomment the desired drive system and adjust the motor configuration as needed
		//Mecanum example :

//		add(new MDDriveSubsystem(this, "driveSystem", Type.TankDrive)
		MDDriveSubsystem driveSubsystem = new MDDriveSubsystem(this, "driveSystem", Type.TankDrive);
		add(driveSubsystem);
//		System.out.println("Hashtable after adding drive");
//		Hashtable<String, MDSubsystem> ht = this.getSubsystems();
//		System.out.println(ht.toString());
		driveSubsystem.add("accelerometer", new MD_BuiltInAccelerometer());
//		System.out.println("\nMDDrive after adding accelerometer");
//		System.out.println(driveSubsystem.toString());
		driveSubsystem.add("IMU", new MD_IMU())
				.add(MotorPosition.left, new WPI_TalonSRX(4))
				.add(MotorPosition.right, new WPI_TalonSRX(3))
//				.add(MotorPosition.rearLeft, new WPI_TalonSRX(1))
//				.add(MotorPosition.rearRight, new WPI_TalonSRX(2))
				//.add("Drive-F", new DoubleConfigSetting(0.0, 1.0, 0.0))
		 	    //.add("Drive-P", new DoubleConfigSetting(0.0, 1.0, 0.1))
				//.add("Drive-I", new DoubleConfigSetting(0.0, 1.0, 0.8))
				//.add("Drive-D", new DoubleConfigSetting(0.0, 1.0, 0.1))
				.add("highSpeed", new DoubleConfigSetting(0.0, 1.0, 0.25)) //High Speed - Turn Factor
		 	    .add("lowSpeed", new DoubleConfigSetting(0.0, 1.0, 0.4)) //Slow Speed - Turn Factor
				.add("governor", new DoubleConfigSetting(0.0, 1.0, 1.0)); //Speed Governor
//				driveSubsystem.configure();
				driveSubsystem.configure();
			
		
		add(new AutonomousSubsystem(this, "autoSubsystem")
				.add("scenario1Speed", new DoubleConfigSetting(0.0, 1.0, 0.5))
				.configure()
		);
		
		add(new LiftSubsystem(this, "liftSubsystem")
				.add(LiftSubsystem.motorName, new WPI_TalonSRX(1))
				.add("liftSpeed", new DoubleConfigSetting(0.0, 1.0, 0.5))
				.configure()
		);
		
		ClawSubsystem clawSubsystem = new ClawSubsystem(this, "clawSubsystem");
		add(clawSubsystem);
		clawSubsystem.add(ClawSubsystem.clawMotorName, new WPI_TalonSRX(5))
				.add(ClawSubsystem.extendclawMotorName, new WPI_TalonSRX(6))
				.add("clawSpeed", new DoubleConfigSetting(0.0, 1.0, 0.5))
				.configure();
//		System.out.println(clawSubsystem.toString());
		
		
		
		
		
		debug("\n \n \n done with config robot");
		debug("printing the state of the robot");
		debug(this.toString());

	}


		
		
		/*		add(new MDDriveSubsystem(this, "driveSystem", Type.TankDrive)
		.add("accelerometer", new MD_BuiltInAccelerometer())
		.add("IMU", new MD_IMU())
		.add(MotorPosition.frontLeft, new WPI_TalonSRX(1))
		.add(MotorPosition.frontRight, new WPI_TalonSRX(2))
		.add(MotorPosition.rearLeft, new WPI_TalonSRX(3))
		.add(MotorPosition.rearRight, new WPI_TalonSRX(4))
		//.add("Drive-F", new DoubleConfigSetting(0.0, 1.0, 0.0))
 	    //.add("Drive-P", new DoubleConfigSetting(0.0, 1.0, 0.1))
		//.add("Drive-I", new DoubleConfigSetting(0.0, 1.0, 0.8))
		//.add("Drive-D", new DoubleConfigSetting(0.0, 1.0, 0.1))
		.add("highSpeed", new DoubleConfigSetting(0.0, 1.0, 0.25)) //High Speed - Turn Factor
 	    .add("lowSpeed", new DoubleConfigSetting(0.0, 1.0, 0.4)) //Slow Speed - Turn Factor
		.add("governor", new DoubleConfigSetting(0.0, 1.0, 1.0)) //Speed Governor
		.configure()
);	
*/		
		
//		add(new ClawSubsystem(this, "clawSubsystem")
//				.add(ClawSubsystem.motorName, new WPI_TalonSRX(2))
//				.add(ClawSubsystem.motorName2, new WPI_TalonSRX(0))
//				.add("clawSpeed", new DoubleConfigSetting(0.0, 1.0, 0.5))
//				.configure()
//		);
		
		//TankDrive with 2 motors example:
//		add(new MDDriveSubsystem(this, "driveSystem", Type.TankDrive)
//				.add(MotorPosition.right, new Victor(0))
//				.add(MotorPosition.left, new Victor(1))
//				.add("accelerometer", new MD_BuiltInAccelerometer())
//				.add("IMU", new MD_IMU())
//				.configure()
//		);	
		//TankDrive with 4 motors example:
//		add(new MDDriveSubsystem(this, "driveSystem", Type.TankDrive)
//				.add(MotorPosition.frontRight, new Victor(0))
//				.add(MotorPosition.rearRight, new Victor(1))
//				.add(MotorPosition.frontLeft, new Victor(2))
//				.add(MotorPosition.rearLeft, new Victor(3))
//				.add("accelerometer", new MD_BuiltInAccelerometer())
//				.add("IMU", new MD_IMU())
//				.configure()
//		);	
		
	
	//Override lifecycle methods, as needed
	//	@Override
	//	public void teleopPeriodic() {
	//		super.teleopPeriodic();
	//		...
	//	}
	//	@Override
	//	public void autonomousPeriodic() {
	//		super.autonomousPeriodic();
	//		...
	//	}	
	
		
		//Event manager WebSocket related methods
		//Override as needed
	//	@Override
	//	public void onConnect(Session session) {
	//		super.onConnect(session);
	//		...
	//	}
	
}
