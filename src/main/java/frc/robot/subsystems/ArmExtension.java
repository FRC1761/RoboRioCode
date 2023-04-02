package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmExtension extends SubsystemBase {
    // CANSparkMax elevatorMax = new CANSparkMax(Constants.elevatorMax, MotorType.kBrushless);
    WPI_TalonSRX extensionTalon = new WPI_TalonSRX(Constants.extensionTalon);
    XboxController driverController = new XboxController(Constants.xboxPort);
    public double driveLimiter;

    public ArmExtension() {
        super();
        //get key value or use default 0.5
        driveLimiter = Preferences.getDouble("ArmExtension Factor", 0.5);
        //Push value back to Preferences widget so it forces
        //correct key to show up with default value if not set.
        Preferences.setDouble("ArmExtension Factor",driveLimiter);
    }

    /**
     * Example command factory method.
     *
     * @return a command
     */

     
    public CommandBase exampleMethodCommand() {
      // Inline construction of command goes here.
      // Subsystem::RunOnce implicitly requires `this` subsystem.
      return runOnce(
          () -> {
            /* one-time action goes here */
          });
    }
  
    @Override
    public void periodic() {
       // XOR gate
        double triggerSum = -driverController.getLeftTriggerAxis() + driverController.getRightTriggerAxis();
        
        extensionTalon.set(triggerSum*driveLimiter);
    }
  
    @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    }

}