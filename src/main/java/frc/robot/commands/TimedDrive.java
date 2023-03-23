// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class TimedDrive extends CommandBase {
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  
  private Double timeout;
  
  public TimedDrive(Double seconds) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.m_drive);
    timeout = seconds;
  }
  
  public void startTimer() {
    expireTime = timeSinceInitialized() + timeout;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTimer();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
  }
  
  // Returns true when the command should end.
  public boolean isFinished() {
    return (timeSinceInitialized() >= expireTime);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}
  
}
