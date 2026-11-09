// Los plugins se declaran aquí sin aplicarlos; cada módulo aplica los que necesita.
// Kotlin viene integrado en AGP 9, por eso no se declara org.jetbrains.kotlin.android.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
}
