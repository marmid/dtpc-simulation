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

package main.java.objects;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.simulation.SimulationFramework;
import main.java.types.ControlCommand;
import main.java.types.KinematicState;
import main.java.types.ObjectType;
import main.java.types.Plot;
import main.java.types.Position;
import main.java.types.SensorArea;

/**
 * Defines the class SensorPlatform.
 */
public class SensorPlatform extends WorldObject implements Runnable {
  
  private static Logger logger = LoggerFactory.getLogger(SensorPlatform.class);

  private SensorArea sensorArea;

  private ArrayList< Position > positions;

  private ArrayList< Position > visitedPositions;

  private ArrayList< Plot > plots;

  private ConcurrentLinkedQueue< ControlCommand > commandQueue;

  private KinematicState currentKinematicState = KinematicState.ON_GROUND;

  private boolean doStop = false;

  /**
   * @param world
   * @param id
   * @param position
   * @param type
   * @param sensorArea
   * @param positions
   * @param plots
   * @param commandQueue
   */
  public SensorPlatform( World world, UUID id, Position position, ObjectType type, SensorArea sensorArea, ArrayList< Position > positions,
      ConcurrentLinkedQueue< ControlCommand > commandQueue ) {
    super( world, id, position, type );
    this.sensorArea = sensorArea;
    this.positions = positions;
    this.commandQueue = commandQueue;
    this.plots = new ArrayList< Plot >();
    this.visitedPositions = new ArrayList< Position >();
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub
    Thread thread = Thread.currentThread();
    logger.info( "Thread " + thread.getName() + " run method." );
    this.currentKinematicState = KinematicState.STARTED;
    while( keepRunning() ) {
      if( this.commandQueue.peek() != null  ) {
        ControlCommand command = commandQueue.poll();
        if( command.getCommandTarget().equals( this ) ) {
          switch( command.getCommandType() ) {
          case START_MOVING:
            if( currentKinematicState.equals( KinematicState.STARTED ) ) {
              this.currentKinematicState = KinematicState.MOVING;
              logger.info( "Sensor Platform " + this.getId() + " starts moving." );
            }
            break;
          case STOP_MOVING:
            if( currentKinematicState.equals( KinematicState.MOVING ) || 
                currentKinematicState.equals( KinematicState.STARTED )
                || currentKinematicState.equals( KinematicState.PAUSED ) ) {
              this.currentKinematicState = KinematicState.FINISHED;
              logger.info( "Sensor Platform " + this.getId() + " stops moving." );
            }
            break;
          case PAUSE_MOVING:
            if( currentKinematicState.equals( KinematicState.STARTED ) || 
                currentKinematicState.equals( KinematicState.MOVING ) ) {
              this.currentKinematicState = KinematicState.PAUSED;
              logger.info( "Sensor Platform " + this.getId() + " pauses moving." );
            }
            break;
          case RESUME_MOVING:
            if( currentKinematicState.equals( KinematicState.PAUSED ) ) {
              this.currentKinematicState = KinematicState.MOVING;
              logger.info( "Sensor Platform " + this.getId() + " resumes moving." );
            }
            break;
          case CONNECT:
            connect();
            break;
          case DISCONNECT:
            disconnect();
            break;
          default:
            break;
          }
        }
      }

      if( currentKinematicState.equals( KinematicState.MOVING ) ) {
        updatePosition();
        sense();
        try {
          Thread.sleep( 2000 );
        } catch( InterruptedException e ) {
          e.printStackTrace();
        }
      }

    }

  }

  public void sense() {
    ArrayList< Target > worldTargets = this.world.getListOfTargets();
    int xLowerBound = this.getPosition().getX() - this.getSensorArea().getWidth() / 2;
    int xUpperBound = this.getPosition().getX() + this.getSensorArea().getWidth() / 2;
    int yLowerBound = this.getPosition().getY() - this.getSensorArea().getLength() / 2;
    int yUpperBound = this.getPosition().getY() + this.getSensorArea().getLength() / 2;
    for( Target target : worldTargets ) {
      int targetX = target.getPosition().getX();
      int targetY = target.getPosition().getY();
      if( targetX >= xLowerBound && targetX <= xUpperBound && 
          targetY >= yLowerBound && targetY <= yUpperBound ) {
        Plot plot = new Plot( UUID.randomUUID(), 
                              Timestamp.valueOf( LocalDateTime.now() ), 
                              this.getId(), 
                              this.getPosition(), 
                              this.getType(),
                              target.getId(), 
                              target.getPosition(), 
                              target.getType() );
        this.plots.add( plot );
        logger.info( "Sensor Platform " + this.getId() + ": new Plot: " + plot );

      }
    }
  }

