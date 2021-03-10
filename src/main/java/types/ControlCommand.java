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

package main.java.types;

import java.util.Objects;

import main.java.objects.SensorPlatform;

/**
 * Defines the class ControlCommand.
 */
public class ControlCommand {
  
  /**
   * @param commandTarget
   * @param commandType
   */
  public ControlCommand( SensorPlatform commandTarget, ControlCommandType commandType ) {
    super();
    this.commandTarget = commandTarget;
    this.commandType = commandType;
  }


  private SensorPlatform commandTarget;
  
  private ControlCommandType commandType;

  
  /**
   * Returns the commandTarget of this ControlCommand.
   * @return the commandTarget of this ControlCommand.
   */
  public SensorPlatform getCommandTarget() {
    return commandTarget;
  }

  
  /**
   * Sets the commandTarget of this ControlCommand.
   * @param commandTarget the commandTarget to set.
   */
  public void setCommandTarget( SensorPlatform commandTarget ) {
    this.commandTarget = commandTarget;
  }

  
  /**
   * Returns the commandType of this ControlCommand.
   * @return the commandType of this ControlCommand.
   */
  public ControlCommandType getCommandType() {
    return commandType;
  }

  
  /**
   * Sets the commandType of this ControlCommand.
   * @param commandType the commandType to set.
   */
  public void setCommandType( ControlCommandType commandType ) {
    this.commandType = commandType;
  }


  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "ControlCommand [commandTarget=" + commandTarget + ", commandType=" + commandType + "]";
  }


  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash( commandTarget, commandType );
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
    ControlCommand other = (ControlCommand)obj;
    return Objects.equals( commandTarget, other.commandTarget ) && commandType == other.commandType;
  }
  
  

}
