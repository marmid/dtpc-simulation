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

import java.util.Random;

/**
 * Defines the class ObjectType.
 */
public enum ObjectType {
  TARGET1,
  TARGET2,
  TARGET3,
  SENSOR;
  
  public static ObjectType getRandomTargetObjectType(Random random) {
    return values()[random.nextInt(values().length - 1)];
  }
}
