/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.Constants;
import frc.robot.commands.ShooterDriveWithJoystick;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;

/**
 * Add your docs here.
 */
public class VelocityControlledShooter extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
    TalonSRX _talonL = new TalonSRX(Constants.ShooterLeft);
    TalonSRX _talonR = new TalonSRX(Constants.ShooterRight);
    Joystick _joy = new Joystick(0);
    /* String for output */
    StringBuilder _sb = new StringBuilder();
    /* Loop tracker for prints */
  	int _loops = 0;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    initTalon(_talonR);
    initTalon(_talonL);
    
    
  }
  
  private void initTalon(TalonSRX tal){
     tal.configFactoryDefault();
    
      /* Config sensor used for primary PID [Velocity] */
      tal.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                                            Constants.kPIDLoopIdx, 
                                            Constants.kTimeoutMs);
      /**
       * Phase sensor accordingly. 
       * Positive Sensor Reading should match Green (blinking) Leds on Talon
       */

  		tal.setSensorPhase(true);  
    /* Config the peak and nominal outputs */
		tal.configNominalOutputForward(0, Constants.kTimeoutMs);
		tal.configNominalOutputReverse(0, Constants.kTimeoutMs);
		tal.configPeakOutputForward(1, Constants.kTimeoutMs);
		tal.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		/* Config the Velocity closed loop gains in slot0 */
		tal.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
		tal.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
		tal.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
		tal.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);
  }
}
