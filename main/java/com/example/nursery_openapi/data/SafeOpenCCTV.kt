package com.example.nursery_openapi.data

data class safeOpenCCTV(
    val RESULT: RESULT,
    val list_total_count: Int,
    val row: List<Row>
)