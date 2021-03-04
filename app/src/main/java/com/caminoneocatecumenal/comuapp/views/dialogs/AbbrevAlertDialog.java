package com.caminoneocatecumenal.comuapp.views.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;

import com.caminoneocatecumenal.comuapp.databinding.AlertDialogAbbrevBinding;
import com.caminoneocatecumenal.comuapp.views.adapters.AbbrevAlertDialogAdapter;

import java.util.ArrayList;
import java.util.List;


public class AbbrevAlertDialog extends AlertDialog.Builder implements DialogInterface {

    private boolean isCancelable = true;
    private boolean isCanceledOnTouchOutside = true;
    private View view= null;

    private View.OnClickListener onCloseClick = null;

    private AlertDialogAbbrevBinding binding = null;



    private AlertDialog alertDialog = null;


    public AbbrevAlertDialog(Context context) {
        super(context);
    }


    @Override
    public AlertDialog create() {


        super.setCancelable(isCancelable);

        if (view != null) {
            super.setView(view);
        }

        alertDialog = super.create();
        if(alertDialog.getWindow()!= null)
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        return alertDialog;

    }

    @Override
    public AlertDialog show() {
        return super.show();
    }

    @Override
    public void cancel() {
        if(this.alertDialog.isShowing())
        this.alertDialog.cancel();
    }

    @Override
    public void dismiss() {
        if(this.alertDialog.isShowing())
            this.alertDialog.dismiss();
    }

    public boolean isShowing(){
        if (this.alertDialog != null) return this.alertDialog.isShowing();
        else return false;
    }

    public AlertDialogAbbrevBinding getBinding() {
        return binding;
    }


    public static class Builder {

        private Context context;
        private AbbrevAlertDialogAdapter abbrevAlertDialogAdapter;

        public Builder(Context context) {
            this.context = context;
        }

        private View.OnClickListener onCloseClick = null;
        private List<String> readingList = new ArrayList<>();


        private View mView = null;
        private AlertDialogAbbrevBinding mBinding = null;

        private boolean mIsCancelable = true;
        private boolean mIsCanceledOnTouchOutside = true;



        public AbbrevAlertDialog build(){

            AbbrevAlertDialog alert = new AbbrevAlertDialog(context);


            if (this.mBinding != null){
                mBinding.buttonCloseOrange.setOnClickListener(view1 -> {
                    onCloseClick.onClick(view1);
                    alert.dismiss();
                });
                mBinding.buttonCloseWhite.setOnClickListener(view1 -> {
                    onCloseClick.onClick(view1);
                    alert.dismiss();
                });
                abbrevAlertDialogAdapter = new AbbrevAlertDialogAdapter();
                mBinding.rvReadings.setAdapter(abbrevAlertDialogAdapter);
                abbrevAlertDialogAdapter.submitList(readingList);
                mBinding.option2.setOnClickListener(view1 -> {
                    mBinding.llOther.setVisibility(View.VISIBLE);
                    mBinding.llBb.setVisibility(View.GONE);
                });
                mBinding.option1.setOnClickListener(view1 -> {
                    mBinding.llBb.setVisibility(View.VISIBLE);
                    mBinding.llOther.setVisibility(View.GONE);
                });
            }

            alert.isCancelable = mIsCancelable;
            alert.isCanceledOnTouchOutside = mIsCanceledOnTouchOutside;

            alert.view = mView;

            if (this.mBinding != null)
                alert.binding = mBinding;

            return alert;
        }

        public Builder setOnCloseClick(@NonNull View.OnClickListener onClick) {
            this.onCloseClick = onClick;
            return this;
        }

        public Builder setView(View view) {
            this.mView = view;
            mBinding = DataBindingUtil.bind(this.mView);
            return this;
        }

        public Builder setView(@LayoutRes int idLayout) {
            if (context instanceof Activity) this.mView = ((Activity)context ).getLayoutInflater().inflate(idLayout, null);
            mBinding = DataBindingUtil.bind(this.mView);
            return this;
        }

        public Builder setCancelable(boolean isCancellable) {
            this.mIsCancelable = isCancellable;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean isCanceledOnTouchOutside) {
            this.mIsCanceledOnTouchOutside = isCanceledOnTouchOutside;
            return this;
        }

        public Builder setReadingList(List<String> readingList){
            this.readingList = readingList;
            return this;
        }

    }
}
