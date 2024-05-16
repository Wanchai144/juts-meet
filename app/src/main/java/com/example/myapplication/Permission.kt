package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

fun AppCompatActivity.requestNeededPermissions(onHasPermissions: () -> Unit) {
    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { grants ->
            var hasDenied = false
            // Check if any permissions weren't granted.
            for (grant in grants.entries) {
                if (!grant.value) {
                    Toast.makeText(this, "Missing permission: ${grant.key}", Toast.LENGTH_SHORT)
                        .show()

                    hasDenied = true
                }
            }

            if (!hasDenied) {
                onHasPermissions()
            }
        }

    // Assemble the needed permissions to request
    val neededPermissions = listOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA)
        .filter { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_DENIED }
        .toTypedArray()

    if (neededPermissions.isNotEmpty()) {
        requestPermissionLauncher.launch(neededPermissions)
    } else {
        onHasPermissions()
    }
}

fun AppCompatActivity.requestNeededPermissionsCalendar(onHasPermissions: () -> Unit) {
    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { grants ->
            var hasDenied = false
            // Check if any permissions weren't granted.
            for (grant in grants.entries) {
                if (!grant.value) {
                    Toast.makeText(this, "Missing permission: ${grant.key}", Toast.LENGTH_SHORT)
                        .show()

                    hasDenied = true
                }
            }

            if (!hasDenied) {
                onHasPermissions()
            }
        }

    // Assemble the needed permissions to request
    val neededPermissions = listOf(
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.GET_ACCOUNTS
    )
        .filter { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_DENIED }
        .toTypedArray()

    if (neededPermissions.isNotEmpty()) {
        requestPermissionLauncher.launch(neededPermissions)
    } else {
        onHasPermissions()
    }
}