package swimmingpool.co.uk.jesmondswimmingpool.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;



public class DrawableUtils    {

	/**
	 * 创建一个图片
	 * @param contentColor 内部填充颜色
	 * @param strokeColor  描边颜色
	 * @param radius       圆角
	 */
	public static GradientDrawable createDrawable(int contentColor, int strokeColor, int radius) {
		GradientDrawable drawable = new GradientDrawable(); // 生成Shape
		drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT); // 设置矩形
		drawable.setColor(contentColor);// 内容区域的颜色
		drawable.setStroke(1, strokeColor); // 四周描边,描边后四角真正为圆角，不会出现黑色阴影。如果父窗体是可以滑动的，需要把父View设置setScrollCache(false)
		drawable.setCornerRadius(radius); // 设置四角都为圆角
		return drawable;
	}

	/**
	 * 创建一个图片选择器
	 * @param normalState  普通状态的图片
	 * @param pressedState 按压状态的图片
	 */
	public static StateListDrawable createSelector(Drawable normalState, Drawable pressedState) {
		StateListDrawable bg = new StateListDrawable();
		bg.addState(new int[]{android.R.attr.state_activated}, pressedState);
		bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressedState);
		bg.addState(new int[]{android.R.attr.state_enabled}, normalState);
		bg.addState(new int[]{}, normalState);
		return bg;
	}

	/** 获取图片的大小 */
	public static int getDrawableSize(Drawable drawable) {
		if (drawable == null) {
			return 0;
		}
		Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			return bitmap.getByteCount();
		} else {
			return bitmap.getRowBytes() * bitmap.getHeight();
		}
	}


	public static Bitmap DrawableToBitmap(Drawable drawable){

		return  ((BitmapDrawable) drawable).getBitmap();
	}

	public static   Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = bitmap.getWidth() / 2;

		paint.setAntiAlias(true);
		//画背景为透明
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		//画圆
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		//设置模式为叠加以后取交集,并且去上面的
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		//画图
		canvas.drawBitmap(bitmap, rect, rect, paint);
		bitmap.recycle();
		return output;
	}

	/**
	 * 获得压缩后的bitmap
	 * @param uri
	 * @return
	 */
	public static  Bitmap getCompressBitmap(Uri uri){
		BitmapFactory.Options options=new BitmapFactory.Options();

		options.inJustDecodeBounds=true;
		try {
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			BitmapFactory.decodeStream(App.context.getContentResolver().openInputStream(uri), null, options);
			if(options.outWidth>1920||options.outHeight>1920){
				options.inSampleSize=4;
			}
			int quality=100;
			options.inJustDecodeBounds=false;
			Bitmap src = BitmapFactory.decodeStream(App.context.getContentResolver().openInputStream(uri), null, options);
			src.compress(Bitmap.CompressFormat.JPEG, quality, baos);

			//只要图片的大小大于512KB
			while (baos.toByteArray().length/1024>512){
				baos.reset();
				quality-=1;
				src.compress(Bitmap.CompressFormat.JPEG, quality, baos);
			}
			Bitmap bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));
			return bitmap;
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 获得压缩后的bitmap
	 * @param uri
	 * @return
	 */
	public static  Bitmap getSuperCompressBitmap(Uri uri){
		BitmapFactory.Options options=new BitmapFactory.Options();

		options.inJustDecodeBounds=true;
		try {
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			BitmapFactory.decodeStream(App.context.getContentResolver().openInputStream(uri), null, options);
			if(options.outWidth>1920||options.outHeight>1920){
				options.inSampleSize=5;
			}
			int quality=95;
			options.inJustDecodeBounds=false;
			Bitmap src = BitmapFactory.decodeStream(App.context.getContentResolver().openInputStream(uri), null, options);
			src.compress(Bitmap.CompressFormat.JPEG, quality, baos);

			//只要图片的大小大于512KB
			while (baos.toByteArray().length/1024>128){
				baos.reset();
				quality-=3;
				src.compress(Bitmap.CompressFormat.JPEG, quality, baos);
			}
			Bitmap bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));
			return bitmap;
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return null;
	}


}
