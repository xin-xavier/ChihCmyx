package com.chih.mecm.cmyx.utils

import com.blankj.utilcode.constant.TimeConstants
import com.blankj.utilcode.util.TimeUtils
import java.util.*


object XavierTimeUtils {

    /**
     * @demand 消息列表和聊天窗口的时间显示昨天时间段分：今天（时间点）、昨天、星期几、年月日 ，四个时间段进行展示。（今天的显示几点几分就可以了）
     *
     */
    fun getTimeSegmentation(oldTime: String): String {
        val oldMills: Long = TimeUtils.string2Millis(oldTime)
        return if (TimeUtils.isToday(oldMills)) { // 判断是否今天
            TimeUtils.millis2String(oldMills, "HH:mm")
        } else {
            val timeSpanByNowDay: Long = TimeUtils.getTimeSpanByNow(oldMills, TimeConstants.DAY)
            if (timeSpanByNowDay == 1L) { // 判断时间差是否为一天
                "昨天"
            } else {
                val timeOfWeekStart: Long = getTimeOfWeekStart()
                if (oldMills >= timeOfWeekStart) { // 判断是否本周
                    TimeUtils.getChineseWeek(oldMills)
                } else {
                    TimeUtils.millis2String(
                        oldMills,
                        "yyyy年MM月dd日"
                    )
                }
            }
        }
    }

    /**
     * 获取本周的起始时间
     *
     * @return
     */
    private fun getTimeOfWeekStart(): Long {
        val ca = Calendar.getInstance()
        ca[Calendar.HOUR_OF_DAY] = 0
        ca.clear(Calendar.MINUTE)
        ca.clear(Calendar.SECOND)
        ca.clear(Calendar.MILLISECOND)
        ca[Calendar.DAY_OF_WEEK] = ca.firstDayOfWeek
        return ca.timeInMillis
    }

}