package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.POM_lib.Motors.POMVictorSpx;

public class IntakeRollerSubsystem extends PomMotorSubsystem{
    private static IntakeRollerSubsystem instance;
    private POMVictorSpx leftVictor;
    private POMVictorSpx rightVictor;
    private IntakeRollerSubsystem(){
        leftVictor = new POMVictorSpx(0);
        rightVictor = new POMVictorSpx(0);
        leftVictor.follow(rightVictor);
    }
    public static IntakeRollerSubsystem getInstance(){
        if(instance == null){
            instance = new IntakeRollerSubsystem();
        }
        return instance;
    }
    @Override
    public void stopMotor() {
        rightVictor.set(0);
    }
    @Override
    public void setMotor(double percent) {
        rightVictor.set(percent);
    }
    @Override
    public void setIdleMode(boolean brake) {
       leftVictor.setBrake(false);
       rightVictor.setBrake(false);
    }

}
