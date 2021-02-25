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

/**
 * Defines the class World.
 */
public class World {
  
  private int width;
  
  private int length;
  
  private ArrayList<Target> listOfTargets;
  
  private ArrayList<SensorPlatform> listOfSensorPlatforms;
  
  public World(int width, int length) {
    this.width = width;
    this.length = length;
    this.listOfTargets = new ArrayList<Target>();
    this.listOfSensorPlatforms = new ArrayList<SensorPlatform>();
  }

  
  /**
   * Returns the width of this World.
   * @return the width of this World.
   */
  public int getWidth() {
    return width;
  }

  
  /**
   * Sets the width of this World.
   * @param width the width to set.
   */
  public void setWidth( int width ) {
    this.width = width;
  }

  
  /**
   * Returns the length of this World.
   * @return the length of this World.
   */
  public int getLength() {
    return length;
  }

  
  /**
   * Sets the length of this World.
   * @param length the length to set.
   */
  public void setLength( int length ) {
    this.length = length;
  }

  
  /**
   * Returns the listOfTargets of this World.
   * @return the listOfTargets of this World.
   */
  public ArrayList< Target > getListOfTargets() {
    return listOfTargets;
  }

  
  /**
   * Sets the listOfTargets of this World.
   * @param listOfTargets the listOfTargets to set.
   */
  public void setListOfTargets( ArrayList< Target > listOfTargets ) {
    this.listOfTargets = listOfTargets;
  }

  
  /**
   * Returns the listOfSensorPlatforms of this World.
   * @return the listOfSensorPlatforms of this World.
   */
  public ArrayList< SensorPlatform > getListOfSensorPlatforms() {
    return listOfSensorPlatforms;
  }

  
  /**
   * Sets the listOfSensorPlatforms of this World.
   * @param listOfSensorPlatforms the listOfSensorPlatforms to set.
   */
  public void setListOfSensorPlatforms( ArrayList< SensorPlatform > listOfSensorPlatforms ) {
    this.listOfSensorPlatforms = listOfSensorPlatforms;
  }
  
  /**
   * Adds a Target to the listOfTargets
   * @param target
   */
  public void addTarget(Target target) {
    this.listOfTargets.add( target );
  }
  
  /**
   * Removes a Target from the listOfTargets
   * @param target
   */
  public void removeTarget(Target target) {
    this.listOfTargets.remove( target );
  }
  
  /**
   * Adds a SensorPlatform to the listOfSensorPlatforms
   * @param sensorPlatform
   */
  public void addSensorPlatform(SensorPlatform sensorPlatform) {
    this.listOfSensorPlatforms.add( sensorPlatform );
  }
  
  /**
   * Removes a SensorPlatform from the listOfSensorPlatforms
   * @param sensorPlatform
   */
  public void removeSensorPlatform(SensorPlatform sensorPlatform) {
    this.listOfSensorPlatforms.remove( sensorPlatform );
  }


  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash( length, listOfSensorPlatforms, listOfTargets, width );
  }


  /** {@inheritDoc} */
  @Override
  public boolean equals( Object obj ) {
    if( this == obj )
      return true;
    if( obj == null )
      return false;
    if( getClass() != obj.getClass() )
      return false;
    World other = (World)obj;
    return length == other.length && Objects.equals( listOfSensorPlatforms, other.listOfSensorPlatforms )
           && Objects.equals( listOfTargets, other.listOfTargets ) && width == other.width;
  }


  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "World [width=" + width 
        + ", length=" + length 
        + ", listOfTargets=" 
        + listOfTargets + ", listOfSensorPlatforms=" 
        + listOfSensorPlatforms
           + "]";
  }

}
