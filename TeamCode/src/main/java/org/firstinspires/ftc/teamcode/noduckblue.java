package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous
public class noduckblue extends LinearOpMode {
    public Servo intakegate; //open close
    public DcMotor bottomIntake; //bottom
    public DcMotor pulley; //topintake-
    public  DcMotor topwheel; //greenwheel
    public  DcMotor intakeExtension;

    @Override
    public void runOpMode() throws InterruptedException {
        bottomIntake = hardwareMap.dcMotor.get("bottomIntake");
        pulley = hardwareMap.dcMotor.get("pulley");
        intakegate = hardwareMap.servo.get("intakegate");
        intakeExtension = hardwareMap.dcMotor.get("intakeExtension");
        topwheel = hardwareMap.dcMotor.get("topwheel");

        Pose2d startPose = new Pose2d(7.9, 72.2, Math.toRadians(90));
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        waitForStart();
        drive.setPoseEstimate(startPose);
        drive.followTrajectorySequence(drive.trajectorySequenceBuilder(startPose)

                    /*    .lineTo(new Vector2d(-9.6, 39.5))
                        .waitSeconds(1)
                        .lineToLinearHeading(new Pose2d(-8.2, 64.6, Math.toRadians(0)))
                        //   .waitSeconds(1)
                        .forward(50)
*/

                        .lineTo(new Vector2d(-9.6, 49))
                        .waitSeconds(1)

                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            pulley.setPower(1);
                            intakegate.setPosition(1);
                        })
                        .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                            pulley.setPower(0);
                            intakegate.setPosition(0);
                        })
                .waitSeconds(3)

                        .lineToLinearHeading(new Pose2d(-8.2, 75, Math.toRadians(0)))
                .strafeLeft(0.5)
                        //   .waitSeconds(1)
                        .forward(50)
                                .build()
                );

    }
}
