package com.tangle.payrollapp.service;

import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.RequestOptions;
import com.stripe.param.*;
import com.stripe.Stripe;
import org.springframework.stereotype.Service;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountUpdateParams;
import com.stripe.param.PayoutCreateParams;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    public StripeService() {

        Stripe.apiKey = "sk_test_51PsksEGeC9xeaPWHFATqTvP2rT2NYt8nzYbzrILsYOQjmzKOTw77cbZrZ1XpW5YO4yE3cSwSRJ8BFTS6EgwmxaMI005RPGa8tz";
    }





    public Account createEmployeeAccount(String employeeEmail) throws StripeException {
        AccountCreateParams params =
                AccountCreateParams.builder()
                        .setCountry("US")
                        .setEmail(employeeEmail)
                        .setBusinessType(AccountCreateParams.BusinessType.INDIVIDUAL)
                        .setCapabilities(
                                AccountCreateParams.Capabilities.builder()
                                        .setCardPayments(
                                                AccountCreateParams.Capabilities.CardPayments.builder()
                                                        .setRequested(true)
                                                        .build()
                                        )
                                        .setTransfers(
                                                AccountCreateParams.Capabilities.Transfers.builder()
                                                        .setRequested(true)
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();

        Account account = Account.create(params);

        // Optionally, you can then retrieve and handle the account’s requirements manually if needed:
        Account retrievedAccount = Account.retrieve(account.getId());
        if (retrievedAccount.getRequirements() != null) {
            // Handle requirements, such as collecting information from the user via your application.
        }

        return account;
    }



    public BankAccount linkBankAccountToEmployee(String employeeAccountId, String routingNumber, String accountNumber) throws StripeException {
        Account account = Account.retrieve(employeeAccountId);

        Map<String, Object> bankAccountParams = new HashMap<>();
        bankAccountParams.put("object", "bank_account");
        bankAccountParams.put("country", "US");
        bankAccountParams.put("currency", "usd");
        bankAccountParams.put("account_holder_name", "Employee Name");
        bankAccountParams.put("routing_number", routingNumber);
        bankAccountParams.put("account_number", accountNumber);

        Map<String, Object> params = new HashMap<>();
        params.put("external_account", bankAccountParams);

        account = account.update(params);

        return (BankAccount) account.getExternalAccounts().retrieve(account.getExternalAccounts().getData().get(0).getId());
    }

    public Payout sendSalaryDirectlyToBank(String employeeAccountId, long amount) throws StripeException {
        Account account = Account.retrieve(employeeAccountId);

        // Check if payouts are enabled
        if (!account.getPayoutsEnabled()) {
            String requirementsMessage = "Payouts are not enabled for this account. Please provide the required information to enable payouts.";
            String errorMessage = account.getRequirements().getErrors().stream()
                    .map(error -> error.getReason())
                    .reduce("", (acc, reason) -> acc + reason + "\n");

            throw new InvalidRequestException(
                    requirementsMessage + "\n" + errorMessage,
                    null, null, null, 0, null
            );
        }

        PayoutCreateParams params = PayoutCreateParams.builder()
                .setAmount(amount) // Salary amount in cents (e.g., 1000 for $10)
                .setCurrency("usd")
                .build();

        RequestOptions requestOptions = RequestOptions.builder()
                .setStripeAccount(employeeAccountId)
                .build();

        return Payout.create(params, requestOptions);
    }

    public Account updateAccountMetadata(String accountId, String key, String value) throws StripeException {
        Account resource = Account.retrieve(accountId);

        AccountUpdateParams params = AccountUpdateParams.builder()
                .putMetadata(key, value)
                .build();

        return resource.update(params);
    }



    public Account updateTermsOfServiceAcceptance(String accountId, long date, String ip) throws StripeException {
        Account resource = Account.retrieve(accountId);

        AccountUpdateParams params = AccountUpdateParams.builder()
                .setTosAcceptance(
                        AccountUpdateParams.TosAcceptance.builder()
                                .setDate(date)
                                .setIp(ip)
                                .build()
                )
                .build();

        return resource.update(params);
    }

    public Account updateAccountRequirements(String accountId) throws StripeException {
        Account account = Account.retrieve(accountId);

        // Setting the requirement_collection parameter
        Map<String, Object> params = new HashMap<>();
        params.put("requirement_collection", "stripe");

        // Updating account with the correct requirement_collection
        return account.update(params);
    }
//    public Person updatePersonInformation(String accountId, String personId, String firstName, String lastName, String dobDay, String dobMonth, String dobYear, String line1, String city, String state, String postalCode) throws StripeException {
//        // Retrieve the person using the person ID and account ID
//        Person person = Person.retrieve(personId, RequestOptions.builder()
//                .setStripeAccount(accountId)
//                .build());
//
//        // Prepare the update parameters
//        PersonUpdateParams.Address address = PersonUpdateParams.Address.builder()
//                .setLine1(line1)
//                .setCity(city)
//                .setState(state)
//                .setPostalCode(postalCode)
//                .setCountry("US")
//                .build();
//
//        PersonUpdateParams params = PersonUpdateParams.builder()
//                .setFirstName(firstName)
//                .setLastName(lastName)
//                .setDob(PersonUpdateParams.Dob.builder()
//                        .setDay(Long.parseLong(dobDay))
//                        .setMonth(Long.parseLong(dobMonth))
//                        .setYear(Long.parseLong(dobYear))
//                        .build())
//                .setAddress(address)
//                .build();
//
//        // Update the person with the new information
//        return person.update(params);
//    }



    public Account updateAccountForPayouts(String accountId, String businessProfileUrl, String mcc, String accountHolderName) throws StripeException {
        AccountUpdateParams params = AccountUpdateParams.builder()
                .setBusinessProfile(AccountUpdateParams.BusinessProfile.builder()
                        .setUrl(businessProfileUrl)
                        .setMcc(mcc)
                        .build())
                .setIndividual(AccountUpdateParams.Individual.builder()
                        .setFirstName(accountHolderName.split(" ")[0])
                        .setLastName(accountHolderName.split(" ")[1])
                        .build())
                .build();

        return Account.retrieve(accountId).update(params);
    }

    public Account checkAccountCapabilities(String accountId) throws StripeException {
        Account account = Account.retrieve(accountId);
        Account.Capabilities capabilities = account.getCapabilities();

        String transfersStatus = capabilities.getTransfers();
        String achPaymentsStatus = capabilities.getUsBankAccountAchPayments();

        System.out.println("Transfers capability status: " + transfersStatus);
        System.out.println("ACH Direct Debit capability status: " + achPaymentsStatus);

        return account;
    }
    public Charge addFundsToBalance(long amount) throws StripeException {
        ChargeCreateParams params = ChargeCreateParams.builder()
                .setAmount(amount) // Amount in cents (e.g., 1000 for $10)
                .setCurrency("usd")
                .setSource("tok_bypassPending") // Using a test token that bypasses pending state
                .build();

        return Charge.create(params);
    }
    public Token createBankAccountToken() throws StripeException {
        TokenCreateParams.BankAccount bankAccount = TokenCreateParams.BankAccount.builder()
                .setCountry("US")
                .setCurrency("usd")
                .setAccountHolderName("Jenny Rosen")
                .setAccountHolderType(TokenCreateParams.BankAccount.AccountHolderType.INDIVIDUAL)
                .setRoutingNumber("110000000")
                .setAccountNumber("000123456789")
                .build();

        TokenCreateParams params = TokenCreateParams.builder()
                .setBankAccount(bankAccount)
                .build();

        return Token.create(params);
    }


    public BankAccount attachBankAccountToCustomer(String customerId, String tokenId) throws StripeException {
        Customer customer = Customer.retrieve(customerId);
        BankAccount bankAccount = (BankAccount) customer.getSources().create(Map.of("source", tokenId));
        return bankAccount;
    }
    public Account createConnectedAccount(String email) throws StripeException {
        AccountCreateParams params = AccountCreateParams.builder()
                .setType(AccountCreateParams.Type.EXPRESS)
                .setCountry("US")
                .setEmail(email)
                .build();

        Account account = Account.create(params);
        return account;
    }




    public Transfer transferToConnectedAccount(String connectedAccountId, long amount) throws StripeException {
        TransferCreateParams params = TransferCreateParams.builder()
                .setAmount(amount)
                .setCurrency("usd")
                .setDestination(connectedAccountId)
                .build();

        return Transfer.create(params);
    }
    // Method to transfer funds to a specific bank account on a connected account
//    public Transfer transferToBankAccount(String connectedAccountId, String bankAccountId, long amount) throws StripeException {
//        TransferCreateParams params = TransferCreateParams.builder()
//                .setAmount(amount) // Amount in cents (e.g., 1000 for $10)
//                .setCurrency("usd")
//                .setDestination(connectedAccountId) // Connected account ID
//                .setDestinationPaymentMethod(bankAccountId) // Specific bank account ID
//                .build();
//
//        return Transfer.create(params);
//    }

    public Payout createPayout(String connectedAccountId, long amount) throws StripeException {
        PayoutCreateParams params = PayoutCreateParams.builder()
                .setAmount(amount)
                .setCurrency("usd")
                .build();


        RequestOptions requestOptions = RequestOptions.builder()
                .setStripeAccount(connectedAccountId)
                .build();


        return Payout.create(params, requestOptions);
    }
    public Account createAccount() throws StripeException {
        AccountCreateParams params = AccountCreateParams.builder()
                .setCountry("US")
                .setEmail("jenny.rosen@example.com")
                .setBusinessType(AccountCreateParams.BusinessType.INDIVIDUAL)
                .setCapabilities(
                        AccountCreateParams.Capabilities.builder()
                                .setCardPayments(
                                        AccountCreateParams.Capabilities.CardPayments.builder()
                                                .setRequested(true)
                                                .build()
                                )
                                .setTransfers(
                                        AccountCreateParams.Capabilities.Transfers.builder()
                                                .setRequested(true)
                                                .build()
                                )
                                .setUsBankAccountAchPayments(
                                        AccountCreateParams.Capabilities.UsBankAccountAchPayments.builder()
                                                .setRequested(true)
                                                .build()
                                )
                                .build()
                )
                .build();

        Account account = Account.create(params);


        return checkAccountCapabilities(account.getId());
    }
}
