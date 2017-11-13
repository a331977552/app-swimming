package swimmingpool.co.uk.jesmondswimmingpool.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hxh on 2015/12/4.
 */
public class BitmapUtils {
    private static Context context;
    public final float PICTURE_RATIO = 3f / 4f;
    private static BitmapUtils bitmapUtils;

    private BitmapUtils() {
    }

    public static BitmapUtils getInstance() {
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils();
        }
        return bitmapUtils;
    }

    /**
     * 异步保存图片. 并返回图片实例
     *
     * @return
     */
    public Uri SaveToLocal(final Bitmap bitmap) {
        File file = Environment.getExternalStorageDirectory();
        File file2 = new File(file, "ycbpicture/");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd:HHmmss");
        String picname = format.format(new Date());
        picname+=".jpg";
        //如果存在,并且是个文件不是文件夹,则删掉创建文件夹
        if (file2.exists() && file2.isFile()) {
            file2.delete();
            file2.mkdir();
        }  //如果不存在,创建文件夹
        else if (!file2.exists()) {
            file2.mkdir();
        }


        File pic = new File(file2.getAbsolutePath() + "/" + picname);
        if (pic.exists()) {
            pic.delete();
        }
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(pic));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(pic);
        return uri;
    }


    public Bitmap cutBitmap(byte[] data) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, opts);
        int outWidth = opts.outWidth;
        int outHeight = opts.outHeight;
        int photoWidth = outWidth < outHeight ? outWidth : outHeight;
        float photoHeight = photoWidth * PICTURE_RATIO;
        Rect r = new Rect(0, 0, (int) photoHeight, photoWidth);
        return cropImg(r, data);
    }

    private Bitmap cropImg(Rect r, byte[] data) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(bais, false);
            bais.close();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            options.inPreferredConfig= Bitmap.Config.RGB_565;
            Bitmap cropBitmap = decoder.decodeRegion(r, options);
            decoder.recycle();
            Matrix matrix = new Matrix();
            matrix.setRotate(90);
            Bitmap bitmap = Bitmap.createBitmap(cropBitmap, 0, 0, cropBitmap.getWidth(), cropBitmap.getHeight(), matrix, true);
            cropBitmap.recycle();

            int quality=100;
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            options.inJustDecodeBounds=true;
            options.inSampleSize = 1;
            BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length, options);
            if(options.outWidth>1920||options.outHeight>1920){
                options.inSampleSize=4;
            }
            options.inJustDecodeBounds=false;
            bitmap.recycle();
            bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length, options);
            //只要图片的大小大于512KB
            while (baos.toByteArray().length/1024>512){
                baos.reset();
                quality-=1;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            }
            bitmap.recycle();
          return BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }


}
