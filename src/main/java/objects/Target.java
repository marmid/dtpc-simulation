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

import java.util.UUID;

import main.java.types.ObjectType;
import main.java.types.Position;

/**
 * Defines the class Target.
 */
public class Target extends WorldObject {
  
  private Boolean hasBeenDetected = false;
  
  /**
   * @param world
   * @param id
   * @param position
   * @param type
   */
  public Target( World world, UUID id, Position position, ObjectType type ) {
    super( world, id, position, type );
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return super.hashCode();
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
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "Target [id=" + id 
        + ", position=" + position 
        + ", type=" + type + "]";
  }

  
  /**
   * Returns the hasBeenDetected of this Target.
   * @return the hasBeenDetected of this Target.
   */
  public synchronized Boolean getHasBeenDetected() {
    return hasBeenDetected;
  }

  
  /**
   * Sets the hasBeenDetected of this Target.
   * @param hasBeenDetected the hasBeenDetected to set.
   */
  public synchronized void setHasBeenDetected( Boolean hasBeenDetected ) {
    this.hasBeenDetected = hasBeenDetected;
  }

}
