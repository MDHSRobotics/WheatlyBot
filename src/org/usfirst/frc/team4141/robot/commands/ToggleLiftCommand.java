package org.usfirst.frc.team4141.robot.commands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.subsystems.LiftSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem.MotorPosition;

import edu.wpi.first.wpilibj.command.Scheduler;

public class ToggleLiftCommand extends MDCommand {
	
	public ToggleLiftCommand(MDRobotBase robot) {
		super (robot, "Toggle Lift Command");
		
		driveSystem = (MDDriveSubsystem)getRobot().getSubsystem("driveSystem");
		
		liftSystem = (LiftSubsystem)getRobot().getSubsystem("liftSubsystem"); 
		requires(liftSystem);
		
	}
	
	//---------------------//
	
	private LiftSubsystem liftSystem;
	private MDDriveSubsystem driveSystem;
	
	//---------------------//
	
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
		
	}
	
	@Override
	protected void end() {
		Scheduler.getInstance().add(new LiftCommand(getRobot()));
	}
	
	
	

}
