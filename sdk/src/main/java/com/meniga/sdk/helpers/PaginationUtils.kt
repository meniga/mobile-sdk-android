@file:JvmName("PaginationUtils")

package com.meniga.sdk.helpers

internal fun toSkipAndTake(page: Int, itemsPerPage: Int): Pair<Int, Int> {
    require(page >= 0) { "page should be >= 0 but $page provided" }
    require(itemsPerPage >= 0) { "itemsPerPage should be >= 0 but $itemsPerPage provided" }
    return Pair(if (page == 0) 0 else itemsPerPage / page, itemsPerPage)
}

internal fun hasMoreData(totalCount: Int, page: Int, itemsPerPage: Int): Boolean {
    require(totalCount >= 0) { "totalCount should be >= 0 but $totalCount provided" }
    require(page >= 0) { "page should be >= 0 but $page provided" }
    require(itemsPerPage >= 0) { "itemsPerPage should be >= 0 but $itemsPerPage provided" }
    return (page + 1) * itemsPerPage < totalCount
}
