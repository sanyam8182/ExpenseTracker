package com.expensetracker.spikes.s04

import org.junit.Assert.assertEquals
import org.junit.Test

class CapturePolicyTest {
    private val allowed = setOf("com.expensetracker.spikes", "com.android.shell")

    @Test
    fun `allowlisted package is captured with text`() {
        assertEquals(Capture.Text, CapturePolicy.decide("com.android.shell", allowed))
    }

    @Test
    fun `any other package is recorded by name only`() {
        assertEquals(Capture.PackageOnly, CapturePolicy.decide("org.telegram.messenger", allowed))
    }

    @Test
    fun `a package that only contains an allowed name is not allowed`() {
        assertEquals(Capture.PackageOnly, CapturePolicy.decide("com.android.shell.evil", allowed))
    }

    @Test
    fun `empty allowlist captures no text at all`() {
        assertEquals(Capture.PackageOnly, CapturePolicy.decide("com.android.shell", emptySet()))
    }

    @Test
    fun `defaults allow only this app and the adb shell`() {
        assertEquals(
            setOf("com.expensetracker.spikes", "com.android.shell"),
            CapturePolicy.defaults("com.expensetracker.spikes"),
        )
    }
}