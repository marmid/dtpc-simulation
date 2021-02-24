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

package objects;

import java.util.ArrayList;
import java.util.Objects;

import types.ObjectType;
import types.Plot;
import types.Position;
import types.SensorArea;

/**
 * Defines the class SensorPlatform.
 */
public class SensorPlatform extends WorldObject implements Runnable {
  
  /**
   * @param world
   * @param id
   * @param position
   * @param type
   * @param sensorArea
   * @param positions
   * @param plots
   */
  public SensorPlatform( World world, Position position, ObjectType type, SensorArea sensorArea, ArrayList< Position > positions,
      ArrayList< Plot > plots ) {
    super( world, position, type );
    this.sensorArea = sensorArea;
    this.positions = positions;
    this.plots = plots;
  }


  private SensorArea sensorArea;
  
  private ArrayList<Position> positions;
  
  private ArrayList<Plot> plots;


  @Override
  public void run() {
    // TODO Auto-generated method stub
    
  }
  
  public void sense() {
    
  }
  
  public void updatePosition() {
    
  }
  
  public void connect() {
    
  }
  
  public void disconnect() {
    
  }

  
  /**
   * Returns the plots of this SensorPlatform.
   * @return the plots of this SensorPlatform.
   */
  public ArrayList< Plot > getPlots() {
    return plots;
  }

  
  /**
   * Sets the plots of this SensorPlatform.
   * @param plots the plots to set.
   */
  public void setPlots( ArrayList< Plot > plots ) {
    this.plots = plots;
  }

  
  /**
   * Returns the positions of this SensorPlatform.
   * @return the positions of this SensorPlatform.
   */
  public ArrayList< Position > getPositions() {
    return positions;
  }

  
  /**
   * Sets the positions of this SensorPlatform.
   * @param positions the positions to set.
   */
  public void setPositions( ArrayList< Position > positions ) {
    this.positions = positions;
  }

  
  /**
   * Returns the sensorArea of this SensorPlatform.
   * @return the sensorArea of this SensorPlatform.
   */
  public SensorArea getSensorArea() {
    return sensorArea;
  }

  
  /**
   * Sets the sensorArea of this SensorPlatform.
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
    return "SensorPlatform [sensorArea=" + sensorArea + ", positions=" + positions + ", plots=" + plots + "]";
  }
  

}
