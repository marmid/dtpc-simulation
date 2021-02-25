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

import java.util.Objects;

/**
 * Defines the class Position.
 */
public class Position {
  
  private int x;
  
  private int y;
  
  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  
  /**
   * Returns the x of this Position.
   * @return the x of this Position.
   */
  public int getX() {
    return x;
  }

  
  /**
   * Sets the x of this Position.
   * @param x the x to set.
   */
  public void setX( int x ) {
    this.x = x;
  }

  
  /**
   * Returns the y of this Position.
   * @return the y of this Position.
   */
  public int getY() {
    return y;
  }

  
  /**
   * Sets the y of this Position.
   * @param y the y to set.
   */
  public void setY( int y ) {
    this.y = y;
  }


  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash( x, y );
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
    Position other = (Position)obj;
    return x == other.x && y == other.y;
  }


  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "Position [x=" + x + ", y=" + y + "]";
  }

}
