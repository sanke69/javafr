/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
package fr.java.draw.tools;

import java.util.HashMap;
import java.util.Map;

import fr.java.draw.tools.Color;

// TODO:: Move to SDK or Media
public class Colors {
	private static final double DARKER_BRIGHTER_FACTOR = 0.7;
	private static final double SATURATE_DESATURATE_FACTOR = 0.7;

	static class SimpleRGB implements Color.RGB {
		double r, g, b, a;

		SimpleRGB() {
			super();
			r = g = b = a = 0d;
		}
		SimpleRGB(double _red, double _green, double _blue) {
			super();
			r = _red;
			g = _green;
			b = _blue;
			a = 0d;
		}
		SimpleRGB(double _red, double _green, double _blue, double _opacity) {
			super();
			r = _red;
			g = _green;
			b = _blue;
			a = 1d - _opacity;
		}
		SimpleRGB(double[] _vect) {
			super();
			r = _vect[0];
			g = _vect[1];
			b = _vect[2];
			a = _vect.length >= 4 ? _vect[3] : 0d;
		}

		@Override public RGB toRGB() { return this; }
		@Override public HSB toHSB() {
			double[] _hsb = RGBtoHSB(getRed(), getGreen(), getBlue());
			return hsb(_hsb[0], _hsb[1], _hsb[2]);
		}

		@Override public double getRed() 			{ return r; }
		@Override public double getGreen() 			{ return g; }
		@Override public double getBlue() 			{ return b; }
		@Override public double getOpacity() 		{ return 1d - a; }
		@Override public double getTransparency() 	{ return a; }
//		@Override public double getAlpha() 			{ return a; }

	}
	static class SimpleHSB implements Color.HSB {
		double h, s, b, o;

		SimpleHSB() {
			super();
			h = s = b = o = 0d;
		}
		SimpleHSB(double _hue, double _saturation, double _brightness) {
			super();
			h = _hue;
			s = _saturation;
			b = _brightness;
			o = 0d;
		}
		SimpleHSB(double _hue, double _saturation, double _brightness, double _opacity) {
			super();
			h = _hue;
			s = _saturation;
			b = _brightness;
			o = _opacity;
		}

		@Override public RGB toRGB() { 
			double[] _rgb = HSBtoRGB(getHue(), getSaturation(), getBrightness());
			return rgb(_rgb[0], _rgb[1], _rgb[2]); }
		@Override public HSB toHSB() { return this; }

		@Override public double getHue() 			{ return h; }
		@Override public double getSaturation() 	{ return s; }
		@Override public double getBrightness() 	{ return b; }
		@Override public double getOpacity() 		{ return o; }
		@Override public double getTransparency() 	{ return 1d- getOpacity(); }

	}

	static class SimpleInterpolable extends SimpleRGB implements Color.Interpolable {

		SimpleInterpolable() {
			super();
		}
		SimpleInterpolable(double _red, double _green, double _blue) {
			super(_red, _green, _blue);
		}
		SimpleInterpolable(double _red, double _green, double _blue, double _opacity) {
			super(_red, _green, _blue, _opacity);
		}
		SimpleInterpolable(Color.RGB _rgb) {
			super(_rgb.getRed(), _rgb.getGreen(), _rgb.getBlue(), _rgb.getOpacity());
		}
		SimpleInterpolable(Color.HSB _hsb) {
			super(HSBtoRGB(_hsb.getHue(), _hsb.getSaturation(), _hsb.getBrightness()));
		}

	    public Color brighter() {
	        return derive(0, 1.0, 1.0 / DARKER_BRIGHTER_FACTOR, 1.0);
	    }
	    public Color darker() {
	        return derive(0, 1.0, DARKER_BRIGHTER_FACTOR, 1.0);
	    }

	    public Color saturate() {
	        return derive(0, 1.0 / SATURATE_DESATURATE_FACTOR, 1.0, 1.0);
	    }
	    public Color desaturate() {
	        return derive(0, SATURATE_DESATURATE_FACTOR, 1.0, 1.0);
	    }

	    public Color grayscale() {
	        double gray = 0.21 * getRed() + 0.71 * getGreen() + 0.07 * getBlue();
	        return rgb(gray, gray, gray, getOpacity());
	    }
	  
	    public Color derive(double hueShift, double saturationFactor, double brightnessFactor, double opacityFactor) {
	        double[] hsb = RGBtoHSB(getRed(), getGreen(), getBlue());

	        double b = hsb[2];
	        if (b == 0 && brightnessFactor > 1.0)
	            b = 0.05;

	        double h = (((hsb[0] + hueShift) % 360) + 360) % 360;
	        double s = Math.max(Math.min(hsb[1] * saturationFactor, 1.0), 0.0);
	        b = Math.max(Math.min(b * brightnessFactor, 1.0), 0.0);
	        double a = Math.max(Math.min(getOpacity() * opacityFactor, 1.0), 0.0);
	        return hsb(h, s, b, a);
	    }
	    public Color opacity(double opacityFactor) {
	        return derive(1, 1, 1, opacityFactor);
	    }

