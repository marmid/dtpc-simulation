//============================================================================
//                                 Airbus Amber
//                                 UNCLASSIFIED
//============================================================================
//
//  COPYRIGHT
//  This software is copyrighted. It is the property of Airbus Defence and
//  Space GmbH which reserves all right and title to it. It must not be
//  reproduced, copied, published or released to third parties nor may the
//  content be disclosed to third parties without the prior written consent of
//  Airbus Defence and Space GmbH. Offenders are liable to the payment of
//  damages. All rights reserved in the event of the granting of a patent or
//  the registration of a utility model or design.
//  (c) Airbus Defence and Space GmbH 2020
//  Additional restrictions apply. See license.txt supplied with this file.
//
//============================================================================

package main.java.simulation;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.objects.SensorPlatform;
import main.java.objects.Target;
import main.java.objects.World;
import main.java.types.ControlCommand;
import main.java.types.ControlCommandType;
import main.java.types.KinematicState;
import main.java.types.ObjectType;
import main.java.types.Position;
import main.java.types.SensorArea;

/**
 * Defines the class SimulationFramework.
 */
public class SimulationFramework {

  private static final int WORLD_WIDTH = 1000;

  private static final int WORLD_LENGTH = 3000;

  private static final int NUMBER_OF_TARGETS = 100;

  private static final int NUMBER_OF_SENSOR_PLATFORMS = 5;

  private static final int SENSOR_AREA_WIDTH = 550;

  private static final int SENSOR_AREA_LENGTH = 250;

  private static final int SEED = 1614267322;

  private static final int RANDOM_MINIMUM = 0;

  private static int szenarioNo = 2;

  private static Logger logger = LoggerFactory.getLogger( SimulationFramework.class );

  private Random random;

  private World world;

  private ArrayList< ConcurrentLinkedQueue< ControlCommand > > listOfCommandQueues;

  private ArrayList< ControlCommand > storyBook;

  private ArrayList< Thread > listOfThreads;

  public SimulationFramework() {
    this.random = new Random( SEED );
    this.listOfCommandQueues = new ArrayList< ConcurrentLinkedQueue< ControlCommand > >();
    this.listOfThreads = new ArrayList< Thread >();
  }

  public void init() {
    logger.trace( "SimulationFramework - Simulation Scenario: " + szenarioNo );
    createWorld( WORLD_WIDTH, WORLD_LENGTH );
    createTargets();
    createSensorPlatforms();
    startSensorPlatformThreads();
  }

  public void createWorld( int width, int length ) {
    this.world = new World( width, length );
    logger.trace( "New World created: " + this.world.toString() );
  }

  public void createTargets() {
    for( int i = 0; i < NUMBER_OF_TARGETS; i++ ) {
      Target target = new Target( this.world, getID(), createRandomPosition(), giveRandomTargetObjectType() );
      logger.trace( "New Target created: " + target.toString() );
      this.world.addTarget( target );
    }
  }

  public void createSensorPlatforms() {

    double xAxisOffset = this.world.getWidth() / (NUMBER_OF_SENSOR_PLATFORMS + 1);

    for( int i = 0; i < NUMBER_OF_SENSOR_PLATFORMS; i++ ) {
      int xOffset = (int)((i + 1) * xAxisOffset);
      ArrayList< Position > positions = new ArrayList<>();
      positions.add( new Position( xOffset, 200 ) );
      positions.add( new Position( xOffset, 400 ) );
      positions.add( new Position( xOffset, 600 ) );
      positions.add( new Position( xOffset, 800 ) );
      positions.add( new Position( xOffset, 1000 ) );
      positions.add( new Position( xOffset, 1200 ) );
      positions.add( new Position( xOffset, 1400 ) );
      positions.add( new Position( xOffset, 1600 ) );
      positions.add( new Position( xOffset, 1800 ) );
      positions.add( new Position( xOffset, 2000 ) );
      positions.add( new Position( xOffset, 2200 ) );
      positions.add( new Position( xOffset, 2400 ) );
      positions.add( new Position( xOffset, 2600 ) );
      positions.add( new Position( xOffset, 2800 ) );
      positions.add( new Position( xOffset, 3000 ) );

      ConcurrentLinkedQueue< ControlCommand > commandQueue = new ConcurrentLinkedQueue<>();
      this.listOfCommandQueues.add( commandQueue );
      SensorPlatform sensor = new SensorPlatform( this.world, getID(), new Position( xOffset, 0 ), ObjectType.SENSOR,
                                                  new SensorArea( SENSOR_AREA_WIDTH, SENSOR_AREA_LENGTH ), positions, commandQueue );
      this.world.addSensorPlatform( sensor );
      logger.trace( "New SensorPlatform created: " + sensor.toString() );

    }
  }

