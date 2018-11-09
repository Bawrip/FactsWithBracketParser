/*
import org.junit.*;
import taskpack.exceptions.FactsExpectedException;
import taskpack.parser.Facts;
import taskpack.parser.FactsParser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FactsMethodsTest {
    private static Method pRuleToInternalFormat;
    private static Method pAddFacts;
    private static FactsParser factsParser;

    @BeforeClass
    public static void init() {
        factsParser = new FactsParser();
        Class c = FactsParser.class;

        try {
            pRuleToInternalFormat = c.getDeclaredMethod("ruleToInternalFormat", String.class);
            pRuleToInternalFormat.setAccessible(true);
            pAddFacts = c.getDeclaredMethod("addFacts", String.class, Facts.class);
            pAddFacts.setAccessible(true);

        } catch (NoSuchMethodException ex) {
            System.out.println("NoSuchMethodException");
        }
    }

    @AfterClass
    public static void tearDown() {
        pAddFacts = null;
        pRuleToInternalFormat = null;
    }

    @Test
    public void trueRegs() {
        try {
            Assert.assertTrue((boolean) pRuleToInternalFormat.invoke(factsParser,"A||K  ->Y"));
            Assert.assertTrue((boolean) pRuleToInternalFormat.invoke(factsParser,"A&& C&&B||D->T"));
            Assert.assertTrue((boolean) pRuleToInternalFormat.invoke(factsParser,"A&&S||K&&R->  Y"));
            Assert.assertTrue((boolean) pRuleToInternalFormat.invoke(factsParser,"A||K&&O||U->Y"));
            Assert.assertTrue((boolean) pRuleToInternalFormat.invoke(factsParser,"$KERER-> Y "));
            Assert.assertTrue((boolean) pRuleToInternalFormat.invoke(factsParser," _$Ker3 && DER->Y"));
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccessException");
        } catch (InvocationTargetException ex) {
            System.out.println("InvocationTargetException");
        }
    }

    @Test
    public void falseRightPartRegs() {
        try {
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"K->Y&&R"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"A&&C&&B||D->T|||"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"A&&C&&B||D->T||"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"A&&C&&B||D->T|"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"A&&C&&B||D->T&"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"A&&C&&B||D->||T"));
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccessException");
        } catch (InvocationTargetException ex) {
            System.out.println("InvocationTargetException");
        }
    }

    @Test
    public void falseLeftPartRegs() {
        try {
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"A&&C&&B||D||->T"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"||A&&C&&B||D->T"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"|||A&&C&&B||D->T"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"A&&C&&B|||D->T"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"|A&&C&&B||D->T"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"A&|&C&&B||D->T"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"&&A&&C&&B||D->T"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"A&&C&&B||D&&->T"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"A&&C&&B||&&D->T"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"A&&C&&B&&||D->T"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"&&A&&C&&B||D->T"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"&A&&C&&B||D->T"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"&&&A&&C&&B||D->T"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"A&&C&&B||D&&&->T"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"A&&C&& &&B||D->T"));
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccessException");
        } catch (InvocationTargetException ex) {
            System.out.println("InvocationTargetException");
        }
    }


    @Test
    public void falseFullRegs() {
        try {
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"K->Y->"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"A&&C&&B||DT"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"A&&C&&B||D->T->E"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"A&&sd C&&B||D->T->E"));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,""));
            Assert.assertFalse((boolean) pRuleToInternalFormat.invoke(factsParser,"       "));
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccessException");
        } catch (InvocationTargetException ex) {
            System.out.println("InvocationTargetException");
        }
    }

    @Test
    public void trueFactsLine() {
        try {
            Facts facts = new Facts();

            try {
                pAddFacts.invoke(factsParser, " A, b, cadDW", facts);
            } catch (FactsExpectedException ex) {

            }
            Assert.assertTrue((boolean) pAddFacts.invoke(factsParser," A, b, cadDW"));
            Assert.assertTrue((boolean) pAddFacts.invoke(factsParser," A"));
            Assert.assertTrue((boolean) pAddFacts.invoke(factsParser,"$A , f ,  Ert    "));
            Assert.assertTrue((boolean) pAddFacts.invoke(factsParser,"A5s, e_re, R"));
            Assert.assertTrue((boolean) pAddFacts.invoke(factsParser,"  Aasdwr  "));
            Assert.assertTrue((boolean) pAddFacts.invoke(factsParser,"  A,B  "));
            Assert.assertTrue((boolean) pAddFacts.invoke(factsParser," A,   B$"));
            Assert.assertTrue((boolean) pAddFacts.invoke(factsParser,"A, B "));
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccessException");
        } catch (InvocationTargetException ex) {
            System.out.println("InvocationTargetException");
        }
    }

    @Test
    public void falseFactsLine() {
        try {
            Assert.assertFalse((boolean) pAddFacts.invoke(factsParser," Aawde$_34fas "));
            Assert.assertFalse((boolean) pAddFacts.invoke(factsParser,"A, "));
            Assert.assertFalse((boolean) pAddFacts.invoke(factsParser,",A"));
            Assert.assertFalse((boolean) pAddFacts.invoke(factsParser,"3ds"));
            Assert.assertFalse((boolean) pAddFacts.invoke(factsParser,"A+3, B"));
            Assert.assertFalse((boolean) pAddFacts.invoke(factsParser,"A, R  r, w    E"));
            Assert.assertFalse((boolean) pAddFacts.invoke(factsParser,"A, 3Fd"));
            Assert.assertFalse((boolean) pAddFacts.invoke(factsParser,""));
            Assert.assertFalse((boolean) pAddFacts.invoke(factsParser,"       "));
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccessException");
        } catch (InvocationTargetException ex) {
            System.out.println("InvocationTargetException");
        }
    }
}
*/
