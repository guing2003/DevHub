package com.delecrode.devhub.data.mapper

import com.delecrode.devhub.data.model.LanguagesDto
import com.delecrode.devhub.data.model.RepoDetailDto
import com.delecrode.devhub.data.model.ReposDto
import com.delecrode.devhub.domain.model.Languages
import com.delecrode.devhub.domain.model.RepoDetail
import com.delecrode.devhub.domain.model.Repos
fun LanguagesDto.toLanguagesDomain(): Languages {
    return Languages(
        languages = this.keys.toList()
    )
}



fun ReposDto.toReposDomain(): Repos {
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

fun RepoDetailDto.toRepoDetailDomain(): RepoDetail {
    return RepoDetail(
        name = name,
        html_url = html_url,
        description = description ?: "",
        branches_url = branches_url,
        tags_url = tags_url,
        created_at = created_at,
        updated_at = updated_at,
        pushed_at = pushed_at,
        clone_url = clone_url,
        size = size,
        language = language,
        forks_count = forks_count,
        default_branch = default_branch,
        subscribers_count = subscribers_count
    )
}