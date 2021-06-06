package fr.java.sdk.math.functions.onedim;

import java.util.Arrays;

import fr.java.math.functions.MathFunction;

public class PiecewiseConstantFunction implements MathFunction {
   private double[] x;
   private double[] y;

   public PiecewiseConstantFunction (double[] x, double[] y) {
      if (x.length != y.length)
         throw new IllegalArgumentException();
      this.x = x.clone ();
      this.y = y.clone ();
   }

   public double[] getX() {
      return x.clone ();
   }

   public double[] getY() {
      return y.clone ();
   }

   public double evaluate (double x) {
      final int idx = Arrays.binarySearch (this.x, x);
      if (idx >= 0)
         return y[idx];
      final int insertionPoint = -(idx + 1);
      return y[insertionPoint];
   }
}
