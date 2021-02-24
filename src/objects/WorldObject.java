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

import java.util.Objects;
import java.util.UUID;

import types.ObjectType;
import types.Position;

/**
 * Defines the class WorldObject.
 */
abstract class WorldObject {
  
  /**
   * @param world
   * @param position
   * @param type
   */
  public WorldObject( World world, UUID id, Position position, ObjectType type ) {
    super();
    this.world = world;
    this.id = id;
    this.position = position;
    this.type = type;
  }


  private World world;

  private UUID id;

  private Position position;
  
  private ObjectType type;

  
  /**
   * Returns the world of this WorldObject.
   * @return the world of this WorldObject.
   */
  public World getWorld() {
    return world;
  }

  
  /**
   * Sets the world of this WorldObject.
   * @param world the world to set.
   */
  public void setWorld( World world ) {
    this.world = world;
  }

  
  /**
   * Returns the id of this WorldObject.
   * @return the id of this WorldObject.
   */
  public UUID getId() {
    return id;
  }

  
  /**
   * Sets the id of this WorldObject.
   * @param id the id to set.
   */
  public void setId( UUID id ) {
    this.id = id;
  }

  
  /**
   * Returns the position of this WorldObject.
   * @return the position of this WorldObject.
   */
  public Position getPosition() {
    return position;
  }

  
  /**
   * Sets the position of this WorldObject.
   * @param position the position to set.
   */
  public void setPosition( Position position ) {
    this.position = position;
  }

  
  /**
   * Returns the type of this WorldObject.
   * @return the type of this WorldObject.
   */
  public ObjectType getType() {
    return type;
  }

  
  /**
   * Sets the type of this WorldObject.
   * @param type the type to set.
   */
  public void setType( ObjectType type ) {
    this.type = type;
  }


  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "WorldObject [world=" + world 
        + ", id=" + id 
        + ", position=" + position 
        + ", type=" + type + "]";
  }


  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash( id, position, type, world );
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
    WorldObject other = (WorldObject)obj;
    return Objects.equals( id, other.id ) && Objects.equals( position, other.position ) && type == other.type && Objects.equals( world, other.world );
  }

}
