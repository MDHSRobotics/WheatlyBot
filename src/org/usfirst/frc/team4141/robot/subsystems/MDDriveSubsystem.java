package org.usfirst.frc.team4141.robot.subsystems;

import java.util.Date;

import javax.print.attribute.standard.Media;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.MultiSpeedController;
import org.usfirst.frc.team4141.MDRobotBase.NotImplementedException;
import org.usfirst.frc.team4141.MDRobotBase.TankDriveInterpolator;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.sensors.MD_IMU;
import org.usfirst.frc.team4141.MDRobotBase.sensors.Sensor;
import org.usfirst.frc.team4141.robot.commands.ArcadeDriveCommand;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem.MotorPosition;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem.Type;
import com.analog.adis16448.frc.*;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class MDDriveSubsystem extends MDSubsystem {
	public enum Type{
		TankDrive,
		MecanumDrive
	}
	

	public enum MotorPosition{
		left,
		right,
		secondRight,
		secondLeft,
		frontLeft,
		rearLeft,
		frontRight,
		rearRight
	}
	public enum TalonPosition{
		
	}
	
	// ------------------------------------------------ //
	
	private DifferentialDrive differentialDrive;
	private MecanumDrive mecanumDrive;
	private Type type;
	private boolean isFlipped = false;
	private boolean resettingGyro = false;
	private long gyroResetStart;
	private long gyroResetDuration = 150;
	private double speed = 0;
	private double governor = 1.0;
	private double timeInS;
	private double targetDistance; 
	private double distanceInFeet;
	private double encoderDistance; // <--- Placeholder
	private MDDriveSubsystem driveSystem;
	private TankDriveInterpolator interpolator = new TankDriveInterpolator();
	public MD_IMU imu = new MD_IMU();
	public double yawAngle = imu.getAngle();
	
//	private double F=0.0;
//	private double P=0.0;
//	private double I=0.1;
//	private double D=0.0;
//	private double rpm=1.0;

	
	// ------------------------------------------------ //
	
	/**
	 * The method is used to hold the parameters robot, name, and type.
	 *  
	 * @param robot the default name used after the MDRobotBase in the constructor
	 * @param name the default name used after the string in the constructor
	 * @param type is used to determine the type of driveTrain
	 */
	public MDDriveSubsystem(MDRobotBase robot, String name, Type type) {
		super(robot, name);
		this.type = type;
		debug("\n at the end of the MDDrive Sbsystem Constructor after creating drive");
	}
	
	/**
	 * This method is used to hold the parameters position and speedController.
	 * Inside the method is a check to see if the speedController is a PWM or 
	 * a CANTALON in which it will add a position and a speedController. And if it is not a 
	 * PWM or a CANTALON it will return that the input is not a PWM.
	 * 
	 * @param position is used to set the different positions 
	 * @param speedController is used to set the motor to a speed controller
	 * @return true if the PWM or a CANTALON found, else Input is not a PWM.
	 */
	public MDDriveSubsystem add(MotorPosition position,SpeedController speedController){
		if(speedController instanceof PWM || speedController instanceof WPI_TalonSRX){
//			((TalonSRX) speedController).configClosedloopRamp(timeInS, 10);
//			((TalonSRX) speedController).configOpenloopRamp(timeInS, 10);
			super.add(position.toString(),(SpeedController)speedController);
		}
		else
		{
			throw new NotImplementedException("Input is not a PWM");
		}
		return this;
	}


	public MDDriveSubsystem add(String name,Sensor sensor){
		super.add(name,sensor);
		return this;
	}
	
	// ------------------------------------------------ //
	
	/**
	 * This method is used to map a SpeedController motor to a position on the robot, 
	 * and if the robot is a instance of of a speed controller return the speed controller
	 * as a motor.
	 * 
	 * @param position the mapping of a SpeedController to a motor on the robot
	 * @return a speedController motor, else return nothing.
	 */
	public SpeedController get(MotorPosition position){
		SpeedController motor = getMotors().get(position.toString());
		if(motor instanceof SpeedController){
			return (SpeedController) motor;
		}
		return null;
	}
	
	private void configureTalonSRX(MotorPosition motorPosition, MotorPosition motorToFollow, boolean invert){
		TalonSRX speedController;
		speedController = (TalonSRX) get(motorPosition);
		speedController.setInverted(invert);
		if(motorPosition == motorToFollow){
			//This is a master
			System.out.println("The master is ramping: " + timeInS);
			 speedController.configOpenloopRamp(timeInS, 10);
		}else{
			//this is a slave
			System.out.println("The slave is ramping: " + timeInS);
			TalonSRX speedControllerToFollow = (TalonSRX) get(motorToFollow);
			speedController.follow(speedControllerToFollow);
			speedController.configOpenloopRamp(timeInS, 0);
		}
//		((TalonSRX) speedController).configClosedloopRamp(timeInS, 10);
//		((TalonSRX) speedController).configOpenloopRamp(timeInS, 10);
	}
	
	/**
	 * This method is used as a fail-safe to check that all the motors and sensors used in the
	 * selected drive train is connected and ready to be used. If none of the items are connected 
	 * the Robot will not enable.
	 * 
	 * @return true unless there is a item not connected, and so stop the motors. 
	 */
	public MDSubsystem configure(){
		super.configure();
		// debug("inside MDDriveSubsystem Configure");
		// debug(this.toString());
		switch(type){
		case TankDrive:
			if(getMotors()==null){
				throw new IllegalArgumentException("Invalid motor configuration for TankDrive system.");
			}				
			if(getMotors().size()==2){
				if(!getMotors().containsKey(MotorPosition.left.toString()) || !getMotors().containsKey(MotorPosition.right.toString())){
					throw new IllegalArgumentException("Invalid MDDriveSubsystem TankDrive configuraton, missing motors.");
				}
				differentialDrive = new DifferentialDrive(get(MotorPosition.left), get(MotorPosition.right));
			}
			else if(getMotors().size()==4){
				if(!getMotors().containsKey(MotorPosition.rearLeft.toString()) || !getMotors().containsKey(MotorPosition.frontLeft.toString())
						  || !getMotors().containsKey(MotorPosition.rearRight.toString()) || !getMotors().containsKey(MotorPosition.frontRight.toString())){
					throw new IllegalArgumentException("Invalid MDDriveSubsystem TankDrive configuraton, missing motors.");
				}
				System.out.println(this.toString());
				configureTalonSRX(MotorPosition.frontLeft, MotorPosition.rearLeft, false);			//slave
				configureTalonSRX(MotorPosition.rearLeft, MotorPosition.rearLeft, false);			//master
				configureTalonSRX(MotorPosition.frontRight, MotorPosition.frontRight, false);		//master
				configureTalonSRX(MotorPosition.rearRight, MotorPosition.frontRight, false);		//slave
				differentialDrive = new DifferentialDrive(new MultiSpeedController(new SpeedController[]{get(MotorPosition.rearLeft), get(MotorPosition.frontLeft)}),
						new MultiSpeedController(new SpeedController[]{get(MotorPosition.rearRight), get(MotorPosition.frontRight)}));
			
			}
			System.out.println("Governor:" + governor);
			
//			if(getSolenoids()==null 
//					|| !getSolenoids().containsKey(rightShiftSolenoidName) || !(getSolenoids().get(rightShiftSolenoidName) instanceof Solenoid)) {
//					throw new IllegalArgumentException("Invalid MDDriveSubsystem configuraton, missing shift solenoid.");
//			}	
//			rightShiftSolenoid=(Solenoid) getSolenoids().get(rightShiftSolenoidName);

//			if(getSolenoids()==null 
//					|| !getSolenoids().containsKey(leftShiftSolenoidName) || !(getSolenoids().get(leftShiftSolenoidName) instanceof Solenoid)) {
//					throw new IllegalArgumentException("Invalid MDDriveSubsystem configuraton, missing shift solenoid1.");
//			}	
//			leftShiftSolenoid=(Solenoid) getSolenoids().get(leftShiftSolenoidName);
//			
			if(getSensors()==null && !getSensors().containsKey("IMU")){
				throw new IllegalArgumentException("Invalid MDDriveSubsystem configuraton, missing IMU.");
			}
		    imu=(MD_IMU) getSensors().get("IMU");
		    gyroReset();
		    if(getSensors()==null && !getSensors().containsKey("High Gear")){
				throw new IllegalArgumentException("Invalid MDDriveSubsystem configuraton, missing Gear Shift Sensors.");
			}
			differentialDrive.stopMotor();
			
			break;
		case MecanumDrive:
			if(getMotors()==null || !getMotors().containsKey(MotorPosition.rearLeft.toString()) || !getMotors().containsKey(MotorPosition.frontLeft.toString())
									  || !getMotors().containsKey(MotorPosition.rearRight.toString()) || !getMotors().containsKey(MotorPosition.frontRight.toString())){
				throw new IllegalArgumentException("Invalid motor configuration for MecanumDrive system.");
			}	
			mecanumDrive = new MecanumDrive(get(MotorPosition.rearLeft), get(MotorPosition.frontLeft),
					get(MotorPosition.rearRight), get(MotorPosition.frontRight));
			mecanumDrive.stopMotor();
			break;
		default:
			throw new NotImplementedException("drive of type "+type.toString()+" is not supported.");
		}

		return this;
	}
	
	/**
	 * This method is used to find the type of drive train selected.
	 * 
	 * @return the type of drive train selected.
	 */
	public Type getType() { 
		return type;
	}

	/**
	 * This method holds the default command used in a subsystem.
	 * The command that it is doing here is stopping the motors from moving, so that
	 * the robot does not drive away when we connect.
	 */
	@Override
	protected void initDefaultCommand() {
		
		switch(type) {
		case MecanumDrive:
			mecanumDrive.stopMotor();
			break;
		default:
			differentialDrive.stopMotor();
		}
		
		//set up default command, as needed
		//setDefaultCommand(new ArcadeDriveCommand(getRobot()));
	}
	
	// ------------------------------------------------ //

	/**
	 * This method holds the amount of power given to the motors
	 * when the driver moves the joystick in the x or y direction. 
	 * 
	 * @return the input of the joystick and output power
	 */
	private double calculateMagnitude(double x,double y){
		//joystick will give x & y in a range of -1 <= 0 <= 1
		// the magnitude indicates how fast the robot shoudl be driving
		// use the distance formula:  s = sqrt(x^2 = y^2)
		return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
	}
	
	/**
	 * This method takes into account the direction that the driver has the joystick in
	 * and then applies the speed accordingly towards the desired direction.
	 *  
	 * @return the input of the joystick and output direction
	 */
	private double calculateDirection(double x, double y){
			//joystick will give x & y in a range of -1 <= 0 <= 1
			// the direction indicates in what angle the robot should move. this is not rotation.
			// 0 degrees means go straight
			// 180 degrees means back up
			// 90 degrees means go to the right
			// use trigonometry.  Tangent (angle) = opposite (in our case x) / adjacent (in our case y)
			// we have x & y, solve for angle by taking the inverse tangent
			// angle = tangent^-1(x/y)
			// since this includes a division we need logic to handle things when x & y are 0
			double angle = 0;
			if(y==0){
				if(x>0) angle = 90;
				if (x<0) angle = -90;
			}
			else if (x==0){
				if(y<0) angle = 180;
			}
			else{
				angle = Math.atan2(x, y)*180/Math.PI;
			}
			return angle;  
	}
	
	/**
	 * This method holds the parameter joystick.
	 * This method connects the axis of the joystick to the different 
	 * values of magnitude, direction, and rotation for MecanumDrive or
	 * speeds 0 and 1 for Tank Drive.
	 *  
	 * @param joystick to be used as a variable calling the actual Joystick
	 * @return the input of the joystick and output direction
	 */
	public void arcadeDrive(Joystick joystick) {
		switch(type){
		case MecanumDrive:
			double magnitude= calculateMagnitude(joystick.getRawAxis(0),joystick.getRawAxis(1));
			double direction = calculateDirection(-joystick.getRawAxis(0),-joystick.getRawAxis(1));
			double rotation = joystick.getRawAxis(1);
			mecanumDrive.drivePolar(magnitude, direction, rotation);
			break;
		default:
		 // double rightTriggerValue = joystick.getRawAxis(3);
		 //	double leftTriggerValue = -joystick.getRawAxis(2);
		//added minus sign
			double deadThreshold = 0.05;
			
			double rotate = -joystick.getRawAxis(2); //(Changed to accompass shifting w/controller and deadzoned)
			SmartDashboard.putNumber("Raw Z Axis", rotate);
			if(rotate < deadThreshold && rotate > -deadThreshold) rotate = 0.0;
			
			double forwardAxisValue = joystick.getRawAxis(1);
			SmartDashboard.putNumber("Raw Y Axis", forwardAxisValue);
			if(forwardAxisValue < deadThreshold && forwardAxisValue > -deadThreshold) forwardAxisValue = 0.0;
			
			double forward = (forwardAxisValue)*(1.0-(1.0-governor));
		  	
		  	System.out.println("MDDriveSubsystem has run" + " forwardAxisValue: " + forwardAxisValue + " forward: " + forward + " rotate value: " + rotate);
		  	if(isFlipped){
		  		forward = -forward;
		  	}
	  	  //debug("forward = " + forward + ", rotate = " + rotate);
		  	if(rotate>0){
		  		System.out.print("turning");
		  	}
		  	double[] speeds = interpolator.calculate(forward, rotate);
		  	SmartDashboard.putNumber("Speed Left", speeds[0]);
		  	SmartDashboard.putNumber("Speed Right", speeds[1]);
		    //debug("left: "+speeds[0]+", right: "+speeds[1]);
		  	differentialDrive.tankDrive(-speeds[0], -speeds[1]);
		  	System.out.println("Your two speeds are: " + -speeds[0] + " and " + -speeds[1]);
		}
	}
	
	// ------------------------------------------------ //

	/**
	 * This method calls all the drive motors that are being used 
	 * to come to a complete stop.
	 */
	public void stop(){
//		debug("motors stopped");
		speed = 0;
		switch(type) {
		case TankDrive:
			differentialDrive.stopMotor();
			break;
		case MecanumDrive:
			mecanumDrive.stopMotor();
		default:
		}
	}	
	
	/**
	 * This method is used for defining the variable c, a, and b to show up 
	 * on the MDConsole. C being the governor of the speed, a being the speed, 
	 * and be being the torque of the motors.
	 */
	@Override
	protected void setUp() {
		//called after configuration is completed
		if(getConfigSettings().containsKey("governor")) governor = getConfigSettings().get("governor").getDouble();
		if(getConfigSettings().containsKey("Ramp Time In seconds")) timeInS = getConfigSettings().get("Ramp Time In seconds").getDouble();
		if(getConfigSettings().containsKey("forwardSpeed")) interpolator.setA(getConfigSettings().get("forwardSpeed").getDouble());
		if(getConfigSettings().containsKey("rotateSpeed")) interpolator.setB(getConfigSettings().get("rotateSpeed").getDouble());
//		if(getConfigSettings().containsKey("F")) F = getConfigSettings().get("F").getDouble();
//		if(getConfigSettings().containsKey("P")) P = getConfigSettings().get("P").getDouble();
//		if(getConfigSettings().containsKey("I")) I = getConfigSettings().get("I").getDouble();
//		if(getConfigSettings().containsKey("D")) D = getConfigSettings().get("D").getDouble();
//		if(getConfigSettings().containsKey("RPM")) rpm = getConfigSettings().get("RPM").getDouble();//*1000;
	}
	
	/**
	 * This method allows us to change the values of the variable on the fly, 
	 * without going and re-deploying the code every time we want to change the value.
	 * Instead we can now do it with the new and improved MDConsole.
	 */
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		if(changedSetting.getName().equals("governor")) governor = changedSetting.getDouble();
		if(changedSetting.getName().equals("Ramp Time In seconds")) timeInS = changedSetting.getDouble();
		if(changedSetting.getName().equals("forwardSpeed")) interpolator.setA(changedSetting.getDouble());
		if(changedSetting.getName().equals("rotateSpeed")) interpolator.setB(changedSetting.getDouble());
//		if(changedSetting.getName().equals("F")) F = changedSetting.getDouble();
//		if(changedSetting.getName().equals("P")) P = changedSetting.getDouble()*pidFactor;
//		if(changedSetting.getName().equals("I")) I = changedSetting.getDouble()*pidFactor;
//		if(changedSetting.getName().equals("D")) D = changedSetting.getDouble()*pidFactor;
//		if(changedSetting.getName().equals("RPM")) rpm = changedSetting.getDouble();//*1000;
		//method to listen to setting changes
	}

	// ------------------------------------------------ //
	
	
	public void pivot(double power) {
		switch(type){
		case MecanumDrive:
			//TODO fix
//			double magnitude= calculateMagnitude(joystick.getRawAxis(0),joystick.getRawAxis(1));
//			double direction = calculateDirection(-joystick.getRawAxis(0),-joystick.getRawAxis(1));
//			double rotation = joystick.getRawAxis(1);
//			mecanumDrive.drivePolar(magnitude, direction, rotation);
			break;
		default:
		  	double[] speeds = interpolator.calculate(0.0, power);
		    //debug("left: "+speeds[0]+", right: "+speeds[1]);
		  	differentialDrive.tankDrive(speeds[0], speeds[1]);
		}
	}
	
	/**
	 * This method calls the robot to make a right turn, 
	 * which is mainly used for testing and maybe autonomous commands.
	 * 
	 * @param speed used for the activation of the motors 
	 */
	public void right(double speed) {
		this.speed = speed;
		
		if (isFlipped) {
			this.speed = -this.speed;
		}
		debug("right");
		double direction = -90;
		switch(type){
		case MecanumDrive:
			mecanumDrive.drivePolar(speed, direction, 0);
			break;
		default:
			differentialDrive.tankDrive(this.speed, this.speed/10);
		}
	}

	/**
	 * This method calls the robot to make a left turn, 
	 * which is mainly used for testing and maybe autonomous commands.
	 * 
	 * @param speed used for the activation of the motors 
	 */
	
	public void left(double speed) {
		this.speed = speed;
		if (isFlipped) {
			this.speed = -this.speed;
		}
		debug("left");
		double direction = 90;
		switch(type){
		case MecanumDrive:
			mecanumDrive.drivePolar(speed, direction, 0);
			break;
		default:
			differentialDrive.tankDrive(this.speed/10, this.speed);
		}
	}

	/**
	 * This method calls the robot to go backwards, 
	 * which is mainly used for testing and maybe autonomous commands.
	 * 
	 * @param speed used for the activation of the motors 
	 */
	public void reverse(double speed) {
		this.speed = speed;
		if (isFlipped) {
			this.speed = -this.speed;
		}
		debug("reverse");
		double direction = 180;
		switch(type){
		case MecanumDrive:
			mecanumDrive.drivePolar(speed, direction, 0);
			break;
		default:
			differentialDrive.tankDrive(-this.speed, this.speed);
		}
	}

	/**
	 * This method calls the robot to go fowards, 
	 * which is mainly used for testing and maybe autonomous commands.
	 * 
	 * @param speed used for the activation of the motors 
	 */
	public void forward(double speed) {
		
	//	debug("forward"); 	
		this.speed = speed;
		if (isFlipped) {
			this.speed = -this.speed;
		}
		
		double direction = 0;
		
		switch(type){
		case MecanumDrive:
			mecanumDrive.drivePolar(speed, direction, 0);
			break;
		default:
			// debug("speed =" + speed); // <--- Enable for speed debug
			differentialDrive.tankDrive(this.speed, this.speed);
		}
	}
	
	// ------------------------------------------------ //

	/**
	 * This method calls the robot to flip its motors opposite of 
	 * its current direction. 
	 */
