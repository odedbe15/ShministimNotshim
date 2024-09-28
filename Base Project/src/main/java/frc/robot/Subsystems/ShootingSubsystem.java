package frc.robot.Subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;

import static frc.robot.Constants.ShootingConstants.*;

public class ShootingSubsystem extends PomMotorSubsystem{

    CANSparkMax leftMotor = new CANSparkMax(LEFT_MOTOR_ID, MotorType.kBrushless);
    CANSparkMax rightMotor = new CANSparkMax(RIGHT_MOTOR_ID, MotorType.kBrushless);


    PIDController controller = new PIDController(KP,KI,KD);
    @Override
    public void stopMotor() {
        leftMotor.set(0);
        rightMotor.set(0);
        
    }

    @Override
    public void setMotor(double percent) {
        leftMotor.set(percent);
        rightMotor.set(-percent);
    }

    @Override
    public void setIdleMode(boolean brake) {
        leftMotor.setIdleMode(brake ? IdleMode.kBrake : IdleMode.kCoast);
        rightMotor.setIdleMode(brake ? IdleMode.kBrake : IdleMode.kCoast);
    }


    
    public double getVelocity(){
        return (leftMotor.getEncoder().getVelocity() + rightMotor.getEncoder().getVelocity())/2;
    }


    public void shoot(double velocity){
        setMotor(controller.calculate(velocity));
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber( "Shooting Velocity",getVelocity());
    }
    
}
