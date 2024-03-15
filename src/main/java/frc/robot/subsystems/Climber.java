package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {

  /*-------------------------------- Private instance variables ---------------------------------*/
  private static Climber mInstance;
  private PeriodicIO mPeriodicIO;

  public static Climber getInstance() {
    if (mInstance == null) {
      mInstance = new Climber();
    }
    return mInstance;
  }

  private TalonSRX mLeftClimberMotor;
  private TalonSRX mRightClimberMotor;

  private Climber() {
    super("Climber");

    mPeriodicIO = new PeriodicIO();

    mLeftClimberMotor = new TalonSRX(ClimberConstants.kClimberLeftMotorId);
    mRightClimberMotor = new TalonSRX(ClimberConstants.kClimberRightMotorId);

    mLeftClimberMotor.setNeutralMode(NeutralMode.Brake);
    mRightClimberMotor.setNeutralMode(NeutralMode.Brake);

    mLeftClimberMotor.setInverted(true);
    mRightClimberMotor.setInverted(true);
  }

  private static class PeriodicIO {
    double climber_right_speed = 0.0; // RPM of spool (Spark Max default unit)
    double climber_left_speed = 0.0; // RPM of spool (Spark Max default unit)
  }

  /*-------------------------------- Generic Subsystem Functions --------------------------------*/

  @Override
  public void periodic() {
    //if these are called from periodic, they will automatically be called
    //    by the CommandScheduler instead of shoving it in Robot.periodic
    writePeriodicOutputs();
    outputTelemetry();
    //writeToLog();
  }

  public void writePeriodicOutputs() {
    mLeftClimberMotor.set(TalonSRXControlMode.PercentOutput,mPeriodicIO.climber_left_speed);
    mRightClimberMotor.set(TalonSRXControlMode.PercentOutput,mPeriodicIO.climber_right_speed);
  }

  public void stop() {
    stopClimber();
  }

  public void outputTelemetry() {
    /*
    putNumber("Left speed setpoint:", mPeriodicIO.climber_left_speed);
    putNumber("Left speed:", mLeftClimberEncoder.getVelocity());
    putNumber("Right speed setpoint:", mPeriodicIO.climber_right_speed);
    putNumber("Right speed:", mRightClimberEncoder.getVelocity());
    /* */
  }

  public void reset() {
  }

  /*---------------------------------- Custom Public Functions ----------------------------------*/

  public void setBrakeMode() {
    mLeftClimberMotor.setNeutralMode(NeutralMode.Brake);
    mRightClimberMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void setCoastMode() {
    mLeftClimberMotor.setNeutralMode(NeutralMode.Coast);
    mRightClimberMotor.setNeutralMode(NeutralMode.Coast);
  }

  public void climb() {
    mPeriodicIO.climber_left_speed = ClimberConstants.kClimberClimbPower;
    mPeriodicIO.climber_right_speed = ClimberConstants.kClimberClimbPower;
  }

  public void release() {
    mPeriodicIO.climber_left_speed = ClimberConstants.kClimberReleasePower;
    mPeriodicIO.climber_right_speed = ClimberConstants.kClimberReleasePower;
  }

  public void tiltLeft() {
    mPeriodicIO.climber_left_speed = ClimberConstants.kClimberReleasePower;
    mPeriodicIO.climber_right_speed = 0.0;
  }

  public void tiltRight() {
    mPeriodicIO.climber_left_speed = 0.0;
    mPeriodicIO.climber_right_speed = ClimberConstants.kClimberReleasePower;
  }

  public void stopClimber() {
    mPeriodicIO.climber_left_speed = 0.0;
    mPeriodicIO.climber_right_speed = 0.0;
  }

  /*---------------------------------- Custom Private Functions ---------------------------------*/
}
