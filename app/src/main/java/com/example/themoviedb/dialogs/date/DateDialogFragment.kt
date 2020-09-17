package com.example.themoviedb.dialogs.date

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.themoviedb.R
import com.example.themoviedb.dialogs.BaseDialogFragment
import com.example.themoviedb.fragments.home.DateFilter
import com.example.themoviedb.fragments.home.SharedHomeViewModel
import com.example.themoviedb.util.DateTextWatcher
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class DateDialogFragment : BaseDialogFragment() {

    private val sharedHomeViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[SharedHomeViewModel::class.java]
    }

    @Inject
    lateinit var dateFormat: SimpleDateFormat

    private var lteEditTxt: EditText? = null
    private var gteEditTxt: EditText? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            val view = layoutInflater.inflate(R.layout.dialog_fragment_date, null)
            lteEditTxt = view.findViewById(R.id.lteEditTxt)
            gteEditTxt = view.findViewById(R.id.gteEditTxt)
            val textWatcher = DateTextWatcher()
            lteEditTxt?.addTextChangedListener(textWatcher)
            gteEditTxt?.addTextChangedListener(textWatcher)

            val builder = AlertDialog.Builder(it)
                .setTitle(R.string.date_dialog_fragment_title)
                .setView(view)
                .setPositiveButton(R.string.filter) { _, _ ->
                    var gte: Date? = null
                    try {
                        gte = dateFormat.parse(gteEditTxt?.text.toString())
                    } catch (throwable: Throwable) {
                    }
                    try {
                        val lte = dateFormat.parse(lteEditTxt?.text.toString())!!
                        sharedHomeViewModel.postDateFilter(DateFilter(gte, lte))
                    } catch (throwable: Throwable) {
                    }
                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lteEditTxt?.text?.clear()
        lteEditTxt?.text?.append(dateFormat.format(Date()))
        sharedHomeViewModel.singleEventDateFilter.observe(this) { singleEvent ->
            lteEditTxt?.text?.clear()
            lteEditTxt?.text?.append(dateFormat.format(singleEvent.value.lte))
            singleEvent.value.gte?.let {
                gteEditTxt?.text?.clear()
                gteEditTxt?.text?.append(dateFormat.format(it))
            }
        }
    }

    override fun onDestroyView() {
        gteEditTxt = null
        lteEditTxt = null
        super.onDestroyView()
    }
}