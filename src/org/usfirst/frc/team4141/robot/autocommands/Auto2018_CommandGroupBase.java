package org.usfirst.frc.team4141.robot.autocommands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommandGroup;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.robot.commands.MDPrintCommand;
import org.usfirst.frc.team4141.robot.subsystems.AutonomousSubsystem;

public class Auto2018_CommandGroupBase extends MDCommandGroup {
	
	private AutonomousSubsystem autoSubsystem;
	private AutonomousSubsystem.TypeOfDriveStrategy driveStrategy;
	private boolean basicScenario = true;
	
	public Auto2018_CommandGroupBase(MDRobotBase robot, String name) {
		super(robot, name);

		autoSubsystem = (AutonomousSubsystem) robot.getSubsystem("autoSubsystem");
		
		driveStrategy = autoSubsystem.getDriveStrategy();
		
		log(name,"Creating command group " + name + " with drive strategy of " + driveStrategy.toString());
		System.out.println("\nCreating command group " + name + " with drive strategy of " + driveStrategy.toString());		
		
	}
	
	// The nearScenario() method should be called in the constructor of any derived class where 
	// our alliance color is on the side of the switch nearest our start position.
	// That is, when:
	//    a) we start in position #1 and our alliance color is on the left side of the switch - OR -
	//    b) we start in position #3 and our alliance color is on the right side of the switch
	// The sequence of command is basically the same in both situations, with the only difference
	// being whether the first turn is to the left or to the right.
	// The input argument to this method specifies whether we're starting in position #1 or #3
	
	protected void nearScenario(int startingPosition) {
		
		double turnAngle = 90.0;
		if (startingPosition == 3) turnAngle *= (-1.0);  // Flip angle of first turn if starting at position #3
		
		addSequential(new MDPrintCommand(getRobot(), this.getName(), "Executing command group " + this.getName() ) );
		
		
		if (basicScenario = true){
			
			// Potentially wait a bit before starting to avoid contact with other alliance robots
			addWaitCommand("STEP 0: Wait Command");
		
			// *****************************************
			// ****** Insert commands to execute  ******
		
			addDriveCommand("STEP 1: DriveDistanceCommand", 14., .8);
		
			addTurnCommand("STEP 2: TurnCommand", turnAngle, .8);		
			turnAngle *= (-1.0);  // Flip angle of for next turn
		
			addDriveCommand("STEP 3: DriveDistanceCommand", 1., .5);
		
			// *****************************************
		
			// When we're all done, just idle until the autonomous session is over
			addIdleCommand("IDLE......");
		
		}
		else{
			// Potentially wait a bit before starting to avoid contact with other alliance robots
			addWaitCommand("STEP 0: Wait Command");
			
			// *****************************************
			// ****** Insert commands to execute  ******
			
			addDriveCommand("STEP 1: DriveDistanceCommand", 14., .8);
			
			addTurnCommand("STEP 2: TurnCommand", turnAngle, .8);		
			turnAngle *= (-1.0);  // Flip angle of for next turn
			
			addDriveCommand("STEP 3: DriveDistanceCommand", 1., .5);
			
			// *****************************************
		
			//ADD LIFT CODE HERE
		
			// When we're all done, just idle until the autonomous session is over
			addIdleCommand("IDLE......");
	
		}
	}
	
	// The farScenario() method should be called in the constructor of any derived class where 
	// our alliance color is on the opposite side of the switch nearest our start position.
	// That is, when:
	//    a) we start in position #1 and our alliance color is on the right side of the switch - OR -
	//    b) we start in position #3 and our alliance color is on the left side of the switch
	// The sequence of command is basically the same in both situations, with the only difference
	// being whether the first turn is to the left or to the right.
	// The input argument to this method specifies whether we're starting in position #1 or #3
	
