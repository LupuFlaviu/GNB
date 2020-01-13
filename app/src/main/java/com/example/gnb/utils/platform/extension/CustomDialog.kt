package com.example.gnb.utils.platform.extension

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.gnb.R
import com.example.gnb.utils.Constants
import com.example.gnb.utils.platform.extension.TwoButtonsDialog.TwoButtonDialogListener

/**
 * Custom dialog that contains Title, Message, Positive button and Negative button
 * @param title the title of the dialog as [String]
 * @param message the message of the dialog
 * @param positiveButton the text of the positive action button of the dialog as [String]
 * @param negativeButton the text of the negative action button of the dialog as [String]
 * @param listener the listener for positive and negative buttons actions as [TwoButtonDialogListener]
 */
class TwoButtonsDialog(
    private val title: String, private val message: String, private val positiveButton: String,
    private val negativeButton: String, private var listener: TwoButtonDialogListener
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title)
            builder.setMessage(message)
                .setPositiveButton(positiveButton) { _, _ ->
                    listener.onTwoButtonsDialogPositiveClick(
                        this
                    )
                }
                .setNegativeButton(negativeButton) { _, _ ->
                    listener.onTwoButtonsDialogNegativeClick(
                        this
                    )
                }
                .setCancelable(false)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /** The activity/fragment that creates an instance of this dialog fragment must implement this interface
     * in order to receive event callbacks. Each method passes the DialogFragment in case the host needs to query it.
     */
    interface TwoButtonDialogListener {
        fun onTwoButtonsDialogNegativeClick(dialog: DialogFragment)
        fun onTwoButtonsDialogPositiveClick(dialog: DialogFragment)
    }
}

/**
 * Extension function for showing an error dialog
 * @param context the application context
 * @param fragmentManager the [FragmentManager] for showing the dialog
 * @param message the message to be shown in the dialog
 * @param listener the object that listens for dialog buttons clicks
 */
fun showErrorDialog(
    context: Context, fragmentManager: FragmentManager, message: String,
    listener: TwoButtonDialogListener
) {
    val dialog = TwoButtonsDialog(
        context.resources.getString(R.string.dialog_title), message,
        context.resources.getString(R.string.dialog_button_retry),
        context.resources.getString(R.string.dialog_button_exit), listener
    )
    dialog.isCancelable = false
    dialog.show(fragmentManager, Constants.TAG_ERROR_DIALOG)
}