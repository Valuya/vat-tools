/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.valuya.vattools;

import eu.europa.ec.taxud.vies.services.checkvat.CheckVatPortType;
import eu.europa.ec.taxud.vies.services.checkvat.CheckVatService;
import jakarta.xml.ws.Holder;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Yannick
 */
public class VatChecker {

    public static VatSyntaxStatus checkVatSyntaxStatus(String countryCode, String vatNr) {
        if (countryCode == null) {
            return VatSyntaxStatus.INVALID;
        }
        String trimmedCountryCode = countryCode.trim();
        if (trimmedCountryCode.isEmpty()) {
            return VatSyntaxStatus.INVALID;
        }
        if (vatNr == null) {
            return VatSyntaxStatus.INVALID;
        }
        String trimmedVatNr = vatNr.trim();
        if (trimmedVatNr.isEmpty()) {
            return VatSyntaxStatus.INVALID;
        }
        try {
            VatSyntaxChecker vatSyntaxChecker;
            switch (trimmedCountryCode) {
                case "BE":
                    vatSyntaxChecker = new BEVatSyntaxChecker();
                    break;
                case "FR":
                    vatSyntaxChecker = new FRVatSyntaxChecker();
                    break;
                case "":
                case "NA":
                    vatSyntaxChecker = new NAVatSyntaxChecker();
                    break;
                default:
                    return VatSyntaxStatus.UNKNOWN;
            }
            return vatSyntaxChecker.checkVatSyntaxStatus(vatNr);
        } catch (NumberFormatException numberFormatException) {
            return VatSyntaxStatus.INVALID;
        }
    }

    public static VatInformation getVatInformation(String countryCode, String vatNr) {
        if (countryCode == null || countryCode.isEmpty() || vatNr == null || vatNr.isEmpty()) {
            return null;
        }
        CheckVatService checkVatService = new CheckVatService();
        CheckVatPortType checkVatPortType = checkVatService.getCheckVatPort(
        );

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
