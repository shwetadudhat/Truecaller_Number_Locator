package com.yi.truecaller.number.locator.name.finder.Base;


import android.app.Fragment;
import android.os.Environment;

import com.yi.truecaller.number.locator.name.finder.R;

import java.io.File;

public class BaseFragment extends Fragment
{
    public String getAppFolderPath()
    {
        File mydir = new File(Environment.getExternalStorageDirectory(), getResources().getString(R.string.app_name));
        if (!mydir.exists())
        {
            mydir.mkdirs();
        }

        return mydir.getAbsolutePath();
    }
}
