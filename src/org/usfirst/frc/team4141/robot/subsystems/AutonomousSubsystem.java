package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.config.StringConfigSetting;

public class AutonomousSubsystem extends MDSubsystem {

	private double autoSpeed;
	private long autoDuration;
	private MDDriveSubsystem driveSystem;
	
	public enum TypeOfDriveStrategy {
		Duration,			// Open Loop based on time duration
		ClosedLoop,			// Closed Loop using encoder
		Simulate,			// Don't actually use motors, just simulate commands to log
		Undefined			// Placeholder for strategy not set
	}	
	
	// Strategy to determine how far robot has traveled - 
	// Change this in the code, not in MDConsole because it must be set when the robot is initialized!
	private TypeOfDriveStrategy driveStrategy = TypeOfDriveStrategy.Duration;   
	
	private double delayStartTime = 0.0;						// Time in seconds to delay the autonomous commands
	
	private int startingPosition = 1;							// Starting position (1, 2, or 3)

	
	//--------------------------------------------------------//
	
	public AutonomousSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
		setCore(true);
	}
	
	//--------------------------------------------------------//

	
	@Override
	protected void setUp() {
		
		if(getConfigSettings().containsKey("autoSpeed")) autoSpeed = getConfigSettings().get("autoSpeed").getDouble();
		if(getConfigSettings().containsKey("autoDuration")) autoDuration = (long) (getConfigSettings().get("autoDuration").getDouble()*1000);

		// Configuration setting for time (in seconds) to delay starting autonmous commands
		if(getConfigSettings().containsKey("delayStartTime")) delayStartTime = getConfigSettings().get("delayStartTime").getDouble();
		
		// Configuration setting for starting position (1, 2, or 3)
		if(getConfigSettings().containsKey("startingPosition")) startingPosition = getConfigSettings().get("startingPosition").getInt();
		
	}
	
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		
		if(changedSetting.getName().equals("autoSpeed")) autoSpeed = changedSetting.getDouble();
		if(changedSetting.getName().equals("autoDuration")) autoDuration = (long) (changedSetting.getDouble()*1000);
		
		// Configuration setting for time (in seconds) to delay starting autonmous commands
		if(getConfigSettings().containsKey("delayStartTime")) delayStartTime = getConfigSettings().get("delayStartTime").getDouble();
		
		// Configuration setting for starting position (1, 2, or 3)
		if(getConfigSettings().containsKey("startingPosition")) startingPosition = getConfigSettings().get("startingPosition").getInt();
	}
	
	@Override
	protected void initDefaultCommand() {

	}
	
	//--------------------------------------------------------//
	
	public double getAutoSpeed() {
		return autoSpeed;
	}
	
	public long getAutoDuration() {
		return autoDuration;
	}
	
	public void AUTOPosOne_RLR(){
//		driveSystem.driveDistance(10, .5);
//		driveSystem.turn(90);
//		driveSystem.driveDistance(10, .5);
//		driveSystem.turn(90);
	}
	public void AUTOPosTwo_RLR(){
//		driveSystem.driveDistance(10, .5);
//		driveSystem.turn(90);
//		driveSystem.driveDistance(10, .5);
//		driveSystem.turn(90);
//		driveSystem.driveDistance(10, .5);
//		driveSystem.turn(90);
//		driveSystem.driveDistance(10, .5);
//		driveSystem.turn(90);
	//	driveSystem.driveDistance(10, .5);
	//	driveSystem.turn(90);
	}
	public void AUTOPosThree_RLR(){
	//	driveSystem.driveDistance(10, .5);
	//	driveSystem.turn(90);
	//	driveSystem.driveDistance(10, .5);
	//	driveSystem.turn(90);
	//	driveSystem.driveDistance(10, .5);
	//	driveSystem.turn(90);
	}
	public void AUTOPosOne_LRL(){
	//	driveSystem.driveDistance(10, .5);
	//	driveSystem.turn(90);
	//	driveSystem.driveDistance(10, .5);
	//	driveSystem.turn(90);
	//	driveSystem.driveDistance(10, .5);
	//	driveSystem.turn(90);
	}
	public void AUTOPosTwo_LRL(){
	//	driveSystem.driveDistance(10, .5);
	//	driveSystem.turn(90);
	//	driveSystem.driveDistance(10, .5);
	//	driveSystem.turn(90);
	//	driveSystem.driveDistance(10, .5);
	//	driveSystem.turn(90);
	//	driveSystem.driveDistance(10, .5);
	//	driveSystem.turn(90);
	//	driveSystem.driveDistance(10, .5);
	//	driveSystem.turn(90);
	}
	public void AUTOPosThree_LRL(){
	//	driveSystem.driveDistance(10, .5);
	//	driveSystem.turn(90);
	//	driveSystem.driveDistance(10, .5);
	//	driveSystem.turn(90);
	}
	public void AUTOPosOne_LLL(){
	//	driveSystem.driveDistance(10, .5);
	//	driveSystem.turn(180);
	//	driveSystem.driveDistance(10, .5);
		//driveSystem.turn(90);
//		driveSystem.driveDistance(10, .5);
	}
	public void AUTOPosTwo_LLL(){
	//	driveSystem.driveDistance(10, .5);
	}
	public void AUTOPosThree_LLL(){
	//	driveSystem.driveDistance(10, .5);
	}
	public void AUTOPosOne_RRR(){
		//driveSystem.driveDistance(10, .5);
	}
	public void AUTOPosTwo_RRR(){
		//driveSystem.driveDistance(10, .5);
	}
	public void AUTOPosThree_RRR(){
		//driveSystem.driveDistance(10, .5);
	}
	public void end(){
		driveSystem.stop();
	}
	
	public TypeOfDriveStrategy getDriveStrategy() {
		return driveStrategy;
	}

	public double getDelayStartTime() {
		return delayStartTime;
	}
	
	public int getStartingPosition() {
		return startingPosition;
	}
}
