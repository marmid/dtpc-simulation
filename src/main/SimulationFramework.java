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

import objects.Target;
import objects.World;
import objects.WorldObject;
import types.ControlCommand;
import types.ObjectType;
import types.Position;

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

  private ArrayList< ConcurrentLinkedQueue > listOfCommandQueues;

  private ArrayList< ControlCommand > storyBook;
  
  public SimulationFramework() {
    
  }

  public void init() {
    random = new Random( SEED );
    createWorld( WORLD_WIDTH, WORLD_LENGTH );
    createTargets();
    createSensorPlatforms();
  }

  public void createWorld( int width, int length ) {
    this.world = new World( width, length );
    System.out.println( this.world.toString() );
  }

  public void createTargets() {
    for( int i = 0; i < NUMBER_OF_TARGETS; i++ ) {
      Target target = new Target( this.world, getID(), createRandomPosition(), giveRandomTargetObjectType() );
      System.out.println( target.toString() );
      this.world.addTarget( target );
      
    }
  }

  public void createSensorPlatforms() {

  }

  public void startSensorPlatforms() {

  }

  public Position createRandomPosition() {
    int x = random.nextInt( (WORLD_WIDTH - RANDOM_MINIMUM) + 1 ) + RANDOM_MINIMUM;
    int y = random.nextInt( (WORLD_LENGTH - RANDOM_MINIMUM) + 1 ) + RANDOM_MINIMUM;
    return new Position( x, y );
  }

  public ObjectType giveRandomTargetObjectType() {
    return ObjectType.getRandomTargetObjectType( random );
  }

  public UUID getID() {
    byte[] array = new byte[16];
    random.nextBytes( array );
    return UUID.nameUUIDFromBytes( array );
  }

}
