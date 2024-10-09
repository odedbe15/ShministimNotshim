package frc.robot.Subsystems;

import static frc.robot.Constants.DriveConstants.LEFT_MASTER_ID;
import static frc.robot.Constants.DriveConstants.LEFT_SLAVE_ID;
import static frc.robot.Constants.DriveConstants.PIGEON_ID;
import static frc.robot.Constants.DriveConstants.RIGHT_MASTER_ID;
import static frc.robot.Constants.DriveConstants.RIGHT_SLAVE_ID;

import com.ctre.phoenix.sensors.WPI_PigeonIMU;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import frc.robot.POM_lib.Motors.POMSparkMax;
import static frc.robot.Constants.GeneralConstants.*;
import static frc.robot.Constants.DriveConstants.*;
import org.opencv.core.Mat;

public class DriveSubsystem extends PomMotorSubsystem {

    POMSparkMax rightMaster = new POMSparkMax(RIGHT_MASTER_ID, MotorType.kBrushless);
    POMSparkMax rightSlave = new POMSparkMax(RIGHT_SLAVE_ID, MotorType.kBrushless);
    POMSparkMax leftMaster = new POMSparkMax(LEFT_MASTER_ID, MotorType.kBrushless);
    POMSparkMax leftSlave = new POMSparkMax(LEFT_SLAVE_ID, MotorType.kBrushless);

    WPI_PigeonIMU yona = new WPI_PigeonIMU(PIGEON_ID);

    DifferentialDrive drive = new DifferentialDrive(leftMaster, rightMaster);
    DifferentialDriveOdometry driveOdometry;

    public DriveSubsystem() {
        rightSlave.follow(rightMaster);
        leftSlave.follow(leftMaster);
        leftMaster.setInverted(true);

        // TODO Set Encoder Conversion Factor

        ResetEncoders();

        driveOdometry = new DifferentialDriveOdometry(yona.getRotation2d(), leftMaster.getEncoder().getPosition(),
                rightMaster.getEncoder().getPosition());

        rightMaster.getPIDController().setP(RP);
        rightMaster.getPIDController().setI(RI);
        rightMaster.getPIDController().setP(RD);

        leftMaster.getPIDController().setP(LP);
        leftMaster.getPIDController().setI(LI);
        leftMaster.getPIDController().setP(LD);

    }

    @Override
    public void periodic() {
        driveOdometry.update(yona.getRotation2d(), leftMaster.getEncoder().getPosition(),
                rightMaster.getEncoder().getPosition());
    }

    public Pose2d getPose() {
        return driveOdometry.getPoseMeters();
    }

    @Override
    public void stopMotor() {
        rightMaster.stop();
        leftMaster.stop();

    }

    @Override
    public void setMotor(double percent) {
        rightMaster.set(percent);
        leftMaster.set(percent);

    }

    @Override
    public void setIdleMode(boolean brake) {
        rightMaster.setBrake(brake);
        rightSlave.setBrake(brake);
        leftMaster.setBrake(brake);
        leftSlave.setBrake(brake);

    }

    public WheelSpeeds WheelSpeedSeter(double xSpeed, double zRotation, boolean squareInputs) {

        xSpeed = MathUtil.clamp(xSpeed, -1.0, 1.0);
        zRotation = MathUtil.clamp(zRotation, -1.0, 1.0);

        if (squareInputs) {
            xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);
            zRotation = Math.copySign(zRotation * zRotation, zRotation);
        }

        double leftSpeed = xSpeed - zRotation;
        double rightSpeed = xSpeed + zRotation;

        double greaterInput = Math.max(Math.abs(xSpeed), Math.abs(zRotation));
        double lesserInput = Math.min(Math.abs(xSpeed), Math.abs(zRotation));

        if (greaterInput == 0.0) {
            return new WheelSpeeds(0.0, 0.0);

        }
        double saturatedInput = (greaterInput + lesserInput) / greaterInput;
        leftSpeed /= saturatedInput;
        rightSpeed /= saturatedInput;

        return new WheelSpeeds(leftSpeed, rightSpeed);

    }

    public void drivVelocity(double xSpeed, double zRotation, boolean squareInputs) {
        SimpleMotorFeedforward rightFF = new SimpleMotorFeedforward(RKS, RKV, RKA);
        SimpleMotorFeedforward leftFF = new SimpleMotorFeedforward(LKS, LKV, LKA);
        xSpeed = MathUtil.applyDeadband(xSpeed, DEFAULT_INPUT_DEADBAND);
        zRotation = MathUtil.applyDeadband(zRotation, DEFAULT_INPUT_DEADBAND);

        var speeds = WheelSpeedSeter(xSpeed, zRotation, squareInputs);

        leftMaster.getPIDController().setReference(speeds.left, ControlType.kVelocity, 0,
                leftFF.calculate(speeds.left));
        rightMaster.getPIDController().setReference(speeds.right, ControlType.kVelocity, 0,
                rightFF.calculate(speeds.right));

    }

    public void driveDistance(double distance) {

        SimpleMotorFeedforward rightFF = new SimpleMotorFeedforward(RKS, RKV, RKA);
        SimpleMotorFeedforward leftFF = new SimpleMotorFeedforward(LKS, LKV, LKA);
        distance = getAvgEncoderDistance() + distance;

        // TODO ask uri about FF for driveDistance
        leftMaster.getPIDController().setReference(distance, ControlType.kPosition, 0,
                leftFF.calculate(leftMaster.getEncoder().getPosition()));
        rightMaster.getPIDController().setReference(distance, ControlType.kPosition, 0,
                rightFF.calculate(rightMaster.getEncoder().getPosition()));

    }

    public void ResetEncoders() {
        rightMaster.getEncoder().setPosition(0);
        leftMaster.getEncoder().setPosition(0);
        rightSlave.getEncoder().setPosition(0);
        leftSlave.getEncoder().setPosition(0);
    }

    public double getAvgEncoderDistance() {
        return (rightMaster.getEncoder().getPosition() + leftMaster.getEncoder().getPosition()) / 2;
    }

    public void resetGyro() {
        yona.reset();
    }

    public void arcadeDrive(double fwd, double rot) {
        drive.arcadeDrive(fwd, rot);
    }

}