		@Override
		public Color invert() {
	        return rgb(1.0 - getRed(), 1.0 - getGreen(), 1.0 - getBlue(), getOpacity());
	    }

	    @Override
	    public Color interpolate(Color endValue, double t) {
	        if (t <= 0.0) return this;
	        if (t >= 1.0) return endValue;
	        float ft = (float) t;
	        return rgb(
	        		getRed()     + (endValue.getRed()     - getRed())     * ft,
	        		getGreen()   + (endValue.getGreen()   - getGreen())   * ft,
	        		getBlue()    + (endValue.getBlue()    - getBlue())    * ft,
	        		getOpacity() + (endValue.getOpacity() - getOpacity()) * ft
	        );
	    }

	}

	public static String    toWeb(int _red, int _green, int _blue) {
		return String.format("#%02X%02X%02X", _red, _green, _blue);
	}
	public static String    toWeb(int _red, int _green, int _blue, int _alpha) {
		return String.format("#%02X%02X%02X%02X", _red, _green, _blue, _alpha);
	}
	public static String    toWeb(Color _rgb) {
		return String.format("#%02X%02X%02X%02X",
							    ((int) _rgb . getRed())   * 255,
							    ((int) _rgb . getGreen()) * 255,
							    ((int) _rgb . getBlue())  * 255,
							    ((int) _rgb . getOpacity()) * 255);
	}
	public static String    toWeb(Color.RGB _rgb) {
		return String.format("#%02X%02X%02X",
							    ((int) _rgb . getRed())   * 255,
							    ((int) _rgb . getGreen()) * 255,
							    ((int) _rgb . getBlue())  * 255,
							    ((int) _rgb . getOpacity()) * 255);
	}

    public static Color.RGB rgb(double _red, double _green, double _blue) {
        return new SimpleRGB(_red, _green, _blue);
    }
    public static Color.RGB rgb(double _red, double _green, double _blue, double _opacity) {
        return new SimpleRGB(_red, _green, _blue, _opacity);
    }

    public static Color.HSB hsb(double _hue, double _saturation, double _brightness) {
        return new SimpleHSB(_hue, _saturation, _brightness);
    }
    public static Color.HSB hsb(double _hue, double _saturation, double _brightness, double _opacity) {
        return new SimpleHSB(_hue, _saturation, _brightness, _opacity);
    }

	public static final Color.Interpolable interpolable() {
		return new SimpleInterpolable();
	}

	public static final Color.Interpolable of(int _red, int _green, int _blue) {
		return new SimpleInterpolable(_red / 255d, _green / 255d, _blue / 255d);
	}
	public static final Color.Interpolable of(int _red, int _green, int _blue, int _opacity) {
		return new SimpleInterpolable(_red / 255d, _green / 255d, _blue / 255d, _opacity / 255d);
	}
	public static final Color.Interpolable of(float _red, float _green, float _blue) {
		return new SimpleInterpolable(_red, _green, _blue);
	}
	public static final Color.Interpolable of(float _red, float _green, float _blue, float _opacity) {
		return new SimpleInterpolable(_red, _green, _blue, _opacity);
	}
	public static final Color.Interpolable of(double _red, double _green, double _blue) {
		return new SimpleInterpolable(_red, _green, _blue);
	}
	public static final Color.Interpolable of(double _red, double _green, double _blue, double _opacity) {
		return new SimpleInterpolable(_red, _green, _blue, _opacity);
	}
	public static final Color.Interpolable of(java.awt.Color awt) {
		return new SimpleInterpolable(awt.getRed(), awt.getGreen(), awt.getBlue(), awt.getAlpha());
	}
	public static final Color.Interpolable of(String _name) {
		return namedColors.get(_name);
	}
	public static final Color.Interpolable of(String _name, double _opacity) {
		return namedColors.get(_name);
	}
	public static final Color.Interpolable web(String _name) {
		return new SimpleInterpolable(Integer.valueOf( _name.substring( 1, 3 ), 16 ) / 255d, Integer.valueOf( _name.substring( 3, 5 ), 16 ) / 255d, Integer.valueOf( _name.substring( 5, 7 ), 16 ) / 255d);
	}

	public static final Color.Interpolable interpolate(Color _from, Color _to, double t) {
        if (t <= 0.0) return new SimpleInterpolable(_from.toRGB());
        if (t >= 1.0) return new SimpleInterpolable(_to.toRGB());
        float ft = (float) t;
        
//        System.out.println(ft + ": " + "\t" + "I= " + _from.getRed() + "\t" + ", O= " + _to.getRed() + "\t" + (_from.getRed() + (_to.getRed() - _from.getRed()) * t));
        
        return of(
        		_from.getRed()     + (_to.getRed()     - _from.getRed())     * ft,
        		_from.getGreen()   + (_to.getGreen()   - _from.getGreen())   * ft,
        		_from.getBlue()    + (_to.getBlue()    - _from.getBlue())    * ft,
        		1d//_from.getOpacity() + (_to.getOpacity() - _from.getOpacity()) * ft
        );
	}

