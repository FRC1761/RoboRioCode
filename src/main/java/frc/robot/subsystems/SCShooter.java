package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import frc.robot.RobotPreferences;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.OIConstants;
import frc.robot.Constants.ShooterConstants;

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
  private   XboxController m_operatorController = new XboxController(OIConstants.kOperatorControllerPort);

  private SCShooter() {
    super("SCShooter");

    mPeriodicIO = new PeriodicIO();

    mLeftShooterMotor = new CANSparkFlex(ShooterConstants.kLeftShooterCanId, MotorType.kBrushless);
    mRightShooterMotor = new CANSparkFlex(ShooterConstants.kRightShooterCanId, MotorType.kBrushless);
    mLeftShooterMotor.restoreFactoryDefaults();
    mRightShooterMotor.restoreFactoryDefaults();

    mLeftShooterPID = mLeftShooterMotor.getPIDController();
    mLeftShooterPID.setP(RobotPreferences.getShooterP());
    mLeftShooterPID.setI(RobotPreferences.getShooterI());
    mLeftShooterPID.setD(RobotPreferences.getShooterD());
    mLeftShooterPID.setFF(RobotPreferences.getShooterFF());
    mLeftShooterPID.setOutputRange(ShooterConstants.kShooterMinOutput, ShooterConstants.kShooterMaxOutput);

    mRightShooterPID = mRightShooterMotor.getPIDController();
    mRightShooterPID.setP(RobotPreferences.getShooterP());
    mRightShooterPID.setI(RobotPreferences.getShooterI());
    mRightShooterPID.setD(RobotPreferences.getShooterD());
    mRightShooterPID.setFF(RobotPreferences.getShooterFF());
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
   // setPIDfromPreferences();
    writePeriodicOutputs();
    outputTelemetry();
  }

  public void writePeriodicOutputs() {
    double limitedSpeed = mSpeedLimiter.calculate(mPeriodicIO.shooter_rpm);
    mLeftShooterPID.setReference(limitedSpeed, ControlType.kVelocity);
    mRightShooterPID.setReference(limitedSpeed, ControlType.kVelocity);
  }

  public void setPIDfromPreferences(){
    if(RobotPreferences.getPIDTuning()){
    mLeftShooterPID.setP(RobotPreferences.getShooterP());
    mLeftShooterPID.setI(RobotPreferences.getShooterI());
    mLeftShooterPID.setD(RobotPreferences.getShooterD());
    mLeftShooterPID.setFF(RobotPreferences.getShooterFF());
    
    mRightShooterPID.setP(RobotPreferences.getShooterP());
    mRightShooterPID.setI(RobotPreferences.getShooterI());
    mRightShooterPID.setD(RobotPreferences.getShooterD());
    mRightShooterPID.setFF(RobotPreferences.getShooterFF());
    }
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
  public double getSpeed(){
    double result = 0.0;
    if( m_operatorController.getLeftBumper()) result = RobotPreferences.getSpeakerSpeed();
    if( m_operatorController.getRightBumper()) result = RobotPreferences.getAmpSpeed();
    return result;
  } 

  public boolean isAtSpeed(){
    boolean isItReally = mLeftShooterEncoder.getVelocity() >= mPeriodicIO.shooter_rpm && 
           mRightShooterEncoder.getVelocity() >= mPeriodicIO.shooter_rpm;
    SmartDashboard.putBoolean("isAtSpeed:", isItReally);

    return isItReally;
  }
  /*---------------------------------- Custom Private Functions ---------------------------------*/
}