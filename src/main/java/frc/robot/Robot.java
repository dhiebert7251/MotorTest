// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private CANSparkMax driveMotor;
  private VictorSPX turnMotor;

  private RelativeEncoder driveEncoder;
  private AnalogEncoder turnEncoder;

  private int kDriveMotorID = 20;
  private int kTurnMotorID = 30;
  private int kTurnEncoderID = 0;

  //private ShuffleboardTab motorTab =  Shuffleboard.getTab("Motor Data");



  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    driveMotor = new CANSparkMax(kDriveMotorID, MotorType.kBrushless);
    turnMotor = new VictorSPX(kTurnMotorID);

    driveEncoder = driveMotor.getEncoder();
    turnEncoder = new AnalogEncoder(kTurnEncoderID);

    driveEncoder.setPosition(0.0); //initialize position to 0
    driveEncoder.setPositionConversionFactor(33.488); //360 degrees divided by 10.75 gear ratio - output in degrees
    driveEncoder.setVelocityConversionFactor((5676/10.75)*Units.inchesToMeters(12.56)/60); //(5676 free speed / 10.75 gear ratio) * 12.56" circumference (to meters) /60 seconds
    
    turnEncoder.setDistancePerRotation(360); //rotation in degrees
    turnEncoder.reset();
    
    SmartDashboard.putNumber("Drive ID", kDriveMotorID);
    SmartDashboard.putNumber("Initial Drive Position", driveEncoder.getPosition()); //should be 0
    SmartDashboard.putNumber("Position Conversion Factor", driveEncoder.getPositionConversionFactor());
    SmartDashboard.putNumber("Velocity Conversion Factor m/s", driveEncoder.getVelocityConversionFactor());
    SmartDashboard.putNumber("Initial Drive Velocity", driveEncoder.getVelocity());

    SmartDashboard.putNumber("Turn Motor ID", kTurnMotorID);
    SmartDashboard.putNumber("Turn Encoder ID", kTurnEncoderID);
    SmartDashboard.putNumber("Distance Per Rotation", turnEncoder.getDistancePerRotation());
    SmartDashboard.putNumber("Initial Turn Encoder Value", turnEncoder.get());
    SmartDashboard.putNumber("Initial Turn Absolute Position",turnEncoder.getAbsolutePosition());
    SmartDashboard.putNumber("Initial Turn Distance", turnEncoder.getDistance());
  
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

    SmartDashboard.putNumber("Drive Velocity", driveEncoder.getVelocity());
    SmartDashboard.putNumber("Drive Position", driveEncoder.getPosition());

    SmartDashboard.putNumber("Current Turn Encoder Value", turnEncoder.get());
    SmartDashboard.putNumber("Current Turn Absolute Position",turnEncoder.getAbsolutePosition());
    SmartDashboard.putNumber("Current Turn Distance", turnEncoder.getDistance());


  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
