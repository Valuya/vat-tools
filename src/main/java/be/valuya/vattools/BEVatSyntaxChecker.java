/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.valuya.vattools;

/**
 *
 * @author Yannick
 */
public class BEVatSyntaxChecker implements VatSyntaxChecker {

    @Override
    public boolean isVatSyntaxValid(String vatNr) {
        if (vatNr.length() != 10) {
            return false;
        }
        String firstDigitsStr = vatNr.substring(0, 8);
        String moduloStr = vatNr.substring(8, 10);
        Long vatNrLong = Long.valueOf(firstDigitsStr);
        Long moduloLong = Long.valueOf(moduloStr);
        Long expectedModuloLong = 97 - vatNrLong % 97;
        return moduloLong.equals(expectedModuloLong);
    }
}
