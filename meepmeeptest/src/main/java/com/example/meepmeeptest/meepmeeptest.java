package com.example.meepmeeptest;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.SampleMecanumDrive;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class meepmeeptest {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(500);
        Pose2d startPose = new Pose2d(7.9, -72.2, Math.toRadians(90));

        //THIS IS RED WITHOUT A DUCK
        RoadRunnerBotEntity myfirstBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(30, 30, Math.toRadians(180), Math.toRadians(-180), 15)
                .setDimensions(13.4252,14.4094)

                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(startPose)

                                .lineTo(new Vector2d(-9.6, -39.5))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(8.2, -64.6, Math.toRadians(0)))
                             //   .waitSeconds(1)
                                .forward(35)

                                .build()

                );
//THIS IS RED WITH A DUCK
      RoadRunnerBotEntity mySecondBot = new DefaultBotBuilder(meepMeep)

                // We set this bot to be red
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(30, 30, Math.toRadians(180), Math.toRadians(-180), 15)
                .setDimensions(13.4252,14.4094)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-40, -69, Math.toRadians(180)))
                                .strafeRight(8)
                                .forward(25)
                                .waitSeconds(1)
                                .back(2)
                              //  .waitSeconds(1)

                               .turn(Math.toRadians(-90))

                               // .lineTo(new Vector2d(-46.5,-22.3))
                                .splineToConstantHeading(new Vector2d(-46.5, -22.3), Math.toRadians(90))

                                .turn(Math.toRadians(-90))
                              //  .waitSeconds(1)
                                .forward(15)
                                .waitSeconds(1)
                                .back(30)
                                .waitSeconds(1)
                               .strafeRight(13)
                                .build()
                );



        //THIS IS BLUE WITH A DUCK
        RoadRunnerBotEntity blueone = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(30, 30, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(13.4252,16.4094)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-40, 69, Math.toRadians(-180)))
                                .strafeLeft(8)
                                .forward(20)
                                .waitSeconds(1)
                                .turn(Math.toRadians(-90))
                                .back(33)
                                .turn(Math.toRadians(90))
                              //  .splineTo(new Vector2d(-46.5, +22.3), Math.toRadians(0))
                                .waitSeconds(1)
                               .back(30)
                                .waitSeconds(1)
                                .forward(30)
                                .strafeRight(5)

                                .build()
                );


        RoadRunnerBotEntity blue2 = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(30, 30, Math.toRadians(180), Math.toRadians(180), 15)
             //   .setDimensions(13.4252,14.4094)
                .setDimensions(14,16)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(7.9, 72.2, Math.toRadians(-90)))
                               .lineTo(new Vector2d(-9.6, 39.5))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(-8.2, 64.6, Math.toRadians(0)))
                                //   .waitSeconds(1)
                                .forward(50)
                                .build()
                );




        meepMeep.setBackground(MeepMeep.Background.FIELD_FREIGHTFRENZY_ADI_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myfirstBot)
               .addEntity(blueone)
               .addEntity(mySecondBot)
              .addEntity(blue2)

                .start();
    }
}