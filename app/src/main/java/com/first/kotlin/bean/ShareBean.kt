package com.first.kotlin.bean

import com.first.kotlin.R

class ShareBean {
    private var imgRes: Int = R.mipmap.ic_launcher
    private var imgName: String = ""

    constructor(imgRes: Int, imgName: String) {
        this.imgRes = imgRes
        this.imgName = imgName
    }

    fun getImgRes(): Int {
        return imgRes
    }

    fun getImgName(): String {
        return imgName
    }
}