  public void startSensorPlatformThreads() {
    for( SensorPlatform sensor : this.world.getListOfSensorPlatforms() ) {
      Thread thread = new Thread( sensor, sensor.getId().toString() );
      logger.trace( "Thread " + thread.getName() + " started." );
      thread.start();
      this.listOfThreads.add( thread );
    }
  }

  public void startSimulation() {
    if( szenarioNo == 1 ) {
      for( SensorPlatform sensor : this.world.getListOfSensorPlatforms() ) {
        ConcurrentLinkedQueue< ControlCommand > commandQueue = sensor.getCommandQueue();
        for( SensorPlatform sensor2 : this.world.getListOfSensorPlatforms() ) {
          if( !sensor.equals( sensor2 ) ) {
            ControlCommand commandCrdt = new ControlCommand( sensor2, ControlCommandType.CONNECT );
            commandQueue.offer( commandCrdt );
          }
        }
        ControlCommand commandSensor = new ControlCommand( sensor, ControlCommandType.ACTIVATE_SENSOR );
        ControlCommand commandKinematic = new ControlCommand( sensor, ControlCommandType.START_MOVING );
        commandQueue.offer( commandSensor );
        commandQueue.offer( commandKinematic );
      }
    } else if( szenarioNo == 2 ) {
      for( SensorPlatform sensor : this.world.getListOfSensorPlatforms() ) {
        ConcurrentLinkedQueue< ControlCommand > commandQueue = sensor.getCommandQueue();
        for( SensorPlatform sensor2 : this.world.getListOfSensorPlatforms() ) {
          if( !sensor.equals( sensor2 ) ) {
            ControlCommand commandCrdt = new ControlCommand( sensor2, ControlCommandType.CONNECT );
            commandQueue.offer( commandCrdt );
          }
        }
        ControlCommand commandSensor = new ControlCommand( sensor, ControlCommandType.ACTIVATE_SENSOR );
        ControlCommand commandKinematic = new ControlCommand( sensor, ControlCommandType.START_MOVING );
        commandQueue.offer( commandSensor );
        commandQueue.offer( commandKinematic );
      }

      Thread thisThread = Thread.currentThread();
      try {
        thisThread.sleep( 6000 );
      } catch( InterruptedException e ) {
        logger.error( e.getStackTrace().toString() );
      }
      ArrayList< SensorPlatform > sensorList = this.world.getListOfSensorPlatforms();
      // SensorPlatform sensor4 = sensorList.get( sensorList.size()-2 );
      // ConcurrentLinkedQueue< ControlCommand > commandQueue4 =
      // sensor4.getCommandQueue();
      // SensorPlatform sensor5 = sensorList.get( sensorList.size()-1 );
      // ConcurrentLinkedQueue< ControlCommand > commandQueue5 =
      // sensor5.getCommandQueue();
      //
      // ControlCommand commandCrdt4 = new ControlCommand( sensor4,
      // ControlCommandType.DISCONNECT );
      // ControlCommand commandSensor4 = new ControlCommand( sensor4,
      // ControlCommandType.DEACTIVATE_SENSOR );
      // ControlCommand commandKinematic4 = new ControlCommand( sensor4,
      // ControlCommandType.STOP_MOVING );
      // commandQueue4.offer( commandCrdt4 );
      // commandQueue4.offer( commandSensor4 );
      // commandQueue4.offer( commandKinematic4 );
      //
      // ControlCommand commandCrdt5 = new ControlCommand( sensor5,
      // ControlCommandType.DISCONNECT );
      // ControlCommand commandSensor5 = new ControlCommand( sensor5,
      // ControlCommandType.DEACTIVATE_SENSOR );
      // ControlCommand commandKinematic5 = new ControlCommand( sensor5,
      // ControlCommandType.STOP_MOVING );
      // commandQueue5.offer( commandCrdt5 );
      // commandQueue5.offer( commandSensor5 );
      // commandQueue5.offer( commandKinematic5 );

      SensorPlatform sensor1 = sensorList.get( 0 );
      SensorPlatform sensor2 = sensorList.get( 1 );
      SensorPlatform sensor3 = sensorList.get( 2 );
      SensorPlatform sensor4 = sensorList.get( 3 );
      SensorPlatform sensor5 = sensorList.get( 4 );

      ConcurrentLinkedQueue< ControlCommand > commandQueue4 = sensor4.getCommandQueue();
      ConcurrentLinkedQueue< ControlCommand > commandQueue5 = sensor5.getCommandQueue();

      ControlCommand disconnectfrom1 = new ControlCommand( sensor1, ControlCommandType.DISCONNECT );
      ControlCommand disconnectfrom2 = new ControlCommand( sensor2, ControlCommandType.DISCONNECT );
      ControlCommand disconnectfrom3 = new ControlCommand( sensor3, ControlCommandType.DISCONNECT );
      ControlCommand disconnectfrom4 = new ControlCommand( sensor4, ControlCommandType.DISCONNECT );
      ControlCommand disconnectfrom5 = new ControlCommand( sensor5, ControlCommandType.DISCONNECT );

      ControlCommand commandSensor4 = new ControlCommand( sensor4, ControlCommandType.DEACTIVATE_SENSOR );
      ControlCommand commandKinematic4 = new ControlCommand( sensor4, ControlCommandType.STOP_MOVING );

      ControlCommand commandSensor5 = new ControlCommand( sensor5, ControlCommandType.DEACTIVATE_SENSOR );
      ControlCommand commandKinematic5 = new ControlCommand( sensor5, ControlCommandType.STOP_MOVING );
      
      commandQueue4.offer( disconnectfrom1 );
      commandQueue4.offer( disconnectfrom2 );
      commandQueue4.offer( disconnectfrom3 );
      commandQueue4.offer( disconnectfrom5 );
      commandQueue4.offer( commandSensor4 );
      commandQueue4.offer( commandKinematic4 );
      
      commandQueue5.offer( disconnectfrom1 );
      commandQueue5.offer( disconnectfrom2 );
      commandQueue5.offer( disconnectfrom3 );
      commandQueue5.offer( disconnectfrom4 );
      commandQueue5.offer( commandSensor5 );
      commandQueue5.offer( commandKinematic5 );
      

    } else if( szenarioNo == 3 ) {
      for( SensorPlatform sensor : this.world.getListOfSensorPlatforms() ) {
        ConcurrentLinkedQueue< ControlCommand > commandQueue = sensor.getCommandQueue();
        for( SensorPlatform sensor2 : this.world.getListOfSensorPlatforms() ) {
          if( !sensor.equals( sensor2 ) ) {
            ControlCommand commandCrdt = new ControlCommand( sensor2, ControlCommandType.CONNECT );
            commandQueue.offer( commandCrdt );
          }
        }
        ControlCommand commandSensor = new ControlCommand( sensor, ControlCommandType.ACTIVATE_SENSOR );
        ControlCommand commandKinematic = new ControlCommand( sensor, ControlCommandType.START_MOVING );
        commandQueue.offer( commandSensor );
        commandQueue.offer( commandKinematic );
      }

      Thread thisThread = Thread.currentThread();
      try {
        thisThread.sleep( 6000 );
      } catch( InterruptedException e ) {
        logger.error( e.getStackTrace().toString() );
      }
      
      ArrayList< SensorPlatform > sensorList = this.world.getListOfSensorPlatforms();
      
      SensorPlatform sensor1 = sensorList.get( 0 );
      SensorPlatform sensor2 = sensorList.get( 1 );
      SensorPlatform sensor3 = sensorList.get( 2 );
      SensorPlatform sensor4 = sensorList.get( 3 );
      SensorPlatform sensor5 = sensorList.get( 4 );

      ConcurrentLinkedQueue< ControlCommand > commandQueue4 = sensor4.getCommandQueue();
      ConcurrentLinkedQueue< ControlCommand > commandQueue5 = sensor5.getCommandQueue();

      ControlCommand disconnectfrom1 = new ControlCommand( sensor1, ControlCommandType.DISCONNECT );
      ControlCommand disconnectfrom2 = new ControlCommand( sensor2, ControlCommandType.DISCONNECT );
      ControlCommand disconnectfrom3 = new ControlCommand( sensor3, ControlCommandType.DISCONNECT );
      ControlCommand disconnectfrom4 = new ControlCommand( sensor4, ControlCommandType.DISCONNECT );
      ControlCommand disconnectfrom5 = new ControlCommand( sensor5, ControlCommandType.DISCONNECT );
      
      commandQueue4.offer( disconnectfrom1 );
      commandQueue4.offer( disconnectfrom2 );
      commandQueue4.offer( disconnectfrom3 );
      
      commandQueue5.offer( disconnectfrom1 );
      commandQueue5.offer( disconnectfrom2 );
      commandQueue5.offer( disconnectfrom3 );
      
      try {
        thisThread.sleep( 6000 );
      } catch( InterruptedException e ) {
        logger.error( e.getStackTrace().toString() );
      }
      
      ControlCommand connectto1 = new ControlCommand( sensor1, ControlCommandType.CONNECT );
      ControlCommand connectto2 = new ControlCommand( sensor2, ControlCommandType.CONNECT );
      ControlCommand connectto3 = new ControlCommand( sensor3, ControlCommandType.CONNECT );
      ControlCommand connectto4 = new ControlCommand( sensor4, ControlCommandType.CONNECT );
      ControlCommand connectto5 = new ControlCommand( sensor5, ControlCommandType.CONNECT );
      
      commandQueue4.offer( connectto1 );
      commandQueue4.offer( connectto2 );
      commandQueue4.offer( connectto3 );
      
      commandQueue5.offer( connectto1 );
      commandQueue5.offer( connectto2 );
      commandQueue5.offer( connectto3 );

    } else if( szenarioNo == 4 ) {
      
      ArrayList< SensorPlatform > sensorList = this.world.getListOfSensorPlatforms();
      
      SensorPlatform sensor1 = sensorList.get( 0 );
      SensorPlatform sensor2 = sensorList.get( 1 );
      SensorPlatform sensor3 = sensorList.get( 2 );
      SensorPlatform sensor4 = sensorList.get( 3 );
      SensorPlatform sensor5 = sensorList.get( 4 );
      
      ConcurrentLinkedQueue< ControlCommand > commandQueue1 = sensor1.getCommandQueue();
      ConcurrentLinkedQueue< ControlCommand > commandQueue2 = sensor2.getCommandQueue();
      ConcurrentLinkedQueue< ControlCommand > commandQueue3 = sensor3.getCommandQueue();
      ConcurrentLinkedQueue< ControlCommand > commandQueue4 = sensor4.getCommandQueue();
      ConcurrentLinkedQueue< ControlCommand > commandQueue5 = sensor5.getCommandQueue();
      
      ControlCommand disconnectfrom1 = new ControlCommand( sensor1, ControlCommandType.DISCONNECT );
      ControlCommand disconnectfrom2 = new ControlCommand( sensor2, ControlCommandType.DISCONNECT );
      ControlCommand disconnectfrom3 = new ControlCommand( sensor3, ControlCommandType.DISCONNECT );
      ControlCommand disconnectfrom4 = new ControlCommand( sensor4, ControlCommandType.DISCONNECT );
      ControlCommand disconnectfrom5 = new ControlCommand( sensor5, ControlCommandType.DISCONNECT );
      
      ControlCommand connectto1 = new ControlCommand( sensor1, ControlCommandType.CONNECT );
      ControlCommand connectto2 = new ControlCommand( sensor2, ControlCommandType.CONNECT );
      ControlCommand connectto3 = new ControlCommand( sensor3, ControlCommandType.CONNECT );
      ControlCommand connectto4 = new ControlCommand( sensor4, ControlCommandType.CONNECT );
      ControlCommand connectto5 = new ControlCommand( sensor5, ControlCommandType.CONNECT );
      
      commandQueue1.offer(connectto2);
      commandQueue1.offer(connectto4);
      commandQueue1.offer(connectto5);
      
      commandQueue2.offer(connectto4);
      commandQueue2.offer(connectto5);
      
      commandQueue4.offer(connectto5);      
      
      ControlCommand commandSensor1 = new ControlCommand( sensor1, ControlCommandType.ACTIVATE_SENSOR );
      ControlCommand commandKinematic1 = new ControlCommand( sensor1, ControlCommandType.START_MOVING );
      commandQueue1.offer( commandSensor1 );
      commandQueue1.offer( commandKinematic1 );
      
      ControlCommand commandSensor2 = new ControlCommand( sensor2, ControlCommandType.ACTIVATE_SENSOR );
      ControlCommand commandKinematic2 = new ControlCommand( sensor2, ControlCommandType.START_MOVING );
      commandQueue2.offer( commandSensor2 );
      commandQueue2.offer( commandKinematic2 );
      
      ControlCommand commandSensor3 = new ControlCommand( sensor3, ControlCommandType.DEACTIVATE_SENSOR );
      ControlCommand commandKinematic3 = new ControlCommand( sensor3, ControlCommandType.START_MOVING );
      commandQueue3.offer( commandSensor3 );
      commandQueue3.offer( commandKinematic3 );
      
      ControlCommand commandSensor4 = new ControlCommand( sensor4, ControlCommandType.ACTIVATE_SENSOR );
      ControlCommand commandKinematic4 = new ControlCommand( sensor4, ControlCommandType.START_MOVING );
      commandQueue4.offer( commandSensor4 );
      commandQueue4.offer( commandKinematic4 );
      
      ControlCommand commandSensor5 = new ControlCommand( sensor5, ControlCommandType.ACTIVATE_SENSOR );
      ControlCommand commandKinematic5 = new ControlCommand( sensor5, ControlCommandType.START_MOVING );
      commandQueue5.offer( commandSensor5 );
      commandQueue5.offer( commandKinematic5 );
      

      Thread thisThread = Thread.currentThread();
      try {
        thisThread.sleep( 4000 );
      } catch( InterruptedException e ) {
        logger.error( e.getStackTrace().toString() );
      }

      commandQueue4.offer( disconnectfrom1 );
      commandQueue4.offer( disconnectfrom2 );
      commandQueue4.offer( disconnectfrom3 );
      commandQueue4.offer( disconnectfrom5 );
      
      
      try {
        thisThread.sleep( 2000 );
      } catch( InterruptedException e ) {
        logger.error( e.getStackTrace().toString() );
      }
      
      commandSensor3 = new ControlCommand( sensor3, ControlCommandType.ACTIVATE_SENSOR );
      commandQueue3.offer( commandSensor3 );
      commandQueue3.offer( connectto1 );
      commandQueue3.offer( connectto2 );
      commandQueue3.offer( connectto5 );
      
      try {
        thisThread.sleep( 4000 );
      } catch( InterruptedException e ) {
        logger.error( e.getStackTrace().toString() );
      }
      

      commandQueue3.offer( disconnectfrom1 );
      commandQueue3.offer( disconnectfrom2 );
      commandQueue3.offer( disconnectfrom5 );
      commandSensor3 = new ControlCommand( sensor3, ControlCommandType.DEACTIVATE_SENSOR );
      commandKinematic3 = new ControlCommand( sensor3, ControlCommandType.STOP_MOVING );
      commandQueue3.offer( commandSensor3 );
      commandQueue3.offer( commandKinematic3 );
      
      try {
        thisThread.sleep( 2000 );
      } catch( InterruptedException e ) {
        logger.error( e.getStackTrace().toString() );
      }
      
      commandQueue4.offer( connectto1 );
      commandQueue4.offer( connectto2 );
      commandQueue4.offer( connectto5 );

    } else if( szenarioNo == 5 ) {

    } else if( szenarioNo == 6 ) {

    } else if( szenarioNo == 7 ) {

    } else if( szenarioNo == 8 ) {

    }

    boolean allSensorsFinished = false;
    while( allSensorsFinished == false ) {
      int finishedSensors = 0;

      for( SensorPlatform sensor : this.world.getListOfSensorPlatforms() ) {
        if( sensor.getCurrentKinematicState().equals( KinematicState.FINISHED ) ) {
          finishedSensors++;
        }
      }

      if( finishedSensors == this.world.getListOfSensorPlatforms().size() ) {

        for( SensorPlatform sensor : this.world.getListOfSensorPlatforms() ) {
          if( sensor.getCurrentKinematicState().equals( KinematicState.FINISHED ) ) {
            sensor.doStop();
          }
        }

        allSensorsFinished = true;
        logger.trace( "All Sensors finished! Shutting down." );
      }

    }

  }

