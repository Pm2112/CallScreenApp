package com.example.callscreenapp.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.example.callscreenapp.R
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory

@SuppressLint("InflateParams")
class RateAppDialog(context: Context, private val activity: Activity) {
    private val dialog: Dialog = Dialog(context)
    private lateinit var reviewManager: ReviewManager

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_rate, null)
        dialog.setContentView(view)
        dialog.setCancelable(false)  // Ngăn người dùng hủy bỏ dialog bằng cách chạm bên ngoài
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val starOne: ImageView = view.findViewById(R.id.star_one)
        val starTwo: ImageView = view.findViewById(R.id.star_two)
        val starThree: ImageView = view.findViewById(R.id.star_three)
        val starFour: ImageView = view.findViewById(R.id.star_four)
        val starFive: ImageView = view.findViewById(R.id.star_five)
        val btnLater: CardView = view.findViewById(R.id.btn_later)
        val btnRate: CardView = view.findViewById(R.id.btn_rate)

        reviewManager = ReviewManagerFactory.create(context)

        starOne.setOnClickListener {
            starOne.isSelected = true
            starTwo.isSelected = false
            starThree.isSelected = false
            starFour.isSelected = false
            starFive.isSelected = false
        }

        starTwo.setOnClickListener {
            starOne.isSelected = true
            starTwo.isSelected = true
            starThree.isSelected = false
            starFour.isSelected = false
            starFive.isSelected = false
        }

        starThree.setOnClickListener {
            starOne.isSelected = true
            starTwo.isSelected = true
            starThree.isSelected = true
            starFour.isSelected = false
            starFive.isSelected = false
        }

        starFour.setOnClickListener {
            starOne.isSelected = true
            starTwo.isSelected = true
            starThree.isSelected = true
            starFour.isSelected = true
            starFive.isSelected = false
        }

        starFive.setOnClickListener {
            starOne.isSelected = true
            starTwo.isSelected = true
            starThree.isSelected = true
            starFour.isSelected = true
            starFive.isSelected = true
        }

        btnLater.setOnClickListener {
            dismiss()
        }

        btnRate.setOnClickListener {
            showReviewDialog()
            dismiss()
        }
    }

    fun show() {
        dialog.show()
    }

    private fun dismiss() {
        dialog.dismiss()
    }

    private fun showReviewDialog() {
        val request = reviewManager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                val flow = reviewManager.launchReviewFlow(activity, reviewInfo)
                flow.addOnCompleteListener { _ ->
                    // Đánh giá đã hoàn tất hoặc bị hủy
                }
            } else {
                // Xử lý lỗi khi yêu cầu đánh giá không thành công
            }
        }
    }
}
