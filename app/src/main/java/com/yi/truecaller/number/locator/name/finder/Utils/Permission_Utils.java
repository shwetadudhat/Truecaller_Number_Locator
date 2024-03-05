package com.yi.truecaller.number.locator.name.finder.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.yi.truecaller.number.locator.name.finder.R;

public abstract class Permission_Utils {

    public static class PermissionDeniedDialog extends DialogFragment {
        private static final String ARGUMENT_FINISH_ACTIVITY = "finish";
        private boolean FinishActivity = false;

        public static PermissionDeniedDialog newInstance(boolean z) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(ARGUMENT_FINISH_ACTIVITY, z);
            PermissionDeniedDialog permissionDeniedDialog = new PermissionDeniedDialog();
            permissionDeniedDialog.setArguments(bundle);
            return permissionDeniedDialog;
        }

        public Dialog onCreateDialog(Bundle bundle) {
            this.FinishActivity = getArguments().getBoolean(ARGUMENT_FINISH_ACTIVITY);
            return new AlertDialog.Builder(getActivity()).setMessage(R.string.location_permission_denied).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
        }

        public void onDismiss(DialogInterface dialogInterface) {
            super.onDismiss(dialogInterface);
            if (this.FinishActivity) {
                Toast.makeText(getActivity(), R.string.permission_required_toast, 0).show();
                getActivity().finish();
            }
        }
    }

    public static class RationaleDialog extends DialogFragment {
        private static final String ARGUMENT_FINISH_ACTIVITY = "finish";
        private static final String ARGUMENT_PERMISSION_REQUEST_CODE = "requestCode";
        public boolean FinishActivity = false;

        public static RationaleDialog newInstance(int i, boolean z) {
            Bundle bundle = new Bundle();
            bundle.putInt(ARGUMENT_PERMISSION_REQUEST_CODE, i);
            bundle.putBoolean(ARGUMENT_FINISH_ACTIVITY, z);
            RationaleDialog rationaleDialog = new RationaleDialog();
            rationaleDialog.setArguments(bundle);
            return rationaleDialog;
        }

        public Dialog onCreateDialog(Bundle bundle) {
            Bundle arguments = getArguments();
            arguments.getInt(ARGUMENT_PERMISSION_REQUEST_CODE);
            this.FinishActivity = arguments.getBoolean(ARGUMENT_FINISH_ACTIVITY);
            return new AlertDialog.Builder(getActivity()).setMessage(R.string.permission_rationale_location).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(RationaleDialog.this.getActivity(), new String[]{"android.permission.ACCESS_FINE_LOCATION"}, i);
                    RationaleDialog.this.FinishActivity = false;
                }
            }).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).create();
        }

        public void onDismiss(DialogInterface dialogInterface) {
            super.onDismiss(dialogInterface);
            if (this.FinishActivity) {
                Toast.makeText(getActivity(), R.string.permission_required_toast, 0).show();
                getActivity().finish();
            }
        }
    }

    public static void requestPermission(FragmentActivity trafficFinder_Activity, int i, String str, boolean z) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(trafficFinder_Activity, str)) {
            RationaleDialog.newInstance(i, z).show(trafficFinder_Activity.getSupportFragmentManager(), "dialog");
            return;
        }
        ActivityCompat.requestPermissions(trafficFinder_Activity, new String[]{str}, i);
    }

    public static boolean isPermissionGranted(String[] strArr, int[] iArr, String str) {
        int i = 0;
        while (i < strArr.length) {
            if (!str.equals(strArr[i])) {
                i++;
            } else if (iArr[i] == 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}