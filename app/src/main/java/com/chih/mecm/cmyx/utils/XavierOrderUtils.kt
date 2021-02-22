package com.chih.mecm.cmyx.utils

import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.bean.result.OrderListResultSectionEntity
import com.chih.mecm.cmyx.utils.extend.dp
import com.chih.mecm.cmyx.utils.extend.value

object XavierOrderUtils : View.OnClickListener {

    private val primaryColor = R.color.main_orange.value()
    private val secondaryColor = R.color.textColor.value()

    private val primaryDrawable =
        MaterialShapeDrawableUtils.getShapeDrawable(13f, R.color.grey_900_alpha_100)
    private val secondaryDrawable =
        MaterialShapeDrawableUtils.strokeShapeDrawable(13f, R.color.grey_600_alpha_100)

    private val primaryStyle = OrderActionStyleEntity(primaryColor, primaryDrawable)
    private val secondaryStyle = OrderActionStyleEntity(secondaryColor, secondaryDrawable)

    private val actionStyleMap = mapOf(
        "去付款" to primaryStyle,
        "提取卡密" to primaryStyle,
        "确认收货" to primaryStyle,
        "查看卡密" to primaryStyle,
        "评价" to primaryStyle,

        "取消订单" to secondaryStyle,
        "查看物流" to secondaryStyle,
        "删除订单" to secondaryStyle
    )

    fun pointOrderStatus(status: Int): String {
        return when (status) {
            0 -> "买家未付款"
            1 -> "买家已付款"
            2, 8 -> "卖家已发货"
            3, 5 -> "交易完成"
            6 -> "取消订单"
            24 -> "交易关闭"
            else -> ""
        }
    }

    fun pointCommodityStatus(status: Int): String {
        return when (status) {
            0 -> "待付款"
            1 -> "待发货"
            2 -> "待收货"
            3 -> "待评价"
            4 -> "申请换货"
            5 -> "已完成"
            6 -> "取消订单"
            7 -> "申请退款"
            8 -> "已查看卡密"
            9 -> "同意退款"
            10 -> "拒绝退款"
            11 -> "申请退款退货"
            12 -> "退款中"
            13 -> "退款成功"
            14 -> "拒绝退款"
            15 -> "撤销退款"
            16 -> "换货中"
            17 -> "换货成功"
            18 -> "拒绝换货"
            19 -> "撤销换货"
            20 -> "退货退款中"
            21 -> "退货退款成功"
            22 -> "拒绝退货退款"
            23 -> "撤销退货退款"
            24 -> "交易关闭"
            else -> ""
        }
    }

    fun pointOrderAction(entity: OrderListResultSectionEntity, linearLayout: LinearLayout) {
        val shop = entity.shop
        val shippingMethod = shop.shippingMethod
        when (shop.status) {
            0 -> {
                addAction(entity, linearLayout, "去付款", "取消订单")
            }
            2 -> {
                val strings = arrayListOf<String>()
                strings.add(
                    if (shippingMethod == 2) {
                        "提取卡密"
                    } else {
                        "确认收货"
                    }
                )
                if (shippingMethod == 0) {
                    strings.add("确认收货")
                }
                addAction(entity, linearLayout, *strings.toTypedArray())
            }
            3 -> {
                if (shippingMethod == 2) {
                    addAction(entity, linearLayout, "评价", "查看卡密", "删除订单")
                } else {
                    addAction(entity, linearLayout, "评价", "删除订单")
                }
            }
            5 -> {
                if (shippingMethod == 2) {
                    addAction(entity, linearLayout, "查看卡密", "删除订单")
                } else {
                    addAction(entity, linearLayout, "删除订单")
                }
            }
            8 -> {
                addAction(entity, linearLayout, "查看卡密", "确认收货")
            }
            else -> {
            }
        }
    }

    private fun addAction(
        entity: OrderListResultSectionEntity,
        linearLayout: LinearLayout,
        vararg texts: String
    ) {
        linearLayout.removeAllViews()
        for (text in texts) {
            val context = linearLayout.context
            val button = Button(context)
            button.id = View.generateViewId()
            button.gravity = Gravity.CENTER
            button.setPadding(0, 0, 0, 0)
            button.text = text
            button.textSize = 12f
            val actionStyle = actionStyleMap.getOrElse(text) {
                primaryStyle
            }
            actionStyle.entity=entity
            button.setTextColor(actionStyle.textColor)
            button.background = actionStyle.drawable
            linearLayout.addView(button)
            button.layoutParams.width = 65f.dp().toInt()
            button.layoutParams.height = 22f.dp().toInt()
            XavierViewUtils.setMarginStart(button)
            button.setOnClickListener(this)
        }
    }

    data class OrderActionStyleEntity(
        val textColor: Int,
        val drawable: Drawable,
        var entity: OrderListResultSectionEntity? = null
    )

    override fun onClick(view: View?) {
        view?.let { v ->
            if (v is Button) {
                val text = v.text.toString()
                val actionStyle = actionStyleMap.getOrElse(text) {
                    primaryStyle
                }
                val entity = actionStyle.entity
                when (text) {
                    "去付款" -> {
                    }
                    "提取卡密" -> {
                    }
                    "确认收货" -> {
                    }
                    "查看卡密" -> {
                    }
                    "评价" -> {
                    }
                    "取消订单" -> {
                    }
                    "查看物流" -> {
                    }
                    "删除订单" -> {
                    }
                    else -> {
                    }
                }
            }
        }
    }

}