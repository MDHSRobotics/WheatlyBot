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
	private int stepNum = 0;
	
	// CONSTANTS
	double kDrivePowerHigh = .8;
	double kDrivePowerLow = .5;
	double kTurnPower = .8;
	double kLiftPower = .8;
	double kMaintainLiftPower = .15;
	double kClawPower = .5;
	double kWaitBetweenMoves = 1.0;
	
	public Auto2018_CommandGroupBase(MDRobotBase robot, String name) {
		super(robot, name);
		
		stepNum = 0;

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
			addWaitCommand(m_delayTime);	
		
			addDriveCommand(14., kDrivePowerHigh);		
				addWaitCommand(kWaitBetweenMoves);
			
			addTurnCommand(turnAngle, kTurnPower);		
			turnAngle *= (-1.0);  // Flip angle of for next turn			
				addWaitCommand(15.0);
 
			addDriveCommand(1., kDrivePowerLow);
				addWaitCommand(kWaitBetweenMoves);
		
			// When we're all done, just idle until the autonomous session is over
			addIdleCommand();
		
		}
		else{
			
			// Potentially wait a bit before starting to avoid contact with other alliance robots
			addWaitCommand(m_delayTime);	
			
			addDriveCommand(2., kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
			
			addDriveCommand(-2., kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
			
			addDriveCommand(14., kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
				
			addAutoLiftCommand(2., kLiftPower);
				
			addParallelLiftCommand(15., kMaintainLiftPower);
			
			addTurnCommand(turnAngle, kTurnPower);
				addWaitCommand(kWaitBetweenMoves);
			
			addDriveCommand(1., kDrivePowerLow);
				addWaitCommand(kWaitBetweenMoves);
			
			addAutoClawCommand(1., kClawPower);
			
			addDriveCommand(-1., kDrivePowerLow);
		
			// When we're all done, just idle until the autonomous session is over
			addIdleCommand();
	
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
			addWaitCommand(m_delayTime);	
		
//			addDriveCommand(19., kDrivePowerHigh);

			addDriveCommand(14., kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
		
			addTurnCommand(turnAngle, kTurnPower);	
				addWaitCommand(15.0);
		
			addDriveCommand(2., kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
		
			addTurnCommand(turnAngle, kTurnPower);	
				addWaitCommand(kWaitBetweenMoves);
		
			addDriveCommand(1., kDrivePowerHigh);		
		
			// When we're all done, just idle until the autonomous session is over
			addIdleCommand();
		
		}
		else{
			
			// Potentially wait a bit before starting to avoid contact with other alliance robots
			addWaitCommand(m_delayTime);	
			
			addDriveCommand(2., kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
			
			addDriveCommand(-2., kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
			
			addDriveCommand(19., kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
		
			addTurnCommand(turnAngle, kTurnPower);
				addWaitCommand(kWaitBetweenMoves);
		
			addDriveCommand(13., kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
			
			addAutoLiftCommand(2., kLiftPower);
			
			addParallelLiftCommand(15., kMaintainLiftPower);
			
			addTurnCommand(turnAngle, kTurnPower);	
				addWaitCommand(kWaitBetweenMoves);
			
			addDriveCommand(2., kDrivePowerLow);
				addWaitCommand(kWaitBetweenMoves);
			
			addAutoClawCommand(1., kClawPower);
		
			// When we're all done, just idle until the autonomous session is over
			addIdleCommand();
		
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
			addWaitCommand(m_delayTime);
		
			addDriveCommand(7., kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
		
			addTurnCommand(turnAngle, kTurnPower);		
				addWaitCommand(15.0);
		
			addDriveCommand(latDistance, kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
		
			addTurnCommand(-turnAngle, kTurnPower);	
				addWaitCommand(kWaitBetweenMoves);
		
			addDriveCommand(7., kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
		
			addTurnCommand(-turnAngle, kTurnPower);	
				addWaitCommand(kWaitBetweenMoves);
		
			addDriveCommand(1., kDrivePowerHigh);	
				
			// When we're all done, just idle until the autonomous session is over
			addIdleCommand();
		
		}
		else{
			
			// Potentially wait a bit before starting to avoid contact with other alliance robots
			addWaitCommand(m_delayTime);
			
			addDriveCommand(2., kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
			
			addDriveCommand(-2., kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
				
			addDriveCommand(7., kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
			
			addTurnCommand(turnAngle, kTurnPower);
				addWaitCommand(kWaitBetweenMoves);
			
			addDriveCommand(latDistance, kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
			
			addTurnCommand(-turnAngle, kTurnPower);	
				addWaitCommand(kWaitBetweenMoves);
			
			addDriveCommand(7., kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
			
			addAutoLiftCommand(2., kLiftPower);
				addWaitCommand(kWaitBetweenMoves);
			
			addParallelLiftCommand(15., kMaintainLiftPower);
				addWaitCommand(kWaitBetweenMoves);
			
			addTurnCommand(-turnAngle, kTurnPower);	
				addWaitCommand(kWaitBetweenMoves);
			
			addDriveCommand(1., kDrivePowerLow);
				addWaitCommand(kWaitBetweenMoves);
			
			addAutoClawCommand(1., kClawPower);
				addWaitCommand(kWaitBetweenMoves);
			
			addDriveCommand(-1., kDrivePowerHigh);
				addWaitCommand(kWaitBetweenMoves);
			
			// When we're all done, just idle until the autonomous session is over
			addIdleCommand();
			
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
	//		distanceToTravelInFeet: how far to travel expressed in feet (negative implies to travel backwards)
	//		speed: how fast to travel. The value depends on the type of drive distance strategy:
	//			- Duration: power rating (0.0 to 1.0)
	//			- Open Loop Encoder: power rating (0.0 to 1.0)
	//			- Closed Loop: velocity in feet per second
	private void addDriveCommand(double distanceToDriveInFeet, double speed) {
		
		++stepNum;
		String commandName = "STEP " + stepNum + ": DriveDistance";

		// Log what we're doing
		log("addDriveCommand","Adding Drive command " + commandName + " for " + distanceToDriveInFeet + " ft. at speed " + speed);
		System.out.println("Adding Drive command " + commandName + " for " + distanceToDriveInFeet + " ft. at speed " + speed);

		switch (driveStrategy) {
		
		case Duration:
			addSequential(new DriveDistanceCommand(getRobot(), commandName, distanceToDriveInFeet, speed));
			break;
			
		case Simulate:
			// Just print the command that would be executed if we weren't in Simulate mode
			addSequential(new MDPrintCommand(getRobot(), commandName, "Drive for a distance of " + distanceToDriveInFeet + " ft. at speed " + speed));
			break;
			
		case ClosedLoop:
			addSequential(new ClosedLoopDriveDistanceCommand(getRobot(), commandName, distanceToDriveInFeet, false));
			
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
		//		angle: number degrees to turn (positive to right; negative to left)
		//		speed: how fast to travel. The value depends on the type of drive strategy:
		//			- Duration: power rating (0.0 to 1.0)
		//			- Open Loop Encoder: power rating (0.0 to 1.0)
		//			- Closed Loop: velocity in feet per second
		private void addTurnCommand(double angle, double speed) {
			
			++stepNum;
			String commandName = "STEP " + stepNum + ": Turn";
			
			// Log what we're doing
			log("addTurnCommand","Adding Turn command " + commandName + " for " + angle + " degrees at speed " + speed);
			System.out.println("Adding Turn command " + commandName + " for " + angle + " degrees at speed " + speed);
			
			switch (driveStrategy) {
			
			case Duration:
				addSequential(new TurnCommand(getRobot(), commandName, angle, speed));
				break;
				
			case Simulate:
				// Just print the command that would be executed if we weren't in Simulate mode
				addSequential(new MDPrintCommand(getRobot(), commandName, "Turn for " + angle + " degrees at speed " + speed));
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
		//		waitTime - time to wait (in seconds)
		
		
		private void addWaitCommand(double waitTime) {
			
			String commandName = " WAIT";
			
			// Log what we're doing
			log("addWaitCommand","Adding Wait command " + commandName);
			System.out.println("Adding Wait command " + commandName );
			
			addSequential(new WaitCommand(getRobot(), commandName, waitTime));
		}
		
		private void addIdleCommand() {			
			
			++stepNum;			
			String commandName = "STEP " + stepNum + ": Idling ...";
			
			// Log what we're doing
			log("addIdleCommand","Adding Idle command " + commandName );
			System.out.println("Adding Idle command " + commandName );
			
			addSequential(new IdleCommand(getRobot(), commandName));
		}
		
		private void addAutoLiftCommand(double duration, double power) {
			
			++stepNum;	
			String commandName = "STEP " + stepNum + ": Lift";

			log("addAutoLiftCommand","Adding AutoLift command " + commandName );
			System.out.println("Adding Lift command " + commandName );
			
			addSequential(new AutoLiftCommand(getRobot(), commandName, power, duration));
		}

		private void addAutoClawCommand(double duration,  double power) {
			
			++stepNum;
			String commandName = "STEP " + stepNum + ": Claw";

			log("addAutoClawCommand","Adding AutoLift command " + commandName );
			System.out.println("Adding Lift command " + commandName );
			
			addSequential(new AutoClawCommand(getRobot(), commandName, duration, power));
		}
		
		private void addParallelLiftCommand(double duration, double power) {
			
			++stepNum;
			String commandName = "STEP " + stepNum + ": Parallel Lift";

			log("addAutoLiftCommand","Adding in Parallel AutoLift command " + commandName );
			System.out.println("Adding in Parallel Lift command " + commandName );
			
			addParallel(new AutoLiftCommand(getRobot(), commandName, power, duration));
		}

}



