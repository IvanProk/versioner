package com.ivanprok.versioner.git

import org.eclipse.jgit.lib.ObjectId

data class Tag(
    val name: String,
    val objectId: ObjectId
)
