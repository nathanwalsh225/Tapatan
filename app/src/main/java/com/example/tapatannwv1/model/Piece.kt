package com.example.tapatannwv1.model

import android.net.Uri

data class Piece(
    val resourceId: Int? = null,  //default images
    val uri: Uri? = null          //Custom images
)