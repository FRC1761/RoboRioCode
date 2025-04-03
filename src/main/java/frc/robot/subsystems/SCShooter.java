package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.RelativeEncoder;

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

  private SparkMax mLeftShooterMotor, mRightShooterMotor;

  private SparkBaseConfig mLeftConfig,mRightConfig;

  private SparkClosedLoopController mLeftShooterPID, mRightShooterPID;

  private RelativeEncoder mLeftShooterEncoder, mRightShooterEncoder;

  private SlewRateLimiter mSpeedLimiter = new SlewRateLimiter(1000);
  private   XboxController m_operatorController = new XboxController(OIConstants.kOperatorControllerPort);

  private SCShooter() {
    super("SCShooter");

    mPeriodicIO = new PeriodicIO();

    mLeftShooterMotor = new SparkMax(ShooterConstants.kLeftShooterCanId, MotorType.kBrushless);
    mRightShooterMotor = new SparkMax(ShooterConstants.kRightShooterCanId, MotorType.kBrushless);

    mLeftConfig = new SparkMaxConfig();
    mLeftConfig.idleMode(IdleMode.kCoast);
    mLeftConfig.inverted(false);
    mLeftConfig.closedLoop.p(RobotPreferences.getShooterP());
    mLeftConfig.closedLoop.i(RobotPreferences.getShooterI());
    mLeftConfig.closedLoop.d(RobotPreferences.getShooterD());
    mLeftConfig.closedLoop.velocityFF(RobotPreferences.getShooterFF());
    mLeftConfig.closedLoop.outputRange(ShooterConstants.kShooterMinOutput, ShooterConstants.kShooterMaxOutput);
    mLeftShooterMotor.configure(mLeftConfig,
                                ResetMode.kResetSafeParameters,
                                PersistMode.kPersistParameters);
    //Right is same as left config except for Inverted state
    mRightConfig = new SparkMaxConfig();
    mRightConfig.apply(mLeftConfig);
    mRightConfig.inverted(true);
    mRightShooterMotor.configure(mRightConfig,
                                 ResetMode.kResetSafeParameters,
                                 PersistMode.kPersistParameters);

    mLeftShooterEncoder = mLeftShooterMotor.getEncoder();
    mRightShooterEncoder = mRightShooterMotor.getEncoder();

    mLeftShooterPID = mLeftShooterMotor.getClosedLoopController();
    mRightShooterPID = mRightShooterMotor.getClosedLoopController();
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
      mLeftConfig.closedLoop.p(RobotPreferences.getShooterP());
      mLeftConfig.closedLoop.i(RobotPreferences.getShooterI());
      mLeftConfig.closedLoop.d(RobotPreferences.getShooterD());
      mLeftConfig.closedLoop.velocityFF(RobotPreferences.getShooterFF());
    
      //both motors use the same PID settings. 
      mLeftShooterMotor.configure(mLeftConfig, null, null);
      mRightShooterMotor.configure(mLeftConfig, null, null);
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