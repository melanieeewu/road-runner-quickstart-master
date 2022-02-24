package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import java.util.List;

@Autonomous
public class testEncoders extends LinearOpMode {
    private List<Double> motors;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            motors = drive.getWheelPositions();
            telemetry.addData("leftFront", motors.get(0));
            telemetry.addData("leftBack", motors.get(1));
            telemetry.addData("rightBack", motors.get(2));
            telemetry.addData("rightFront", motors.get(3));
            telemetry.update();



        }

    }
}
