// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.PWM.PeriodMultiplier;
//import edu.wpi.first.wpilibj.DigitalOutput;
//include libraries we will use 
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkClosedLoopController;

import frc.robot.RobotPreferences;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {

  //subsystem properties
  private static Shooter m_instance; 
  private static PeriodicIO m_PeriodicIO;
  private final SparkFlex shooterMotor;
  private final SparkClosedLoopController shooterController;
  private final SparkMax  gateMotor;
  private final SparkClosedLoopController gateController;
  private final RelativeEncoder shooterEncoder;
  private final AbsoluteEncoder gateEncoder;
  private final Timer clock;
  private double gatePower;
  /** Creates a new Shooter. */
  public Shooter() {
    shooterMotor = new SparkFlex(ShooterConstants.ShooterAddress,
                                 MotorType.kBrushless);
    shooterEncoder = shooterMotor.getEncoder();
    shooterController = shooterMotor.getClosedLoopController();
    SparkFlexConfig shooterConfig = new SparkFlexConfig();
    shooterConfig.idleMode(IdleMode.kCoast);
    shooterConfig.closedLoop.p(ShooterConstants.shooterP);
    shooterConfig.closedLoop.d(ShooterConstants.shooterD);
    
    shooterMotor.configure(shooterConfig,ResetMode.kNoResetSafeParameters,PersistMode.kPersistParameters);

    gateMotor = new SparkMax(ShooterConstants.GateAddress,MotorType.kBrushless);
    gateEncoder = gateMotor.getAbsoluteEncoder();
    SparkMaxConfig gateConfig = new SparkMaxConfig();
    gateConfig.idleMode(SparkMaxConfig.IdleMode.kBrake);
    gateConfig.smartCurrentLimit(30);
    gateConfig.closedLoop.p(ShooterConstants.gateP);
    gateConfig.closedLoop.d(ShooterConstants.gateD);    

    gateMotor.configure(gateConfig,ResetMode.kNoResetSafeParameters,PersistMode.kPersistParameters);
    gateController = gateMotor.getClosedLoopController();
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
    m_PeriodicIO.shooterTarget = PeriodicIO.ShooterTarget.FARSHOT;
    //shooterMotor.set(RobotPreferences.getShooterFar();
  }

  public void shootShort(){   
    m_PeriodicIO.shooterTarget = PeriodicIO.ShooterTarget.CLOSESHOT;
    //shooterMotor.set(RobotPreferences.getShooterShort());
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

  //this is old code before PiD controlled Gate.
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

  //this returns the number of rotations to turn motor to target angle
  private double gateToTargetRotations(){
    if(m_PeriodicIO.gateTarget == PeriodicIO.GateState.CLOSED) return ShooterConstants.kGateCloseAngle;
    else return ShooterConstants.kGateOpenAngle; //thats 90 degrees
  }

  private double shooterTargetToVelocity(){
    double speed = 0.0;
    if(m_PeriodicIO.shooterTarget == PeriodicIO.ShooterTarget.NONE) {
      speed = 0.0;
    } else if (m_PeriodicIO.shooterTarget == PeriodicIO.ShooterTarget.FARSHOT){
      speed = ShooterConstants.kShootFarSpeed;
    } else if (m_PeriodicIO.shooterTarget == PeriodicIO.ShooterTarget.CLOSESHOT){
      speed = ShooterConstants.kShootShortSpeed;
    }
    return speed;
  }

  public void writePeriodicOutputs() {
    double targetVelocity = shooterTargetToVelocity();

    shooterController.setSetpoint(targetVelocity, ControlType.kVelocity);
    gateController.setSetpoint(gateToTargetRotations(), ControlType.kPosition);
    //if we reach 90% of speed let's drop gate
    if(shooterEncoder.getVelocity() > .7 * targetVelocity && 
       m_PeriodicIO.shooterTarget != PeriodicIO.ShooterTarget.NONE){
      openGate();
    }
    
    /* old code software driving gate to target
    if(!isGateAtTarget()){
      gateMotor.set(getGatePercentage());
    }
      /**/
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
    //we are no longer going to set motor power directly
    //shooterMotor.set(0);
    m_PeriodicIO.shooterTarget = PeriodicIO.ShooterTarget.NONE;
    m_PeriodicIO.gateTarget    = PeriodicIO.GateState.CLOSED;
  }


  @Override
  public void periodic() {
    if(gateEncoder.getPosition()==0.0){
      //System.out.println("PivotEncoder is registering at Zero, check connection");
    }

    writePeriodicOutputs();
    if(clock.hasElapsed(.5)){
      RobotPreferences.setGateState(m_PeriodicIO.gateTarget.toString());
      RobotPreferences.setShooterSpeedDisplay(shooterEncoder.getVelocity());
      Preferences.setDouble("gateAngle", getGateAngle());
      Preferences.setDouble("gatePercentage",getGatePercentage());
      clock.reset();
    }
  }
}
