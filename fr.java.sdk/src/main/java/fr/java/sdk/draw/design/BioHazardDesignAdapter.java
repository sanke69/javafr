package fr.java.sdk.draw.design;

import java.util.function.BiFunction;

import fr.java.draw.Drawable;
import fr.java.draw.Drawer;
import fr.java.draw.tools.Brush;
import fr.java.draw.tools.Color;
import fr.java.draw.tools.Colors;

public class BioHazardDesignAdapter implements Drawable {
	public static final Brush BIOHAZARD_BACKGROUND_BRUSH = Brush.of(1.0, Colors.BLACK, Colors.BLACK);
	public static final Brush BIOHAZARD_FOREGROUND_BRUSH = Brush.of(1.0, Colors.YELLOW, Colors.YELLOW);

	Brush backgroundBrush = BIOHAZARD_BACKGROUND_BRUSH;
	Brush foregroundBrush = BIOHAZARD_FOREGROUND_BRUSH;

	double x, y, dim;

	public BioHazardDesignAdapter(double _dim) {
		this(0, 0, _dim);
	}
	public BioHazardDesignAdapter(double _x, double _y, double _dim) {
		super();
		
		x   = _x;
		y   = _y;
		dim = _dim;
	}

	public void setColors(Color _background, Color _foreground) {
		backgroundBrush = Brush.of(1.0, _background, _background);
		foregroundBrush = Brush.of(1.0, _foreground, _foreground);
	}
	public void setBrushes(Brush _background, Brush _foreground) {
		backgroundBrush = _background;
		foregroundBrush = _foreground;
	}
	
	@Override
	public void draw(Drawer _drawer) {
		_drawer.drawRectangle(0, 0, dim, dim, Colors.TRANSPARENT);

		double square_dim  = dim;

		double sign_height = Math.sqrt(3) / 2 * square_dim;
		double sign_y      = ( square_dim - sign_height ) / 2; 

		double sign_xs[]   = { 0, square_dim / 2, square_dim, 0 };
		double sign_ys[]   = { square_dim - sign_y, sign_y, square_dim - sign_y, square_dim - sign_y };

//		_drawer.drawRectangle(0, 0, square_dim, square_dim);
		_drawer.drawPolygon(sign_xs, sign_ys, foregroundBrush);

		double bio_x   = square_dim / 2;
		double bio_y   = Math.sqrt(4) / 3 * square_dim;
		double bio_dim = 0.3  * square_dim;
		double bii_dim = 0.85 * bio_dim;

		double Px =  0, COx =  0, CIx =  0;
		double Py = .25*square_dim, COy = .165*square_dim, CIy = .2*square_dim;
		for(int i = 0; i < 360; i += 120) {
			final double cos_theta = Math.cos(Math.PI * i / 180);
			final double sin_theta = Math.sin(Math.PI * i / 180);

			final BiFunction<Double, Double, Double> rotX = (X, Y) -> bio_x + X * cos_theta - Y * sin_theta;
			final BiFunction<Double, Double, Double> rotY = (X, Y) -> bio_y + X * sin_theta - Y * cos_theta;

			double p_x  = rotX.apply( Px,   Py);
			double p_y  = rotY.apply( Px,   Py);
			double c0_x = rotX.apply(COx,  COy);
			double c0_y = rotY.apply(COx,  COy);
			double c1_x = rotX.apply(CIx,  CIy);
			double c1_y = rotY.apply(CIx,  CIy);
			double r0_x = rotX.apply( 0d,  0d);
			double r0_y = rotY.apply( 0d,  0d);
			double r1_x = rotX.apply( 0d, .1*square_dim);
			double r1_y = rotY.apply( 0d, .1*square_dim);

			_drawer.drawEllipse(c0_x - bio_dim/2 , c0_y - bio_dim/2, bio_dim, bio_dim, backgroundBrush);
			_drawer.drawEllipse(c1_x - bii_dim/2 , c1_y - bii_dim/2, bii_dim, bii_dim, foregroundBrush);
			_drawer.drawPoint(p_x, p_y, 2, Colors.GREEN);
			_drawer.drawLine(r0_x, r0_y, r1_x, r1_y, 6, foregroundBrush.getPaint().toColor());
		}
		_drawer.drawEllipse(bio_x-.045*square_dim, bio_y-.045*square_dim, .09*square_dim, .09*square_dim, foregroundBrush);

		double commonx, commony, commonh, commonw;
		for (int i = (int) (0.25*square_dim); i <= (int) (0.31*square_dim); i+=3) {
			commonx = bio_x - i / 2;
			commony = bio_y - i / 2;
			commonh = commonw = i * 1;
			_drawer.drawArc(commonx, commony, commonh, commonw,  60d, 60d, Drawer.ArcMode.OPEN, backgroundBrush);
			_drawer.drawArc(commonx, commony, commonh, commonw, 180d, 60d, Drawer.ArcMode.OPEN, backgroundBrush);
			_drawer.drawArc(commonx, commony, commonh, commonw, 300d, 60d, Drawer.ArcMode.OPEN, backgroundBrush);
		}
	}

}
