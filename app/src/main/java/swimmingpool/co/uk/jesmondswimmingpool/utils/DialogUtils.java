package swimmingpool.co.uk.jesmondswimmingpool.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import swimmingpool.co.uk.jesmondswimmingpool.R;

/**
 * Created by cody on 2017/11/28.
 */

public class DialogUtils {
    public static MaterialDialog processingDialog(Context context,String title,String content){
        MaterialDialog processing = new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .progress(true, 0).cancelable(false).build();

        return processing;
}
    public static MaterialDialog processingDialog(Context context){
        MaterialDialog processing = new MaterialDialog.Builder(context)
                .title("processing")
                .content(R.string.please_wait)
                .progress(true, 0).cancelable(false).build();

        return processing;

}
    public static MaterialDialog showProcessingDialog(Context context){
        return new MaterialDialog.Builder(context)
                .title("processing")
                .content(R.string.please_wait)
                .progress(true, 0).cancelable(false).show();


}


    public static MaterialDialog showDialog(Context context, String title, String content, String positive, String negative,
                                            MaterialDialog.SingleButtonCallback pos, MaterialDialog.SingleButtonCallback neg) {
        MaterialDialog show = new MaterialDialog.Builder(context).title(title).content(content).positiveText(positive)
                .negativeText(negative).onPositive(pos).onNegative(neg).show();
        return show;

    }
    public static MaterialDialog showDialog(Context context, String content, MaterialDialog.SingleButtonCallback pos, MaterialDialog.SingleButtonCallback neg) {
        return showDialog(context,"reminder",content,"YES","CANCEL",pos,neg);
    }
    public static MaterialDialog showDialog(Context context, String content, MaterialDialog.SingleButtonCallback pos) {
        return showDialog(context, "reminder", content, "YES", "CANCEL", pos, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
    }
}
