/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.valuya.vattools;

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Yannick
 */
public class VatCheckerTest {

    private static final String COUNTRY_CODE_BE = "BE";
    private static final String VAT_BE_SYNTAX_OK_VALID = "0845914036";
    private static final String VAT_BE_SYNTAX_KO1 = "845914036";
    private static final String VAT_BE_SYNTAX_KO2 = "0845914037";
    private static final String COUNTRY_CODE_FR = "FR";
    private static final String VAT_FR_SYNTAX_OK_INVALID = "83404833048";
    private static final String VAT_FR_SYNTAX_KO = "73404833048";

    /**
     * Test of isVatSyntaxValid method, of class VatChecker.
     */
    @Test
    public void testIsVatSyntaxValid() {
        boolean goodVatBe1Valid = VatChecker.isVatSyntaxValid(COUNTRY_CODE_BE, VAT_BE_SYNTAX_OK_VALID);
        Assert.assertTrue(goodVatBe1Valid);
        boolean wrongVatBe1Valid = VatChecker.isVatSyntaxValid(COUNTRY_CODE_BE, VAT_BE_SYNTAX_KO1);
        Assert.assertFalse(wrongVatBe1Valid);
        boolean wrongVatBe2Valid = VatChecker.isVatSyntaxValid(COUNTRY_CODE_BE, VAT_BE_SYNTAX_KO2);
        Assert.assertFalse(wrongVatBe2Valid);
        boolean goodVatFr1Valid = VatChecker.isVatSyntaxValid(COUNTRY_CODE_FR, VAT_FR_SYNTAX_OK_INVALID);
        Assert.assertTrue(goodVatFr1Valid);
        boolean wrongVatFr1Valid = VatChecker.isVatSyntaxValid(COUNTRY_CODE_FR, VAT_FR_SYNTAX_KO);
        Assert.assertFalse(wrongVatFr1Valid);
    }

    @Test
    public void testGetVatInformationBE() {
        VatInformation vatInformation = VatChecker.getVatInformation(COUNTRY_CODE_BE, VAT_BE_SYNTAX_OK_VALID);
        String countryCode = vatInformation.getCountryCode();
        String vatNumber = vatInformation.getVatNumber();
        String name = vatInformation.getName();
        String address = vatInformation.getAddress();
        boolean valid = vatInformation.isValid();
        Assert.assertTrue(valid);
        Assert.assertEquals(countryCode, COUNTRY_CODE_BE);
        Assert.assertEquals(vatNumber, VAT_BE_SYNTAX_OK_VALID);
        Assert.assertEquals("SPRL VALUYA", name);
        Assert.assertEquals("RUE HAUTE 19\n5140  SOMBREFFE(LIGNY)", address);
    }

    @Test
    public void testGetVatInformationFR() {
        VatInformation vatInformation = VatChecker.getVatInformation(COUNTRY_CODE_BE, VAT_FR_SYNTAX_OK_INVALID);
        boolean valid = vatInformation.isValid();
        Assert.assertFalse(valid);
    }
}
