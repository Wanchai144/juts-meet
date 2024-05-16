package com.example.myapplication


enum class OPTION_MEET_CLICK(val menuName: String) {
    MENU_SHARE_SCREEN(menuName = "screen share"),
    MENU_SOUND(menuName = "sound audio"),
    MENU_PERMISSION(menuName = "permissions"),
    MENU_DEBUG(menuName = "debug menu")
}

val menuName = arrayListOf(
    OPTION_MEET_CLICK.MENU_SHARE_SCREEN.menuName,
    OPTION_MEET_CLICK.MENU_SOUND.menuName,
    OPTION_MEET_CLICK.MENU_PERMISSION.menuName,
    OPTION_MEET_CLICK.MENU_DEBUG.menuName
)


val menuIcon = arrayListOf(
    R.drawable.baseline_cast_24,
    R.drawable.volume_up_48px,
    R.drawable.account_cancel_outline,
    R.drawable.dots_horizontal_circle_outline
)
