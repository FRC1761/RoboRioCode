// Copyright 2021-2024 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot.subsystems.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

/** IO implementation for Pigeon2 */
public class GyroADXRS450 implements GyroIO {
  private final ADXRS450_Gyro gyro = new ADXRS450_Gyro();
  public boolean connected = false;
  public Rotation2d yawPosition = new Rotation2d();
  public double yawVelocityRadPerSec = 0.0;
  
  public GyroADXRS450() {
        this.gyro.calibrate();
  }

  @Override
  public void updateInputs(GyroIOInputs inputs) {
    inputs.connected = gyro.isConnected();
    inputs.yawPosition = gyro.getRotation2d();
    inputs.yawVelocityRadPerSec = Units.degreesToRadians(gyro.getRate());
  }
}
