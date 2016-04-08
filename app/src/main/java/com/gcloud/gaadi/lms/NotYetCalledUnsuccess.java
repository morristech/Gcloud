package com.gcloud.gaadi.lms;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gcloud.gaadi.R;
import com.gcloud.gaadi.constants.Constants;
import com.gcloud.gaadi.model.LeadData;
import com.gcloud.gaadi.utils.CommonUtils;

/**
 * Created by Gaurav on 22-06-2015.
 */
public class NotYetCalledUnsuccess extends DialogFragment implements View.OnClickListener, DialogInterface.OnKeyListener {

    private static final String TAG = "NotYetCalledUnsuccess";
    private LeadData leadData;
    private Activity activity;
    private LMSInterface listener;
    private int nextKey = Constants.OUTSIDE_TOUCHED;

    private Activity getFragmentActivity() {
        return activity;
    }

    public static NotYetCalledUnsuccess getInstance(Bundle data, Activity activity, LMSInterface listener) {
        NotYetCalledUnsuccess fragment = new NotYetCalledUnsuccess();
        fragment.leadData = (LeadData) data.get(Constants.MODEL_DATA);
        fragment.activity = activity;
        fragment.listener = listener;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getFragmentActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogLayout = inflater.inflate(R.layout.lms_not_yet_called_unsuccess, null);
        ((TextView) dialogLayout.findViewById(R.id.dialog_title)).setText(CommonUtils.getReplacementString(getFragmentActivity(),
                R.string.notYetCalled_callUnsuccess,
                leadData.getName()));
        dialogLayout.findViewById(R.id.callBack).setOnClickListener(this);
        dialogLayout.findViewById(R.id.markClosed).setOnClickListener(this);
        AlertDialog dialog = new AlertDialog.Builder(getFragmentActivity())
//                .setTitle(CommonUtils.getReplacementString(getFragmentActivity(), R.string.notYetCalled_callUnsuccess, leadData.getName()))
                .setView(dialogLayout)
                .create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.AnimationDialog;
        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        listener.onFragmentDismiss(nextKey);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.callBack:
                nextKey = 1;
                dismiss();
                break;

            case R.id.markClosed:
                nextKey = Constants.MARK_CLOSED;
                dismiss();
                break;
        }
    }

    @Override
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_BACK) {
            nextKey = Constants.BACK_PRESSED;
            dismiss();
            return true;
        } else {
            return false;
        }
    }
}