  public void joinThreads() {
    
    try {
      Thread.currentThread().sleep( 2000 );
    } catch( InterruptedException e ) {
      logger.error( e.getStackTrace().toString() );
    }

    try {
      for( Thread t : this.listOfThreads ) {

        t.join();

      }
    } catch( InterruptedException e ) {
      logger.error( e.getStackTrace().toString() );
    } finally {
      // int detectedCounter = 0;
      // int undetectedCounter = 0;
      logger.info( "# of detected targets: " + this.world.getListOfFoundTargets().size() );
      logger.info( "# of targets total: " + this.world.getListOfTargets().size() );
      // for( Target target : this.world.getListOfFoundTargets() ) {
      // // logger.info( "Detected Target: " + target );
      // }
      // for( Target target : this.world.getListOfTargets() ) {
      // if( !this.world.getListOfFoundTargets().contains( target ) ) {
      // // logger.info( "Undetected Target: " + target );
      // } else {
      //
      // }
      // if( target.getHasBeenDetected() ) {
      // detectedCounter++;
      // } else {
      // undetectedCounter++;
      // logger.info( target.getPosition().toString() );
      // }
      // }
      // logger.info( "# detectedCounter: " + detectedCounter );
      // logger.info( "# undetectedCounter: " + undetectedCounter );

      // //Clear GSets
      // for(SensorPlatform sensor : this.world.getListOfSensorPlatforms()) {
      // sensor.getPlotsGset().removeAll( sensor.getPlots() );
      // logger.trace( sensor.getId() + " cleared GSet. # of Plots in Gset: " +
      // sensor.getPlotsGset().size() );
      // }

    }
  }

  public Position createRandomPosition() {
    int x = this.random.nextInt( (WORLD_WIDTH - RANDOM_MINIMUM) + 1 ) + RANDOM_MINIMUM;
    int y = this.random.nextInt( (WORLD_LENGTH - RANDOM_MINIMUM) + 1 ) + RANDOM_MINIMUM;
    return new Position( x, y );
  }

  public ObjectType giveRandomTargetObjectType() {
    return ObjectType.getRandomTargetObjectType( random );
  }

  public UUID getID() {
    byte[] array = new byte[16];
    this.random.nextBytes( array );
    return UUID.nameUUIDFromBytes( array );
  }

}