  public void updatePosition() {
    if( !this.positions.isEmpty() ) {
      this.visitedPositions.add( this.position );
      Position nextPosition = this.positions.get( 0 );
      this.setPosition( nextPosition );
      this.positions.remove( nextPosition );
      logger.info( "Sensor Platform " + this.getId() + ": new Position: " + this.getPosition() );
    } else {
      this.currentKinematicState = KinematicState.FINISHED;
      logger.info( "Sensor Platform " + this.getId() + " finished." );
      doStop();
    }
  }

  public void connect() {
    logger.info( "Sensor Platform " + this.getId() + " connect." );

  }

  public void disconnect() {
    logger.info( "Sensor Platform " + this.getId() + " disconnect." );

  }

  public synchronized void doStop() {
    this.doStop = true;
  }

  public synchronized boolean keepRunning() {
    return this.doStop == false;
  }

  /**
   * Returns the plots of this SensorPlatform.
   * 
   * @return the plots of this SensorPlatform.
   */
  public ArrayList< Plot > getPlots() {
    return plots;
  }

  /**
   * Sets the plots of this SensorPlatform.
   * 
   * @param plots the plots to set.
   */
  public void setPlots( ArrayList< Plot > plots ) {
    this.plots = plots;
  }

  /**
   * Returns the positions of this SensorPlatform.
   * 
   * @return the positions of this SensorPlatform.
   */
  public ArrayList< Position > getPositions() {
    return positions;
  }

  /**
   * Sets the positions of this SensorPlatform.
   * 
   * @param positions the positions to set.
   */
  public void setPositions( ArrayList< Position > positions ) {
    this.positions = positions;
  }

  /**
   * Returns the sensorArea of this SensorPlatform.
   * 
   * @return the sensorArea of this SensorPlatform.
   */
  public SensorArea getSensorArea() {
    return sensorArea;
  }

  /**
   * Sets the sensorArea of this SensorPlatform.
   * 
   * @param sensorArea the sensorArea to set.
   */
  public void setSensorArea( SensorArea sensorArea ) {
    this.sensorArea = sensorArea;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Objects.hash( plots, positions, sensorArea );
    return result;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals( Object obj ) {
    if( this == obj )
      return true;
    if( !super.equals( obj ) )
      return false;
    if( getClass() != obj.getClass() )
      return false;
    SensorPlatform other = (SensorPlatform)obj;
    return Objects.equals( plots, other.plots ) && Objects.equals( positions, other.positions ) && Objects.equals( sensorArea, other.sensorArea );
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "SensorPlatform [" + "id=" + id + ", position=" + position + ", type=" + type + ", sensorArea=" + sensorArea + ", positions=" + positions
           + ", plots=" + plots + ", commandQueue=" + commandQueue + "]";
  }

  /**
   * Returns the commandQueue of this SensorPlatform.
   * 
   * @return the commandQueue of this SensorPlatform.
   */
  public synchronized ConcurrentLinkedQueue< ControlCommand > getCommandQueue() {
    return commandQueue;
  }

  /**
   * Sets the commandQueue of this SensorPlatform.
   * 
   * @param commandQueue the commandQueue to set.
   */
  public synchronized void setCommandQueue( ConcurrentLinkedQueue< ControlCommand > commandQueue ) {
    this.commandQueue = commandQueue;
  }

  /**
   * Returns the currentKinematicState of this SensorPlatform.
   * 
   * @return the currentKinematicState of this SensorPlatform.
   */
  public KinematicState getCurrentKinematicState() {
    return currentKinematicState;
  }

  /**
   * Sets the currentKinematicState of this SensorPlatform.
   * 
   * @param currentKinematicState the currentKinematicState to set.
   */
  public void setCurrentKinematicState( KinematicState currentKinematicState ) {
    this.currentKinematicState = currentKinematicState;
  }

}