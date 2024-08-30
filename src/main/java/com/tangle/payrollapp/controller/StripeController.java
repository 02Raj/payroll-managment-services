package com.tangle.payrollapp.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.tangle.payrollapp.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/stripe")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/create-account")
    public ResponseEntity<String> createEmployeeAccount(@RequestParam String email) {
        try {
            Account account = stripeService.createEmployeeAccount(email);
            return ResponseEntity.ok("Account created successfully, Account ID: " + account);
        } catch (StripeException e) {
            return ResponseEntity.status(500).body("Error creating account: " + e.getMessage());
        }
    }

    // Endpoint to link a bank account to the employee's Stripe connected account
    @PostMapping("/link-bank-account")
    public ResponseEntity<String> linkBankAccount(@RequestParam String accountId, @RequestParam String routingNumber, @RequestParam String accountNumber) {
        try {
            BankAccount bankAccount = stripeService.linkBankAccountToEmployee(accountId, routingNumber, accountNumber);
            return ResponseEntity.ok("Bank account linked successfully, Bank Account ID: " + bankAccount.getId());
        } catch (StripeException e) {
            return ResponseEntity.status(500).body("Error linking bank account: " + e.getMessage());
        }
    }


    @PostMapping("/send-salary")
    public ResponseEntity<String> sendSalary(@RequestBody Map<String, Object> payload) {
        try {
            String accountId = (String) payload.get("accountId");
            long amount = Long.parseLong(payload.get("amount").toString());

            Payout payout = stripeService.sendSalaryDirectlyToBank(accountId, amount);
            return ResponseEntity.ok("Payout successful, Payout ID: " + payout);
        } catch (StripeException e) {
            return ResponseEntity.status(500).body("Error during payout: " + e.getMessage());
        }
    }

    // Endpoint to update account for payouts
    @PostMapping("/update-account")
    public ResponseEntity<String> updateAccountForPayouts(@RequestBody Map<String, String> payload) {
        try {
            String accountId = payload.get("accountId");
            String businessProfileUrl = payload.get("businessProfileUrl");
            String mcc = payload.get("mcc");
            String accountHolderName = payload.get("accountHolderName");

            Account account = stripeService.updateAccountForPayouts(accountId, businessProfileUrl, mcc, accountHolderName);
            return ResponseEntity.ok("Account updated successfully, Account ID: " + account.getId());
        } catch (StripeException e) {
            return ResponseEntity.status(500).body("Error updating account: " + e.getMessage());
        }
    }


    @PostMapping("/update-account-metadata")
    public ResponseEntity<String> updateAccountMetadata(@RequestParam String accountId, @RequestParam String key, @RequestParam String value) {
        try {
            Account account = stripeService.updateAccountMetadata(accountId, key, value);
            return ResponseEntity.ok("Account metadata updated successfully, Account ID: " + account.getId());
        } catch (StripeException e) {
            return ResponseEntity.status(500).body("Error updating account metadata: " + e.getMessage());
        }
    }
    @PostMapping("/transfer")
    public ResponseEntity<String> transferToConnectedAccount(@RequestParam String connectedAccountId, @RequestParam long amount) {
        try {
            // Check capabilities before transferring
            stripeService.checkAccountCapabilities(connectedAccountId);

            Transfer transfer = stripeService.transferToConnectedAccount(connectedAccountId, amount);
            return ResponseEntity.ok("Transfer successful, Transfer ID: " + transfer.getId());
        } catch (StripeException e) {
            return ResponseEntity.status(500).body("Error during transfer: " + e.getMessage());
        }
    }

    @PostMapping("/{accountId}/update-terms")
    public ResponseEntity<?> updateTermsOfService(
            @PathVariable String accountId,
            @RequestParam long date,
            @RequestParam String ip) {
        try {
            Account updatedAccount = stripeService.updateTermsOfServiceAcceptance(accountId, date, ip);
            return ResponseEntity.ok(updatedAccount);
        } catch (StripeException e) {
            e.printStackTrace(); // Log the stack trace
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the Terms of Service acceptance: " + e.getMessage());
        }
    }
    @PostMapping("/{accountId}/update-requirements")
    public ResponseEntity<?> updateAccountRequirements(
            @PathVariable String accountId) {
        try {
            Account updatedAccount = stripeService.updateAccountRequirements(accountId);
            return ResponseEntity.ok(updatedAccount);
        } catch (StripeException e) {
            e.printStackTrace(); // Log the stack trace
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the account requirements: " + e.getMessage());
        }
    }
    @PostMapping("/payout")
    public ResponseEntity<String> createPayout(@RequestParam String connectedAccountId, @RequestParam long amount) {
        try {

            stripeService.checkAccountCapabilities(connectedAccountId);

            Payout payout = stripeService.createPayout(connectedAccountId, amount);
            return ResponseEntity.ok("Payout successful, Payout ID: " + payout.getId());
        } catch (StripeException e) {
            return ResponseEntity.status(500).body("Error during payout: " + e.getMessage());
        }
    }
    @PostMapping("/create-bank-account-token")
    public ResponseEntity<String> createBankAccountToken() {
        try {
            Token token = stripeService.createBankAccountToken();
            return ResponseEntity.ok("Bank account token created successfully: " + token.getId());
        } catch (StripeException e) {
            return ResponseEntity.status(500).body("Error creating bank account token: " + e.getMessage());
        }
    }

    @PostMapping("/add-funds")
    public ResponseEntity<String> addFundsToBalance(@RequestParam long amount) {
        try {
            Charge charge = stripeService.addFundsToBalance(amount);
            return ResponseEntity.ok("Funds added successfully, Charge ID: " + charge.getId());
        } catch (StripeException e) {
            return ResponseEntity.status(500).body("Error adding funds: " + e.getMessage());
        }
    }
    @PostMapping("/attach-bank-account")
    public ResponseEntity<String> attachBankAccountToCustomer(@RequestParam String customerId, @RequestParam String tokenId) {
        try {
            BankAccount bankAccount = stripeService.attachBankAccountToCustomer(customerId, tokenId);
            return ResponseEntity.ok("Bank account attached successfully: " + bankAccount.getId());
        } catch (StripeException e) {
            return ResponseEntity.status(500).body("Error attaching bank account: " + e.getMessage());
        }
    }
