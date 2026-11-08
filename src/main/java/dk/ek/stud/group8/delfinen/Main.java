package dk.ek.stud.group8.delfinen;

@SuppressWarnings("unused")
public class Main {
    enum Cardinal3 {
        ONE,
        TWO,
        THREE
    }

    sealed interface Cardinal4 {
        record One() implements Cardinal4 { }
        record Two() implements Cardinal4 { }
        record Three() implements Cardinal4 { }
        record Four() implements Cardinal4 { }
    }

    static void main() {
        // SUM TYPES (finite)
        Void unit = null; // 2^0 = 1 values (can only ever be null)
        boolean bool = false; // 2^1 = 1 + 1 = 2 values (true or false)
        Cardinal3 cardinal3 = Cardinal3.ONE; // 1 + 1 + 1 = 3 values
        Cardinal4 cardinal4 = new Cardinal4.One(); // 1 + 1 + 1 + 1 = 4 values
        byte cardinal256 = 0; // 2^8 = 256 values
        short cardinal65536 = 0; // 2^16 = 65536 values

        // PRODUCT TYPES
        record Composite4(Cardinal3 a, Cardinal3 b) { } // (1 + 1 + 1) * (1 + 1 + 1) = 9 values
        Composite4 composite9 = new Composite4(Cardinal3.THREE, Cardinal3.TWO);

        // SUM OF PRODUCTS
        SumOfProduct sumOfProduct1 = new SumOfProduct.BoringValue();
        SumOfProduct sumOfProduct2 = new SumOfProduct.CoolValue(Cardinal3.TWO, true);
        // (1) + (3 * 2) = 7 values
    }

    sealed interface SumOfProduct {
        record BoringValue() implements SumOfProduct { }
        record CoolValue(Cardinal3 value, boolean isAwesome) implements SumOfProduct { }
    }
}
