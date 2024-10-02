package frc.robot.Subsystems;

import com.ctre.phoenix.sensors.WPI_PigeonIMU;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.POM_lib.Motors.POMSparkMax;

import static frc.robot.Constants.DriveConstants.*;

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

        //TODO Set Encoder Conversion Factor

        ResetEncoders();

        
        

        driveOdometry = new DifferentialDriveOdometry(yona.getRotation2d() , leftMaster.getEncoder().getPosition() ,rightMaster.getEncoder().getPosition());

        
    }



    @Override
    public void periodic() {
        driveOdometry.update(yona.getRotation2d(),leftMaster.getEncoder().getPosition(), rightMaster.getEncoder().getPosition());
    }


    public Pose2d getPose(){
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


    public void ResetEncoders(){
        rightMaster.getEncoder().setPosition(0);
        leftMaster.getEncoder().setPosition(0);
        rightSlave.getEncoder().setPosition(0);
        leftSlave.getEncoder().setPosition(0);
    }

    public double getAvgEncoderDistance(){
        return(rightMaster.getEncoder().getPosition() + leftMaster.getEncoder().getPosition() )/2;
    }

    public void resetGyro(){
        yona.reset();
    }


    public void arcadeDrive(double fwd, double rot){
        drive.arcadeDrive(fwd, rot);
    }

}
