package org.usfirst.frc.team1761.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team1761.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FourBar extends Subsystem {
	
	private DoubleSolenoid m_leftArm;
	private DoubleSolenoid m_rightArm;
	private DoubleSolenoid m_lifterPiston;
	private DoubleSolenoid m_kickerPiston;
	
	private boolean isExtended; 
	public FourBar() {
		super();
		m_leftArm = new DoubleSolenoid(0, 1);
		m_rightArm = new DoubleSolenoid(2, 3);
		m_lifterPiston = new DoubleSolenoid(4,5);
		m_kickerPiston = new DoubleSolenoid(6,7);
		
		//We should initialize the arms in the retracted position.
		// by firing both pistons with right setting.
		//TODO fire pistons
		isExtended = false;		
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		//setDefaultCommand(new ActuateArms());
	}
	
	public void extendArms() {
		isExtended = true;
		m_leftArm.set(DoubleSolenoid.Value.kForward);
		m_rightArm.set(DoubleSolenoid.Value.kForward);		
	}
	
	public void retractArms() {
		isExtended = false;
		m_leftArm.set(DoubleSolenoid.Value.kReverse);
		m_rightArm.set(DoubleSolenoid.Value.kReverse);		
		
	}

	public void toggleArms() {	
	    if(isExtended)
	    {
	    	retractArms();
	    } else {
	    	extendArms();
	    }		
	}
	
	public void lift( ) {
		m_lifterPiston.set(DoubleSolenoid.Value.kForward);
		
	}
	
	public void lower( ) {
		m_lifterPiston.set(DoubleSolenoid.Value.kReverse);		
	}
	
	public void kick() {
		m_kickerPiston.set(DoubleSolenoid.Value.kForward);
	}
	
	public void windup() {
		m_kickerPiston.set(DoubleSolenoid.Value.kReverse);	
	}
	
	
	

	

}