/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.commands.ArmDriveWithJoystick;
import edu.wpi.first.wpilibj.SpeedController;
//import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.AnalogInput;
/**
 * Add your docs here.
 */
public class Arm extends Subsystem { 
   //private final SpeedController armMotor = new SpeedControllerGroup(new WPI_TalonSRX(21), new WPI_TalonSRX(24));//Todo set can bus 
   private final SpeedController armMotor = new WPI_TalonSRX(21);
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private final AnalogInput distSensor = new AnalogInput(0);
  private double driveFactor = Preferences.getInstance().getDouble("Arm Factor", 1.0);

  public void drive(double armValue) {
    armMotor.set(armValue*driveFactor);
  }

  public void drive(Joystick joy) {
    armMotor.set(joy.getY()*driveFactor);
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new ArmDriveWithJoystick());

  }

  public double getDistance(){
    double dist = distSensor.getVoltage()/2; //distance in centimeters should be voltage/2
    return dist;
  }

  public void log() {
    System.out.println("UltraSonice Reads: " + getDistance() +"cm");
  }
}
