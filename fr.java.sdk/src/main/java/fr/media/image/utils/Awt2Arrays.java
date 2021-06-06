package fr.media.image.utils;

import java.awt.image.BufferedImage;

public interface Awt2Arrays {

    public static int[][] asIntBinaryArray2D(BufferedImage img, int _threshold) {
        int height = img.getHeight();
        int width = img.getWidth();

        int[][] gs = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int bits = img.getRGB(j, i);
                long avg = Math.round((((bits >> 16) & 0xff) + ((bits >> 8) & 0xff) + (bits & 0xff)) / 3.0);
                gs[i][j] = (int) avg;
            }
        }
        
        return gs;
    }

    public static int[][] asIntGrayArray2D(BufferedImage img) {
        int height = img.getHeight();
        int width = img.getWidth();

        int[][] gs = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int bits = img.getRGB(j, i);
                long avg = Math.round((((bits >> 16) & 0xff) + ((bits >> 8) & 0xff) + (bits & 0xff)) / 3.0);
                gs[i][j] = (int) avg;
            }
        }
        
        return gs;
    }

    public static BufferedImage GSImg(int[][] raw) {
        BufferedImage img = null;
        int height = raw.length;
        int width = raw[0].length;
        
        if (height > 0 && width > 0) {
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    img.setRGB(j, i, (raw[i][j] << 16) | (raw[i][j] << 8) | (raw[i][j]));
                }
            }
        }
        
        return img;
    }
    
    
	public static int[][][] RGBArray(BufferedImage _image) {
        int height = _image.getHeight();
        int width  = _image.getWidth();
        
        int[][][] out = new int[3][height][width];

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
            	int   rgbValue = _image.getRGB(column, row);
                int[] rgbArray = { (rgbValue >> 16) & 0xff, (rgbValue >> 8) & 0xff, rgbValue & 0xff };

                for (int i = 0; i < 3; i++) {
                    if (rgbArray[i] < 0) {
                    	rgbArray[i] = 0;
                    } else if (rgbArray[i] > 255) {
                    	rgbArray[i] = 255;
                    }
                    
                    out[i][row][column] = rgbArray[2 - i];
                }
            }
        }
        
        return out;
    }
	public static BufferedImage RGBImg(int[][][] _array) {
        BufferedImage img = null;

        int depth  = _array.length;
        int height = _array[0].length;
        int width  = _array[0][0].length;
        
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
            	
            	if(depth == 3) {
	            	int red = _array[0][row][column];
	            	int gre = _array[1][row][column];
	            	int blu = _array[2][row][column];
	
	                img.setRGB(column, row, (red << 16) | (gre << 8) | (blu));
            	} else {	// Assume mono channel
	            	int lum = _array[0][row][column];
            		
            		img.setRGB(column, row, (lum << 16) | (lum << 8) | (lum));
            	}
            }
        }
        
        return img;
    }

}
