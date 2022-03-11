// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ReverseIntakeWithButton extends Command {
  //initialize intakeSpeed variable
  
  // ReverseIntakeWithButton is run by OI on button press
  public ReverseIntakeWithButton() {
    requires(Robot.m_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  public void execute() {
    double intakeSpeed = Preferences.getDouble("Intake Factor", 0.0);
    Robot.m_intake.drive(-1 * intakeSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(){
    Robot.m_intake.drive(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
