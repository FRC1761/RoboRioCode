package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
        private SparkMax shooterLeft, shooterRight;
        private SparkMaxConfig config;
        
    public ShooterSubsystem() {
        shooterLeft = new SparkMax(ShooterConstants.kLeftShooterCanId, MotorType.kBrushless);
        shooterRight = new SparkMax(ShooterConstants.kRightShooterCanId, MotorType.kBrushless);
        config = new SparkMaxConfig();
        config.inverted(false);

        shooterLeft.configure(config,
                              ResetMode.kResetSafeParameters,
                              PersistMode.kPersistParameters);

        config.inverted(true);

        shooterRight.configure(config,
                               ResetMode.kResetSafeParameters,
                               PersistMode.kPersistParameters);
    }
    public void drive(double speed){
        shooterLeft.set(speed);
        shooterRight.set(speed);
    }

    
}