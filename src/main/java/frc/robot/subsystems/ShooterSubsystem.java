package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
        private CANSparkMax shooterLeft;
        private CANSparkMax shooterRight;
        
    public ShooterSubsystem() {
        shooterLeft = new CANSparkMax(ShooterConstants.kLeftShooterCanId, MotorType.kBrushless);
        shooterRight = new CANSparkMax(ShooterConstants.kRightShooterCanId, MotorType.kBrushless);
        shooterLeft.setInverted(false);
        shooterRight.setInverted(true);
    }
    public void drive(double speed){
        shooterLeft.set(speed);
        shooterRight.set(speed);
    }

    
}