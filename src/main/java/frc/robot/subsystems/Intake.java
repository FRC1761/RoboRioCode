package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.*;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkBase.ControlType;
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
  private final SparkClosedLoopController mPivotPID;
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
  private SparkMax mIntakeMotor;
  private SparkMax mPivotMotor;

  private Intake() {
    super("Intake");
    // CANSparK Settings
    mIntakeMotor = new SparkMax(IntakeConstants.kIntakeCanId, MotorType.kBrushless);
    SparkMaxConfig intakeConfig = new SparkMaxConfig(),
                   pivotConfig  = new SparkMaxConfig();
    intakeConfig.idleMode(SparkBaseConfig.IdleMode.kCoast);
    mIntakeMotor.configure(intakeConfig,SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
    mPivotMotor = new SparkMax(IntakeConstants.kArmPivotCanId, MotorType.kBrushless);
    pivotConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
    pivotConfig.smartCurrentLimit(40);
    mPivotMotor.configure(pivotConfig, SparkBase.ResetMode.kNoResetSafeParameters,SparkBase.PersistMode.kPersistParameters);
    m_pivotEncoder = mPivotMotor.getAbsoluteEncoder();

    m_leds = LEDs.getInstance();

    if(isPIDcontrolled){
      pivotConfig.closedLoop.pid(k_pivotMotorP, k_pivotMotorI, k_pivotMotorD);
      pivotConfig.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder);
      mPivotMotor.configure(pivotConfig,SparkBase.ResetMode.kNoResetSafeParameters,SparkBase.PersistMode.kPersistParameters);
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
      mPivotPID.setSetpoint(pivot_angle,ControlType.kPosition);
    } else {
      m_periodicIO.intake_pivot_voltage = getPivotPercentage();
      //mPivotMotor.set(m_periodicIO.intake_pivot_voltage);
    }

//mPivotPID.
    // If the pivot is at exactly 0.0, it's probably not connected, so disable it
    if (m_pivotEncoder.getPosition() == 0.0) {
      if(isPIDcontrolled){
        mPivotPID.setSetpoint(0.0,ControlType.kPosition);
      } else {
        mPivotMotor.set(0.0);
      }
      System.out.println("OOPs no pivotEncoder pivot is set to ");
    }

    // Intake control
    m_periodicIO.intake_speed = intakeStateToSpeed(m_periodicIO.intake_state);
    SmartDashboard.putString("Intake State", m_periodicIO.intake_state.toString());
    SmartDashboard.putString("Pivot State", m_periodicIO.pivot_target.toString());
    
    writePeriodicOutputs();
   // outputTelemetry();
  } //end periodic

  public void writePeriodicOutputs() {
    if(isPIDcontrolled){
      mPivotPID.setSetpoint(m_periodicIO.intake_pivot_voltage,SparkMax.ControlType.kVoltage);
    } else {
      mPivotMotor.set(m_periodicIO.intake_pivot_voltage);
    }
    mIntakeMotor.set(m_periodicIO.intake_speed);
  }

  public void stop() {
    m_periodicIO.intake_pivot_voltage = 0.0;
    m_periodicIO.intake_speed = 0.0;
  }

  public void outputTelemetry() {
    SmartDashboard.putNumber("Speed", intakeStateToSpeed(m_periodicIO.intake_state));
    SmartDashboard.putNumber("Pivot/Abs Enc (getPivotAngle)", getPivotAngle());
    SmartDashboard.putNumber("Pivot/Setpoint", pivotTargetToAngle(m_periodicIO.pivot_target));

    SmartDashboard.putBoolean("Intake_Limit_Switch", getIntakeHasNote());
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
      goToStow();
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
    return Math.abs(getPivotAngle() - pivotTargetToAngle(m_periodicIO.pivot_target)) < 5.0/360;
  }
}