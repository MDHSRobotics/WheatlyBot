package org.usfirst.frc.team4141.robot;

//===================================================================== Imported Systems ===================================================================== //
// Base Systems
import org.usfirst.frc.team4141.MDRobotBase.ConsoleOI;
import org.usfirst.frc.team4141.MDRobotBase.MDJoystick;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.OIBase;
import org.usfirst.frc.team4141.MDRobotBase.RioHID;
import org.usfirst.frc.team4141.robot.autocommands.DriveDistanceCommand;
import org.usfirst.frc.team4141.robot.autocommands.TurnCommand;
import org.usfirst.frc.team4141.robot.commands.GearPlaceCommand;
import org.usfirst.frc.team4141.robot.commands.LiftCommand;
import org.usfirst.frc.team4141.robot.commands.MDPrintCommand;
import org.usfirst.frc.team4141.robot.commands.ToggleOrientationCommand;



/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * We use devices including but not limited to:
 * 	Xbox Controller
 * 	Flight Simulator Controller
 * 
 */

public class OI extends OIBase{
	
	public OI(MDRobotBase robot) {
		super(robot);
		System.out.println("OI created");
	}


	public void configureOI(){
		
		/**
		 * 
		 * A robot should have 1 or more operator interfaces (OI)
		 * Typically, a robot will have at least 1 joystick and 1 console (MDConsole in our instance)
		 * We control what buttons are mapped in both the console and the joystick.
		 * We configure the joysticks below.
		 * 
		 */	
	
					// === Joystick Mapping Structure === //
/*
     	add(new MDJoystick(getRobot(), "[joystickName]", [JOYSTICK NUMBER]);
		    .("[JOYSTICK_BUTTON_NAME]",[JOYSTICK BUTTON NUMBER],new [COMMAND NAME]))
			.whileHeld("[JOYSTICK_BUTTON_NAME]",[JOYSTICK BUTTON NUMBER],new [COMMAND NAME]))
			.configure()
		);
*/
		
		add(new MDJoystick(getRobot(), "joystick", 0)
			.whenPressed("5",5,new ToggleOrientationCommand(getRobot(), "ToggleOrientationCommand"))
			.whenPressed("8",8,new DriveDistanceCommand(getRobot(), "DriveDistanceCommand", 10.0 , 1.0))
			.whenPressed("7",7,new TurnCommand(getRobot(), "TurnCommand", 90.0 , 1.0))
			.whenPressed("10",10, new GearPlaceCommand(getRobot()))
			.whileHeld("4",4,new LiftCommand(getRobot()))
			.configure()
		);
		
//		add(new MDJoystick(getRobot(), "xbox", 1)
//				.whileHeld("LiftCommand",5,new LiftCommand(getRobot()))
//				.configure()
//			);

//                               Configure the RioHID 
//         Uncomment the following to attach a command to the user button on the RoboRIO. 
		// ============================================================================= //
		add(new RioHID(getRobot())
			.whileHeld(new MDPrintCommand(getRobot(),"ExampleCommand1","ExampleCommand1 message"))
			.configure()
		);
		
		
//                         Configure the MDConsole "Joystick"
//          We add commands here that will show up as buttons in the MDConsole
		// ==================================================================== //
		add(new ConsoleOI(getRobot())
				.configure()
			);		
		
	
	}
}  
