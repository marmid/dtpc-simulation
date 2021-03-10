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

/**
 * Defines the class SensorArea.
 */
public class SensorArea {
  
  private int width;
  
  private int length;
  
  public SensorArea(int width, int length) {
    this.width = width;
    this.length = length;
  }

  
  /**
   * Returns the width of this SensorArea.
   * @return the width of this SensorArea.
   */
  public int getWidth() {
    return width;
  }

  
  /**
   * Sets the width of this SensorArea.
   * @param width the width to set.
   */
  public void setWidth( int width ) {
    this.width = width;
  }

  
  /**
   * Returns the length of this SensorArea.
   * @return the length of this SensorArea.
   */
  public int getLength() {
    return length;
  }

  
  /**
   * Sets the length of this SensorArea.
   * @param length the length to set.
   */
  public void setLength( int length ) {
    this.length = length;
  }


  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "SensorArea [width=" + width + ", length=" + length + "]";
  }


  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash( length, width );
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
    SensorArea other = (SensorArea)obj;
    return length == other.length && width == other.width;
  }

}