	protected void farScenario(int startingPosition) {
		
		double turnAngle = 90.0;
		if (startingPosition == 3) turnAngle *= (-1.0);  // Flip angle of first turn if starting at position #3
		
		addSequential(new MDPrintCommand(getRobot(), this.getName(), "Executing command group " + this.getName() ) );

		if (basicScenario = true){
			
			// Potentially wait a bit before starting to avoid contact with other alliance robots
			addWaitCommand("STEP 0: Wait Command");
		
			// *****************************************
			// ****** Insert commands to execute  ******
		
			addDriveCommand("STEP 1: DriveDistanceCommand", 19., .8);
		
			addTurnCommand("STEP 2: TurnCommand", turnAngle, .8);		
		
			addDriveCommand("STEP 3: DriveDistanceCommand", 2., .8);
		
			addTurnCommand("STEP 4: TurnCommand", turnAngle, .8);	
		
			addDriveCommand("STEP 5: DriveDistanceCommand", 1., .8);
		
			// *****************************************		
		
			// When we're all done, just idle until the autonomous session is over
			addIdleCommand("IDLE......");
		
		}
		else{
			// Potentially wait a bit before starting to avoid contact with other alliance robots
			addWaitCommand("STEP 0: Wait Command");
			
			// *****************************************
			// ****** Insert commands to execute  ******
			
			addDriveCommand("STEP 1: DriveDistanceCommand", 19., .8);
			
			addTurnCommand("STEP 2: TurnCommand", turnAngle, .8);		
			
			addDriveCommand("STEP 3: DriveDistanceCommand", 2., .8);
			
			addTurnCommand("STEP 4: TurnCommand", turnAngle, .8);	
			
			addDriveCommand("STEP 5: DriveDistanceCommand", 1., .8);
			
			// *****************************************		
			
			//ADD LIFT CODE HERE
		
			// When we're all done, just idle until the autonomous session is over
			addIdleCommand("IDLE......");
		
		}
	}	
	
	// The midScenario() method should be called in the constructor of any derived class where 
	// we are starting in position #2
	// The input argument says whether or not to head for the switch on the left sid; that is:
	//    a) headLeft = TRUE if our alliance color is on the left side of the switch - OR -
	//    b) headLeft = FALSE if our alliance color is on the right side of the switch
	// The sequence of command is basically the same in both situations, with the only difference
	// being whether the first turn is to the left or to the right.
	
	protected void midScenario(boolean headLeft) {
		
		double turnAngle = 90.0;
		double latDistance = 6.0; //lateral distance the bot moves in Pos #2
		if (headLeft) turnAngle *= (-1.0);  // Flip angle of first turn if starting at position #3
		if (headLeft) latDistance = 10;
		
		addSequential(new MDPrintCommand(getRobot(), this.getName(), "Executing command group " + this.getName() ) );
	
		if(basicScenario = true){
		
			// Potentially wait a bit before starting to avoid contact with other alliance robots
			addWaitCommand("STEP 0: Wait Command");
		
			// *****************************************
			// ****** Insert commands to execute  ******
		
			addDriveCommand("STEP 1: DriveDistanceCommand", 7., .8);
		
			addTurnCommand("STEP 2: TurnCommand", turnAngle, .8);		
		
			addDriveCommand("STEP 3: DriveDistanceCommand", latDistance, .8);
		
			addTurnCommand("STEP 4: TurnCommand", -turnAngle, .8);	
		
			addDriveCommand("STEP 5: DriveDistanceCommand", 7., .8);
		
			addTurnCommand("STEP 6: TurnCommand", -turnAngle, .8);	
		
			addDriveCommand("STEP 7: DriveDistanceCommand", 1., .8);
		
			// *****************************************		
				
			// When we're all done, just idle until the autonomous session is over
			addIdleCommand("IDLE......");
		
		}
		else{
			// Potentially wait a bit before starting to avoid contact with other alliance robots
			addWaitCommand("STEP 0: Wait Command");
			
			// *****************************************
			// ****** Insert commands to execute  ******
			
			addDriveCommand("STEP 1: DriveDistanceCommand", 7., .8);
			
			addTurnCommand("STEP 2: TurnCommand", turnAngle, .8);		
			
			addDriveCommand("STEP 3: DriveDistanceCommand", latDistance, .8);
			
			addTurnCommand("STEP 4: TurnCommand", -turnAngle, .8);	
			
			addDriveCommand("STEP 5: DriveDistanceCommand", 7., .8);
			
			addTurnCommand("STEP 6: TurnCommand", -turnAngle, .8);	
			
			addDriveCommand("STEP 7: DriveDistanceCommand", 1., .8);
			
			// *****************************************		
					
			//ADD LIFT CODE HERE
			
			// When we're all done, just idle until the autonomous session is over
			addIdleCommand("IDLE......");
			
		}
		
	}		
	