	public static final Color.Interpolable	TRANSPARENT				= Colors.of(0f, 0f, 0f, 0f);
	public static final Color.Interpolable	ALICEBLUE				= Colors.of(0.9411765f, 0.972549f, 1.0f);
	public static final Color.Interpolable	ANTIQUEWHITE			= Colors.of(0.98039216f, 0.92156863f, 0.84313726f);
	public static final Color.Interpolable	AQUA					= Colors.of(0.0f, 1.0f, 1.0f);
	public static final Color.Interpolable	AQUAMARINE				= Colors.of(0.49803922f, 1.0f, 0.83137256f);
	public static final Color.Interpolable	AZURE					= Colors.of(0.9411765f, 1.0f, 1.0f);
	public static final Color.Interpolable	BEIGE					= Colors.of(0.9607843f, 0.9607843f, 0.8627451f);
	public static final Color.Interpolable	BISQUE					= Colors.of(1.0f, 0.89411765f, 0.76862746f);
	public static final Color.Interpolable	BLACK					= Colors.of(0.0f, 0.0f, 0.0f);
	public static final Color.Interpolable	BLANCHEDALMOND			= Colors.of(1.0f, 0.92156863f, 0.8039216f);
	public static final Color.Interpolable	BLUE					= Colors.of(0.0f, 0.0f, 1.0f);
	public static final Color.Interpolable	BLUEVIOLET				= Colors.of(0.5411765f, 0.16862746f, 0.8862745f);
	public static final Color.Interpolable	BROWN					= Colors.of(0.64705884f, 0.16470589f, 0.16470589f);
	public static final Color.Interpolable	BURLYWOOD				= Colors.of(0.87058824f, 0.72156864f, 0.5294118f);
	public static final Color.Interpolable	CADETBLUE				= Colors.of(0.37254903f, 0.61960787f, 0.627451f);
	public static final Color.Interpolable	CHARTREUSE				= Colors.of(0.49803922f, 1.0f, 0.0f);
	public static final Color.Interpolable	CHOCOLATE				= Colors.of(0.8235294f, 0.4117647f, 0.11764706f);
	public static final Color.Interpolable	CORAL					= Colors.of(1.0f, 0.49803922f, 0.3137255f);
	public static final Color.Interpolable	CORNFLOWERBLUE			= Colors.of(0.39215687f, 0.58431375f, 0.92941177f);
	public static final Color.Interpolable	CORNSILK				= Colors.of(1.0f, 0.972549f, 0.8627451f);
	public static final Color.Interpolable	CRIMSON					= Colors.of(0.8627451f, 0.078431375f, 0.23529412f);
	public static final Color.Interpolable	CYAN					= Colors.of(0.0f, 1.0f, 1.0f);
	public static final Color.Interpolable	DARKBLUE				= Colors.of(0.0f, 0.0f, 0.54509807f);
	public static final Color.Interpolable	DARKCYAN				= Colors.of(0.0f, 0.54509807f, 0.54509807f);
	public static final Color.Interpolable	DARKGOLDENROD			= Colors.of(0.72156864f, 0.5254902f, 0.043137256f);
	public static final Color.Interpolable	DARKGRAY				= Colors.of(0.6627451f, 0.6627451f, 0.6627451f);
	public static final Color.Interpolable	DARKGREEN				= Colors.of(0.0f, 0.39215687f, 0.0f);
	public static final Color.Interpolable	DARKGREY				= DARKGRAY;
	public static final Color.Interpolable	DARKKHAKI				= Colors.of(0.7411765f, 0.7176471f, 0.41960785f);
	public static final Color.Interpolable	DARKMAGENTA				= Colors.of(0.54509807f, 0.0f, 0.54509807f);
	public static final Color.Interpolable	DARKOLIVEGREEN			= Colors.of(0.33333334f, 0.41960785f, 0.18431373f);
	public static final Color.Interpolable	DARKORANGE				= Colors.of(1.0f, 0.54901963f, 0.0f);
	public static final Color.Interpolable	DARKORCHID				= Colors.of(0.6f, 0.19607843f, 0.8f);
	public static final Color.Interpolable	DARKRED					= Colors.of(0.54509807f, 0.0f, 0.0f);
	public static final Color.Interpolable	DARKSALMON				= Colors.of(0.9137255f, 0.5882353f, 0.47843137f);
	public static final Color.Interpolable	DARKSEAGREEN			= Colors.of(0.56078434f, 0.7372549f, 0.56078434f);
	public static final Color.Interpolable	DARKSLATEBLUE			= Colors.of(0.28235295f, 0.23921569f, 0.54509807f);
	public static final Color.Interpolable	DARKSLATEGRAY			= Colors.of(0.18431373f, 0.30980393f, 0.30980393f);
	public static final Color.Interpolable	DARKSLATEGREY			= DARKSLATEGRAY;
	public static final Color.Interpolable	DARKTURQUOISE			= Colors.of(0.0f, 0.80784315f, 0.81960785f);
	public static final Color.Interpolable	DARKVIOLET				= Colors.of(0.5803922f, 0.0f, 0.827451f);
	public static final Color.Interpolable	DEEPPINK				= Colors.of(1.0f, 0.078431375f, 0.5764706f);
	public static final Color.Interpolable	DEEPSKYBLUE				= Colors.of(0.0f, 0.7490196f, 1.0f);
	public static final Color.Interpolable	DIMGRAY					= Colors.of(0.4117647f, 0.4117647f, 0.4117647f);
	public static final Color.Interpolable	DIMGREY					= DIMGRAY;
	public static final Color.Interpolable	DODGERBLUE				= Colors.of(0.11764706f, 0.5647059f, 1.0f);
	public static final Color.Interpolable	FIREBRICK				= Colors.of(0.69803923f, 0.13333334f, 0.13333334f);
	public static final Color.Interpolable	FLORALWHITE				= Colors.of(1.0f, 0.98039216f, 0.9411765f);
	public static final Color.Interpolable	FORESTGREEN				= Colors.of(0.13333334f, 0.54509807f, 0.13333334f);
	public static final Color.Interpolable	FUCHSIA					= Colors.of(1.0f, 0.0f, 1.0f);
	public static final Color.Interpolable	GAINSBORO				= Colors.of(0.8627451f, 0.8627451f, 0.8627451f);
	public static final Color.Interpolable	GHOSTWHITE				= Colors.of(0.972549f, 0.972549f, 1.0f);
	public static final Color.Interpolable	GOLD					= Colors.of(1.0f, 0.84313726f, 0.0f);
	public static final Color.Interpolable	GOLDENROD				= Colors.of(0.85490197f, 0.64705884f, 0.1254902f);
	public static final Color.Interpolable	GRAY					= Colors.of(0.5019608f, 0.5019608f, 0.5019608f);
	public static final Color.Interpolable	GREEN					= Colors.of(0.0f, 0.5019608f, 0.0f);
	public static final Color.Interpolable	GREENYELLOW				= Colors.of(0.6784314f, 1.0f, 0.18431373f);
	public static final Color.Interpolable	GREY					= GRAY;
	public static final Color.Interpolable	HONEYDEW				= Colors.of(0.9411765f, 1.0f, 0.9411765f);
	public static final Color.Interpolable	HOTPINK					= Colors.of(1.0f, 0.4117647f, 0.7058824f);
	public static final Color.Interpolable	INDIANRED				= Colors.of(0.8039216f, 0.36078432f, 0.36078432f);
	public static final Color.Interpolable	INDIGO					= Colors.of(0.29411766f, 0.0f, 0.50980395f);
	public static final Color.Interpolable	IVORY					= Colors.of(1.0f, 1.0f, 0.9411765f);
	public static final Color.Interpolable	KHAKI					= Colors.of(0.9411765f, 0.9019608f, 0.54901963f);
	public static final Color.Interpolable	LAVENDER				= Colors.of(0.9019608f, 0.9019608f, 0.98039216f);
	public static final Color.Interpolable	LAVENDERBLUSH			= Colors.of(1.0f, 0.9411765f, 0.9607843f);
	public static final Color.Interpolable	LAWNGREEN				= Colors.of(0.4862745f, 0.9882353f, 0.0f);
	public static final Color.Interpolable	LEMONCHIFFON			= Colors.of(1.0f, 0.98039216f, 0.8039216f);
	public static final Color.Interpolable	LIGHTBLUE				= Colors.of(0.6784314f, 0.84705883f, 0.9019608f);
	public static final Color.Interpolable	LIGHTCORAL				= Colors.of(0.9411765f, 0.5019608f, 0.5019608f);
	public static final Color.Interpolable	LIGHTCYAN				= Colors.of(0.8784314f, 1.0f, 1.0f);
	public static final Color.Interpolable	LIGHTGOLDENRODYELLOW	= Colors.of(0.98039216f, 0.98039216f, 0.8235294f);
	public static final Color.Interpolable	LIGHTGRAY				= Colors.of(0.827451f, 0.827451f, 0.827451f);
	public static final Color.Interpolable	LIGHTGREEN				= Colors.of(0.5647059f, 0.93333334f, 0.5647059f);
	public static final Color.Interpolable	LIGHTGREY				= LIGHTGRAY;
	public static final Color.Interpolable	LIGHTPINK				= Colors.of(1.0f, 0.7137255f, 0.75686276f);
	public static final Color.Interpolable	LIGHTSALMON				= Colors.of(1.0f, 0.627451f, 0.47843137f);
	public static final Color.Interpolable	LIGHTSEAGREEN			= Colors.of(0.1254902f, 0.69803923f, 0.6666667f);
	public static final Color.Interpolable	LIGHTSKYBLUE			= Colors.of(0.5294118f, 0.80784315f, 0.98039216f);
	public static final Color.Interpolable	LIGHTSLATEGRAY			= Colors.of(0.46666667f, 0.53333336f, 0.6f);
	public static final Color.Interpolable	LIGHTSLATEGREY			= LIGHTSLATEGRAY;
	public static final Color.Interpolable	LIGHTSTEELBLUE			= Colors.of(0.6901961f, 0.76862746f, 0.87058824f);
	public static final Color.Interpolable	LIGHTYELLOW				= Colors.of(1.0f, 1.0f, 0.8784314f);
	public static final Color.Interpolable	LIME					= Colors.of(0.0f, 1.0f, 0.0f);
	public static final Color.Interpolable	LIMEGREEN				= Colors.of(0.19607843f, 0.8039216f, 0.19607843f);
	public static final Color.Interpolable	LINEN					= Colors.of(0.98039216f, 0.9411765f, 0.9019608f);
	public static final Color.Interpolable	MAGENTA					= Colors.of(1.0f, 0.0f, 1.0f);
	public static final Color.Interpolable	MAROON					= Colors.of(0.5019608f, 0.0f, 0.0f);
	public static final Color.Interpolable	MEDIUMAQUAMARINE		= Colors.of(0.4f, 0.8039216f, 0.6666667f);
	public static final Color.Interpolable	MEDIUMBLUE				= Colors.of(0.0f, 0.0f, 0.8039216f);
	public static final Color.Interpolable	MEDIUMORCHID			= Colors.of(0.7294118f, 0.33333334f, 0.827451f);
	public static final Color.Interpolable	MEDIUMPURPLE			= Colors.of(0.5764706f, 0.4392157f, 0.85882354f);
	public static final Color.Interpolable	MEDIUMSEAGREEN			= Colors.of(0.23529412f, 0.7019608f, 0.44313726f);
	public static final Color.Interpolable	MEDIUMSLATEBLUE			= Colors.of(0.48235294f, 0.40784314f, 0.93333334f);
	public static final Color.Interpolable	MEDIUMSPRINGGREEN		= Colors.of(0.0f, 0.98039216f, 0.6039216f);
	public static final Color.Interpolable	MEDIUMTURQUOISE			= Colors.of(0.28235295f, 0.81960785f, 0.8f);
	public static final Color.Interpolable	MEDIUMVIOLETRED			= Colors.of(0.78039217f, 0.08235294f, 0.52156866f);
	public static final Color.Interpolable	MIDNIGHTBLUE			= Colors.of(0.09803922f, 0.09803922f, 0.4392157f);
	public static final Color.Interpolable	MINTCREAM				= Colors.of(0.9607843f, 1.0f, 0.98039216f);
	public static final Color.Interpolable	MISTYROSE				= Colors.of(1.0f, 0.89411765f, 0.88235295f);
	public static final Color.Interpolable	MOCCASIN				= Colors.of(1.0f, 0.89411765f, 0.70980394f);
	public static final Color.Interpolable	NAVAJOWHITE				= Colors.of(1.0f, 0.87058824f, 0.6784314f);
	public static final Color.Interpolable	NAVY					= Colors.of(0.0f, 0.0f, 0.5019608f);
	public static final Color.Interpolable	OLDLACE					= Colors.of(0.99215686f, 0.9607843f, 0.9019608f);
	public static final Color.Interpolable	OLIVE					= Colors.of(0.5019608f, 0.5019608f, 0.0f);
	public static final Color.Interpolable	OLIVEDRAB				= Colors.of(0.41960785f, 0.5568628f, 0.13725491f);
	public static final Color.Interpolable	ORANGE					= Colors.of(1.0f, 0.64705884f, 0.0f);
	public static final Color.Interpolable	ORANGERED				= Colors.of(1.0f, 0.27058825f, 0.0f);
	public static final Color.Interpolable	ORCHID					= Colors.of(0.85490197f, 0.4392157f, 0.8392157f);
	public static final Color.Interpolable	PALEGOLDENROD			= Colors.of(0.93333334f, 0.9098039f, 0.6666667f);
	public static final Color.Interpolable	PALEGREEN				= Colors.of(0.59607846f, 0.9843137f, 0.59607846f);
	public static final Color.Interpolable	PALETURQUOISE			= Colors.of(0.6862745f, 0.93333334f, 0.93333334f);
	public static final Color.Interpolable	PALEVIOLETRED			= Colors.of(0.85882354f, 0.4392157f, 0.5764706f);
	public static final Color.Interpolable	PAPAYAWHIP				= Colors.of(1.0f, 0.9372549f, 0.8352941f);
	public static final Color.Interpolable	PEACHPUFF				= Colors.of(1.0f, 0.85490197f, 0.7254902f);
	public static final Color.Interpolable	PERU					= Colors.of(0.8039216f, 0.52156866f, 0.24705882f);
	public static final Color.Interpolable	PINK					= Colors.of(1.0f, 0.7529412f, 0.79607844f);
	public static final Color.Interpolable	PLUM					= Colors.of(0.8666667f, 0.627451f, 0.8666667f);
	public static final Color.Interpolable	POWDERBLUE				= Colors.of(0.6901961f, 0.8784314f, 0.9019608f);
	public static final Color.Interpolable	PURPLE					= Colors.of(0.5019608f, 0.0f, 0.5019608f);
	public static final Color.Interpolable	RED						= Colors.of(1.0f, 0.0f, 0.0f);
	public static final Color.Interpolable	ROSYBROWN				= Colors.of(0.7372549f, 0.56078434f, 0.56078434f);
	public static final Color.Interpolable	ROYALBLUE				= Colors.of(0.25490198f, 0.4117647f, 0.88235295f);
	public static final Color.Interpolable	SADDLEBROWN				= Colors.of(0.54509807f, 0.27058825f, 0.07450981f);
	public static final Color.Interpolable	SALMON					= Colors.of(0.98039216f, 0.5019608f, 0.44705883f);
	public static final Color.Interpolable	SANDYBROWN				= Colors.of(0.95686275f, 0.6431373f, 0.3764706f);
	public static final Color.Interpolable	SEAGREEN				= Colors.of(0.18039216f, 0.54509807f, 0.34117648f);
	public static final Color.Interpolable	SEASHELL				= Colors.of(1.0f, 0.9607843f, 0.93333334f);
	public static final Color.Interpolable	SIENNA					= Colors.of(0.627451f, 0.32156864f, 0.1764706f);
	public static final Color.Interpolable	SILVER					= Colors.of(0.7529412f, 0.7529412f, 0.7529412f);
	public static final Color.Interpolable	SKYBLUE					= Colors.of(0.5294118f, 0.80784315f, 0.92156863f);
	public static final Color.Interpolable	SLATEBLUE				= Colors.of(0.41568628f, 0.3529412f, 0.8039216f);
	public static final Color.Interpolable	SLATEGRAY				= Colors.of(0.4392157f, 0.5019608f, 0.5647059f);
	public static final Color.Interpolable	SLATEGREY				= SLATEGRAY;
	public static final Color.Interpolable	SNOW					= Colors.of(1.0f, 0.98039216f, 0.98039216f);
	public static final Color.Interpolable	SPRINGGREEN				= Colors.of(0.0f, 1.0f, 0.49803922f);
	public static final Color.Interpolable	STEELBLUE				= Colors.of(0.27450982f, 0.50980395f, 0.7058824f);
	public static final Color.Interpolable	TAN						= Colors.of(0.8235294f, 0.7058824f, 0.54901963f);
	public static final Color.Interpolable	TEAL					= Colors.of(0.0f, 0.5019608f, 0.5019608f);
	public static final Color.Interpolable	THISTLE					= Colors.of(0.84705883f, 0.7490196f, 0.84705883f);
	public static final Color.Interpolable	TOMATO					= Colors.of(1.0f, 0.3882353f, 0.2784314f);
	public static final Color.Interpolable	TURQUOISE				= Colors.of(0.2509804f, 0.8784314f, 0.8156863f);
	public static final Color.Interpolable	VIOLET					= Colors.of(0.93333334f, 0.50980395f, 0.93333334f);
	public static final Color.Interpolable	WHEAT					= Colors.of(0.9607843f, 0.87058824f, 0.7019608f);
	public static final Color.Interpolable	WHITE					= Colors.of(1.0f, 1.0f, 1.0f);
	public static final Color.Interpolable	WHITESMOKE				= Colors.of(0.9607843f, 0.9607843f, 0.9607843f);
	public static final Color.Interpolable	YELLOW					= Colors.of(1.0f, 1.0f, 0.0f);
	public static final Color.Interpolable	YELLOWGREEN				= Colors.of(0.6039216f, 0.8039216f, 0.19607843f);


