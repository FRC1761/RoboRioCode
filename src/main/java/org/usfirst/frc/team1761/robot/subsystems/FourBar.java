package org.usfirst.frc.team1761.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class FourBar extends Subsystem {
	
	private DoubleSolenoid m_leftArm;
	private DoubleSolenoid m_rightArm;
	private DoubleSolenoid m_lifterPiston1;
	private DoubleSolenoid m_lifterPiston2;	
	private DoubleSolenoid m_kickerPiston;
	private Compressor myCompressor;
	
	private boolean isExtended; 
	public FourBar() {
		super();
		m_leftArm = new DoubleSolenoid(0, 0, 1);
		m_rightArm = new DoubleSolenoid(0, 2, 3);
		m_lifterPiston1 = new DoubleSolenoid(0, 4, 5);
		m_lifterPiston2 = new DoubleSolenoid(0, 6, 7);
		m_kickerPiston = new DoubleSolenoid(1, 0, 1);
		myCompressor = new Compressor(1);
		myCompressor.stop();
		//We should initialize the arms in the retracted position.
		// by firing both pistons with right setting.
		isExtended = false;		
	}
	
	@Override
	protected void initDefaultCommand() {
		// Auto-generated method stub
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
	
	public boolean getStatus() {
		return myCompressor.getPressureSwitchValue();
	}
	
	public void lift( ) {
		m_lifterPiston1.set(DoubleSolenoid.Value.kForward);
		m_lifterPiston2.set(DoubleSolenoid.Value.kForward);
	}
	
	public void lower( ) {
		m_lifterPiston1.set(DoubleSolenoid.Value.kReverse);		
		m_lifterPiston2.set(DoubleSolenoid.Value.kReverse);	
	}
	
	public void kick() {
		m_kickerPiston.set(DoubleSolenoid.Value.kForward);
	}
	
	public void windup() {
		m_kickerPiston.set(DoubleSolenoid.Value.kReverse);	
	}
	
}