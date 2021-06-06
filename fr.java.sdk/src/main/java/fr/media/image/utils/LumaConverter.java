package fr.media.image.utils;

// see https://stackoverflow.com/questions/596216/formula-to-determine-brightness-of-rgb-color
public interface LumaConverter {

	public static double convert(double _red, double _green, double _blue) {
		return Digital_ITU_BT_601(_red, _green, _blue);
	}
	
	public static double Photometric_ITU_BT_709(double _red, double _green, double _blue) {
		return 0.2126 * _red + 0.7152 * _green + 0.0722 * _blue;
	}
	public static double Digital_ITU_BT_601(double _red, double _green, double _blue) {
		return 0.299 * _red + 0.587 * _green + 0.114 * _blue;
	}
	public static double HSP_Color_Model(double _red, double _green, double _blue) {
		return Math.sqrt(0.299 * _red*_red + 0.587 * _green*_green + 0.114 * _blue*_blue);
	}

}
