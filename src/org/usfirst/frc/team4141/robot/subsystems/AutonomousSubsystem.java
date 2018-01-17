package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;

public class AutonomousSubsystem extends MDSubsystem {

	private double scenario1Speed;
	
	//--------------------------------------------------------//
	
	public AutonomousSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
		setCore(true);
	}
	
	//--------------------------------------------------------//

	
	@Override
	protected void setUp() {
		
		if(getConfigSettings().containsKey("scenario1Speed")) scenario1Speed = getConfigSettings().get("scenario1Speed").getDouble();

	}
	
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		
		if(changedSetting.getName().equals("scenario1Speed")) scenario1Speed = changedSetting.getDouble();

	}
	
	@Override
	protected void initDefaultCommand() {

	}
	
	//--------------------------------------------------------//
	
	public long getScenario1Speed() {
		return scenario1Speed;
	}

}
