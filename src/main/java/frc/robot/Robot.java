// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.ArcadeDrive;


public class Robot extends TimedRobot {
  private ArcadeDrive arcadeDrive = new ArcadeDrive();

  @Override
  public void teleopPeriodic() {
    arcadeDrive.periodic();
  }
}
