package fr.java.lang.enums;

public enum Resolution {
	SXGA			(1280, 1024, ScreenFormat.Q_5_4),
	QSXGA			(2560, 2048, ScreenFormat.Q_5_4),

	QVGA			( 320,  240, ScreenFormat.Q_4_3),
	SIF				( 384,  288, ScreenFormat.Q_4_3),
	VGA				( 640,  480, ScreenFormat.Q_4_3),
	PAL_Star		( 768,  576, ScreenFormat.Q_4_3),
	SVGA			( 800,  600, ScreenFormat.Q_4_3),
	XGA				(1024,  768, ScreenFormat.Q_4_3),
	XGA_Plus		(1152,  864, ScreenFormat.Q_4_3),
	No_Name			(1280,  960, ScreenFormat.Q_4_3),
	SXGA_Plus		(1400, 1050, ScreenFormat.Q_4_3),
	No_Name_2		(1440, 1080, ScreenFormat.Q_4_3),
	UXGA			(1600, 1200, ScreenFormat.Q_4_3),
	QXGA			(2048, 1536, ScreenFormat.Q_4_3),

	HVGA			( 480,  320, ScreenFormat.Q_3_2),
	No_Name_32		(1152,  768, ScreenFormat.Q_3_2),
	No_Name_32_2	(1280,  854, ScreenFormat.Q_3_2),
	No_Name_32_3	(1440,  960, ScreenFormat.Q_3_2),

	CGA				( 320,  200, ScreenFormat.Q_8_5),
	WXGA			(1280,  800, ScreenFormat.Q_8_5),
	No_Name_85		(1440,  900, ScreenFormat.Q_8_5),
	WSXGA_Plus		(1680, 1050, ScreenFormat.Q_8_5),
	WUXGA			(1920, 1200, ScreenFormat.Q_8_5),
	WQXGA			(2560, 1600, ScreenFormat.Q_8_5),

	WVGA			( 800,  480, ScreenFormat.Q_5_3), 
	WXGA_Star		(1280,  768, ScreenFormat.Q_5_3),

	WVGA_Star		( 854,  480, ScreenFormat.Q_16_9),
	PAL				(1024,  576, ScreenFormat.Q_16_9),
	HD720			(1280,  720, ScreenFormat.Q_16_9),		
	No_Name_169		(1366,  768, ScreenFormat.Q_16_9),
	No_Name_169_2	(1600,  900, ScreenFormat.Q_16_9),
	HD1080			(1920, 1080, ScreenFormat.Q_16_9),
	WQHD			(2560, 1440, ScreenFormat.Q_16_9),
	UHD_1			(3840, 2160, ScreenFormat.Q_16_9),
	
	TWO_K			(2048, 1080, ScreenFormat.Q_17_9),

	UWUXGA			(2560, 1080, ScreenFormat.Q_21_9),

	EGA				( 640,  350, ScreenFormat.Q_UnDef),

	WXGAAlt			(1280,  800),
	WSVGA			(1024,  600);

	public enum ScreenFormat {
		Q_21_9, Q_17_9, Q_16_9, Q_5_3, Q_8_5, Q_3_2, Q_4_3, Q_5_4, Q_UnDef;
	}

	ScreenFormat format;
	int          width, height;

	Resolution(int _width, int _height) {
		format = ScreenFormat.Q_UnDef;
		width  = _width;
		height = _height;
	}
	Resolution(int _width, int _height, ScreenFormat _sf) {
		format = _sf;
		width  = _width;
		height = _height;
	}
	
	public int horizontalResolution() {
		return width;
	}
	public int verticalResolution() {
		return height;
	}
	public int pixelResolution() {
		return width * height;
	}
	public double format() {
		return width / height;
	}
	
	public String toString() {
		return width + "x" + height;
	}

}
