/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.valuya.vattools;

/**
 *
 * @author Yannick
 */
public class NAVatSyntaxChecker implements VatSyntaxChecker {

    @Override
    public VatSyntaxStatus checkVatSyntaxStatus(String vatNr) {
        return VatSyntaxStatus.VALID;
    }
}
