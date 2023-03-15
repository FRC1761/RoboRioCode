package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmExtension extends SubsystemBase {
    // CANSparkMax elevatorMax = new CANSparkMax(Constants.elevatorMax, MotorType.kBrushless);
    CANSparkMax extensionMax = new CANSparkMax(Constants.extensionMax, MotorType.kBrushless);
    XboxController driverController = new XboxController(Constants.xboxPort);
    public double leftTrigger = driverController.getLeftTriggerAxis();
    public double rightTrigger = driverController.getRightTriggerAxis();

    public double driveLimiter;
    


    public ArmExtension() {
        super();
        //get key value or use default 1.0;
        driveLimiter = Preferences.getDouble("ArmExtension Factor", 1.0);
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
        double triggerSum = -(leftTrigger) + rightTrigger; // XOR gate
        
        extensionMax.set(triggerSum*driveLimiter);
    }
  
    @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    }

}


