package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;

public class AutonomousSubsystem extends MDSubsystem {

	private double autoSpeed;
	private long autoDuration;
	
	
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
	}
	
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		
		if(changedSetting.getName().equals("autoSpeed")) autoSpeed = changedSetting.getDouble();
		if(changedSetting.getName().equals("autoDuration")) autoDuration = (long) (changedSetting.getDouble()*1000);
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
	

}
