package org.usfirst.frc.team4141.robot.autocommands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommandGroup;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.robot.commands.MDPrintCommand;
import org.usfirst.frc.team4141.robot.subsystems.AutonomousSubsystem;

public class Auto2018_CommandGroupBase extends MDCommandGroup {
	
	private AutonomousSubsystem autoSubsystem;
	private AutonomousSubsystem.TypeOfDriveStrategy driveStrategy;
	private boolean basicScenario = true;
	private double m_delayTime;
	
	// CONSTANTS
	double kDrivePowerHigh = .8;
	double kDrivePowerLow = .5;
	double kTurnPower = .8;
	double kLiftPower = .8;
	double kMaintainLiftPower = .15;
	double kClawPower = .5;
	double kWaitBetweenMoves = 2.0;
	
	public Auto2018_CommandGroupBase(MDRobotBase robot, String name) {
		super(robot, name);

		autoSubsystem = (AutonomousSubsystem) robot.getSubsystem("autoSubsystem");
		
		driveStrategy = autoSubsystem.getDriveStrategy();
		// Get the amount of time to wait from the config setting in Autonomous Subsystem		
		m_delayTime = autoSubsystem.getDelayStartTime();
				
		
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
		
		if (basicScenario){
			// Potentially wait a bit before starting to avoid contact with other alliance robots
			addWaitCommand("STEP 0: Wait Command", m_delayTime);	
		
			addDriveCommand("STEP 1: DriveDistanceCommand", 14., kDrivePowerHigh);		
				addWaitCommand("Wait Command", kWaitBetweenMoves);
				
			addAutoLiftCommand("STEP 2: AutoLiftCommand", 0.7, kLiftPower);
			addParallelMaintainCommand("STEP 2b: Parallel Lift Command", 15., kMaintainLiftPower);
				addWaitCommand("Wait Command", 0.5);
			 
			addTurnCommand("STEP 3: TurnCommand", turnAngle, kTurnPower);		
			turnAngle *= (-1.0);  // Flip angle of for next turn		
			addWaitCommand("Wait Command", 1.5);
			 
			addDriveCommand("STEP 4: DriveDistanceCommand", 4., .70);
			
			addAutoClawCommand("STEP 5: AutoClawCommand", 0.4, kClawPower);
				addWaitCommand("Wait Command", 15.0);
 
			addDriveCommand("STEP 6: DriveDistanceCommand", 1., kDrivePowerLow);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
		
			// When we're all done, just idle until the autonomous session is over
			addIdleCommand("IDLE......");
//			
//			// Potentially wait a bit before starting to avoid contact with other alliance robots
//			addWaitCommand("STEP 0: Wait Command", m_delayTime);	
//		
//			addDriveCommand("STEP 1: DriveDistanceCommand", 14., kDrivePowerHigh);		
//				addWaitCommand("Wait Command", kWaitBetweenMoves);
//			
//			addTurnCommand("STEP 2: TurnCommand", turnAngle, kTurnPower);		
//			turnAngle *= (-1.0);  // Flip angle of for next turn			
//				addWaitCommand("Wait Command", 15.0);
// 
//			addDriveCommand("STEP 3: DriveDistanceCommand", 1., kDrivePowerLow);
//				addWaitCommand("Wait Command", kWaitBetweenMoves);
//		
//			// When we're all done, just idle until the autonomous session is over
//			addIdleCommand("IDLE......");
		
		}
		else{
			
			// Potentially wait a bit before starting to avoid contact with other alliance robots
			addWaitCommand("STEP 0: Wait Command", m_delayTime);	
			
			addDriveCommand("STEP 1: DriveDistanceCommand", 2., kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addDriveCommand("STEP 2: DriveDistanceCommand", -2., kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addDriveCommand("STEP 3: DriveDistanceCommand", 14., kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
				
			addAutoLiftCommand("STEP 4: AutoLiftCommand", 2., kLiftPower);
				
			addParallelMaintainCommand("STEP 4b: Parallel Lift Command", 15., kMaintainLiftPower);
			
			addTurnCommand("STEP 5: TurnCommand", turnAngle, kTurnPower);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addDriveCommand("STEP 6: DriveDistanceCommand", 1., kDrivePowerLow);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addAutoClawCommand("STEP 7: AutoClawCommand", 1., kClawPower);
			
			addDriveCommand("STEP 8: DriveDistanceCommand", -1., kDrivePowerLow);
		
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
		
		if (basicScenario){
			
			// Potentially wait a bit before starting to avoid contact with other alliance robots
			addWaitCommand("STEP 0: Wait Command", m_delayTime);	
		
//			addDriveCommand("STEP 1: DriveDistanceCommand", 19., kDrivePowerHigh);

			addDriveCommand("STEP 1: DriveDistanceCommand", 18., kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
		
			addTurnCommand("STEP 2: TurnCommand", turnAngle, kTurnPower);	
				addWaitCommand("Wait Command", 15.0);
		
			addDriveCommand("STEP 3: DriveDistanceCommand", 2., kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
		
			addTurnCommand("STEP 4: TurnCommand", turnAngle, kTurnPower);	
				addWaitCommand("Wait Command", kWaitBetweenMoves);
		
			addDriveCommand("STEP 5: DriveDistanceCommand", 1., kDrivePowerHigh);		
		
			// When we're all done, just idle until the autonomous session is over
			addIdleCommand("IDLE......");
		
		}
		else{
			
			// Potentially wait a bit before starting to avoid contact with other alliance robots
			addWaitCommand("STEP 0: Wait Command", m_delayTime);	
			
			addDriveCommand("STEP 1: DriveDistanceCommand", 2., kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addDriveCommand("STEP 2: DriveDistanceCommand", -2., kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addDriveCommand("STEP 3: DriveDistanceCommand", 19., kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
		
			addTurnCommand("STEP 4: TurnCommand", turnAngle, kTurnPower);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
		
			addDriveCommand("STEP 5: DriveDistanceCommand", 13., kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addAutoLiftCommand("STEP 6: AutoLiftCommand", 2., kLiftPower);
			
			addParallelMaintainCommand("STEP 4b: Parallel Lift Command", 15., kMaintainLiftPower);
			
			addTurnCommand("STEP 7: TurnCommand", turnAngle, kTurnPower);	
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addDriveCommand("STEP 8: DriveDistanceCommand", 2., kDrivePowerLow);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addAutoClawCommand("STEP 9: AutoClawCommand", 1., kClawPower);
		
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
		
		if(basicScenario){
			
			// Potentially wait a bit before starting to avoid contact with other alliance robots
			addWaitCommand("STEP 0: Wait Command", m_delayTime);
		
			addDriveCommand("STEP 1: DriveDistanceCommand", 7., kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
		
			addTurnCommand("STEP 2: TurnCommand", turnAngle, kTurnPower);		
				addWaitCommand("Wait Command", 15.0);
		
			addDriveCommand("STEP 3: DriveDistanceCommand", latDistance, kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
		
			addTurnCommand("STEP 4: TurnCommand", -turnAngle, kTurnPower);	
				addWaitCommand("Wait Command", kWaitBetweenMoves);
		
			addDriveCommand("STEP 5: DriveDistanceCommand", 7., kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
		
			addTurnCommand("STEP 6: TurnCommand", -turnAngle, kTurnPower);	
				addWaitCommand("Wait Command", kWaitBetweenMoves);
		
			addDriveCommand("STEP 7: DriveDistanceCommand", 1., kDrivePowerHigh);	
				
			// When we're all done, just idle until the autonomous session is over
			addIdleCommand("IDLE......");
		
		}
		else{
			
			// Potentially wait a bit before starting to avoid contact with other alliance robots
			addWaitCommand("STEP 0: Wait Command", m_delayTime);
			
			addDriveCommand("STEP 1: DriveDistanceCommand", 2., kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addDriveCommand("STEP 2: DriveDistanceCommand", -2., kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
				
			addDriveCommand("STEP 1: DriveDistanceCommand", 7., kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addTurnCommand("STEP 2: TurnCommand", turnAngle, kTurnPower);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addDriveCommand("STEP 3: DriveDistanceCommand", latDistance, kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addTurnCommand("STEP 4: TurnCommand", -turnAngle, kTurnPower);	
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addDriveCommand("STEP 5: DriveDistanceCommand", 7., kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addAutoLiftCommand("STEP 6: AutoLiftCommand", 2., kLiftPower);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
				addParallelMaintainCommand("STEP 4b: Parallel Lift Command", 15., kMaintainLiftPower);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addTurnCommand("STEP 6: TurnCommand", -turnAngle, kTurnPower);	
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addDriveCommand("STEP 7: DriveDistanceCommand", 1., kDrivePowerLow);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addAutoClawCommand("STEP 9: AutoClawCommand", 1., kClawPower);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
			addDriveCommand("STEP 7: DriveDistanceCommand", -1., kDrivePowerHigh);
				addWaitCommand("Wait Command", kWaitBetweenMoves);
			
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
		
		
		private void addWaitCommand(String commandDescription, double waitTime) {			
			
			// Log what we're doing
			log("addWaitCommand","Adding Wait command " + commandDescription );
			System.out.println("Adding Wait command " + commandDescription );
			
			addSequential(new WaitCommand(getRobot(), commandDescription, waitTime));
		}
		
		private void addIdleCommand(String commandDescription) {			
			
			// Log what we're doing
			log("addIdleCommand","Adding Idle command " + commandDescription );
			System.out.println("Adding Idle command " + commandDescription );
			
			addSequential(new IdleCommand(getRobot(), commandDescription));
		}
		
		private void addAutoLiftCommand(String commandDescription, double duration, double power) {

			log("addAutoLiftCommand","Adding AutoLift command " + commandDescription );
			System.out.println("Adding Lift command " + commandDescription );
			
			addSequential(new AutoLiftCommand(getRobot(), commandDescription, duration, power));
		}

		private void addAutoClawCommand(String commandDescription, double duration,  double power) {
			log("addAutoClawCommand","Adding AutoLift command " + commandDescription );
			System.out.println("Adding Lift command " + commandDescription );
			
			addSequential(new AutoClawCommand(getRobot(), commandDescription, duration, power));
		}
		
		private void addParallelMaintainCommand(String commandDescription, double duration, double power) {

			log("addMaintainCommand","Adding in Parallel Maintain command " + commandDescription );
			System.out.println("Adding in Parallel Maintain command " + commandDescription );
			
			addParallel(new MaintainCommand(getRobot(), commandDescription, duration, power));
		}

}



