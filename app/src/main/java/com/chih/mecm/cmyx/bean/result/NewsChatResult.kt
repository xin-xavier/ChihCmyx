package com.chih.mecm.cmyx.bean.result


import com.google.gson.annotations.SerializedName

data class NewsChatResult(
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("dataList")
    val dataList: List<NewsChatDataListItem>?
)


data class NewsChatDataListItem(
    @SerializedName("send_time")
    val sendTime: String = "",
    @SerializedName("unread")
    val unread: Int = 0,
    @SerializedName("mid")
    val mid: Int = 0,
    @SerializedName("last_message")
    val lastMessage: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("mname")
    val mName: String = "",
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("mavatar")
    val mAvatar: String = ""
)


