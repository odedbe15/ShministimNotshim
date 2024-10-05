package frc.robot.Subsystems;

import frc.robot.POM_lib.Motors.POMSparkMax;
import frc.robot.POM_lib.sensors.POMDigitalInput;

public class TransferSubsystem extends PomMotorSubsystem {
    private POMSparkMax motor;
    private POMDigitalInput noteIsIn;
    private TransferSubsystem instance;

    private TransferSubsystem() {
    }

    @Override
    public void stopMotor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stopMotor'");
    }

    @Override
    public void setMotor(double percent) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setMotor'");
    }

    @Override
    public void setIdleMode(boolean brake) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setIdleMode'");
    }

}
