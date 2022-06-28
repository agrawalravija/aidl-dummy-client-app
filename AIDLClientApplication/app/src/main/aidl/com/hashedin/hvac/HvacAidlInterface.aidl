// HvacAidlInterface.aidl
package com.hashedin.hvac;

// Declare any non-default types here with import statements

interface HvacAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   /* void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);*/

      /** Request the value of Left Temperature */
      int getLeftTempValue();

      /** Request the value of Right Temperature */
      int getRightTempValue();

      /** Request the value of AC */
      boolean getAcValue();

      /** Request the value of Fan speed */
      int getFanSpeed();

      void setLeftTemp(int value);

      void setRightTemp(int value);

      void setAcValue(boolean value);

      void setFanSpeed(int value);
}