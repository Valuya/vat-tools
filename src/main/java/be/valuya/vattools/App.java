package be.valuya.vattools;

import java.util.Date;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        System.out.println(VatChecker.isVatSyntaxValid("BE", "0845914036"));
        VatInformation vatInformation = VatChecker.getVatInformation("BE", "0845914036");
        boolean valid = vatInformation.isValid();
        String countryCode = vatInformation.getCountryCode();
        String vatNumber = vatInformation.getVatNumber();
        String name = vatInformation.getName();
        String address = vatInformation.getAddress();
        Date requestDate = vatInformation.getRequestDate();
        System.out.println(valid);
        System.out.println(name);
        System.out.println(address);
        System.out.println(countryCode + vatNumber);
        System.out.println(requestDate);
        System.out.println(VatChecker.isVatSyntaxValid("FR", "83404833048"));
    }
}
