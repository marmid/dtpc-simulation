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

package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import objects.SensorPlatform;
import objects.Target;
import objects.World;
import types.ControlCommand;
import types.ControlCommandType;
import types.ObjectType;
import types.Position;
import types.SensorArea;

/**
 * Defines the class SimulationFramework.
 */
public class SimulationFramework {

  private static final int WORLD_WIDTH = 1000;

  private static final int WORLD_LENGTH = 3000;

  private static final int NUMBER_OF_TARGETS = 100;

  private static final int NUMBER_OF_SENSOR_PLATFORMS = 3;

  private static final int SENSOR_AREA_WIDTH = 400;

  private static final int SENSOR_AREA_LENGTH = 150;

  private static final int SEED = 1614267322;

  private static final int RANDOM_MINIMUM = 0;

  private Random random;

  private World world;

  private ArrayList< ConcurrentLinkedQueue<ControlCommand> > listOfCommandQueues;

  private ArrayList< ControlCommand > storyBook;
  
  private ArrayList< Thread > listOfThreads;

  public SimulationFramework() {

  }

  public void init() {
    this.random = new Random( SEED );
    this.listOfCommandQueues = new ArrayList<ConcurrentLinkedQueue< ControlCommand >>();
    this.listOfThreads = new ArrayList<Thread>();
    createWorld( WORLD_WIDTH, WORLD_LENGTH );
    createTargets();
    createSensorPlatforms();
    startSensorPlatformThreads();
  }

  public void createWorld( int width, int length ) {
    this.world = new World( width, length );
    System.out.println( this.world.toString() );
  }

  public void createTargets() {
    for( int i = 0; i < NUMBER_OF_TARGETS; i++ ) {
      Target target = new Target( this.world, getID(), 
                                  createRandomPosition(), 
                                  giveRandomTargetObjectType() );
      System.out.println( target.toString() );
      this.world.addTarget( target );

    }
  }

  public void createSensorPlatforms() {

    /*
     * create first sensor platform
     */
    ArrayList<Position> positions1 = new ArrayList<>();
    positions1.add( new Position(250, 200) );
    positions1.add( new Position(250, 400) );
    positions1.add( new Position(250, 600) );
    positions1.add( new Position(250, 800) );
    positions1.add( new Position(250, 1000) );
    positions1.add( new Position(250, 1200) );
    positions1.add( new Position(250, 1400) );
    positions1.add( new Position(250, 1600) );
    positions1.add( new Position(250, 1800) );
    positions1.add( new Position(250, 2000) );
    positions1.add( new Position(250, 2200) );
    positions1.add( new Position(250, 2400) );
    positions1.add( new Position(250, 2600) );
    positions1.add( new Position(250, 2800) );
    positions1.add( new Position(250, 3000) );
    
    ConcurrentLinkedQueue< ControlCommand > commandQueue1 = new ConcurrentLinkedQueue<>();
    this.listOfCommandQueues.add( commandQueue1 );
    SensorPlatform sensor1 = new SensorPlatform( this.world, 
                                                 getID(), 
                                                 new Position( 250, 0 ), 
                                                 ObjectType.SENSOR,
                                                 new SensorArea( SENSOR_AREA_WIDTH, SENSOR_AREA_LENGTH ), 
                                                 positions1, 
                                                 commandQueue1 );
    this.world.addSensorPlatform( sensor1 );
    System.out.println( sensor1.toString() );
    
    /*
     * create second sensor platform
     */
    ArrayList<Position> positions2 = new ArrayList<>();
    positions2.add( new Position(500, 200) );
    positions2.add( new Position(500, 400) );
    positions2.add( new Position(500, 600) );
    positions2.add( new Position(500, 800) );
    positions2.add( new Position(500, 1000) );
    positions2.add( new Position(500, 1200) );
    positions2.add( new Position(500, 1400) );
    positions2.add( new Position(500, 1600) );
    positions2.add( new Position(500, 1800) );
    positions2.add( new Position(500, 2000) );
    positions2.add( new Position(500, 2200) );
    positions2.add( new Position(500, 2400) );
    positions2.add( new Position(500, 2600) );
    positions2.add( new Position(500, 2800) );
    positions2.add( new Position(500, 3000) );
    
    ConcurrentLinkedQueue< ControlCommand > commandQueue2 = new ConcurrentLinkedQueue<>();
    this.listOfCommandQueues.add( commandQueue2 );
    SensorPlatform sensor2 = new SensorPlatform( this.world, 
                                                 getID(), 
                                                 new Position( 500, 0 ), 
                                                 ObjectType.SENSOR,
                                                 new SensorArea( SENSOR_AREA_WIDTH, SENSOR_AREA_LENGTH ), 
                                                 positions2, 
                                                 commandQueue2 );
    this.world.addSensorPlatform( sensor2 );
    System.out.println( sensor2.toString() );

    
    /*
     * create third sensor platform
     */
    ArrayList<Position> positions3 = new ArrayList<>();
    positions3.add( new Position(750, 200) );
    positions3.add( new Position(750, 400) );
    positions3.add( new Position(750, 600) );
    positions3.add( new Position(750, 800) );
    positions3.add( new Position(750, 1000) );
    positions3.add( new Position(750, 1200) );
    positions3.add( new Position(750, 1400) );
    positions3.add( new Position(750, 1600) );
    positions3.add( new Position(750, 1800) );
    positions3.add( new Position(750, 2000) );
    positions3.add( new Position(750, 2200) );
    positions3.add( new Position(750, 2400) );
    positions3.add( new Position(750, 2600) );
    positions3.add( new Position(750, 2800) );
    positions3.add( new Position(750, 3000) );
    
    ConcurrentLinkedQueue< ControlCommand > commandQueue3 = new ConcurrentLinkedQueue<>();
    this.listOfCommandQueues.add( commandQueue3 );
    SensorPlatform sensor3 = new SensorPlatform( this.world, 
                                                 getID(), 
                                                 new Position( 750, 0 ), 
                                                 ObjectType.SENSOR,
                                                 new SensorArea( SENSOR_AREA_WIDTH, SENSOR_AREA_LENGTH ), 
                                                 positions3, 
                                                 commandQueue3 );
    this.world.addSensorPlatform( sensor3 );
    System.out.println( sensor3.toString() );

  }

  public void startSensorPlatformThreads() {
    for(SensorPlatform sensor : this.world.getListOfSensorPlatforms()) {
      Thread thread = new Thread(sensor, sensor.getId().toString());
      System.out.println( "Thread " + thread.getName() + " started." );
      thread.start();
      this.listOfThreads.add( thread );
    }
  }

  public void startSimulation() {
    for(SensorPlatform sensor : this.world.getListOfSensorPlatforms()) {
      ControlCommand command = new ControlCommand( sensor, ControlCommandType.START_MOVING);
      ConcurrentLinkedQueue< ControlCommand > commandQueue = sensor.getCommandQueue();
      commandQueue.offer( command );
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
