package com.expensetracker.spikes.s04

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class AlertRedactorTest {
    @Test
    fun `keeps amount date and merchant`() {
        val text = "Rs.4,500.00 spent on HDFC Bank Card XX1234 at SWIGGY on 05-10-2026 14:32."
        assertEquals(text, AlertRedactor.redact(text))
    }

    @Test
    fun `keeps rupee symbol and inr amounts`() {
        assertEquals("INR 1,23,456.78 debited", AlertRedactor.redact("INR 1,23,456.78 debited"))
        assertEquals("Paid ₹250 to BigBasket", AlertRedactor.redact("Paid ₹250 to BigBasket"))
    }

    @Test
    fun `masks the code in an otp message but not the amount`() {
        assertEquals("XXXXXX is your OTP for login", AlertRedactor.redact("123456 is your OTP for login"))
        assertEquals("OTP XXXXXX to pay Rs.1,299.00 at AMAZON", AlertRedactor.redact("OTP 884213 to pay Rs.1,299.00 at AMAZON"))
    }

    @Test
    fun `short numbers outside an otp message stay`() {
        assertEquals("Txn 4821 at shop", AlertRedactor.redact("Txn 4821 at shop"))
    }

    @Test
    fun `long numbers keep the last four digits`() {
        assertEquals("A/c XXXXXXXXXX6789 debited", AlertRedactor.redact("A/c 50100123456789 debited"))
        assertEquals("Card XXXXXXXXXXXX1111 used", AlertRedactor.redact("Card 4111111111111111 used"))
    }

    @Test
    fun `mobile numbers are fully masked`() {
        assertEquals("Call XXXXXXXXXX now", AlertRedactor.redact("Call 9876543210 now"))
        assertEquals("Call XXXXXXXXXX now", AlertRedactor.redact("Call +91 98765 43210 now"))
        assertEquals("Call XXXXXXXXXX now", AlertRedactor.redact("Call +919876543210 now"))
    }

    @Test
    fun `upi handle email and names`() {
        assertEquals("Sent to REDACTED@upi via UPI", AlertRedactor.redact("Sent to ravi.k@okhdfcbank via UPI"))
        assertEquals("Statement to redacted@example.com", AlertRedactor.redact("Statement to ravi.kumar@gmail.com"))
        assertEquals("Dear CUSTOMER, Rs.50 debited", AlertRedactor.redact("Dear Ravi Kumar, Rs.50 debited"))
        assertEquals("Mr. CUSTOMER paid Rs.50", AlertRedactor.redact("Mr. Ravi Kumar paid Rs.50"))
    }

    @Test
    fun `upi handle at the end of a sentence is still masked`() {
        assertEquals("sent to REDACTED@upi.", AlertRedactor.redact("sent to a.b@ybl."))
    }

    @Test
    fun `redacting twice changes nothing more`() {
        val once = AlertRedactor.redact("Dear Asha, OTP 1234 for A/c 50100123456789 call 9876543210 a.b@ybl")
        assertEquals(once, AlertRedactor.redact(once))
        assertFalse(once.contains("Asha"))
    }
}