	// addDriveCommand() method does two things:
	//		1. Creates a new drive distance command
	//		2. Adds it as a sequential command to the current command group
	// There different types of drive distance commands:
	//		a) Duration base, whereby the distance traveled is inferred from the amount of elapsed time
	//		b) Open Loop Encoder, whereby the encoder is checked periodically to determine the distance traveled
	//		c) Closed Loop, whereby the encoder is instructed how to travel
	// Arguments:
	//		commandDescription: description for this command - e.g. "STEP3: Drive 10 feet"
	//		distanceToTravelInFeet: how far to travel expressed in feet (negative implies to travel backwards)
	//		speed: how fast to travel. The value depends on the type of drive distance strategy:
	//			- Duration: power rating (0.0 to 1.0)
	//			- Open Loop Encoder: power rating (0.0 to 1.0)
	//			- Closed Loop: velocity in feet per second
	private void addDriveCommand(String commandDescription, double distanceToDriveInFeet, double speed) {
		
		// Log what we're doing
		log("addDriveCommand","Adding Drive command " + commandDescription + " for " + distanceToDriveInFeet + " ft. at speed " + speed);
		System.out.println("Adding Drive command " + commandDescription + " for " + distanceToDriveInFeet + " ft. at speed " + speed);

		switch (driveStrategy) {
		
		case Duration:
			addSequential(new DriveDistanceCommand(getRobot(), commandDescription, distanceToDriveInFeet, speed));
			break;
			
		case Simulate:
			// Just print the command that would be executed if we weren't in Simulate mode
			addSequential(new MDPrintCommand(getRobot(), commandDescription, "Drive for a distance of " + distanceToDriveInFeet + " ft. at speed " + speed));
			break;
			
		case ClosedLoop:
			addSequential(new ClosedLoopDriveDistanceCommand(getRobot(), commandDescription, distanceToDriveInFeet, false));
			
		default:
			// Raise an error if the drive strategy is not set properly
			throw new IllegalArgumentException("Invalid motor configuration for TankDrive system.");
		}
	}
		
		// addSequentialTurnCommand() method does two things:
		//		1. Creates a new turn command
		//		2. Adds it as a sequential command to the current command group
		// There different types of turn commands:
		//		a) Duration base, whereby the turn angle is inferred from the amount of elapsed time
		//		b) Open Loop Encoder, whereby the encoder is checked periodically to determine the angle turned
		//		c) Closed Loop, whereby the encoder is instructed how to travel
		// Arguments:
		//		commandDescription: description for this command - e.g. "STEP3: Turn 90 degrees"
		//		angle: number degrees to turn (positive to right; negative to left)
		//		speed: how fast to travel. The value depends on the type of drive strategy:
		//			- Duration: power rating (0.0 to 1.0)
		//			- Open Loop Encoder: power rating (0.0 to 1.0)
		//			- Closed Loop: velocity in feet per second
		private void addTurnCommand(String commandDescription, double angle, double speed) {
			
			// Log what we're doing
			log("addTurnCommand","Adding Turn command " + commandDescription + " for " + angle + " degrees at speed " + speed);
			System.out.println("Adding Turn command " + commandDescription + " for " + angle + " degrees at speed " + speed);
			
			switch (driveStrategy) {
			
			case Duration:
				addSequential(new TurnCommand(getRobot(), commandDescription, angle, speed));
				break;
				
			case Simulate:
				// Just print the command that would be executed if we weren't in Simulate mode
				addSequential(new MDPrintCommand(getRobot(), commandDescription, "Turn for " + angle + " degrees at speed " + speed));
				break;
				
			case ClosedLoop:
				// To be done later
				throw new IllegalArgumentException("Closed Loop drive strategy not yet implemented");
//				break;
				
			default:
				// Raise an error if the drive strategy is not set properly
				throw new IllegalArgumentException("Invalid motor configuration for TankDrive system.");
			}
		}
		
		// addWaitCommand() method does two things:
		//		1. Creates a new Wait command
		//		2. Adds it as a sequential command to the current command group
		// Arguments:
		//		commandDescription: description for this command - e.g. "STEP 0: Wait"
		
		
		private void addWaitCommand(String commandDescription) {			
			
			// Log what we're doing
			log("addWaitCommand","Adding Wait command " + commandDescription );
			System.out.println("Adding Wait command " + commandDescription );
			
			addSequential(new WaitCommand(getRobot(), commandDescription));
		}
		
		private void addIdleCommand(String commandDescription) {			
			
			// Log what we're doing
			log("addIdleCommand","Adding Idle command " + commandDescription );
			System.out.println("Adding Idle command " + commandDescription );
			
			addSequential(new IdleCommand(getRobot(), commandDescription));
		}
		
		private void addAutoLiftCommand(String commandDescription, double power, double duration) {

			log("addAutoLiftCommand","Adding AutoLift command " + commandDescription );
			System.out.println("Adding Lift command " + commandDescription );
			
			addSequential(new AutoLiftCommand(getRobot(), commandDescription, power, duration));
		}

}