	public static Map<String, Color.Interpolable> namedColors = new HashMap<String,Color.Interpolable>(){
		private static final long serialVersionUID = 1L;
	{
        put("aliceblue",            Colors.ALICEBLUE);
        put("antiquewhite",         Colors.ANTIQUEWHITE);
        put("aqua",                 Colors.AQUA);
        put("aquamarine",           Colors.AQUAMARINE);
        put("azure",                Colors.AZURE);
        put("beige",                Colors.BEIGE);
        put("bisque",               Colors.BISQUE);
        put("black",                Colors.BLACK);
        put("blanchedalmond",       Colors.BLANCHEDALMOND);
        put("blue",                 Colors.BLUE);
        put("blueviolet",           Colors.BLUEVIOLET);
        put("brown",                Colors.BROWN);
        put("burlywood",            Colors.BURLYWOOD);
        put("cadetblue",            Colors.CADETBLUE);
        put("chartreuse",           Colors.CHARTREUSE);
        put("chocolate",            Colors.CHOCOLATE);
        put("coral",                Colors.CORAL);
        put("cornflowerblue",       Colors.CORNFLOWERBLUE);
        put("cornsilk",             Colors.CORNSILK);
        put("crimson",              Colors.CRIMSON);
        put("cyan",                 Colors.CYAN);
        put("darkblue",             Colors.DARKBLUE);
        put("darkcyan",             Colors.DARKCYAN);
        put("darkgoldenrod",        Colors.DARKGOLDENROD);
        put("darkgray",             Colors.DARKGRAY);
        put("darkgreen",            Colors.DARKGREEN);
        put("darkgrey",             Colors.DARKGREY);
        put("darkkhaki",            Colors.DARKKHAKI);
        put("darkmagenta",          Colors.DARKMAGENTA);
        put("darkolivegreen",       Colors.DARKOLIVEGREEN);
        put("darkorange",           Colors.DARKORANGE);
        put("darkorchid",           Colors.DARKORCHID);
        put("darkred",              Colors.DARKRED);
        put("darksalmon",           Colors.DARKSALMON);
        put("darkseagreen",         Colors.DARKSEAGREEN);
        put("darkslateblue",        Colors.DARKSLATEBLUE);
        put("darkslategray",        Colors.DARKSLATEGRAY);
        put("darkslategrey",        Colors.DARKSLATEGREY);
        put("darkturquoise",        Colors.DARKTURQUOISE);
        put("darkviolet",           Colors.DARKVIOLET);
        put("deeppink",             Colors.DEEPPINK);
        put("deepskyblue",          Colors.DEEPSKYBLUE);
        put("dimgray",              Colors.DIMGRAY);
        put("dimgrey",              Colors.DIMGREY);
        put("dodgerblue",           Colors.DODGERBLUE);
        put("firebrick",            Colors.FIREBRICK);
        put("floralwhite",          Colors.FLORALWHITE);
        put("forestgreen",          Colors.FORESTGREEN);
        put("fuchsia",              Colors.FUCHSIA);
        put("gainsboro",            Colors.GAINSBORO);
        put("ghostwhite",           Colors.GHOSTWHITE);
        put("gold",                 Colors.GOLD);
        put("goldenrod",            Colors.GOLDENROD);
        put("gray",                 Colors.GRAY);
        put("green",                Colors.GREEN);
        put("greenyellow",          Colors.GREENYELLOW);
        put("grey",                 Colors.GREY);
        put("honeydew",             Colors.HONEYDEW);
        put("hotpink",              Colors.HOTPINK);
        put("indianred",            Colors.INDIANRED);
        put("indigo",               Colors.INDIGO);
        put("ivory",                Colors.IVORY);
        put("khaki",                Colors.KHAKI);
        put("lavender",             Colors.LAVENDER);
        put("lavenderblush",        Colors.LAVENDERBLUSH);
        put("lawngreen",            Colors.LAWNGREEN);
        put("lemonchiffon",         Colors.LEMONCHIFFON);
        put("lightblue",            Colors.LIGHTBLUE);
        put("lightcoral",           Colors.LIGHTCORAL);
        put("lightcyan",            Colors.LIGHTCYAN);
        put("lightgoldenrodyellow", Colors.LIGHTGOLDENRODYELLOW);
        put("lightgray",            Colors.LIGHTGRAY);
        put("lightgreen",           Colors.LIGHTGREEN);
        put("lightgrey",            Colors.LIGHTGREY);
        put("lightpink",            Colors.LIGHTPINK);
        put("lightsalmon",          Colors.LIGHTSALMON);
        put("lightseagreen",        Colors.LIGHTSEAGREEN);
        put("lightskyblue",         Colors.LIGHTSKYBLUE);
        put("lightslategray",       Colors.LIGHTSLATEGRAY);
        put("lightslategrey",       Colors.LIGHTSLATEGREY);
        put("lightsteelblue",       Colors.LIGHTSTEELBLUE);
        put("lightyellow",          Colors.LIGHTYELLOW);
        put("lime",                 Colors.LIME);
        put("limegreen",            Colors.LIMEGREEN);
        put("linen",                Colors.LINEN);
        put("magenta",              Colors.MAGENTA);
        put("maroon",               Colors.MAROON);
        put("mediumaquamarine",     Colors.MEDIUMAQUAMARINE);
        put("mediumblue",           Colors.MEDIUMBLUE);
        put("mediumorchid",         Colors.MEDIUMORCHID);
        put("mediumpurple",         Colors.MEDIUMPURPLE);
        put("mediumseagreen",       Colors.MEDIUMSEAGREEN);
        put("mediumslateblue",      Colors.MEDIUMSLATEBLUE);
        put("mediumspringgreen",    Colors.MEDIUMSPRINGGREEN);
        put("mediumturquoise",      Colors.MEDIUMTURQUOISE);
        put("mediumvioletred",      Colors.MEDIUMVIOLETRED);
        put("midnightblue",         Colors.MIDNIGHTBLUE);
        put("mintcream",            Colors.MINTCREAM);
        put("mistyrose",            Colors.MISTYROSE);
        put("moccasin",             Colors.MOCCASIN);
        put("navajowhite",          Colors.NAVAJOWHITE);
        put("navy",                 Colors.NAVY);
        put("oldlace",              Colors.OLDLACE);
        put("olive",                Colors.OLIVE);
        put("olivedrab",            Colors.OLIVEDRAB);
        put("orange",               Colors.ORANGE);
        put("orangered",            Colors.ORANGERED);
        put("orchid",               Colors.ORCHID);
        put("palegoldenrod",        Colors.PALEGOLDENROD);
        put("palegreen",            Colors.PALEGREEN);
        put("paleturquoise",        Colors.PALETURQUOISE);
        put("palevioletred",        Colors.PALEVIOLETRED);
        put("papayawhip",           Colors.PAPAYAWHIP);
        put("peachpuff",            Colors.PEACHPUFF);
        put("peru",                 Colors.PERU);
        put("pink",                 Colors.PINK);
        put("plum",                 Colors.PLUM);
        put("powderblue",           Colors.POWDERBLUE);
        put("purple",               Colors.PURPLE);
        put("red",                  Colors.RED);
        put("rosybrown",            Colors.ROSYBROWN);
        put("royalblue",            Colors.ROYALBLUE);
        put("saddlebrown",          Colors.SADDLEBROWN);
        put("salmon",               Colors.SALMON);
        put("sandybrown",           Colors.SANDYBROWN);
        put("seagreen",             Colors.SEAGREEN);
        put("seashell",             Colors.SEASHELL);
        put("sienna",               Colors.SIENNA);
        put("silver",               Colors.SILVER);
        put("skyblue",              Colors.SKYBLUE);
        put("slateblue",            Colors.SLATEBLUE);
        put("slategray",            Colors.SLATEGRAY);
        put("slategrey",            Colors.SLATEGREY);
        put("snow",                 Colors.SNOW);
        put("springgreen",          Colors.SPRINGGREEN);
        put("steelblue",            Colors.STEELBLUE);
        put("tan",                  Colors.TAN);
        put("teal",                 Colors.TEAL);
        put("thistle",              Colors.THISTLE);
        put("tomato",               Colors.TOMATO);
        put("transparent",          Colors.TRANSPARENT);
        put("turquoise",            Colors.TURQUOISE);
        put("violet",               Colors.VIOLET);
        put("wheat",                Colors.WHEAT);
        put("white",                Colors.WHITE);
        put("whitesmoke",           Colors.WHITESMOKE);
        put("yellow",               Colors.YELLOW);
        put("yellowgreen",          Colors.YELLOWGREEN);
    }};

