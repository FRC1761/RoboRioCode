package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Timer;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants.IntakeConstants;
import frc.robot.RobotPreferences;
/*
 * Intake Subsystem modified from 
 * https://github.com/CranberryAlarm/CA24_RobotCode
 */
public class Intake extends SubsystemBase {
  //Our Neo could move intake arm at .06 so start there 
  //.12 was Cranberry's start point
  private static final double k_pivotMotorP = 0.06;
  private static final double k_pivotMotorI = 0.0;
  private static final double k_pivotMotorD = 0.001; 

  private static final boolean isPIDcontrolled = false;
  private final SparkPIDController mPivotPID;
  //private final PIDController m_pivotPID = new PIDController(k_pivotMotorP, k_pivotMotorI, k_pivotMotorD);

  //private final DutyCycleEncoder m_pivotEncoder = new DutyCycleEncoder(IntakeConstants.kArmPivotEncoderId);
  private final AbsoluteEncoder m_pivotEncoder;

  private final DigitalInput m_IntakeLimitSwitch = new DigitalInput(IntakeConstants.kIntakeLimitSwitchId);

  /*-------------------------------- Private instance variables ---------------------------------*/
  private static Intake mInstance;
  private PeriodicIO m_periodicIO;
  private LEDs m_leds;

  public static Intake getInstance() {
    if (mInstance == null) {
      mInstance = new Intake();
    }
    return mInstance;
  }

  //private TalonSRX mIntakeMotor;
  private TalonSRX mIntakeMotor;
  private CANSparkMax mPivotMotor;

  private Intake() {
    super("Intake");
    // CANSparK Settings
    //mIntakeMotor = new CANSparkMax(IntakeConstants.kIntakeCanId, MotorType.kBrushless);
    //mIntakeMotor.restoreFactoryDefaults();
    //mIntakeMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
    // Talon Settings
    mIntakeMotor = new TalonSRX(IntakeConstants.kIntakeCanId);
    mIntakeMotor.configFactoryDefault();
    mIntakeMotor.setNeutralMode(NeutralMode.Coast);

    mPivotMotor = new CANSparkMax(IntakeConstants.kArmPivotCanId, MotorType.kBrushless);
    mPivotMotor.restoreFactoryDefaults();
    mPivotMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    mPivotMotor.setSmartCurrentLimit(40);
    m_pivotEncoder = mPivotMotor.getAbsoluteEncoder(Type.kDutyCycle);

    m_leds = LEDs.getInstance();

    if(isPIDcontrolled){
      mPivotPID = mPivotMotor.getPIDController();
      mPivotPID.setP(k_pivotMotorP);
      mPivotPID.setI(k_pivotMotorI);
      mPivotPID.setD(k_pivotMotorD);
      mPivotPID.setFeedbackDevice(mPivotMotor.getAbsoluteEncoder());      
    } else {
      mPivotPID = null;
    }
    m_periodicIO = new PeriodicIO();
  }

  private static class PeriodicIO {
    // Input: Desired state
    PivotTarget pivot_target = PivotTarget.STOW;
    IntakeState intake_state = IntakeState.NONE;

    // Output: Motor set values
    double intake_pivot_voltage = 0.0;
    double intake_speed = 0.0;
  }

  public enum PivotTarget {
    NONE,
    GROUND,
    SOURCE,
    AMP,
    STOW
  }

  public enum IntakeState {
    NONE,
    INTAKE,
    EJECT,
    PULSE,
    FEED_SHOOTER,
  }

  /*-------------------------------- Generic Subsystem Functions --------------------------------*/

  @Override
  public void periodic() {
    checkAutoTasks();

    // Pivot control
    if(isPIDcontrolled){
      double pivot_angle = pivotTargetToAngle(m_periodicIO.pivot_target);
      mPivotPID.setReference(pivot_angle,ControlType.kPosition);
    } else {
      m_periodicIO.intake_pivot_voltage = getPivotPercentage();
      //mPivotMotor.set(m_periodicIO.intake_pivot_voltage);
    }

//mPivotPID.
    // If the pivot is at exactly 0.0, it's probably not connected, so disable it
    if (m_pivotEncoder.getPosition() == 0.0) {
      if(isPIDcontrolled){
        mPivotPID.setReference(0.0,ControlType.kPosition);
      } else {
        mPivotMotor.set(0.0);
      }
      System.out.println("OOPs no pivotEncoder pivot is set to ");
    }

    // Intake control
    m_periodicIO.intake_speed = intakeStateToSpeed(m_periodicIO.intake_state);
    SmartDashboard.putString("Intake State", m_periodicIO.intake_state.toString());
    writePeriodicOutputs();
    outputTelemetry();
  } //end periodic

  public void writePeriodicOutputs() {
    if(isPIDcontrolled){
      mPivotPID.setReference(m_periodicIO.intake_pivot_voltage,CANSparkMax.ControlType.kVoltage);
    } else {
      mPivotMotor.set(m_periodicIO.intake_pivot_voltage);
    }
    //mPivotMotor.setVoltage(m_periodicIO.intake_pivot_voltage);
    mIntakeMotor.set(TalonSRXControlMode.PercentOutput,m_periodicIO.intake_speed); //Talon
  }

  public void stop() {
    m_periodicIO.intake_pivot_voltage = 0.0;
    m_periodicIO.intake_speed = 0.0;
  }

