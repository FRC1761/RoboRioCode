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
   private final SpeedController armMotor = new WPI_TalonSRX(22);
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private final AnalogInput distSensor = new AnalogInput(0);
  private double driveFactor;
  //These values represent the different encoder target values
  // to reach different levels of the Hatch Portal or Cargo. 
  //seven in total, ground + 1 for each cargo and hatch height. 
  private double[] encoderTargets;
  private int targetIndex;

  public Arm(){
    //get key or use default
    Preferences myPrefs = Preferences.getInstance();
    driveFactor = myPrefs.getDouble("Arm Factor", 1.0);
    //Push value back to Preferences widget so it forces
    //correct key to show up with default value if not set. 
    myPrefs.putDouble("Arm Factor", driveFactor);

    targetIndex =0 ;  //0 references Ground
    encoderTargets[0] = 0;   //Arm encoder should read 0 at ground level.
    encoderTargets[1] = myPrefs.getDouble("Hatch Level 1",1000);
    myPrefs.putDouble("Hatch Level 1",encoderTargets[1]);
    encoderTargets[1] = myPrefs.getDouble("Cargo Level 1",2000);
    myPrefs.putDouble("Cargo Level 1",encoderTargets[1]);
    encoderTargets[1] = myPrefs.getDouble("Hatch Level 2",3000);
    myPrefs.putDouble("Hatch Level 2",encoderTargets[1]);
    encoderTargets[1] = myPrefs.getDouble("Cargo Level 2",4000);
    myPrefs.putDouble("Cargo Level 2",encoderTargets[1]);
    encoderTargets[1] = myPrefs.getDouble("Hatch Level 3",5000);
    myPrefs.putDouble("Hatch Level 3",encoderTargets[1]);
    encoderTargets[1] = myPrefs.getDouble("Cargo Level 3",6000);
    myPrefs.putDouble("Cargo Level 3",encoderTargets[1]);
  }

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
