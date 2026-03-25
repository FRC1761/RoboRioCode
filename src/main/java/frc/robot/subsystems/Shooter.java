// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.DigitalOutput;
//include libraries we will use 
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.time.Period;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.Robot;
import frc.robot.RobotPreferences;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {

  //subsystem properties
  private static Shooter m_instance; 
  private static PeriodicIO m_PeriodicIO;
  private final SparkFlex shooterMotor;
  private final SparkMax  gateMotor;
  private final RelativeEncoder shooterEncoder;
  private final AbsoluteEncoder gateEncoder;
  private final Timer clock;
  private double gatePower;
  /** Creates a new Shooter. */
  public Shooter() {
    shooterMotor = new SparkFlex(ShooterConstants.ShooterAddress,
                                 MotorType.kBrushless);
    shooterEncoder = shooterMotor.getEncoder();

    gateMotor = new SparkMax(ShooterConstants.GateAddress,MotorType.kBrushless);
    gateEncoder = gateMotor.getAbsoluteEncoder();
    SparkMaxConfig gateConfig = new SparkMaxConfig();
    gateConfig.idleMode(SparkMaxConfig.IdleMode.kBrake);
    gateConfig.smartCurrentLimit(30);
    gateMotor.configure(gateConfig,ResetMode.kNoResetSafeParameters,PersistMode.kPersistParameters);
    gatePower = RobotPreferences.getGatePower();
    m_PeriodicIO = new PeriodicIO();
    clock = new Timer();
    clock.start();
  }

  public static Shooter getInstance() {
    if (m_instance == null) {
      m_instance = new Shooter();
    }
    return m_instance;
  }

  
  public void shootFar(){   
    shooterMotor.set(RobotPreferences.getShooterFar());
  }

  public void shootShort(){   
    shooterMotor.set(RobotPreferences.getShooterShort());
  }

  public void shootReverse(){   
    shooterMotor.set(-RobotPreferences.getShooterFar());
  }

  public void moveGate(){
    gateMotor.set(RobotPreferences.getGatePower());
  }

  public void openGate() {
    m_PeriodicIO.gateTarget = PeriodicIO.GateState.OPEN;
  }
  
  public void closeGate() {
    m_PeriodicIO.gateTarget = PeriodicIO.GateState.CLOSED;
  }

  private boolean isGateAtTarget(){
    double angleDiff  = getGateAngle()-gateToTargetAngle(); 
    return Math.abs(angleDiff) < .1;
  }

  private double getGatePercentage() {
    double diffAngle = 0.0;
    switch (m_PeriodicIO.gateTarget){
      case CLOSED:
        diffAngle = getGateAngle() - m_PeriodicIO.gateCloseAngle;
        break;
      case OPEN:
        diffAngle = getGateAngle() - m_PeriodicIO.gateOpenAngle;
        break;
    }
    return -1.0 * gatePower*diffAngle;
  }

  private double gateToTargetAngle(){
    if(m_PeriodicIO.gateTarget == PeriodicIO.GateState.CLOSED) return 0.75;
    else return .5; //thats 90 degrees
  
  }

  public void writePeriodicOutputs() {

    if(!isGateAtTarget()){
      gateMotor.set(getGatePercentage());
    }
  }

  private static class PeriodicIO{
    ShooterTarget shooterTarget = ShooterTarget.NONE;
    ShooterState shooterState   = ShooterState.OFF;
    GateState gateState         = GateState.CLOSED;
    GateState gateTarget        = GateState.CLOSED;
    double gatePower = RobotPreferences.getGatePower();
    double gateOpenAngle =RobotPreferences.getGateOpenAngle(),
           gateCloseAngle = RobotPreferences.getGateCloseAngle(); 
  
    public enum ShooterTarget {
      NONE,
      CLOSESHOT,
      MEDIUMSHOT,
      FARSHOT,
    }

    public enum ShooterState {
      OFF,
      RAMPINGUP,
      READY,
    }

    public enum GateState {
      OPEN,
      CLOSED,
    }
  }

  public double getGateAngle(){
    return gateEncoder.getPosition();
  }

  public void stop(){
    shooterMotor.set(0);
  }


  @Override
  public void periodic() {
    if(gateEncoder.getPosition()==0.0){
      //System.out.println("PivotEncoder is registering at Zero, check connection");
    }

    writePeriodicOutputs();
    if(clock.hasElapsed)){
      RobotPreferences.setGateState(m_PeriodicIO.gateTarget.toString());
      RobotPreferences.setShooterSpeedDisplay(shooterEncoder.getVelocity());
      Preferences.setDouble("gateAngle", getGateAngle());
      Preferences.setDouble("gatePercentage",getGatePercentage());
      clock.reset();
    }
  }
}
