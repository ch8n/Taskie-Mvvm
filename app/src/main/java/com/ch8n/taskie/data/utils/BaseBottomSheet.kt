package com.ch8n.taskie.data.utils

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ch8n.taskie.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// checkout my view binding article to know in detail how viewbinding work
// https://chetangupta.net/viewbinding/
abstract class ViewBindingBottomSheet<VB : ViewBinding> : BaseBottomSheet() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    abstract fun setup()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


abstract class BaseBottomSheet() : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogThemeNoFloating)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as? BottomSheetDialog
        bottomSheetDialog?.setOnShowListener { dialog ->
            val bottomSheet = dialog as? BottomSheetDialog
            val bottomSheetView = bottomSheet?.findViewById<View>(R.id.design_bottom_sheet)
            if (bottomSheetView != null) {
                BottomSheetBehavior.from(bottomSheetView).state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return bottomSheetDialog as Dialog
    }

    protected var isOperationSuccess: Boolean = false

}
