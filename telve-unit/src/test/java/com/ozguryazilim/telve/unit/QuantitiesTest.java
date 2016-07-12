/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozguryazilim.telve.unit;

import com.ozguryazilim.telve.unit.sets.MassUnitSet;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author oyas
 */
public class QuantitiesTest {
    
    public QuantitiesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    private static final UnitName GRAM = UnitName.of("MASS:GRAM");
    
    /**
     * Test of of method, of class Quantities.
     */
    @Test
    public void testOf_BigDecimal_Unit() {
        System.out.println("of");
        BigDecimal amount = BigDecimal.TEN;
        
        Unit unit = new Unit( GRAM, new Quantity(BigDecimal.ONE, GRAM ));
        Quantity expResult = new Quantity(BigDecimal.TEN, GRAM);
        
        Quantity result = Quantities.of(amount, unit);
        assertEquals(expResult, result);
    }

    /**
     * Test of of method, of class Quantities.
     */
    @Test
    public void testOf_BigDecimal_UnitName() {
        System.out.println("of");
        BigDecimal amount = BigDecimal.TEN;
        UnitName unitName = UnitName.of("MASS:GRAM");
        Quantity expResult = new Quantity(BigDecimal.TEN, GRAM);
        Quantity result = Quantities.of(amount, unitName);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of of method, of class Quantities.
     */
    @Test
    public void testOf_BigDecimal_String() {
        System.out.println("of");
        BigDecimal amount = BigDecimal.TEN;
        String unitName = "MASS:GRAM";
        Quantity expResult = new Quantity(BigDecimal.TEN, GRAM);
        Quantity result = Quantities.of(amount, unitName);
        assertEquals(expResult, result);
        
    }
    
    
    /**
     * Test of of method, of class Quantities.
     * 
     * Birimler aynı ise değer aynı olmalı
     * 
     */
    @Test
    public void testConvert() throws UnitException {
        System.out.println("convert");
                
        
        Quantity q = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity expResult = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity result = Quantities.convert(q, MassUnitSet.GRAM);
        assertEquals(expResult, result);
        
    }
    
    /**
     * Test of of method, of class Quantities.
     * 
     * Birimler aynı ise değer aynı olmalı
     * 
     */
    @Test
    public void testConvert2() throws UnitException {
        System.out.println("convert2");
                
        Quantity q = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity expResult = new Quantity( new BigDecimal(100), MassUnitSet.DECIGRAM);
        Quantity result = Quantities.convert(q, MassUnitSet.DECIGRAM);
        assertEquals(expResult, result);
        
    }
    
    
    /**
     * Test of convert method, of class Quantities.
     * 
     * Saçma bir birime çevirmek istersek exception gelmeli
     * Uyumsuz unit
     */
    @Test( expected = UnitException.class)
    public void testConver3() throws UnitException {
        System.out.println("convert3");
        Quantity q = Quantities.of(BigDecimal.ONE, MassUnitSet.GRAM);
        Quantities.convert(q, UnitName.of("HEDE:HODO"));
        
    }
    
    /**
     * Test of convert method, of class Quantities.
     * 
     * Saçma bir birime çevirmek istersek exception gelmeli
     * Bilinmeyen Unit
     */
    @Test( expected = UnitException.class )
    public void testConver4() throws UnitException {
        System.out.println("convert4");
        Quantity q = Quantities.of(BigDecimal.ONE, MassUnitSet.GRAM);
        Quantities.convert(q, UnitName.of("MASS:HODO"));
        
    }
    
    /**
     * Test of convert method, of class Quantities.
     * 
     * Saçma bir birime çevirmek istersek exception gelmeli
     * Bilinmeyen Dimension
     */
    @Test( expected = UnitException.class )
    public void testConver5() throws UnitException {
        System.out.println("convert4");
        Quantity q = Quantities.of( BigDecimal.ONE, UnitName.of("HODO:BISI"));
        Quantities.convert(q, UnitName.of("HODO:HODO"));
        
    }
    
    
    /**
     * 
     * Verilen değeri qunatity toplar
     * 
     */
    @Test
    public void testAdd1() throws UnitException {
        System.out.println("add1");
                
        Quantity q = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity expResult = new Quantity( new BigDecimal(20), MassUnitSet.GRAM);
        Quantity result = Quantities.add( q, BigDecimal.TEN);
        assertEquals(expResult, result);
        
    }
    
    /**
     * 
     * Verilen değeri qunatity toplar
     * 
     */
    @Test
    public void testAdd2() throws UnitException {
        System.out.println("add2");
                
        Quantity q1 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity q2 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity expResult = new Quantity( new BigDecimal(20), MassUnitSet.GRAM);
        Quantity result = Quantities.add( q1, q2);
        assertEquals(expResult, result);
        
    }
    
    /**
     * 
     * Verilen değeri qunatity toplar
     * 
     */
    @Test
    public void testAdd3() throws UnitException {
        System.out.println("add3");
                
        Quantity q1 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity q2 = new Quantity(new BigDecimal(100), MassUnitSet.DECIGRAM);
        Quantity expResult = new Quantity( new BigDecimal(20), MassUnitSet.GRAM);
        Quantity result = Quantities.add( q1, q2);
        assertEquals(expResult, result);
        
    }
    
    /**
     * 
     * Verilen değeri qunatity toplar
     * 
     */
    @Test
    public void testAdd4() throws UnitException {
        System.out.println("add4");
                
        Quantity q1 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity q2 = new Quantity(new BigDecimal(100), MassUnitSet.DECIGRAM);
        Quantity expResult = new Quantity( new BigDecimal(2000), MassUnitSet.CENTIGRAM);
        Quantity result = Quantities.add(q1, q2, MassUnitSet.CENTIGRAM);
        assertEquals(expResult, result);
        
    }
    
    /**
     * 
     * Verilen değeri qunatity toplar
     * 
     */
    @Test
    public void testsubtract1() throws UnitException {
        System.out.println("subtract1");
                
        Quantity q = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity expResult = new Quantity( BigDecimal.ZERO, MassUnitSet.GRAM);
        Quantity result = Quantities.subtract(q, BigDecimal.TEN);
        assertEquals(expResult, result);
        
    }
    
    /**
     * 
     * Verilen değeri qunatity toplar
     * 
     */
    @Test
    public void testsubtract2() throws UnitException {
        System.out.println("subtract2");
                
        Quantity q1 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity q2 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity expResult = new Quantity( BigDecimal.ZERO, MassUnitSet.GRAM);
        Quantity result = Quantities.subtract( q1, q2);
        assertEquals(expResult, result);
        
    }
    
    /**
     * 
     * Verilen değeri qunatity toplar
     * 
     */
    @Test
    public void testsubtract3() throws UnitException {
        System.out.println("subtract3");
                
        Quantity q1 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity q2 = new Quantity(new BigDecimal(100), MassUnitSet.DECIGRAM);
        Quantity expResult = new Quantity( BigDecimal.ZERO, MassUnitSet.GRAM);
        Quantity result = Quantities.subtract( q1, q2);
        assertEquals(expResult, result);
        
    }
    
    /**
     * 
     * Verilen değeri qunatity toplar
     * 
     */
    @Test
    public void testsubtract4() throws UnitException {
        System.out.println("add4");
                
        Quantity q1 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity q2 = new Quantity(new BigDecimal(100), MassUnitSet.DECIGRAM);
        Quantity expResult = new Quantity( BigDecimal.ZERO, MassUnitSet.CENTIGRAM);
        Quantity result = Quantities.subtract(q1, q2, MassUnitSet.CENTIGRAM);
        assertEquals(expResult, result);
        
    }
    
    
    /**
     * 
     * Verilen değeri qunatity toplar
     * 
     */
    @Test
    public void testmultiply1() throws UnitException {
        System.out.println("multiply1");
                
        Quantity q = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity expResult = new Quantity( new BigDecimal(100), MassUnitSet.GRAM);
        Quantity result = Quantities.multiply(q, BigDecimal.TEN);
        assertEquals(expResult, result);
        
    }
    
    /**
     * 
     * Verilen değeri qunatity toplar
     * 
     */
    @Test
    public void testmultiply2() throws UnitException {
        System.out.println("multiply2");
                
        Quantity q1 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity q2 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity expResult = new Quantity( new BigDecimal(100), MassUnitSet.GRAM);
        Quantity result = Quantities.multiply( q1, q2);
        assertEquals(expResult, result);
        
    }
    
    /**
     * 
     * Verilen değeri qunatity toplar
     * 
     */
    @Test
    public void testmultiply3() throws UnitException {
        System.out.println("multiply3");
                
        Quantity q1 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity q2 = new Quantity(new BigDecimal(100), MassUnitSet.DECIGRAM);
        Quantity expResult = new Quantity( new BigDecimal(100), MassUnitSet.GRAM);
        Quantity result = Quantities.multiply( q1, q2);
        assertEquals(expResult, result);
        
    }
    
    /**
     * 
     * Verilen değeri qunatity toplar
     * 
     */
    @Test
    public void testmultiply4() throws UnitException {
        System.out.println("multiply4");
                
        Quantity q1 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity q2 = new Quantity(new BigDecimal(100), MassUnitSet.DECIGRAM);
        Quantity expResult = new Quantity( new BigDecimal(1000000), MassUnitSet.CENTIGRAM);
        Quantity result = Quantities.multiply(q1, q2, MassUnitSet.CENTIGRAM);
        assertEquals(expResult, result);
        
    }
    
    
    /**
     * 
     * Verilen değeri qunatity toplar
     * 
     */
    @Test
    public void testdivide1() throws UnitException {
        System.out.println("divide1");
                
        Quantity q = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity expResult = new Quantity( BigDecimal.ONE, MassUnitSet.GRAM);
        Quantity result = Quantities.divide(q, BigDecimal.TEN);
        assertEquals(expResult, result);
        
    }
    
    /**
     * 
     * Verilen değeri qunatity toplar
     * 
     */
    @Test
    public void testdivide2() throws UnitException {
        System.out.println("divide2");
                
        Quantity q1 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity q2 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity expResult = new Quantity( BigDecimal.ONE, MassUnitSet.GRAM);
        Quantity result = Quantities.divide( q1, q2);
        assertEquals(expResult, result);
        
    }
    
    /**
     * 
     * Verilen değeri qunatity toplar
     * 
     */
    @Test
    public void testdivide3() throws UnitException {
        System.out.println("divide3");
                
        Quantity q1 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity q2 = new Quantity(new BigDecimal(100), MassUnitSet.DECIGRAM);
        Quantity expResult = new Quantity( BigDecimal.ONE, MassUnitSet.GRAM);
        Quantity result = Quantities.divide( q1, q2);
        assertEquals(expResult, result);
        
    }
    
    /**
     * 
     * Verilen değeri qunatity toplar
     * 
     */
    @Test
    public void testdivide4() throws UnitException {
        System.out.println("divide4");
                
        Quantity q1 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity q2 = new Quantity(new BigDecimal(100), MassUnitSet.DECIGRAM);
        Quantity expResult = new Quantity( BigDecimal.ONE, MassUnitSet.CENTIGRAM);
        Quantity result = Quantities.divide(q1, q2, MassUnitSet.CENTIGRAM);
        assertEquals(expResult, result);
        
    }
    
    /**
     * 
     * Sıfıra bölme hatası
     * 
     */
    @Test( expected = ArithmeticException.class)
    public void testdivide5() throws UnitException {
        System.out.println("divide5");
                
        Quantity q1 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity q2 = new Quantity(BigDecimal.ZERO, MassUnitSet.DECIGRAM);
        Quantity expResult = new Quantity( BigDecimal.ONE, MassUnitSet.CENTIGRAM);
        Quantity result = Quantities.divide(q1, q2, MassUnitSet.CENTIGRAM);
        assertEquals(expResult, result);
        
    }
    
    /**
     * 
     * Sıfır'ı bölersek 0 gelir
     * 
     */
    @Test()
    public void testdivide6() throws UnitException {
        System.out.println("divide6");
                
        Quantity q1 = new Quantity(BigDecimal.TEN, MassUnitSet.GRAM);
        Quantity q2 = new Quantity(BigDecimal.ZERO, MassUnitSet.DECIGRAM);
        Quantity expResult = new Quantity( BigDecimal.ZERO, MassUnitSet.CENTIGRAM);
        Quantity result = Quantities.divide(q2, q1, MassUnitSet.CENTIGRAM);
        assertEquals(expResult, result);
        
    }
    
    
    /**
     * 
     * PI testi
     * 
     * FIXME: Burada sanırım bir hata yapıyoruz.
     * 
     */
    @Test()
    public void testdivide7() throws UnitException {
        System.out.println("divide7");
                
        Quantity q1 = new Quantity( new BigDecimal(22), MassUnitSet.GRAM);
        Quantity q2 = new Quantity( new BigDecimal(7), MassUnitSet.GRAM);
        Quantity expResult = new Quantity( new BigDecimal("3.142857142857143"), MassUnitSet.GRAM);
        
        Quantity result = Quantities.divide(q1, q2, MassUnitSet.GRAM);
        
        
        Quantity q3 = Quantities.of(new BigDecimal("3.142857142857143"), MassUnitSet.GRAM);
                
        System.out.println(Quantities.convert(q1, MassUnitSet.CENTIGRAM ));
        System.out.println(Quantities.convert(q2, MassUnitSet.CENTIGRAM ));
        System.out.println(Quantities.convert(q3, MassUnitSet.CENTIGRAM ));
        System.out.println(Quantities.divide(q1, q2, MassUnitSet.GRAM ));
        System.out.println(Quantities.divide(q1, q2, MassUnitSet.CENTIGRAM ));
        
        assertEquals(expResult, result);
        
    }
    
    /**
     * 
     * PI testi 2
     * 
     * FIXME: Burada sanırım bir hata yapıyoruz.
     * 
     */
    @Test()
    public void testdivide8() throws UnitException {
        System.out.println("divide8");
                
        Quantity q1 = new Quantity( new BigDecimal(22), MassUnitSet.GRAM);
        Quantity q2 = new Quantity( new BigDecimal(700), MassUnitSet.CENTIGRAM);
        Quantity expResult = new Quantity( new BigDecimal("3.142857142857143"), MassUnitSet.GRAM);
        
        Quantity result = Quantities.divide(q1, q2, MassUnitSet.GRAM);
        
        
        Quantity q3 = Quantities.of(new BigDecimal("3.142857142857143"), MassUnitSet.GRAM);
                
        System.out.println(Quantities.convert(q1, MassUnitSet.CENTIGRAM ));
        System.out.println(Quantities.convert(q2, MassUnitSet.CENTIGRAM ));
        System.out.println(Quantities.convert(q3, MassUnitSet.CENTIGRAM ));
        System.out.println(Quantities.divide(q1, q2, MassUnitSet.GRAM ));
        System.out.println(Quantities.divide(q1, q2, MassUnitSet.CENTIGRAM ));
        
        assertEquals(expResult, result);
        
    }

    /**
     * 
     * Ondalık bölmeli
     * 
     * FIXME: Burada sanırım bir hata yapıyoruz.
     * 
     */
    @Test()
    public void testdivide9() throws UnitException {
        System.out.println("divide9");
                
        Quantity q1 = new Quantity( new BigDecimal(1.3), MassUnitSet.GRAM);
        Quantity q2 = new Quantity( new BigDecimal(7.21), MassUnitSet.CENTIGRAM);
        Quantity expResult = new Quantity( new BigDecimal("18.03051317614424"), MassUnitSet.GRAM);
        
        Quantity result = Quantities.divide(q1, q2, MassUnitSet.GRAM);
        
        assertEquals(expResult, result);
        
    }
    
    /**
     * 
     * Ondalık bölmeli
     * 
     * FIXME: Burada sanırım bir hata yapıyoruz.
     * 
     */
    @Test()
    public void testdivide10() throws UnitException {
        System.out.println("divide9");
                
        Quantity q1 = Quantity.of(1.3, MassUnitSet.GRAM);
        Quantity q2 = Quantity.of(7.21, MassUnitSet.CENTIGRAM);
        Quantity expResult = Quantity.of("18.03051317614424", MassUnitSet.GRAM);
        
        Quantity result = q1.divide(q2, MassUnitSet.GRAM);
        
        assertEquals(expResult, result);
        
    }
}
