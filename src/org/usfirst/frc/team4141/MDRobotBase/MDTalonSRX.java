package org.usfirst.frc.team4141.MDRobotBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class MDTalonSRX extends WPI_TalonSRX {

	public MDTalonSRX(int deviceNumber) {
		super(deviceNumber);
		
	}
	
	public String toString() {
		String printString;
		
		printString = "Talon SRX: (";

		printString += "Name = " + this.getName() + "; ";
		printString += "Description = " + this.getDescription() + "; ";
		printString += "Inverted = " + this.getInverted() + "; ";
		printString += "Is Alive = " + this.isAlive() + "; ";
		printString += "Safety Enabled = " + this.isSafetyEnabled() + "; ";
		printString += "Firmware Ver = " + this.getFirmwareVersion()  + "; ";
		printString += "ID = " + this.getBaseID() + "; ";
		printString += ")";				
		return printString;
	}

}
