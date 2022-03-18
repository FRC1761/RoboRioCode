// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AdjustAlignment extends Command {
  private double m_timeout;
  // AdjustAlignment is run by OI on button press
  public AdjustAlignment(double timeout) {
    m_timeout = timeout;
    requires(Robot.m_drivetrain);
    }

  public void initialize() {
    setTimeout(m_timeout);
    Robot.m_drivetrain.drive(1, 1);
  }
    
  public void execute() {
  }
    
  public boolean isFinished() {
    return isTimedOut(); //stops the loop after `timeout` seconds
  }
    
  public void end() {
    Robot.m_drivetrain.drive(0, 0); //triggered by isFinished
  }
    
  public void interrupted() {
    Robot.m_drivetrain.drive(0, 0); //emerg stop
  }
}