    public static double[] HSBtoRGB(double hue, double saturation, double brightness) {
        // normalize the hue
        double normalizedHue = ((hue % 360) + 360) % 360;
        hue = normalizedHue/360;

        double r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = brightness;
        } else {
            double h = (hue - Math.floor(hue)) * 6.0;
            double f = h - java.lang.Math.floor(h);
            double p = brightness * (1.0 - saturation);
            double q = brightness * (1.0 - saturation * f);
            double t = brightness * (1.0 - (saturation * (1.0 - f)));
            switch ((int) h) {
                case 0:
                    r = brightness;
                    g = t;
                    b = p;
                    break;
                case 1:
                    r = q;
                    g = brightness;
                    b = p;
                    break;
                case 2:
                    r = p;
                    g = brightness;
                    b = t;
                    break;
                case 3:
                    r = p;
                    g = q;
                    b = brightness;
                    break;
                case 4:
                    r = t;
                    g = p;
                    b = brightness;
                    break;
                case 5:
                    r = brightness;
                    g = p;
                    b = q;
                    break;
            }
        }
        double[] f = new double[3];
        f[0] = r;
        f[1] = g;
        f[2] = b;
        return f;
    }

    public static double[] RGBtoHSB(double r, double g, double b) {
        double hue, saturation, brightness;
        double[] hsbvals = new double[3];
        double cmax = (r > g) ? r : g;
        if (b > cmax) cmax = b;
        double cmin = (r < g) ? r : g;
        if (b < cmin) cmin = b;

        brightness = cmax;
        if (cmax != 0)
            saturation = (double) (cmax - cmin) / cmax;
        else
            saturation = 0;

        if (saturation == 0) {
            hue = 0;
        } else {
            double redc = (cmax - r) / (cmax - cmin);
            double greenc = (cmax - g) / (cmax - cmin);
            double bluec = (cmax - b) / (cmax - cmin);
            if (r == cmax)
                hue = bluec - greenc;
            else if (g == cmax)
                hue = 2.0 + redc - bluec;
            else
                hue = 4.0 + greenc - redc;
            hue = hue / 6.0;
            if (hue < 0)
                hue = hue + 1.0;
        }
        hsbvals[0] = hue * 360;
        hsbvals[1] = saturation;
        hsbvals[2] = brightness;
        return hsbvals;
    }

}
