package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ShooterConstants;
//TODO update Robot to use SCShooter to match Cranberry's speed controlled Version (this one)
public class SCShooter extends SubsystemBase {

  /*-------------------------------- Private instance variables ---------------------------------*/
  private static SCShooter mInstance;
  private PeriodicIO mPeriodicIO;

  public static SCShooter getInstance() {
    if (mInstance == null) {
      mInstance = new SCShooter();
    }
    return mInstance;
  }

  private CANSparkFlex mLeftShooterMotor;
  private CANSparkFlex mRightShooterMotor;

  private SparkPIDController mLeftShooterPID;
  private SparkPIDController mRightShooterPID;

  private RelativeEncoder mLeftShooterEncoder;
  private RelativeEncoder mRightShooterEncoder;

  private SlewRateLimiter mSpeedLimiter = new SlewRateLimiter(1000);

  private SCShooter() {
    super("SCShooter");

    mPeriodicIO = new PeriodicIO();

    mLeftShooterMotor = new CANSparkFlex(ShooterConstants.kLeftShooterCanId, MotorType.kBrushless);
    mRightShooterMotor = new CANSparkFlex(ShooterConstants.kRightShooterCanId, MotorType.kBrushless);
    mLeftShooterMotor.restoreFactoryDefaults();
    mRightShooterMotor.restoreFactoryDefaults();

    mLeftShooterPID = mLeftShooterMotor.getPIDController();
    mLeftShooterPID.setP(ShooterConstants.kShooterP);
    mLeftShooterPID.setI(ShooterConstants.kShooterI);
    mLeftShooterPID.setD(ShooterConstants.kShooterD);
    mLeftShooterPID.setFF(ShooterConstants.kShooterFF);
    mLeftShooterPID.setOutputRange(ShooterConstants.kShooterMinOutput, ShooterConstants.kShooterMaxOutput);

    mRightShooterPID = mRightShooterMotor.getPIDController();
    mRightShooterPID.setP(ShooterConstants.kShooterP);
    mRightShooterPID.setI(ShooterConstants.kShooterI);
    mRightShooterPID.setD(ShooterConstants.kShooterD);
    mRightShooterPID.setFF(ShooterConstants.kShooterFF);
    mRightShooterPID.setOutputRange(ShooterConstants.kShooterMinOutput, ShooterConstants.kShooterMaxOutput);

    mLeftShooterEncoder = mLeftShooterMotor.getEncoder();
    mRightShooterEncoder = mRightShooterMotor.getEncoder();

    mLeftShooterMotor.setIdleMode(CANSparkFlex.IdleMode.kCoast);
    mRightShooterMotor.setIdleMode(CANSparkFlex.IdleMode.kCoast);

    mLeftShooterMotor.setInverted(false);
    mRightShooterMotor.setInverted(true);

  }

  private static class PeriodicIO {
    double shooter_rpm = 0.0;
  }

  /*-------------------------------- Generic Subsystem Functions --------------------------------*/

  @Override
  public void periodic() {
    writePeriodicOutputs();
    outputTelemetry();
  }

  public void writePeriodicOutputs() {
    double limitedSpeed = mSpeedLimiter.calculate(mPeriodicIO.shooter_rpm);
    mLeftShooterPID.setReference(limitedSpeed, ControlType.kVelocity);
    mRightShooterPID.setReference(limitedSpeed, ControlType.kVelocity);
  }
  public void stop() {
    stopShooter();
  }
  public void outputTelemetry() {
    SmartDashboard.putNumber("Speed (RPM):", mPeriodicIO.shooter_rpm);
    SmartDashboard.putNumber("Left speed:", mLeftShooterEncoder.getVelocity());
    SmartDashboard.putNumber("Right speed:", mRightShooterEncoder.getVelocity());
  }

  public void reset() {
  }

  /*---------------------------------- Custom Public Functions ----------------------------------*/

  public void setSpeed(double rpm) {
    mPeriodicIO.shooter_rpm = rpm;
  }

  public void stopShooter() {
    mPeriodicIO.shooter_rpm = 0.0;
  }

  /*---------------------------------- Custom Private Functions ---------------------------------*/
}