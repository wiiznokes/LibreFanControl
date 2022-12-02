package model

interface BaseItem {
    var name: String
    val index: Int

    var value: Int
    val id: String
    val globalType: String
    val specifyType: String
}