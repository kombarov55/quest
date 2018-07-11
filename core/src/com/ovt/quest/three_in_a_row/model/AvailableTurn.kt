package com.ovt.quest.three_in_a_row.model

data class AvailableTurn(val toSwap: Pair<Item, Item>, val group: List<Item>)