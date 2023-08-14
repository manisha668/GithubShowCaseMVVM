package com.example.githubshowcaseapp.dialogs

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.githubshowcaseapp.R
import com.example.githubshowcaseapp.databinding.LayoutCustomDialogBinding

class DetailsViewDialog(
    context: Context,
    hostView: ViewGroup,
    issuesString: String,
    contributorsString: String
) {
    private var mBinding: LayoutCustomDialogBinding? = null
    private var popupWindow: PopupWindow? = null

    init {
        mBinding = LayoutCustomDialogBinding.inflate(
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
            null, false
        )
        (mBinding?.root as View).setPadding(
            context.resources.getDimension(R.dimen.dp_16).toInt(),
            0,
            context.resources.getDimension(R.dimen.dp_16).toInt(),
            0
        )
        popupWindow = PopupWindow(
            (mBinding as LayoutCustomDialogBinding).root,
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            true
        ).apply {
            hostView.post {
                this.showAtLocation(mBinding?.root, Gravity.CENTER, 0, 0)
            }
        }

        mBinding?.tvIssues?.text = issuesString
        mBinding?.tvContributors?.text = contributorsString
        mBinding?.btDialog?.setOnClickListener {
            popupWindow?.dismiss()
        }
    }
}