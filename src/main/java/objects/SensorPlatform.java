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

import com.netopyr.wurmloch.crdt.GSet;
import com.netopyr.wurmloch.store.CrdtStore;
import com.netopyr.wurmloch.store.LocalCrdtStore;

import main.java.simulation.SimulationFramework;
import main.java.types.ControlCommand;
import main.java.types.KinematicState;
import main.java.types.ObjectType;
import main.java.types.Plot;
import main.java.types.Position;
import main.java.types.SensorArea;
import main.java.types.SensorState;

/**
 * Defines the class SensorPlatform.
 */
public class SensorPlatform extends WorldObject implements Runnable {
  
  private static Logger logger = LoggerFactory.getLogger(SensorPlatform.class);

  private SensorArea sensorArea;

  private ArrayList< Position > positions;

  private ArrayList< Position > visitedPositions;

  private ArrayList< Plot > plots;
  
  private LocalCrdtStore crdtStore;
  
  private GSet<Plot> plotsGset;

  private ConcurrentLinkedQueue< ControlCommand > commandQueue;

  private KinematicState currentKinematicState = KinematicState.ON_GROUND;
  
  private SensorState currentSensorState = SensorState.OFF;

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
    this.crdtStore = new LocalCrdtStore();
    
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub
    Thread thread = Thread.currentThread();
    logger.trace( "Thread " + thread.getName() + " run method." );
    setCurrentKinematicState( KinematicState.STARTED );
    
    for(SensorPlatform sensor : this.world.getListOfSensorPlatforms()) {
      this.crdtStore.connect( sensor.getCrdtStore() );
      logger.trace( "Connected " + this.crdtStore + " with " + sensor.getCrdtStore() );
    }
    if(this.crdtStore.<Plot>findGSet( "Plots" ).isDefined()) {
      this.plotsGset = this.crdtStore.<Plot>findGSet( "Plots" ).get();
      logger.trace( "GSet found: "  + this.plotsGset );
    } else {
      this.plotsGset = this.crdtStore.createGSet("Plots");
      logger.trace( "new GSet generated: " + this.plotsGset );
    }
    
    
    while( keepRunning() ) {
      if( this.commandQueue.peek() != null  ) {
        ControlCommand command = commandQueue.poll();
        if( command.getCommandTarget().equals( this ) ) {
          switch( command.getCommandType() ) {
          case START_MOVING:
            if( getCurrentKinematicState().equals( KinematicState.STARTED ) ) {
              setCurrentKinematicState( KinematicState.MOVING );
              logger.trace( "Sensor Platform " + this.getId() + " starts moving." );
            }
            break;
          case STOP_MOVING:
            if( getCurrentKinematicState().equals( KinematicState.MOVING ) || 
                getCurrentKinematicState().equals( KinematicState.STARTED )
                || getCurrentKinematicState().equals( KinematicState.PAUSED ) ) {
              setCurrentKinematicState( KinematicState.FINISHED );
              logger.trace( "Sensor Platform " + this.getId() + " stops moving." );
            }
            break;
          case PAUSE_MOVING:
            if( getCurrentKinematicState().equals( KinematicState.STARTED ) || 
                getCurrentKinematicState().equals( KinematicState.MOVING ) ) {
              setCurrentKinematicState( KinematicState.PAUSED );
              logger.trace( "Sensor Platform " + this.getId() + " pauses moving." );
            }
            break;
          case RESUME_MOVING:
            if( getCurrentKinematicState().equals( KinematicState.PAUSED ) ) {
              setCurrentKinematicState( KinematicState.MOVING );
              logger.trace( "Sensor Platform " + this.getId() + " resumes moving." );
            }
            break;
          case ACTIVATE_SENSOR:
            if( getCurrentSensorState().equals( SensorState.OFF )) {
              setCurrentSensorState( SensorState.ON );
              logger.trace( "Sensor Platform " + this.getId() + " sensor activated." );
            }
            break;
          case DEACTIVATE_SENSOR:
            if( getCurrentSensorState().equals( SensorState.ON )) {
              setCurrentSensorState( SensorState.OFF );
              logger.trace( "Sensor Platform " + this.getId() + " sensor deactivated." );
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
      
      if( getCurrentSensorState().equals( SensorState.ON )) {
        sense();
      }

      if( getCurrentKinematicState().equals( KinematicState.MOVING ) ) {
        updatePosition();
      }
      
      try {
        Thread.sleep( 2000 );
      } catch( InterruptedException e ) {
        e.printStackTrace();
      }

    }
    
    logger.trace( "Sensor Platform " + this.getId() + " Plots Ratio: " + this.plots.size() + "/" + this.plotsGset.size() );

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
        this.plotsGset.add( plot );
        this.world.addFoundTarget( target );
        target.setHasBeenDetected( true );
                
        logger.trace( "Sensor Platform " + this.getId() + ": new Plot: " + plot );

      }
    }
  }

  public void updatePosition() {
    if( !this.positions.isEmpty() ) {
      this.visitedPositions.add( this.position );
      Position nextPosition = this.positions.get( 0 );
      this.setPosition( nextPosition );
      this.positions.remove( nextPosition );
      logger.trace( "Sensor Platform " + this.getId() + ": new Current-Position: " + this.getPosition() );
    } else {
      setCurrentKinematicState( KinematicState.FINISHED );
      logger.trace( "Sensor Platform " + this.getId() + " finished." );
      doStop();
    }
  }

  public void connect() {
    logger.trace( "Sensor Platform " + this.getId() + " connect." );

  }

  public void disconnect() {
    logger.trace( "Sensor Platform " + this.getId() + " disconnect." );

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
  public synchronized KinematicState getCurrentKinematicState() {
    return currentKinematicState;
  }

  /**
   * Sets the currentKinematicState of this SensorPlatform.
   * 
   * @param currentKinematicState the currentKinematicState to set.
   */
  public synchronized void setCurrentKinematicState( KinematicState currentKinematicState ) {
    this.currentKinematicState = currentKinematicState;
  }

  
  /**
   * Returns the crdtStore of this SensorPlatform.
   * @return the crdtStore of this SensorPlatform.
   */
  public LocalCrdtStore getCrdtStore() {
    return crdtStore;
  }

  
  /**
   * Sets the crdtStore of this SensorPlatform.
   * @param crdtStore the crdtStore to set.
   */
  public void setCrdtStore( LocalCrdtStore crdtStore ) {
    this.crdtStore = crdtStore;
  }

  
  /**
   * Returns the currentSensorState of this SensorPlatform.
   * @return the currentSensorState of this SensorPlatform.
   */
  public synchronized SensorState getCurrentSensorState() {
    return currentSensorState;
  }

  
  /**
   * Sets the currentSensorState of this SensorPlatform.
   * @param currentSensorState the currentSensorState to set.
   */
  public synchronized void setCurrentSensorState( SensorState currentSensorState ) {
    this.currentSensorState = currentSensorState;
  }

}
