import unittest

from redact_alert import redact


class AmountsAndMerchantsSurvive(unittest.TestCase):
    def test_keeps_amount_date_and_merchant(self):
        text = "Rs.4,500.00 spent on HDFC Bank Card XX1234 at SWIGGY on 05-10-2026 14:32."
        self.assertEqual(redact(text), text)

    def test_keeps_inr_and_rupee_symbol_amounts(self):
        self.assertEqual(redact("INR 1,23,456.78 debited"), "INR 1,23,456.78 debited")
        self.assertEqual(redact("Paid ₹250 to BigBasket"), "Paid ₹250 to BigBasket")

    def test_keeps_already_masked_card_suffix(self):
        self.assertEqual(redact("Card ending 4321 used"), "Card ending 4321 used")


class OneTimePasswords(unittest.TestCase):
    def test_masks_every_digit_of_the_code(self):
        self.assertEqual(redact("123456 is your OTP for login"), "XXXXXX is your OTP for login")

    def test_masks_code_after_the_word_otp(self):
        self.assertEqual(redact("Your OTP is 4821. Do not share."), "Your OTP is XXXX. Do not share.")

    def test_keeps_the_amount_in_an_otp_message(self):
        out = redact("OTP 884213 to pay Rs.1,299.00 at AMAZON")
        self.assertEqual(out, "OTP XXXXXX to pay Rs.1,299.00 at AMAZON")

    def test_non_otp_message_keeps_short_numbers(self):
        self.assertEqual(redact("Txn 4821 at shop"), "Txn 4821 at shop")


class LongNumbers(unittest.TestCase):
    def test_account_number_keeps_last_four(self):
        self.assertEqual(redact("A/c 50100123456789 debited"), "A/c XXXXXXXXXX6789 debited")

    def test_sixteen_digit_card_keeps_last_four(self):
        self.assertEqual(redact("Card 4111111111111111 used"), "Card XXXXXXXXXXXX1111 used")

    def test_mobile_number_fully_masked(self):
        self.assertEqual(redact("Call 9876543210 now"), "Call XXXXXXXXXX now")

    def test_mobile_with_country_code_fully_masked(self):
        self.assertEqual(redact("Call +91 98765 43210 now"), "Call XXXXXXXXXX now")
        self.assertEqual(redact("Call +919876543210 now"), "Call XXXXXXXXXX now")


class Identities(unittest.TestCase):
    def test_upi_handle(self):
        self.assertEqual(redact("Sent to ravi.k@okhdfcbank via UPI"), "Sent to REDACTED@upi via UPI")

    def test_email(self):
        self.assertEqual(redact("Statement to ravi.kumar@gmail.com"), "Statement to redacted@example.com")

    def test_greeting_name(self):
        self.assertEqual(redact("Dear Ravi Kumar, Rs.50 debited"), "Dear CUSTOMER, Rs.50 debited")

    def test_title_name(self):
        self.assertEqual(redact("Mr. Ravi Kumar paid Rs.50"), "Mr. CUSTOMER paid Rs.50")


class Report(unittest.TestCase):
    def test_counts_what_it_changed(self):
        from redact_alert import redact_with_report

        text, report = redact_with_report("Dear Asha, OTP 1234 for A/c 50100123456789")
        self.assertEqual(report["name"], 1)
        self.assertEqual(report["otp"], 1)
        self.assertEqual(report["long_number"], 1)
        self.assertNotIn("1234", text)
        self.assertNotIn("Asha", text)


if __name__ == "__main__":
    unittest.main()