//    @PostMapping("/create-account")
//    public ResponseEntity<String> createConnectedAccount(@RequestParam String email) {
//        try {
//            Account account = stripeService.createConnectedAccount(email);
//            return ResponseEntity.ok("Account created successfully, Account ID: " + account.getId());
//        } catch (StripeException e) {
//            return ResponseEntity.status(500).body("Error creating account: " + e.getMessage());
//        }
//    }


//    PostMapping("/update-individual")
//    public ResponseEntity<String> updateIndividual(@RequestParam String accountId, @RequestParam String firstName, @RequestParam String lastName,
//                                                   @RequestParam String dobDay, @RequestParam String dobMonth, @RequestParam String dobYear,
//                                                   @RequestParam String line1, @RequestParam String city, @RequestParam String state, @RequestParam String postalCode) {
//        try {
//            Account account = stripeService.updateIndividualInformation(accountId, firstName, lastName, dobDay, dobMonth, dobYear, line1, city, state, postalCode);
//            return ResponseEntity.ok("Individual information updated successfully, Account ID: " + account.getId());
//        } catch (StripeException e) {
//            return ResponseEntity.status(500).body("Error updating individual information: " + e.getMessage());
//        }
//    }

//    @PostMapping("/update-person")
//    public ResponseEntity<String> updatePerson(@RequestParam String accountId, @RequestParam String personId, @RequestParam String firstName,
//                                               @RequestParam String lastName, @RequestParam String dobDay, @RequestParam String dobMonth,
//                                               @RequestParam String dobYear, @RequestParam String line1, @RequestParam String city,
//                                               @RequestParam String state, @RequestParam String postalCode) {
//        try {
//            Person person = stripeService.updatePersonInformation(accountId, personId, firstName, lastName, dobDay, dobMonth, dobYear, line1, city, state, postalCode);
//            return ResponseEntity.ok("Person information updated successfully, Person ID: " + person.getId());
//        } catch (StripeException e) {
//            return ResponseEntity.status(500).body("Error updating person information: " + e.getMessage());
//        }
//    }






}
