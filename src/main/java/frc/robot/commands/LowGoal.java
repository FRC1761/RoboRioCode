// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Encoder;

public class LowGoal extends Command {
    
  private Encoder shooterEncoder;


  public LowGoal() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_intake);
    requires(Robot.m_shooter);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    shooterEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k1X);
    shooterEncoder.getRate();
    System.out.println("ULTRA CANNON 3000 INITIATED...");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    while (shooterEncoder.getRate() < 32750) {
      Robot.m_shooter.drive(0.7);
    }

    while (shooterEncoder.getRate() > 29000) {
      Robot.m_intake.drive(1.0);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (shooterEncoder.getRate() <= 31000) {
      return true;
    } else {
      return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_shooter.drive(0);
    Robot.m_intake.drive(0);
    
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {}
}
