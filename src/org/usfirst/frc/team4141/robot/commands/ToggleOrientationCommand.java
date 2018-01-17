package org.usfirst.frc.team4141.robot.commands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;

import edu.wpi.first.wpilibj.command.Scheduler;

public class ToggleOrientationCommand extends MDCommand {

	public ToggleOrientationCommand(MDRobotBase robot, String name) {
		super(robot, name);
		if(!getRobot().getSubsystems().containsKey("driveSystem")){
			log(Level.ERROR, "initialize()",  "Orientation not found");
			throw new IllegalArgumentException("Orientation system not found");
		}
		driveSystem = (MDDriveSubsystem)getRobot().getSubsystems().get("driveSystem"); 
		requires(driveSystem);
		
	}
	
	// ------------------------------------------------ //
	
	private MDDriveSubsystem driveSystem;
	
	// ------------------------------------------------ //
	
	@Override
	protected void initialize() {
		driveSystem.stop();
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}
	
	@Override
	protected void execute() {
		driveSystem.flip();
		
	}
	
	@Override
	protected void end() {
		Scheduler.getInstance().add(new ArcadeDriveCommand(getRobot()));
	}

}