  public void outputTelemetry() {
    SmartDashboard.putNumber("Speed", intakeStateToSpeed(m_periodicIO.intake_state));
    SmartDashboard.putNumber("Pivot/Abs Enc (getPosition)", m_pivotEncoder.getPosition());
    SmartDashboard.putNumber("Pivot/Abs Enc (getPivotAngle)", getPivotAngle());
    SmartDashboard.putNumber("Pivot/Setpoint", pivotTargetToAngle(m_periodicIO.pivot_target));

    SmartDashboard.putNumber("Pivot/Power", m_periodicIO.intake_pivot_voltage);
    SmartDashboard.putNumber("Pivot/Current", mPivotMotor.getOutputCurrent());

    SmartDashboard.putBoolean("Intake Limit Switch", getIntakeHasNote());
  }

  public void reset() {
  }

  public double pivotTargetToAngle(PivotTarget target) {
    switch (target) {
      case GROUND:
        return IntakeConstants.k_pivotAngleGround;
      case SOURCE:
        return IntakeConstants.k_pivotAngleSource;
      case AMP:
        return IntakeConstants.k_pivotAngleAmp;
      case STOW:
        return IntakeConstants.k_pivotAngleStow;
      default:
        // "Safe" default
        return .5;
    }
  }

  public double intakeStateToSpeed(IntakeState state) {
    switch (state) {
      case INTAKE:
        return IntakeConstants.k_intakeSpeed;
      case EJECT:
        return RobotPreferences.getEjectSpeed();
      case PULSE:
      /*
        // Use the timer to pulse the intake on for a 1/16 second,
        // then off for a 15/16 second
        if (Timer.getFPGATimestamp() % 1.0 < (1.0 / 45.0)) {
          return IntakeConstants.k_pulseSpeed;
        }/* */
        return 0.0;
      case FEED_SHOOTER:
        return IntakeConstants.k_feedShooterSpeed;
      default:
        // "Safe" default
        return 0.0;
    }
  }

  /*---------------------------------- Custom Public Functions ----------------------------------*/

  public IntakeState getIntakeState() {
    return m_periodicIO.intake_state;
  }

  public double getPivotAngle() {
    //getPosition should return rotations just like 
    //former code DutyCycleEncoder.getAbsolutePosition.  
    double value = m_pivotEncoder.getPosition() -
        IntakeConstants.k_pivotEncoderOffset; 
    return value;
  }

  public boolean getIntakeHasNote() {
    // NOTE: this is intentionally inverted, because the limit switch is normally
    // closed.  It returns true when NO note.  
    return !m_IntakeLimitSwitch.get();
  }

  public double getPivotPercentage(){
    //Note: we will automatically go to STOW if we have note
    if(getIntakeHasNote()) {
      setPivotTarget(PivotTarget.STOW);
      m_leds.goGreen();
    }
    switch(m_periodicIO.pivot_target){
      case GROUND:

        if(getPivotAngle()-.2 < IntakeConstants.k_pivotAngleAmp){
          return -IntakeConstants.kPivotPercentage;
        } else {
          return -IntakeConstants.kPivotSlowPercentage;
        }

      case STOW:
        if(!getIntakeHasNote()) {
          m_leds.goTeamColor();
        }
        if(getPivotAngle() > IntakeConstants.k_pivotAngleAmp){
          return IntakeConstants.kPivotPercentage;
        } else {
          return IntakeConstants.kPivotSlowPercentage;
        }
      case NONE:
      case AMP:
      case SOURCE:
      default:
        return 0.0;
    }
  }
  // Pivot helper functions
  public void goToGround() {
    m_periodicIO.pivot_target = PivotTarget.GROUND;
    m_periodicIO.intake_state = IntakeState.INTAKE;
    m_leds.goYellow();
  }

  public void goToSource() {
    m_periodicIO.pivot_target = PivotTarget.SOURCE;
    m_periodicIO.intake_state = IntakeState.NONE;
  }

  public void goToAmp() {
    m_periodicIO.pivot_target = PivotTarget.SOURCE;
    m_periodicIO.intake_state = IntakeState.NONE;
  }

  public void goToStow() {
    m_periodicIO.pivot_target = PivotTarget.STOW;
    m_periodicIO.intake_state = IntakeState.NONE;
  }

  // Intake helper functions
  public void intake() {
    m_periodicIO.intake_state = IntakeState.INTAKE;
  }

  public void eject() {
    m_periodicIO.intake_state = IntakeState.EJECT;
  }

  public void pulse() {
    m_periodicIO.intake_state = IntakeState.PULSE;
  }

  public void feedShooter() {
    m_periodicIO.intake_state = IntakeState.FEED_SHOOTER;
  }

  public void stopIntake() {
    m_periodicIO.intake_state = IntakeState.NONE;
    m_periodicIO.intake_speed = 0.0;
  }

  public void setState(IntakeState state) {
    m_periodicIO.intake_state = state;
  }

  public void setPivotTarget(PivotTarget target) {
    m_periodicIO.pivot_target = target;
  }

  /*---------------------------------- Custom Private Functions ---------------------------------*/
  private void checkAutoTasks() {
    // If the intake is set to GROUND, and the intake has a note, and the pivot is
    // close to it's target
    // Stop the intake and go to the SOURCE position
    if (m_periodicIO.pivot_target == PivotTarget.GROUND && getIntakeHasNote() && isPivotAtTarget()) {
      m_periodicIO.pivot_target = PivotTarget.STOW;
      m_periodicIO.intake_state = IntakeState.NONE;
      //m_leds.setColor(Color.kGreen);
    }
  }

  private boolean isPivotAtTarget() {
    //TODO need to recheck tolerance here. It was < 5 degrees (we use rotations)
    return Math.abs(getPivotAngle() - pivotTargetToAngle(m_periodicIO.pivot_target)) < 5.0/360;
  }
}