package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.IntakeConstants;
import frc.robot.POM_lib.Dashboard.DashboardNumber;
import frc.robot.Subsystems.IntakeLiftSubsystem;

public class OpenCloseIntakeCommand extends Command {
    private IntakeLiftSubsystem intakeLiftSubsystem;
    private DashboardNumber kp, ki, kd, maxVel, maxAcc, ks, kg, kv;
    private double goal;

    public OpenCloseIntakeCommand(IntakeLiftSubsystem intakeLiftSubsystem, boolean toOpen) {
        this.intakeLiftSubsystem = intakeLiftSubsystem;
        addRequirements(intakeLiftSubsystem);

        this.goal = toOpen ? IntakeConstants.OPENED_POSITION : IntakeConstants.CLOSED_POSITION;

        kp = new DashboardNumber("intake kp");
        ki = new DashboardNumber("intake ki");
        kd = new DashboardNumber("intake kd");
        maxVel = new DashboardNumber("intake max vel");
        maxAcc = new DashboardNumber("intake max acc");
        ks = new DashboardNumber("intake ks");
        kg = new DashboardNumber("intake kg");
        kv = new DashboardNumber("intake kv");

    }

    @Override
    public void initialize() {
        intakeLiftSubsystem.setConstants(kp.get(), ki.get(), kd.get(), maxVel.get(), maxAcc.get(), ks.get(), kg.get(),
                kv.get());
    }

    @Override
    public void execute() {
        intakeLiftSubsystem.goToPosition(goal);
    }

    @Override
    public void end(boolean interrupted) {
        intakeLiftSubsystem.stopMotor();
    }

    @Override
    public boolean isFinished() {
        return intakeLiftSubsystem.getAtGoal();
    }

}
