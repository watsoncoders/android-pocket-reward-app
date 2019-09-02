package com.droidoxy.easymoneyrewards.utils;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.droidoxy.easymoneyrewards.R;
import com.droidoxy.easymoneyrewards.app.App;
import static com.droidoxy.easymoneyrewards.constants.Constants.LICENSE_COPY;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by DroidOXY
 */

public class Dialogs {

    public static void warningDialog(Context ctx, String title, String message, Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText, SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE);

        dialog.setTitleText("\n"+title);
        if(message != null){
            dialog.setContentText(message+"\n");
        }
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if(ShowCancelButton){

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if(ButtonClicklistener != null){

            dialog.setConfirmClickListener(ButtonClicklistener);

        }else{

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();
    }

    public static void succesDialog(Context ctx, String title, String message, Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText, SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE);

        dialog.setTitleText("\n"+title);
        if(message != null){
            dialog.setContentText(message+"\n");
        }
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if(ShowCancelButton){

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if(ButtonClicklistener != null){

            dialog.setConfirmClickListener(ButtonClicklistener);

        }else{

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

    public static void errorDialog(Context ctx, String title, String message, Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText, SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE);

        dialog.setTitleText("\n"+title);
        if(message != null){
            dialog.setContentText(message+"\n");
        }
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if(ShowCancelButton){

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if(ButtonClicklistener != null){

            dialog.setConfirmClickListener(ButtonClicklistener);

        }else{

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

    public static void normalDialog(Context ctx, String title, String message, Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText, SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx);

        dialog.setTitleText("\n"+title);
        if(message != null){
            dialog.setContentText(message+"\n");
        }
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if(ShowCancelButton){

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if(ButtonClicklistener != null){

            dialog.setConfirmClickListener(ButtonClicklistener);

        }else{

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

    public static void processDialog(Context ctx, String title, String message, Boolean Cancellable) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.PROGRESS_TYPE);

        dialog.getProgressHelper().setBarColor(ctx.getResources().getColor(R.color.colorPrimary));
        dialog.setTitleText(title);
        if(message != null){
            dialog.setContentText(message+"\n");
        }
        dialog.setCancelable(Cancellable);

        dialog.show();

    }

    public static void editTextDialog(Context ctx, EditText editText, String message, Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText, SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.NORMAL_TYPE);

        dialog.setTitleText("\n"+message+"\n");
        dialog.setCustomView(editText);
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if(ShowCancelButton){

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if(ButtonClicklistener != null){

            dialog.setConfirmClickListener(ButtonClicklistener);

        }else{

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

    public static void customDialog(Context ctx, View customView, String message, Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText, SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.NORMAL_TYPE);

        dialog.setTitleText("\n"+message+"\n");
        dialog.setCustomView(customView);
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if(ShowCancelButton){

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if(ButtonClicklistener != null){

            dialog.setConfirmClickListener(ButtonClicklistener);

        }else{

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

    public static void serverError(Context ctx, String btnText, SweetAlertDialog.OnSweetClickListener Buttonlistener){

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE);

        dialog.setTitleText("\n"+ctx.getResources().getString(R.string.title_server_problem));
        dialog.setContentText(ctx.getResources().getString(R.string.msg_server_problem)+"\n");
        dialog.setConfirmText(btnText);
        dialog.setCancelable(true);

        if(Buttonlistener != null){

            dialog.setConfirmClickListener(Buttonlistener);

        }else{

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

    public static void validationError(final Context ctx, final Integer Code){

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE);

        if(Code == 699){
            dialog.setTitleText("\n Demo WebPanel URL Detected");
            dialog.setContentText("Change the Server URL to yours or Please purchase a valid WebPanel License from : \n\n WWW.CODYHUB.COM \n");
            dialog.setConfirmText("OK");
        }else{
            dialog.setTitleText("\n License Validation Error");
            dialog.setContentText("Please Activate your License for App Source Code by opening a ticket in our Support Forum : \n\n WWW.DROIDOXY.COM/SUPPORT \n");
            dialog.setConfirmText("ACTIVATE");
            dialog.setCancelButton("PURCHASE", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    AppUtils.parse(ctx, App.getInstance().get("APP_LICENSE_URL","http://www.codyhub.com/item/android-rewards-app-pocket"));
                }
            });
        }

        dialog.setCancelable(false);

        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if(Code == 699){
                    AppUtils.parse(ctx, App.getInstance().get("WEB_LICENSE_URL","http://www.codyhub.com/item/pocket-webpanel"));
                }else{
                    AppUtils.parse(ctx,App.getInstance().get("VENDOR_SUPPORT_URL","http://www.droidoxy.com/support"));
                }
            }
        });

        dialog.show();

    }

}