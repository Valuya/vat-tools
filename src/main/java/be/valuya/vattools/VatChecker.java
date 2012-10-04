/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.valuya.vattools;

import eu.europa.ec.taxud.vies.services.checkvat.CheckVatPortType;
import eu.europa.ec.taxud.vies.services.checkvat.CheckVatService;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

/**
 *
 * @author Yannick
 */
public class VatChecker {

    public static boolean isVatSyntaxValid(String countryCode, String vatNr) {
        if (countryCode == null) {
            return false;
        }
        String trimmedCountryCode = countryCode.trim();
        if (trimmedCountryCode.isEmpty()) {
            return false;
        }
        if (vatNr == null) {
            return false;
        }
        String trimmedVatNr = vatNr.trim();
        if (trimmedVatNr.isEmpty()) {
            return false;
        }
        VatSyntaxChecker vatSyntaxChecker;
        switch (countryCode) {
            case "BE":
                vatSyntaxChecker = new BEVatSyntaxChecker();
                break;
            case "FR":
                vatSyntaxChecker = new FRVatSyntaxChecker();
                break;
            default:
                throw new IllegalStateException();
        }
        return vatSyntaxChecker.isVatSyntaxValid(vatNr);
    }

    public static VatInformation getVatInformation(String countryCode, String vatNr) {
        CheckVatService checkVatService = new CheckVatService();
        CheckVatPortType checkVatPortType = checkVatService.getCheckVatPort();
        Holder<String> holderCountryCode = new Holder<>(countryCode);
        Holder<String> holderVatNumber = new Holder<>(vatNr);
        Holder<XMLGregorianCalendar> holderRequestDate = new Holder<>();
        Holder<Boolean> holderValid = new Holder<>(true);
        Holder<String> holderName = new Holder<>();
        Holder<String> holderAddress = new Holder<>();
        checkVatPortType.checkVat(holderCountryCode, holderVatNumber, holderRequestDate, holderValid, holderName, holderAddress);
        VatInformation vatInformation = new VatInformation();
        vatInformation.setAddress(holderAddress.value);
        vatInformation.setCountryCode(holderCountryCode.value);
        vatInformation.setName(holderName.value);
        vatInformation.setValid(holderValid.value);
        vatInformation.setVatNumber(holderVatNumber.value);
        vatInformation.setRequestDate(holderRequestDate.value.toGregorianCalendar().getTime());
        return vatInformation;
    }
}
