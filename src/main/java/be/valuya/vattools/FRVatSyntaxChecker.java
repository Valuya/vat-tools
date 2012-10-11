/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.valuya.vattools;

/**
 *
 * @author Yannick
 */
public class FRVatSyntaxChecker implements VatSyntaxChecker {

    @Override
    public VatSyntaxStatus checkVatSyntaxStatus(String vatNr) {
        if (vatNr.length() != 11) {
            return VatSyntaxStatus.INVALID;
        }
        String sirenStr = vatNr.substring(2, 11);
        String moduloStr = vatNr.substring(0, 2);
        Long sirenLong = Long.valueOf(sirenStr);
        Long moduloLong = Long.valueOf(moduloStr);
        Long expectedModuloLong = (12 + 3 * (sirenLong % 97)) % 97;
        if (moduloLong.equals(expectedModuloLong)) {
            return VatSyntaxStatus.VALID;
        }
        return VatSyntaxStatus.INVALID;
    }
}
