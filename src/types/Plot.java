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

package types;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * Defines the class Plot.
 */
public class Plot {
  
  /**
   * @param id
   * @param timestamp
   * @param sensorID
   * @param sensorPosition
   * @param sensorObjectType
   * @param targetID
   * @param targetPosition
   * @param targetType
   */
  public Plot( UUID id, Timestamp timestamp, UUID sensorID, Position sensorPosition, ObjectType sensorObjectType, UUID targetID,
      Position targetPosition, ObjectType targetType ) {
    this.id = id;
    this.timestamp = timestamp;
    this.sensorID = sensorID;
    this.sensorPosition = sensorPosition;
    this.sensorObjectType = sensorObjectType;
    this.targetID = targetID;
    this.targetPosition = targetPosition;
    this.targetType = targetType;
  }


  private UUID id;
  
  private Timestamp timestamp;
  
  private UUID sensorID;
  
  private Position sensorPosition;
  
  private ObjectType sensorObjectType;
  
  private UUID targetID;
  
  private Position targetPosition;
  
  private ObjectType targetType;

  
  /**
   * Returns the id of this Plot.
   * @return the id of this Plot.
   */
  public UUID getId() {
    return id;
  }

  
  /**
   * Sets the id of this Plot.
   * @param id the id to set.
   */
  public void setId( UUID id ) {
    this.id = id;
  }

  
  /**
   * Returns the timestamp of this Plot.
   * @return the timestamp of this Plot.
   */
  public Timestamp getTimestamp() {
    return timestamp;
  }

  
  /**
   * Sets the timestamp of this Plot.
   * @param timestamp the timestamp to set.
   */
  public void setTimestamp( Timestamp timestamp ) {
    this.timestamp = timestamp;
  }

  
  /**
   * Returns the sensorID of this Plot.
   * @return the sensorID of this Plot.
   */
  public UUID getSensorID() {
    return sensorID;
  }

  
  /**
   * Sets the sensorID of this Plot.
   * @param sensorID the sensorID to set.
   */
  public void setSensorID( UUID sensorID ) {
    this.sensorID = sensorID;
  }

  
  /**
   * Returns the sensorPosition of this Plot.
   * @return the sensorPosition of this Plot.
   */
  public Position getSensorPosition() {
    return sensorPosition;
  }

  
  /**
   * Sets the sensorPosition of this Plot.
   * @param sensorPosition the sensorPosition to set.
   */
  public void setSensorPosition( Position sensorPosition ) {
    this.sensorPosition = sensorPosition;
  }

  
  /**
   * Returns the sensorObjectType of this Plot.
   * @return the sensorObjectType of this Plot.
   */
  public ObjectType getSensorObjectType() {
    return sensorObjectType;
  }

  
  /**
   * Sets the sensorObjectType of this Plot.
   * @param sensorObjectType the sensorObjectType to set.
   */
  public void setSensorObjectType( ObjectType sensorObjectType ) {
    this.sensorObjectType = sensorObjectType;
  }

  
  /**
   * Returns the targetID of this Plot.
   * @return the targetID of this Plot.
   */
  public UUID getTargetID() {
    return targetID;
  }

  
  /**
   * Sets the targetID of this Plot.
   * @param targetID the targetID to set.
   */
  public void setTargetID( UUID targetID ) {
    this.targetID = targetID;
  }

  
  /**
   * Returns the targetPosition of this Plot.
   * @return the targetPosition of this Plot.
   */
  public Position getTargetPosition() {
    return targetPosition;
  }

  
  /**
   * Sets the targetPosition of this Plot.
   * @param targetPosition the targetPosition to set.
   */
  public void setTargetPosition( Position targetPosition ) {
    this.targetPosition = targetPosition;
  }

  
  /**
   * Returns the targetType of this Plot.
   * @return the targetType of this Plot.
   */
  public ObjectType getTargetType() {
    return targetType;
  }

  
  /**
   * Sets the targetType of this Plot.
   * @param targetType the targetType to set.
   */
  public void setTargetType( ObjectType targetType ) {
    this.targetType = targetType;
  }


  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash( id, sensorID, sensorObjectType, sensorPosition, targetID, targetPosition, targetType, timestamp );
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
    Plot other = (Plot)obj;
    return Objects.equals( id, other.id ) && Objects.equals( sensorID, other.sensorID ) && sensorObjectType == other.sensorObjectType
           && Objects.equals( sensorPosition, other.sensorPosition ) && Objects.equals( targetID, other.targetID )
           && Objects.equals( targetPosition, other.targetPosition ) && targetType == other.targetType
           && Objects.equals( timestamp, other.timestamp );
  }


  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "Plot [id=" + id + ", timestamp=" + timestamp + ", sensorID=" + sensorID + ", sensorPosition=" + sensorPosition + ", sensorObjectType="
           + sensorObjectType + ", targetID=" + targetID + ", targetPosition=" + targetPosition + ", targetType=" + targetType + "]";
  }

}