//	private void configureTalonSRX(MotorPosition motorPosition, MotorPosition motorToFollow){
//		TalonSRX speedController;
//		speedController = (TalonSRX) get(motorPosition);
//		if(motorPosition == motorToFollow){
//			//This is a master
//			System.out.println("The master is ramping: " + timeInS);
//			 speedController.configOpenloopRamp(timeInS, 10);
//		}else{
//			//this is a slave
//			System.out.println("The slave is ramping: " + timeInS);
//			TalonSRX speedControllerToFollow = (TalonSRX) get(motorToFollow);
//			speedController.follow(speedControllerToFollow);
//			speedController.configOpenloopRamp(timeInS, 0);
//		}
////		((TalonSRX) speedController).configClosedloopRamp(timeInS, 10);
////		((TalonSRX) speedController).configOpenloopRamp(timeInS, 10);
//	}
	public void flip() {
		if (speed != 0) return;
		isFlipped = !isFlipped;
		debug("flip. isFlipped now sent to " + isFlipped + ". speed = " + speed);
	}
	
	
	/**
	 * This method calls that if the robot receives a go ahead 
	 * from the gyroReset method, then set the time back to 0 until 
	 * the current time is greater than the gyroResetDuration. 
	 * Else the gyro has not been rested.
	 * 
	 * @return the z angle of the imu.
	 */
	public double getAngle() {
		if (resettingGyro) { 
			long now = (new Date()).getTime();
			if (now - gyroResetStart <= gyroResetDuration) return 0;
			else resettingGyro = false;
		}

		return imu.getAngleZ();
//		return imu.getAngleX();
	}

	// ------------------------------------------------ //

	/**
	 * This method calls the reset of the current angle 
	 * values on the imu back to 0, and then states that the 
	 * gyro has been reseted. It also grabs a new time stamp.
	 * 
	 * 
	 * @return the z angle of the imu.
	 */
	public void gyroReset() {
		resettingGyro = true;
	    gyroResetStart = (new Date()).getTime();
	    
	    // Reset IMU call was missing - WH???
	    // imu.reset();
	}

	/**
	 * This method corrects the robot for when the direction is flipped,
	 * so that a strait direction can be achieved when the speed is flipped.
	 * 
	 * @param speed to be used for the amount of power
	 * @param angle to be used for the directional adjustments 
	 */
	public void move(double speed, double angle) {
		if(speed == 0) {stop();return;}
		double correction = angle/180.00;
  	  	debug("speed = " + speed + ", angle = " + angle+ ", correction = "+correction+", isFlipped = "+ isFlipped);
	  	//double[] speeds = interpolator.calculate(speed, correction, isFlipped);
		double[] speeds = new double[2];
		if(angle>=0){
			if(angle>10) angle = 10;
			speeds[1]=speed;
			speeds[0]=speed*(1.0 - angle/10.0);
		}
		else{
			if(angle<-10) angle = -10;
			speeds[1]=speed*(1.0 + angle/10.0);
			speeds[0]=speed;
		}
		differentialDrive.tankDrive(speeds[0], speeds[1]);
	}
	
	public String toString(){
		String objectString;
		objectString = super.toString();
		objectString += "\n Drive Type =" + type;
		if (differentialDrive != null)
			objectString += "\n Differential Drive: Name = " + differentialDrive.getName() + "; Descrip = " + differentialDrive.getDescription() + "; IsSafetyEnabled = " + differentialDrive.isSafetyEnabled();
		return objectString;
	}

	/**
	 * This method is still in the works.
	 */
	//TODO 
	}

	// boolean isOn = false; // Why is this here? What is this? It doesn't link to anything.;





