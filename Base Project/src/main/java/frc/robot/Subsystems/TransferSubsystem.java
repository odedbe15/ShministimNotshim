package frc.robot.Subsystems;

import frc.robot.POM_lib.Motors.POMSparkMax;
import frc.robot.POM_lib.sensors.POMDigitalInput;

public class TransferSubsystem extends PomMotorSubsystem {
    private POMSparkMax motor;
    private POMDigitalInput isNoteIn;
    private static TransferSubsystem instance;

    private TransferSubsystem() {
        motor = new POMSparkMax(0);
        isNoteIn = new POMDigitalInput(0);

    }

    public static TransferSubsystem getInstance() {
        if (instance == null) {
            instance = new TransferSubsystem();
        }
        return instance;
    }

    @Override
    public void stopMotor() {
        motor.stop();
    }

    @Override
    public void setMotor(double percent) {
        motor.set(percent);
    }

    @Override
    public void setIdleMode(boolean brake) {
    }

    public boolean isNoteInTransfer() {
        return isNoteIn.get();
    }

}
