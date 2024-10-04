package frc.robot.Subsystems;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.POM_lib.Motors.POMVictorSpx;

import static frc.robot.Constants.IntakeConstants.*;

public class IntakeLiftSubsystem extends PomMotorSubsystem {
    private POMVictorSpx motor;
    private ArmFeedforward ff;
    private ProfiledPIDController pidController;
    private AnalogPotentiometer encoder;

    private static IntakeLiftSubsystem instance;

    private IntakeLiftSubsystem() {
        motor = new POMVictorSpx(LIFT_MOTOR_ID);
        ff = new ArmFeedforward(KS, KG, KV);
        pidController = new ProfiledPIDController(KP, KI, KD, new Constraints(MAX_VEL, MAX_ACC));
        pidController.setTolerance(TOLERANCE);
        encoder = new AnalogPotentiometer(LIFT_ENCODER_ID, 2 * Math.PI, ENCODER_OFFSET);
    }

    public static IntakeLiftSubsystem getInstance() {
        if (instance == null) {
            instance = new IntakeLiftSubsystem();
        }
        return instance;
    }

    public boolean getAtGoal() {
        return pidController.atGoal();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("intake encoder", getEncoderPosition());
    }

    @Override
    public void stopMotor() {
        motor.stopMotor();
    }

    @Override
    public void setMotor(double percent) {
        motor.set(percent);
    }

    @Override
    public void setIdleMode(boolean brake) {
    }

    @Override
    public double getEncoderPosition() {
        return encoder.get();
    }

    public void goToPosition(double goalPosition) {
        double pidVal = pidController.calculate(getEncoderPosition(), goalPosition);
        motor.setVoltage(
                pidVal +
                        ff.calculate(getEncoderPosition(), pidController.getSetpoint().velocity));
    }

    public void setConstants(double kp, double ki, double kd, double maxVel, double maxAcc, double ks, double kg,
            double kv) {
        pidController.setPID(kp, ki, kd);
        pidController.setConstraints(new Constraints(maxVel, maxAcc));
        ff = new ArmFeedforward(ks, kg, kv);
    }

}
