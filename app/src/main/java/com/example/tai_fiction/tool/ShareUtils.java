package com.example.tai_fiction.tool;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tai_fiction.R;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Tai on 2016/4/5.
 */
public class ShareUtils {
    //微信应用程序APPID
    public static final String APP_ID = "wxe14c1134c293ef0e";
    private IWXAPI api;

    public ShareUtils(Context context){
        api = WXAPIFactory.createWXAPI(context, APP_ID);
        //将APP_ID注册到微信中
        api.registerApp(APP_ID);
    }

    /**
     * 分享文本到微信客户端
     */
    public void SendTextToWX(final Context context) {
        // api.openWXApp();//启动微信客户端
        //创建EditText控件，用于输入文本
        final EditText editText = new EditText(context);
        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setText("默认分享文本");
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("共享文本");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setView(editText);//将EditText控件与对话框绑定
        builder.setMessage("请输入要分享的文本");
        builder.setPositiveButton("分享", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //获取待分享的文本
                String text = editText.getText().toString();
                if (text == null || text.length() == 0) {
                    return;
                }
                //1.初始化创建一个用于分装待分享文本的对象
                WXTextObject textObject = new WXTextObject();
                textObject.text = text;
                //2.创建WXMediaMessage对象，该对象用于Android客户端向微信发送数据
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = textObject;
                msg.description = text;
                //3.创建一个用于请求微信客户端的SendMessageToWX.Req对象
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.message = msg;
                //设置唯一标识
                req.transaction = buildTransaction("text");
                //表示发送给朋友还是朋友圈
                req.scene = SendMessageToWX.Req.WXSceneSession;//朋友
                //req.scene = SendMessageToWX.Req.WXSceneTimeline;//朋友圈
                //4.发送给微信客户端
                Toast.makeText(context, String.valueOf(api.sendReq(req)), Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("取消", null);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    //为分享到微信客户端请求生成一个唯一的标识
    private String buildTransaction(final String type) {
        //时间：毫秒
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 发送二进制格式的图像
     */
    public void SendBinaryImageToWX(final Context context) {
        //1.获取二进制图像的Bitmap对象
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.abv123456);

        //2.创建WXImageObject对象，并包装bitmap
        WXImageObject imageObject = new WXImageObject(bitmap);

        //3.创建WXMediaMessage对象，并包装WXImageObject对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;

        //4.压缩图像
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 80, 100, true);
        bitmap.recycle();//释放图像所占用的内存资源
        msg.thumbData = bmpToByteArray(thumbBmp, true);//设置缩略图

        //5.创建SendMessageToWX.Req对象，用于发送数据
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;//发送到好友

        Toast.makeText(context, String.valueOf(api.sendReq(req)), Toast.LENGTH_LONG).show();
        //finish();
    }

    //将Bitmap转换成byte格式的数组
    private byte[] bmpToByteArray(final Bitmap bitmap, final boolean needRecycle) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int options = 100;//压缩率
        bitmap.compress(Bitmap.CompressFormat.PNG, options, outputStream);

        if (needRecycle) {
            bitmap.recycle();
        }
        byte[] result = outputStream.toByteArray();
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 通过Intent.ACTION_SEND分享
     */
    private void SendDataToWX(final Context context) {


        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        shareIntent.setType("image/*");
        //当用户选择短信时使用sms_body取得文字
        shareIntent.putExtra("sms_body", "短信？");
        shareIntent.setType("text/plain");

        shareIntent.putExtra(Intent.EXTRA_TEXT, "Demo");
        //自定义选择框的标题
        //startActivity(Intent.createChooser(shareIntent, "邀请好友"));
        //系统默认标题
        context.startActivity(shareIntent);
    }
}
