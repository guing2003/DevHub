package com.delecrode.devhub.data.mapper

import com.delecrode.devhub.data.model.ReposDto
import com.delecrode.devhub.domain.model.Repos

fun ReposDto.toReposDomain(): Repos{
    return Repos(
        id = id,
        node_id = node_id,
        name = name,
        full_name = full_name,
        private = private,
        description = description,
        url = url,
        created_at = created_at,
        updated_at = updated_at,
        pushed_at = pushed_at,
        clone_url = clone_url,
    )
}